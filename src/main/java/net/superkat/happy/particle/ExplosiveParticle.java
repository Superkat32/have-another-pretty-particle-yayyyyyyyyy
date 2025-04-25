package net.superkat.happy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.superkat.happy.compat.ExplosiveEnhancementCompat;
import org.jetbrains.annotations.Nullable;

public class ExplosiveParticle extends NoRenderParticle {
    public ExplosiveParticle(ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, ExplosiveParticleEffect params) {
        super(clientWorld, x, y, z, velX, velY, velZ);

        if(FabricLoader.getInstance().isModLoaded("explosiveenhancement")) {
            ExplosiveEnhancementCompat.spawnExplosiveParticle(clientWorld, x, y, z, velX, velY, velZ, params);
        } else {
            this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, x, y, z, velX, velY, velZ);
        }
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
