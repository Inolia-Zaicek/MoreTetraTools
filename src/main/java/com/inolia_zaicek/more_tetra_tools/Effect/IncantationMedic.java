package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static net.minecraft.tags.DamageTypeTags.WITCH_RESISTANT_TO;

public class IncantationMedic {

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity player) {
                var mob = event.getEntity();
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, incantationMedicEffect));
                if (effectLevel > 0) {
                    if (event.getSource().is(WITCH_RESISTANT_TO)) {
                        float finish = event.getAmount() * (effectLevel / 100);

                        var playerList = MTTUtil.PlayerList(6,mob);
                        for (Player players:playerList){
                            if(players!=null) {
                                players.heal(finish);
                            }
                        }
                    }
                }
            } else if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
                var mob = event.getEntity();
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, incantationMedicEffect));
                if (effectLevel > 0) {
                    if (event.getSource().is(WITCH_RESISTANT_TO)) {
                        float finish = event.getAmount() * (effectLevel / 100);

                        var playerList = MTTUtil.PlayerList(6,mob);
                        for (Player players:playerList){
                            if(players!=null) {
                                players.heal(finish);
                            }
                        }
                    }
                }
            }
        }
}
