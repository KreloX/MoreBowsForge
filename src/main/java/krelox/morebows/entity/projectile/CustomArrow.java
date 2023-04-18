package krelox.morebows.entity.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CustomArrow extends Arrow {
    private ItemStack pickupArrow;
    private boolean isEnder = false;
    private LivingEntity owner;

    public CustomArrow(Level level, LivingEntity entity) {
        super(level, entity);
        setOwner(entity);
    }

    public CustomArrow(Level level, double p_36862_, double p_36863_, double p_36864_) {
        super(level, p_36862_, p_36863_, p_36864_);
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        return owner;
    }

    public void setEnder(boolean ender) {
        isEnder = ender;
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return !this.isEnder && this.owner != entity && super.canHitEntity(entity);
    }

    public void setPickupItem(ItemStack pickupArrow) {
        this.pickupArrow = pickupArrow;
    }

    @Override
    public ItemStack getPickupItem() {
        return this.pickupArrow == null ? super.getPickupItem() : this.pickupArrow;
    }
}
