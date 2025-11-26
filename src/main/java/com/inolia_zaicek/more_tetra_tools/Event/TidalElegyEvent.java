package com.inolia_zaicek.more_tetra_tools.Event;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class TidalElegyEvent {
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        float effectLevel1 = 0;
        float effectLevel2 = 0;
        float effectLevel3 = 0;
        if (mainHandItem.getItem() instanceof IModularItem item) {
            float mainEffectLevel1 = item.getEffectLevel(mainHandItem, tidalElegyAttackEffect);
            if (mainEffectLevel1 > 0) {
                effectLevel1 += mainEffectLevel1;
            }
            float mainEffectLevel2 = item.getEffectLevel(mainHandItem, tidalElegyArmorEffect);
            if (mainEffectLevel2 > 0) {
                effectLevel2 += mainEffectLevel2;
            }
            float mainEffectLevel3 = item.getEffectLevel(mainHandItem, tidalElegyHealthEffect);
            if (mainEffectLevel3 > 0) {
                effectLevel3 += mainEffectLevel3;
            }
        }
        if (offhandItem.getItem() instanceof IModularItem item) {
            float offEffectLevel1 = item.getEffectLevel(offhandItem, tidalElegyAttackEffect);
            if (offEffectLevel1 > 0) {
                effectLevel1 += offEffectLevel1;
            }
            float offEffectLevel2 = item.getEffectLevel(offhandItem, tidalElegyArmorEffect);
            if (offEffectLevel2 > 0) {
                effectLevel2 += offEffectLevel2;
            }
            float offEffectLevel3 = item.getEffectLevel(offhandItem, tidalElegyHealthEffect);
            if (offEffectLevel3 > 0) {
                effectLevel3 += offEffectLevel3;
            }
        }
        if (effectLevel1<=0&&player.hasEffect(MTTEffectsRegister.TidalElegyAttack.get())) {
            player.removeEffect(MTTEffectsRegister.TidalElegyAttack.get());
        }
        if (effectLevel2<=0&&player.hasEffect(MTTEffectsRegister.TidalElegyArmor.get())) {
            player.removeEffect(MTTEffectsRegister.TidalElegyArmor.get());
        }
        if (effectLevel3<=0&&player.hasEffect(MTTEffectsRegister.TidalElegyHealth.get())) {
            player.removeEffect(MTTEffectsRegister.TidalElegyHealth.get());
        }
    }
}
