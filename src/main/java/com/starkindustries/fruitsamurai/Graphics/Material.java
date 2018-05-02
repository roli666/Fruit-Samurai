package com.starkindustries.fruitsamurai.Graphics;

import org.joml.Vector3f;

public class Material {
    private String Name;
    private float Ns;
    private Vector3f Ka;
    private Vector3f Kd;
    private Vector3f Ks;
    private Vector3f Ke;
    private float Ni;
    private float D;
    private float Illum;

    public Material(String name){
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getNs() {
        return Ns;
    }

    public void setNs(float ns) {
        Ns = ns;
    }

    public Vector3f getKa() {
        return Ka;
    }

    public void setKa(Vector3f ka) {
        Ka = ka;
    }

    public Vector3f getKd() {
        return Kd;
    }

    public void setKd(Vector3f kd) {
        Kd = kd;
    }

    public Vector3f getKs() {
        return Ks;
    }

    public void setKs(Vector3f ks) {
        Ks = ks;
    }

    public Vector3f getKe() {
        return Ke;
    }

    public void setKe(Vector3f ke) {
        Ke = ke;
    }

    public float getNi() {
        return Ni;
    }

    public void setNi(float ni) {
        Ni = ni;
    }

    public float getD() {
        return D;
    }

    public void setD(float d) {
        D = d;
    }

    public float getIllum() {
        return Illum;
    }

    public void setIllum(float illum) {
        Illum = illum;
    }
}
