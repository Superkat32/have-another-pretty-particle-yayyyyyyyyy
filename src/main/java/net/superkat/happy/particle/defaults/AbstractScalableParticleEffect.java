package net.superkat.happy.particle.defaults;

import com.mojang.serialization.Codec;
import net.minecraft.particle.ParticleEffect;

public abstract class AbstractScalableParticleEffect implements ParticleEffect {

    protected static final Codec<Float> SCALE_CODEC = Codec.FLOAT;

    protected final float scale;

    public AbstractScalableParticleEffect(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }
}
