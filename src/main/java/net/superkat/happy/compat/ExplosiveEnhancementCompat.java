package net.superkat.happy.compat;

import net.minecraft.client.world.ClientWorld;
import net.superkat.explosiveenhancement.api.ExplosiveApi;
import net.superkat.happy.particle.ExplosiveParticleEffect;

public class ExplosiveEnhancementCompat {

    public static void spawnExplosiveParticle(ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ, ExplosiveParticleEffect params) {
        float scale = params.getScale();
        ExplosiveApi.spawnParticles(clientWorld, x, y, z, scale);
    }

}
