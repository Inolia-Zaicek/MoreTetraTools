package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class MosterHunter {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        //攻击
        if (event.getSource().getEntity() instanceof LivingEntity player && event.getEntity() instanceof LivingEntity) {
            var mob = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, mosterHunterEffect));
            float mobHp = mob.getMaxHealth();
            float playerHhp = player.getMaxHealth();
            //生命值不等
            if (effectLevel > 0 && mobHp != playerHhp) {
                //怪物大于玩家
                if (mobHp > playerHhp) {
                    //生命值差额
                    float dhp =Math.min(80,mobHp-playerHhp);
                    //增幅强度*生命值差额进度
                    float finish = 1 + (effectLevel/100)*(dhp/80);
                    event.setAmount(event.getAmount() * finish);
                }
            }
        }
        else if (event.getSource().getDirectEntity() instanceof LivingEntity player && event.getEntity() instanceof LivingEntity) {
            var mob = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, mosterHunterEffect));
            float mobHp = mob.getMaxHealth();
            float playerHhp = player.getMaxHealth();
            //生命值不等
            if (effectLevel > 0 && mobHp != playerHhp) {
                //怪物大于玩家
                if (mobHp > playerHhp) {
                    //生命值差额
                    float dhp =Math.min(80,mobHp-playerHhp);
                    //增幅强度*生命值差额进度
                    float finish = 1 + (effectLevel/100)*(dhp/80);
                    event.setAmount(event.getAmount() * finish);
                }
            }
        }
    }
}
