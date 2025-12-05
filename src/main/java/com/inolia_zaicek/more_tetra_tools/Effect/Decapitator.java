package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
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

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class Decapitator {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        //挨打--满层减伤
        if (event.getEntity()!=null) {
            LivingEntity player = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, waxingMoonEffect));
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, halfMoonEffect));
            if(effectLevel>0&&player.hasEffect(MTTEffectsRegister.WaxingMoon.get())){
                int buffLevel = player.getEffect(MTTEffectsRegister.WaxingMoon.get()).getAmplifier();
                if(buffLevel>=9){
                    event.setAmount(event.getAmount()*0.75f);
                }
            }
        }
        //攻击额外附伤
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, waxingMoonEffect));
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, halfMoonEffect));
            if( effectLevel2>0 && player.hasEffect(MTTEffectsRegister.HalfMoon.get()) && MTTDamageSourceHelper.isMeleeAttack(event.getSource()) ){
                float number = effectLevel2 / 100;
                var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKAMAGE, player);
                
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                mob.invulnerableTime = 0;
                mob.hurt(DamageType, event.getAmount() * number);
            }
        }
    }

    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity player = event.getEntity();
        float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, waxingMoonEffect));
        float hp = player.getHealth();
        float mhp = player.getMaxHealth();
        float dhp = 100*hp/mhp;
        //算法：将100-effect等级（什么时候满额）/10，然后100-结果与dhp比较
        if (effectLevel > 0) {
            //分成10份
            float effectHP=(100-effectLevel)/10;
            //例如当前98hp>[(100-{(100-70)/10}*n)]=[100-(30/10)]=[100-3]=97，也就是每降低3%生命值提升1级（最高降低30%，也就是70%满额）
            if( dhp>=(100-effectHP*2) ){
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.WaxingMoon.get(), 200, 0));
            };
            if( dhp>=(100-effectHP*3) && dhp<(100-effectHP*2) ){
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.WaxingMoon.get(), 200, 1));
            };
            if( dhp>=(100-effectHP*4) && dhp<(100-effectHP*3) ){
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.WaxingMoon.get(), 200, 2));
            };
            if( dhp>=(100-effectHP*5) && dhp<(100-effectHP*4) ){
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.WaxingMoon.get(), 200, 3));
            };
            if( dhp>=(100-effectHP*6) && dhp<(100-effectHP*5) ){
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.WaxingMoon.get(), 200, 4));
            };
            if( dhp>=(100-effectHP*7) && dhp<(100-effectHP*6) ){
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.WaxingMoon.get(), 200, 5));
            };
            if( dhp>=(100-effectHP*8) && dhp<(100-effectHP*7) ){
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.WaxingMoon.get(), 200, 6));
            };
            if( dhp>=(100-effectHP*9) && dhp<(100-effectHP*8) ){
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.WaxingMoon.get(), 200, 7));
            };
            if( dhp>(100-effectHP*10) && dhp<(100-effectHP*9) ){
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.WaxingMoon.get(), 200, 8));
            };
            if( dhp<=(100-effectHP*10) ){
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.WaxingMoon.get(), 200, 9));
            };
        }else{
            player.removeEffect(MTTEffectsRegister.WaxingMoon.get());
        }
    }
}
