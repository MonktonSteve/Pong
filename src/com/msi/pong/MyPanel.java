package com.msi.pong;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class MyPanel extends View {
	int color;
	float w, h, ballRadius;
	float lineLength, lineHeighth, lineX;
	float xMax, yMax, xMin, yMin;
	float ballX, ballY, ballSpeedX, ballSpeedY;
	float bullsEyeAngle;
	int score;
	Paint bullsEyePaint = new Paint();
	Paint circlePaint = new Paint();
	Paint linePaint = new Paint();
	Paint backPaint = new Paint();
	MediaPlayer mp1, mp2;
	boolean boomed;
	boolean yesno = false;
	boolean play_again = true;
	Context _context;
	   
	// For touch inputs - previous touch (x, y)
	private float previousX;
	private float previousY;
	   
    public MyPanel(Context context) {
        super(context);
        _context = context;
    	backPaint.setColor(Color.BLUE);
    	bullsEyePaint.setColor(Color.BLACK);
    	bullsEyePaint.setStrokeWidth(3);
    	circlePaint.setStyle(Paint.Style.FILL);
    	linePaint.setStyle(Paint.Style.FILL);

		mp1 = MediaPlayer.create(context, R.raw.lock);
		mp1.setLooping(false);
		//mp2 = MediaPlayer.create(context, R.raw.neon);
    }
    
    private void _init() {
        ballX = w/2;
        ballY = h/2;
        lineX = (w/2);
        ballSpeedX = -2;
        ballSpeedY = -3;
        bullsEyeAngle = 0;
    	boomed = false;
    }
    @Override 
    public void onSizeChanged(int _w, int _h, int _oldw, int _oldh) {
        w = _w;
        h = _h;
        xMax = _w - 1;
        yMax = _h - 1;
        lineLength = w/4;
        lineHeighth = 12;
        xMin = 0;
        yMin = 0;
        ballX = w/2;
        ballY = h/2;
        lineX = (w/2);
        ballSpeedX = -2;
        ballSpeedY = -3;
        bullsEyeAngle = 0;
        score = 0;
  	  	if(w > h){
  	  		ballRadius = h/5;
  	  	}else{
  	  		ballRadius = w/5;
  	  	}
    	circlePaint.setAntiAlias(true);
    	linePaint.setAntiAlias(true);
    	boomed = false;
    }

    @Override
    public void onDraw(Canvas canvas) {
    	if (yesno == false) {
    		if (boomed == false) {
    			updateGame(canvas);
        		invalidate();
        	} else {
    			showScore(canvas);
        	}
    	}
    	if (play_again == false) {
    		System.exit(1);
    	}
    }
    
    private void updateGame(Canvas canvas) {
    	canvas.drawRect(0, 0, w, h, backPaint);
    	drawCircle(canvas);
    	drawLine(canvas);
    	updateCircle(canvas);
    	updateLine();    	
    }
    
    private void drawCircle(Canvas canvas) {
    	int[] circleColors = {Color.YELLOW, Color.MAGENTA, Color.GRAY, Color.CYAN, Color.RED, Color.GREEN};
    	float[] circlePositions ={0f,0.20f, 0.4F, 0.6f, 0.80f, 1f};    	
 //   	circlePaint.setShader(new SweepGradient(
 //   	        ballX, 
 //   	        ballY, 
 //   	        circleColors, 
 //   	        circlePositions));
    	circlePaint.setShader(new RadialGradient(
    	        ballX, 
    	        ballY, 
    	        ballRadius,
    	        circleColors, 
    	        circlePositions,
    	        Shader.TileMode.CLAMP));
    	canvas.drawCircle(ballX, ballY, ballRadius, circlePaint);
    	drawBullsEye(canvas);
    }
    
    private void drawBullsEye(Canvas canvas) {
    	if (bullsEyeAngle < 360) {
    		bullsEyeAngle += 1;
    	} else {
    		bullsEyeAngle = 0;
    	}
    	drawEyeA(canvas);
    	drawEyeB(canvas);
    }
    
    private void drawEyeA(Canvas canvas) {
    	float xF = (float) Math.sin(Math.toRadians(bullsEyeAngle));
    	float yF = (float) Math.cos(Math.toRadians(bullsEyeAngle));
    	float xStart = ballX - (ballRadius * xF);
    	float xEnd = ballX + (ballRadius * xF);
    	float yStart = ballY - (ballRadius * yF);
    	float yEnd = ballY + (ballRadius * yF);
    	canvas.drawLine(xStart, yStart, xEnd, yEnd, bullsEyePaint);
    }
    
    private void drawEyeB(Canvas canvas) {
    	float bAngle = bullsEyeAngle + 90;
    	if (bAngle > 360) {
    		bAngle -= 360;
    	}
    	float xF = (float) Math.sin(Math.toRadians(bAngle));
    	float yF = (float) Math.cos(Math.toRadians(bAngle));
    	float xStart = ballX - (ballRadius * xF);
    	float xEnd = ballX + (ballRadius * xF);
    	float yStart = ballY - (ballRadius * yF);
    	float yEnd = ballY + (ballRadius * yF);
    	canvas.drawLine(xStart, yStart, xEnd, yEnd, bullsEyePaint);
    }

    private void drawLine(Canvas canvas) {    	
    	linePaint.setColor(Color.WHITE);
    	linePaint.setStrokeWidth(lineHeighth);
    	canvas.drawLine(lineX-(lineLength/2), yMax-20, lineX+(lineLength/2), yMax-20, linePaint);
    }   
    
    // Detect collision and update the position of the ball.
    private void updateCircle(Canvas canvas) {
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
       if (((ballY + ballRadius) >= (yMax-(20+lineHeighth)))
       &&  ((ballX > (lineX-(lineLength/2)))
       &&   (ballX < (lineX+(lineLength/2))))) {
             ballSpeedY = -ballSpeedY;
             ballY = (yMax-(20+lineHeighth)) - ballRadius;
             score += 1;
             mp1.start();
       } else if (ballY + ballRadius > yMax) {
    	   boom(canvas);
       } else if (ballY - ballRadius < yMin) {
          ballSpeedY = -ballSpeedY;
          ballY = yMin + ballRadius;
       }
    }
    
    private void updateLine() {
    	
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
    
    private void boom(Canvas canvas) {
    	int[] circleColors = {Color.RED, Color.RED};
    	float[] circlePositions ={0f,1f};
    	
    	circlePaint.setShader(new SweepGradient(
    	        ballX, 
    	        ballY, 
    	        circleColors, 
    	        circlePositions));
    	canvas.drawCircle(ballX, ballY, ballRadius, circlePaint);
    	boomed = true;
	}
    
    private void showScore(Canvas canvas) {
    	Paint paint = new Paint();
    	
		//mp2.start();
    	circlePaint.setTextSize(30f);
    	circlePaint.setAntiAlias(true);
    	circlePaint.setDither(true);
    	circlePaint.setSubpixelText(true);
    	circlePaint.setTextAlign(Align.CENTER);
    	circlePaint.setColor(Color.CYAN);
    	String scoreText = "Your score is : " + Integer.toString(score);
    	canvas.drawText(scoreText, w/2, h/2, circlePaint);
    	//mp2.release();
    	yesno = true;
    	AlertDialog.Builder builder = new AlertDialog.Builder(_context);
    	builder.setMessage("Do you want to play again?").setPositiveButton("Yes", dialogClickListener)
    	    .setNegativeButton("No", dialogClickListener).show();
    }
    
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	            //Yes button clicked
	        	play_again = true;
	        	yesno = false;
	    		_init();
	        	invalidate();
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	        	play_again = false;
	        	invalidate();
	            break;
	        }
	    }
	};
}

