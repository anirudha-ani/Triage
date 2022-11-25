package engine.support;// package

import java.io.Serializable;

/**
 * A class representing a two-dimensional vector of {@code int}s. Useful when working with positions
 * and sizes in a discrete space, such as tiles on a grid.
 * 
 * {@code Vec2i} instances, like {@link String}s, are immutable, and should be treated in a similar
 * way. Specifically, note that all methods will return a new instance rather than modifying the
 * same instance, so the return value must be used or saved.
 * 
 * For performance, this class and all of its methods have been marked {@code final}.
 * 
 * @author zdavis
 */
public final class Vec2i implements Serializable {
	private static final long serialVersionUID = 5659632794862666944L;
	
	public static final Vec2i ORIGIN = new Vec2i(0, 0);
	
	/**
	 * Since {@link Vec2i} instances are immutable, their x and y fields may be accessed without getters.
	 */
	public final int x, y;

	/**
	 * Constructor. Creates a new instance from a value
	 * @param val      the x and y components of the vector
	 */
	public Vec2i(int val) {
		this.x = val;
		this.y = val;
	}
	
	/**
	 * Creates a new vector from an x and y component.
	 * @param x      the x-component of the vector
	 * @param y      the y-component of the vector
	 */
	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Factory method that creates a {@code Vec2i} by {@linkplain Math#round(float) rounding} the components of a {@link Vec2d} to the nearest integer.
	 * @param v      the Vec2f to round
	 * @return       a Vec2i created by rounding the components of {@code v}
	 */
	public final static Vec2i fromRounded(Vec2d v) {
		return new Vec2i(Math.round((float) v.x), Math.round((float) v.y));
	}
	
	/**
	 * Factory method that creates a {@code Vec2i} by taking the {@linkplain Math#floor(double) floor} of the components of a {@link Vec2d}.
	 * @param v      the {@code Vec2f} to floor
	 * @return       a {@code Vec2i} created by taking the floor of the components of {@code v}
	 */
	public final static Vec2i fromFloored(Vec2d v) {
		return new Vec2i((int) Math.floor((float) v.x), (int) Math.floor((float) v.y));
	}
	
	
	/**
	 * Factory method that creates a {@code Vec2i} by taking the {@linkplain Math#ceil(double) ceiling} of the components of a {@link Vec2d}.
	 * @param v      the {@code Vec2f} to ceil
	 * @return       a {@code Vec2i} created by taking the ceiling of the components of {@code v}
	 */
	public final static Vec2i fromCeiled(Vec2d v) {
		return new Vec2i((int) Math.ceil((float) v.x), (int) Math.ceil((float) v.y));
	}
	
	/*
	 * Vector ops
	 */
	
	/**
	 * Multiplies the vector by a scalar.
	 * @param s      the scalar by which to multiply this vector
	 * @return       a new {@link Vec2i} instance where each component has been multiplied by {@code s}
	 */
	public final Vec2i smult(int s) {
		return new Vec2i(this.x * s, this.y * s);
	}
	
	/**
	 * Divides the vector by a scalar. Note that this is integer division.
	 * @param s		the scalar by which to divide this vector
	 * @return		a new {@link Vec2i} instance where each component has been divided by
	 * 				{@code s}
	 */
	public final Vec2i sdiv(int s) {
		return new Vec2i(this.x / s, this.y / s);
	}
	
	/**
	 * Multiplies the vector piecewise by another vector. NOT A DOT PRODUCT.
	 * @param v      the vector by which to multiply this vector
	 * @return       a new {@link Vec2i} instance where each component has been multiplied by the corresponding component in {@code v}
	 */
	public final Vec2i pmult(Vec2i v) {
		return new Vec2i(this.x * v.x, this.y * v.y);
	}
	
	/**
	 * Primitive version of {@link #pmult(Vec2i)}.
	 */
	public final Vec2i pmult(int x, int y) {
		return new Vec2i(this.x * x, this.y * y);
	}
	
	/**
	 * Divides the vector piecewise by another vector.
	 * @param v      the vector by which to divide this vector
	 * @return       a new {@link Vec2i} instance where each component has been divided by the corresponding component in {@code v}
	 */
	public final Vec2i pdiv(Vec2i v) {
		return new Vec2i(this.x / v.x, this.y / v.y);
	}
	
	/**
	 * Primitive version of {@link #pdiv(Vec2i)}.
	 */
	public final Vec2i pdiv(int x, int y) {
		return new Vec2i(this.x / x, this.y / y);
	}
	
	/**
	 * Adds another vector to this vector.
	 * @param v      the vector to add to this vector
	 * @return       a new {@link Vec2i} instance where each component has added the corresponding component in {@code v}
	 */
	public final Vec2i plus(Vec2i v) {
		return new Vec2i(this.x + v.x, this.y + v.y);
	}
	
	/**
	 * Primitive version of {@link #plus(Vec2i)}.
	 */
	public final Vec2i plus(int x, int y) {
		return new Vec2i(this.x + x, this.y + y);
	}
	
	/**
	 * Subtracts another vector from this vector.
	 * @param v      the vector to subtract from this vector
	 * @return       a new {@link Vec2i} instance where each component has added the corresponding component in {@code v}
	 */
	public final Vec2i minus(Vec2i v) {
		return new Vec2i(this.x - v.x, this.y - v.y);
	}
	
	/**
	 * Primitive version of {@link #minus(Vec2i)}.
	 */
	public final Vec2i minus(int x, int y) {
		return new Vec2i(this.x - x, this.y - y);
	}
	
	/*
	 * Object overrides
	 */

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == null || obj.getClass() != Vec2i.class)
			return false;
		Vec2i other = (Vec2i) obj;
		return this.x == other.x && this.y == other.y;
	}

	@Override
	public final String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
}
