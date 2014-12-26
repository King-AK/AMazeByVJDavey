package ui;

import java.io.File;
import java.util.Random;

import edu.wm.cs.cs301.amazebyvjdavey.R;
import falstad.MazePanel;
import falstad.Maze;
import falstad.globals;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;



//TODO: fix code so that turning on the music during play activity stops play activity music --(the drop down menu on an S5)
//make the UI prettier


@SuppressLint("HandlerLeak")
public class PlayActivity extends ActionBarActivity {
	
	private static final String LOGTAG = "PlayActivity";
	
	
	private Maze maze;
	MazePanel mazeView;
	public ProgressBar battery;
	public int level = 2500;
	
	String path;//used to keep track of the apps location in the system, and make use of stored data
	
	boolean turtleTime = false;
	
	public boolean solved=false;
	
	MediaPlayer backgroundMusic;//used to keep track of background music used for the app
	
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message message) {
			Log.v(LOGTAG,"handler called");
		}
	};
	public Thread thread = new Thread(new Runnable(){

		@Override
		public void run() {
			try{
					maze.drive();
					if(level <= 0){
						goToFinish();
					}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	});
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Intent intent =getIntent();
		super.onCreate(savedInstanceState);
		Log.v(LOGTAG,"playing has begun!");
		setContentView(R.layout.play_activity);
		
		
		//maze panel view is added in the XML file, it is controlled here
		maze = globals.maze;
		if(maze.getTurtleTime()){
			turtleTime = true;
			Toast.makeText(getApplicationContext(), "Donatello: I want you to run some tests on MetalHead. Help me guide MetalHead through the sewer and back to the lair!", Toast.LENGTH_LONG).show();
		}
		MazePanel panel = (MazePanel) findViewById(R.id.mazePanel);
		panel.setBitmap();
		maze.setPanel(panel);
		maze.makeViews(panel);
		mazeView = panel;
		
		//battery set up
		battery =(ProgressBar)findViewById(R.id.progressBar1);
		battery.setMax(level);
		battery.setProgress(level);
		if(turtleTime){
			battery.getProgressDrawable().setColorFilter(Color.rgb(51, 138, 25), Mode.SRC_IN);
		}
		//the maze is given this activity as one of its fields, helps with methods
		maze.play = this;
	
		//file work
		File file = getFilesDir();//file object created to get access to the Files Directory
		path = file.getAbsolutePath();//string of the path, used for toast
		Random random = new Random();
		int music = random.nextInt(3);
		AudioManager manager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
		if(turtleTime && !manager.isMusicActive()){
			switch(music){
			case 0:
				backgroundMusic = MediaPlayer.create(this, R.raw.tmntshellshock);
				break;
			case 1:
				backgroundMusic = MediaPlayer.create(this, R.raw.tmnt03theme);
				break;
			case 2:
				backgroundMusic = MediaPlayer.create(this, R.raw.tmnt2k12them);
				break;
			}
			backgroundMusic.setLooping(true);
			backgroundMusic.start();
		}
		start();
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AudioManager manager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
		if(turtleTime &&  !manager.isMusicActive()){
			backgroundMusic.start();
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(turtleTime){
			if(backgroundMusic!=null){
				backgroundMusic.pause();
			}
		}
		
	}
	void start(){
		if(maze.algorithm != 0){
			thread.start();
		}
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.play_activity_actions, menu);
		return true;
	}
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(maze.algorithm!=0){
			globals.algorithm.setInterrupted();
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(turtleTime){
				Toast.makeText(getApplicationContext(), "Donatello: This algorithm is good enough, I suppose.", Toast.LENGTH_SHORT).show();
			}
			Log.v(LOGTAG, "Back button pressed. thread killed.");
		}else{
			if(turtleTime){
			Toast.makeText(getApplicationContext(), "Donatello: Testing MetalHead is fun and all, but a pizza break sounds even better!", Toast.LENGTH_SHORT).show();
			}
		}
		
		super.onBackPressed();
		
		
	}
	
	/*
	 * Methods for movement. Called by a button push from play_activity.xml
	 * Toasts and Logs only for now.
	 */
	public void rotateLeft (View v) {

		//Toast.makeText(getApplicationContext(), "Turning left", Toast.LENGTH_SHORT).show();
		Log.v(LOGTAG, "rotateLeft");
		if(maze.algorithm == 0){
			maze.turnLeft();
		}
		if(level<=0)
			goToFinish();


	}
	public void rotateRight(View v){

		//Toast.makeText(getApplicationContext(), "Turning Right", Toast.LENGTH_SHORT).show();
		Log.v(LOGTAG, "rotateRight");
		if(maze.algorithm == 0){
			maze.turnRight();
		}
		
		if(level<=0)
			goToFinish();

	}
	public void moveAhead (View v){
	
		//Toast.makeText(getApplicationContext(), "Stepping Forward", Toast.LENGTH_SHORT).show();
		Log.v(LOGTAG, "moveAhead");
		if(maze.algorithm == 0){
			maze.stepForward();
		}
		if(level<=0)
			goToFinish();
		
	}
	public void moveBack (View v){

		//Toast.makeText(getApplicationContext(), "Stepping Backward", Toast.LENGTH_SHORT).show();
		Log.v(LOGTAG, "moveBack");
		if(maze.algorithm == 0){
			maze.stepBackward();
		}
		if(level<=0)
			goToFinish();
		
	}
	/** method for the toggle button. this will pause the game if the robot is traveling automatically. 
	 * toasts and logs only for now
	 */
	public void pauseGame (View v){
		if(maze.algorithm!=0){
			// Is the toggle on?
			boolean on = ((ToggleButton) v).isChecked();
			if (on) {
				//Toast.makeText(getApplicationContext(), "Pausing Game", Toast.LENGTH_SHORT).show();
				globals.algorithm.setPaused();
				Log.v(LOGTAG, "pauseGame- NOTE Pausing only works for algorithm exploration");
			} else {
				//Toast.makeText(getApplicationContext(), "Resuming Game", Toast.LENGTH_SHORT).show();
				globals.algorithm.setPaused();
				Log.v(LOGTAG, "pauseGame");
			}
		}

	}
	/*
	 * methods for the menu buttons. Currently only show toasts, but will show the overhead views of the Maze, Solution and Visible Walls 
	 * when falstad code is ported.
	 */
	public void showMaze(MenuItem i){
		//Toast.makeText(getApplicationContext(), "Toggle Showing Maze Overhead View", Toast.LENGTH_SHORT).show();
		Log.v(LOGTAG, "showingMaze");
		maze.mapShow();
	}
	public void showSolution(MenuItem i){
		//Toast.makeText(getApplicationContext(), "Toggle Showing Maze Solution", Toast.LENGTH_SHORT).show();
		Log.v(LOGTAG, "showingSolution");
		maze.solShow();
	}
	public void showWalls(MenuItem i){
		//Toast.makeText(getApplicationContext(), "Toggle Showing Visible Walls", Toast.LENGTH_SHORT).show();
		Log.v(LOGTAG, "showingWalls");
		maze.mazeShow();
	}
	
	/**
	 * Saves an XML version of the maze to app internal storage through use of
	 * the MazeFileWriter.
	 * @param v
	 */
	public void saveMaze(View v){
		if(!turtleTime){
			maze.saveMaze();
		Toast.makeText(getApplicationContext(), "Maze Saved to "+path, Toast.LENGTH_SHORT).show();
		Log.v(LOGTAG, "saveMaze");
		}else{
			Toast.makeText(getApplicationContext(), "Donatello: We can't have MetalHead storing a map of our sewer just yet. If he falls into the wrong hands, were busted!", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * method for the onClick of the temporary shortcut button. When Falstad Code is ported, this will depend on 
	 * battery level, broken status, and outside of maze status of the robot.
	 *  Jumps to the FinishState.
	 */
	public void goToFinish() {
		Intent intent = new Intent(this, FinishActivity.class);
		intent.putExtra("stepsTaken", maze.steps);
		if(level<0){
			level = 0;//done so that we don't have negative values making it read as though we are consuming more battery than is available.
			}
		intent.putExtra("usedBattery", 2500-level);
		intent.putExtra("boolean",solved);
		startActivity(intent);
		Log.v(LOGTAG, "Switching to FinishActivity");
		this.finish();
		Log.v(LOGTAG, "PlayActivity Finished");
	}
	

}
