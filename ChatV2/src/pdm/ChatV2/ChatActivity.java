package pdm.ChatV2;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatActivity extends Activity {
    /** Called when the activity is first created. */
	EditText et;
	ListView lv;
	String user;
	String pass;
	ArrayAdapter<String> adapter;
	Connection connection;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        et = (EditText)findViewById(R.id.EditText1);
        lv = (ListView)findViewById(R.id.ListView);
        user = getIntent().getExtras().getString("nome");
        pass = getIntent().getExtras().getString("codice");
        
        adapter = new ArrayAdapter<String> (ChatActivity.this,R.layout.row,R.id.rowText);
        lv.setAdapter(adapter);        
        
        try {
   		 ConnectionConfiguration config = new ConnectionConfiguration ("ppl.eln.uniroma2.it",5222);
   		 config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
   		 connection = new XMPPConnection (config);
   		 connection.connect();
   		 connection.login(user, pass);
   		 } catch (XMPPException e) {
   			 e.printStackTrace();
   		 }
        connection.addPacketListener(new PacketListener() {

			public void processPacket(Packet pkt) {
				// TODO Auto-generated method stub
				Message msg = (Message) pkt;
				String from = msg.getFrom();
				String body = msg.getBody();
				adapter.add(from+" : "+body+"\n");
				lv.setSelection(adapter.getCount()-1);
			}
        	
        }, new MessageTypeFilter(Message.Type.normal));
        
        Button send = (Button)findViewById(R.id.button1);
        send.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				adapter.add("ME: "+et.getText().toString()+"\n");
				
				Message msg = new Message();
				msg.setTo("loreti@ppl.eln.uniroma2.it");
				msg.setBody(et.getText().toString());
				connection.sendPacket(msg);
				lv.setSelection(adapter.getCount()-1);
                                                   				
				}
       
        });
    }
}