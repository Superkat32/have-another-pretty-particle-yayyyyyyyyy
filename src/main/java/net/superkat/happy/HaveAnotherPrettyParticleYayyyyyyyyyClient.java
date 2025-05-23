package net.superkat.happy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.superkat.happy.particle.BubbleParticle;
import net.superkat.happy.particle.CloudParticle;
import net.superkat.happy.particle.ExplosiveParticle;
import net.superkat.happy.particle.JellyfishParticle;
import net.superkat.happy.particle.SnailParticle;
import net.superkat.happy.particle.SparkleParticle;

public class HaveAnotherPrettyParticleYayyyyyyyyyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(HappyParticles.PINK_SPARKLE, SparkleParticle.SimpleFactory::new);

        ParticleFactoryRegistry.getInstance().register(HappyParticles.JELLYFISH_PARTICLE, JellyfishParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(HappyParticles.CLOUD_PARTICLE, CloudParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(HappyParticles.BUBBLE_PARTICLE, BubbleParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(HappyParticles.SNAIL_PARTICLE, SnailParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(HappyParticles.EXPLOSIVE_PARTICLE, ExplosiveParticle.SimpleFactory::new);
    }
}
