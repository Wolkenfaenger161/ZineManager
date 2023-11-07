/**	ZineManager v0.0		Wf	28.09.2023
 * 	
 * 	gui.tableelements
 * 	  BasicTableElement
 * 		
 * 
 * Exceptions:
 * 	  01 Wrong length
 * 	  02 Wrong Value
 * 	  03 Calculation Error
 * 	  04 Nullpointer Error
 * 	  05 Empty List Error
 * 	  06 Wrong Type Error
 * 	  07 Index Error
 * 	  08 Equal Object Error
 */

package org.zinemanager.gui.tableelements;

import org.zinemanager.logic.manager.LogManager;

public abstract class BasicTableElement {
	protected int id;

	/**	Wf 28.09.2023
	 * 
	 * @param pID
	 */
	public BasicTableElement(int pID) {
		try {setId(pID);}
		catch(Exception ex) {LogManager.handleException(ex);}
	}

//--------------------------------------------------------------------------------------------------------

	/**	Wf	28.09.2023
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**	Wf	28.09.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void setId(int pID) throws Exception {
		if (pID >= -1) id = pID;
		else throw new Exception("02; sId,BTE");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	28.09.2023
	 * 
	 */
	@Override
	public abstract String toString();
}
