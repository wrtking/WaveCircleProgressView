package com.example.wavedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

/**
 * Created by wenruitao on 2018/5/31.
 */

public class MainActivity2 extends Activity{
    private RainView rainView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rainview);
        rainView=(RainView)findViewById(R.id.rainview);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
