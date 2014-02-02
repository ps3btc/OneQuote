package hatchingamazing.labs.onequote;

import hatchingamazing.labs.onequote.R;
import hatchingamazing.labs.onequote.QuoteHead;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();

        Button launch = (Button)findViewById(R.id.button1);
        launch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this, QuoteHead.class);
                EditText editText = (EditText) findViewById(R.id.reminderText);
                String reminderMessage = editText.getText().toString();
                if (!reminderMessage.matches("")) {
                    serviceIntent.putExtra("KEY", editText.getText().toString());
                    startService(serviceIntent);
                    finish();
                }
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
