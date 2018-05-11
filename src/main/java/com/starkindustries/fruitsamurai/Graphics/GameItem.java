package com.starkindustries.fruitsamurai.Graphics;

import org.joml.Vector3f;

/**
 * This class is used to represent an object in the game.
 * Stores every data needed to effectively render objects to the screen.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class GameItem {
	private Mesh mesh;
	private Vector3f position;
	private Vector3f rotation;
	private Mesh[] meshes;
	private float scale;
	public boolean affectedByPhysics = false;
	public boolean visible = true;
	public boolean menuItem = false;
	private Vector3f acceleration;
	private Vector3f velocity;
	private boolean toDelete;
	private boolean startsGame;
	private boolean showsLeaderboards;

	private boolean exitsGame;
	/**
	 * Standard constructor of the {@link GameItem} class.
	 * @author Aszalós Roland
	 * @version 1.0
	 * @since Fruit Samurai 0.1
	 */
	public GameItem() {
		position = new Vector3f();
		rotation = new Vector3f();
		acceleration = new Vector3f();
		velocity = new Vector3f();
		toDelete = false;
		startsGame = false;
		showsLeaderboards = false;
		exitsGame = false;
		scale = 1;
	}
	/**
	 * Standard constructor of the {@link GameItem} class.
	 * @param meshes an array of meshes that make up a game item.
	 * @author Aszalós Roland
	 * @version 1.0
	 * @since Fruit Samurai 0.1
	 */
	public GameItem(Mesh[] meshes) {
		this();
		if(meshes.length==1)
			mesh = meshes[0];
		else
			this.meshes = meshes;
	}
	/**
	 * Standard constructor of the {@link GameItem} class.
	 * @param mesh {@link Mesh} that makes up a game item.
	 * @author Aszalós Roland
	 * @version 1.0
	 * @since Fruit Samurai 0.1
	 */
	public GameItem(Mesh mesh) {
		this();
		this.mesh=mesh;
	}

	/**
	 * @return the position of the game item.
	 */
	public Vector3f getPosition()
	{
		return position;
	}
	/**
	 * @param x sets the x position of the {@link GameItem}
	 * @param y sets the y position of the {@link GameItem}
	 * @param z sets the z position of the {@link GameItem}
	 */
	public void setPosition(float x, float y, float z)
	{
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}
	/**
	 * @param pos sets the position of the {@link GameItem}
	 */
	public void setPosition(Vector3f pos)
	{
		position.x = pos.x;
		position.y = pos.y;
		position.z = pos.z;
	}
	/**
	 * @return the scale of the game item.
	 */
	public float getScale()
	{
		return scale;
	}
	/**
	 * @param scale sets the scale of the {@link GameItem}
	 */
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	/**
	 * @return the rotation of the game item.
	 */
	public Vector3f getRotation()
	{
		return rotation;
	}
	/**
	 * @param x sets the x rotation of the {@link GameItem}
	 * @param y sets the y rotation of the {@link GameItem}
	 * @param z sets the z rotation of the {@link GameItem}
	 */
	public void setRotation(float x, float y, float z)
	{
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}
	/**
	 * @return the {@link Mesh} of the game item.
	 */
	public Mesh getMesh() 
	{
		return mesh;
	}
	/**
	 * @return the {@link Mesh} array of the game item.
	 */
	public Mesh [] getMeshes()
	{
		return meshes;
	}
	/**
	 * @param mesh sets the mesh of the {@link GameItem}
	 */
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	/**
	 * @param mesh sets the meshes of the {@link GameItem}
	 */
	public void setMesh(Mesh[] mesh) {
		this.meshes = mesh;
	}
	/**
	 * @return the velocity of the game item.
	 */
	public Vector3f getVelocity()
	{
		return velocity;
	}
	/**
	 * @param v sets the velocity of the {@link GameItem}
	 */
	public void setVelocity(Vector3f v)
	{
		velocity=v;
	}
	/**
	 * @param x sets the x velocity of the {@link GameItem}
	 * @param y sets the y velocity of the {@link GameItem}
	 * @param z sets the z velocity of the {@link GameItem}
	 */
	public void setVelocity(float x,float y,float z)
	{
		velocity.x=x;
		velocity.y=y;
		velocity.z=z;
	}
	/**
	 * @return the acceleration of the game item.
	 */
	public Vector3f getAcceleration()
	{
		return acceleration;
	}
	/**
	 * @param v sets the acceleration of the {@link GameItem}
	 */
	public void setAcceleration(Vector3f v)
	{
		acceleration=v;
	}
	/**
	 * @param x sets the x acceleration of the {@link GameItem}
	 * @param y sets the y acceleration of the {@link GameItem}
	 * @param z sets the z acceleration of the {@link GameItem}
	 */
	public void setAcceleration(float x,float y,float z)
	{
		acceleration.x=x;
		acceleration.y=y;
		acceleration.z=z;
	}
	/**
	 * @param b sets whether to delete the {@link GameItem}
	 */
	public void setToDelete(boolean b){
		toDelete = b;
	}
	/**
	 * @return whether to delete a {@link GameItem} or not.
	 */
	public boolean isToDelete() {
		return toDelete;
	}
	/**
	 * @return whether a {@link GameItem} starts the game or not.
	 */
	public boolean isStartsGame() {
		return startsGame;
	}
	/**
	 * @param startsGame sets whether the {@link GameItem} starts the game or not.
	 */
	public void setStartsGame(boolean startsGame) {
		this.startsGame = startsGame;
	}
	/**
	 * @return whether a {@link GameItem} shows the leader boards or not.
	 */
	public boolean isShowsLeaderboards() {
		return showsLeaderboards;
	}
	/**
	 * @param showsLeaderboards sets whether the {@link GameItem} shows the leader boards or not.
	 */
	public void setShowsLeaderboards(boolean showsLeaderboards) {
		this.showsLeaderboards = showsLeaderboards;
	}
	/**
	 * @return whether a {@link GameItem} exits the game or not.
	 */
	public boolean isExitsGame() {
		return exitsGame;
	}
	/**
	 * @param exitsGame sets whether the {@link GameItem} exits the game or not.
	 */
	public void setExitsGame(boolean exitsGame) {
		this.exitsGame = exitsGame;
	}
}
