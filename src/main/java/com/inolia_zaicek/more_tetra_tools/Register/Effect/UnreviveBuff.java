package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class UnreviveBuff extends MobEffect {
    public UnreviveBuff() {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {return pDuration % 1 == 0;}
    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int pAmplifier){
        if(livingEntity.getHealth()<=0) {
            livingEntity.gameEvent(GameEvent.ENTITY_DIE);
            livingEntity.kill();
            livingEntity.isDeadOrDying();
            livingEntity.setHealth(0);
        }
    }
}
