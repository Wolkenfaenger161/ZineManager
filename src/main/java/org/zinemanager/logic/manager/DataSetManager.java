/**	ZineManager v0.0	Wf	14.11.2023
 * 
 * 	logic.manager
 * 	  BasicManager
 * 	  	DataSetManager
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

import java.nio.file.NoSuchFileException;

import org.zinemanager.logic.entities.DataSet;
import org.zinemanager.logic.exceptions.NoFileLoadedException;

public class DataSetManager extends BasicManager {
	private DatabaseManager databaseManager;
	private SettingManager settingManager;

	private DataSet currentDataset;
	
	/**	Wf	12.11.2023
	 * 
	 * @param pDatabaseManager
	 * @param pSettingManager
	 */
	public DataSetManager(DatabaseManager pDatabaseManager, SettingManager pSettingManager) {
		if ((pDatabaseManager != null) && (pSettingManager != null)) {
			databaseManager = pDatabaseManager;
			settingManager = pSettingManager;	
		} else {
			databaseManager = null;
			settingManager = null;
			
			LogManager.handleException(new Exception("04; ZiM,ZiM"));
		}
		
		try{ 
			if (databaseManager != null) {
				try {
					currentDataset = databaseManager.loadDataSet(databaseManager.getCurrentDirectoryFilePath());
					settingManager.setCurrentDataSetPath(databaseManager.getCurrentDirectoryFilePath());
				}catch(NoFileLoadedException nfle) {
					try {
						if ((settingManager != null) && (settingManager.getCurrentDataSetPath() != "")) 
							currentDataset = databaseManager.loadDataSet(settingManager.getCurrentDataSetPath());
						else currentDataset = null;
					}catch(NoFileLoadedException nfle2) { 
						currentDataset = null;
						settingManager.setCurrentDataSetPath("");
					}
				} 
			} else currentDataset = null;
		}catch (Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	11.11.2023
	 * 
	 * @return
	 * @throws Exception
	 */
	protected DataSet getCurrentDataSet(){
		return currentDataset;
	}
	
	/**	Wf	11.11.2023
	 * 
	 * @return
	 * @throws Exception
	 */
	protected DataSet getDatasetCopy() throws Exception{
		return (DataSet)currentDataset.clone();
	}
	
	/**	Wf	11.11.2023
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDataSetName(){
		String vRet = "";
		
		if (currentDataset != null) {
			vRet = currentDataset.getName();
		}
		
		return vRet;
	}
	
	/**	Wf	14.11.2023
	 * 
	 * @return
	 */
	public String getCurrentDirectoryFilePath() {
		return databaseManager.getCurrentDirectoryFilePath();
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	11.11.2023
	 * 
	 * @param pDataSet
	 * @throws Exception
	 */
	protected void setCurrentDataSet(DataSet pDataSet) throws Exception{
		if (pDataSet != null) currentDataset = pDataSet;
		else throw new Exception("04; sCDS,DSM");
	}
	
	/**	Wf	11.11.2023
	 * 
	 * @param pName
	 * @throws Exception
	 */
	public void setCurrentDataSetName(String pName) throws Exception{
		if ((pName != null) && (!pName.equals(""))) currentDataset.setName(pName);
		else throw new Exception("02/04; sCDSN,DSM");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	11.11.2023
	 * 
	 * @param pID
	 * @return
	 */
	public boolean hasDataSetLoaded() {
		return (currentDataset != null);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	12.11.2023
	 * 
	 * @param pName
	 */
	public void createNewDataset() {
		currentDataset = new DataSet(settingManager.getLastDataSetID(), "Useless");
		
		try {
			settingManager.setLastDataSetID(settingManager.determineNextDataSetID());
			settingManager.setCurrentDataSetPath("");
		}catch(Exception ex) {};
	}
	
	/**	Wf	11.11.2023
	 * 
	 * @throws Exception
	 */
	public void deleteDataSet() throws Exception{
		if (settingManager.getCurrentDataSetPath() != "") databaseManager.removeDataSet(settingManager.getCurrentDataSetPath());
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	14.11.2023
	 * 
	 * @throws Exception
	 */
	public void saveDataSet() throws Exception{
		if (!settingManager.getCurrentDataSetPath().equals(""))	exportDataSet(settingManager.getCurrentDataSetPath());
		else exportDataSet(databaseManager.getCurrentDirectoryFilePath());
	}
	
	/**	Wf	13.11.2023
	 * 
	 * @param pFilePath
	 * @throws Exception
	 */
	public void exportDataSet(String pFilePath) throws Exception{
		if (pFilePath != "") {
			if (currentDataset != null) databaseManager.saveDataSet(currentDataset, pFilePath);
			else throw new Exception("04; eDS,DSM");
		} else throw new NoSuchFileException("02; eDS,DSM");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	11.11.2023
	 * 
	 * @throws Exception
	 */
	public void loadDataSet() throws Exception{
		importDataSet(settingManager.getCurrentDataSetPath());
	}
	
	/**	Wf	12.11.2023
	 * 
	 * @param pFilePath
	 * @param pID
	 * @throws Exception
	 */
	public void importDataSet(String pFilePath) throws Exception{
		DataSet vLoadedDataSet;
		
		if (pFilePath != "") {
			vLoadedDataSet = databaseManager.loadDataSet(pFilePath);
			
			if (vLoadedDataSet != null) {
				currentDataset = vLoadedDataSet;
				
				currentDataset.checkAllZinePathValididies();
			}else throw new NoFileLoadedException("04; iDS,DSM");
		} else throw new Exception("02; iDS,DSM");
	}
	
	
}
