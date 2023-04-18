package krelox.morebows.mixin;

import krelox.morebows.item.CustomBowItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends LivingEntity {
    @Deprecated
    private AbstractClientPlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "getFieldOfViewModifier()F", at = @At("HEAD"), cancellable = true)
    private void getFieldOfViewModifier(CallbackInfoReturnable<Float> cir) {
        if (isUsingItem() && getUseItem().getItem() instanceof final CustomBowItem bow) {
            float customBow = getTicksUsingItem() / bow.bowType.drawDuration;
            if (customBow > 1.0F) customBow = 1.0F;
            else customBow *= customBow;
            cir.setReturnValue(1.0F - (customBow * 0.15F));
        }
    }
}
