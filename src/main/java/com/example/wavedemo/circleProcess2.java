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

public class circleProcess2 extends View{
    private Paint paint;
    private int defaultSize;//自定义view默认的宽高
    private int viewSize;//重新测量后View实际的宽高
    private int ringRadiu;
    private Paint cirlePaint;
    private int paintWidth=5;
    private int cirleRadiu=20;

    private boolean isReDraw=false;//是否需要重新绘制小球
    private long time;//设置小球消失的时间


    public circleProcess2(Context context){
        super(context);
        init(context);
    }
    public circleProcess2(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }
    public circleProcess2(Context context,AttributeSet attributeSet,int defStable){
        super(context,attributeSet,defStable);
        init(context);
    }
    private void init(Context context){

        defaultSize=DpOrPxUtils.dip2px(context,200);


        paint=new Paint();
        paint.setColor(Color.parseColor("#33FFFFFF"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);

        cirlePaint=new Paint();
        cirlePaint.setColor(Color.WHITE);
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
        canvas.drawCircle(viewSize/2, cirleRadiu, cirleRadiu, cirlePaint);
    }


}
