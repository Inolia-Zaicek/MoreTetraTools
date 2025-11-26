package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class ToolEffect {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(mainHandItem, tool_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, tool_Effect);
            }
                if (effectLevel > 0) {
                    //event.setAmount(0);
                    event.setCanceled(true);
                }
        }
        if (event.getSource().getDirectEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(mainHandItem, tool_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, tool_Effect);
            }
            if (effectLevel > 0) {
                //event.setAmount(0);
                event.setCanceled(true);
            }
        }
    }
}