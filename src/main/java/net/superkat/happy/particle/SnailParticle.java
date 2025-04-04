package net.superkat.happy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.superkat.happy.HSVColor;
import net.superkat.happy.particle.defaults.AbstractColorfulParticle;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class SnailParticle extends AbstractColorfulParticle {
    private static final double MAX_SQUARED_COLLISION_CHECK_DISTANCE = MathHelper.square(100.0);
    private final SpriteProvider spriteProvider;

    public boolean landed = false;
    public int landedTicks = 0;
    public float speed = 0;

    public boolean shrinking = false;
    public boolean flipped = false;
    public int animTicks = 0;
    public int maxAnimTicks = 7;
    public int frame = 0;

    public SnailParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SnailParticleEffect params, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, spriteProvider);
        this.spriteProvider = spriteProvider;

        this.maxAge = params.getMaxAge();

        if(params.getScale() == 0) {
            this.scale = 0.15f + Math.abs(this.random.nextFloat() / 4);
        } else {
            this.scale = params.getScale();
        }

        this.angle = this.random.nextFloat() * 3.14f * 2;
        this.prevAngle = this.angle;

        this.flipped = this.random.nextInt(3) == 0;

        if (velY == 0) {
            this.velocityY = 0.5f;
        }

        this.speed = params.getSpeed();
        this.gravityStrength = params.getGravityStrength();
        this.collidesWithWorld = true;

        this.minColor = params.getMinColor();
        this.maxColor = params.getMaxColor();
        boolean defaultStart = this.minColor.equals(SnailParticleEffect.DEFAULT_MIN_COLOR);
        boolean defaultEnd = this.maxColor.equals(SnailParticleEffect.DEFAULT_MAX_COLOR);
        boolean defaultColors = defaultStart && defaultEnd;

        Vector3f color = defaultColors ? randomColor() : randomColor(minColor, maxColor);
        this.setColorFromColor(color);

        this.setSpriteForAge(this.spriteProvider);
    }

    @Override
    protected Vector3f randomColor() {
        float minHue = 0.4f;
        float maxHue = 0.6f;

        float hue = MathHelper.nextFloat(this.random, 0f, 1f);
        if((hue < minHue || hue > maxHue) && this.random.nextBetween(1, 5) != 1) {
            hue = MathHelper.nextFloat(this.random, minHue, maxHue);
        }

        float sat = MathHelper.nextFloat(this.random, 0.7f, 1f);
        float light = MathHelper.nextFloat(this.random, 0.8f, 1f);
        HSVColor color = new HSVColor(hue, sat, light);
        return HSVColor.toRgb(color);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.prevAngle = this.angle;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.velocityY = this.velocityY - 0.04 * this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            if (this.ascending && this.y == this.prevPosY) {
                this.velocityX *= 1.1;
                this.velocityZ *= 1.1;
            }

            this.velocityX = this.velocityX * this.velocityMultiplier;
            this.velocityY = this.velocityY * this.velocityMultiplier;
            this.velocityZ = this.velocityZ * this.velocityMultiplier;
        }

        if(this.shrinking) {
            this.scale -= 0.01f;
            if(this.scale <= 0f) this.markDead();
        }

        if(!this.onGround && !this.landed) {
            float angleAdded = (float) (0.3f + Math.abs(this.velocityY * 2));
            this.angle += this.flipped ? angleAdded : -angleAdded;
        } else {
            // This didn't do what I wanted it to, I was hoping it would lerp it for me, but I guess that works too
            this.angle = MathHelper.angleBetween(this.prevAngle, this.angle);
            if(landed) {
                landedTicks++;
                if(landedTicks >= 20) {
                    boolean noVelX = Math.abs(this.velocityX) < 0.005f;
                    boolean noVelZ = Math.abs(this.velocityZ) < 0.005f;
                    if(noVelX && noVelZ) this.shrinking = true;
                }
            } else {
                this.velocityY = 0;

                if(this.speed == 0) {
                    this.shrinking = true;
                    this.velocityX = 0;
                    this.velocityZ = 0;
                } else {
                    this.velocityX = this.speed * this.random.nextGaussian();
                    this.velocityZ = this.speed * this.random.nextGaussian();
                }

                this.onGround = false;
                this.landed = true;
            }
        }
        this.setSpriteForAge(this.spriteProvider);
    }

    @Override
    public void setSpriteForAge(SpriteProvider spriteProvider) {
        if(this.landed && spriteProvider instanceof  FabricSpriteProvider fab) {
            List<Sprite> sprites = fab.getSprites();
            if (sprites.isEmpty()) return;
            this.animTicks++;

            if(this.animTicks > this.maxAnimTicks) {
                this.frame = (this.frame + 1) % (fab.getSprites().size());
                this.animTicks = 0;
            }

            this.setSprite(sprites.get(this.frame));
        } else {
            super.setSpriteForAge(spriteProvider);
        }
    }

    @Override
    public void move(double dx, double dy, double dz) {
        double initVelX = dx;
        double initVelY = dy;
        double initVelZ = dz;
        if (this.collidesWithWorld && (dx != 0.0 || dy != 0.0 || dz != 0.0) && dx * dx + dy * dy + dz * dz < MAX_SQUARED_COLLISION_CHECK_DISTANCE) {
            Vec3d vec3d = Entity.adjustMovementForCollisions(null, new Vec3d(dx, dy, dz), this.getBoundingBox(), this.world, List.of());
            dx = vec3d.x;
            dy = vec3d.y;
            dz = vec3d.z;
        }

        if (dx != 0.0 || dy != 0.0 || dz != 0.0) {
            this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
            this.repositionFromBoundingBox();
        }

        this.onGround = initVelY != dy && initVelY < 0.0;
    }

    // There surely won't be *any* consequences of my actions here, yeah?
    @Override
    protected float getMinU() {
        if(this.flipped) return super.getMaxU();
        return super.getMinU();
    }

    @Override
    protected float getMaxU() {
        if(this.flipped) return super.getMinU();
        return super.getMaxU();
    }


    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SnailParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SnailParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SnailParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, this.spriteProvider);
        }
    }


}
