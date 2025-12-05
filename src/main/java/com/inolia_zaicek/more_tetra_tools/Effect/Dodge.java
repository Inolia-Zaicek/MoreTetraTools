package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class Dodge {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        //闪避统一计算（焰尾、老爷子
        if (event.getEntity()!=null) {
            LivingEntity player = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, flameheartEffect));
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, halfMoonEffect));
            if (effectLevel > 0||effectLevel2>0) {
                Random random = new Random();
                //闪避几率
                float chance = 0;
                //焰尾
                if (effectLevel > 0) {
                    chance += 10;
                }
                //老爷子
                if (effectLevel2 > 0) {
                    chance += effectLevel2;
                }
                //焰心状态
                if (player.hasEffect(MTTEffectsRegister.Flameheart.get())) {
                    chance += effectLevel;
                }
                //结算
                if (random.nextInt(100) <= chance) {
                    event.setAmount(0);
                    //有焰心等级
                    if (effectLevel > 0) {
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.VanguardSwordsmanship.get(), 300, 0));
                    }
                }
            }
        }
    }
}
