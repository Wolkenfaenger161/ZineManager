/**	ZineManager v0.0	Wf	07.10.2023
 * 
 * 	logic.manager
 * 	BasicManager
 * 	  SettingManager
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

package org.zinemanager.logic.manager;

import org.zinemanager.logic.settings.ZineManagerSettings;

public class SettingManager extends BasicManager {
	private DatabaseManager databaseManager;
	
	private ZineManagerSettings zineManagerSettings;
	
	/**	Wf	07.10.2023
	 * 
	 */
	public SettingManager(DatabaseManager pDatabaseManager) {
		databaseManager = pDatabaseManager;
		
		try { loadAllSettings(); }
		catch(Exception ex) {
			if (ex.getMessage().equals("21; lOfXML,DaM")) {
				zineManagerSettings = new ZineManagerSettings();
			}else {
				LogManager.handleException(ex);
			}
		}
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 * @return
	 */
	public int getLastDataSetID() {
		return zineManagerSettings.getLastDataSetID();
	}
	/**	Wf	07.10.2023
	 * 
	 * @return
	 */
	public int getCurrentDataSetID() {
		return zineManagerSettings.getCurrentDataSetID();
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @param pLastDataSetID
	 * @throws Exception
	 */
	public void setLastDataSetID(int pLastDataSetID) throws Exception{
		zineManagerSettings.setLastDataSetID(pLastDataSetID);
	}
	/**	Wf	07.20.23
	 * 
	 * @param pCurrentDataSetID
	 * @throws Exception
	 */
	public void setCurrentDataSetID(int pCurrentDataSetID) throws Exception {
		zineManagerSettings.setCurrentDataSetID(pCurrentDataSetID);
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 * @throws Exception
	 */
	public void loadZineManagerSettings() throws Exception {
		zineManagerSettings = databaseManager.loadZineManagerSettings();
	}
	/**	Wf	07.10.2023
	 * 
	 */
	public void loadAllSettings() throws Exception{
		loadZineManagerSettings();
	}
	
	//-----
	
	/**	Wf	07.10.2023
	 * 
	 * @throws Exception
	 */
	public void saveZineManagerSettings() throws Exception{
		databaseManager.saveZineManagerSettings(zineManagerSettings);
	}
	/**	Wf	07.10.2023
	 * 
	 */
	public void saveAllSettings() throws Exception{
		saveZineManagerSettings();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @return
	 */
	public int determineNextDataSetID() {
		zineManagerSettings.increaseLastDataSetID();
		
		return zineManagerSettings.getLastDataSetID();
	}
	
}
