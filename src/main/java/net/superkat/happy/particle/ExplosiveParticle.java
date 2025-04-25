package net.superkat.happy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.superkat.explosiveenhancement.api.ExplosiveApi;
import org.jetbrains.annotations.Nullable;

public class ExplosiveParticle extends NoRenderParticle {
    public ExplosiveParticle(ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, ExplosiveParticleEffect params) {
        super(clientWorld, x, y, z, velX, velY, velZ);

        float scale = params.getScale();
        ExplosiveApi.spawnParticles(clientWorld, x, y, z, scale);
    }

    @Environment(EnvType.CLIENT)
    public static class SimpleFactory implements ParticleFactory<ExplosiveParticleEffect> {
        private final SpriteProvider spriteProvider;

        public SimpleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(ExplosiveParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ExplosiveParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters);
        }
    }
}
