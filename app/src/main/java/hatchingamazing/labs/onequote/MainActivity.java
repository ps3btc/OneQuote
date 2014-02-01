package hatchingamazing.labs.onequote;

import hatchingamazing.labs.onequote.R;
import hatchingamazing.labs.onequote.QuoteHead;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.getString("LAUNCH").equals("YES")) {
            startService(new Intent(MainActivity.this, QuoteHead.class));
        }

        Button launch = (Button)findViewById(R.id.button1);
        launch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, QuoteHead.class));
            }
        });

        Button stop = (Button)findViewById(R.id.button2);
        stop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, QuoteHead.class));
            }
        });

    }

    @Override
    protected void onResume() {
        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.getString("LAUNCH").equals("YES")) {
            startService(new Intent(MainActivity.this, QuoteHead.class));
        }
        super.onResume();
    }
}
