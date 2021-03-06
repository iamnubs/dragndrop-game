package com.aulia.belajariqra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        if (getIntent().hasExtra("message")) {
            Toast.makeText(this, getIntent().getStringExtra("message"), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ll_iqra1)
    void iqra1() {
        startActivity(
                new Intent(this, PilihActivity.class)
                        .putExtra("level", 1)
        );
    }
}
