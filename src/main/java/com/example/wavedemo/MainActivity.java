package com.example.wavedemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private TextView tv;
    private circleProcess2 circleprocess;
    private RotateAnimation rotate;
    private RelativeLayout rl_circle;
    private Button btn;
    //private ImageView center_circle;
    //private ImageView iv;
    private RainView rainview;
    AnimatorSet animatorSet=new AnimatorSet();
    private CircleRainView circleRainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        circleRainView=(CircleRainView)findViewById(R.id.circleRainView);
        //iv=(ImageView)findViewById(R.id.iv);
        rainview=(RainView)findViewById(R.id.rainview);
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //center_circle.setVisibility(View.VISIBLE);
                animatorSet.start();

                //iv.setVisibility(View.GONE);
                rainview.setVisibility(View.GONE);
                //startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });
        tv=(TextView) findViewById(R.id.tv);
        tv.setVisibility(View.VISIBLE);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rotate.cancel();
//                rotate=null;
//                circleprocess.cancelAnim();
                startActivity(new Intent(MainActivity.this,MainActivity1.class));
            }
        });
        circleprocess=(circleProcess2)findViewById(R.id.circleprocess);
        rl_circle=(RelativeLayout)findViewById(R.id.rl_circle);
        //circleprocess.setVisibility(View.GONE);
        rotate=new RotateAnimation(0f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(6000);//设置动画持续时间
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setRepeatCount(Animation.INFINITE);

        circleRainView.startAnimation(rotate);


//        center_circle=(ImageView)findViewById(R.id.center_circle);
//        List<Animator> list=new ArrayList<>();
//
//        ObjectAnimator alpha=ObjectAnimator.ofFloat(center_circle,"alpha",0f,1f,0f);
//        //alpha.setRepeatCount(Animation.INFINITE);
//        alpha.setDuration(2000);
//        list.add(alpha);
//        ObjectAnimator scaleX=ObjectAnimator.ofFloat(center_circle,"scaleX",0f,1f,0f);
//        scaleX.setDuration(2000);
//        //scaleX.setRepeatCount(Animation.INFINITE);
//        list.add(scaleX);
//        ObjectAnimator scaleY=ObjectAnimator.ofFloat(center_circle,"scaleY",0f,1f,0f);
//        scaleY.setDuration(2000);
//        //scaleY.setRepeatCount(Animation.INFINITE);
//        list.add(scaleY);
//
//        animatorSet.playTogether(list);
//
//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                startActivity(new Intent(MainActivity.this,MainActivity1.class));
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });



    }


}
