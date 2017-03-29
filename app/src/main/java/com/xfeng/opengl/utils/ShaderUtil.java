package com.xfeng.opengl.utils;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lixianfeng on 2017/3/29.
 */

public class ShaderUtil {

    /**
     * 创建Shader-->加载Shader资源-->编译Shader-->查询编译状态
     *
     * @param shaderType
     * @param source
     * @return
     */
    public static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);

            int[] compileId = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileId, 0);
            if (compileId[0] == 0) {
                GLES20.glDeleteShader(shader);
                shader = 0;
            }

        }
        return shader;
    }

    /**
     * 加载Shader-->创建program-->attach各Shader-->链接program
     * @param vertexSource
     * @param fragmentSource
     * @return
     */
    public static int createProgram(String vertexSource, String fragmentSource) {

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
        int fragShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (fragShader == 0) {
            return 0;
        }
        int program = GLES20.glCreateProgram();
        if (program != 0) {

            GLES20.glAttachShader(program, vertexShader);
            checkGLError("glAttachShader");
            GLES20.glAttachShader(program, fragShader);
            checkGLError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {

                GLES20.glDeleteProgram(program);
                program = 0;
            }

        }
        return program;
    }

    public static void checkGLError(String op){
        int error;
        while((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR){
            Log.e("OPENGL_ES_ERROR", op + ": glerror " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    public static String loadFromAssetsFile(String fname, Resources r){
        String result = null;

        try {
            InputStream in = r.getAssets().open(fname);
            int ch = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((ch = in.read()) != -1){
                baos.write(ch);
            }

            byte[] buffer = baos.toByteArray();
            baos.close();
            in.close();
            result = new String(buffer, "utf-8");
            result = result.replace("\\r\\n", "\\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
