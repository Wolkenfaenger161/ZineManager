/**	ZineManager v0.0	Wf	11.10.2023
 * 
 * 	logic.entities
 * 	IDElement
 * 	  NameElement
 * 		DataSet
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

import java.util.ArrayList;

import org.zinemanager.logic.exceptions.InvalidZinePathFileException;
import org.zinemanager.logic.manager.LogManager;

public class DataSet extends NameElement {
	protected ArrayList<Category> categories;
	protected ArrayList<ZineElement> zines;
	protected ArrayList<ZineList> zineLists;

	/**	Wf	27.09.2023
	 * 
	 */
	public DataSet() {
		super();
		
		categories = new ArrayList<Category>();
		zines 	   = new ArrayList<ZineElement>();
		zineLists  = new ArrayList<ZineList>();
	}
	
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @param pName
	 */
	public DataSet(int pID, String pName) {
		super(pID, pName);
		
		categories = new ArrayList<Category>();
		zines 	   = new ArrayList<ZineElement>();
		zineLists  = new ArrayList<ZineList>();
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @param pName
	 * @param pCategories
	 * @param pZines
	 * @param pZineLists
	 */
	public DataSet(int pID, String pName, ArrayList<Category> pCategories, ArrayList<ZineElement> pZines, ArrayList<ZineList> pZineLists) {
		super(pID, pName);
		
		try {
			setCategories(pCategories);
			setZines(pZines);
			setZineLists(pZineLists);
		}catch(Exception ex) {
			LogManager.handleException(ex);
			
			categories = new ArrayList<Category>();
			zines 	   = new ArrayList<ZineElement>();
			zineLists  = new ArrayList<ZineList>();
		}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public Category getCategory(int pID) throws Exception {
		Category vRet = null;
		
		if (pID >= 0) {
			for (int i=0; (i<categories.size()) && (vRet == null); i++) {
				if (categories.get(i).getId() == pID) vRet = categories.get(i);
			}
		}else throw new Exception("02; gCa,DaS");
		
		return vRet;
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public ZineElement getZine(int pID) throws Exception{
		ZineElement vRet = null;
		
		if (pID >= 0) {
			for (int i=0; (i<zines.size()) && (vRet == null); i++) {
				if (zines.get(i).getId() == pID) vRet = zines.get(i);
			}
		}else throw new Exception("02; gZi,DaS");
		
		return vRet;
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public ZineList getZineList(int pID) throws Exception{
		ZineList vRet = null;
		
		if (pID >= 0) {
			for (int i=0; (i<zineLists.size()) && (vRet == null); i++) {
				if (zineLists.get(i).getId() == pID) vRet = zineLists.get(i);
			}
		}else throw new Exception("02; gZL,DaS");
		
		return vRet;
	}
	
	/**	Wf	27.09.2023
	 * 
	 * @return
	 */
	public ArrayList<Category> getCategories(){
		return categories;
	}
	/**	Wf	27.09.2023
	 * 
	 * @return
	 */
	public ArrayList<ZineElement> getZines(){
		return zines;
	}
	/**	Wf	27.09.2023
	 * 
	 * @return
	 */
	public ArrayList<ZineList> getZineLists(){
		return zineLists;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	27.09.2023
	 * 
	 * @param pCategory
	 * @throws Exception
	 */
	public void setCategories(ArrayList<Category> pCategory) throws Exception{
		if (pCategory != null) {
			if (isValidIDList(pCategory)) categories = pCategory;
			else throw new Exception("02; sCa,DaS"); 
		}else throw new Exception("04; sCa,DaS");
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pZines
	 * @throws Exception
	 */
	public void setZines(ArrayList<ZineElement> pZines) throws Exception{
		if (pZines != null) {
			if (isValidIDList(pZines)) zines = pZines;
			else throw new Exception("02; sZin,DaS"); 
		}else throw new Exception("04; sZin,DaS");
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pZineList
	 * @throws Exception
	 */
	public void setZineLists(ArrayList<ZineList> pZineList) throws Exception{
		if (pZineList != null) {
			if (isValidIDList(pZineList)) zineLists = pZineList;
			else throw new Exception("02; sZiL,DaS"); 
		}else throw new Exception("04; sZiL,DaS");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public boolean hasCategory(int pID) throws Exception {
		return (getCategory(pID) != null);
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public boolean hasZine(int pID) throws Exception{
		return (getZine(pID) != null);
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public boolean hasZineList(int pID) throws Exception{
		return (getZineList(pID) != null);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	27.09.2023
	 * 
	 * @param pNewID
	 * @param pName
	 * @throws Exception
	 */
	public void addCategory(int pNewID, String pName) throws Exception{		
		if ((pName != null) && (!pName.equals(""))) {
			if ((pNewID != -1) && (!hasCategory(pNewID))) categories.add(new Category(pNewID, pName));
			else throw new Exception("02; aCa,DaS");
		} else throw new Exception("02/04; aCa,DaS");
	}
	/**	Wf	01.10.2023
	 * 
	 * @param pNewID
	 * @param pName
	 * @param pQuota
	 * @param pDistributedOffset
	 * @param pCategoryID
	 * @param pFilePath
	 * @param pCounts
	 * @throws Exception
	 */
	public void addZine(int pNewID, String pName, int pQuota, int pDistributedOffset, int pCategoryID, String pFilePath, ArrayList<DatedCount> pCounts) throws Exception{
		boolean vDoubledID = false;
		ArrayList<Integer> vExistingCountIDs = new ArrayList<Integer>();
		
		if ((pName != null) && (pFilePath != null) && (pCounts != null)) {
			if ((pQuota >= 0) && (pDistributedOffset >= 0) && (pCategoryID >= -1) && (ZineElement.isFilePathValid(pFilePath))) {
				for (DatedCount vCount : pCounts) {
					if (vExistingCountIDs.contains(Integer.valueOf(vCount.getId()))) vDoubledID = true;
					
					vExistingCountIDs.add(Integer.valueOf( vCount.getId() ));
				}
				
				if ((vDoubledID == false) && (pNewID != -1) && (!hasZine(pNewID))) zines.add(new ZineElement(pNewID, pName, pQuota, pDistributedOffset, pCategoryID, pFilePath, pCounts));
				else throw new Exception("02b; aZi,DaS");
			} else throw new Exception("02; aZi,DaS");
		} else throw new Exception("04; aZi,DaS");

	}
	/**	Wf	27.09.2023
	 * 
	 * @param pNewID
	 * @param pName
	 * @param pZineIDs
	 * @throws Exception
	 */
	public void addZineList(int pNewID, String pName, ArrayList<Integer> pZineIDs) throws Exception{
		if ((pName != null) && (pZineIDs != null)) {
			if (!pName.equals("")) {
				for (Integer vZineID : pZineIDs) {
					if (!hasZine(vZineID)) throw new Exception("02b; aZL,ZiM");
				}
				
				if ((pNewID != -1) && (!hasZineList(pNewID))) zineLists.add(new ZineList(pNewID, pName, pZineIDs));
				else throw new Exception("02b; aZL,DaS");
			} else throw new Exception("02; aZL,DaS");
		} else throw new Exception("04; aZL,DaS");
	}
	
	//----------------------------------------------------------------------------------------------------

	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void removeCategory(int pID) throws Exception{
		Category vTemp = getCategory(pID);
		
		if (vTemp != null) {
			for (ZineElement vZine : zines) {
				if (vZine.getCategoryID() == pID) vZine.setCategoryID(-1);
			}
			
			categories.remove(vTemp);
		}
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void removeZine(int pID) throws Exception{
		ZineElement vTemp = getZine(pID);
		
		if (vTemp != null) {
			for (ZineList vZineList : zineLists) {
				if (vZineList.hasZineID(pID)) vZineList.removeZineID(pID);
			}
			
			zines.remove(vTemp);
		}
	}
	/**	Wf	27.09.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void removeZineList(int pID) throws Exception{
		ZineList vTemp = getZineList(pID);
		
		if (vTemp != null) zineLists.remove(vTemp);
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	19.10.2023
	 * 
	 * @throws InvalidZinePathFileException
	 */
	public void checkAllZinePathValididies() throws InvalidZinePathFileException {
		boolean vInvalidZinePath = false;
		ArrayList<Integer> vInvalidFilePathZineIDs = new ArrayList<Integer>();
		
		for (ZineElement vZine : zines) {
			if (!vZine.isFilePathValid()) {
				vInvalidZinePath = true;
				
				vInvalidFilePathZineIDs.add(Integer.valueOf(vZine.getId()));
			}
		}
		
		if (vInvalidZinePath) throw new InvalidZinePathFileException("cAZPV,DaS", vInvalidFilePathZineIDs);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	27.09.202
	 * 
	 */
	public DataSet clone(int pID) throws Exception {
		DataSet vRet = null;
		
		ArrayList<Category> pClonedCategories = cloneIDElementList(categories);
		ArrayList<ZineElement> pClonedZines   = cloneIDElementList(zines);
		ArrayList<ZineList> pClonedZineLists  = cloneIDElementList(zineLists);
		
		if (pID >= -1) vRet = new DataSet(pID, super.name, pClonedCategories, pClonedZines, pClonedZineLists);
		else throw new Exception("02; clo,DaS");
		
		return vRet;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	27.09.2023
	 * 
	 * @param pIDList
	 * @return
	 */
	private boolean isValidIDList(ArrayList<? extends IDElement> pIDList) {
		boolean vRet = true;
		ArrayList<Integer> vIDs = new ArrayList<Integer>();
		
		for (IDElement vIDElement : pIDList) {
			if ((vIDElement == null) || (vIDs.contains(Integer.valueOf(vIDElement.getId())))) vRet = false;
			else vIDs.add(Integer.valueOf(vIDElement.getId()));
		}
		
		return vRet;
	}
	
	/**	Wf	11.10.2023
	 * 
	 * @param <T>
	 * @param pIDElementList
	 * @return
	 */
	private <T extends IDElement> ArrayList<T> cloneIDElementList(ArrayList<T> pIDElementList) throws Exception{
		ArrayList<T> vRet = new ArrayList<T>();
		
		for (T vIDElement : pIDElementList) {
			vRet.add((T)vIDElement.clone(vIDElement.getId()));
		}
		
		return vRet;
	}
	
}
