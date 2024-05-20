package com.gxd.thumbs.redheart.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LoveViewLayout extends RelativeLayout {

    private OnCallBack onCallBack;
    private final Context context;
    private final float[] num = new float[]{-35f, -25f, 0f, 25f, 35f};
    private final long[] mHits = new long[2];
    private final List<Integer> resIds;
    private final Random mRandom = new Random();

    public LoveViewLayout(Context context) {
        this(context, null);
    }

    public LoveViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.resIds = new ArrayList<>();
    }

    public void addImageList(List<Integer> resIds) {
        this.resIds.addAll(resIds);
    }

    public void addImages(Integer... resIds) {
        this.addImageList(Arrays.asList(resIds));
    }

    //监听点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
            mHits[mHits.length - 1] = SystemClock.uptimeMillis();
            if (mHits[0] >= (SystemClock.uptimeMillis() - 200)) {
                final ImageView imageView = new ImageView(context);
                //在事件产生的坐标处添加心形组件
                LayoutParams layoutParams = new LayoutParams(300, 300);
                layoutParams.leftMargin = (int) (event.getX() - 150);
                layoutParams.topMargin = (int) (event.getY() - 300);
                int imageRes = Math.abs(resIds.get(mRandom.nextInt(resIds.size())));
                imageView.setImageResource(imageRes);
                imageView.setLayoutParams(layoutParams);
                imageView.bringToFront();
                addView(imageView);

                final ImageView defImageView = new ImageView(context);
                LayoutParams defParams = new LayoutParams(200, 200);
                defParams.leftMargin = (int) (event.getX() - 100);
                defParams.topMargin = (int) (event.getY() - 200);
                defImageView.setImageResource(R.drawable.ic_heart);
                defImageView.setLayoutParams(defParams);
                defImageView.bringToFront();
                addView(defImageView);

                //为组件添加动画
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ObjectAnimator.ofFloat(imageView, ALPHA, 0.2f, 1f),
                        ObjectAnimator.ofFloat(imageView, SCALE_X, 0.2f, 1f),
                        ObjectAnimator.ofFloat(imageView, SCALE_Y, 0.2f, 1f));
                animatorSet.setInterpolator(new LinearInterpolator());
                animatorSet.setDuration(500);
                animatorSet.start();
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        removeViewInLayout(imageView);
                        removeViewInLayout(defImageView);
                    }
                });

                if (onCallBack != null) {
                    onCallBack.callback();
                }
            }
        }

        return super.onTouchEvent(event);
    }

    private ObjectAnimator scaleAni(View view, String propertyName, Float from, Float to, Long time, Long delay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, propertyName, from, to);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setStartDelay(delay);
        objectAnimator.setDuration(time);
        return objectAnimator;
    }

    private ObjectAnimator translationX(View view, Float from, Float to, Long time, Long delayTime) {
        ObjectAnimator ani = ObjectAnimator.ofFloat(view, "translationX", from, to);
        ani.setInterpolator(new LinearInterpolator());
        ani.setStartDelay(delayTime);
        ani.setDuration(time);
        return ani;
    }

    private ObjectAnimator translationY(View view, Float from, Float to, Long time, Long delayTime) {
        ObjectAnimator ani = ObjectAnimator.ofFloat(view, "translationY", from, to);
        ani.setInterpolator(new LinearInterpolator());
        ani.setStartDelay(delayTime);
        ani.setDuration(time);
        return ani;
    }

    private ObjectAnimator alphaAni(View view, Float from, Float to, Long time, Long delayTime) {
        ObjectAnimator ani = ObjectAnimator.ofFloat(view, "alpha", from, to);
        ani.setInterpolator(new LinearInterpolator());
        ani.setStartDelay(delayTime);
        ani.setDuration(time);
        return ani;
    }

    private ObjectAnimator rotation(View view, Long time, Long delayTime, Float values) {
        ObjectAnimator ani = ObjectAnimator.ofFloat(view, "rotation", values);
        ani.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return 0;
            }
        });
        ani.setStartDelay(delayTime);
        ani.setDuration(time);
        return ani;
    }

    public void setCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    public interface OnCallBack {
        void callback();
    }
}
