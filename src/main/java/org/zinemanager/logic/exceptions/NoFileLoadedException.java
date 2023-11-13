/**	ZineManager v0.0	Wf	12.11.2023
 * 
 * 	locig.exceptions
 * 		NoFileLoadedException
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

public class NoFileLoadedException extends Exception {
	
	/**	Wf	12.11.2023
	 * 
	 * @param pErrorMessage
	 */
	public NoFileLoadedException(String pErrorMessage) {
		super(pErrorMessage);
	}
}
