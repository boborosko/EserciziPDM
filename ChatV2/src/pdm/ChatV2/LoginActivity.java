package pdm.ChatV2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{
	EditText user;
	EditText pass;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		user = (EditText)findViewById(R.id.EditText2);
		pass = (EditText)findViewById(R.id.EditText3);
		
		Button login =(Button)findViewById(R.id.button2);
		login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = (new Intent(LoginActivity.this, ChatActivity.class));
				String utente = user.getText().toString();
				String chiave = pass.getText().toString();
				
				intent.putExtra("nome", utente);
				intent.putExtra("codice", chiave);
				startActivity(intent);
			}
			
		});
		
	}

}
