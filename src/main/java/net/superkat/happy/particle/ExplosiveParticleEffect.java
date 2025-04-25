package net.superkat.happy.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleType;
import net.superkat.happy.HappyParticles;
import net.superkat.happy.particle.defaults.AbstractScalableParticleEffect;

public class ExplosiveParticleEffect extends AbstractScalableParticleEffect {
    public static final float DEFAULT_SCALE = 4f;

    public static final MapCodec<ExplosiveParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    SCALE_CODEC.optionalFieldOf("scale", DEFAULT_SCALE).forGetter(effect -> effect.scale)
            ).apply(instance, ExplosiveParticleEffect::new)
    );

    public static final PacketCodec<RegistryByteBuf, ExplosiveParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, effect -> effect.scale,
            ExplosiveParticleEffect::new
    );

    public ExplosiveParticleEffect(float scale) {
        super(scale);
    }

    @Override
    public ParticleType<?> getType() {
        return HappyParticles.EXPLOSIVE_PARTICLE;
    }
}
