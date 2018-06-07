package com.example.wavedemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;

/**
 * Created by wenruitao on 2018/5/29.
 */

public class circleProcess extends View{
    private Paint paint;
    private int defaultSize;//自定义view默认的宽高
    private int viewSize;//重新测量后View实际的宽高
    private int ringRadiu;
    private Paint cirlePaint;
    private int paintWidth=5;
    private int cirleRadiu=30;

    private double currentAngle;

    private ValueAnimator animator;
    private boolean flag;

    float angle;

    public circleProcess(Context context){
        super(context);
        init(context);
    }
    public circleProcess(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }
    public circleProcess(Context context,AttributeSet attributeSet,int defStable){
        super(context,attributeSet,defStable);
        init(context);
    }
    private void init(Context context){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
        defaultSize=DpOrPxUtils.dip2px(context,200);


        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);

        cirlePaint=new Paint();
        cirlePaint.setColor(Color.BLACK);
        cirlePaint.setStyle(Paint.Style.FILL_AND_STROKE);


    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height=measureSize(defaultSize,heightMeasureSpec);
        int width=measureSize(defaultSize,widthMeasureSpec);
        int min=Math.min(width,height);
        setMeasuredDimension(min,min);
        viewSize=min;
    }
    private int measureSize(int defaultSize,int measureSpec){
        int result=defaultSize;
        int specMode=View.MeasureSpec.getMode(measureSpec);
        int specSize=View.MeasureSpec.getSize(measureSpec);
        if(specMode== View.MeasureSpec.EXACTLY){
            result=specSize;
        }else if(specMode==View.MeasureSpec.AT_MOST){
            result=Math.min(result,specSize);
        }
        return result;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ringRadiu=viewSize/2-cirleRadiu;
        canvas.drawCircle(viewSize/2,viewSize/2,ringRadiu,paint);

        //Log.i("wrt","ringRadiu=="+ringRadiu);

        if(!flag){
            startCirMotion();
            flag=true;
        }

        Log.i("wrt","angle11=="+angle);
        drawGlobule(canvas,viewSize/2);

    }
    /**
     * 绘制小球,起始位置为圆环最低点
     *
     * @param canvas
     * @param central
     */
    private void drawGlobule(Canvas canvas, float central) {

        Log.i("wrt","angle22=="+angle);
        float cx = central + (float) (ringRadiu * Math.cos(angle));
        float cy = (float) (central + ringRadiu * Math.sin(angle));
        //Log.i("wrt","cx=="+Math.cos(angle)+"---cy=="+Math.sin(angle));
        canvas.drawCircle(cx, cy, cirleRadiu, cirlePaint);
    }
    /**
     * 旋转小球
     */
    private void startCirMotion() {
        animator= ValueAnimator.ofFloat(0f, 1f);//起始位置在最低点
        animator.setDuration(100000)
                .setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                angle = 360*(Float) animation.getAnimatedValue();
                //currentAngle = angle * Math.PI / 180;
                Log.i("wrt","angle=="+angle);
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());// 匀速旋转

        //animator.start();
        animator.setStartDelay(500);
        animator.start();
        Log.i("wrt","angle333=="+angle);
    }
    public void cancelAnim(){
        animator.cancel();
        animator=null;
    }

}
