package com.penck.gl.view;

import android.opengl.GLES30;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by peng on 2017/7/5.
 * This code actually will draw a cube.
 * Some of the code is used from https://github.com/christopherperry/cube-rotation
 * and changed up to opengl 3.0
 */
public class Cube {
    private int mProgramObject;
    private int mMVPMatrixHandle;
    private int mColorHandle;
    private FloatBuffer mVertices;
    //initial size of the cube.  set here, so it is easier to change later.
    private float size = 0.4f;

    //this is the initial data, which will need to translated into the mVertices variable in the consturctor.
    private float[] mVerticesData = new float[]{
            ////////////////////////////////////////////////////////////////////
            // FRONT
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            -size, size, size, // top-left
            -size, -size, size, // bottom-left
            size, -size, size, // bottom-right
            // Triangle 2
            size, -size, size, // bottom-right
            size, size, size, // top-right
            -size, size, size, // top-left
            ////////////////////////////////////////////////////////////////////
            // BACK
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            -size, size, -size, // top-left
            -size, -size, -size, // bottom-left
            size, -size, -size, // bottom-right
            // Triangle 2
            size, -size, -size, // bottom-right
            size, size, -size, // top-right
            -size, size, -size, // top-left

            ////////////////////////////////////////////////////////////////////
            // LEFT
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            -size, size, -size, // top-left
            -size, -size, -size, // bottom-left
            -size, -size, size, // bottom-right
            // Triangle 2
            -size, -size, size, // bottom-right
            -size, size, size, // top-right
            -size, size, -size, // top-left
            ////////////////////////////////////////////////////////////////////
            // RIGHT
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            size, size, -size, // top-left
            size, -size, -size, // bottom-left
            size, -size, size, // bottom-right
            // Triangle 2
            size, -size, size, // bottom-right
            size, size, size, // top-right
            size, size, -size, // top-left

            ////////////////////////////////////////////////////////////////////
            // TOP
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            -size, size, -size, // top-left
            -size, size, size, // bottom-left
            size, size, size, // bottom-right
            // Triangle 2
            size, size, size, // bottom-right
            size, size, -size, // top-right
            -size, size, -size, // top-left
            ////////////////////////////////////////////////////////////////////
            // BOTTOM
            ////////////////////////////////////////////////////////////////////
            // Triangle 1
            -size, -size, -size, // top-left
            -size, -size, size, // bottom-left
            size, -size, size, // bottom-right
            // Triangle 2
            size, -size, size, // bottom-right
            size, -size, -size, // top-right
            -size, -size, -size // top-left
    };

    private float colorcyan[] = CubeColor.cyan();
    private float colorblue[] = CubeColor.blue();
    private float colorred[] = CubeColor.red();
    private float colorgray[] = CubeColor.gray();
    private float colorgreen[] = CubeColor.green();
    private float coloryellow[] = CubeColor.yellow();

    //vertex shader code
    private final String vShaderStr = "#version 300 es    \n"
            + "uniform mat4 uMVPMatrix;     \n"
            + "in vec4 vPosition;           \n"
            + "void main()                  \n"
            + "{                            \n"
            + "   gl_Position = uMVPMatrix * vPosition;  \n"
            + "}                            \n";
    //fragment shader code.
    private final String fShaderStr = "#version 300 es\n"
            + "precision mediump float;	\n"
            + "uniform vec4 vColor;	 	\n"
            + "out vec4 fragColor;	 	\n"
            + "void main()              \n"
            + "{                        \n"
            + "  fragColor = vColor;    \n"
            + "}                        \n";

    String TAG = "Cube";

    public Cube() {
        //first setup the mVertices correctly.
        mVertices = ByteBuffer
                .allocateDirect(mVerticesData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVerticesData);
        mVertices.position(0);

        //setup the shaders
        int vertexShader;
        int fragmentShader;
        int programObject;
        int[] linked = new int[1];
        // Load the vertex/fragment shaders
        vertexShader = getGlShader(GLES30.GL_VERTEX_SHADER, vShaderStr);
        fragmentShader = getGlShader(GLES30.GL_FRAGMENT_SHADER, fShaderStr);
        // Create the program object
        programObject = GLES30.glCreateProgram();
        if (programObject == 0) {
            Log.e(TAG, "So some kind of error, but what?");
            return;
        }
        //attach shader to programObj
        GLES30.glAttachShader(programObject, vertexShader);
        GLES30.glAttachShader(programObject, fragmentShader);
        // Bind vPosition to attribute 0
        GLES30.glBindAttribLocation(programObject, 0, "vPosition");
        // Link the program
        GLES30.glLinkProgram(programObject);
        // Check the link status
        GLES30.glGetProgramiv(programObject, GLES30.GL_LINK_STATUS, linked, 0);
        if (linked[0] == 0) {
            Log.e(TAG, "Error linking program:");
            Log.e(TAG, GLES30.glGetProgramInfoLog(programObject));
            GLES30.glDeleteProgram(programObject);
            return;
        }
        // Store the program object
        mProgramObject = programObject;

        //now everything is setup and ready to draw.
    }

    /**
     * draw cube by model*view*projection matrix
     *
     * @param mvpMatrix
     */
    public void draw(float[] mvpMatrix) {
        // Use the program object
        GLES30.glUseProgram(mProgramObject);
        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgramObject, "uMVPMatrix");
        checkGlError("glGetUniformLocation");
        // get handle to fragment shader's vColor member
        mColorHandle = GLES30.glGetUniformLocation(mProgramObject, "vColor");
        // Apply the projection and view transformation
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        checkGlError("glUniformMatrix4fv");

        int VERTEX_POS_INDX = 0;
        mVertices.position(VERTEX_POS_INDX);//just in case.  We did it already though.
        //add all the points to the space, so they can be correct by the transformations.
        //would need to do this even if there were no transformations actually.
        GLES30.glVertexAttribPointer(VERTEX_POS_INDX, 3, GLES30.GL_FLOAT, false, 0, mVertices);
        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDX);

        //Now we are ready to draw the cube finally.
        int startPos = 0;
        int verticesPerface = 6;
        //draw front face
        GLES30.glUniform4fv(mColorHandle, 1, colorblue, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, verticesPerface);
        startPos += verticesPerface;

        //draw back face
        GLES30.glUniform4fv(mColorHandle, 1, colorcyan, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, verticesPerface);
        startPos += verticesPerface;

        //draw left face
        GLES30.glUniform4fv(mColorHandle, 1, colorred, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, verticesPerface);
        startPos += verticesPerface;

        //draw right face
        GLES30.glUniform4fv(mColorHandle, 1, colorgray, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, verticesPerface);
        startPos += verticesPerface;

        //draw top face
        GLES30.glUniform4fv(mColorHandle, 1, colorgreen, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, verticesPerface);
        startPos += verticesPerface;

        //draw bottom face
        GLES30.glUniform4fv(mColorHandle, 1, coloryellow, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, startPos, verticesPerface);
        //last face, so no need to increment.
    }


    /**
     * Create a shader object, load the shader source, and compile the shader.
     *
     * @param type
     * @param shaderCode
     * @return
     */
    public static int getGlShader(int type, String shaderCode) {
        int shader = 0;
        int[] compiled = new int[1];
        // Create the shader object
        shader = GLES30.glCreateShader(type);
        if (shader == 0) {
            return 0;
        }
        // Load the shader source
        GLES30.glShaderSource(shader, shaderCode);
        // Compile the shader
        GLES30.glCompileShader(shader);
        // Check the compile status
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            return 0;
        }
        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     * <p>
     * <pre>
     * mColorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     * <p>
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}