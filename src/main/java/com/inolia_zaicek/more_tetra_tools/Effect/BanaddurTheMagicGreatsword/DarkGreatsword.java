package com.inolia_zaicek.more_tetra_tools.Effect.BanaddurTheMagicGreatsword;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class DarkGreatsword {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(dark_greatsword_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(dark_greatsword_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                dark_greatsword_Name, 0, 50, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(dark_greatsword_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }
    //伤转、吸血（法伤增幅在法伤effect内
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
            //攻击
            if (event.getSource().getEntity() instanceof Player player) {
                var mob = event.getEntity();
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offhandItem = player.getOffhandItem();
                int effectLevel = 0;
                if (mainHandItem.getItem() instanceof IModularItem item) {
                    float mainEffectLevel = item.getEffectLevel(mainHandItem, dark_greatsword_Effect);
                    if (mainEffectLevel > 0) {
                        effectLevel += (int) mainEffectLevel;
                    }
                }
                if (offhandItem.getItem() instanceof IModularItem item) {
                    float offEffectLevel = item.getEffectLevel(offhandItem, dark_greatsword_Effect);
                    if (offEffectLevel > 0) {
                        effectLevel += (int) offEffectLevel;
                    }
                }
                if (effectLevel > 0) {
                    float number = (float) effectLevel / 100;
                    float damage = event.getAmount();
                    event.setAmount(Math.max(0, damage * (1 - number)));
                    mob.invulnerableTime = 0;
                    mob.setLastHurtByPlayer(player);
                    var DamageType = MTTTickZero.hasSource(player.level(), DamageTypes.MAGIC, player);
                    mob.hurt(DamageType, damage * number);
                }
            } else if (event.getSource().getDirectEntity() instanceof Player player) {
                var mob = event.getEntity();
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offhandItem = player.getOffhandItem();
                int effectLevel = 0;
                if (mainHandItem.getItem() instanceof IModularItem item) {
                    float mainEffectLevel = item.getEffectLevel(mainHandItem, dark_greatsword_Effect);
                    if (mainEffectLevel > 0) {
                        effectLevel += (int) mainEffectLevel;
                    }
                }
                if (offhandItem.getItem() instanceof IModularItem item) {
                    float offEffectLevel = item.getEffectLevel(offhandItem, dark_greatsword_Effect);
                    if (offEffectLevel > 0) {
                        effectLevel += (int) offEffectLevel;
                    }
                }
                if (effectLevel > 0) {
                    float number = (float) effectLevel / 100;
                    float damage = event.getAmount();
                    event.setAmount(Math.max(0, damage * (1 - number)));
                    mob.invulnerableTime = 0;
                    mob.setLastHurtByPlayer(player);
                    var DamageType = MTTTickZero.hasSource(player.level(), DamageTypes.MAGIC, player);
                    mob.hurt(DamageType, damage * number);
                }
            }
        }
        //吸血
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectEfficiency(mainHandItem, dark_greatsword_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectEfficiency(offhandItem, dark_greatsword_Effect);
            }
            if (effectLevel > 0) {
                player.heal(event.getAmount()*effectLevel/100);
            }
        }else if (event.getSource().getDirectEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectEfficiency(mainHandItem, dark_greatsword_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectEfficiency(offhandItem, dark_greatsword_Effect);
            }
            if (effectLevel > 0) {
                player.heal(event.getAmount()*effectLevel/100);
            }
        }
    }
}