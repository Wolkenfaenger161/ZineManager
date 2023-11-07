/**	ZineManager v0.0	Wf	31.08.2023
 * 
 * 	logic.entities
 * 	IDElement
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

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class IDElement {
	@JsonProperty("id")
	protected int id;
	
	/**	Wf	21.08.2023
	 * 
	 */
	public IDElement() {
		id = -1;
	}
	/**	Wf	21.08.2023
	 * 
	 * @param pID
	 */
	public IDElement(int pID) {
		try {setId(pID);}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	21.08.2023
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**	Wf	21.08.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void setId(int pID) throws Exception{
		if (pID >= -1) id = pID;
		else throw new Exception("02; sID,IDE");
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	31.08.2023
	 * 
	 * @param pCompareObject
	 * @return
	 */
	public boolean isSameObject(IDElement pCompareObject){
		boolean vRet = false;
		
		if (pCompareObject != null) vRet = (pCompareObject.getId() == id);
		
		return vRet;
	}

	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	31.08.2023
	 * 
	 */
	public IDElement clone() {
		IDElement vRet = null;
		
		try { vRet = clone(-1); }
		catch(Exception ex) {LogManager.handleException(ex);}
		
		return vRet;
	}
	/**	Wf	31.08.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public abstract IDElement clone(int pID) throws Exception;
	
}
