package com.penck.gl.glsl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by peng on 2017/8/31.
 */

public class GLSLSurfaceView extends GLSurfaceView {

    public GLSLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GLSLSurfaceView(Context context) {
        super(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void config() {
        setPreserveEGLContextOnPause(true);
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
       // setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
