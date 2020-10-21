package com.breathe.hexmap;

import com.badlogic.gdx.math.Vector2;

public class HexCell {
    public int x;
    public int y;
    public int i;
    public int j;

    public int id;

    public HexCell(int id, int x, int y, int i, int j) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.i = i;
        this.j = j;

    }

    public String toString() {
        return id + ": " + x + "," + y+ "," + i+ "," + j;
    }
}
