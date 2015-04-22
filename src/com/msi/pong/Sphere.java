package com.msi.pong;

import java.nio.FloatBuffer;
import android.opengl.GLES20;

public class Sphere {
    static private FloatBuffer sphereVertex;
    static float sphere_parms[]=new float[3];
    double mRaduis;
    double mStep;
    float mVertices[];
	private final int mProgram;
    private static double DEG = Math.PI/180;
    int mPoints;
    /**
     * The value of step will define the size of each facet as well as the number of facets
     *  
     * @param radius
     * @param step
     */
    public Sphere( float radius, double step) {
        this.mRaduis = radius;
        this.mStep = step;
        sphereVertex = FloatBuffer.allocate(40000);
        mPoints = build();
        
		// create empty OpenGL ES Program
		mProgram = GLES20.glCreateProgram();


    }
    
    public void draw() {
		GLES20.glUseProgram(mProgram);
        GLES20.glFrontFace(GLES20.GL_CW);
       // GLES20.glEnableClientState(GLES20.GL_VERTEX_ARRAY);
        //GLES20.glVertexPointer(3, GLES20.GL_FLOAT, 0, sphereVertex);
        //GLES20.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        //GLES20.glDrawArrays(GL10.GL_POINTS, 0, mPoints);
        //GLES20.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
    
    private int build() {
        /**
         * x = p * sin(phi) * cos(theta)
         * y = p * sin(phi) * sin(theta)
         * z = p * cos(phi)
         */
        double dTheta = mStep * DEG;
        double dPhi = dTheta;
        int points = 0;
        for(double phi = -(Math.PI/2); phi <= Math.PI/2; phi+=dPhi) {
            //for each stage calculating the slices
            for(double theta = 0.0; theta <= (Math.PI * 2); theta+=dTheta) {
                sphereVertex.put((float) (mRaduis * Math.sin(phi) * Math.cos(theta)) );
                sphereVertex.put((float) (mRaduis * Math.sin(phi) * Math.sin(theta)) );
                sphereVertex.put((float) (mRaduis * Math.cos(phi)) );
                points++;
            }
        }
        sphereVertex.position(0);
        return points;
    }
}