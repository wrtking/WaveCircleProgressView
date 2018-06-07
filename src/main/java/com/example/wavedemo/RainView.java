package com.example.wavedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wenruitao on 2018/6/1.
 */

public class RainView extends BaseRainView {


    ArrayList<RainItem> rainList=new ArrayList<>();

    int size=30 ;

    public RainView(Context context){
        super(context);
    }
    public RainView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }
    public RainView(Context context,AttributeSet attributeSet,int defyStable){
        super(context,attributeSet,defyStable);
    }



    @Override
    protected void initData() {
        Log.i("wrt","getWindth=="+getWidth()+"---getHeight=="+getHeight());
        for(int i=0;i<size;i++){
            RainItem item=new RainItem(getWidth(),getHeight(),200);
            rainList.add(item);
        }
    }

    @Override
    protected void logic() {
        for (RainItem item:rainList){
            item.move();
        }

    }

    @Override
    protected void drawSub(Canvas canvas) {

        for (RainItem item:rainList){
            item.draw(canvas);
        }

    }
}
