package pdm.indovinaIlNumero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends Activity {
    /** Called when the activity is first created. */
	EditText user1,user2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        
        user1 = (EditText)findViewById(R.id.editext1);
        user2 = (EditText)findViewById(R.id.editext2);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(StartActivity.this, Main.class);
				String utente = user1.getText().toString();
				String avversario = user2.getText().toString();
				intent.putExtra("nomeutente", utente);
				intent.putExtra("nomeavversario", avversario);
				startActivity(intent);
				}
		});
        
    }
}