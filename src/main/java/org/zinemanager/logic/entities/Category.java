/**	ZineManager v0.0	Wf	31.08.2023
 * 
 * 	logic.entities
 * 	IDElement
 * 	  NameElement
 * 		Category
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

package org.zinemanager.logic.entities;

import org.zinemanager.logic.manager.LogManager;

public class Category extends NameElement {

	/**	Wf	31.08.2023
	 * 
	 */
	public Category() {
		super();
	}
	/**	Wf	31.08.2023
	 * 
	 * @param pID
	 * @param pName
	 */
	public Category(int pID, String pName) {
		try {
			setId(pID);
			setName(pName);
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	31.08.2023
	 * 
	 */
	public void setName(String pName) throws Exception{
		if ((pName != null) && (!pName.equals(""))) name = pName;
		else throw new Exception("02; sNa,Cat");
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	31.08.2023
	 * 
	 */
	public Category clone(int pID) throws Exception {
		Category vRet = null;
		
		if (pID >= -1) vRet = new Category(pID, name);
		else throw new Exception("02; clo,Cat");
		
		return vRet;
	}
	
}
