/**
 * 
 */
package falstad;

import java.util.Stack;

/**
 * Tremaux Algorithm for solving a maze, basically a depth first search
 * @author VJ
 *
 */
public class Tremaux implements MazeSolverAlgorithm {

	private Maze maze;
	private Cells cells;
	private Sensor sensor;
	private Robot robot;
	private Stack<PosLog> stack = new Stack<PosLog>();//holds PosLog objects
	private PosLog poslog;//holds information on cells at a junction, especially mutable info regarding boolean directions
	private boolean paused=false;
	private boolean interrupted=false;


	@Override
	public void solve(Robot robot, Sensor sensor) throws Exception {
		maze = sensor.getMaze();
		cells = maze.mazecells;
		this.sensor = sensor;
		this.robot = robot;
		boolean a,l,r;
		PosLog current=null;
		boolean junction =false;
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
			int[]currentPos = maze.getPos();
			int[] currentDir = maze.getDirection();
			a=l=r=false;//as a default, set the directions ahead, left, and right as false
			//sensor collects data on if the robot can go ahead, left or right
			
			a = sensor.wallSenseAhead();
			robot.rotate(Robot.Turn.LEFT);//turn left (now facing left of the original direction)
			l = sensor.wallSenseAhead();
			robot.rotate(Robot.Turn.AROUND);//turn around (now facing right of the original direction)
			r = sensor.wallSenseAhead();
			robot.rotate(Robot.Turn.LEFT);//turn left (now facing the original direction)

			//if we are at a junction, go ahead unless you cant, otherwise go left unless you cant, otherwise go right
			
			//perform a junction check against the stack if you can
			//stack information regarding viable directions overrides current sensory data
			if((a&&l) || (a&&r) || (l&&r)||(a&&l&&r)){
				junction = true ; 
			}
			if(junction){
				PosLog stackHead;
				if(!stack.empty()){
					stackHead = (PosLog) stack.peek();
					int[] stackPos = stackHead.getPosition();
					if(stackPos[0] != currentPos[0] && stackPos[1] != currentPos[1]){//check if the position log of the head element matches the current position, if it does not, then create a new poslog object for the current junction
						current = new PosLog(currentPos, currentDir, a, l, r);//this is a new 3-way junction that must be added to the stack
						stack.push(current);//put current on top of the stack
					}else{//otherwise, if this is not a new 3-way junction
						//travel in the direction(s) not yet marked by the log OR turn around if you are at a dead end
					
						//first rotate in place until you are facing the uniform direction for the posLog
						while(stackHead.getDirection()[0] != currentDir[0] && stackHead.getDirection()[1] != currentDir[1]){
							robot.rotate(Robot.Turn.LEFT);
						}
						//second, override current sensory information with saved log data
						//this will treat visited directions as walls
						l = stackHead.l;
						r = stackHead.r;
						a = stackHead.a;
						
					}
				}else{//in the event the stack is empty, and we are at a 3-way junction
					//we gotta start up the stack
					current = new PosLog(currentPos, currentDir, a, l, r);
					stack.push(current);
				}
			}
			
			//if you can go forward, go forward
			if (a){
				//if at a junction, ahead is no longer a viable move
				if(junction){
					current.closeAhead();
				}
				robot.move(1);
				continue;
			}
			//if you cant go ahead but can go left, go left
			if(l && ! a){
				//if at a junction, left is no longer a viable move
				if(junction && current !=null){
					current.closeLeft();
				}
				robot.rotate(Robot.Turn.LEFT);
				robot.move(1);
				continue;
			}
			//if you can only go right, go right
			if(!l && !a && r){
				//if at a junction, right is no longer a viable move
				if(junction){
					current.closeRight();
				}
				robot.rotate(Robot.Turn.RIGHT);
				robot.move(1);
				continue;
			}
			//if you cannot go left right or ahead, turn around
			if(!l&&!r&&!a){
				robot.rotate(Robot.Turn.AROUND);
				//if the stack is not empty, wallfollow back to the position given at the top of the stack, then rotate in place until you are facing the logged direction
				if(!stack.empty()){
					PosLog returnTo;
					if(junction){//if we are at a junction and no viable directions are available, 
						returnTo = stack.pop();//remove the current item from the head of the stack
						wallFollow(returnTo);//wallFollow back to the previous junction, the head of the stack
					}
				}
				continue;
			}
			


			//check the stack against the current position if it is in the stack
			//!!!! VIABLE STACK MOVEMENTS OVERRIDE SENSORS. If stack says left is no longer viable, we cant go left even if the sensor says we can
			//if you cannot go left right or ahead and the stack is empty: 
			//turn around
			//if the position stack is not empty
			//check if the current position is the head of the position stack
			//if it is, pop the top of the stack and remove the element
			//wall follow to the position logged at the (new) top of the stack
			//if the current position is a junction (has more than one viable direction) and is not already logged in the position stack, add it to the stack
			//also log the direction and viable moves
			//always go ahead if possible
			//if not, turn left if possible and remove left from viable directions
			//if not, turn right if possible and remove right from viable directions

		}
		
	}
	public void wallFollow(PosLog returnTo) throws Exception{
		while(returnTo.getDirection()[0] != robot.getCurrentDirection()[0] && returnTo.getDirection()[1] != robot.getCurrentDirection()[1]);
		boolean a,l;
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


	
	@Override
	public boolean reachedExit() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setPaused() {
		// TODO Auto-generated method stub
		paused = !paused;
	}
	@Override
	public void setInterrupted() {
		// TODO Auto-generated method stub
		interrupted  =true;
		paused = false;
	}



}
class PosLog{
	//local class unique to Treamux for holding positions, directions, and viable directions at a junction

	//fields
	int[] position;
	int[] direction;
	boolean a,l,r;//boolean values regarding available directions 

	PosLog(int[] position, int[] direction, boolean a, boolean l, boolean r){
		this.position = position;
		this.direction = direction;
		this.a = a;
		this.l = l;
		this.r = r;
	}
	//used to close off the ahead path after it has been visited
	boolean closeAhead(){
		a = false;
		return a;
	}
	//used to close off the left path after it has been visited
	boolean closeLeft(){
		l = false;
		return l;
	}
	//used to close off the right path after it has been visited
	boolean closeRight(){
		r = false;
		return r;
	}
	//returns the direction the robot is facing at a junction, used to keep consistency on what is left, right and ahead
	int[] getDirection(){
		return direction;
	}
	//used to keep track of the position in the maze where the junction is so the robot can return to this spot
	int[] getPosition(){
		return position;
	}

}
