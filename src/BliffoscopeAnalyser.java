

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;



/**
 * This class controls the analysis of Bliffoscope data
 * It reads the signatures of the various space objects and saves them as 2D arrays
 * Then the bliffoscope data is read. This data is matched with the saved 
 * space objects' signatures to see if they match
 */
public class BliffoscopeAnalyser {
	
	/**
	 * Space Objects found in bliffoscope
	 */
	private ArrayList<TargetObject> identifiedSpaceObjects = new ArrayList<TargetObject>();
	
	/**
	 * Signatures of various space objects such as SlimeTorpedo and StarShip
	 */
	private ArrayList<SpaceObject> spaceObjects = new ArrayList<SpaceObject>();

	/**
	 * Bliffoscope data read from file
	 */
	private BliffoscopeData bliffoscopeData;
	
	
	/**
	 * Constructor. Reads files, builds signatures and bliffoscope data
	 * @param slimeTorpedoPath File path which contains torpedo's signature(definition)
	 * @param starshipPath File path which contains starship's signature(definition)
	 * @param bliffoscopeData File path which contains bliffoscope data
	 * @throws SpaceObjectException Throws this exception when any error occurs
	 */
	public BliffoscopeAnalyser(String slimeTorpedoPath, String starshipPath,
			String bliffoscopeData) throws SpaceObjectException {

		//Read the signatures and the bliffoscope Data
		processSlimeTorpedo(slimeTorpedoPath);
		processStarship(starshipPath);
		processBliffoscopeData(bliffoscopeData);
		

	}
	
	/**
	 * Get the space objects in bliffoscope data
	 * @return
	 */
	public ArrayList<TargetObject> getIdentifiedSpaceObjects() {
		return this.identifiedSpaceObjects;
	}

	/**
	 * Compares the space objects represented as 2D arrays with the bliffoscope data.
	 * The space objects are compared with every submatrix in bliffoscope data. The size of the space
	 * object controls how many +s are compared
	 * 
	 */
	private void analyzeBliffoscopeData() {
		
		//Start analyzing from the start of bliffoscope data till the end
		//Assumed that there are no objects at the edges.

		int bliffRows = bliffoscopeData.getHeight();
		int bliffCols = bliffoscopeData.getWidth();
		
		
		//Process ALL rows and columns of matrix
		for(int  i = 0; i < bliffRows; i++) {
			for(int j=0; j < bliffCols; j++) {
				
				//Pick a matrix starting at [i,j] location
				
				for(int k =0; k < spaceObjects.size();k++) {
				
					SpaceObject spaceObj = spaceObjects.get(k);
					
					//Fetching matrix twice every loop. Slow operation, but will hit data on L1/L2 cache.
					int[][] probableTorpedo = getBliffData(bliffoscopeData.getBliffscopeData(),bliffRows, 
															bliffCols, i, j,  spaceObj.getWidth(), spaceObj.getHeight());
					
					//Compare the 2D arrays to see how many +s are common among them
					TargetObject target = compareTorpedos(probableTorpedo, spaceObj.getSpaceObjectSignature());
					target.type = spaceObj.getType();
					target.startRow = i;
					target.startCol = j;
					
					identifiedSpaceObjects.add(target);
					
				}
			}
		}
		
	}
	
	/**
	 * Gets a submatrix from the available bliffData
	 * @param bliffoScopeData 2D representation of bliffoscope data
	 * @param bliffRows total number of rows in bliffoscope data
	 * @param bliffCols total number of columns in bliffoscope data
	 * @param startRow Starting row in bliffoscope data
	 * @param startCol Starting col in bliffoscope data
	 * @param spaceObjCols Total number of columns to fetch
	 * @param spaceObjRows Total number of rows to fetch
	 * @return 2D array with bliffoscope data
	 */
	private int[][] getBliffData(int[][] bliffoScopeData,int bliffRows, int bliffCols,  int startRow, int startCol, int spaceObjCols, int spaceObjRows) {
		

		int torpedoStart = startRow;
		int torpedoEnd   = startCol + spaceObjCols;
		int[][] probableMatrix = new int[spaceObjRows][spaceObjCols];

		if(torpedoEnd > bliffCols) {
			//@Todo : Should be handled better
			
			return probableMatrix;
		}
		if(torpedoStart + spaceObjRows > bliffRows ) {
			//@Todo : Should be handled better
			return probableMatrix;
		}
		int k = 0,l = 0;
		int row = -1, col = -1;
		
		//Get the required sub array
		for( k = torpedoStart; k < torpedoStart + spaceObjRows; k++) {
			row++;
			col = 0;
			for(l = startCol; l < startCol + spaceObjCols; l++) {
				probableMatrix[row][col] =  bliffoScopeData[k][l];
				col++;
			}
		}

		return probableMatrix;
	}
	
	/**
	 * Compare the two 2D arrays to see if they match. The number of +s that match
	 * are counted to generate a confidence value.
	 * confidence = (matched +s) / (total +s).
	 * 
	 * @param probableSpaceObj bliffoscope data in which we are searching for a space object
	 * @param spaceObjSignature the actual signature[definition] of the space object
	 * @return TargetObject which contains the probable space obj, the number of matched +s and
	 * a confidence value.
	 */
	private TargetObject compareTorpedos(int[][] probableSpaceObj,
			int[][] spaceObjSignature) {

        
		TargetObject outputDetails = new TargetObject();
		int found= 0; int total = 0;
		
		for(int i =0; i < spaceObjSignature.length;i++) {
			for(int j =0; j < spaceObjSignature[0].length;j++) {
				
				//Ignore blips which are not part of torpedo signature
				if(spaceObjSignature[i][j] == 1) {
					if(probableSpaceObj[i][j] == 1 ) {
						found++;
					}
					total++;

				}
			}
		}
		//save the info
		outputDetails.found      = found;
		outputDetails.total      = total;
		outputDetails.confidence = (float)found/(float)total;
		outputDetails.spaceObj   = probableSpaceObj;
				
		return outputDetails;
	}
	
	

	/**
	 * Processes Slime Torpedo
	 * @param slimeTorpedoPath
	 * @throws SpaceObjectException
	 */
	private void processSlimeTorpedo(String slimeTorpedoPath) throws SpaceObjectException {
		spaceObjects.add(new SlimeTorpedo(slimeTorpedoPath));
		
	}

	/**
	 * 
	 * @param starShipPath
	 * @throws SpaceObjectException
	 */
	private void processStarship(String starShipPath) throws SpaceObjectException {
		spaceObjects.add(new StarShip(starShipPath));
		
	}




	/**
	 * 
	 * @param bliffoscopeDataPath
	 * @throws SpaceObjectException
	 */
	private void processBliffoscopeData(String bliffoscopeDataPath) throws SpaceObjectException {

		bliffoscopeData = new BliffoscopeData(bliffoscopeDataPath);
		
	}




	public static void main(String[] args){
		
		
		String slimeTorpedoPath  =  "Files\\SlimeTorpedo.blf";
		String starShipPath      =  "Files\\Starship.blf";
		String bliffoscopeData   =  "Files\\TestData.blf";
		boolean printTargets = true;
		

		BliffoscopeAnalyser bliffoscopeAnalyser = null;
		try {
			bliffoscopeAnalyser = new BliffoscopeAnalyser(slimeTorpedoPath, starShipPath, bliffoscopeData);
		} catch (SpaceObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error occured, exiting");
			System.out.println("Error is "+e.getMessage());
			
		}


		bliffoscopeAnalyser.analyzeBliffoscopeData();
		ArrayList<TargetObject> targets = bliffoscopeAnalyser.getIdentifiedSpaceObjects();
				
				
		
		DecimalFormat df = new DecimalFormat("###.###");
		
		float highConfidence = 1.0f;
		float lowConfidence = 0.9f;
		System.out.println();
		System.out.println("TYPE\tCONFIDENCE\t[start - ROW,COL]\t[end-ROW,COL]");
		System.out.println("----------------------------------------------------");
		for(int loop = 1; loop < 6; loop++) {
				
//			System.out.println();

			for(int i =0; i < targets.size();i++) {
				TargetObject targetObject = targets.get(i);
				
				if(targetObject.confidence <= highConfidence && targetObject.confidence > lowConfidence) {
					
					System.out.println("");
					System.out.print(targetObject.type+"\t");
					//Complicated pretty print in table format
					System.out.print(df.format(targetObject.confidence)+
									"\t["+targetObject.startRow+","+targetObject.startCol+"]"+
									"\t["+(targetObject.startRow+(targetObject.spaceObj.length))+","+(targetObject.startCol+(targetObject.spaceObj[0].length))+"]");
					if(printTargets) {
						SpaceUtility.print2DMatrix(targetObject.spaceObj);	
					}
					
				}
				
			}
			highConfidence = lowConfidence;
			lowConfidence = lowConfidence -0.1f;
		}
		
		
		
		
	}
	
	
}
