package pdm.RobertoRusco;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyPlayerActivity extends Activity {
    /** Called when the activity is first created. */
	MediaPlayer mp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mp = MediaPlayer.create(MyPlayerActivity.this, R.raw.dst);
        
        Button StartButton = (Button) findViewById(R.id.button2);
        StartButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.start();
				
			}
    });
        
        Button PausaButton = (Button) findViewById(R.id.button1);
        PausaButton.setOnClickListener(new OnClickListener () {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mp.pause();
				
			}
        	
        });
} public void onDestroy(Bundle savedInstanceState) {
	super.onDestroy();
	mp.release();
}
}