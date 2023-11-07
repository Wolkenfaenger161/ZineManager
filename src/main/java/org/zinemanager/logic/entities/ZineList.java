/**	ZineManager v0.0	Wf	01.09.2023
 * 
 * 	logic.entities
 * 	IDElement
 * 	  NameElement
 * 		ZineList
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

import org.zinemanager.logic.manager.LogManager;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ZineList extends NameElement {
	protected ArrayList<Integer> zineIDs;
	
	/**	Wf	01.09.2023
	 * 
	 */
	public ZineList() {
		super();
		
		zineIDs = new ArrayList<Integer>();
	}
	/**	Wf	01.09.2023
	 * 
	 * @param pID
	 * @param pName
	 * @param pZineIDs
	 */
	public ZineList(int pID, String pName, ArrayList<Integer> pZineIDs) {
		super(pID, pName);
		
		try {setZineIDs(pZineIDs);}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	01.09.2023
	 * 
	 */
	public void setName(String pName) throws Exception{
		if ((pName != null) && (!pName.equals(""))) name = pName;
		else throw new Exception("02; sNa,ZiL");
	}

	/**	Wf	01.09.2023
	 * 
	 * @param pZineIDs
	 * @throws Exception
	 */
	public void setZineIDs(ArrayList<Integer> pZineIDs) throws Exception {
		ArrayList<Integer> vExistingIds = new ArrayList<Integer>();
		
		if (pZineIDs != null) {
			for (Integer vZineID : pZineIDs) {
				if (vZineID < 0) throw new Exception("02a; sZIDs,ZiL");
				if (vExistingIds.contains(vZineID)) throw new Exception("02b; sZIDs,ZiL");
				
				vExistingIds.add(vZineID);
			}
			
			zineIDs = pZineIDs;
		}else throw new Exception("04; sZIDs,ZiL");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	01.09.2023
	 * 
	 * @return
	 */
	public ArrayList<Integer> getZineIDs() {
		return zineIDs;
	}
	/**	Wf	01.09.2023
	 * 
	 * @return
	 */
	@JsonIgnore
	public ArrayList<Integer> getZineIDsCopy(){
		ArrayList<Integer> vRet = new ArrayList<Integer>();
			
			
		for (Integer vZineID : zineIDs) {
			vRet.add(Integer.valueOf(vZineID.intValue()));
		}
			
		return vRet;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	01.09.2023
	 * 
	 * @param pZineID
	 * @return
	 */
	public boolean hasZineID(int pZineID) {
		return zineIDs.contains(pZineID);
	}
	
	//-----
	
	/**	Wf	01.09.2023
	 * 
	 * @param pZineID
	 * @throws Exception
	 */
	public void addZineID(int pZineID) throws Exception{
		if ((pZineID >= 0) && (!hasZineID(pZineID))) zineIDs.add(Integer.valueOf(pZineID));
		else throw new Exception("02; aZID,ZiL");
	}
	/**	Wf	01.09.2023
	 * 
	 * @param pZineIDs
	 * @throws Exception
	 */
	public void addZineIDs(ArrayList<Integer> pZineIDs) throws Exception{
		if (pZineIDs != null) {
			for (Integer vZineID : pZineIDs) {
				addZineID(vZineID);
			}
		}else throw new Exception("04; aZIDs,ZiL");
	}
	
	/**	Wf	01.09.2023
	 * 
	 * @param pZineID
	 */
	public void removeZineID(int pZineID) {
		zineIDs.remove(Integer.valueOf(pZineID));
	}
	/**	Wf	01.09.2023
	 * 
	 * @param pZineIDs
	 * @throws Exception
	 */
	public void removeZineIDs(ArrayList<Integer> pZineIDs) throws Exception {
		if (pZineIDs != null) {
			for (Integer vZineID : pZineIDs) {
				removeZineID(vZineID);
			}
		} else throw new Exception("04; rZiIDs,ZiL");
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	01.09.2023
	 * 
	 */
	public ZineList clone(int pID) throws Exception {
		ZineList vRet = null;
		
		if (pID >= -1) vRet = new ZineList(pID, name, getZineIDsCopy());
		else throw new Exception("02; clo,ZiL");
		
		return vRet;
	}
	
}
