package krelox.morebows.client.render;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class EmptyEntityRenderer extends EntityRenderer {
    public EmptyEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public boolean shouldRender(Entity p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(Entity p_114482_) {
        return null;
    }
}
