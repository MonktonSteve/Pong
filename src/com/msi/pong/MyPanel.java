package com.msi.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.SweepGradient;
import android.view.MotionEvent;
import android.view.View;

public class MyPanel extends View {
	int color;
	float w, h, ballRadius;
	float lineLength, lineHeighth, lineX;
	float ballX, ballY, xMax, yMax, xMin, yMin, ballSpeedX, ballSpeedY;
	Paint circlePaint = new Paint();
	Paint linePaint = new Paint();
	Paint backPaint = new Paint();
	   
	// For touch inputs - previous touch (x, y)
	private float previousX;
	private float previousY;
	   
    public MyPanel(Context context) {
        super(context);
    	backPaint.setColor(Color.BLUE);
    	circlePaint.setStyle(Paint.Style.FILL);
    	linePaint.setStyle(Paint.Style.FILL);
  	  	
    }
    
    @Override 
    public void onSizeChanged(int _w, int _h, int _oldw, int _oldh) {
        w = _w;
        h = _h;
        xMax = _w - 1;
        yMax = _h - 1;
        lineLength = w/2;
        lineHeighth = 12;
        xMin = 0;
        yMin = 0;
        ballX = w/2;
        ballY = h/2;
        lineX = (w/2);
        ballSpeedX = 2;
        ballSpeedY = 3;
  	  	if(w > h){
  	  		ballRadius = h/5;
  	  	}else{
  	  		ballRadius = w/5;
  	  	}
    	circlePaint.setAntiAlias(true);
    	linePaint.setAntiAlias(true);
    }
    
    // Touch-input handler
    @Override
    public boolean onTouchEvent(MotionEvent event) {
       float currentX = event.getX();
       float currentY = event.getY();
       float deltaX, deltaY;
       float scalingFactor = 5.0f / ((xMax > yMax) ? yMax : xMax);
       switch (event.getAction()) {
          case MotionEvent.ACTION_MOVE:
             // Modify rotational angles according to movement
             deltaX = currentX - previousX;
             deltaY = currentY - previousY;
             ballSpeedX += deltaX * scalingFactor;
             ballSpeedY += deltaY * scalingFactor;
          case MotionEvent.ACTION_DOWN:
              // Modify rotational angles according to movement
              lineX = currentX;
       }
       // Save current x, y
       previousX = currentX;
       previousY = currentY;
       return true;  // Event handled
    }

    @Override
    public void onDraw(Canvas canvas) {
    	canvas.drawRect(0, 0, w, h, backPaint);
    	drawCircle1(canvas);
    	updateCircle();
    	updateLine();
    	invalidate();
    }
    
    private void drawCircle1(Canvas canvas) {
    	int[] circleColors = {Color.RED, Color.GREEN, Color.GREEN, Color.RED};
    	float[] circlePositions ={0f,0.01f,0.99f, 1f};
    	int[] lineColors = {Color.WHITE, Color.BLACK};
    	float[] linePositions ={0f,1f};
    	
    	circlePaint.setShader(new SweepGradient(
    	        ballX, 
    	        ballY, 
    	        circleColors, 
    	        circlePositions));
    	canvas.drawCircle(ballX, ballY, ballRadius, circlePaint);
    	
    	linePaint.setColor(Color.WHITE);
    	linePaint.setStrokeWidth(lineHeighth);
    	canvas.drawLine(lineX, yMax-20, lineX-(lineLength/2), yMax-20, linePaint);
    }
    
    // Detect collision and update the position of the ball.
    private void updateCircle() {
       // Get new (x,y) position
       ballX += ballSpeedX;
       ballY += ballSpeedY;
       // Detect collision and react
       if (ballX + ballRadius > xMax) {
          ballSpeedX = -ballSpeedX;
          ballX = xMax-ballRadius;
       } else if (ballX - ballRadius < xMin) {
          ballSpeedX = -ballSpeedX;
          ballX = xMin+ballRadius;
       }
       if (ballY + ballRadius > yMax) {
          ballSpeedY = -ballSpeedY;
          ballY = yMax - ballRadius;
       } else if (ballY - ballRadius < yMin) {
          ballSpeedY = -ballSpeedY;
          ballY = yMin + ballRadius;
       }
    }
    
    private void updateLine() {
    	
    }
}

