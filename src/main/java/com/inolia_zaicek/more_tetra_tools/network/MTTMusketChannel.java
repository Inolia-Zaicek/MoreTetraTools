package com.inolia_zaicek.more_tetra_tools.network;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.network.Packet.MTTMusketPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class MTTMusketChannel {
    private static final String PROTOCOL_VERSION = "1";
    // 将 CHANNEL 的初始化延迟到 FMLCommonSetupEvent 中
    public static SimpleChannel CHANNEL; // 声明为 null，稍后初始化

    public static void init(FMLCommonSetupEvent event) {
        CHANNEL = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(MoreTetraTools.MODID, "more_tetra_tools_a"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );

        int packetID = 0;
        CHANNEL.registerMessage(
                packetID++,
                MTTMusketPacket.class,
                MTTMusketPacket::encode,
                MTTMusketPacket::decode,
                MTTMusketPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }
}