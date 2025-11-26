package com.inolia_zaicek.more_tetra_tools.Event;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.fateDivineVesselEffect;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class UnreviveEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void LivingDeathVampire(LivingDeathEvent event) {
        if (event.getEntity().hasEffect(MTTEffectsRegister.Unrevive.get())) {
            event.getEntity().gameEvent(GameEvent.ENTITY_DIE);
            event.getEntity().kill();
            event.getEntity().isDeadOrDying();
            event.getEntity().setHealth(0);
            event.getEntity().deathTime = 20;
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getEntity().hasEffect(MTTEffectsRegister.Unrevive.get()) && event.getEntity().getHealth()<=0) {
            event.getEntity().gameEvent(GameEvent.ENTITY_DIE);
            event.getEntity().kill();
            event.getEntity().isDeadOrDying();
            event.getEntity().setHealth(0);
            event.getEntity().deathTime = 20;
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getEntity().hasEffect(MTTEffectsRegister.Unrevive.get()) && event.getEntity().getHealth()<=0) {
            event.getEntity().gameEvent(GameEvent.ENTITY_DIE);
            event.getEntity().kill();
            event.getEntity().isDeadOrDying();
            event.getEntity().setHealth(0);
            event.getEntity().deathTime = 20;
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDamage(LivingUseTotemEvent event) {
        if (event.getEntity().hasEffect(MTTEffectsRegister.Unrevive.get())) {
            event.setCanceled(true);
        }
    }
}
