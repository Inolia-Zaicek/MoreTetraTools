package com.inolia_zaicek.more_tetra_tools.entity;


import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.inolia_zaicek.more_tetra_tools.MoreTetraTools.MODID;

public class MTTModEntities {
        public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
                DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID); // YourMod.MODID是你的modid

        public static final RegistryObject<EntityType<MTTMusketArrowEntity>> MY_MUSKET_ARROW =
                ENTITY_TYPES.register("musket_arrow", () -> EntityType.Builder.<MTTMusketArrowEntity>of(MTTMusketArrowEntity::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F) // 箭矢的碰撞箱大小
                        .clientTrackingRange(10) // 客户端跟踪范围
                        .updateInterval(10) // 更新间隔 (ticks)
                        .build(new ResourceLocation(MoreTetraTools.MODID, "musket_arrow").toString())); // 内部名称
    }
