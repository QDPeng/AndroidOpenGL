package com.penck.gl.glsl.readerer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.penck.gl.R;
import com.penck.gl.glsl.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by peng on 2017/8/31.
 */

public class BackgroundReader {
    private static final int COORDS_PER_VERTEX = 3;
    private static final int TEXCOORDS_PER_VERTEX = 2;
    private static final int FLOAT_SIZE = 4;

    private FloatBuffer mQuadVertices;
    private FloatBuffer mQuadTexCoord;
    private FloatBuffer mQuadTexCoordTransformed;

    private int mQuadProgram;
    private int mQuadPositionHandle;
    private int mQuadTexCoordHandle;
    private int mTextureId = -1;
    // private int mTextureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
    public static final float[] QUAD_COORDS = new float[]{
            -1.0f, -1.0f, 0.0f,
            -1.0f, +1.0f, 0.0f,
            +1.0f, -1.0f, 0.0f,
            +1.0f, +1.0f, 0.0f,
    };

    public static final float[] QUAD_TEXCOORDS = new float[]{
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
    };
    private Context context;

    public BackgroundReader(Context context) {
        this.context = context;
        create();
    }

    private void create() {
        // Generate the background texture.
        int textures[] = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        mTextureId = textures[0];
//        GLES20.glBindTexture(mTextureTarget, mTextureId);
//        GLES20.glTexParameteri(mTextureTarget, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
//        GLES20.glTexParameteri(mTextureTarget, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
//        GLES20.glTexParameteri(mTextureTarget, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
//        GLES20.glTexParameteri(mTextureTarget, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        int numVertices = 4;
        if (numVertices != QUAD_COORDS.length / COORDS_PER_VERTEX) {
            throw new RuntimeException("Unexpected number of vertices in BackgroundRenderer.");
        }

        ByteBuffer bbVertices = ByteBuffer.allocateDirect(QUAD_COORDS.length * FLOAT_SIZE);
        bbVertices.order(ByteOrder.nativeOrder());
        mQuadVertices = bbVertices.asFloatBuffer();
        mQuadVertices.put(QUAD_COORDS);
        mQuadVertices.position(0);

        ByteBuffer bbTexCoords = ByteBuffer.allocateDirect(
                numVertices * TEXCOORDS_PER_VERTEX * FLOAT_SIZE);
        bbTexCoords.order(ByteOrder.nativeOrder());
        mQuadTexCoord = bbTexCoords.asFloatBuffer();
        mQuadTexCoord.put(QUAD_TEXCOORDS);
        mQuadTexCoord.position(0);

        ByteBuffer bbTexCoordsTransformed = ByteBuffer.allocateDirect(
                numVertices * TEXCOORDS_PER_VERTEX * FLOAT_SIZE);
        bbTexCoordsTransformed.order(ByteOrder.nativeOrder());
        mQuadTexCoordTransformed = bbTexCoordsTransformed.asFloatBuffer();

        int vertexShader = ShaderUtil.loadGLShader(context, GLES20.GL_VERTEX_SHADER, R.raw.screenquad_vertex);
        int fragmentShader = ShaderUtil.loadGLShader(context, GLES20.GL_FRAGMENT_SHADER, R.raw.screenquad_fragment_oes);
        mQuadProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mQuadProgram, vertexShader);
        GLES20.glAttachShader(mQuadProgram, fragmentShader);
        GLES20.glLinkProgram(mQuadProgram);
        GLES20.glUseProgram(mQuadProgram);
        ShaderUtil.checkGLError("Program creation");
        mQuadPositionHandle = GLES20.glGetAttribLocation(mQuadProgram, "a_Position");
        mQuadTexCoordHandle = GLES20.glGetAttribLocation(mQuadProgram, "a_TexCoord");
        ShaderUtil.checkGLError("Program parameters");
    }

    /**
     * Draws the background image
     */
    public void draw() {
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthMask(false);
        //GLES20.glBindRenderbuffer(mTextureTarget, mTextureId);
        GLES20.glUseProgram(mQuadProgram);
        // Set the vertex positions.
        GLES20.glVertexAttribPointer(mQuadPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mQuadVertices);
        // Set the texture coordinates.
        GLES20.glVertexAttribPointer(mQuadTexCoordHandle, TEXCOORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, 0, mQuadTexCoordTransformed);
        // Enable vertex arrays
        GLES20.glEnableVertexAttribArray(mQuadPositionHandle);
        GLES20.glEnableVertexAttribArray(mQuadTexCoordHandle);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
//        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
//        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
//        // Disable vertex arrays
        GLES20.glDisableVertexAttribArray(mQuadPositionHandle);
        GLES20.glDisableVertexAttribArray(mQuadTexCoordHandle);

        // Restore the depth state for further drawing.
        GLES20.glDepthMask(true);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        ShaderUtil.checkGLError("Draw");


    }


}
