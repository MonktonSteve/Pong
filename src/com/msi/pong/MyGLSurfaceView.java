package com.msi.pong;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;




class MyGLSurfaceView extends GLSurfaceView {
	private final MyGLRenderer mRenderer;

	public MyGLSurfaceView(Context context) {
		super(context);
		setEGLContextClientVersion(2);
		mRenderer = new MyGLRenderer();
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	public class MyGLRenderer implements GLSurfaceView.Renderer {

		private Sphere mSphere;

		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

			mSphere.draw();
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			mSphere = new Sphere(0, 0);			
		}

	}
}

