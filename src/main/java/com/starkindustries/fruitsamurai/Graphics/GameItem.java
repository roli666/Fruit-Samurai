package com.starkindustries.fruitsamurai.Graphics;

import org.joml.Vector3f;

public class GameItem {
	private final Mesh mesh;
	private final Vector3f position;
	private final Vector3f rotation;
	private float scale;
	private Vector3f acceleration;
	private Vector3f velocity;
	public boolean isFruit;
	public boolean affectedByPhysics = false;
	public boolean menuItem = false;
	public boolean visible = true;

	public GameItem(Mesh mesh) 
	{
		this.mesh = mesh;
		position = new Vector3f(0, 0, 0);
		scale = 1;
		rotation = new Vector3f(0, 0, 0);
		acceleration = new Vector3f(0,0,0);
		velocity = new Vector3f(0,0,0);
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
	public Vector3f getVelocity() 
	{
		return velocity;
	}
	public void setVelocity(Vector3f v) 
	{
		velocity=v;
	}
	public Vector3f getAcceleration() 
	{
		return acceleration;
	}
	public void setAcceleration(Vector3f v) 
	{
		acceleration=v;
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
}
