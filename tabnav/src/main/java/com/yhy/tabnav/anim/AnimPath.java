package com.yhy.tabnav.anim;

import java.util.ArrayList;
import java.util.List;

public class AnimPath {
    public static final int MOVE = 0;
    public static final int LINE = 1;
    public static final int QUAD = 2;
    public static final int CURVE = 3;
    private List<AnimPoint> pointList;

    public AnimPath() {
        pointList = new ArrayList<>();
    }

    public AnimPath moveTo(float x, float y) {
        pointList.add(AnimPoint.moveTo(x, y, MOVE));
        return this;
    }

    public AnimPath lineTo(float x, float y) {
        pointList.add(AnimPoint.lineTo(x, y, LINE));
        return this;
    }

    public AnimPath quadTo(float x, float y, float x1, float y1) {
        pointList.add(AnimPoint.quadTo(x, y, x1, y1, QUAD));
        return this;
    }

    public AnimPath curveTo(float x, float y, float x1, float y1, float x2, float y2) {
        pointList.add(AnimPoint.curveTo(x, y, x1, y1, x2, y2, CURVE));
        return this;
    }

    public List<AnimPoint> getPointList() {
        return pointList;
    }
}
