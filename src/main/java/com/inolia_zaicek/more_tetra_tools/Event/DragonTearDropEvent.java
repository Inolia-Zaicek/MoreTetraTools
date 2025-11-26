package com.inolia_zaicek.more_tetra_tools.Event;

import com.inolia_zaicek.more_tetra_tools.Register.MTTItemRegister;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DragonTearDropEvent {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void drop(LivingDropsEvent event) {
        if (event.getEntity() instanceof EnderDragon enderDragon) {
            Level level = enderDragon.level();
            for (int i = 0; i < 4; i++) {
                ItemEntity itementity = new ItemEntity(level, enderDragon.getX(),
                        enderDragon.getY(), enderDragon.getZ(),
                        MTTItemRegister.DragonTear.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }
        }
    }
}
