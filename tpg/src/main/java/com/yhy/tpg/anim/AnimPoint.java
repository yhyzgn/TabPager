package com.yhy.tpg.anim;

public class AnimPoint {
    public float x, y;
    public float x1, y1;
    public float x2, y2;

    public int operation;

    public AnimPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public AnimPoint(float x, float y, int operation) {
        this(x, y);
        this.operation = operation;
    }

    public AnimPoint(float x, float y, float x1, float y1, int operation) {
        this(x, y, operation);
        this.x1 = x1;
        this.y1 = y1;
    }

    public AnimPoint(float x, float y, float x1, float y1, float x2, float y2, int operation) {
        this(x, y, x1, y1, operation);
        this.x2 = x2;
        this.y2 = y2;
    }

    public static AnimPoint moveTo(float x, float y, int operation) {
        return new AnimPoint(x, y, operation);
    }

    public static AnimPoint lineTo(float x, float y, int operation) {
        return new AnimPoint(x, y, operation);
    }

    public static AnimPoint quadTo(float x, float y, float x1, float y1, int operation) {
        return new AnimPoint(x, y, x1, y1, operation);
    }

    public static AnimPoint curveTo(float x, float y, float x1, float y1, float x2, float y2, int operation) {
        return new AnimPoint(x, y, x1, y1, x2, y2, operation);
    }
}
