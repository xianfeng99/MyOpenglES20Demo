package com.xfeng.opengl.one;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import com.xfeng.opengl.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by lixianfeng on 2017/3/29.
 */

public class Triangle {

    FloatBuffer mVertexBuffer;
    FloatBuffer mColorBuffer;

    int programId;
    int aPositionHandle;
    int aColorHandle;
    int uMVPMatrixHandle;

    public Triangle(Resources resources){
        loadVertexData();
        initShader(resources);
    }

    private void loadVertexData(){

        int vCount = 3;
        final float size = 0.2f;
        float vertices[] = new float[]{
                -4 * size, 0, 0,
                4 * size, 0, 0,
                0, 4 * size, 0
        };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        float colors[] = new float[]{
          1, 1, 1, 0,
                0, 0, 1, 0,
                0, 1, 0, 0
        };
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);

    }

    private void initShader(Resources resources){
        String vertexShader = ShaderUtil.loadFromAssetsFile("one/vertex.gsl", resources);
        String fragShader = ShaderUtil.loadFromAssetsFile("one/frag.gsl", resources);

        programId = ShaderUtil.createProgram(vertexShader, fragShader);
        aPositionHandle = GLES20.glGetAttribLocation(programId, "aPosition");
        aColorHandle = GLES20.glGetAttribLocation(programId, "aColor");
        uMVPMatrixHandle = GLES20.glGetUniformLocation(programId, "uMVPMatrix");
    }

    public void draw(){
        GLES20.glUseProgram(programId);

        GLES20.glUniformMatrix4fv(uMVPMatrixHandle, 1, false, new float[16], 0);
        GLES20.glVertexAttribPointer(aPositionHandle, 3, GLES20.GL_FLOAT, false, 3*4, mVertexBuffer);
        GLES20.glVertexAttribPointer(aColorHandle, 3, GLES20.GL_FLOAT, false, 3*4, mColorBuffer);
        GLES20.glEnableVertexAttribArray(aPositionHandle);
        GLES20.glEnableVertexAttribArray(aColorHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

        Log.e("Triangle-draw", "---draw");
    }



}
