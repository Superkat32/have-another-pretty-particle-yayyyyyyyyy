package net.superkat.happy;

import java.util.function.IntSupplier;

/**
 * Represents opaque colors based on HSV. These colors are in gamma (sRGB) space.
 */
public record HSVColor(float hue, float saturation, float value) implements IntSupplier {

    /**
     * Gets a shifted color, clamped and rotated to valid values.
     * @param hueDelta The hue delta. Can be negative. Hues will be kept within 0..360.
     * @param satDelta The saturation delta. Can be negative. Saturation will be clamped to 0..1.
     * @param valueDelta The value delta. Can be negative. Value will be clamped to 0..1.
     * @return The shifted color.
     */
    public HSVColor withDelta(float hueDelta, float satDelta, float valueDelta) {
        return new HSVColor((hue + hueDelta) % 360.0f, clampToOne(saturation + satDelta), clampToOne(value + valueDelta));
    }

    public int getTranslucent(float opacity) {
        int base = getAsInt() & 0xFFFFFF;
        int a = ((int) opacity * 255) & 0xFF;
        return (a << 24) | base;
    }

    /**
     * Gets as an opaque sRGB color.
     */
    @Override
    public int getAsInt() {
        float c = value * saturation;
        float x = c * (1 - Math.abs((hue / 60) % 2 - 1));
        float m = value - c;
        
        float rp = 0;
        float gp = 0;
        float bp = 0;
        
        float h = hue % 360;
        
        if (h <= 60) {
            rp = c;
            gp = x;
            bp = 0;
        } else if (h <= 120) {
            rp = x;
            gp = c;
            bp = 0;
        } else if (h <= 180) {
            rp = 0;
            gp = c;
            bp = x;
        } else if (h <= 240) {
            rp = 0;
            gp = x;
            bp = c;
        } else if (h <= 300) {
            rp = x;
            gp = 0;
            bp = c;
        } else {
            rp = c;
            gp = 0;
            bp = x;
        }
        
        rp = (rp + m) * 255;
        gp = (gp + m) * 255;
        bp = (bp + m) * 255;
        
        int result = 0xFF_000000;
        result |= ((int) rp) << 16;
        result |= ((int) gp) <<  8;
        result |= ((int) bp) <<  0;
        
        return result;
    }
    
    /**
     * Clamps the value to between zero and one, inclusive
     */
    private float clampToOne(float value) {
        return clamp(value, 0, 1);
    }
    
    /**
     * Clamps the value to between min and max, inclusive.
     */
    private float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
