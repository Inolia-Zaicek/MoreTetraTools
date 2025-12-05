package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class FlameKatana {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, avengerEffect));
                if (effectLevel > 0&& player.getHealth() <= (player.getMaxHealth()/2) ) {
                    float number = 1+effectLevel / 100;
                    event.setAmount(event.getAmount()*number);
            }
        }
        if (event.getEntity()!=null) {
            LivingEntity player = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, fireAndSteelEffect));
            if (effectLevel > 0 && player.getHealth() <= (player.getMaxHealth()/2) &&player.hasEffect(MTTEffectsRegister.FireAndSteel.get()) ) {
                float number = 1-effectLevel / 100;
                event.setAmount(event.getAmount()*number);
            }
        }
    }
    @SubscribeEvent
    public static void heal(LivingHealEvent event) {
        if(event.getEntity()!=null){
            LivingEntity player = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, fireAndSteelEffect);
                if (mainEffectLevel > 0) {
                    effectLevel +=  mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, fireAndSteelEffect);
                if (offEffectLevel > 0) {
                    effectLevel +=offEffectLevel;
                }
            }
            float heal =event.getAmount();
            float hp =player.getHealth();
            float mhp2 =player.getMaxHealth() / 2 ;
            //治疗血量+总血量大于最大hp
            if (effectLevel > 0&& (heal+hp>mhp2) &&player.hasEffect(MTTEffectsRegister.FireAndSteel.get()) ) {
                //超出多少
                float over = heal+hp-mhp2;
                //超出量小于0
                if(over<=0){
                    event.setAmount(0);
                }else {
                    //治疗量-超出量
                    event.setAmount(heal - over);
                }
            }
        }
    }
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        float effectLevel=0;
        if (mainHandItem.getItem() instanceof IModularItem item) {
            float mainEffectLevel = item.getEffectLevel(mainHandItem, fireAndSteelEffect);
            if (mainEffectLevel > 0) {
                effectLevel +=  mainEffectLevel;
            }
        }
        if (offhandItem.getItem() instanceof IModularItem item) {
            float offEffectLevel = item.getEffectLevel(offhandItem, fireAndSteelEffect);
            if (offEffectLevel > 0) {
                effectLevel += offEffectLevel;
            }
        }
        float hp =player.getHealth();
        float mhp =player.getMaxHealth();
        if (effectLevel > 0&&hp>(mhp/2)  &&player.hasEffect(MTTEffectsRegister.FireAndSteel.get()) ) {
            player.setHealth(mhp/2);
        }
    }
}