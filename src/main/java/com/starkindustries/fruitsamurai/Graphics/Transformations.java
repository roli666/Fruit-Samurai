package com.starkindustries.fruitsamurai.Graphics;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
/**
 * This class represents a transformations in the game.
 * <ul>
 *     <li>Scales</li>
 *     <li>Rotates</li>
 *     <li>Translates</li>
 * </ul>
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Transformations {
	private final Matrix4f projectionMatrix;
	private final Matrix4f modelMatrix;
	private final Matrix4f orthoModelMatrix;
	/**
	 * Standard constructor of the {@link Transformations} class.
	 * @author Aszalós Roland
	 * @version 1.0
	 * @since Fruit Samurai 0.1
	 */
	public Transformations() {
		modelMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
		orthoModelMatrix = new Matrix4f();
	}
	/**
	 * Builds a Perspective projection matrix.
	 * @param fov
	 * @param width
	 * @param height
	 * @param zNear
	 * @param zFar
	 * @author Aszalós Roland
	 * @version 1.0
	 * @since Fruit Samurai 0.1
	 * @return a perspective projection matrix
	 */
	public final Matrix4f getProjectionMatrixPersp(float fov, float width, float height, float zNear, float zFar) {
		float aspectRatio = width / height;
		projectionMatrix.identity();
		projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
		return projectionMatrix;
	}
	/**
	 * Builds an OrthoGraphic projection matrix.
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param zNear
	 * @param zFar
	 * @author Aszalós Roland
	 * @version 1.0
	 * @since Fruit Samurai 0.1
	 * @return a perspective projection matrix
	 */
	public final Matrix4f getProjectionMatrixOrtho(float left, float right, float bottom, float top, float zNear,float zFar) {
		projectionMatrix.identity();
		projectionMatrix.ortho(left, right, bottom, top, zNear, zFar);
		return projectionMatrix;
	}

	/**
	 * Builds the world matrix.
	 * @param offset
	 * @param rotation
	 * @param scale
	 * @author Aszalós Roland
	 * @version 1.0
	 * @since Fruit Samurai 0.1
	 * @return a perspective projection matrix
	 */
	public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
		modelMatrix.identity().translate(offset)
			.rotateX((float) Math.toRadians(rotation.x))
			.rotateY((float) Math.toRadians(rotation.y))
			.rotateZ((float) Math.toRadians(rotation.z))
			.scale(scale);
		return modelMatrix;
	}
	@Deprecated
	public Matrix4f buildOrtoProjModelMatrix(GameItem gameItem, Matrix4f orthoMatrix) {
		Vector3f rotation = gameItem.getRotation();
		modelMatrix.identity().translate(gameItem.getPosition()).
				rotateX((float) Math.toRadians(-rotation.x)).
				rotateY((float) Math.toRadians(-rotation.y)).
				rotateZ((float) Math.toRadians(-rotation.z)).
				scale(gameItem.getScale());
		orthoModelMatrix.set(orthoMatrix);
		orthoModelMatrix.mul(modelMatrix);
		return orthoModelMatrix;
	}

}
