package com.xfeng.opengl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xfeng.opengl.one.TriangleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Class[] clazz = new Class[]{TriangleActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout contianer = (LinearLayout) findViewById(R.id.activity_container);

        for(Class cz : clazz){
            Button button = new Button(this);
            button.setText(cz.getSimpleName());
            button.setTag(cz);
            contianer.addView(button);
            button.setOnClickListener(this);
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, (Class<?>) v.getTag());
        startActivity(intent);
    }
}
