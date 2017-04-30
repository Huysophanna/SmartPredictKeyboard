package com.arcanetecher.smartpredictkeyboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int unicode = 0x1F60A;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);
        TextView emoji = (TextView)findViewById(R.id.emoji);
        emoji.setText(getEmoji(unicode) + getEmoji(unicode) + getEmoji(unicode));
    }

    public String getEmoji(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
