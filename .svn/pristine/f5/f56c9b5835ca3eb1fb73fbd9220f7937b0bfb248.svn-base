package ui;

import java.util.Random;

import edu.wm.cs.cs301.amazebyvjdavey.R;
import falstad.globals;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FinishActivity extends Activity {

	TextView batteryInfo;
	TextView stepsInfo;
	TextView comments;
	boolean turtleTime = globals.maze.getTurtleTime();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		int stepsTaken = (Integer) getIntent().getSerializableExtra("stepsTaken");
		int batteryUsed = (Integer) getIntent().getSerializableExtra("usedBattery");
		boolean solved =  (Boolean) getIntent().getSerializableExtra("boolean");
		setContentView(R.layout.activity_finish);
		batteryInfo =(TextView)findViewById(R.id.textView2);
		stepsInfo =(TextView)findViewById(R.id.textView3);
		comments =(TextView)findViewById(R.id.textView4);
		if(turtleTime){
			batteryInfo.setText("MUTAGEN CONSUMED : "+batteryUsed+" OUNCES");
			stepsInfo.setText("DISTANCE TRAVELED : "+stepsTaken+" STEPS");
		}
		else{
			batteryInfo.setText("BATTERY CONSUMED : "+batteryUsed+" UNITS");
			stepsInfo.setText("LENGTH OF PATH : "+stepsTaken+" STEPS");
		}
		//randomize Turtle feedback on MetalHead
		Random random = new Random();
		int turtle = random.nextInt(4);
		
		if(solved){
			if(turtleTime){
				comments.setText("MISSION ACCOMPLISHED. REACHED LAIR.");
				switch(turtle){
				case 0://raph
					Toast.makeText(getApplicationContext(), "Raphael: Wow, Don. I guess this scrapheap isn't completely useless after all...", Toast.LENGTH_SHORT).show();
					break;
				case 1://leo
					Toast.makeText(getApplicationContext(), "Leonardo: Great job Donnie! The Shredder won't know what hit him with this one!", Toast.LENGTH_SHORT).show();
					break;
				case 2://mikey
					Toast.makeText(getApplicationContext(), "Mikey: BOOYAKASHA! This calls for some pizza!", Toast.LENGTH_SHORT).show();
					break;
				case 3: //donnie
					Toast.makeText(getApplicationContext(), "Donatello: I-It worked? IT WORKED! YEAH BABY!", Toast.LENGTH_SHORT).show();
					break;
			}
			}else{
			comments.setText("Congratulations! You Solved The Maze! Hooray!");
			}
		}
		else{
			if(turtleTime){
				comments.setText("MISSION FAILURE. MUTAGEN TANK EMPTY.");
				switch(turtle){
					case 0://raph
						Toast.makeText(getApplicationContext(), "Raphael: That piece of junk couldn't even make it's way home. How are we supposed to use this to fight Shredder?!", Toast.LENGTH_SHORT).show();
						break;
					case 1://leo
						Toast.makeText(getApplicationContext(), "Leonardo: Looks like you have some extra testing to do, Donnie.", Toast.LENGTH_SHORT).show();
						break;
					case 2://mikey
						Toast.makeText(getApplicationContext(), "Mikey: Maybe MetalHead would be better at this if he ran on pizza, like I do.", Toast.LENGTH_SHORT).show();
						break;
					case 3: //donnie
						Toast.makeText(getApplicationContext(), "Donatello: Ugh, I swore I was going to get it that time. Back to the drawing board.", Toast.LENGTH_SHORT).show();
						break;
				}
			
			
			}else{
			comments.setText("Uh Oh! We've ran out of Battery!");
			}
		}
		clearGlobals();
	}
	void clearGlobals(){
		globals.clear();
	}
	public void finishUp(View v){
		finish();
	}
}
