package com.yhy.tabnav.anim;

import android.animation.TypeEvaluator;

import com.orhanobut.logger.Logger;

public class AnimEvaluator implements TypeEvaluator<AnimPoint> {

    @Override
    public AnimPoint evaluate(float fraction, AnimPoint startValue, AnimPoint endValue) {
        float remain = 1 - fraction;
        float x = 0, y = 0;
        float startX = 0, startY = 0;
        switch (endValue.operation) {
            case AnimPath.MOVE:
                x = endValue.x;
                y = endValue.y;
                break;
            case AnimPath.LINE:
                startX = startValue.operation == AnimPath.QUAD ? startValue.x1 : startValue.x;
                startX = startValue.operation == AnimPath.CURVE ? startValue.x2 : startX;
                startY = startValue.operation == AnimPath.QUAD ? startValue.y1 : startValue.y;
                startY = startValue.operation == AnimPath.CURVE ? startValue.y2 : startY;
                //当前位置 = 起始位置 + （终点位置 - 起始位置） * 百分比
                x = startValue.x + (endValue.x - startX) * fraction;
                y = startValue.y + (endValue.y - startY) * fraction;
                break;
            case AnimPath.QUAD:
                startX = startValue.operation == AnimPath.CURVE ? startValue.x2 : startValue.x;
                startY = startValue.operation == AnimPath.CURVE ? startValue.y2 : startValue.y;
                startX = startValue.operation == AnimPath.QUAD ? startValue.x1 : startX;
                startY = startValue.operation == AnimPath.QUAD ? startValue.y1 : startY;
                x = remain * remain * startX + 2 * remain * fraction * endValue.x + fraction * fraction * endValue.x1;
                y = remain * remain * startY + 2 * remain * fraction * endValue.y + fraction * fraction * endValue.y1;
                break;
            case AnimPath.CURVE:
                startX = startValue.operation == AnimPath.QUAD ? startValue.x1 : startValue.x;
                startY = startValue.operation == AnimPath.QUAD ? startValue.y1 : startValue.y;
                x = remain * remain * remain * startX + 3 * remain * remain * fraction * endValue.x + 3 * remain * fraction * fraction * endValue.x1 + fraction * fraction * fraction * endValue.x2;
                y = remain * remain * remain * startY + 3 * remain * remain * fraction * endValue.y + 3 * remain * fraction * fraction * endValue.y1 + fraction * fraction * fraction * endValue.y2;
                break;
            default:
                x = endValue.x;
                y = endValue.y;
                break;
        }
//        Logger.t("AnimEvaluator").i("x = " + x + ", y = " + y);
        return new AnimPoint(x, y);
    }
}
