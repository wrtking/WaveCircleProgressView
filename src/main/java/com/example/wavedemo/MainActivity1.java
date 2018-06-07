package com.example.wavedemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenruitao on 2018/5/28.
 */

public class MainActivity1 extends Activity{
    private waveView waveview;
    private ImageView iv;
    private waveBackView wavebackview;
    private RainCircleView rainCircleView;
    private RoundCircleView radioaaaa;
    private ImageView ivssfk;

    private ImageView ivaaaa;

    private RelativeLayout rl_frist,rl_second;
    private Button btn;
    ObjectAnimator alphafirst;
    ObjectAnimator alphasecond;
    private RotateAnimation rotate;
    private CircleRainView circleRainView;
    private AnimatorSet animatorSet;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity1_main);

        rl_frist=(RelativeLayout)findViewById(R.id.rl_frist);
        rl_second=(RelativeLayout)findViewById(R.id.rl_second);
        circleRainView=(CircleRainView)findViewById(R.id.circleRainView);
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_second.setVisibility(View.VISIBLE);
                alphafirst.start();
                alphasecond.start();
            }
        });


        iv=(ImageView)findViewById(R.id.iv_center);
        ivssfk=(ImageView)findViewById(R.id.ivssfk);

        waveview=(waveView)findViewById(R.id.waveview);
        waveview.setTime(10000,iv);



        //图片实现萤火虫效果
        ivaaaa=(ImageView)findViewById(R.id.ivaaaa);
        List<Animator> list=new ArrayList<>();
        animatorSet=new AnimatorSet();
        ObjectAnimator alpha=ObjectAnimator.ofFloat(ivaaaa,"alpha",0f,1.0f,0f);
        alpha.setRepeatCount(Animation.INFINITE);
        alpha.setDuration(2500);
        list.add(alpha);
        ObjectAnimator scaleX=ObjectAnimator.ofFloat(ivaaaa,"scaleX",1.0f,1.6f,1.0f);
        scaleX.setDuration(2500);
        scaleX.setRepeatCount(Animation.INFINITE);
        list.add(scaleX);
        ObjectAnimator scaleY=ObjectAnimator.ofFloat(ivaaaa,"scaleY",1.0f,1.6f,1.0f);
        scaleY.setDuration(2500);
        scaleY.setRepeatCount(Animation.INFINITE);
        list.add(scaleY);
        animatorSet.playTogether(list);
        animatorSet.start();


        //第一个圆到第二个圆的过渡
        alphafirst=ObjectAnimator.ofFloat(rl_frist,"alpha",1f,0f);
        alphafirst.setDuration(1000);
        alphasecond=ObjectAnimator.ofFloat(rl_second,"alpha",0f,1f);
        alphasecond.setDuration(1000);



        //第一个圆旋转的效果
        rotate=new RotateAnimation(0f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(6000);//设置动画持续时间
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setRepeatCount(Animation.INFINITE);
        circleRainView.startAnimation(rotate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waveview.cancelAnim();
        wavebackview.cancelAnim();
        animatorSet.cancel();
        animatorSet=null;
        rotate.cancel();
        rotate=null;
    }
}
