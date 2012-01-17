package TextDialer.RobertoRusco;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TextDialerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button dial = (Button)findViewById(R.id.button1);
        dial.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText etext = (EditText) findViewById(R.id.button1);
			String telString = etext.getText().toString();
			    String telUriString = "tel:" + telString;
			Uri telUri = Uri.parse(telUriString);
			
			Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(telUri);
			startActivity(intent);
				
			}
        	
        });EditText etext = (EditText) findViewById(R.id.editText);
           etext.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
           
           Button call = (Button)findViewById(R.id.button2);
           call.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText etext = (EditText) findViewById(R.id.button2);
			String telString = etext.getText().toString();
			    String telUriString = "tel:" + telString;
			Uri telUri = Uri.parse(telUriString);
			
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(telUri);
		startActivity(intent);
			    		
				
			}
        	   
           });
      
    }
}