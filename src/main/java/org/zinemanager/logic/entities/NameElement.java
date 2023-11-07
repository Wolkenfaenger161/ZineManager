/**	ZineManager v0.0	Wf	21.08.2023
 * 
 * 	logic.entities
 * 	IDElement
 * 	  NameElement
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

public abstract class NameElement extends IDElement {
	@JsonProperty("name")
	protected String name;
	
	/**	Wf	21.08.2023
	 * 
	 */
	public NameElement() {
		name = "";
	}
	/**	Wf	21.08.2023
	 * 
	 * @param pID
	 * @param pName
	 */
	public NameElement(int pID, String pName) {
		super(pID);
		
		try {setName(pName);}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------	
	
	/**	Wf	21.08.2023
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	//-----
	
	/**	Wf	21.08.2023
	 * 
	 * @param pName
	 * @throws Exception
	 */
	public void setName(String pName) throws Exception{
		if (pName != null) name = pName;
		else throw new Exception("04; sNa,NaE");
	}
	
}
