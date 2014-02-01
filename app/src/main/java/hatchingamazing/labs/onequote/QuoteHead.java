package hatchingamazing.labs.onequote;

import hatchingamazing.labs.onequote.R;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

// The QuoteHead class ...
public class QuoteHead extends Service {
    private WindowManager windowManager;
    private View appHead;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
        textView.setText("there's an image above me");
        Button okButton = (Button) appHead.findViewById(R.id.dismissButton);
        if (okButton != null) {
            if (onClickListener != null) {
                okButton.setOnClickListener(onClickListener);
            }
        }
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
    }

    public void onCreate() {
        super.onCreate();
        CreateAppHead();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (appHead != null) windowManager.removeView(appHead);
        CreateAppHead();
        //windowManager.addView(appHead, params);
    }
}