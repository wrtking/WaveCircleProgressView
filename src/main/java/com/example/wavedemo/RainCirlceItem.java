package com.example.wavedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by wenruitao on 2018/6/1.
 */

public class RainCirlceItem {
    private int width;
    private int height;

    //圆球
    private int centerX;
    private int centerY;
    private int radiu=20;
    private Paint circlePaint;
    private int a=0xff76C2DC;
    private int b=0xff97DCFF;
    private int c=0xff6C9ED7;
    private int d=0xff5861D9;
    private int[] setcolor=new int[]{a,b,c,d};
    private int flag=-1;
    private int flag2=-1;

    public RainCirlceItem(int width, int height){
        this.width=width;
        this.height=height;
        init();
    }

    private float opt;
    private float opt2;
    private Random random;
    public void init(){
        random=new Random();
        if(random.nextFloat()>0.5){
            flag=1;
        }else{
            flag=-1;
        }
        if(random.nextFloat()>0.5){
            flag2=1;
        }else{
            flag2=-1;
        }
        //圆球
        centerX=this.width/2+flag*random.nextInt(this.width/4);
        centerY=this.height/2+flag2*random.nextInt(this.height/4);

        radiu=5+random.nextInt(10);
        circlePaint=new Paint();
        circlePaint.setColor(setcolor[random.nextInt(4)]);

    }
    public void draw(Canvas canvas){
        canvas.drawCircle(centerX,centerY,radiu,circlePaint);
    }
    public void move(){
        //圆球运动
        opt=(0.02f+random.nextFloat())*flag;
        //opt2=(0.02f+random.nextFloat())*flag2;
        //opt2=height*(random.nextFloat()/5000)*flag;
        centerX+=radiu*opt;
        centerY+=radiu*opt;

        if(centerX>(this.width-2*radiu)||centerY>(this.height-2*radiu)||centerX<2*radiu||centerY<2*radiu){
            //圆球
            centerX=this.width/2+flag*random.nextInt(this.width/4);
            centerY=this.height/2+flag2*random.nextInt(this.height/4);
            radiu=10+random.nextInt(10);
        }
    }



}
