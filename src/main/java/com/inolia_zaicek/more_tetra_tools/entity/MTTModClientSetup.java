package com.inolia_zaicek.more_tetra_tools.entity;

// 例如：com.inolia_zaicek.zeroing_story.YourModClientSetup.java

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class MTTModClientSetup {
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            EntityRenderers.register(MTTModEntities.MY_MUSKET_ARROW.get(), MTTMusketArrowRenderer::new);
        });
    }
}
