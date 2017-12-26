package com.qmz.slidezoomviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private ZoomView zoomView;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoomView=findViewById(R.id.zoom);
        seekBar=findViewById(R.id.seekbar);

        zoomView.setSeekBar(seekBar);
    }
}
