package com.example.wavedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by wenruitao on 2018/6/6.
 */

public class CircleRainView extends BaseRainView {

    //散射的小球
    ArrayList<RainItem> rainList=new ArrayList<>();
    int size=20 ;
    int bitmapWidth,bitmapHeight;
    //圆环
    private Paint paint;
    private int defaultSize;//自定义view默认的宽高
    private int viewSize;//重新测量后View实际的宽高
    private Paint cirlePaint;
    private int paintWidth=5;

    public CircleRainView(Context context){
        super(context);
        init(context);
    }
    public CircleRainView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }
    public CircleRainView(Context context,AttributeSet attributeSet,int defStable){
        super(context,attributeSet,defStable);
        init(context);
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
        int specMode= View.MeasureSpec.getMode(measureSpec);
        int specSize=View.MeasureSpec.getSize(measureSpec);
        if(specMode== View.MeasureSpec.EXACTLY){
            result=specSize;
        }else if(specMode==View.MeasureSpec.AT_MOST){
            result=Math.min(result,specSize);
        }
        return result;
    }
    private void init(Context context){
        defaultSize=DpOrPxUtils.dip2px(context,200);
        bitmapWidth=DpOrPxUtils.dip2px(context,100);
        bitmapHeight=DpOrPxUtils.dip2px(context,40);

        paint=new Paint();
        paint.setColor(Color.parseColor("#33FFFFFF"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);
        paint.setAntiAlias(true);

        cirlePaint=new Paint();
        cirlePaint.setColor(Color.WHITE);
        cirlePaint.setAntiAlias(true);
        cirlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void initData() {
        Log.i("wrt","bitmapWidth=="+bitmapWidth+"---bitmapHeigth=="+bitmapHeight+"---viewSize=="+viewSize);
        for(int i=0;i<size;i++){
            RainItem item=new RainItem(bitmapWidth,bitmapHeight,viewSize);
            rainList.add(item);
        }
    }

    @Override
    protected void drawSub(Canvas canvas) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        for (RainItem item:rainList){
            item.draw(canvas);
        }
        canvas.drawCircle(viewSize/2,viewSize/2,viewSize/2-bitmapHeight/2,paint);
        canvas.drawCircle(viewSize/2, bitmapHeight/2, 20, cirlePaint);
    }
    @Override
    protected void logic() {
        for (RainItem item:rainList){
            item.move();
        }
    }
}
