package com.inolia_zaicek.more_tetra_tools.Effect.Khaslana;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.calamitySoulscorchEdictEffect;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class CalamitySoulscorchEdict {
    //新建nbt
    private static final ResourceLocation soulscorch_tick = new ResourceLocation(MoreTetraTools.MODID, "calamity_soulscorch_edict");
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        ItemStack mainHandItem = livingEntity.getMainHandItem();
        if (mainHandItem.getItem() instanceof IModularItem item) {
            //火种
            float effectLevel = (float) item.getEffectLevel(livingEntity.getMainHandItem(), calamitySoulscorchEdictEffect);
            if (effectLevel > 0&&livingEntity.hasEffect(MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get())) {
                //获取工具nbt数值
                CompoundTag persistentData = mainHandItem.getOrCreateTag();
                //不存在就是0
                    int tick =persistentData.getInt(String.valueOf(soulscorch_tick));
                    if(tick<20 *2) {
                        persistentData.putInt(String.valueOf(soulscorch_tick), Math.min(20 *2, tick + 1));
                        livingEntity.removeEffect(MTTEffectsRegister.Soulscorch.get());
                    }
                    if(tick>=20 *2){
                        //反击buff
                        livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Soulscorch.get(), 200, 0));
                    }
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        //挨打减伤
        if (event.getEntity()!=null) {
            LivingEntity livingEntity = event.getEntity();
            ItemStack mainHandItem = livingEntity.getMainHandItem();
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float effectLevel = (float) item.getEffectLevel(livingEntity.getMainHandItem(), calamitySoulscorchEdictEffect);
                if (effectLevel > 0 && livingEntity.hasEffect(MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get())
                && livingEntity.hasEffect(MTTEffectsRegister.Soulscorch.get()) ) {
                    //等级
                    int buffLevel = livingEntity.getEffect(MTTEffectsRegister.Soulscorch.get()).getAmplifier();
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Soulscorch.get(), 200, buffLevel+1));
                    //减伤
                    float number = effectLevel/100;
                    event.setAmount(event.getAmount()*(1-number));
                }
            }
        }
        //反击
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            LivingEntity mob = event.getEntity();
            ItemStack mainHandItem = livingEntity.getMainHandItem();
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float effectLevel = (float) item.getEffectLevel(livingEntity.getMainHandItem(), calamitySoulscorchEdictEffect);
                if (effectLevel > 0 && livingEntity.hasEffect(MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get())
                        && livingEntity.hasEffect(MTTEffectsRegister.Soulscorch.get()) && MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                    //等级
                    int buffLevel = livingEntity.getEffect(MTTEffectsRegister.Soulscorch.get()).getAmplifier()+1;
                    var DamageType = MTTTickZero.hasSource(livingEntity.level(), MTTTickZero.TICKAMAGE, livingEntity);
                    float atk =(float) livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    float number = effectLevel/100;
                    //连击
                    for (int i = 0; i < buffLevel; i++) {
                    if(livingEntity instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                        mob.invulnerableTime = 0;
                        mob.hurt(DamageType, atk * number);
                    }
                    CompoundTag persistentData = mainHandItem.getOrCreateTag();
                    persistentData.putInt(String.valueOf(soulscorch_tick), 0);
                    livingEntity.removeEffect(MTTEffectsRegister.Soulscorch.get());
                }else{
                    CompoundTag persistentData = mainHandItem.getOrCreateTag();
                    persistentData.putInt(String.valueOf(soulscorch_tick), 0);
                    livingEntity.removeEffect(MTTEffectsRegister.Soulscorch.get());
                }
            }
        }
    }
}
