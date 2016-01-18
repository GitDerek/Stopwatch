package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends Activity {

    private int seconds = 0;  //  記綠經歷的秒數
    private boolean running = false; // 使用 running 來紀綠是否正在計時

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
        
        runTimer(); // 使用獨立的方法來更新碼表，並且在 activity 裡建立時啟動它
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { // Activity 被銷毀前會被執行
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }

    // 當 Start 按錄被點擊時，開始碼表計時
    public void onClickStart(View view){
        running = true;
    }

    // 當 Sttop 按錄被點擊時，停止碼表計時
    public void onClickStop(View view){
        running = false;
    }

    // 當 Reset 按錄被點擊時，停止碼表計時並歸零
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    // 設定計時器上的秒數
    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view); // Why need to "final" ?
        final Handler handler = new Handler(); // 建立新的 Handler

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);

                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000); // 發佈 Runnable 裡的程式碼，讓它在1,000毫秒之後再一次被執行。
                                                // 因為這一行程式碼被包含在 Runnable 的 run() 方法裡，它將持續被呼叫。
            }
        });
    }
}
