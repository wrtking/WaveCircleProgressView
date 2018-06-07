package com.example.wavedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wenruitao on 2018/6/1.
 */

public abstract class BaseRainView extends View{

    Thread thread;
    public BaseRainView(Context context){
        super(context);
    }
    public BaseRainView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }
    public BaseRainView(Context context, AttributeSet attributeSet, int defyStable){
        super(context,attributeSet,defyStable);
    }

    @Override
    final protected void onDraw(Canvas canvas) {
        //禁止子类覆盖，应final
        if(thread==null){
            thread=new MyThread();
            thread.start();
        }else{
            drawSub(canvas);
        }
    }
    protected abstract void logic();
    protected abstract void drawSub(Canvas canvas);
    protected abstract void initData();

    @Override
    final protected void onDetachedFromWindow() {
        //离开屏幕时结束
        running=false;
        super.onDetachedFromWindow();
    }

    private boolean running=true;
    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            initData();
            while(running){
                logic();
                postInvalidate();//线程中更新绘制，重新调用onDraw方法
                try {
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
