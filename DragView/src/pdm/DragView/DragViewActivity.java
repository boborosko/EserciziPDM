package pdm.DragView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DragViewActivity extends Activity {
    /** Called when the activity is first created. */
	private View selected_item = null;
	private int offset_x = 0;
	private int offset_y = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView image1 = (ImageView)findViewById(R.id.imageView1);
        ImageView image2 = (ImageView)findViewById(R.id.imageView2);
        
        image1.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getActionMasked()==MotionEvent.ACTION_DOWN) {
					offset_x = (int) event.getX();
					offset_y = (int) event.getY();
					selected_item = v;
					
				}
				return false;
			}
		});
        image2.setOnTouchListener(new View.OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getActionMasked()==MotionEvent.ACTION_DOWN) {
					offset_x = (int) event.getX();
					offset_y = (int) event.getY();
					selected_item = v;
				}
				
				return false;
			}
		});
        
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativeLayout1);
            rl.setOnTouchListener(new View.OnTouchListener() {
				
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(selected_item==null) return false;
					else {
						int eventaction = event.getAction();
						int x = (int)event.getX() - offset_x;
						int y = (int)event.getY() - offset_y;
						int w = getWindowManager().getDefaultDisplay().getWidth() -128;
						int h = getWindowManager().getDefaultDisplay().getWidth() -128;
						if(x > w)
							x = w;
						if(y > h)
							y = h;
						RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
								new ViewGroup.MarginLayoutParams(
										RelativeLayout.LayoutParams.WRAP_CONTENT,
										RelativeLayout.LayoutParams.WRAP_CONTENT));
						
						lp.setMargins(x, y, 0, 0);
						selected_item.setLayoutParams(lp);
						
						switch (eventaction) {
						case MotionEvent.ACTION_MOVE:
							break;
						case MotionEvent.ACTION_UP:
							selected_item = null;
							break;
							}
					return true;
				    }
			    }
           });
       }
}