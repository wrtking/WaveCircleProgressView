package com.example.wavedemo;

import android.content.Context;

/**
 * Created by wenruitao on 2018/5/28.
 */

public class DpOrPxUtils {
    public static int dip2px(Context context,float dpValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
    public static int px2dip(Context context,float pxValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }
}
