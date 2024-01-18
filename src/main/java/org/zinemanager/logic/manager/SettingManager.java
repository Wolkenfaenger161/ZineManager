/**	ZineManager v0.1	Wf	18.01.2024
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

import javax.print.attribute.standard.Sides;

import org.zinemanager.logic.settings.PrintManagerSettings;
import org.zinemanager.logic.settings.ZineManagerSettings;

public class SettingManager extends BasicManager {
	private DatabaseManager databaseManager;
	
	private ZineManagerSettings zineManagerSettings;
	private PrintManagerSettings printManagerSettings;
	
	/**	Wf	18.01.2024
	 * 
	 */
	public SettingManager(DatabaseManager pDatabaseManager) {
		databaseManager = pDatabaseManager;
		
		loadAllSettings();
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 * @return
	 */
	public int getLastDataSetID() {
		return zineManagerSettings.getLastDataSetID();
	}
	/**	Wf	11.11.2023
	 * 
	 * @return
	 */
	public String getCurrentDataSetPath() {
		return zineManagerSettings.getCurrentDataSetPath();
	}
	
	/**	Wf	18.01.2024
	 * 
	 * @return
	 */
	public boolean isDeafultExtracoverPrint() {
		return printManagerSettings.isDefaultExtracoverPrint();
	}
	/**	Wf	18.01.2024
	 * 
	 * @return
	 */
	public Sides getDoublesidePrintart() {
		return printManagerSettings.getDoublesidePrintart();
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
	/**	Wf	11.11.23
	 * 
	 * @param pCurrentDataSetID
	 * @throws Exception
	 */
	public void setCurrentDataSetPath(String pCurrentDataSetPath) throws Exception {
		zineManagerSettings.setCurrentDataSetPath(pCurrentDataSetPath);
	}
	
	/**	Wf	18.01.2024
	 * 
	 * @param pHasDefaultExtracoverPrint
	 */
	public void setDefaultExtracoverPrint(boolean pHasDefaultExtracoverPrint) {
		printManagerSettings.setDefaultExtracoverPrint(pHasDefaultExtracoverPrint);
	}
	/**	Wf	18.01.2024
	 * 
	 * @param pDoublesidePrintart
	 * @throws Exception
	 */
	public void setDoublesidePrintart(Sides pDoublesidePrintart) throws Exception{
		printManagerSettings.setDoublesidePrintart(pDoublesidePrintart);
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 * @throws Exception
	 */
	public void loadZineManagerSettings() throws Exception {
		zineManagerSettings = databaseManager.loadZineManagerSettings();
	}
	/**	Wf	18.01.2024
	 * 
	 * @throws Exception
	 */
	public void loadPrintManagerSettings() throws Exception{
		printManagerSettings = databaseManager.loadPrintManagerSettings();
	}
	//-----
	/**	Wf	18.01.2024
	 * 
	 */
	public void loadAllSettings(){
		try {loadZineManagerSettings();}
		catch(Exception ex) {
			if (ex.getMessage().equals("21; lOfXML,DaM")) zineManagerSettings = new ZineManagerSettings();
			else 										  LogManager.handleException(ex);
		}
		
		try {loadPrintManagerSettings();}
		catch(Exception ex) {
			if (ex.getMessage().equals("21; lOfXML,DaM")) printManagerSettings = new PrintManagerSettings();
			else 										  LogManager.handleException(ex);
		}
	}
	
	//-----
	
	/**	Wf	07.10.2023
	 * 
	 * @throws Exception
	 */
	public void saveZineManagerSettings() throws Exception{
		databaseManager.saveZineManagerSettings(zineManagerSettings);
	}
	/**	Wf	18.01.2024
	 * 
	 * @throws Exception
	 */
	public void savePrintManagerSettings() throws Exception{
		databaseManager.savePrintManagerSettings(printManagerSettings);
	}
	//-----
	/**	Wf	18.01.2024
	 * 
	 */
	public void saveAllSettings() throws Exception{
		saveZineManagerSettings();
		savePrintManagerSettings();
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
