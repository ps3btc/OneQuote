package hatchingamazing.labs.onequote;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

// The QuoteHead class ...
public class QuoteHead extends IntentService {
    private WindowManager windowManager;
    private View appHead;
    private String messageToRemember;

    public QuoteHead() {
        super("HelloIntentService");
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (appHead != null) windowManager.removeView(appHead);
        }
    };

    public void CreateAppHead() {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        appHead = vi.inflate(R.layout.quote_head, null);
        TextView textView = (TextView) appHead.findViewById(R.id.myImageViewText);
        if (messageToRemember == null) {
            messageToRemember = "default";
        }
        textView.setText(messageToRemember);
        TextView textViewX = (TextView) appHead.findViewById(R.id.myImageViewTextX);
        textViewX.setOnClickListener(onClickListener);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        windowManager.addView(appHead, params);

        try {
            appHead.setOnTouchListener(new View.OnTouchListener() {
                private WindowManager.LayoutParams paramsF = params;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(appHead, paramsF);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            // Do something to handle the exception
        }

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        messageToRemember = intent.getStringExtra("KEY");
        CreateAppHead();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (appHead != null) windowManager.removeView(appHead);
        CreateAppHead();
    }
}