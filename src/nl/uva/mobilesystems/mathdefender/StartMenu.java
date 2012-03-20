package nl.uva.mobilesystems.mathdefender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class StartMenu extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		Button button;
				
		button = (Button)findViewById(R.id.button_supermarketstart);
		button.setOnClickListener(this);
		
		button = (Button)findViewById(R.id.button_zenstart);
		button.setOnClickListener(this);
		
		button = (Button)findViewById(R.id.button_highscores);
		button.setOnClickListener(this);
	}

	public void onClick(View v) {
		
		Intent intent;
		
		if(v == (Button)findViewById(R.id.button_zenstart)){
			intent = new Intent(this, InfoScreen.class);
			intent.putExtra("mode", "zen");
			startActivity(intent);
		}
		else if(v == (Button)findViewById(R.id.button_supermarketstart)){
			intent = new Intent(this, InfoScreen.class);
			intent.putExtra("mode", "supermarket");
			startActivity(intent);
		}
		else if(v == (Button)findViewById(R.id.button_highscores)) {
			intent = new Intent(this, Highscores.class);
			startActivity(intent);
		}
		
		
	}
}
