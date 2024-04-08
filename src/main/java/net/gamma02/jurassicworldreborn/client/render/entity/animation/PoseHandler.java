package net.gamma02.jurassicworldreborn.client.render.entity.animation;

import com.github.alexthe666.citadel.animation.Animation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.model.animation.dto.AnimatableRenderDefDTO;
import net.gamma02.jurassicworldreborn.client.model.animation.dto.AnimationsDTO;
import net.gamma02.jurassicworldreborn.client.model.animation.dto.PoseDTO;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Animatable;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.GrowthStage;
import net.gamma02.jurassicworldreborn.common.legacy.tabula.TabulaModelHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class PoseHandler<ENTITY extends LivingEntity & Animatable> {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AnimatableRenderDefDTO.class, new AnimatableRenderDefDTO.AnimatableDeserializer())
            .create();

    private Map<GrowthStage, ModelData> modelData;

    public PoseHandler(Dinosaur dinosaur) {
        this(dinosaur.getName(), dinosaur.getSupportedStages());
    }

    public PoseHandler(String name, List<GrowthStage> supported) {
        name = name.toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
        this.modelData = new EnumMap<>(GrowthStage.class);
        URI entityResource;
        try {
            entityResource = new URI("/assets/jurassicworldreborn/models/entities/" + name + "/");
        } catch (URISyntaxException e) {
            Jurassicworldreborn.getLogger().fatal("Illegal URI /assets/jurassicworldreborn/models/entities/" + name + "/", e);
            return;
        }
        for (GrowthStage growth : GrowthStage.values()) {
            try {
                GrowthStage actualGrowth = growth;
                if (!supported.contains(actualGrowth)) {
                    actualGrowth = GrowthStage.ADULT;
                }
                if (this.modelData.containsKey(actualGrowth)) {
                    this.modelData.put(growth, this.modelData.get(actualGrowth));
                } else {
                    ModelData loaded = this.loadModelData(entityResource, name, actualGrowth);
                    this.modelData.put(growth, loaded);
                    if (actualGrowth != growth) {
                        this.modelData.put(actualGrowth, loaded);
                    }
                }
            } catch (Exception e) {//wtf why - gamma
                Jurassicworldreborn/*.INSTANCE*/.getLogger().fatal("Failed to parse growth stage " + growth + " for dinosaur " + name, e);
                this.modelData.put(growth, new ModelData());
            }
        }
    }

    //TODO: Speed up loading, multithread this! This takes like 5 miniutes to do lmao
    private ModelData loadModelData(URI resourceURI, String name, GrowthStage growth) {
        String growthName = growth.name().toLowerCase(Locale.ROOT);
        URI growthSensitiveDir = resourceURI.resolve(growthName + "/");
        URI definitionFile = growthSensitiveDir.resolve(name + "_" + growthName + ".json");
        InputStream modelStream = TabulaModelHelper.class.getResourceAsStream(definitionFile.toString());
        if (modelStream == null) {
            throw new IllegalArgumentException("No model definition for the dino " + name + " with grow-state " + growth + " exists. Expected at " + definitionFile);
        }
        try {
            Reader reader = new InputStreamReader(modelStream);
            AnimationsDTO rawAnimations = GSON.fromJson(reader, AnimationsDTO.class);
            ModelData data = this.loadModelData(growthSensitiveDir, rawAnimations);
            Jurassicworldreborn.getLogger().debug("Successfully loaded " + name + "(" + growth + ") from " + definitionFile);
            reader.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ModelData loadModelData(URI resourceURI, AnimationsDTO animationsDefinition) {
        if (animationsDefinition == null || animationsDefinition.poses == null
                || animationsDefinition.poses.get(EntityAnimation.IDLE.name()) == null
                || animationsDefinition.poses.get(EntityAnimation.IDLE.name()).length == 0) {
            throw new IllegalArgumentException("Animation files must define at least one pose for the IDLE animation");
        }
        List<String> posedModelResources = new ArrayList<>();
        for (PoseDTO[] poses : animationsDefinition.poses.values()) {
            if (poses == null) {
                continue;
            }
            for (PoseDTO pose : poses) {
                if (pose == null) {
                    continue;
                }
                if (pose.pose == null) {
                    throw new IllegalArgumentException("Every pose must define a pose file");
                }
                String resolvedRes = this.resolve(resourceURI, pose.pose);
                int index = posedModelResources.indexOf(resolvedRes);
                if (index == -1) {
                    pose.index = posedModelResources.size();
                    posedModelResources.add(resolvedRes);
                } else {
                    pose.index = index;
                }
            }
        }

        Map<Animation, float[][]> animations = new HashMap<>();

        for (Map.Entry<String, PoseDTO[]> entry : animationsDefinition.poses.entrySet()) {
            Animation animation = EntityAnimation.valueOf(entry.getKey()).get();
            PoseDTO[] poses = entry.getValue();
            float[][] poseSequence = new float[poses.length][2];
            for (int i = 0; i < poses.length; i++) {
                poseSequence[i][0] = poses[i].index;
                poseSequence[i][1] = poses[i].time;
            }
            animations.put(animation, poseSequence);
        }

//        if (DistExecutor.safeCallWhenOn()) {
//            return this.loadModelDataClient(posedModelResources, animations);
//        }
//
//        return new ModelData(animations);
        ModelData data;
        ModelData clientAttempt = DistExecutor.<ModelData>safeCallWhenOn(Dist.CLIENT, () ->  SafeClientMethods.loadModelDataForClient(posedModelResources, animations));//crying and sobbing why is forge like this
        if(clientAttempt != null){
            return clientAttempt;
        }else{
            return new ModelData(animations);
        }

    }



    private String resolve(URI dinoDirURI, String posePath) {
        URI uri = dinoDirURI.resolve(posePath);
        return uri.toString();
    }

    //this creates in an instance of an entity animation handler
    @OnlyIn(Dist.CLIENT)
    public JabelarAnimationHandler<ENTITY> createAnimationHandler(ENTITY entity, AnimatableModel model, GrowthStage growthStage, boolean useInertialTweens) {
        ModelData growthModel = this.modelData.get(growthStage);
        if (!entity.canUseGrowthStage(growthStage)) {
            growthModel = this.modelData.get(growthStage);
        }
        return new JabelarAnimationHandler<>(entity, model, growthModel.poses, growthModel.animations, useInertialTweens);
    }

    public Map<Animation, float[][]> getAnimations(GrowthStage growthStage) {
        return this.modelData.get(growthStage).animations;
    }

    public float getAnimationLength(Animation animation, GrowthStage growthStage) {
        Map<Animation, float[][]> animations = this.getAnimations(growthStage);

        float duration = 0;

        if (animation != null) {
            float[][] poses = animations.get(animation);

            if (poses != null) {
                for (float[] pose : poses) {
                    duration += pose[1];
                }
            }
        }

        return duration;
    }

    public boolean hasAnimation(Animation animation, GrowthStage growthStage) {
        return this.modelData.get(growthStage).animations.get(animation) != null;
    }

    static class ModelData {
        @OnlyIn(Dist.CLIENT)
        PosedCuboid[][] poses;

        Map<Animation, float[][]> animations;

        public ModelData() {
            this(null);
        }

        @OnlyIn(Dist.CLIENT)
        public ModelData(PosedCuboid[][] cuboids, Map<Animation, float[][]> animations) {
            this(animations);

            if (cuboids == null) {
                cuboids = new PosedCuboid[0][];
            }

            this.poses = cuboids;
        }

        public ModelData(Map<Animation, float[][]> animations) {
            if (animations == null) {
                animations = new LinkedHashMap<>();
            }

            this.animations = animations;
        }
    }
}
