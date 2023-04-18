package krelox.morebows.item;

import krelox.morebows.config.MoreBowsConfig;
import krelox.morebows.entity.ArrowSpawner;
import krelox.morebows.entity.projectile.CustomArrow;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class CustomBowItem extends BowItem {
    public BowType bowType;

    public CustomBowItem(BowType bowType, Properties properties) {
        super(properties);
        this.bowType = bowType;
    }

    @Override
    public void releaseUsing(ItemStack arrowStack, Level level, LivingEntity shooter, int power) {
        if (shooter instanceof Player player) {
            int arrowCount = this.bowType != BowType.MULTI ? 1 : RandomSource.create().nextIntBetweenInclusive(2, 3);
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, arrowStack) > 0;
            ItemStack itemstack = player.getProjectile(arrowStack);

            int i = this.getUseDuration(arrowStack) - power;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(arrowStack, level, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i, this.bowType);
                if (!((double) f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem &&
                            ((ArrowItem) itemstack.getItem()).isInfinite(itemstack, arrowStack, player));
                    if (!level.isClientSide) {
                        CustomArrow[] abstractarrow = new CustomArrow[] {createArrow(level, itemstack, player), createArrow(level, itemstack, player), createArrow(level, itemstack, player)};
                        for (int l = 0; l < arrowCount; l++) {
                            abstractarrow[l].setPickupItem(abstractarrow[l].getPickupItem());
                            switch (l) {
                                case 0 -> {
                                    abstractarrow[l].shootFromRotation(player, player.getXRot(), player.getYRot(),
                                            0.0F, f * 3.0F, 1.0F);
                                }
                                case 1 -> {
                                    abstractarrow[l].shootFromRotation(player, player.getXRot(), player.getYRot() - (float) Math.PI / 2,
                                            0.0F, f * 3.0F, 1.0F);
                                }
                                case 2 -> {
                                    abstractarrow[l].shootFromRotation(player, player.getXRot(), player.getYRot() + (float) Math.PI / 2,
                                            0.0F, f * 3.0F, 1.0F);
                                }
                            }

                            if (this.bowType == BowType.FROST) {
                                abstractarrow[l].addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 6));
                            }

                            if (f == 1.0F) {
                                abstractarrow[l].setCritArrow(true);
                            }

                            abstractarrow[l].setBaseDamage(abstractarrow[l].getBaseDamage() * this.bowType.damageMultiplier);

                            int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, arrowStack);
                            if (j > 0) {
                                abstractarrow[l].setBaseDamage(abstractarrow[l].getBaseDamage() + (double) j * 0.5D + 0.5D);
                            }

                            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, arrowStack);
                            if (k > 0) {
                                abstractarrow[l].setKnockback(k);
                            }

                            if (this.bowType == BowType.FLAME || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, arrowStack) > 0) {
                                abstractarrow[l].setSecondsOnFire(100);
                            }

                            arrowStack.hurtAndBreak(1, player, (p_276007_) -> {
                                p_276007_.broadcastBreakEvent(player.getUsedItemHand());
                            });

                            if (l > 0 || flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                                abstractarrow[l].pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }

                            if (this.bowType == BowType.ENDER) {
                                level.addFreshEntity(new ArrowSpawner(level, player, f, abstractarrow[l]));
                                abstractarrow[l].setEnder(true);
                            }
                            level.addFreshEntity(abstractarrow[l]);
                        }
                    }

                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    private static CustomArrow createArrow(Level level, ItemStack stack, LivingEntity entity) {
        CustomArrow arrow = new CustomArrow(level, entity);
        arrow.setEffectsFromItem(stack);
        return arrow;
    }

    public static float getPowerForTime(int time, BowType bowType) {
        float f = (float) time / bowType.drawDuration;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    // I tried making bow stats configurable but FML loads config files later than desired
    public enum BowType {
        STONE(1.15, 20.0F, 484),
        IRON(1.5, 17.0F, 550),
        GOLD(2.5, 6.0F, 68),
        DIAMOND(2.25, 6.0F, 1016),
        ENDER(1.0, 22.0F, 215),
        FLAME(2.0, 15.0F, 576),
        FROST(1.0, 26.0F, 550),
        MULTI(1.0, 13.0F, 550);

        public double damageMultiplier;
        public float drawDuration;
        public int durability;

        BowType(double damageMultiplier, float drawDuration, int durability) {
            this.damageMultiplier = damageMultiplier;
            this.drawDuration = drawDuration;
            this.durability = durability;
        }
    }
}
