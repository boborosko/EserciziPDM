package pdm.uniroma2.it;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Second extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        TextView label = (TextView) findViewById(R.id.button);
        String iltestoricevuto = getIntent().getExtras().getString("iltestonelbox");
        label.setText(iltestoricevuto);
        Log.d("Second", "onCreate");
	}
        
	}


