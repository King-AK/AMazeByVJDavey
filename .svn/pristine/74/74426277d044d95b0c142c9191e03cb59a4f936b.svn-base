package falstad;

import java.util.ArrayList;

public class MazeBuilderAldousBroder extends MazeBuilder implements Runnable {
	
	public MazeBuilderAldousBroder() {
		System.out.println("MazeBuilderAldousBroder uses Aldous Broder algorithm to generate maze.");
	}

	public MazeBuilderAldousBroder(boolean deterministic) {
		super(deterministic);
		System.out.println("MazeBuilderAldousBroder uses Aldous Broder algorithm to generate maze.");
	}
	
	/**
	 * edits/overrides to the base mazebuilder code below
	 * 
	 * NOTE: should start with a maze with all the walls up. This means it should let everything initialize, and not come into play until the wall breaking 
	 * methods of the generate()/generatepathways() methods.
	 */

	@Override
	protected void generatePathways(){
		//focuses on the CELLS, and jumps between them. Wall deletion is a result of cell statuses
		
		//randomly select a starting position from all possible spots on the maze
		int x = random.nextIntWithinInterval(0, width-1);
		int y = random.nextIntWithinInterval(0, height - 1);
		
		//keep count of the remaining number of cells to visit
		int remaining = width*height-1;
		
		cells.setCellAsVisited(x, y);//A visited cell will have a value of 0, according to Cells.java

		while(remaining > 0){
		//iterate over directions randomly until we find a direction that doesnt take us outside of the maze boundaries
			/**NOTE: instead of looking at 4 possible different walls, like in Prim, we are looking at 4 possible different cells. If the cell is unvisited,
			*delete the wall that exists between the testMe cell and the current cell.
			*/
			int dir = randoJump();//picks a random direction for the cell to jump in between 0, 1, 2, and 3 for the options of Constants.java
			int dx = 0, dy=0;
			dx = Constants.DIRS_X[dir];
			dy = Constants.DIRS_Y[dir];
			int nx = x+dx;//index value for the new x
			int ny = y+dy;//index value for the new y
			
			if(nx >= 0 && ny >= 0 && nx < width && ny < height){//if the new x and y values are still within the border of the cell block
				if(cells.hasMaskedBitsTrue(nx, ny, Constants.CW_VISITED)){//if the value of the cell at (nx,ny) results in a value meaning  it has been unvisited
					cells.deleteWall(x, y, dx, dy);// delete wall from maze between the original cell and the new cell	
					cells.setCellAsVisited(nx, ny);//we set the value of the newly establised x,y cell as visited.
					remaining-=1;//decrement the tally of remaining spots to cover by one
					}
				//Regardless of whether the cell wall was deleteable or not
				//Update the current cell to correspond to the new cell index values
				x = nx;//x now points to the value of new x
				y = ny;//y now points to the value of new y
				
				
				}
			}

		}
		
		
	

	public int randoJump(){
		//used to randomly jump from one cell to another cell that is within the originals reach. 
		int choice = random.nextIntWithinInterval(0, 3);
		return choice;
		
	}
}
