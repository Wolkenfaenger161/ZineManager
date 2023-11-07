/**	ZineManager v0.0	Wf	19.10.2023
 * 
 * 	locig.exceptions
 * 		InvalidZinePathFileException
 * 
 * 	Exceptions:
 * 	  01 Wrong length
 * 	  02 Wrong Value
 * 	  03 Calculation Error
 * 	  04 Nullpointer Error
 * 	  05 Empty List Error
 * 	  06 Wrong Type Error
 * 	  07 Index Error
 * 	  08 Equal Object Error
 * 	  09 Wrong Selection
 */

package org.zinemanager.logic.exceptions;

import java.util.ArrayList;
import java.util.Arrays;

public class InvalidZinePathFileException extends Exception {
	private ArrayList<Integer> invalidFilePathsZineIDs;
	
	/**	Wf	19.10.2023
	 * 
	 * @param pErrorMessage
	 * @param pInvalidFilePathZineID
	 */
	public InvalidZinePathFileException(String pErrorMessage, int pInvalidFilePathZineID) {
		this(pErrorMessage, new ArrayList<Integer>(Arrays.asList(Integer.valueOf(pInvalidFilePathZineID))));
	}
	
	/**	Wf	19.10.2023
	 * 
	 * @param pErrorMessage
	 * @param pInvalidFilePathZineIDs
	 */
	public InvalidZinePathFileException(String pErrorMessage, ArrayList<Integer> pInvalidFilePathZineIDs) {
		super(pErrorMessage);
		
		invalidFilePathsZineIDs = pInvalidFilePathZineIDs;
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	19.10.2023
	 * 
	 * @return
	 */
	public ArrayList<Integer> getInvalidFilePathZineIDs(){
		return invalidFilePathsZineIDs;
	}
	
//--------------------------------------------------------------------------------------------------------
	
}
