package net.superkat.happy.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.particle.ParticleEffect;

public abstract class AbstractScalableParticleEffect implements ParticleEffect {

    //stolen from AbstractDustParticleEffect
    protected static final Codec<Float> SCALE_CODEC = Codec.FLOAT
            .validate(scale -> scale >= 0.01F && scale <= 4.0F ? DataResult.success(scale) : DataResult.error(() -> "Value must be within range [0.01;4.0]: " + scale));

    protected final float scale;

    public AbstractScalableParticleEffect(float scale) {
        this.scale = scale <= 0 ? 0.01f : scale;
    }

    public float getScale() {
        return scale;
    }
}
