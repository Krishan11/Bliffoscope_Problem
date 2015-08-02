


/**
 * The object that has been identified in the bliffoscope data
 *
 */
public class TargetObject extends SpaceObject{

	//Total number of blips
	public int total = 0;
	//The number of blips that match
	public int found = 0;
	
	public float confidence = 0;
	//The start row in the bliffoscope data
	public int startRow = 0;
	//The start column in the bliffoscope data
	public int startCol = 0;
	
	//Type of the object - slimetorpedo or starship
	public Type type;
	//2D array that contains all the blips for this space object
	public int[][] spaceObj = null;

	
	public TargetObject() {
		
	}
	
	public TargetObject(int total, int found, int startRow, int startCol, Type type, int[][] spaceObj) {
		this.total = total;
		this.found = found;
		this.spaceObj = spaceObj;
		this.startCol = startCol;
		this.startRow = startRow;
		this.type = type;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
