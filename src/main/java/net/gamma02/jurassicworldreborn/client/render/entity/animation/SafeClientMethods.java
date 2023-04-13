package net.gamma02.jurassicworldreborn.client.render.entity.animation;


import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.openjdk.nashorn.internal.codegen.CompilerConstants;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class SafeClientMethods {

    static SafeClientMethods INSTANCE = new SafeClientMethods();
    public SafeClientMethods(){}

    static Callable<PoseHandler.ModelData> loadModelDataForClient(List<String> posedModelResources, Map<Animation, float[][]> animations){
        return new Callable<PoseHandler.ModelData>() {
            @Override
            public PoseHandler.ModelData call() throws Exception {
                return INSTANCE.loadModelDataClient(posedModelResources, animations);
            }
        };
    }

    @OnlyIn(Dist.CLIENT)
    private PoseHandler.ModelData loadModelDataClient(List<String> posedModelResources, Map<Animation, float[][]> animations) {
        PosedCuboid[][] posedCuboids = new PosedCuboid[posedModelResources.size()][];
        AnimatableModel mainModel = JabelarAnimationHandler.loadModel(posedModelResources.get(0));
        if (mainModel == null) {
            throw new IllegalArgumentException("Couldn't load the model from " + posedModelResources.get(0));
        }
        String[] identifiers = mainModel.getCubeIdentifierArray();
        int partCount = identifiers.length;
        for (int i = 0; i < posedModelResources.size(); i++) {
            String resource = posedModelResources.get(i);
            AnimatableModel model = JabelarAnimationHandler.loadModel(resource);
            if (model == null) {
                throw new IllegalArgumentException("Couldn't load the model from " + resource);
            }
            PosedCuboid[] pose = new PosedCuboid[partCount];
            for (int partIndex = 0; partIndex < partCount; partIndex++) {
                String identifier = identifiers[partIndex];
                AdvancedModelBox cube = model.getCubeByIdentifier(identifier);
                if (cube == null) {
                    AdvancedModelBox mainCube = mainModel.getCubeByIdentifier(identifier);
//                    if(Jurassicworldreborn.INSTANCE.getEnv()) {//wtf???
                    Jurassicworldreborn.getLogger().error("Could not retrieve cube " + identifier + " (" + mainCube.boxName + ", " + partIndex + ") from the model " + resource);
//                    }
                    pose[partIndex] = new PosedCuboid(mainCube);
                } else {
                    pose[partIndex] = new PosedCuboid(cube);
                }
            }
            posedCuboids[i] = pose;
        }
        return new PoseHandler.ModelData(posedCuboids, animations);
    }
}
