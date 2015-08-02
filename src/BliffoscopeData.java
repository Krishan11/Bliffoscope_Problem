
/**
 * Represents Bliffoscope data as 2D array
 *
 */
public class BliffoscopeData {
	
	int[][] bliffoscopeData;
	int width;
	int height;
	
	public BliffoscopeData() {
		
	}
	/**
	 * COnstructs the bliffoscope data
	 * This constructor throws an exception!!
	 * @param bliffoscopeFilePath
	 * @throws SpaceObjectException
	 */
	public BliffoscopeData(String bliffoscopeFilePath) throws SpaceObjectException {
		SpaceDataReader spaceDataReader = new SpaceDataReader();
		bliffoscopeData = spaceDataReader.readSpaceData(bliffoscopeFilePath);
		this.width = spaceDataReader.getWidth();
		this.height = spaceDataReader.getHeight();
		
		//Used for debugging
		//printSpaceObjectData(bliffoscopeData);
	}

	private void printSpaceObjectData(int[][] bliffoscopeData) {
		System.out.println();
		System.out.println("Printing BliffoscopeData, contains "+height+" rows and "+width+" columns");
		
		
		SpaceUtility.print2DMatrix(bliffoscopeData);
		
	}
	
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public int[][] getBliffscopeData() {
		return this.bliffoscopeData;
	}
	

}
