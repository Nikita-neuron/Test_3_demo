package com.example.test3_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    Thread myThread;

    float x = -1000;
    float y = -1000;
    float r = 0;

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        myThread = new MyThread();

        myThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        myThread.interrupt();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        r = 0;

        return super.onTouchEvent(event);
    }

    class MyThread extends Thread {

        public void run() {
            while (!isInterrupted()) {
                Canvas canvas = getHolder().lockCanvas();
                Paint paint = new Paint();
                paint.setColor(Color.YELLOW);

                if (canvas != null) {
                    canvas.drawColor(Color.BLUE);

                    canvas.drawCircle(x, y, r, paint);

                    r += 5;
                    getHolder().unlockCanvasAndPost(canvas);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
