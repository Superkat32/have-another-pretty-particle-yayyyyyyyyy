package net.superkat.happy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.superkat.happy.particle.defaults.AbstractColorfulParticle;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class JellyfishParticle extends AbstractColorfulParticle {
    public int bounces;
    public float maxScale;
    public int bounceTicks;
    public int maxBounceTicks;
    public int maxAnimAge;

    public JellyfishParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, JellyfishParticleEffect params, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ, spriteProvider);

        if (velX == 0 && velY == 0 && velZ == 0) {
            this.velocityX /= 2f;
            this.velocityY = 0.115f;
            this.velocityZ /= 2f;
        } else {
            this.velocityX = velX;
            this.velocityY = velY;
            this.velocityZ = velZ;
        }
        this.velocityMultiplier = 0.99f;
        this.maxAge = params.getMaxAge();
        this.bounces = params.getBounces();
        this.scale = params.getScale();
        this.maxScale = scale;

        this.maxBounceTicks = this.maxAge / this.bounces;
        this.maxAnimAge = this.maxBounceTicks + (this.maxAge / this.maxBounceTicks);

        Vector3f startColor = params.getStartColor();
        Vector3f endColor = params.getEndColor();
        boolean defaultStart = startColor == JellyfishParticleEffect.DEFAULT_START;
        boolean defaultEnd = endColor == JellyfishParticleEffect.DEFAULT_END;
        boolean defaultColors = defaultStart && defaultEnd;
        if (params.isRandomColor()) {
            Vector3f color = defaultColors ? randomColor() : randomColor(startColor, endColor);
            this.setColorFromColor(color);
        } else if (params.isTransitionRandomColor()) {
            Vector3f color1 = defaultColors ? randomColor() : randomColor(startColor, endColor);
            Vector3f color2 = defaultColors ? randomColor() : randomColor(startColor, endColor);
            startColor = color1;
            endColor = color2;
            this.setTransitionColors(startColor, endColor);
        } else {
            this.setTransitionColors(startColor, endColor);
        }

        this.setSpriteForAge(this.spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.bounces <= 0 || this.scale <= 0f) {
            this.markDead();
            return;
        }
        this.bounceTicks++;

        if (this.bounceTicks == this.maxBounceTicks) {
            this.velocityY += 0.05f;
            this.bounceTicks = 0;
            this.bounces--;
        } else if (this.bounceTicks == 7) {
            this.velocityY *= 0.96f;
        }

        if (this.bounces == 1) {
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
        if (this.bounces > 1) return 15728880; //emissive
        else {
            float delta = bounces == 1 ? (float) this.bounceTicks / this.maxBounceTicks : 1f;
            int blockLight = MathHelper.lerp(delta, 15, 3);
            int skyLight = MathHelper.lerp(delta, 15, 7);
            return LightmapTextureManager.pack(blockLight, skyLight);
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

