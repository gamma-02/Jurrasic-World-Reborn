package net.gamma02.jurassicworldreborn.common.entities.EntityUtils;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 *
 * this is just to help with adding AI to entities<br>
 * *slaps code* this puppy is only for me to be  l  a  z  y and not correct every entities' code for tasks.<br>
 * doing that sounds like a problem for <i>someone <b>else</b></i>
 * <br>
 * @author  gamma_02
 * */
public class TaskHelper {

     GoalSelector goalSelector;
     TargetSelector targetSelector;
     Class<? extends Entity> entityClass;

    Object2IntArrayMap<Goal> goalPriorityMap = new Object2IntArrayMap<>();

    public TaskHelper(GoalSelector Eselector/*if you get this refrence, nice*/, TargetSelector Tselector, Class<? extends Entity> entityClass){
        this.goalSelector = Eselector;
        this.targetSelector = Tselector;
        this.entityClass = entityClass;
    }

    public TaskHelper(Class<? extends Entity> entityClass){
        this.entityClass = entityClass;
    }

    public void setGoalSelector(GoalSelector selector){
        if(selector != null){//do NOT want this null.. same with target selector - gamma_02
            this.goalSelector = selector;
        }
    }

    public void addGoal(Goal goal, int priority){
        this.goalPriorityMap.put(goal, priority);
    }

    public int getPriority(Goal goal){
        return this.goalPriorityMap.apply(goal);
    }

    public List<Goal> getGoalsForPriority(int priority){
        if(this.goalPriorityMap.containsValue(priority)){
            return goalPriorityMap.keySet().stream().filter((goal) -> goalPriorityMap.apply(goal) == priority).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
