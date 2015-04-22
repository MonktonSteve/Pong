
package com.msi.pong;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class Renderer implements GLSurfaceView.Renderer {
	private float xRot;
	private float yRot;
	
	
	public void setxRot(float xRot) {
		this.xRot += xRot;
	}
	public void setyRot(float yRot) {
		this.yRot += yRot;
	}
	public Renderer(Context ctx) {
;		//initialize our 3D triangle here
		//mSphere = new Sphere(1, 25);
		//Initializing the rate counter object
		//This will help us in calculating the frames per second
		//fpsCalculator = new RateCounter(TAG);
		//fpsCalculator.start();
	}
	public void onSurfaceCreated1(GL10 gl, EGLConfig config) {
		//initialize all the things required for openGL configurations
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);    
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	public void xxxonDrawFrame(GL10 gl) {
		//if(fpsCalculator != null)
		//	fpsCalculator.countOnce();
		//write the drawing code needed
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);  
		gl.glLoadIdentity();
		/**
		 * Change this value in z if you want to see the image zoomed in
		 */
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		gl.glRotatef(xRot, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(yRot, 1.0f, 0.0f, 0.0f);
		//mSphere.draw(gl);
	}
	public void xxxonSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) {                      
			height = 1;                        
		}
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();                
		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f,
				100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);     //Select The Modelview Matrix
		gl.glLoadIdentity();                    //Reset The Modelview Matrix
	}
	/**
	 * Used to stop the FPS counter
	 */
	//public void pause() {
	//	if(fpsCalculator != null)
	//		fpsCalculator.stop();
	//}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		
	}
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0.0f,  0.0f,  0.0f,  1.0f);
		
	}

}