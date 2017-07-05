package com.penck.gl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by peng on 2017/7/5.
 */

public class MyGLSurfaceView extends GLSurfaceView {
    MyRender myRender;

    public MyGLSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 3.0 context.
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        myRender = new MyRender();
        setRenderer(myRender);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    //private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private static final float TOUCH_SCALE_FACTOR = 0.005f;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                //subtract, so the cube moves the same direction as your finger.
                //with plus it moves the opposite direction.
                myRender.setX(myRender.getX() - (dx * TOUCH_SCALE_FACTOR));

                float dy = y - mPreviousY;
                myRender.setY(myRender.getY() - (dy * TOUCH_SCALE_FACTOR));
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
