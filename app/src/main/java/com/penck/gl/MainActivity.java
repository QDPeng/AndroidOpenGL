package com.penck.gl;

import android.app.NativeActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.penck.gl.view.CubeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.GLSurfaceView:
                startActivity(new Intent(this, CubeActivity.class));
                break;
            case R.id.native_game:
                //play endless tunnel game
                startActivity(new Intent(this, NativeActivity.class));
                break;
            case R.id.gl_test:
                //play endless tunnel game
                startActivity(new Intent(this, GltestActivity.class));
                break;
        }
    }
}
