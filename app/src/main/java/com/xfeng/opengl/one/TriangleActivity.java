package com.xfeng.opengl.one;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by lixianfeng on 2017/3/29.
 */

public class TriangleActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        MyTriangleView view = new MyTriangleView(this);
        setContentView(view);
    }
}
