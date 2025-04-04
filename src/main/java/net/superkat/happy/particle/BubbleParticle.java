package net.superkat.happy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BubbleParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    public BubbleParticle(ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, BubbleParticleEffect params, SpriteProvider provider) {
        super(clientWorld, x, y, z, velX, velY, velZ);
        this.spriteProvider = provider;
        this.setInitialSprite(clientWorld.getRandom(), provider);

        this.maxAge = params.maxAge();
        if (params.maxAgeRandom() > 0) {
            this.maxAge += this.random.nextInt(params.maxAgeRandom());
        }

        if (params.scale() == 0f) {
            this.scale = (float) Math.abs(clientWorld.getRandom().nextTriangular(0, 1));
        } else {
            this.scale = params.scale();
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(spriteProvider);
    }

    public void setInitialSprite(Random clientRandom, SpriteProvider spriteProvider) {
        if (spriteProvider instanceof FabricSpriteProvider fab) {
            List<Sprite> sprites = fab.getSprites();
            if (sprites.isEmpty()) return;

            int spriteCount = fab.getSprites().size() - 1;
            int selected = clientRandom.nextBetweenExclusive(0, spriteCount);
            this.setSprite(sprites.get(selected));
        }
    }

    @Override
    public void setSpriteForAge(SpriteProvider spriteProvider) {
        if (!this.dead) {
            if (spriteProvider instanceof FabricSpriteProvider fab) {
                List<Sprite> sprites = fab.getSprites();
                if (sprites.isEmpty()) return;

                if (this.age >= this.maxAge - 2) {
                    this.setSprite(sprites.get(sprites.size() - 1));
                }
            }
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<BubbleParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(BubbleParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new BubbleParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters, this.spriteProvider);
        }
    }
}
