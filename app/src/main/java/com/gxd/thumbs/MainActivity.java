package com.gxd.thumbs;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.gxd.thumbs.redheart.library.LoveViewLayout;

/**
 * 测试
 */
public class MainActivity extends AppCompatActivity {
    private int thumbTimes;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).transparentStatusBar().init();
        LoveViewLayout layout = findViewById(R.id.red_heart);
        layout.addImages(R.mipmap.heart0, R.mipmap.heart1, R.mipmap.heart2,
                R.mipmap.heart3, R.mipmap.heart4, R.mipmap.heart5,
                R.mipmap.heart6, R.mipmap.heart7, R.mipmap.heart8);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thumbTimes ++;
                initCountDownTimer();
            }
        });
    }

    private void initCountDownTimer() {
        if (null == countDownTimer) {
            countDownTimer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //todo 提示
                }

                @Override
                public void onFinish() {
                    Toast.makeText(MainActivity.this, String.valueOf(thumbTimes), Toast.LENGTH_SHORT).show();
                    thumbTimes = 0;
                }
            };
        } else {
            countDownTimer.cancel();
        }
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
    }
}
