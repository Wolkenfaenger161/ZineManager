/**	ZineManager v0.0	Wf	19.10.2023
 * 
 * 	logic.manager
 * 	BasicManager
 * 	  DataSetManager
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

import java.util.ArrayList;

import org.zinemanager.logic.entities.DataSet;

public class DataSetManager extends BasicManager {
	private DatabaseManager databaseManager;
	private SettingManager settingManager;

	private ArrayList<DataSet> dataSets;
	
	/**	Wf	07.10.2023
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
			if (databaseManager != null) dataSets = databaseManager.loadAllDataSets();
			else dataSets = new ArrayList<DataSet>();
		} catch (Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	protected DataSet getDataSet(int pID) throws Exception{
		DataSet vRet = null;
		
		if (pID >= 0) {
			for (DataSet vDataSet : dataSets) {
				if (vDataSet.getId() == pID) vRet = vDataSet;
			}
		} else throw new Exception("02; gDS,DSM");
		
		return vRet;
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @return
	 * @throws Exception
	 */
	protected ArrayList<DataSet> getDatasetCopy() throws Exception{
		ArrayList<DataSet> vRet = new ArrayList<DataSet>();
		
		for (DataSet vtemp : dataSets) {
			try { vRet.add(vtemp.clone(vtemp.getId())); }
			catch(Exception ex) {throw ex;}
		}
		
		return vRet;
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public String getDataSetName(int pID) throws Exception{
		String vRet = "";
		
		if (pID >= 0) {
			for (DataSet vDataSet : dataSets) {
				if (vDataSet.getId() == pID) vRet = vDataSet.getName();
			}
		} else throw new Exception("02; gDSN,DSM");
		
		return vRet;
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @return
	 */
	public ArrayList<Integer> getDataSetIDs() {
		return createIDList(dataSets);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @param pDataSet
	 * @throws Exception
	 */
	protected void setDataSet(DataSet pDataSet) throws Exception{
		boolean vDataSetIsSet = false;
		
		if (pDataSet != null) {
			for (DataSet vDataSet : dataSets) {
				if (vDataSet.getId() == pDataSet.getId()) {
					replaceDataSet(pDataSet, vDataSet);
					
					vDataSetIsSet = true;
				}
			}
			
			if (!vDataSetIsSet) throw new Exception("02; sDS,DSM");
		}else throw new Exception("04; sDS,DSM");
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @param pID
	 * @param pName
	 * @throws Exception
	 */
	public void setDataSetName(int pID, String pName) throws Exception{
		boolean vNameAlreadsUsed = false;
		DataSet vTemp = null;
		
		if ((pName != null) && (!pName.equals(""))) {
			for (DataSet vDataSet : dataSets) {
				if (vDataSet.getId() == pID) vTemp = vDataSet;
				else if (vDataSet.getName().equals(pName)) vNameAlreadsUsed = true;
			}
			
			if ((vTemp != null) && (!vNameAlreadsUsed)) vTemp.setName(pName);
			else throw new Exception("02; sDSN,DSM");
		}
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @param pID
	 * @return
	 */
	public boolean hasDataSet(int pID) {
		boolean vRet = false;
		
		for (DataSet vTemp : dataSets) {
			if (vTemp.getId() == pID) vRet = true;
		}
		
		return vRet;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @param pName
	 * @return
	 * @throws Exception
	 */
	public int addDataSet(String pName) throws Exception{
		int vRet = -1;
		boolean vIsNameAlreadyUsed = false;
		
		if ((pName != null) && (!pName.equals(""))) {
			for (DataSet vDataSet : dataSets) {
				if (vDataSet.getName().equals(pName)) vIsNameAlreadyUsed = true;
			}
			
			if (!vIsNameAlreadyUsed) {
				vRet = settingManager.determineNextDataSetID();
				dataSets.add( new DataSet(vRet, pName) );
			} else throw new Exception("02; aDS,DSM");
		} else throw new Exception("04/02; aDS,DSM");
		
		return vRet;
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void removeDataSet(int pID) throws Exception{
		DataSet vTemp = getDataSet(pID);
		
		if (vTemp != null) {
			databaseManager.removeDataSet(pID);
			dataSets.remove(vTemp);
		}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void saveDataSet(int pID) throws Exception{
		DataSet vTemp = getDataSet(pID);
		
		if (vTemp != null) databaseManager.saveDataSet(vTemp);
		else throw new Exception("02; sDS,DSM");
	}
	/**	Wf	07.10.2023
	 * 
	 * @throws Exception
	 */
	public void saveAllDataSets() throws Exception {
		for (DataSet vDataSet : dataSets) {
			databaseManager.saveDataSet(vDataSet);
		}
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @param pID
	 * @param pFilePath
	 * @throws Exception
	 */
	public void exportDataSet(int pID, String pFilePath) throws Exception{
		DataSet vTemp = getDataSet(pID);
		
		if (vTemp != null) databaseManager.exportDataSet(vTemp.clone(-1), pFilePath);
		else throw new Exception("04; eDS,DSM");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	19.10.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void loadDataSet(int pID) throws Exception{
		replaceDataSet(databaseManager.loadDataSet(pID), getDataSet(pID));
		
		getDataSet(pID).checkAllZinePathValididies();
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @throws Exception
	 */
	public void loadAllDataSets() throws Exception{
		dataSets = databaseManager.loadAllDataSets();
	}
	
	/**	Wf	19.10.2023
	 * 
	 * @param pFilePath
	 * @param pID
	 * @throws Exception
	 */
	public int importDataSet(String pFilePath, int pID) throws Exception{
		int vRet = pID;
		DataSet vImportetDataSet = databaseManager.importDataSet(pFilePath);
		
		if (vImportetDataSet != null) {
			if (hasDataSet(pID) || (pID == -1)) {
				if (pID == -1) {
					vRet = settingManager.determineNextDataSetID();
					vImportetDataSet.setId( vRet );
					
					dataSets.add(vImportetDataSet);
				}else replaceDataSet(vImportetDataSet, getDataSet(pID));
				
				vImportetDataSet.checkAllZinePathValididies();
			} else throw new Exception("02; iDS,DSM");			
		}else throw new Exception("04; iDS,DSM");
		
		return vRet;
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	15.10.2023
	 * 
	 * @param pNewDataSet
	 * @param pOldDataSet
	 * @throws Exception
	 */
	private void replaceDataSet(DataSet pNewDataSet, DataSet pOldDataSet) throws Exception{
		if ((pNewDataSet != null) && (pOldDataSet != null)) {
			pOldDataSet.setName(pNewDataSet.getName());
			
			pOldDataSet.setCategories(pNewDataSet.getCategories());
			pOldDataSet.setZines(pNewDataSet.getZines());
			pOldDataSet.setZineLists(pNewDataSet.getZineLists());
		}else throw new Exception("04; rDS,DSM");
	}
	
}
