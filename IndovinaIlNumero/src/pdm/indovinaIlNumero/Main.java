package pdm.indovinaIlNumero;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity  implements MesssageReceiver{
protected static final int SHOW_TOAST = 0;
String TAG;
String selectedNumber;
ConnectionManager connection;
	enum Stato{
		WAIT_FOR_START,WAIT_FOR_START_ACK,USER_SELECTING,WAIT_FOR_NUMBER_SELECTION,
		WAIT_FOR_BET,USER_BETTING
	}
	Stato statoCorrente;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		TextView tw = (TextView)findViewById(R.id.textview3);
		String nomeut = getIntent().getExtras().getString("nomeutente");
		String nomeav = getIntent().getExtras().getString("nomeavversario");
		tw.setText(nomeut+"  "+nomeav);
		
		Button uno = (Button)findViewById(R.id.button2);
		uno.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		Button due = (Button)findViewById(R.id.button3);
		due.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		Button tre = (Button)findViewById(R.id.button4);
		tre.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		connection = new ConnectionManager(nomeut, nomeav, this);
		if(nomeav.hashCode()<nomeut.hashCode()) {
			timer.schedule(sendStart, 1000, 5000);//Inizio Io
			statoCorrente=Stato.WAIT_FOR_START_ACK;
		} else{ statoCorrente=Stato.WAIT_FOR_START; //Inizia Lui;Io aspetto il pacchetto
			                                           
		}
		   };
		   
		   final Handler handler = new Handler() {
			   @Override
			   public void handleMessage(android.os.Message msg) {
				   switch (msg.what) {
				   case Main.SHOW_TOAST:
					   Toast.makeText(Main.this, msg.getData().getString("toast"),
					Toast.LENGTH_LONG).show();
					   break;
					default:
						super.handleMessage(msg);
				   }
			   }
		   };
	Timer timer = new Timer();
	TimerTask sendStart = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(statoCorrente == Stato.WAIT_FOR_START_ACK) {
				connection.send("START");
			} else { Log.d(TAG, "Sending START but the state is " + statoCorrente);
			
			}
			
		}
		
	};
	
	public void numberSelected(View v) {
		Button b = (Button)v;
		String choice = b.getText().toString();
		if(statoCorrente == Stato.USER_SELECTING) {
			connection.send("SELECTED:" + choice);
			statoCorrente=Stato.WAIT_FOR_BET;
		} else if (statoCorrente == Stato.USER_BETTING) {
			String bet = b.getText().toString();
			if(bet.equals(selectedNumber)) {
				connection.send("BET:Y" + bet);
				Toast.makeText(Main.this, "Bravo hai indovinato!ora tocca a te", Toast.LENGTH_LONG)
				.show();
			} else {
				connection.send("BET:N" + bet);
				Toast.makeText(Main.this, "Peccato non hai indovinato, ora tocca a te",
						Toast.LENGTH_LONG).show();
			}
			statoCorrente=Stato.USER_SELECTING;
		}
	}
	public void receiveMessage(String body) {
		// TODO Auto-generated method stub
		if(body.equals("START")) {
			if(statoCorrente == Stato.WAIT_FOR_START) {
				connection.send("STARTACK");
				Message osmsg = handler.obtainMessage(Main.SHOW_TOAST);
				Bundle b = new Bundle();
				b.putString("toast", "Scegli il numero");
				osmsg.setData(b);
				handler.sendMessage(osmsg);
				statoCorrente=Stato.USER_SELECTING;
			} else if(body.equals("STARTACK")) {
				if(statoCorrente == Stato.WAIT_FOR_START_ACK) {
					statoCorrente=Stato.WAIT_FOR_NUMBER_SELECTION;
				} else {
					Log.e(TAG, "Ricevuto STARTACK ma lo Stato è " + statoCorrente);  
				   }
			    } else if (body.startsWith("SELECTED")) {
			    	if (statoCorrente == Stato.WAIT_FOR_NUMBER_SELECTION) {
			    		selectedNumber = body.split(":")[1];
			    		Message osmsg = handler.obtainMessage(Main.SHOW_TOAST);
			    		Bundle b = new Bundle();
			    		b.putString("toast", "Indovina il numero");
			    		osmsg.setData(b);
			    		statoCorrente=Stato.USER_BETTING;//chiudi8
			    	} else {
			    		Log.e(TAG, "Ricevuto SELECTED ma lo stato è" + statoCorrente);
			    	}
			    }   else if(body.startsWith("BET")) {
			    	String result = body.split(":")[1];
			    	Message osmsg = handler.obtainMessage(Main.SHOW_TOAST);
			    	Bundle b = new Bundle();
			    	if(result.equals("Y"))
			    	b.putString("toast", "Hai perso,il tuo avversario ha indovinato");
			    	else
			    		b.putString("toast", "Hai vinto!il tuo avversario ha sbagliato");
			    	osmsg.setData(b);
			    	handler.sendMessage(osmsg);
			    	statoCorrente=Stato.WAIT_FOR_NUMBER_SELECTION;
			    }else{ 
			    	Log.e(TAG, "Ricevuto SELECTED ma lo stato è" + statoCorrente);
			    }
		           }else { 
				Log.e(TAG, "Ricevuto START ma lo stato è" + statoCorrente);
			}
	   }
 }
		
	



