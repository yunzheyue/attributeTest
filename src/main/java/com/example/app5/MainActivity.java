package com.example.app5;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ValueAnimator valueAnimator;
    private RelativeLayout rl_container;
    private TextView tv_test, tv_dollar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_dollar = (TextView) findViewById(R.id.tv_dollar);
        rl_container = (RelativeLayout) findViewById(R.id.rl_container);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "休息休息", Toast.LENGTH_SHORT).show();
            }
        });
//        foo7();

        foo9();

    }

    //计时器动画
    private void foo9() {
        valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                tv_dollar.setText("$" + animatedValue);
            }
        });
        valueAnimator.start();
    }

    //布局动画，能够改变在当前view中添加其他的view时候，添加view具有动画效果。
    private void foo7() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1);
        scaleAnimation.setDuration(2000);
        //第二个参数就是延迟时间
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(scaleAnimation, 0.5f);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_REVERSE);//ORDER_REVERSE 反序
        rl_container.setLayoutAnimation(layoutAnimationController);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAnimation(View view) {

//        foo1();
//        foo2();
//        foo3();
        foo4();
//        foo5();
//        foo6();

//        foo8();


    }

    private void foo4() {
        //类似PropertyValueHolder 可以使用AnimatorSet,并且更精确的顺序控制
        ObjectAnimator translationX = ObjectAnimator.ofFloat(tv_test, "translationX", 0, 700);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tv_test, "scaleX", 1f, 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tv_test, "scaleY", 1f, 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationX, scaleX, scaleY);//这是同时播放动画
//        animatorSet.playSequentially(translationX,scaleX,scaleY);//这是按照顺序播放动画
//        animatorSet.play(scaleX).with(translationX).with(scaleY);//这个的效果其实和第一种相同
        animatorSet.play(scaleX).with(translationX).before(scaleY);//这个控制顺序 play()  with()  before()  after()
        animatorSet.setDuration(5000);
        animatorSet.start();
    }

    private void foo8() {


    }

    //通过animate方法直接驱动动画，可以认为是属性动画的一个简写的方式，
    //这样他的属性会随着view的移动而移动。
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void foo6() {
        tv_test.animate()
                .alpha(0)
                .y(500)
                .setDuration(5000)
                .setInterpolator(new AccelerateDecelerateInterpolator())//使用差值器
                .withStartAction(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TAG", "withStartAction----" + Thread.currentThread().getName());
                    }
                }).withEndAction(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "withEndAction----" + Thread.currentThread().getName());
            }
        }).start();

    }

    //可以将属性动画写在xml中，然后在代码中进行调用。
    private void foo5() {

        //创建xml的位置是在animator的文件夹中。
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.animatortest);
        animator.setTarget(tv_test);
        animator.start();
    }

    private void foo3() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 600);
        valueAnimator.setTarget(tv_test);
        valueAnimator.setDuration(2000);
        //ObjectAnimator 继承自 ValueAnimator一般是使用addUpdateListener的监听数值的变化
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这个就是在2000毫秒中，每个时间间隔中的执行到的位置。
                Log.e("TAG", "animation====" + animation.getAnimatedValue());
            }
        });
        valueAnimator.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void foo1() {
        //这是比较low的方法，一个一个的写动画 ，如果是对一个对象作用多个动画，那么就是要用PropertyValuesHolder
//        ObjectAnimator translationX = ObjectAnimator.ofFloat(tv_test, "translationX", 100, 500);
//        translationX.setDuration(2000);
//        translationX.start();

//        ObjectAnimator rotationX = ObjectAnimator.ofFloat(tv_test, "rotationY", 180, 270);
//        rotationX.setDuration(2000);
//        rotationX.start();
//
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tv_test, "scaleY", 1, 0);
        //设置监听的时候一定要在动画启动之前进行设置，否则就不能监听start的回调。
        scaleX.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {

            }

            @Override
            public void onAnimationResume(Animator animation) {

            }
        });
        //可以重写其中的需要的回调方法，并不一定所有的都要重写。
        scaleX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                Log.e("TAG", "onAnimationCancel");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.e("TAG", "onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                Log.e("TAG", "onAnimationRepeat");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("TAG", "onAnimationStart");
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
                Log.e("TAG", "onAnimationPause");
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
                Log.e("TAG", "onAnimationResume");
            }
        });
        scaleX.setDuration(2000);
        scaleX.start();


        //使用PropertyValuesHollder对一个view，操作多个属性
//        PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX", 100, 1000);
//        PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("rotationY", 120, 234);
//        PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("scaleY", 1, 0);
//        ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(tv_test,pvh1, pvh2, pvh3);
//        valueAnimator.setDuration(5000).start();


    }

    //根据自定义的属性方法，我们亦可以制作自己的动画效果。这里的propertyName就是我们定义的getXXX()  setXXX(),方法，
    //动画效果就是不断的进行get set 来达到效果，后面的可变参数就是动画的执行时间内的渐变的过程，比如下面就是在3000毫秒中
    //调用setWidth()方法传值从0到100。必须使用get() set()方法才可以
    private void foo2() {
        WrapperView wrapperView = new WrapperView(tv_test);

//        ObjectAnimator.ofFloat(wrapperView, "width", 0, 100).setDuration(3000).start();

        //或者也可以使用设置监听的方式进行更改，这样就不需要设置propertyname,这个和上面的效果是相同的。
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100).setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                tv_test.getLayoutParams().width = (int) animatedValue;
                tv_test.requestLayout();
            }
        });

        valueAnimator.start();
    }

    public void startAnimation1(View view) {
        //属性动画的暂停
        valueAnimator.pause();
    }

    public void startAnimation2(View view) {
//        属性动画的恢复
        valueAnimator.resume();
    }

    class ObjectTypeEvaluator implements TypeEvaluator {
        //fraction:表示动画完成度，也就是动画执行的进度  根据它来计算当前的动画的值
        //startValue: 动画的开始值
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {

            return null;
        }
    }
}
