package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class ToolEffect {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, tool_Effect));
                if (effectLevel > 0) {
                    //event.setAmount(0);
                    event.setCanceled(true);
                }
        }
        if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, tool_Effect));
            if (effectLevel > 0) {
                //event.setAmount(0);
                event.setCanceled(true);
            }
        }
    }
}