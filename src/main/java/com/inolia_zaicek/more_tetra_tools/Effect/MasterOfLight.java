package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class MasterOfLight {
    //光能数额实际上是*10再/10的，方便记录
    private static final String light_power_nbt = MoreTetraTools.MODID + ":light_power";

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        LivingEntity mob = event.getEntity();
        // 确保伤害来源是 LivingEntity（例如玩家或怪物）
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            //基础伤害数额
            float baseDamage = event.getAmount();
            //追加的基础伤害
            float fixedDamage = 0;
            //耀斑
            float effectLevel1 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, flares_of_the_blazing_sun_Effect);
            float effectEfficiency1 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, flares_of_the_blazing_sun_Effect);
            //破晓
            float effectLevel2 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, light_of_thie_vanish_dawn_Effect);
            float effectEfficiency2 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, light_of_thie_vanish_dawn_Effect);
            if(isUltimateBossEntity(event.getEntity().getType()) ){
                effectLevel1=effectLevel1*3;
                effectEfficiency1=effectEfficiency1*3;
                effectLevel2=effectLevel2*3;
                effectEfficiency2=effectEfficiency2*3;
            }
            //光能
            CompoundTag compoundTag = livingEntity.getPersistentData();
            int lightPower = compoundTag.getInt(light_power_nbt);
            //记录livingEntity.sendSystemMessage(Component.literal(String.valueOf("1")).withStyle(ChatFormatting.GOLD));
            //耀斑————增伤
            if (effectLevel1 > 0 || effectEfficiency1 > 0) {
                int addLightPower = (int) (10 * baseDamage * effectLevel1 / 100);
                if(MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                    if(isUltimateBossEntity(event.getEntity().getType()) ) {
                        compoundTag.putInt(light_power_nbt, lightPower + addLightPower*3);
                    }else{
                        compoundTag.putInt(light_power_nbt, lightPower + addLightPower);
                    }
                }
                fixedDamage += (float) (lightPower / 10) * (effectEfficiency1 / 100);
            }
            //破晓
            if (effectLevel2 > 0 || effectEfficiency2 > 0) {
                //光能阈值(/20==/2 / 10
                float number = (float) lightPower / 20;
                //目标血量小于数额
                if (mob.getHealth() <= (float) (lightPower / 10) * (effectEfficiency2 / 100)) {
                    fixedDamage += (float) (lightPower / 10) * (effectEfficiency2 / 100);
                }
                if (baseDamage <= number  &&  MTTDamageSourceHelper.isMeleeAttack(event.getSource())  ) {
                    var DamageType = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.LIGHTNING_BOLT, livingEntity);
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, ((float) lightPower / 10) * effectLevel2 / 100);
                    mob.invulnerableTime = 0;
                }
            }
            if (fixedDamage > 0) {
                event.setAmount(baseDamage + fixedDamage);
            }
        }
    }
    //扣除光能
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        CompoundTag compoundTag = livingEntity.getPersistentData();
        int lightPower = compoundTag.getInt(light_power_nbt);
        //10光能：1点伤害
        int now =Math.max(0, (int) (lightPower-lightPower*0.1F) );
        if(lightPower>0&&livingEntity.level().getGameTime() % 20 == 0) {
            compoundTag.putInt(light_power_nbt, now);
        }
    }
    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof IModularItem item && event.getEntity()!=null) {
            float effectLevel = item.getEffectLevel(itemStack, flares_of_the_blazing_sun_Effect);
            if (effectLevel > 0) {
                //获取nbt数值
                CompoundTag compoundTag = event.getEntity().getPersistentData();
                //不存在就是0
                int lightPower = compoundTag.getInt(light_power_nbt);
                if (lightPower > 0 ) {
                    event.getToolTip().add(Component.translatable("tooltip.more_tetra_tools.tenka_ichiken_tick", lightPower/10 ).withStyle(ChatFormatting.YELLOW)); // 使用灰色
                }
            }
        }
    }
}
