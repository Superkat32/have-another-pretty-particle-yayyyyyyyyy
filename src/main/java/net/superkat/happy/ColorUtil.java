package net.superkat.happy;

import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import java.awt.*;

public class ColorUtil {

    public static Vector3f colorToVector(Color color) {
        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        return new Vector3f(red, green, blue);
    }

}
