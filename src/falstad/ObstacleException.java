/**
 * 
 */
package falstad;

/**
 * Exception thrown when the robot hits a wall
 * @author VJ
 *
 */
public class ObstacleException extends Exception {
	public String getMessage(){
		return "You ran into a wall, idiot.";
	}
}
