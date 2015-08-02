
/**
 * Utility class that contains some utility functions 
 *
 */
public class SpaceUtility {

	public SpaceUtility() {

	}

	
	/**
	 * Move this to a utility class
	 * @param matrix
	 */
	public static void print2DMatrix(int[][] matrix) {
		
		
		for(int i =0; i < matrix.length;i++){
			System.out.println();
			for(int j =0; j < matrix[0].length;j++) {
				System.out.print(matrix[i][j] == 1 ? "*" : " ");
			}
		}
			
		
	}}
