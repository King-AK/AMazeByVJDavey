package ui;

import java.io.File;

import edu.wm.cs.cs301.amazebyvjdavey.R;
import falstad.Maze;
import falstad.MazeFileReader;
import falstad.MazePanel;
import falstad.globals;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;

@SuppressLint("HandlerLeak")
public class GeneratingActivity extends Activity {
	public Maze maze;
	Thread t;
	GeneratingActivity gen = this;
	
	ProgressBar pb;
	VideoView vid;
	boolean turtleTime;
	
	int progress = 0;
	MazeFileReader mfr;
	
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message message) {
			Log.v(LOGTAG, "handler called");
			startPlay();
			gen.finish();
		}
	};
	private static final String LOGTAG = "GeneratingActivity";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_generating);
		pb =(ProgressBar)findViewById(R.id.progressBar1);
		maze = globals.maze;
		maze.gen = this;
		turtleTime = maze.getTurtleTime();
		
		int mazebuildAlgorithm = (Integer) getIntent().getSerializableExtra("mazebuilderAlgorithm");
		int driverAlgorithm = (Integer) getIntent().getSerializableExtra("driverAlgorithm");
		int skill = (Integer) getIntent().getSerializableExtra("skill");
		boolean fileloader = (Boolean) getIntent().getSerializableExtra("fileloader");
		
		Log.v(LOGTAG, "taken the parameters in");
		maze.setDriver(driverAlgorithm);//switch this to properly implement robot drivers
		if(!fileloader){
			Log.v(LOGTAG, "attempt at building new maze");
			maze.setMethod(mazebuildAlgorithm);
			maze.setSkill(skill);
			maze.androidMazeBuilder(skill);
		}
		else{
			Log.v(LOGTAG, "attempt at loading stored maze");
			String XMLFile = (String) getIntent().getSerializableExtra("XMLFile");
			File file = getFilesDir();//file object created to get access to the Files Directory
			String path = file.getAbsolutePath() +"/"+XMLFile;//string of the path, used to generate a stored maze
			mfr = new MazeFileReader(path);
			maze.loadMaze(mfr);
		}
		
	}
	public void startPlay(){
		//if error, try inserting thread.join here...
		/*if(turtleTime){
			Intent cutscene = new Intent(this, VideoViewActivity.class);
			startActivity(cutscene);
		}*/
		
		Intent play = new Intent(this, PlayActivity.class);
		startActivity(play);
	}
}