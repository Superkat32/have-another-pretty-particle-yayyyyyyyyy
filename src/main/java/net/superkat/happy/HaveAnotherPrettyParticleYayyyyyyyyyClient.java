package net.superkat.happy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.superkat.happy.particle.JellyfishParticle;

public class HaveAnotherPrettyParticleYayyyyyyyyyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(HappyParticles.JELLYFISH_PARTICLE, JellyfishParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(HappyParticles.JELLYFISH_PARTICLE_SIMPLE, JellyfishParticle.SimpleFactory::new);
    }
}
