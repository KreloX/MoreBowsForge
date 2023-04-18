package krelox.morebows.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import krelox.morebows.item.CustomBowItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @ModifyConstant(method = "renderArmWithItem", constant = @Constant(floatValue = 20.0f))
    private float getModifiedDrawDuration(float drawDuration, AbstractClientPlayer player, float tickDelta, float pitch,
                                          InteractionHand hand, float swingProgress, ItemStack stack, float equipProgress,
                                          PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        if (stack.getItem() instanceof final CustomBowItem bow) {
            return bow.bowType.drawDuration;
        }
        return drawDuration;
    }
}
