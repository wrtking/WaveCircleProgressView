package com.example.wavedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * Created by wenruitao on 2018/6/5.
 */

public class RainCircleView extends BaseRainView {
    ArrayList<RainCirlceItem> rainCircleList=new ArrayList<>();

    int size=15 ;
    public RainCircleView (Context context){
        super(context);
    }
    public RainCircleView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        //init1();
    }
    public RainCircleView(Context context,AttributeSet attributeSet,int defyStable){
        super(context,attributeSet,defyStable);
        //init1();
    }
    @Override
    protected void initData() {
        for(int i=0;i<size;i++){
            RainCirlceItem item=new RainCirlceItem(getWidth(),getHeight());
            rainCircleList.add(item);
        }
    }

    @Override
    protected void logic() {
        for (RainCirlceItem item:rainCircleList){
            item.move();
        }
    }

    @Override
    protected void drawSub(Canvas canvas) {
        for (RainCirlceItem item:rainCircleList){
            item.draw(canvas);
        }
    }

}
