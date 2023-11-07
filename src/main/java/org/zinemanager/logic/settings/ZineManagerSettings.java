/**	ZineManager v0.0	Wf	07.10.2023
 * 
 * 	BasicSetting
 * 	  ZineManagerSettings
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

package org.zinemanager.logic.settings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZineManagerSettings extends BasicSetting {
	@JsonProperty("lastDataSetID")
	private int lastDataSetID;
	@JsonProperty("currentDataSetID")
	private int currentDataSetID;
	
	/**	Wf	07.10.2023
	 * 
	 */
	public ZineManagerSettings() {
		lastDataSetID = -1;
		currentDataSetID = -1;
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 * @return
	 */
	public int getLastDataSetID() {
		return lastDataSetID;
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @return
	 */
	public int getCurrentDataSetID() {
		return currentDataSetID;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @param pLastDataSetID
	 * @throws Exception
	 */
	public void setLastDataSetID(int pLastDataSetID) throws Exception {
		if (pLastDataSetID >= -1) lastDataSetID = pLastDataSetID;
		else throw new Exception("02; sLDSID,ZMS");
	}
	
	/**	Wf	07.10.2032
	 * 
	 * @param pCurrentDataSetID
	 * @throws Exception
	 */
	public void setCurrentDataSetID(int pCurrentDataSetID) throws Exception{
		if (pCurrentDataSetID >= -1) currentDataSetID = pCurrentDataSetID;
		else throw new Exception("02; sCDSID,ZMS");
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 */
	public void increaseLastDataSetID() {
		lastDataSetID ++;
	}
	
}
