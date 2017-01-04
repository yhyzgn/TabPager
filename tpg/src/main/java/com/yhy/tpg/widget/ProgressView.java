package com.yhy.tpg.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yhy.tpg.R;
import com.yhy.tpg.anim.AnimEvaluator;
import com.yhy.tpg.anim.AnimPath;
import com.yhy.tpg.anim.AnimPoint;

/**
 * Created by 颜洪毅 on 2017/1/4
 */
public class ProgressView extends RelativeLayout {
    private static final String TAG = "ProgressView";
    //每个圆点相对于整个控件的缩放比例，缩小的倍数
    private static final int IMG_SCALE_ARG = 5;
    //动画周期时间
    private static final long ANIM_TIME = 2000;
    //3个圆点的颜色，红、紫、绿
    private static final String[] IMG_COLOR_ARR = {"#ff6600", "#a448ff", "#00df16"};

    //整个控件的宽度
    private int mWidth;
    //整个控件高度
    private int mHeight;

    //每个圆点的宽度
    private int mImgWidth;
    //每个圆点的高度
    private int mImgHeight;
    //每个圆点的半径
    private float mImgR;

    //内部区域的宽度
    private float mInnerWidth;
    //内部区域的高度
    private float mInnerHeight;

    //内外区域之间y方向上的偏移量
    private float mOffsetOutY;

    //以下的点都表示圆点运动过程中，圆心所经过的坐标
    //左上角点坐标
    private float mLeftTopX;
    private float mLeftTopY;
    //最左边点坐标
    private float mLeftCenterX;
    private float mLeftCenterY;
    //左下角点坐标
    private float mLeftBottomX;
    private float mLeftBottomY;
    //最中间点坐标
    private float mCenterX;
    private float mCenterY;
    //右上角点坐标
    private float mRightTopX;
    private float mRightTopY;
    //最右边点坐标
    private float mRightCenterX;
    private float mRightCenterY;
    //右下角点坐标
    private float mRightBottomX;
    private float mRightBottomY;

    //3个圆点
    private ImageView ivA;
    private ImageView ivB;
    private ImageView ivC;

    //动画估值器
    private AnimEvaluator mEvaluator;

    //标记位，表示是否已经初始过，默认否
    private boolean inited = false;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //设置背景颜色，默认为透明，主要为了引发onDraw方法被调用
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressViewAttrs);
        int color = ta.getColor(R.styleable.ProgressViewAttrs_bg_color, Color.TRANSPARENT);
        ta.recycle();

        setBackgroundColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //整个控件的宽度
        mWidth = getWidth();
        //整个控件高度
        mHeight = getHeight();

        //按比例计算每个圆点的宽度和高度
        mImgWidth = mWidth / IMG_SCALE_ARG;
        //由于是圆点，所以高度和宽度要相等
        mImgHeight = mImgWidth;

        //每个圆点的半径
        mImgR = mImgWidth / 2.0f;

        //内部区域宽度=外部区域宽度-左右两边各一个圆点的半径
        mInnerWidth = mWidth - mImgWidth;
        //内部高度=内部宽度的一半（保证左右两边区域都是正方形）
        mInnerHeight = mInnerWidth / 2.0f;

        //内外区域之间y方向上的偏移量
        mOffsetOutY = (mHeight - mInnerHeight) / 2.0f;

        //左上角点坐标
        mLeftTopX = mInnerWidth / 4.0f + mImgR;
        mLeftTopY = mOffsetOutY;

        //最左边点坐标
        mLeftCenterX = mImgR;
        mLeftCenterY = mHeight / 2.0f;

        //左下角点坐标
        mLeftBottomX = mInnerWidth / 4.0f + mImgR;
        mLeftBottomY = mOffsetOutY + mInnerHeight;

        //最中间点坐标
        mCenterX = mWidth / 2.0f;
        mCenterY = mHeight / 2.0f;

        //右上角点坐标
        mRightTopX = (mInnerWidth / 4.0f) * 3.0f + mImgR;
        mRightTopY = mOffsetOutY;

        //最右边点坐标
        mRightCenterX = mWidth - mImgR;
        mRightCenterY = mHeight / 2.0f;

        //右下角点坐标
        mRightBottomX = (mInnerWidth / 4.0f) * 3.0f + mImgR;
        mRightBottomY = mOffsetOutY + mInnerHeight;

        //绘制路径，起到辅助线作用
//        drawPath(canvas);
        //实际应用中就不需要绘制辅助线了

        //开始写布局
        if (!inited) {
            //由于onDraw方法会频繁被调用，而这里只需要绘制一遍，所以需要加标记，是否已经初始化过
            //绘制3个圆点
            drawImgs();

            //开始动画
            startAnim();

            //改变标记的值
            inited = true;
        }
    }

    /**
     * 开始动画
     */
    private void startAnim() {
        //创建估值器实例
        mEvaluator = new AnimEvaluator();

        //创建第一个圆点的动画路径
        AnimPath pathA = new AnimPath();
        pathA.moveTo(mLeftTopX - mImgR, mLeftTopY - mImgR)
                .quadTo(mLeftCenterX - mImgR, mLeftTopY - mImgR, mLeftCenterX - mImgR,
                        mLeftCenterY - mImgR)
                .quadTo(mLeftCenterX - mImgR, mLeftBottomY - mImgR, mLeftBottomX - mImgR,
                        mLeftBottomY - mImgR)
                .quadTo(mCenterX - mImgR, mLeftBottomY - mImgR, mCenterX - mImgR, mCenterY - mImgR)
                .quadTo(mCenterX - mImgR, mRightTopY - mImgR, mRightTopX - mImgR, mRightTopY -
                        mImgR)
                .quadTo(mRightCenterX - mImgR, mRightTopY - mImgR, mRightCenterX - mImgR,
                        mRightCenterY - mImgR)
                .quadTo(mRightCenterX - mImgR, mRightBottomY - mImgR, mRightBottomX - mImgR,
                        mRightBottomY - mImgR)
                .quadTo(mCenterX - mImgR, mRightBottomY - mImgR, mCenterX - mImgR, mCenterY - mImgR)
                .quadTo(mCenterX - mImgR, mLeftTopY - mImgR, mLeftTopX - mImgR, mLeftTopY - mImgR);

        //创建第二个圆点的动画路径
        AnimPath pathB = new AnimPath();
        pathB.moveTo(mLeftBottomX - mImgR, mLeftBottomY - mImgR)
                .quadTo(mCenterX - mImgR, mLeftBottomY - mImgR, mCenterX - mImgR, mCenterY - mImgR)
                .quadTo(mCenterX - mImgR, mRightTopY - mImgR, mRightTopX - mImgR, mRightTopY -
                        mImgR)
                .quadTo(mRightCenterX - mImgR, mRightTopY - mImgR, mRightCenterX - mImgR,
                        mRightCenterY - mImgR)
                .quadTo(mRightCenterX - mImgR, mRightBottomY - mImgR, mRightBottomX - mImgR,
                        mRightBottomY - mImgR)
                .quadTo(mCenterX - mImgR, mRightBottomY - mImgR, mCenterX - mImgR, mCenterY - mImgR)
                .quadTo(mCenterX - mImgR, mLeftTopY - mImgR, mLeftTopX - mImgR, mLeftTopY - mImgR)
                .quadTo(mLeftCenterX - mImgR, mLeftTopY - mImgR, mLeftCenterX - mImgR,
                        mLeftCenterY - mImgR)
                .quadTo(mLeftCenterX - mImgR, mLeftBottomY - mImgR, mLeftBottomX - mImgR,
                        mLeftBottomY - mImgR);

        //创建第三个圆点的动画路径
        AnimPath pathC = new AnimPath();
        pathC.moveTo(mRightCenterX - mImgR, mRightCenterY - mImgR)
                .quadTo(mRightCenterX - mImgR, mRightBottomY - mImgR, mRightBottomX - mImgR,
                        mRightBottomY - mImgR)
                .quadTo(mCenterX - mImgR, mRightBottomY - mImgR, mCenterX - mImgR, mCenterY - mImgR)
                .quadTo(mCenterX - mImgR, mLeftTopY - mImgR, mLeftTopX - mImgR, mLeftTopY - mImgR)
                .quadTo(mLeftCenterX - mImgR, mLeftTopY - mImgR, mLeftCenterX - mImgR,
                        mLeftCenterY - mImgR)
                .quadTo(mLeftCenterX - mImgR, mLeftBottomY - mImgR, mLeftBottomX - mImgR,
                        mLeftBottomY - mImgR)
                .quadTo(mCenterX - mImgR, mLeftBottomY - mImgR, mCenterX - mImgR, mCenterY - mImgR)
                .quadTo(mCenterX - mImgR, mRightTopY - mImgR, mRightTopX - mImgR, mRightTopY -
                        mImgR)
                .quadTo(mRightCenterX - mImgR, mRightTopY - mImgR, mRightCenterX - mImgR,
                        mRightCenterY - mImgR);

        //分别创建属性动画
        ObjectAnimator animA = createAnimator("positionA", pathA);
        ObjectAnimator animB = createAnimator("positionB", pathB);
        ObjectAnimator animC = createAnimator("positionC", pathC);

        //创建同步动画集合
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(animA, animB, animC);
        animSet.setDuration(ANIM_TIME);
        //将动画设置成匀速运动，默认是先加速，再减速的
        animSet.setInterpolator(new LinearInterpolator());
        animSet.start();
    }

    /**
     * 创建属性动画
     *
     * @param propertiName 要改变的属性名称
     * @param path         动画路径
     * @return 属性动画
     */
    private ObjectAnimator createAnimator(String propertiName, AnimPath path) {
        ObjectAnimator anim = ObjectAnimator.ofObject(this, propertiName, mEvaluator, path
                .getPointList().toArray());
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setRepeatCount(Integer.MAX_VALUE);
        return anim;
    }

    /**
     * 改变第一个圆点的坐标
     *
     * @param point 动画过程中的点
     */
    public void setPositionA(AnimPoint point) {
        ivA.setTranslationX(point.x);
        ivA.setTranslationY(point.y);
    }

    /**
     * 改变第二个圆点的坐标
     *
     * @param point 动画过程中的点
     */
    public void setPositionB(AnimPoint point) {
        ivB.setTranslationX(point.x);
        ivB.setTranslationY(point.y);
    }

    /**
     * 改变第三个圆点的坐标
     *
     * @param point 动画过程中的点
     */
    public void setPositionC(AnimPoint point) {
        ivC.setTranslationX(point.x);
        ivC.setTranslationY(point.y);
    }

    /**
     * 绘制三个圆点在控件上
     */
    private void drawImgs() {
        ivA = createIV(Color.parseColor(IMG_COLOR_ARR[0]));
        ivB = createIV(Color.parseColor(IMG_COLOR_ARR[1]));
        ivC = createIV(Color.parseColor(IMG_COLOR_ARR[2]));

        //设置初始位置
        initIvPosition(ivA, mLeftTopX - mImgR, mLeftTopY - mImgR);
        initIvPosition(ivB, mLeftBottomX - mImgR, mLeftBottomY - mImgR);
        initIvPosition(ivC, mRightCenterX - mImgR, mRightCenterY - mImgR);

        //将3个圆点添加到控件中
        addView(ivA);
        addView(ivB);
        addView(ivC);
    }

    /**
     * 初始化圆点位置
     *
     * @param iv 圆点
     * @param x  x坐标
     * @param y  y坐标
     */
    private void initIvPosition(ImageView iv, float x, float y) {
        iv.setTranslationX(x);
        iv.setTranslationY(y);
    }

    /**
     * 创建一个圆点
     *
     * @param color 背景颜色
     * @return 圆点
     */
    private ImageView createIV(int color) {
        //布局参数
        LayoutParams params = new LayoutParams(mImgWidth, mImgHeight);

        //设置圆形Shape的drawable作背景
        float[] outerRadii = {mImgR, mImgR, mImgR, mImgR, mImgR, mImgR, mImgR, mImgR};
        Shape shape = new RoundRectShape(outerRadii, new RectF(0, 0, 0, 0), null);
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        drawable.getPaint().setColor(color);
        drawable.getPaint().setAntiAlias(true);

        ImageView iv = new ImageView(getContext());
        iv.setLayoutParams(params);
        iv.setBackgroundDrawable(drawable);

        return iv;
    }

    /**
     * 绘制辅助线
     *
     * @param canvas 画布对象
     */
    private void drawPath(Canvas canvas) {
        //绘制关键点，7个点
        Paint pointPaint = new Paint();
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(10);
        canvas.drawPoint(mLeftTopX, mLeftTopY, pointPaint);
        canvas.drawPoint(mLeftCenterX, mLeftCenterY, pointPaint);
        canvas.drawPoint(mLeftBottomX, mLeftBottomY, pointPaint);
        canvas.drawPoint(mCenterX, mCenterY, pointPaint);
        canvas.drawPoint(mRightTopX, mRightTopY, pointPaint);
        canvas.drawPoint(mRightCenterX, mRightCenterY, pointPaint);
        canvas.drawPoint(mRightBottomX, mRightBottomY, pointPaint);

        //绘制路径
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);

        Path path = new Path();
        path.moveTo(mLeftTopX, mLeftTopY);
        path.quadTo(mLeftCenterX, mLeftTopY, mLeftCenterX, mLeftCenterY);
        path.quadTo(mLeftCenterX, mLeftBottomY, mLeftBottomX, mLeftBottomY);
        path.quadTo(mCenterX, mLeftBottomY, mCenterX, mCenterY);
        path.quadTo(mCenterX, mRightTopY, mRightTopX, mRightTopY);
        path.quadTo(mRightCenterX, mRightTopY, mRightCenterX, mRightCenterY);
        path.quadTo(mRightCenterX, mRightBottomY, mRightBottomX, mRightBottomY);
        path.quadTo(mCenterX, mRightBottomY, mCenterX, mCenterY);
        path.quadTo(mCenterX, mLeftTopY, mLeftTopX, mLeftTopY);

        canvas.drawPath(path, paint);
    }
}
