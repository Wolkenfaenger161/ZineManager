/**	ZineManager v0.2	Wf	20.01.2024
 * 
 * 	logic.manager
 * 	  BasicManager
 * 	  	ZineManager
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
 * 
 * 	  20 Wrong OS
 * 	  21 File dosn't exist
 * 	  22 Wrong Right Error
 */

package org.zinemanager.logic.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.zinemanager.logic.entities.Category;
import org.zinemanager.logic.entities.DataSet;
import org.zinemanager.logic.entities.DatedCount;
import org.zinemanager.logic.entities.IDElement;
import org.zinemanager.logic.entities.ZineElement;
import org.zinemanager.logic.entities.ZineList;
import org.zinemanager.logic.exceptions.NoFileLoadedException;

public class ZineManager extends BasicManager {
	private SettingManager settingManager;
	private DataSetManager dataSetManager;
	private ZinePrintingManager zinePrintingManager;
	
	private DataSet currentDataSet;
	
	/**	Wf	03.12.2023
	 * 
	 * @param pDatabaseManager
	 * @param pSettingManager
	 */
	public ZineManager(SettingManager pSettingManager, DataSetManager pDataSetManager) {
		super();
		LogManager.createLogEntry("Start Init ZineManager");
		if ((pSettingManager != null) && (pDataSetManager != null)) {
			settingManager      = pSettingManager;
			dataSetManager      = pDataSetManager;
			LogManager.createLogEntry("Start Init ZinePrintingManager");
			try {
				zinePrintingManager = new ZinePrintingManager(pSettingManager);
			}catch(Exception ex) {LogManager.handleException(ex);}
		} else {
			settingManager 		= null;
			dataSetManager 		= null;
			zinePrintingManager = null;
			
			LogManager.handleException(new Exception("04; ZiM,ZiM"));
		}
		LogManager.createLogEntry("Finished Init ZinePrintingManager");
		currentDataSet = dataSetManager.getCurrentDataSet();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	10.10.2023
	 * 
	 * @return
	 */
	public DataSetManager getDataSetManager() {
		return dataSetManager;
	}
	/**	Wf	03.12.2023
	 * 
	 * @return
	 */
	public ZinePrintingManager getZinePrintingManager() {
		return zinePrintingManager;
	}
	/**	Wf	19.01.2024
	 * 
	 * @return
	 */
	public SettingManager getSettingManager() {
		return settingManager;
	}
	
	/**	Wf	10.10.2023
	 * 
	 * @return
	 */
	public int getCurrentDataSetID() {
		return (currentDataSet != null) ? currentDataSet.getId() : -1;
	}
	
	//-----
	
	/**	Wf	27.09.202
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	private Category getCategory(int pID) throws Exception {
		return currentDataSet.getCategory(pID);
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	private ZineElement getZine(int pID) throws Exception{
		return currentDataSet.getZine(pID);
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	private ZineList getZineList(int pID) throws Exception{
		return currentDataSet.getZineList(pID);
	}
	
	/**	Wf	27.09.2023
	 * 
	 * @return
	 */
	public ArrayList<Category> getCategories(){
		return currentDataSet.getCategories();
	}
	/**	Wf	27.09.2023
	 * 
	 * @return
	 */
	public ArrayList<ZineElement> getZines(){
		return currentDataSet.getZines();
	}
	/**	Wf	27.09.2023
	 * 
	 * @return
	 */
	public ArrayList<ZineList> getZineLists(){
		return currentDataSet.getZineLists();
	}
	
	/**	Wf	27.09.2023
	 * 
	 * @return
	 */
	public ArrayList<Integer> getCategoryIDs() {
		return createIDList(currentDataSet.getCategories());
	}
	/**	Wf	27.09.2023
	 * 
	 * @return
	 */
	public ArrayList<Integer> getZineIDs(){
		return createIDList(currentDataSet.getZines());
	}
	/**	Wf	27.09.2023
	 * 
	 * @return
	 */
	public ArrayList<Integer> getZineListIDs() {
		return createIDList(currentDataSet.getZineLists());
	}
	
	//-----
	
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public String getCategoryName(int pID) throws Exception {
		Category vTemp = getCategory(pID);
		
		if (vTemp != null) return vTemp.getName();
		else throw new Exception("02; gCN,ZiM");
	}
	
	//-----
	
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public String getZineName(int pID) throws Exception {
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) return vTemp.getName();
		else throw new Exception("02; gZN,ZiM");
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public String getZineFilePath(int pID) throws Exception {
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) return vTemp.getFilePath();
		else throw new Exception("02; gZFP,ZiM");
	}
	
	/**	Wf	20.01.2024
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public boolean hasZineExtraCoverprint(int pID) throws Exception{
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) return vTemp.isExtracoverprint();
		else throw new Exception("02; hZEC,ZiM");
	}
	
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public int getZineQuota(int pID) throws Exception {
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) return vTemp.getQuota();
		else throw new Exception("02; gZQ,ZiM");
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public int getZineDistributedOffset(int pID) throws Exception {
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) return vTemp.getDistributedOffset();
		else throw new Exception("02; gZDO,ZiM");
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public int getZineCategoryID(int pID) throws Exception {
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) return vTemp.getCategoryID();
		else throw new Exception("02; gZCID,ZiM");
	}
	
	/**	Wf	03.09.2023
	 * 
	 * @param pZineID
	 * @param pCountID
	 * @return
	 * @throws Exception
	 */
	public int getZineCountValue(int pZineID, int pCountID) throws Exception {
		ZineElement vTemp = getZine(pZineID);
		DatedCount vCount;
		
		if (vTemp != null) {
			vCount = vTemp.getCount(pCountID);
			
			if (vCount != null) return vCount.getCount();
			else throw new Exception("04b; gZCV,ZiM");
		} else throw new Exception("04; gZCV,ZiM");
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pZineID
	 * @param pCountID
	 * @return
	 * @throws Exception
	 */
	public Date getZineCountDate(int pZineID, int pCountID) throws Exception {
		ZineElement vTemp = getZine(pZineID);
		DatedCount vCount;
		
		if (vTemp != null) {
			vCount = vTemp.getCount(pCountID);
			
			if (vCount != null) return vCount.getDate();
			else throw new Exception("04b; gZCV,ZiM");
		} else throw new Exception("04; gZCV,ZiM");
	}
	//-----
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getZineCountIDs(int pID) throws Exception{
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) return createIDList(vTemp.getCounts());
		else throw new Exception("02; gZCIDs,ZiM");
	}
	
	//-----
	
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public String getZineListName(int pID) throws Exception {
		ZineList vTemp = getZineList(pID);
		
		if (vTemp != null) return vTemp.getName();
		else throw new Exception("02; gZLN,ZiM");
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getZineListZineIDs(int pID) throws Exception{
		ZineList vTemp = getZineList(pID);
		
		if (vTemp != null) return vTemp.getZineIDsCopy();
		else throw new Exception("02; gZLZIDs,ZiM");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @param pName
	 * @throws Exception
	 */
	public void setCategoryName(int pID, String pName) throws Exception{
		Category vTemp = getCategory(pID);
		
		if (vTemp != null) vTemp.setName(pName);
		else throw new Exception("04; sCN,ZiM");
	}
	
	//-----
	
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @param pName
	 * @throws Exception
	 */
	public void setZineName(int pID, String pName) throws Exception{
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) vTemp.setName(pName);
		else throw new Exception("04; sZN,ZiM");
	}
	/**	Wf	10.10.2023
	 * 
	 * @param pID
	 * @param pFilePath
	 * @throws Exception
	 */
	public void setZineFilePath(int pID, String pFilePath) throws Exception{
		ZineElement vTemp = getZine(pID);
		
		if ((vTemp != null) && (isFilePathValid(pFilePath))) vTemp.setFilePath(pFilePath);
		else throw new Exception("04; sZFP,ZiM");
	}
	
	/**	Wf	20.01.2024
	 * 
	 * @param pID
	 * @param pHasExtraCoverprint
	 * @throws Exception
	 */
	public void setZineExtraCoverprint(int pID, boolean pHasExtraCoverprint) throws Exception{
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) vTemp.setExtracoverprint(pHasExtraCoverprint);
		else throw new Exception("04; sZEC,ZiM");
	}
	
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @param pQuota
	 * @throws Exception
	 */
	public void setZineQuota(int pID, int pQuota) throws Exception{
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) vTemp.setQuota(pQuota);
		else throw new Exception("04; sZQ,ZiM");
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @param pDisstributedOffset
	 * @throws Exception
	 */
	public void setZineDistributedOffset(int pID, int pDisstributedOffset) throws Exception{
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) vTemp.setDistributedOffset(pDisstributedOffset);
		else throw new Exception("04; sZDO,ZiM");
	}
	/**	Wf	03.10.2023
	 * 
	 * @param pID
	 * @param pCategoryID
	 * @throws Exception
	 */
	public void setZineCategoryID(int pID, int pCategoryID) throws Exception{
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) {
			if ((pCategoryID == -1) || (hasCategory(pCategoryID))) vTemp.setCategoryID(pCategoryID);
			else throw new Exception("02; sZCID,ZiM");
		} else throw new Exception("04; sZCID,ZiM");
	}
	
	/**	Wf	03.09.2023
	 * 
	 * @param pZineID
	 * @param pCountID
	 * @param pCountValue
	 * @throws Exception
	 */
	public void setZineCountValue(int pZineID, int pCountID, int pCountValue) throws Exception{
		ZineElement vTemp = getZine(pZineID);
		DatedCount vCount;
		
		if (vTemp != null) {
			vCount = vTemp.getCount(pCountID);
			
			if (vCount != null) vCount.setCount(pCountValue);
			else throw new Exception("04b; sZCV,ZiM");
		}else throw new Exception("04; sZCV,ZiM");
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pZineID
	 * @param pCountID
	 * @param pCountDate
	 * @throws Exception
	 */
	public void setZineCountDate(int pZineID, int pCountID, Date pCountDate) throws Exception{
		ZineElement vTemp = getZine(pZineID);
		DatedCount vCount;
		
		if (vTemp != null) {
			vCount = vTemp.getCount(pCountID);
			
			if (vCount != null) vCount.setDate(pCountDate);
			else throw new Exception("04b; sZCD,ZiM");
		}else throw new Exception("04; sZCD,ZiM");
	}
	
	//-----
	
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @param pName
	 * @throws Exception
	 */
	public void setZineListName(int pID, String pName) throws Exception{
		ZineList vTemp = getZineList(pID);
		
		if (vTemp != null) vTemp.setName(pName);
		else throw new Exception("04; sZLN,ZiM");
	}
	/**	Wf	04.09.2023
	 * 
	 * @param pID
	 * @param pZineIDs
	 * @throws Exception
	 */
	public void setZineListZindIDs(int pID, ArrayList<Integer> pZineIDs) throws Exception{
		ZineList vTemp = getZineList(pID);
		
		if (vTemp != null) {
			if (pZineIDs != null) {
				for (Integer vZineID : pZineIDs) {
					if (!hasZine(vZineID)) throw new Exception("02; sZLZIDs,ZiM");
 				
					vTemp.setZineIDs(pZineIDs);
				}
			}else throw new Exception("04b; sZLZIDs,ZiM");
		} else throw new Exception("04; sZLZIDs,ZiM");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	10.10.2023
	 * 
	 * @return
	 */
	public boolean isDataSetLoaded() {
		return (currentDataSet != null);
	}
	
	/**	Wf	11.11.2023
	 * 
	 * @return
	 */
	public boolean hasCurrentDatasetPath() {
		return !settingManager.getCurrentDataSetPath().equals("");
	}
	
	//-----
	
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public boolean hasCategory(int pID) throws Exception {
		return (getCategory(pID) != null);
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public boolean hasZine(int pID) throws Exception{
		return (getZine(pID) != null);
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public boolean hasZineList(int pID) throws Exception{
		return (getZineList(pID) != null);
	}
	
	//-----
	
	/**	Wf	03.09.2023
	 * 
	 * @param pZineID
	 * @param pCountID
	 * @return
	 * @throws Exception
	 */
	public boolean hasZineCount(int pZineID, int pCountID) throws Exception{
		ZineElement vTemp = getZine(pZineID);
		
		if (vTemp != null) return vTemp.hasCount(pCountID);
		else throw new Exception("02; hZLZID,ZiM");
	}
	
	//-----
	
	/**	Wf	03.09.2023
	 * 
	 * @param pZineListID
	 * @param pZineID
	 * @return
	 * @throws Exception
	 */
	public boolean hasZineListZineID(int pZineListID, int pZineID) throws Exception{
		ZineList vTemp = getZineList(pZineListID);
		
		if (vTemp != null) return vTemp.hasZineID(pZineID);
		else throw new Exception("02; hZLZID,ZiM");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	27.09.2023
	 * 
	 * @param pName
	 * @return
	 * @throws Exception
	 */
	public int addCategory(String pName) throws Exception{
		int vID = calculateNewID(currentDataSet.getCategories());
		
		currentDataSet.addCategory(vID, pName);
		
		return vID;
	}
	/**	Wf	20.01.2024
	 * 
	 * @param pName
	 * @param pQuota
	 * @param pDistributedOffset
	 * @param pCategoryID
	 * @param pFilePath
	 * @return
	 * @throws Exception
	 */
	public int addZine(String pName, int pQuota, int pDistributedOffset, int pCategoryID, String pFilePath) throws Exception{
		return this.addZine(pName, settingManager.isDeafultExtracoverPrint(), pQuota, pDistributedOffset, pCategoryID, pFilePath, new ArrayList<DatedCount>());
	}
	/**	Wf	20.01.2024
	 * 
	 * @param pName
	 * @param pHasExtraCoverprint
	 * @param pQuota
	 * @param pDistributedOffset
	 * @param pCategoryID
	 * @param pFilePath
	 * @param pCounts
	 * @return
	 * @throws Exception
	 */
	public int addZine(String pName, boolean pHasExtraCoverprint, int pQuota, int pDistributedOffset, int pCategoryID, String pFilePath, ArrayList<DatedCount> pCounts) throws Exception{
		int vID = calculateNewID(currentDataSet.getZines());
		
		currentDataSet.addZine(vID, pName, pHasExtraCoverprint, pQuota, pDistributedOffset, pCategoryID, pFilePath, pCounts);
		
		return vID;
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pName
	 * @param pZineIDs
	 * @return
	 * @throws Exception
	 */
	public int addZineList(String pName, ArrayList<Integer> pZineIDs) throws Exception{
		int vID = calculateNewID(currentDataSet.getZineLists());
		
		currentDataSet.addZineList(vID, pName, pZineIDs);
		
		return vID;
	}
	
	//-----
	
	/**	Wf	03.09.2023
	 * 
	 * @param pZineID
	 * @param pCountValue
	 * @param pCountDate
	 * @return
	 * @throws Exception
	 */
	public int addCountToZine(int pZineID, int pCountValue, Date pCountDate) throws Exception{
		int vID = -1;
		ZineElement vTemp = getZine(pZineID);
		
		if (vTemp != null) {
			vID = calculateNewID(vTemp.getCounts());
			
			if (vID != -1) vTemp.addCount(new DatedCount(vID, pCountValue, pCountDate));
			else throw new Exception("04b; aZIDtZL,ZiM");
		} else throw new Exception("04; aZIDtZL,ZiM");
		
		return vID;
	}
	
	//-----
	
	/**	Wf	04.09.2023
	 * 
	 * @param pZineListID
	 * @param pZineID
	 * @throws Exception
	 */
	public void addZineIDToZineList(int pZineListID, int pZineID) throws Exception{
		ZineList vTemp = getZineList(pZineID);
		
		if (vTemp != null) {
			if (hasZine(pZineID)) vTemp.addZineID(pZineID);
			else throw new Exception("02; aZIDtZL,ZiM");
		}
		else throw new Exception("04; aZIDtZL,ZiM");
	}
	
	//-----
	
	/**	Wf	03.12.2023
	 * 
	 * @param pZineID
	 * @throws Exception
	 */
	public void addZineToPrintingManager(int pZineID) throws Exception{
		zinePrintingManager.addPrintingElement(getZine(pZineID));
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void removeCategory(int pID) throws Exception{
		currentDataSet.removeCategory(pID);
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void removeZine(int pID) throws Exception{
		currentDataSet.removeZine(pID);
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void removeZineList(int pID) throws Exception{
		currentDataSet.removeZineList(pID);
	}
	
	//-----
	
	/**	Wf	03.09.2023
	 * 
	 * @param pZineID
	 * @param pCountID
	 * @throws Exception
	 */
	public void removeCountFromZine(int pZineID, int pCountID) throws Exception{
		ZineElement vTemp = getZine(pZineID);
		
		if (vTemp != null) vTemp.removeCount(pCountID);
		else throw new Exception("02; rCfZ,ZiM");
	}
	
	//-----

	/**	Wf	03.09.2023
	 * 
	 * @param pZineListID
	 * @param pZineID
	 * @throws Exception
	 */
	public void removeZineIDFromZineList(int pZineListID, int pZineID) throws Exception{
		ZineList vTemp = getZineList(pZineListID);
		
		if (vTemp != null) vTemp.removeZineID(pZineID);
		else throw new Exception("02; rZIDfZL,ZiM");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	12.11.2023
	 * 
	 */
	public void createNewDataset() {
		dataSetManager.createNewDataset();
		currentDataSet = dataSetManager.getCurrentDataSet();
	}
	
	/**	Wf	11.11.2023
	 * 
	 * @throws Exception
	 */
	public void saveDataSet(String pFilePath) throws Exception {
		if (pFilePath != null) {
			if (!pFilePath.equals("")) {
				dataSetManager.exportDataSet(pFilePath);
				settingManager.setCurrentDataSetPath(pFilePath);
			}else dataSetManager.saveDataSet();
		} else throw new Exception("04; sDS,ziM");
	}
	/**	Wf	11.11.2023
	 * 
	 * @param pFilePath
	 * @throws Exception
	 */
	public void exportDataSet(String pFilePath) throws Exception{
		if (pFilePath != null) {
			if (!pFilePath.equals("")) dataSetManager.exportDataSet(pFilePath);
			else throw new Exception("02; eDS,ZiM");
		} else throw new Exception("04; eDS,ziM");
	}
	
	//-----
	
	/**	Wf	12.11.2023
	 * 
	 * @throws Exception
	 */
	public void loadDataSet(String pFilePath) throws Exception {
		DataSet vTemp;
		
		if (pFilePath != null) {
			if (pFilePath.equals("")) dataSetManager.loadDataSet();
			else dataSetManager.importDataSet(pFilePath);
				
			vTemp = dataSetManager.getCurrentDataSet();
			if (vTemp != null) {
				currentDataSet = vTemp;
				
				vTemp.checkAllZinePathValididies();
				if (pFilePath != "") settingManager.setCurrentDataSetPath(pFilePath);
			}else throw new NoFileLoadedException("04; lDS,ZiM");
		}else throw new Exception("04; lDS,ZiM");
	}
	/**	Wf	12.11.2023
	 * 
	 * @param pFilePath
	 * @throws Exception
	 */
	public void importDataSet(String pFilePath, boolean pDeletePath) throws Exception{
		int vOldID;
		DataSet vTemp;
		
		if (currentDataSet != null) vOldID = currentDataSet.getId();
		else vOldID = -1;
		
		dataSetManager.importDataSet(pFilePath);
		
		vTemp = dataSetManager.getCurrentDataSet();
		if (vTemp != null) {
			currentDataSet = vTemp;
			
			if (pDeletePath) {
				settingManager.setCurrentDataSetPath("");
				
				currentDataSet.setId(settingManager.getLastDataSetID());
				settingManager.setLastDataSetID(settingManager.determineNextDataSetID());
			}else if (vOldID != -1) currentDataSet.setId(vOldID);
			else throw new Exception("02; iDs,ZiM");
			
			vTemp.checkAllZinePathValididies();
		}else throw new NoFileLoadedException("04; iDS,ZiM");
	}
		
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public boolean isZineFilePathValid(int pID) throws Exception{
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) return vTemp.isFilePathValid();
		else throw new Exception("02; rCfZ,ZiM");
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pFilePath
	 * @return
	 */
	public boolean isFilePathValid(String pFilePath) {
		return ZineElement.isFilePathValid(pFilePath);
	}
	
//--------------------------------------------------------------------------------------------------------

	/**Wf	18.01.2024
	 * 
	 * @throws Exception
	 */
	public void updatePrintedZineCounts(Date pPrintDate) throws Exception{
		ArrayList<Integer> vPrintingZineIDs = zinePrintingManager.getPrintingElementIDs();
		
		if (pPrintDate != null) {
			for (Integer vID : vPrintingZineIDs) {
				if (zinePrintingManager.hasPrintingElementFinishedPrinting(vID) && (zinePrintingManager.getPrintingElementPrinting(vID) > 0)) {
					addCountToZine(vID, zinePrintingManager.getPrintingElementPrinting(vID), pPrintDate);
				}
			}
		}else throw new Exception("04; uPZC,ZiM");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	04.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public int calculateTotalDistributedValueOfZine(int pID) throws Exception {
		int vRet = -1;
		int vDisValue;
		DatedCount vPreCount = null;
		ZineElement vZine = getZine(pID);
		ArrayList<DatedCount> vTemp;
		
		if (vZine != null) {
			vRet = vZine.getDistributedOffset();
			vTemp = vZine.getCountsCopy();
			
			vTemp.sort((pCount1, pCount2) -> {return pCount1.getDate().compareTo(pCount2.getDate());});
			for (DatedCount vCount : vTemp) {
				if (vPreCount != null) {
					vDisValue = vPreCount.getCount() - vCount.getCount();
						
					if (vDisValue > 0) vRet += vDisValue;
				}
				
				vPreCount = vCount;
			}
		}else throw new Exception("04; cDVoZ,ZiM");
		
		return vRet;
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @param pStartDate
	 * @param pEndDate
	 * @return
	 * @throws Exception
	 */
	public int calculateDistributedValueOfZineBetween(int pID, Date pStartDate, Date pEndDate) throws Exception{
		int vRet = -1;
		int vDisValue;
		DatedCount vPreCount = null;
		ZineElement vZine = getZine(pID);
		ArrayList<DatedCount> vTemp;
		
		if (vZine != null) {
			vRet = vZine.getDistributedOffset();
			vTemp = vZine.getCountsCopy();
			
			vTemp.sort((pCount1, pCount2) -> {return pCount1.getDate().compareTo(pCount2.getDate());});
			for (DatedCount vCount : vTemp) {
				if (vPreCount != null) {
					if (vCount.getDate().after(pStartDate) && vCount.getDate().before(pEndDate)) {
						vDisValue = vPreCount.getCount() - vCount.getCount();
						
						if (vDisValue > 0) vRet += vDisValue;
					}
				}
				
				vPreCount = vCount;
			}
		}else throw new Exception("04; cDVoZb,ZiM");
		
		return vRet;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	20.01.2024
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void openZine(int pID) throws Exception {
		ZineElement vTemp = getZine(pID);
		String vPDFFilePath, vPDFReaderFilePath;
		
		File vPDFReaderFile;
		ProcessBuilder vProcessBuilder;
		
		if (vTemp != null) {
			vPDFFilePath = vTemp.getFilePath();
			
			if (isFilePathValid(vPDFFilePath) && !vPDFFilePath.equals("")) {
				vPDFReaderFilePath = settingManager.getPDFReaderFilePath();
				vProcessBuilder = new ProcessBuilder();
				
				if (vPDFReaderFilePath.equals("")) {
					if (System.getProperty("os.name").contains("Windows")) {
						vProcessBuilder.command("explorer", vPDFFilePath);
					}else if (System.getProperty("os.name").contains("Linux")) {
						vProcessBuilder.command("xdg-open", vPDFFilePath);
					}else throw new Exception("20; oZi,ZiM");
				}else {
					vPDFReaderFile = new File(vPDFReaderFilePath);
					
					if (vPDFReaderFile.exists() && vPDFReaderFile.canExecute()) {
						vProcessBuilder.command(vPDFReaderFilePath, vPDFFilePath);
					}else throw new Exception("21/22; oZi,ZiM");
				}
				
				vProcessBuilder.start();
			}else throw new Exception("02; oZi,ZiM");
		}else throw new Exception("04; oZi,ZiM");
	}
	
//--------------------------------------------------------------------------------------------------------
		
	/**	Wf	16.09.2023
	 * 
	 * @param <T>
	 * @param pIDElementList
	 * @return
	 */
	private <T extends IDElement> int calculateNewID(ArrayList<T> pIDElementList) {
		int vRet = 0;
		
		for (int i=0; i<pIDElementList.size(); i++) {
			if (vRet == pIDElementList.get(i).getId()) {
				i = -1;
				vRet ++;
			}
		}
		
		return vRet;
	}
	
}
