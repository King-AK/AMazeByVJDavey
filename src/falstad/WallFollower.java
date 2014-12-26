/**
 * 
 */
package falstad;

/**
 * Wall Following method to solve a maze
 * @author VJ
 *
 */
public class WallFollower implements MazeSolverAlgorithm {
	boolean solved =false;
	private boolean paused=false;
	private boolean interrupted=false;
	/**
	 * Wall Following method of solving. The robot will follow the 
	 * walls it can sense to its right to get out of the maze.
	 * @throws Exception 
	 */
	@Override
	public void solve(Robot robot, Sensor sensor) throws Exception {
		boolean a;
		boolean l;
		while(!robot.isAtGoal()){
			if(paused){
				Thread.currentThread().sleep(150);
				continue;
			}
			if(interrupted){
				break;
			}
			if(robot.hasStopped()){//if the robot has stopped, it cannot drive to the exit
				break;//loop breaks
			}
			if(sensor.maze.play.level <= 0){
				break;
			}
			//check left and ahead
			
			a = sensor.wallSenseAhead();
			robot.rotate(Robot.Turn.LEFT);
			l = sensor.wallSenseAhead();	
			robot.rotate(Robot.Turn.RIGHT);
			
			//if there is no wall to your left, turn left and step ahead
			if((l)&&(!a)){
				robot.rotate(Robot.Turn.LEFT);
				robot.move(1);
			}
			//if there is no wall to the left or ahead, turn left and step ahead
			else if((l)&&(a)){
				robot.rotate(Robot.Turn.LEFT);
				robot.move(1);
			}
			//if there is a wall to the left, and no wall ahead, step ahead
			else if((!l)&&(a)){
				robot.move(1);
			}
			//if there is a wall to the left, and a wall ahead, turn right
			else if ((!l)&&(!a)){
				robot.rotate(Robot.Turn.RIGHT);
			}
		}
		if(robot.isAtGoal()){
			solved =true;
		}
		
	}
	@Override
	public boolean reachedExit() {
		// TODO Auto-generated method stub
		return solved;
	}
	@Override
	public void setPaused() {
		// TODO Auto-generated method stub
		paused = !paused;
		
	}
	@Override
	public void setInterrupted() {
		// TODO Auto-generated method stub
		interrupted = true;
		paused = false;
	}



}
