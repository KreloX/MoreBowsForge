package krelox.morebows.entity;

import krelox.morebows.MoreBows;
import krelox.morebows.config.MoreBowsConfig;
import krelox.morebows.entity.projectile.CustomArrow;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArrowSpawner extends Entity {
    private Player player;
    private float shotVelocity;
    protected int age;
    private CustomArrow[] arrows = new CustomArrow[5];

    public ArrowSpawner(EntityType<?> type, Level level) {
        super(type, level);
        noPhysics = true;
        setInvisible(true);
    }

    public ArrowSpawner(Level level, Player player, float shotVelocity, Arrow arrow) {
        this(MoreBows.ARROW_SPAWNER.get(), level);
        this.player = player;
        setPos(this.player.getX(), this.player.getY(), this.player.getZ());
        setRot(this.player.getXRot(), this.player.getYRot());
        this.shotVelocity = shotVelocity;
        this.age = 0;
        Arrays.fill(arrows, arrow);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double p_19883_) {
        return false;
    }

    @Override
    public void tick() {
        age++;
        if (age > MoreBowsConfig.ENDER_BOW_DELAY.get()) {
            discard();
            return;
        }

        if (!level.isClientSide() && age == MoreBowsConfig.ENDER_BOW_DELAY.get()) {
            final int arrLength = arrows.length;

            for (int i = 0; i < arrLength; ++i) {
                final double arrYDisp;
                final double arrXDisp;
                final double arrZDisp;
                final float soundVolume;
                final float soundPitch;

                switch (i) {
                    case 0 -> {
                        arrYDisp = 1.0;
                        arrXDisp = -1.25;
                        arrZDisp = -1.25;
                        soundVolume = 1.0F;
                        soundPitch = (1.0F / ((random.nextFloat() * 0.4F) + 1.2F)) + (shotVelocity * 0.5F);
                    }
                    case 1 -> {
                        arrYDisp = 1.45;
                        arrXDisp = -2.25;
                        arrZDisp = -0.75;
                        soundVolume = 0.25F;
                        soundPitch = (1.0F / ((random.nextFloat() * 0.4F) + 1.0F)) + (shotVelocity * 0.3F);
                    }
                    case 2 -> {
                        arrYDisp = 2.0;
                        arrXDisp = 0.25;
                        arrZDisp = 2.5;
                        soundVolume = 1.0F;
                        soundPitch = (1.0F / ((random.nextFloat() * 0.4F) + 1.2F)) + (shotVelocity * 0.5F);
                    }
                    case 3 -> {
                        arrYDisp = 1.75;
                        arrXDisp = 1.75;
                        arrZDisp = 1.5;
                        soundVolume = 0.5F;
                        soundPitch = (1.0F / ((random.nextFloat() * 0.4F) + 1.0F)) + (shotVelocity * 0.4F);
                    }
                    case 4 -> {
                        arrYDisp = 1.25;
                        arrXDisp = 1.0;
                        arrZDisp = -1.0;
                        soundVolume = 0.5F;
                        soundPitch = (1.0F / ((random.nextFloat() * 0.4F) + 1.0F)) + (shotVelocity * 0.4F);
                    }
                    default -> {
                        arrYDisp = 0.0;
                        arrXDisp = 0.0;
                        arrZDisp = 0.0;
                        soundVolume = 0.5F;
                        soundPitch = (1.0F / ((random.nextFloat() * 0.4F) + 1.0F)) + (shotVelocity * 0.4F);
                    }
                }

                final @NotNull CustomArrow arrow = new CustomArrow(level, this.getX() + arrXDisp, this.getY() + arrYDisp + 1.0, this.getZ() + arrZDisp);
                arrow.setOwner(arrows[i].getOwner());
                arrow.setEffectsFromItem(arrows[i].getPickupItem());

                arrow.shootFromRotation(this, this.getYRot(), this.getXRot(), 0.0F, shotVelocity * 2.5F, 1.0F);
                level.addFreshEntity(arrow);
                level.playSound(null, arrow.getX(), arrow.getY(), arrow.getZ(), (i & 1) != 0 ? SoundEvents.ENDERMAN_TELEPORT : SoundEvents.ARROW_SHOOT,
                        SoundSource.PLAYERS, soundVolume, soundPitch);
            }
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
    }
}
