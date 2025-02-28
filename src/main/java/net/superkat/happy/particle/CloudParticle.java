package net.superkat.happy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class CloudParticle extends SpriteBillboardParticle {
    private static final double MAX_SQUARED_COLLISION_CHECK_DISTANCE = MathHelper.square(100.0);

    private boolean stopped = false;
    private boolean fadingOut = false;
    public int hitsUntilFade = 1;
    public int ticksSinceHit = 0;
    public float fadeAmount = 0.1f;
    public Vector3f velocityAfterHit = new Vector3f();

    public CloudParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        this(world, x, y, z, velX, velY, velZ,
                new CloudParticleEffect(3f, 280, 50, 1, 0.1f, new Vector3f()),
                spriteProvider);
    }

    public CloudParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, CloudParticleEffect params, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ);
        this.setSprite(spriteProvider);
        this.scale(params.getScale());
        this.setBoundingBoxSpacing(0.25F, 0.25F);
        this.alpha = 0.9f;
        this.maxAge = params.getMaxAge();
        if(params.getMaxAgeRandom() > 0) {
            this.maxAge += this.random.nextInt(params.getMaxAgeRandom());
        }

        this.hitsUntilFade = params.getHitsUntilFade();
        this.fadeAmount = params.getFadeAmount();
        this.velocityAfterHit = params.getVelocityAfterHit();

        this.gravityStrength = 3.0E-6F;
        this.velocityX = velX;
        this.velocityY = velY + this.random.nextFloat() / 500.0F;
        this.velocityZ = velZ;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;

        ticksSinceHit++;

        if(fadingOut) {
            this.alpha -= fadeAmount;
            if(this.alpha <= 0f) {
                markDead();
                return;
            }
        }

        if (this.age++ < this.maxAge && !(this.alpha <= 0.0F)) {
            this.velocityX = this.velocityX + this.random.nextFloat() / 5000.0F * (this.random.nextBoolean() ? 1 : -1);
            this.velocityZ = this.velocityZ + this.random.nextFloat() / 5000.0F * (this.random.nextBoolean() ? 1 : -1);
            this.velocityY = this.velocityY - this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);

            if(fadingOut) return;
            if (this.age >= this.maxAge - 60 && this.alpha > 0.01F) {
                this.alpha -= 0.015F;
            }
        } else {
            this.markDead();
        }
    }

    @Override
    public void move(double dx, double dy, double dz) {
        //do everything here instead of in super to reduce collision call by one :skull:
//        if (!this.stopped || !fadingOut) {
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

            if (Math.abs(initVelY) >= 1.0E-5F && Math.abs(dy) < 1.0E-5F) {
                this.stopped = true;
            }

            this.onGround = initVelY != dy && initVelY < 0.0;
            this.stopped = initVelX != dx || initVelZ != dz;
            if(stopped && ticksSinceHit >= 10) {
                ticksSinceHit = 0;
                hitsUntilFade--;
                this.velocityX += velocityAfterHit.x;
                this.velocityY += velocityAfterHit.y;
                this.velocityZ += velocityAfterHit.z;
                if(hitsUntilFade <= 0) {
                    fadingOut = true;
                }
            };

//            if (initVelX != dx) {
//                this.velocityX = 0.0;
//            }
//
//            if (initVelZ != dz) {
//                this.velocityZ = 0.0;
//            }
//        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<CloudParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(CloudParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new CloudParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, this.spriteProvider);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class SimpleFactory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public SimpleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new CloudParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}
