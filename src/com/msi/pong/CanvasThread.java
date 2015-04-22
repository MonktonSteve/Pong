package com.msi.pong;
	 
import android.graphics.Canvas;
import android.view.SurfaceHolder;
	 
public class CanvasThread extends Thread {
    private SurfaceHolder _surfaceHolder;
    private MyPanel _panel;
    private boolean _run = false;
	 
    public CanvasThread(SurfaceHolder surfaceHolder, MyPanel panel) {
        _surfaceHolder = surfaceHolder;
        _panel = panel;
    }
	 
    public void setRunning(boolean run) {
        _run = run;
    }
	 
    @Override
    public void run() {
        Canvas c;
        while (_run) {
            c = null;
            try {
                c = _surfaceHolder.lockCanvas(null);
                synchronized (_surfaceHolder) {
                    _panel.draw(c);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    _surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}

