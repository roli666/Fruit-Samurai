package com.starkindustries.fruitsamurai.Graphics;

import org.joml.Quaternionf;
import org.joml.Vector3f;

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

	public GameItem(Mesh[] meshes) {
		this();
		if(meshes.length==1)
			mesh = meshes[0];
		else
			this.meshes = meshes;
	}
	public GameItem(Mesh mesh) {
		this();
		this.mesh=mesh;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(float x, float y, float z)
	{
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}
	public void setPosition(Vector3f pos)
	{
		position.x = pos.x;
		position.y = pos.y;
		position.z = pos.z;
	}

	public float getScale()
	{
		return scale;
	}

	public void setScale(float scale)
	{
		this.scale = scale;
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public void setRotation(float x, float y, float z)
	{
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}

	public Mesh getMesh() 
	{
		return mesh;
	}
	public Mesh [] getMeshes()
	{
		return meshes;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	public void setMesh(Mesh[] mesh) {
		this.meshes = mesh;
	}
	public Vector3f getVelocity()
	{
		return velocity;
	}
	public void setVelocity(Vector3f v)
	{
		velocity=v;
	}
	public void setVelocity(float x,float y,float z)
	{
		velocity.x=x;
		velocity.y=y;
		velocity.z=z;
	}
	public Vector3f getAcceleration()
	{
		return acceleration;
	}
	public void setAcceleration(Vector3f v)
	{
		acceleration=v;
	}
	public void setAcceleration(float x,float y,float z)
	{
		acceleration.x=x;
		acceleration.y=y;
		acceleration.z=z;
	}

	public void setToDelete(boolean b){
		toDelete = b;
	}

	public boolean isToDelete() {
		return toDelete;
	}
	public boolean isStartsGame() {
		return startsGame;
	}

	public void setStartsGame(boolean startsGame) {
		this.startsGame = startsGame;
	}

	public boolean isShowsLeaderboards() {
		return showsLeaderboards;
	}

	public void setShowsLeaderboards(boolean showsLeaderboards) {
		this.showsLeaderboards = showsLeaderboards;
	}
	public boolean isExitsGame() {
		return exitsGame;
	}

	public void setExitsGame(boolean exitsGame) {
		this.exitsGame = exitsGame;
	}
}
