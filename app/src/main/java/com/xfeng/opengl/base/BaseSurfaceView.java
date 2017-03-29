package com.xfeng.opengl.base;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lixianfeng on 2017/3/29.
 */

public abstract class BaseSurfaceView extends GLSurfaceView {

    private Renderer mRenderer;
    private MyThread mThread;

    public BaseSurfaceView(Context context) {
        super(context);

        mRenderer = new MyRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    class MyRenderer implements Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0, 0, 0, 1f);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            mThread = new MyThread();
            mThread.start();

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);

        }

        @Override
        public void onDrawFrame(GL10 gl) {

            drawFrame();
        }
    }

    class MyThread extends Thread {

        public boolean running = false;

        @Override
        public void run() {
            running = true;
            while (running) {

                logic();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    protected abstract void drawFrame();
    protected abstract void logic();

}
