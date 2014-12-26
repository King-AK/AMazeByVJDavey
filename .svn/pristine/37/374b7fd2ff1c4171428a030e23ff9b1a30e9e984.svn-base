package falstad;

/**
 * The Battery class will be used by classes which implement the Robot interface. It is responsible for functioning as a battery. It is set to a certain level 
 * and returns information on how much juice is left in the battery.
 * 
 * This class solely exists to keep the BasicRobot class from getting too messy. 
 * @author VJ
 */

public class Battery {
	float juice;
	public Battery() {
		juice = 0;
	}
	
	public void chargeUp(float power){
		juice = power;
	}
	public float getJuice(){
		return juice;
	}
	public boolean isEmpty(){
		if(juice != 0){return false;}
		return true;
	}

}
