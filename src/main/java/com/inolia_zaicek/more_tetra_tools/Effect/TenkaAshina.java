package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static net.minecraft.tags.DamageTypeTags.IS_FIRE;
import static net.minecraft.tags.DamageTypeTags.IS_LIGHTNING;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class TenkaAshina {
    //新建nbt
    private static final ResourceLocation tenka_ichiken_tick = new ResourceLocation(MoreTetraTools.MODID, "tenka_ichiken_edict");
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            int effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, tenka_ichiken_Effect));
            int effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, chokuhi_kyoshin_joseishu_Effect));
            if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel > 0){
                effectLevel=effectLevel*5;
            }
            //获取工具nbt数值
            CompoundTag persistentData = mainHandItem.getOrCreateTag();
            //不存在就是0
            int tick =persistentData.getInt(String.valueOf(tenka_ichiken_tick));
            //是近战
            if(MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                if (effectLevel > 0 && tick > 0) {
                    int fire = mob.getRemainingFireTicks();
                    //净火
                    if(player.hasEffect(MTTEffectsRegister.JinkaShakushin.get()) && effectLevel2>0) {
                        //增伤
                        event.setAmount(event.getAmount() + mob.getMaxHealth() * (effectLevel / 100) * ((float) tick / 200) * (1+effectLevel2/100) );
                        //非最终boss
                        if(! isUltimateBossEntity(event.getEntity().getType()) ){
                            //充能
                            persistentData.putInt(String.valueOf(tenka_ichiken_tick), 0);
                        }
                        //点燃
                        if(fire<=200) {
                            mob.setRemainingFireTicks( Math.min(200,fire + 200) );
                        }
                    }else {
                        event.setAmount(event.getAmount() + mob.getMaxHealth() * (effectLevel / 100) * ((float) tick / 200));
                        persistentData.putInt(String.valueOf(tenka_ichiken_tick), 0);
                    }
                }
            }
        }
        //挨打
        if (event.getEntity()!=null) {
            LivingEntity player = event.getEntity();
            var mob = event.getEntity();
            int effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, tenka_ichiken_Effect));
            int effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, chokuhi_kyoshin_joseishu_Effect));
            if (effectLevel2 > 0 && player.hasEffect(MTTEffectsRegister.JinkaShakushin.get()) && event.getSource().is(IS_FIRE) ) {
                //取消事件
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity player = event.getEntity();
        ItemStack mainHandItem = player.getMainHandItem();
        int effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, tenka_ichiken_Effect));
        int effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, chokuhi_kyoshin_joseishu_Effect));
        if (effectLevel > 0) {
            //获取工具nbt数值
            CompoundTag persistentData = mainHandItem.getOrCreateTag();
            //不存在就是0
            int tick =persistentData.getInt(String.valueOf(tenka_ichiken_tick));
            //净火
            if(player.hasEffect(MTTEffectsRegister.JinkaShakushin.get()) && effectLevel2>0) {
                persistentData.putInt(String.valueOf(tenka_ichiken_tick), Math.min(100 * 2 * 2, tick + 2));
            }else{
                persistentData.putInt(String.valueOf(tenka_ichiken_tick), Math.min(100 * 2, tick + 1));
            }
        }
    }
    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof IModularItem item) {
            float effectLevel = item.getEffectLevel(itemStack, tenka_ichiken_Effect);
            float effectLevel2 = item.getEffectLevel(itemStack, chokuhi_kyoshin_joseishu_Effect);
            if (effectLevel > 0) {
                //获取工具nbt数值
                CompoundTag persistentData = itemStack.getOrCreateTag();
                //不存在就是0
                int tick = persistentData.getInt(String.valueOf(tenka_ichiken_tick))/2;
                if (tick > 100 ) {
                    event.getToolTip().add(Component.translatable("tooltip.more_tetra_tools.tenka_ichiken_tick", tick).withStyle(ChatFormatting.RED)); // 使用灰色
                }else{
                    event.getToolTip().add(Component.translatable("tooltip.more_tetra_tools.tenka_ichiken_tick", tick).withStyle(ChatFormatting.WHITE)); // 使用灰色
                }
            }
        }
    }
}