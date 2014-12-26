package falstad;


public class MazeView extends DefaultViewer {

	Maze maze ; // need to know the maze model to check the state 
	// and to provide progress information in the generating state
	
	public MazeView(Maze m) {
		super() ;
		maze = m;
	}

	@Override
	public void redraw(MazePanel gc, int state, int px, int py, int view_dx,
			int view_dy, int walk_step, int view_offset, RangeSet rset, int ang) {
		//dbg("redraw") ;
		switch (state) {
		case Constants.STATE_TITLE:
			//redrawTitle(gc);
			break;
		case Constants.STATE_GENERATING:
			//redrawGenerating(gc);
			break;
		case Constants.STATE_PLAY:
			// skip this one
			break;
		case Constants.STATE_FINISH:
			//redrawFinish(gc);
			break;
		}
	}
	
	private void dbg(String str) {
		System.out.println("MazeView:" + str);
	}
	
	// 
	
	/**
	 * Helper method for redraw to draw the title screen, screen is hardcoded
	 * @param  gc graphics is the off screen image
	 */
	/*void redrawTitle(MazePanel gc) {
		gc.setColorWhite();
		gc.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		gc.setFontLarge();
		gc.setFontMetrics();
		gc.setColorRed();
		gc.centerString(gc, gc.fm, "MAZE", 100);
		gc.setColorBlue();
		gc.setFontSmall();
		gc.setFontMetrics();
		gc.centerString(gc, gc.fm, "by Paul Falstad", 160);
		gc.centerString(gc, gc.fm, "Refactored by VJ Davey for CS301 at W&M", 175);
		gc.centerString(gc, gc.fm, "www.falstad.com", 190);
		gc.setColorBlack();
		gc.centerString(gc, gc.fm, "To start:", 270);
		gc.centerString(gc, gc.fm, "Choose a skill level from the drop down menu,", 300);
		gc.centerString(gc, gc.fm, "Choose an algorithm to generate the maze", 320);
		gc.centerString(gc, gc.fm, "Choose a driver algorithm for the robot to exit the maze (Select manual for user control)", 340);
		gc.centerString(gc, gc.fm, "v1.2", 380);
		gc.centerString(gc, gc.fm, "NOTE: Pressing the 'v' button on your keyboard will stop the maze", 400);
		gc.centerString(gc, gc.fm, "and save a copy of the current maze to the directory C:\\maze.xml", 415);
	
	}
	/**
	 * Helper method for redraw to draw final screen, screen is hard coded
	 * @param gc graphics is the off screen image
	 */
	/*void redrawFinish(MazePanel gc) {
		gc.setColorBlue();
		gc.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		gc.setFontLarge();
		gc.setFontMetrics();
		gc.setColorYellow();
		gc.centerString(gc, gc.fm, "You won!", 100);
		gc.setColorOrange();
		gc.setFontSmall();
		gc.setFontMetrics();
		gc.centerString(gc, gc.fm, "Congratulations!", 160);
		gc.setColorWhite();
		gc.centerString(gc, gc.fm, "Hit any key to restart", 300);
	}*/

	/**
	 * Helper method for redraw to draw screen during phase of maze generation, screen is hard coded
	 * only attribute percentdone is dynamic
	 * @param gc graphics is the off screen image
	 */
	/*void redrawGenerating(MazePanel gc) {
		gc.setColorYellow();
		gc.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		gc.setFontLarge();
		gc.setFontMetrics();
		gc.setColorRed();
		gc.centerString(gc, gc.fm, "Building maze", 150);
		gc.setFontSmall();
		gc.setFontMetrics();
		gc.setColorBlack();
		gc.centerString(gc, gc.fm, maze.getPercentDone()+"% completed", 200);
		gc.centerString(gc, gc.fm, "Hit escape to stop", 300);
	}*/

}
