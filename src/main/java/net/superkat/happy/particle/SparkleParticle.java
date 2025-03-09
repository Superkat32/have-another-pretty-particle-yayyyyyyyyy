package net.superkat.happy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class SparkleParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    public float maxScale;
    public float angleIncrease;

    public SparkleParticle(ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider provider) {
        super(clientWorld, x, y, z, velX, velY, velZ);
        this.spriteProvider = provider;
        this.setSpriteForAge(spriteProvider);

        this.velocityX /= 2f;
        this.velocityY /= 2f;
        this.velocityZ /= 2f;

        this.maxAge = 60;
        this.scale = 0f;
        this.maxScale = 0.35f;
        this.angleIncrease = -0.07f;
        this.angle = 0;
        this.prevAngle = angle;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(spriteProvider);
        this.prevAngle = angle;

        int stages = 5;
        int stageMaxAge = this.maxAge / stages;
        int stage = this.age / (stageMaxAge + 1);
        float delta = (float) (this.age % (stageMaxAge + 1)) / stageMaxAge;
        if(stage == stages - 1) delta = ((float) (this.age % (stageMaxAge + 1)) / ((this.maxAge + 1) % (stageMaxAge + 1)));

        float sizeDelta = 1f;
        if(stage == 0) {
            sizeDelta = easeOutBack(delta);
        } else if(stage == 3) {
            if(delta <= 0.5f) {
                sizeDelta = 1f - (delta * 2f);
            } else {
                sizeDelta = (delta - 0.5f) * 2f;
                angleIncrease = -0.05f;
            }
        } else if(stage == 4) {
            sizeDelta = 1f - delta;
        }

        this.scale = this.maxScale * sizeDelta;
        this.angle = this.prevAngle + angleIncrease;
    }

    private float easeOutBack(float x) {
        float c1 = 1.70158f;
        float c3 = c1 + 1f;

        return (float) (1 + c3 * Math.pow(x - 1, 3) + c1 * Math.pow(x - 1, 2));
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class SimpleFactory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public SimpleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SparkleParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}
