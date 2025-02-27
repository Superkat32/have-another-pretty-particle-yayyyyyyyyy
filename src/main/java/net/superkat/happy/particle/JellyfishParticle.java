package net.superkat.happy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class JellyfishParticle extends AbstractColorfulParticle {
    public int bounces;
    public float maxScale;
    public int bounceTicks;
    public int maxBounceTicks;
    public int maxAnimAge;

    public JellyfishParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, JellyfishParticleEffect parameters, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, spriteProvider);

        this.velocityX /= 2f;
        this.velocityY = 0.115f;
        this.velocityZ /= 2f;
        this.velocityMultiplier = 0.99f;
        this.maxAge = parameters.getMaxAge();
        this.bounces = parameters.getBounces();
        this.scale = parameters.getScale();
        this.maxScale = scale;

        this.maxBounceTicks = this.maxAge / this.bounces;
        this.maxAnimAge = this.maxBounceTicks + (this.maxAge / this.maxBounceTicks);

        if(parameters.isRandomColors()) {
            int rgbIncrease = random.nextBetween(1, 3);
            int red = rgbIncrease == 1 ? random.nextBetween(150, 255) : 255;
            int green = rgbIncrease == 2 ? random.nextBetween(150, 255) : 255;
            int blue = rgbIncrease == 3 ? random.nextBetween(150, 255) : 255;
            this.setColorFromColor(new Color(red, green, blue));
        } else {
            this.setTransitionColors(parameters.getStartColor(), parameters.getEndColor());
        }

        this.setSpriteForAge(this.spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.bounces <= 0 || this.scale <= 0f) {
            this.markDead();
            return;
        }
        this.bounceTicks++;

        if(this.bounceTicks == this.maxBounceTicks) {
            this.velocityY += 0.05f;
            this.bounceTicks = 0;
            this.bounces--;
        } else if(this.bounceTicks == 7) {
            this.velocityY *= 0.96f;
        }

        if(this.bounces == 1) {
            this.scale = MathHelper.lerp((float) this.bounceTicks / this.maxBounceTicks, this.maxScale, 0f);
        }
        this.setSpriteForAge(this.spriteProvider);
    }

    @Override
    public void setSpriteForAge(SpriteProvider spriteProvider) {
        if (!this.dead) {
            int extra = this.maxAnimAge / this.maxBounceTicks;
            this.setSprite(spriteProvider.getSprite(bounceTicks, maxBounceTicks + extra));
        }
    }

    @Override
    public int getBrightness(float tint) {
        if(this.bounces > 1) return 15728880; //emissive
        else {
            float delta = bounces == 1 ? (float) this.bounceTicks / this.maxBounceTicks : 1f;
            int blockLight = MathHelper.lerp(delta, 15, 3);
            int skyLight = MathHelper.lerp(delta, 15, 7);
            return LightmapTextureManager.pack(blockLight, skyLight);
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
            return new JellyfishParticle(world, x, y, z, velocityX, velocityY, velocityZ, new JellyfishParticleEffect(), this.spriteProvider);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<JellyfishParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(JellyfishParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new JellyfishParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, this.spriteProvider);
        }
    }
}

