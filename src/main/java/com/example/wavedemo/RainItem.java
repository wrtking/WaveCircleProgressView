package com.example.wavedemo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

/**
 * Created by wenruitao on 2018/6/1.
 */

public class RainItem {
    private int width;
    private int height;

    //圆球
    private int centerX;
    private int centerY;
    private int radiu=10;
    private Paint circlePaint;
    private int a=0xff58BEF8;
    private int b=0xff76C2DC;
    private int c=0xff5861D9;
    private int d=0xff6C9ED7;
    private int e=0xff5E7FD8;
    private int f=0xff7CEDED;
    private int[] setcolor=new int[]{a,b,c,d,e,f};
    private int flag=1;


    private int viewSize;

    public RainItem(int width,int height,int viewSize){
        this.width=width;
        this.height=height;
        this.viewSize=viewSize;
        init();
    }

    private float opt1;
    private float opt2;
    private Random random;
    public void init(){
        random=new Random();
        if(random.nextFloat()>0.5){
            flag=1;
        }else{
            flag=-1;
        }

        //圆球
        centerX=viewSize/2+radiu;
        centerY=height/2;
        //centerY=this.height/2;
        radiu=5+random.nextInt(10);
        circlePaint=new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(setcolor[random.nextInt(6)]);

    }
    public void draw(Canvas canvas){

        canvas.drawCircle(centerX,centerY,radiu,circlePaint);

    }
    public void move(){

        //圆球运动
        opt1=width*random.nextFloat()/5000;
        opt2=height*(random.nextFloat()/5000)*flag;
        centerX+=this.width*opt1;
        centerY+=this.height*opt2;
        if(centerX>(viewSize-radiu)||centerY>(this.height-radiu)||centerY<radiu){
            centerX=viewSize/2+radiu;
            centerY=height/2;
            radiu=5+random.nextInt(10);
        }
    }



}
