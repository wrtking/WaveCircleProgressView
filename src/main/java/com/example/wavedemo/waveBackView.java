package com.example.wavedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BaseInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.TextView;

/**
 * Created by wenruitao on 2018/5/30.
 */

public class waveBackView extends View{

    private Paint wavePaint;//绘制波浪画笔
    private Path wavePath;//绘制波浪Path
    private Paint secondWavePaint;//第二个波浪的画笔
    private int waveNum,waveNum2;//波浪的数量
    private float percent,percent2;//进度条占比

    private float progressNum;//可以更新的进度条数值
    private float progressNum2;
    private float maxNum;//进度条最大值

    private WaveProgressAnim waveProgressAnim;//波浪上升的动画
    private WaveLineAnim waveLineAnim;//波浪横移的动画
    private AnimationSet animationSet;
    private float waveMovingDistance,waveMovingDistance2;//波浪平移的距离

    private float waveWidth,waveWidth2;//波浪宽度
    private float waveHeight,waveHeight2;//波浪高度

    private int windowWindth,windowHeigth;
    private Paint bitmapPaint;
    public waveBackView(Context context){
        super(context);
        init(context);
    }
    public waveBackView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }
    public waveBackView(Context context,AttributeSet attributeSet,int defyStable){
        super(context,attributeSet,defyStable);
        init(context);
    }
    private void init(Context context){
        DisplayMetrics dm=getResources().getDisplayMetrics();
        windowWindth=dm.widthPixels;
        windowHeigth=dm.heightPixels;
        percent = 0;
        percent2=0;
        progressNum = 0;
        progressNum2=0;
        maxNum = 100;
        waveMovingDistance=0;
        waveMovingDistance2=0;
        waveProgressAnim = new WaveProgressAnim();
        waveLineAnim=new WaveLineAnim();
        animationSet=new AnimationSet(true);
        wavePath=new Path();

        waveWidth=DpOrPxUtils.dip2px(context,250);
        waveHeight=DpOrPxUtils.dip2px(context,150);
        waveHeight2=DpOrPxUtils.dip2px(context,60);
        waveWidth2=DpOrPxUtils.dip2px(context,80);

        waveNum=(int)Math.ceil(Double.parseDouble(String.valueOf(windowWindth/waveWidth/2)));
        waveNum2=(int)Math.ceil(Double.parseDouble(String.valueOf(windowWindth/waveWidth2/2)));

        bitmapPaint=new Paint();
        bitmapPaint.setColor(Color.WHITE);
        bitmapPaint.setAntiAlias(true);

        setProgressNum(20,15);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setLayerType(LAYER_TYPE_SOFTWARE,null);
        wavePaint=new Paint();
        LinearGradient linearGradient=new LinearGradient(0,0,0,windowHeigth,new int[]{0xff35D4FC,0x0035D4FC},null, Shader.TileMode.CLAMP);
        wavePaint.setShader(linearGradient);
        wavePaint.setAntiAlias(true);

        secondWavePaint = new Paint();
        LinearGradient linearGradient2=new LinearGradient(0,0,0,windowHeigth,new int[]{0xff5861d9,0x005861d9},null, Shader.TileMode.CLAMP);
        secondWavePaint.setShader(linearGradient2);
        secondWavePaint.setAntiAlias(true);//设置抗锯齿
        //因为要覆盖在第一层波浪上，且要让半透明生效，所以选SRC_ATOP模式
        secondWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));

        canvas.drawPath(getWavePath(),wavePaint);
        canvas.drawPath(getSecondWavePath(),secondWavePaint);
    }

    /**
     * 第一个波浪的路径
     * @return
     */
    private int flag;
    private Path getWavePath(){
        wavePath.reset();
        //移动到左上方，也就是p3点
        wavePath.moveTo(0, (1-percent)*windowHeigth);
        //移动到左下方，也就是p2点
        wavePath.lineTo(0, windowHeigth);
        //移动到右下方，也就是p1点
        wavePath.lineTo(windowWindth, windowHeigth);
        //移动到右上方，也就是p0点
        wavePath.lineTo(windowWindth + waveMovingDistance, (1-percent)*windowHeigth);
        //从p0开始向p3方向绘制波浪曲线（注意绘制二阶贝塞尔曲线控制点和终点x坐标的正负值）
        for (int i=0;i<waveNum2*2;i++){
            wavePath.rQuadTo(-waveWidth/2, waveHeight, -waveWidth, 0);
            wavePath.rQuadTo(-waveWidth/2, -waveHeight, -waveWidth, 0);
        }
        wavePath.close();
        return wavePath;
    }

    /**
     * 第二个波浪的路径
     * @return
     */
    private Path getSecondWavePath(){
        float changeWaveHeight = waveHeight2;

        wavePath.reset();
        //移动到左上方，也就是p3点
        wavePath.moveTo(0, (1-percent2)*windowHeigth);
        //移动到左下方，也就是p2点
        wavePath.lineTo(0, windowHeigth);
        //移动到右下方，也就是p1点
        wavePath.lineTo(windowWindth, windowHeigth);
        //移动到右上方，也就是p0点
        wavePath.lineTo(windowWindth + waveMovingDistance2, (1-percent2)*windowHeigth);
        //从p0开始向p3方向绘制波浪曲线（注意绘制二阶贝塞尔曲线控制点和终点x坐标的正负值）
        for (int i=0;i<waveNum2*2;i++){
            wavePath.rQuadTo(-waveWidth2, changeWaveHeight, -waveWidth2*2, 0);
            wavePath.rQuadTo(-waveWidth2, -changeWaveHeight, -waveWidth2*2, 0);
        }
        //将path封闭起来
        wavePath.close();
        return wavePath;
    }

    public class WaveProgressAnim extends Animation {
        public WaveProgressAnim(){}

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if(percent < progressNum / maxNum){
                percent = interpolatedTime * progressNum / maxNum;
            }
            if(percent2<progressNum2/maxNum){
                percent2 = interpolatedTime * progressNum2 / maxNum;
            }

            postInvalidateOnAnimation();
        }
    }
    public class WaveLineAnim extends Animation{
        public WaveLineAnim(){}
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            waveMovingDistance = interpolatedTime * waveNum * waveWidth * 2;
            waveMovingDistance2=interpolatedTime*waveNum2*waveWidth2*4;
            postInvalidateOnAnimation();
        }
    }
    /**
     * 设置进度条数值
     * @param progressNum 进度条数值
     */
    public void setProgressNum(float progressNum,float progressNum2) {
        this.progressNum = progressNum;
        this.progressNum2=progressNum2;

        percent = 0;
        waveProgressAnim.setDuration(5000);
        waveLineAnim.setDuration(8000);
        waveLineAnim.setRepeatCount(Animation.INFINITE);//让动画无线循环

        animationSet.addAnimation(waveLineAnim);
        animationSet.addAnimation(waveProgressAnim);

        animationSet.setInterpolator(new LinearInterpolator());//让动画匀速播放，不然会出现波浪平移停顿的现象

        this.startAnimation(animationSet);
    }

    public void cancelAnim(){
        waveProgressAnim.cancel();
        waveLineAnim.cancel();
        animationSet.cancel();
    }
}
