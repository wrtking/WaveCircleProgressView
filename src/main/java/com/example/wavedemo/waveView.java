package com.example.wavedemo;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wenruitao on 2018/5/28.
 */

public class waveView extends View{

    private Paint wavePaint;//绘制波浪画笔
    private Path wavePath;//绘制波浪Path

    private float waveWidth,waveWidth2,waveWidth3;//波浪宽度
    private float waveHeight,waveHeight2,waveHeight3;//波浪高度

    private int waveNum,waveNum2,waveNum3;//波浪的数量
    private int defaultSize;//自定义view默认的宽高

    private int viewSize;//重新测量后View实际的宽高

    private float percent;//进度条占比

    private float progressNum;//可以更新的进度条数值
    private float maxNum;//进度条最大值

    private WaveProgressAnim waveProgressAnim;//波浪上升的动画
    private WaveLineAnim waveLineAnim;//波浪横移的动画
    private AnimationSet animationSet;

    private Paint circlePaint;//圆形进度框画笔

    private Bitmap bitmap;//缓存bitmap
    private Canvas bitmapCanvas;//画圆形

    private float waveMovingDistance,waveMovingDistance2,waveMovingDistance3;//波浪平移的距离

    private Paint secondWavePaint;//第二个波浪的画笔
    private Paint thridWavePaint;//第三个波浪的画笔

    //画进度条
    private Paint cirleProcessPaint;
    private int cirleProcess;

    private ImageView iv;


    public waveView(Context context){
        super(context);
        init(context);
    }
    public waveView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }
    public waveView(Context context,AttributeSet attributeSet,int defyStable){
        super(context,attributeSet,defyStable);
        init(context);
    }

    private void init(Context context){
        percent = 0;
        progressNum = 0;
        maxNum = 100;
        waveMovingDistance=0;
        waveMovingDistance2=0;
        waveProgressAnim = new WaveProgressAnim();
        waveLineAnim=new WaveLineAnim();
        animationSet=new AnimationSet(true);

        waveWidth=DpOrPxUtils.dip2px(context,100);
        waveHeight=DpOrPxUtils.dip2px(context,30);
        waveHeight2=DpOrPxUtils.dip2px(context,22);
        waveWidth2=DpOrPxUtils.dip2px(context,90);
        waveWidth3=DpOrPxUtils.dip2px(context,110);
        waveHeight3=DpOrPxUtils.dip2px(context,18);

        defaultSize=DpOrPxUtils.dip2px(context,175);
        cirleProcess=10;
        wavePath=new Path();
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);//设置抗锯齿
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height=measureSize(defaultSize,heightMeasureSpec);
        int width=measureSize(defaultSize,widthMeasureSpec);
        int min=Math.min(width,height);
        setMeasuredDimension(min,min);
        viewSize=min;
        waveNum=(int)Math.ceil(Double.parseDouble(String.valueOf(viewSize/waveWidth/2)));
        waveNum2=(int)Math.ceil(Double.parseDouble(String.valueOf(viewSize/waveWidth2/2)));
        waveNum3=(int)Math.ceil(Double.parseDouble(String.valueOf(viewSize/waveWidth3/2)));
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
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        //画进度圆条
        cirleProcessPaint=new Paint();
        cirleProcessPaint.setColor(Color.parseColor("#55BFFF"));
        LinearGradient linearGradient4=new LinearGradient(0,0,0,viewSize,new int[]{0xff55BFFF,0xff8BFFE6},null, Shader.TileMode.CLAMP);
        cirleProcessPaint.setShader(linearGradient4);
        cirleProcessPaint.setShader(linearGradient4);
        cirleProcessPaint.setAntiAlias(true);
        cirleProcessPaint.setStyle(Paint.Style.STROKE);
        cirleProcessPaint.setStrokeWidth(cirleProcess);
        RectF over=new RectF(cirleProcess,cirleProcess,viewSize-cirleProcess,viewSize-cirleProcess);
        canvas.drawArc(over,-90,percent*360,false,cirleProcessPaint);

        bitmap = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        bitmapCanvas.drawCircle(viewSize/2, viewSize/2, viewSize/2-cirleProcess*2, circlePaint);

        wavePaint=new Paint();
        LinearGradient linearGradient=new LinearGradient(0,0,0,viewSize,new int[]{0xE65861d9,0x1A5861d9},null, Shader.TileMode.CLAMP);
        wavePaint.setShader(linearGradient);
        wavePaint.setAntiAlias(true);
        wavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//根据绘制顺序的不同选择相应的模式即可
        bitmapCanvas.drawPath(getWavePath(),wavePaint);

        secondWavePaint = new Paint();
        LinearGradient linearGradient2=new LinearGradient(0,0,0,viewSize,new int[]{0xE635D4FC,0x1A35D4FC},null, Shader.TileMode.CLAMP);
        secondWavePaint.setShader(linearGradient2);
        secondWavePaint.setAntiAlias(true);//设置抗锯齿
        //因为要覆盖在第一层波浪上，且要让半透明生效，所以选SRC_ATOP模式
        secondWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        bitmapCanvas.drawPath(getSecondWavePath(),secondWavePaint);

        thridWavePaint = new Paint();
        LinearGradient linearGradient3=new LinearGradient(0,0,0,viewSize,new int[]{0xE635D4FC,0x1A35D4FC},null, Shader.TileMode.CLAMP);
        thridWavePaint.setShader(linearGradient3);
        thridWavePaint.setAntiAlias(true);//设置抗锯齿
        //因为要覆盖在第一层波浪上，且要让半透明生效，所以选SRC_ATOP模式
        thridWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        bitmapCanvas.drawPath(getThridWavePath(),thridWavePaint);

        canvas.drawBitmap(bitmap, 0, 0, null);

        //动画结束，ImageView显示
        if((int)percent==1){
            cancelAnim();
            iv.setVisibility(View.VISIBLE);
            Paint endPaint=new Paint();
            endPaint.setColor(0x8045B0E6);
            endPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            endPaint.setAntiAlias(true);
            canvas.drawCircle(viewSize/2, viewSize/2, viewSize/2-cirleProcess*2, endPaint);
        }
    }

    /**
     * 第一个波浪的路径
     * @return
     */
    private Path getWavePath(){
        wavePath.reset();
        wavePath.moveTo(viewSize,(1-percent)*viewSize);
        wavePath.lineTo(viewSize, viewSize);
        wavePath.lineTo(0,viewSize);
        wavePath.lineTo(-waveMovingDistance, (1-percent)*viewSize);
        for(int i=0;i<2*waveNum;i++){
            wavePath.rQuadTo(waveWidth/2,waveHeight,waveWidth,0);
            wavePath.rQuadTo(waveWidth/2,-waveHeight,waveWidth,0);
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
        wavePath.moveTo(0, (1-percent)*viewSize);
        //移动到左下方，也就是p2点
        wavePath.lineTo(0, viewSize);
        //移动到右下方，也就是p1点
        wavePath.lineTo(viewSize, viewSize);
        //移动到右上方，也就是p0点
        wavePath.lineTo(viewSize + waveMovingDistance2, (1-percent)*viewSize);

        //从p0开始向p3方向绘制波浪曲线（注意绘制二阶贝塞尔曲线控制点和终点x坐标的正负值）
        for (int i=0;i<waveNum2*2;i++){
            wavePath.rQuadTo(-waveWidth2/2, changeWaveHeight, -waveWidth2, 0);
            wavePath.rQuadTo(-waveWidth2/2, -changeWaveHeight, -waveWidth2, 0);
        }

        //将path封闭起来
        wavePath.close();
        return wavePath;
    }
    /**
     * 第三个波浪的路径
     * @return
     */
    private Path getThridWavePath(){
        float changeWaveHeight = waveHeight3;

        wavePath.reset();
        //移动到左上方，也就是p3点
        wavePath.moveTo(0, (1-percent)*viewSize);
        //移动到左下方，也就是p2点
        wavePath.lineTo(0, viewSize);
        //移动到右下方，也就是p1点
        wavePath.lineTo(viewSize, viewSize);
        //移动到右上方，也就是p0点
        wavePath.lineTo(viewSize + waveMovingDistance3, (1-percent)*viewSize);

        //从p0开始向p3方向绘制波浪曲线（注意绘制二阶贝塞尔曲线控制点和终点x坐标的正负值）
        for (int i=0;i<waveNum3*2;i++){
            wavePath.rQuadTo(-waveWidth3/2, changeWaveHeight, -waveWidth3, 0);
            wavePath.rQuadTo(-waveWidth3/2, -changeWaveHeight, -waveWidth3, 0);
        }

        //将path封闭起来
        wavePath.close();
        return wavePath;
    }


    /**
     * 上升动画
     */
    public class WaveProgressAnim extends Animation{
        public WaveProgressAnim(){}

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if(percent < progressNum / maxNum){
                percent = interpolatedTime * progressNum / maxNum;
            }
            postInvalidate();
        }
    }

    /**
     * 水平动画
     */
    public class WaveLineAnim extends Animation{
        public WaveLineAnim(){}
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            waveMovingDistance = interpolatedTime * waveNum * waveWidth * 2;
            waveMovingDistance2=interpolatedTime*waveNum2*waveWidth2*2;
            waveMovingDistance3=interpolatedTime*waveNum3*waveWidth3*2;
            postInvalidate();
        }
    }


    /**
     * 设置进度条数值
     * @param progressNum 进度条数值
     * @param time 动画持续时间
     */
    public void setProgressNum(float progressNum, int time) {
        this.progressNum = progressNum;

        percent = 0;
        waveProgressAnim.setDuration(time);
        waveLineAnim.setDuration(5000);
        waveLineAnim.setRepeatCount(Animation.INFINITE);//让动画无线循环

        animationSet.addAnimation(waveLineAnim);
        animationSet.addAnimation(waveProgressAnim);

        animationSet.setInterpolator(new LinearInterpolator());//让动画匀速播放，不然会出现波浪平移停顿的现象

        this.startAnimation(animationSet);
    }

    public void setTime(int time,ImageView iv){
        this.iv=iv;
        setProgressNum(100,time);
    }
    public void cancelAnim(){
        waveProgressAnim.cancel();
        waveLineAnim.cancel();
        animationSet.cancel();
    }

}
