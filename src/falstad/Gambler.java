/**
 * 
 */
package falstad;

import java.util.Observable;
import java.util.Observer;

/**
 * The Gambler, aka Random Mouse algorithm
 * @author VJ
 *
 */
public class Gambler implements MazeSolverAlgorithm, Observer {
	private boolean solved;
	private boolean ObservedObjectStateChange = false;
	private boolean paused = false;
	private boolean interrupted=false;

	/**
	 * Dumb stupid mouse that runs around the maze randomly
	 * @throws Exception 
	 */
	@Override
	public void solve(Robot robot, Sensor sensor) throws Exception {


		while(!robot.isAtGoal()){//so long as the robot has not solved the maze
			
			if(paused){
				Thread.currentThread().sleep(150);
				continue;
			}
			if(interrupted){
				break;
			}

			if(robot.hasStopped() || ObservedObjectStateChange==true){//if the robot has stopped, it cannot drive to the exit
				break;//loop breaks
			}
			if(sensor.maze.play.level <= 0){
				break;
			}


			//sense in three directions
			boolean a = sensor.wallSenseAhead();
			robot.rotate(Robot.Turn.LEFT);
			boolean l = sensor.wallSenseAhead();
			robot.rotate(Robot.Turn.AROUND);
			boolean r =sensor.wallSenseAhead();
			robot.rotate(Robot.Turn.LEFT);


			//if you cannot move left or right, but ahead, go ahead
			if((!l)&&(!r)&&(a)){
				robot.move(1);
			}
			//if you cannot go ahead or right, but can go left, go left
			else if((l)&&(!a)&&(!r)){
				robot.rotate(Robot.Turn.LEFT);
				robot.move(1);
			}
			//if you cannot go ahead or left, but can go right, go right
			else if((!l)&&(!a)&&(r)){
				robot.rotate(Robot.Turn.RIGHT);
				robot.move(1);
			}
			//if you can go ahead or left
			else if((l)&&(a)&&(!r)){
				double rand = Math.random();
				if(rand > 0.5){
					robot.rotate(Robot.Turn.LEFT);
					robot.move(1);
				}
				else{
					robot.move(1);
				}
			}
			//if you can go ahead or right
			else if((!l)&&(a)&&(r)){
				double rand = Math.random();
				if(rand > 0.5){
					robot.rotate(Robot.Turn.RIGHT);
					robot.move(1);
				}
				else{
					robot.move(1);
				}
			}
			//if you can go right or left 
			else if((l)&&(!a)&&(r)){
				double rand = Math.random();
				if(rand > 0.5){
					robot.rotate(Robot.Turn.LEFT);
					robot.move(1);
				}
				else{
					robot.rotate(Robot.Turn.RIGHT);
					robot.move(1);
				}
			}
			//if you can go ahead right or left
			else if((l)&&(a)&&(r)){
				double rand = Math.random();
				if(rand < 0.33){
					robot.rotate(Robot.Turn.LEFT);
					robot.move(1);
				}
				else if(0.33 < rand && rand < 0.66){
					robot.rotate(Robot.Turn.RIGHT);
					robot.move(1);
				}
				else{
					robot.move(1);
				}
			}
			//anything else would be a dead end, so turn around
			else{
				robot.rotate(Robot.Turn.AROUND);
				continue;
			}
		}
		if(robot.isAtGoal()){
			solved =true;
		}

	}

	@Override
	public boolean reachedExit() {
		return solved;
	}
	@Override
	public void update(Observable o, Object arg) {
		if(o.hasChanged()){ObservedObjectStateChange  = true;}
	}

	@Override
	public void setPaused() {
		// TODO Auto-generated method stub
		paused  = !paused;
	}

	@Override
	public void setInterrupted() {
		// TODO Auto-generated method stub
		interrupted = true;
		paused = false;
		
	}

}
