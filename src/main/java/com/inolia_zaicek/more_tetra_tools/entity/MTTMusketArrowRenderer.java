package com.inolia_zaicek.more_tetra_tools.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MTTMusketArrowRenderer extends EntityRenderer<MTTMusketArrowEntity> {
    // 你不需要任何纹理，但EntityRenderer要求提供一个
    // 通常你会用一个占位符或者null，但如果你的渲染方式不需要，可以忽略
    // private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/arrow.png"); // 示例，实际不需要

    public MTTMusketArrowRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.shadowRadius = 0.15F; // 设置阴影大小，可以调整或设为0
    }

    @Override
    public void render(MTTMusketArrowEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // 像普通箭矢一样，将实体旋转到正确的方向
        // 1. 应用俯仰角 (Pitch): 围绕 X 轴旋转
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        // 2. 应用偏航角 (Yaw): 围绕 Y 轴旋转
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) ));

        // 移动到箭矢的中心，以便于绘制
        poseStack.translate(0.0, 0.0, -0.2); // 调整位置以使线看起来像从玩家射出

        // 获取顶点消费者，用于绘制
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.solid());

        // 绘制一个细长的黑色立方体 (模拟线)
        float lineWidth = 0.02F; // 线的宽度
        float lineLength = 0.02F; // 线的长度 (箭矢默认长度大约为1.0)

        // 定义颜色 (R, G, B, A) - 黑色
        int r = 0;
        int g = 0;
        int b = 0;
        int a = 255; // 完全不透明

        int light = packedLight; // 使用传入的光照

        // Front face (facing positive Z) - the "tip"
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2, -lineWidth/2, lineLength).color(r, g, b, a).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2, -lineWidth/2, lineLength).color(r, g, b, a).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2,  lineWidth/2, lineLength).color(r, g, b, a).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2,  lineWidth/2, lineLength).color(r, g, b, a).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 0.0F, 1.0F).endVertex();

        // Back face (facing negative Z) - the "tail"
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2,  lineWidth/2, 0.0F).color(r, g, b, a).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 0.0F, -1.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2,  lineWidth/2, 0.0F).color(r, g, b, a).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 0.0F, -1.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2, -lineWidth/2, 0.0F).color(r, g, b, a).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 0.0F, -1.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2, -lineWidth/2, 0.0F).color(r, g, b, a).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 0.0F, -1.0F).endVertex();

        // Right face (facing positive X)
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2,  lineWidth/2, lineLength).color(r, g, b, a).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2, -lineWidth/2, lineLength).color(r, g, b, a).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2, -lineWidth/2, 0.0F).color(r, g, b, a).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2,  lineWidth/2, 0.0F).color(r, g, b, a).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 1.0F, 0.0F, 0.0F).endVertex();

        // Left face (facing negative X)
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2, -lineWidth/2, lineLength).color(r, g, b, a).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), -1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2,  lineWidth/2, lineLength).color(r, g, b, a).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), -1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2,  lineWidth/2, 0.0F).color(r, g, b, a).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), -1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2, -lineWidth/2, 0.0F).color(r, g, b, a).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), -1.0F, 0.0F, 0.0F).endVertex();

        // Top face (facing positive Y)
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2,  lineWidth/2, lineLength).color(r, g, b, a).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2,  lineWidth/2, lineLength).color(r, g, b, a).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2,  lineWidth/2, 0.0F).color(r, g, b, a).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2,  lineWidth/2, 0.0F).color(r, g, b, a).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

        // Bottom face (facing negative Y)
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2, -lineWidth/2, 0.0F).color(r, g, b, a).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2, -lineWidth/2, 0.0F).color(r, g, b, a).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(), -lineWidth/2, -lineWidth/2, lineLength).color(r, g, b, a).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(poseStack.last().pose(),  lineWidth/2, -lineWidth/2, lineLength).color(r, g, b, a).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(poseStack.last().normal(), 0.0F, -1.0F, 0.0F).endVertex();

        poseStack.popPose(); // 恢复之前的姿态矩阵
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight); // 调用父类渲染，如果需要渲染实体阴影等
    }

    @Override
    public ResourceLocation getTextureLocation(MTTMusketArrowEntity pEntity) {
        // 随意给一个，因为我们不使用纹理
        return new ResourceLocation("minecraft", "textures/entity/arrow/arrow.png");
    }
}