/**
 * 
 */
package falstad;

/**
 * The BasicRobot class implements the Robot interface. It is responsible for all the methods outlined in the Robot Interface.
 * This is the actual robot which will traverse and interact with the maze constructed by the methods of the Maze/MazeBuilder classes. 
 * The Robot will collect sensory information about its environment which it will share with the Robot Driver. The Robot Driver will direct the Robot 
 * out of the maze...
 * manual key override will affect this class by stopping the driver which controls this class... 
 * @author VJ
 * 
 * 
 *
 */
public class BasicRobot implements Robot {
	//FIELDS
	Maze RobotsMaze;//every Robot needs its Maze to play in!
	public Cells RobotsCells;
	boolean broken;

	private boolean frontSense=true;
	private boolean leftSense=true;
	private boolean rightSense=true;
	private boolean backSense=true;
	private boolean roomSense = true;
	//robot parts -- all vital parts to the VALID robot. An incorrect Robot will not have these basic parts!
	Battery RobotLifeSupport;//every Robot needs a battery for life support. They are so fragile
	Sensor RobotSensor;//Robots Sensor, Robot needs these to get a readout on its environment!






	/**
	 * The Constructor of this class. Should provide an existing maze for the Robot to operate with, therefore a maze object should be passed to the robot...
	 * alternatively, the Maze or MazeApplication classes can be edited to provide the Maze the robot will reside in. 
	 * 
	 * The Robot need only have a maze to work in, a driver, a sensor, and a battery readout. Movements are all directed by the RobotDriver.
	 */
	public BasicRobot() {
		RobotLifeSupport = new Battery();//be sure to call the setBatteryLevel() method before playing with a robot each time!
		broken = false;
	}
	public BasicRobot(Maze maze) {
		RobotsMaze = maze;
		RobotsCells = RobotsMaze.mazecells;//be sure to call the setMaze() method before playing with a robot each time!
		RobotLifeSupport = new Battery();//be sure to call the setBatteryLevel() method before playing with a robot each time!
		RobotSensor = new Sensor(RobotsMaze,this);
		broken = false;
	}
	/**
	 * rotate method. used to rotate the robot by giving a turn direction as a parameter. 
	 */
	@Override
	public void rotate(Turn turn) throws Exception {
		//LEFT turn should equate to a rotate(1) in the Maze class.
		//RIGHT turn should equate to a rotate(-1) in the Maze class.
		//AROUND will equate to two LEFT turns, therefore, it will be two successive rotate(1) calls to the Maze class.
		if(hasStopped()){throw new Exception("Not enough battery in the robot or the Robot is broken");}//in the event the battery is empty, the Robot will cease movement	 

		else{
			switch(turn) {
			case LEFT: 
				RobotLifeSupport.juice -= .25 * getEnergyForFullRotation(); 
				RobotsMaze.turnLeft();
				break;
			case RIGHT:
				RobotLifeSupport.juice -= .25 * getEnergyForFullRotation(); 
				RobotsMaze.turnRight();
				break;
			case AROUND:
				RobotLifeSupport.juice -= .5 * getEnergyForFullRotation(); 
				RobotsMaze.turnAround();
				break;
			}
		}	
	}

	/**
	 * robot will move forward a number of spaces equivalent to the integer passed as distance.
	 */
	@Override
	public void move(int distance) throws ObstacleException {
		//move the given distance by making repeated walk calls in the forward direction
		if(!hasStopped()){
			if(RobotsMaze.wallCheck(1)){
				for(int i = 0; i<distance;i++){
					RobotLifeSupport.juice -= getEnergyForStepForward();
					RobotsMaze.stepForward();
				}
			}
			else{
				throw new ObstacleException();
			}
		}
	}


	/**
	 * returns the current position of the robot in the maze
	 */
	@Override
	public int[] getCurrentPosition(){
		//return the Robot's current position in the maze
		int[] currentPos = RobotsMaze.getPos();
		return currentPos;

	}

	/**
	 * gives the robot a maze object to work with
	 */
	@Override
	public void setMaze(Maze maze) {
		RobotsMaze = maze;//give the Robot a Maze to play with!
		RobotsCells = maze.mazecells;
		RobotSensor = new Sensor(maze,this);
	}

	/**
	 * returns a boolean true if the robot is outside of the maze and at the goal
	 */
	@Override
	public boolean isAtGoal() {
		if(RobotsMaze.AtGoal()){return true;}//return true when the robot is out of the maze
		return false;//false otherwise
	}

	/**
	 * returns a boolean true if the robot has the goal in its sight line
	 * @throws Exception 
	 */
	@Override
	public boolean canSeeGoal(Direction direction){
		if(!hasDistanceSensor(direction)){throw new UnsupportedOperationException();}
		else{
			boolean answer = false;
			switch(direction) {
			case FORWARD: 
				answer = RobotSensor.canSeeGoalAhead();
				break;
			case BACKWARD:
				answer = RobotSensor.canSeeGoalBehind();
				break;
			case LEFT:
				answer=RobotSensor.canSeeGoalOnLeft();
				break;
			case RIGHT:
				answer=RobotSensor.canSeeGoalOnRight();
				break;
			}
			return answer;
		}
	}

	/**
	 * returns a boolean true if the robot is in a position where there are no walls to either its left or right.
	 */
	@Override
	public boolean isAtJunction() throws UnsupportedOperationException {

		if(!hasJunctionSensor()){throw new UnsupportedOperationException();}//if there is no Junction Sensing Ability, then this function wont work
		boolean result = RobotSensor.junctionSense();
		return result;
	}

	/**
	 * tells if the robot has a junctionSensing ability
	 */
	@Override
	public boolean hasJunctionSensor() {
		if(leftSense==false && rightSense==false){return false;}
		return true;
	}

	/**
	 * tells if the Robot is inside a room
	 */
	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		if(!hasRoomSensor()){throw new UnsupportedOperationException();}
		return RobotSensor.roomSense();
	}

	/**
	 * tells if the robot has a Room Sensor (create a room sensor object)? 
	 */
	@Override
	public boolean hasRoomSensor() {
		if(roomSense=false){return false;}
		return true;
	}

	/**
	 * returns the current direction the robot is facing in
	 */
	@Override
	public int[] getCurrentDirection() {
		// TODO Auto-generated method stub
		return RobotsMaze.getDirection();
	}

	/**
	 * returns a float integer which represents battery level
	 */
	@Override
	public float getBatteryLevel() {
		return RobotLifeSupport.getJuice();//return information on the level battery is at
	}

	/**
	 * charges the battery of the robot to the level described by the float parameter provided
	 */
	@Override
	public void setBatteryLevel(float level) {
		RobotLifeSupport.chargeUp(level);//charge the battery to a certain level

	}

	/**
	 * returns the energy level needed to complete a full rotation
	 */
	@Override
	public float getEnergyForFullRotation() {
		//A full rotation requires 12 units of energy
		//similarly, a quarter turn or half turn will require 3 and 6 units respectively..
		return 12;
	}

	/**
	 * returns the energy level needed to take a step forward
	 */
	@Override
	public float getEnergyForStepForward() {
		//A single step forward will use up 5 units of energy
		return 5;
	}

	/**
	 * returns a boolean true if the robot has no more battery left and can no longer move
	 */
	@Override
	public boolean hasStopped() {
		if(RobotLifeSupport.isEmpty()){return true;}//if the Battery is out of power, then the Robot has stopped moving!
		if(broken){return true;}
		return false;//otherwise, the Robot should continue moving!
	}

	/**
	 * tells the distance to an obstacle(either a wall or border) for the robots current forward direction, measured in number of cells
	 */
	@Override
	public int distanceToObstacle(Direction direction)throws UnsupportedOperationException {
		if(!hasDistanceSensor(direction)){throw new UnsupportedOperationException();}
		else{
			//decrement battery here
			int distance = 0;
			switch(direction) {
			case FORWARD: 
				distance = RobotSensor.distanceToObstacleAhead();
				break;
			case BACKWARD:
				distance = RobotSensor.distanceToObstacleBehind();
				break;
			case LEFT:
				distance = RobotSensor.distanceToObstacleOnLeft();
				break;
			case RIGHT:
				distance = RobotSensor.distanceToObstacleOnRight();
				break;
			}
			return distance;
		}
	}

	/**
	 * tells if the robot has a DistanceSensor object
	 */
	@Override
	public boolean hasDistanceSensor(Direction direction) {
		switch(direction){
		case FORWARD:
			return frontSense;
		case BACKWARD:
			return backSense;
		case LEFT:
			return leftSense;
		case RIGHT:
			return rightSense;
		}
		return true;
	}
	public Sensor getSensor(){
		return RobotSensor;
	}

}
