package com.xfeng.opengl.one;

import android.content.Context;

import com.xfeng.opengl.base.BaseSurfaceView;

/**
 * Created by lixianfeng on 2017/3/29.
 */

public class MyTriangleView extends BaseSurfaceView {

    Triangle triangle;

    public MyTriangleView(Context context) {
        super(context);
        triangle = new Triangle(context.getResources());
    }

    @Override
    protected void drawFrame() {
        triangle.draw();
    }

    @Override
    protected void logic() {

    }
}
