package ui;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.wm.cs.cs301.amazebyvjdavey.R;
import falstad.Maze;
import falstad.globals;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends ActionBarActivity {
	
	/*
	 * For now, Tremaux is disabled and on hold. I have decided to put
	 * more focus into using this project to learn more about
	 * Android Development. 
	 */
	
	
	public final static String EXTRA_MESSAGE = "message";
	LinearLayout layout;
	SeekBar sb;
	TextView tv;
	Spinner generator;
	Spinner driver;
	Spinner filelist;
	CheckBox cb;
	Maze maze;
	//following parameters set to 0 as a default in the event the user makes no selections
	int skill = 0;
	int driverAlgorithm = 0;
	int mazebuilderAlgorithm = 0;
	String XMLFile;
	boolean fileloader;
	boolean turtleTime;
	private static final String LOGTAG = "MainActivity";
	
	
	//onCreate adjusted to match my specifications. One Seekbar, Two Spinners, and a Checkbox
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = (LinearLayout)findViewById(R.id.mainBackground);
		sb =(SeekBar)findViewById(R.id.seekBar1);
		tv =(TextView)findViewById(R.id.textView4);
		driver=(Spinner)findViewById(R.id.Spinner01);
		generator =(Spinner)findViewById(R.id.spinner1);
		cb =(CheckBox)findViewById(R.id.checkBox1);
		filelist = (Spinner)findViewById(R.id.spinner2);
		Log.v(LOGTAG, "onCreate");
		
		//pull internal files, populate a list with them
		String[] files = getApplicationContext().fileList();
		List<String> list = new ArrayList<String>();
		for(int i = 1; i<files.length; i++){
			if(files[i].contains("xml"))
				list.add(files[i]);
		}
		
		//adjustments for the seekbar
		sb.setMax(16);
		//seekbar listener -- currently makes toasts
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "stop sliding", Toast.LENGTH_SHORT).show();
				Log.v(LOGTAG, "stop sliding");
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "start sliding", Toast.LENGTH_SHORT).show();
				Log.v(LOGTAG, "start sliding");
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// progress changed - skill levels out of 15. Level 16 is the secret Ninja Turtle Mode! Skill is a random integer between 0 and 15.
				if(progress < 16){
					turtleTime = false;
					tv.setText(progress+"/15");
					skill = progress;
					swapBackgrounds();
					
					
				}
				else{
					tv.setText("Ninja Turtle Mode");
					Toast.makeText(getApplicationContext(), "DO NOT CHECK THE FILE LOADER DURING TURTLE MODE. SERIOUSLY. DONT DO IT.", Toast.LENGTH_SHORT).show();
					turtleTime = true;
					Random random = new Random();
					int randomSkill = random.nextInt(16);//random numbers from 0 to 15
					skill = randomSkill;
					swapBackgrounds();
				}
			}
		});
		
		//adjustments for the spinners
		//adapters
		ArrayAdapter<CharSequence> gen_adapter = ArrayAdapter.createFromResource(this, R.array.maze_generators, android.R.layout.simple_spinner_item);
		gen_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		ArrayAdapter<CharSequence> drive_adapter = ArrayAdapter.createFromResource(this, R.array.robot_drivers, android.R.layout.simple_spinner_item);
		drive_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		ArrayAdapter<String> file_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);//may need to be adjusted
		//set adapters
		generator.setAdapter(gen_adapter);
		driver.setAdapter(drive_adapter);
		filelist.setAdapter(file_adapter);
		
		//spinner listeners -- currently makes toasts
		generator.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Item Selected", Toast.LENGTH_SHORT).show();
				Log.v(LOGTAG, "Item selected");
				if(!turtleTime){
					mazebuilderAlgorithm = position;
				}else{
					//if turtleTime is activated, a completely random algorithm is chosen to generate the maze
					Random random = new Random();
					int randomMazeBuild = random.nextInt(6);
					mazebuilderAlgorithm = randomMazeBuild;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
				
			}
		});
		driver.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Item Selected", Toast.LENGTH_SHORT).show();
				Log.v(LOGTAG, "Item Selected");
				driverAlgorithm = position;
				if(driverAlgorithm == 3){
					Toast.makeText(getApplicationContext(), "Tremaux is temporarily disabled. Please choose a different driver.", Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		filelist.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Item Selected", Toast.LENGTH_SHORT).show();
				Log.v(LOGTAG, "Item Selected");
				XMLFile= (String) parent.getItemAtPosition(position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
				
			}
		});
	
		//checkbox for file mazes
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(getApplicationContext(), "File Loader Checked - Ninja Turtle Mode will not work with the file loader active", Toast.LENGTH_SHORT).show();
				Log.v(LOGTAG, "File Loader Checked");
				if(isChecked){
					fileloader = true;
				}else{
					fileloader = false;
				}
				
			}
		});
		
		
	}
	@SuppressLint("NewApi")
	public void swapBackgrounds(){
		if(turtleTime){
			layout.setBackground(getResources().getDrawable(R.drawable.tmnt));
		}else{
			layout.setBackground(null);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//method for onClick of the start button. Creates the GeneratingActivity with the given user input
	public void gotoActivity(View v) {
		if(driverAlgorithm != 3){
			globals.createMaze(this);//edit such that instead of having a maze which takes contets, I just pull the information from the widgets once the start button is clicked
			maze = globals.maze;
			Log.v(LOGTAG, "taking parameters to construct maze");
			Intent intent = new Intent(this, GeneratingActivity.class);
			intent.putExtra("fileloader", fileloader);
			intent.putExtra("skill", skill);
			intent.putExtra("driverAlgorithm", driverAlgorithm);
			intent.putExtra("mazebuilderAlgorithm", mazebuilderAlgorithm);
			if(fileloader){
				intent.putExtra("XMLFile", XMLFile);
			}
			if(turtleTime && !cb.isChecked()){
				maze.activateTurtleTime();
			}
			Toast.makeText(getApplicationContext(), "Starting", Toast.LENGTH_SHORT).show();
			Log.v(LOGTAG, "Activity Switch to Generating Activity");
			startActivity(intent);
			Log.v(LOGTAG, "Activity Switch to Generating Activity");
		}else{
			Toast.makeText(getApplicationContext(), "I just told you not to use Tremaux. Stop it.", Toast.LENGTH_SHORT).show();
		}

	}
}
