package info.u_team.u_team_core.util;

import java.util.Random;

import net.minecraft.util.math.Vec3d;

public class MathUtil {
	
	public static final Random RANDOM = new Random();
	
	public static Vec3d rotateVectorAroundYCC(Vec3d vec, double angle) {
		return rotateVectorCC(vec, new Vec3d(0, 1, 0), angle);
	}
	
	public static Vec3d rotateVectorCC(Vec3d vec, Vec3d axis, double angle) {
		final double x = vec.getX();
		final double y = vec.getY();
		final double z = vec.getZ();
		
		final double u = axis.getX();
		final double v = axis.getY();
		final double w = axis.getZ();
		
		final double rotationX = u * (u * x + v * y + w * z) * (1 - Math.cos(angle)) + x * Math.cos(angle) + (-w * y + v * z) * Math.sin(angle);
		final double rotationY = v * (u * x + v * y + w * z) * (1 - Math.cos(angle)) + y * Math.cos(angle) + (w * x - u * z) * Math.sin(angle);
		final double rotationZ = w * (u * x + v * y + w * z) * (1 - Math.cos(angle)) + z * Math.cos(angle) + (-v * x + u * y) * Math.sin(angle);
		return new Vec3d(rotationX, rotationY, rotationZ);
	}
	
	public static int getRandomNumberInRange(int min, int max) {
		return getRandomNumberInRange(RANDOM, min, max);
	}
	
	public static int getRandomNumberInRange(Random random, int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}
	
	public static float getRandomNumberInRange(float min, float max) {
		return getRandomNumberInRange(RANDOM, min, max);
	}
	
	public static float getRandomNumberInRange(Random random, float min, float max) {
		return random.nextFloat() * (max - min) + min;
	}
	
	public static double getRandomNumberInRange(double min, double max) {
		return getRandomNumberInRange(RANDOM, min, max);
	}
	
	public static double getRandomNumberInRange(Random random, double min, double max) {
		return random.nextDouble() * (max - min) + min;
	}
	
}
