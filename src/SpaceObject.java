

/**
 * Abstract class that represents all space objects such as SlimeTorpedo
 * and StarShip
 */
public abstract class SpaceObject {

	
	int width;    //Width of the space object
	int height;   //Height of the space object
	int[][] spaceObjectSignature;
	public enum Type{SLIMETORPEDO, STARSHIP};
	
	
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public int[][] getSpaceObjectSignature(){
		return spaceObjectSignature;
	}
	
	public SpaceObject(String signatureFilePath) throws SpaceObjectException{
		readSpaceObjectSignature(signatureFilePath);
	}
	
	public SpaceObject(){
		
	}
	
	private void readSpaceObjectSignature(String signatureFilePath) throws SpaceObjectException {
		
		SpaceDataReader spaceDataReader = new SpaceDataReader();
		spaceObjectSignature = spaceDataReader.readSpaceData(signatureFilePath);
		this.width = spaceDataReader.getWidth();
		this.height = spaceDataReader.getHeight();
		
		//Used for debugging
		//printSpaceObjectData(spaceObjectSignature);
	}
	
	
	
	/**
	 * Returns name of the space object
	 * @return space object name
	 */
	public abstract String getName();
	
	/**
	 * Type of space object
	 */
	public abstract Type getType();
	
	/**
	 * Move this to a utility class
	 * @param matrix
	 */
	public void printSpaceObjectData(int[][] matrix) {
		System.out.println();
		System.out.println("Printing signature of "+this.getName());
		
		SpaceUtility.print2DMatrix(matrix);
			
		
	}
	
}
