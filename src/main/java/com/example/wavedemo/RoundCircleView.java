package com.example.wavedemo;

import android.animation.Animator;
import android.animation.PointFEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import java.util.Random;

/**
 * Created by wenruitao on 2018/6/5.
 */

public class RoundCircleView extends View{
    private int[] setcolor=new int[]{0xff76C2DC,0xff97DCFF,0xff6C9ED7,0xff5861D9};
    private int[] setcolor1=new int[]{0x0076C2DC,0x0097DCFF,0x006C9ED7,0x005861D9};
    private Paint circlePaint;
    private int defaultSize;
    private Random random;
    private int round;

    private PointF[] currentPoint;
    private int size=20;

    private int flag=-1;
    private int flag1=-1;

    private int[] startCenterX;
    private int[] startCenterY;
    private int[] endCenterX;
    private int[] endCenterY;
    public RoundCircleView(Context context){
        super(context);
        init(context);
    }
    public RoundCircleView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }
    public RoundCircleView(Context context,AttributeSet attributeSet,int defystable){
        super(context,attributeSet,defystable);
        init(context);
    }
    private void init(Context context){
        random=new Random();
        defaultSize=DpOrPxUtils.dip2px(context,300);

        currentPoint=new PointF[size];
        startCenterX=new int[size];
        startCenterY=new int[size];
        endCenterX=new int[size];
        endCenterY=new int[size];

        for(int i=0;i<size;i++){
            if(random.nextFloat()>0.5){
                flag=1;
            }else{
                flag=-1;
            }
            if(random.nextFloat()>0.5){
                flag1=1;
            }else{
                flag1=-1;
            }
            startCenterX[i]=defaultSize/2+flag*random.nextInt(defaultSize/4);
            startCenterY[i]=defaultSize/2+flag1*random.nextInt(defaultSize/4);
            endCenterX[i]=random.nextInt(defaultSize);
            endCenterY[i]=random.nextInt(defaultSize);
            round=5+random.nextInt(15);
//            startCenterX[i] = 0;
//            startCenterY[i] = 0;
//            endCenterX[i] = random.nextInt(defaultSize);
//            endCenterY[i] = random.nextInt(defaultSize);

            currentPoint[i] = new PointF(0, 0);
            circlePaint=new Paint();
            int color=random.nextInt(4);
            LinearGradient linearGradient2=new LinearGradient(startCenterX[i],startCenterY[i],endCenterX[i],endCenterY[i],new int[]{setcolor1[color],setcolor[color]},null, Shader.TileMode.CLAMP);
            circlePaint.setShader(linearGradient2);
            //circlePaint.setColor(setcolor[random.nextInt(4)]);
            //circlePaint.setColor(Color.parseColor("#aa0000"));
            circlePaint.setAntiAlias(true);
        }
        startAnimation();

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height=measureSize(defaultSize,heightMeasureSpec);
        int width=measureSize(defaultSize,widthMeasureSpec);
        int min=Math.min(width,height);
        setMeasuredDimension(min,min);
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

        for (int i=0;i<size;i++){

//            if(currentPoint[i]==null){
//                currentPoint[i]=new PointF(startCenterX[i],startCenterY[i]);
//                drawCircle(canvas,i);
//                startAnimation(i);
//            }else{
                drawCircle(canvas,i);
//            }

//            if(percent==0){
//                centerX[i]=defaultSize/2+flag*random.nextInt(defaultSize/4);
//                centerY[i]=defaultSize/2+flag1*random.nextInt(defaultSize/4);
//            }
//            Log.i("wrt","centerX=="+centerX+"---centerY=="+centerY);
//            if(flag>0&&flag1>0){
//                canvas.drawCircle(centerX[i]+percent*(defaultSize-centerX[i]),centerY[i]+percent*(defaultSize-centerY[i]),round,circlePaint);
//
//            }else if(flag>0&&flag1<0){
//                canvas.drawCircle(centerX[i]+percent*(defaultSize-centerX[i]),(1-percent)*centerY[i],round,circlePaint);
//            }else if(flag<0&&flag1>0){
//                canvas.drawCircle((1-percent)*centerX[i],centerY[i]+percent*(defaultSize-centerY[i]),round,circlePaint);
//            }else if(flag<0&&flag1<0){
//                canvas.drawCircle((1-percent)*centerX[i],(1-percent)*centerY[i],round,circlePaint);
//                //Log.i("wrt","(1-percent)*centerX=="+(1-percent)*centerX+"----(1-percent)*centerY=="+(1-percent)*centerY);
//            }

//            if(i==0){
//                currentPoint[i]=new PointF(defaultSize/2+flag*random.nextInt(defaultSize/4),defaultSize/2+flag1*random.nextInt(defaultSize/4));
//                drawCircle(canvas,i);
//                startAnimation(i);
//            }else{
//                drawCircle(canvas,i);
//            }
        }

    }
    private void drawCircle(Canvas canvas,int m){
        float x=currentPoint[m].x;
        float y=currentPoint[m].y;
        canvas.drawCircle(x,y,round,circlePaint);
    }


//    public class RoundCircleAnim extends Animation{
//        public RoundCircleAnim(){}
//
//        @Override
//        protected void applyTransformation(float interpolatedTime, Transformation t) {
//            super.applyTransformation(interpolatedTime, t);
//            percent=interpolatedTime;
//            postInvalidate();
//        }
//    }

    private void startAnimation(){
        ValueAnimator animator=ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (float) animation.getAnimatedValue();
                for (int i=0; i<currentPoint.length; i++) {
                    currentPoint[i].x = startCenterX[i] + (endCenterX[i] - startCenterX[i]) * f;
                    currentPoint[i].y = startCenterY[i] + (endCenterY[i] - startCenterY[i]) * f;
                }
                postInvalidateOnAnimation();
            }
        });
        animator.setDuration(5000);
        animator.setRepeatCount(Animation.INFINITE);
        animator.start();
    }

//    public class PointEvaluator implements TypeEvaluator {
//
//        @Override
//        public Object evaluate(float fraction, Object startValue, Object endValue) {
//            PointF startPoint = (PointF) startValue;
//            PointF endPoint = (PointF) endValue;
//            float x = startPoint.x + fraction * (endPoint.x - startPoint.x);
//            float y = startPoint.y + fraction * (endPoint.y - startPoint.y);
//            PointF point = new PointF(x, y);
//            return point;
//        }
//
//    }

}
