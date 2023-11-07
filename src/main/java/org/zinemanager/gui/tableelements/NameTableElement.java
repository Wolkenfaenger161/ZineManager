/**	ZineManager v0.0		Wf	28.09.2023
 * 	
 * 	gui.tableelements
 * 	  BasicTableElement
 * 		NameElement
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

public class NameTableElement extends BasicTableElement {
	protected String name;

	/**	Wf	28.09.2023
	 * 
	 * @param pID
	 * @param pText
	 */
	public NameTableElement(int pID, String pName) {
		super(pID);
			
		try {
			setName(pName);
		} catch(Exception ex) {LogManager.handleException(ex);}
	}

//--------------------------------------------------------------------------------------------------------

	/**	Wf	28.09.2023
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**	Wf	28.09.2023
	 * 
	 * @param pText
	 * @throws Exception
	 */
	public void setName(String pName) throws Exception{
		if (pName != null) name = pName;
		else throw new Exception("02, sNa,NaE");
	}
	
	//----------------------------------------------------------------------------------------------------

	/**	Wf	28.09.2023
	 * 
	 */
	@Override
	public String toString() {
		return name;
	}
}
