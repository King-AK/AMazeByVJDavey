/**
 * 
 */
package falstad;

import java.util.Observer;

/**
 * Base interface for the family of maze solving algorithms.
 * the manual driver will make use of a MazeSolverAlgorithm to direct the robot out of the maze
 * this follows a Strategy pattern wherein any algorithm from a family of algorithms is interchangeable 
 * @author VJ
 *
 */

public interface MazeSolverAlgorithm{
	void solve(Robot robot, Sensor sensor) throws Exception;
	public void setPaused();
	public void setInterrupted();
	boolean reachedExit();

}
