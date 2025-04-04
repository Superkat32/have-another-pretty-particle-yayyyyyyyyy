package net.superkat.happy.particle.defaults;

import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

public abstract class AbstractColorfulParticle extends SpriteBillboardParticle {
    protected final SpriteProvider spriteProvider;

    public boolean colorTransitionMode = false;
    protected Vector3f minColor;
    protected Vector3f maxColor;

    public AbstractColorfulParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velX, velY, velZ);
        this.spriteProvider = spriteProvider;
//        this.setSpriteForAge(this.spriteProvider);
    }

    protected Vector3f randomColor(Vector3f min, Vector3f max) {
        float redMin = Math.min(min.x, max.x);
        float greenMin = Math.min(min.y, max.y);
        float blueMin = Math.min(min.z, max.z);
        float redMax = Math.max(min.x, max.x);
        float greenMax = Math.max(min.y, max.y);
        float blueMax = Math.max(min.z, max.z);

        float red = MathHelper.nextFloat(this.random, redMin, redMax);
        float green = MathHelper.nextFloat(this.random, greenMin, greenMax);
        float blue = MathHelper.nextFloat(this.random, blueMin, blueMax);
        return new Vector3f(red, green, blue);
    }

    protected Vector3f randomColor() {
        //random decent color
        int rgbIncrease = random.nextBetween(1, 3);
        int red = rgbIncrease == 1 ? random.nextBetween(150, 255) : 255;
        int green = rgbIncrease == 2 ? random.nextBetween(150, 255) : 255;
        int blue = rgbIncrease == 3 ? random.nextBetween(150, 255) : 255;
        return new Vector3f(red / 255f, green / 255f, blue / 255f);
    }

    public void setColorFromColor(Vector3f color) {
        this.setColor(color.x, color.y, color.z);
    }

    public void setTransitionColors(Vector3f start, Vector3f end) {
        this.colorTransitionMode = true;
        this.minColor = start;
        this.maxColor = end;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        if (this.colorTransitionMode) this.updateTransitionColor(tickDelta);
        super.buildGeometry(vertexConsumer, camera, tickDelta);
    }

    public void updateTransitionColor(float tickDelta) {
        float f = (this.age + tickDelta) / (this.maxAge + 1.0F);
        Vector3f vector3f = new Vector3f(this.minColor).lerp(this.maxColor, f);
        this.red = vector3f.x();
        this.green = vector3f.y();
        this.blue = vector3f.z();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
}
