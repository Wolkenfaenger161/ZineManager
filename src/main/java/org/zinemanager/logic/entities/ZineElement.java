/**	ZineManager v0.2	Wf	23.01.2024
 * 
 * 	logic.entities
 * 	IDElement
 * 	  NameElement
 * 		ZineElement
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.print.attribute.standard.Sides;

import org.zinemanager.logic.manager.LogManager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ZineElement extends NameElement {
	@JsonProperty("extraCoverprint")
	protected boolean extracoverprint;
	@JsonProperty("quota")
	protected int quota;
	@JsonProperty("distributedOffset")
	protected int distributedOffset;
	@JsonProperty("categoryID")
	protected int categoryID;
	@JsonProperty("filePath")
	protected String filePath;
	
	@JsonIgnore
	protected Sides doublesidePrintart;
	
	@JsonProperty("counts")
	protected ArrayList<DatedCount> counts;
	
	/**	Wf	23.01.2024
	 * 
	 */
	public ZineElement() {
		super();
		
		extracoverprint = true;
		
		quota = 0;
		distributedOffset = 0;
		categoryID = -1;
		
		filePath = "";
		counts = new ArrayList<DatedCount>();
		
		doublesidePrintart = null;
	}
	
	/**	Wf	20.01.2024
	 * 
	 * @param pID
	 * @param pName
	 */
	public ZineElement(int pID, String pName, String pFilePath) {
		this(pID, pName, true, 0, 0, -1, pFilePath, new ArrayList<DatedCount>());
	}
	/**	Wf	20.01.2024
	 * 
	 * @param pID
	 * @param pName
	 * @param pCategoryID
	 * @param pQuota
	 * @param pDistributedOffset
	 * @param pFilePath
	 * @param pCounts
	 */
	public ZineElement(int pID, String pName, boolean pExtraCoverprint, int pQuota, int pDistributedOffset, int pCategoryID, String pFilePath, ArrayList<DatedCount> pCounts) {
		super(pID, pName);
		
		try {
			setExtracoverprint(pExtraCoverprint);
			
			setQuota(pQuota);
			setDistributedOffset(pDistributedOffset);
			setCategoryID(pCategoryID);
			
			setFilePath(pFilePath);
			setCounts(pCounts);
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	20.01.2024
	 * 
	 * @return
	 */
	public boolean isExtracoverprint() {
		return extracoverprint;
	}
	
	/**	Wf	01.09.2023
	 * 
	 * @return
	 */
	public int getQuota() {
		return quota;
	}
	/**	Wf	01.09.2023
	 * 
	 * @return
	 */
	public int getDistributedOffset() {
		return distributedOffset;
	}
	/**	Wf	01.09.2023
	 * 
	 * @return
	 */
	public int getCategoryID() {
		return categoryID;
	}
	
	/**	Wf	21.08.2023
	 * 
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**	Wf	21.08.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public DatedCount getCount(int pID) throws Exception {
		DatedCount vRet = null;
		
		if (pID >= 0) {
			for (int i=0; (i<counts.size()) && (vRet == null); i++) {
				if (counts.get(i).getId() == pID) vRet = counts.get(i);
			}
		}else throw new Exception("02; gCo,ZiE");
		
		return vRet;
	}
	/**	Wf	21.08.2023
	 * 
	 * @return
	 */
	public ArrayList<DatedCount> getCounts() {
		return counts;
	}
	//-----
	/**	Wf	02.09.2023
	 * 
	 * @return
	 */
	@JsonIgnore
	public ArrayList<DatedCount> getCountsCopy(){
		ArrayList<DatedCount> vRet = new ArrayList<DatedCount>();
		
		
		for (DatedCount vCount : counts) {
			try{ vRet.add( vCount.clone(vCount.getId()) ); }
			catch(Exception ex) {LogManager.handleException(ex);}
		}
			
		return vRet;
	}
	
	/**	Wf	23.01.2024
	 * 
	 * @return
	 */
	public Sides getDoublesidePrintart() {
		return doublesidePrintart;
	}
	/**	Wf	23.01.2024
	 * 
	 * @return
	 */
	@JsonProperty("doublesidePrintart")
	public int getDoublesidePrintartSerialitazion() {
		return ( (doublesidePrintart == null) ? 0 : ((doublesidePrintart == Sides.DUPLEX) ? 1 : 0 ) );
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	03.09.2023
	 * 
	 */
	public void setName(String pName) throws Exception{
		if ((pName != null) && (!pName.equals(""))) name = pName;
		else throw new Exception("02; sNa,ZiL");
	}
	
	/**	Wf	20.01.2024
	 * 
	 * @param pHasExtraCoverprint
	 */
	public void setExtracoverprint(boolean pHasExtraCoverprint) {
		extracoverprint = pHasExtraCoverprint;
	}
	
	/**	Wf	01.09.2023
	 * 
	 * @param pQuota
	 * @throws Exception
	 */
	public void setQuota(int pQuota) throws Exception {
		if (pQuota >= 0) quota = pQuota;
		else throw new Exception("02; sQu,ZiE");
	}
	/**	Wf	01.09.2023
	 * 
	 * @param pDistributedOffset
	 * @throws Exception
	 */
	public void setDistributedOffset(int pDistributedOffset) throws Exception {
		if (pDistributedOffset >= 0) distributedOffset = pDistributedOffset;
		else throw new Exception("02; sDO,ZiE");
	}
	/**	Wf	01.09.2023
	 * 
	 * @param pCategoryID
	 * @throws Exception
	 */
	public void setCategoryID(int pCategoryID) throws Exception {
		if (pCategoryID >= -1) categoryID = pCategoryID;
		else throw new Exception("02; sCID,ZiE"); 
	}
	
	/**	Wf	10.10.2023
	 * 
	 * @param pFilePath
	 * @throws Exception
	 */
	public void setFilePath(String pFilePath) throws Exception {
		if (pFilePath != null) filePath = pFilePath;
		else throw new Exception("04; sFP,ZiE");
	}
		
	/**	Wf	03.09.2023
	 * 
	 * @param pDatedCount
	 * @throws Exception
	 */
	public void setCount(DatedCount pCount) throws Exception{
		if (pCount != null) {
			setCount(pCount.getId(), pCount.getCount(), pCount.getDate());
		}else throw new Exception("04; sCo,ZiE");
	}
	/**	Wf	03.09.2023
	 * 
	 * @param pID
	 * @param pCount
	 * @param pDate
	 * @throws Exception
	 */
	public void setCount(int pID, int pCount, Date pDate) throws Exception{
		DatedCount vTemp = getCount(pID);
		
		if (vTemp != null) {
			vTemp.setCount(pCount);
			vTemp.setDate(pDate);
		}else throw new Exception("02a; sCo,ZiE:"+ pID);
	}
	/**	Wf	02.09.2023
	 * 
	 * @param pCounts
	 * @throws Exception
	 */
	public void setCounts(ArrayList<DatedCount> pCounts) throws Exception {
		ArrayList<Integer> vExistingIDs = new ArrayList<Integer>();
		
		if (pCounts != null) {
			for(DatedCount vCount : pCounts) {
				if (vExistingIDs.contains(Integer.valueOf(vCount.getId()))) throw new Exception("02; sCos,ZiE");
				else vExistingIDs.add(Integer.valueOf(vCount.getId()));
			}
			
			counts = pCounts;
		} else throw new Exception("04; sCos,ZiE");
	}
	
	/**	Wf	23.01.2024
	 * 
	 * @param pDoublesidePrintart
	 * @throws Exception
	 */
	public void setDoublesidePrintart(Sides pDoublesidePrintart) throws Exception{
		if ((pDoublesidePrintart == null) || ( pDoublesidePrintart == Sides.DUPLEX) || (pDoublesidePrintart == Sides.TUMBLE))
			doublesidePrintart = pDoublesidePrintart;
		else throw new Exception("02; sDP,ZiE");
	}
	/**	Wf	23.01.2024
	 * 
	 * @param pDoublesidePrintartSerilitazion
	 * @throws Exception
	 */
	public void setDoublesidePrintartSerialitazion(int pDoublesidePrintartSerilitazion) throws Exception{
		if (pDoublesidePrintartSerilitazion == 0) 	   doublesidePrintart = null;
		else if (pDoublesidePrintartSerilitazion == 1) doublesidePrintart = Sides.DUPLEX;
		else if (pDoublesidePrintartSerilitazion == 2) doublesidePrintart = Sides.TUMBLE;
		else throw new Exception("02; sDPS,ZiE");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	01.09.2023
	 * 
	 * @param pCount
	 * @return
	 * @throws Exception
	 */
	public boolean hasCount(DatedCount pCount){
		return hasCount(pCount.getId());
	}
	/**	Wf	01.09.2023
	 * 
	 * @param pCountID
	 * @return
	 * @throws Exception
	 */
	public boolean hasCount(int pCountID){
		boolean vRet = false;
		
		for (int i=0; ((i<counts.size()) && (vRet == false)); i++) {
			if (counts.get(i).getId() == pCountID) vRet = true;
		}
		
		return vRet;
	}
	
	//-----
	
	/**	Wf	22.08.2023
	 * 
	 * @param pNewCount
	 * @throws Exception
	 */
	public void addCount(DatedCount pNewCount) throws Exception{
		boolean vDoubledID = false;
		
		if (pNewCount != null) {
			for (int i=0; (i<counts.size()) && (vDoubledID == false); i++) {
				vDoubledID = counts.get(i).getId() == pNewCount.getId();
			}
			
			if (vDoubledID == false) counts.add(pNewCount);
			else throw new Exception("02; aCo,ZiE");
		}else throw new Exception("04; aCo,ZiE");
	}
	/**	Wf	22.08.2023
	 * 
	 * @param pNewCounts
	 * @throws Exception
	 */
	public void addCounts(ArrayList<DatedCount> pNewCounts) throws Exception{
		if (pNewCounts != null) {
			for (DatedCount vTemp : pNewCounts) {
				addCount(vTemp);
			}
		}else throw new Exception("04; aCos,ZiE");
	}
	
	/**	Wf	22.08.2023
	 * 	
	 * @param pID
	 * @throws Exception
	 */
	public void removeCount(int pID) throws Exception {
		if (pID >= 0) {
			for (int i=0; i<counts.size(); i++) {
				if (counts.get(i).getId() == pID) {
					counts.remove(i);
					i--;
				}
			}
		}else throw new Exception("02; rCo,ZiE");
	}
	/**	Wf	22.08.2023
	 * 
	 * @param pRemoveCount
	 * @throws Exception
	 */
	public void removeCount(DatedCount pRemoveCount) throws Exception {
		if (pRemoveCount != null) {
			counts.remove(pRemoveCount);
		} else throw new Exception("04; rCo,ZiE");
	}
	//-----
	/**	Wf	03.09.2023
	 * 
	 * @param pRemoveCounts
	 * @throws Exception
	 */
	public <T> void removeCounts(ArrayList<T> pRemoveCounts) throws Exception{
		if (pRemoveCounts != null) {
			for (T vTemp : pRemoveCounts) {
				if (vTemp instanceof Integer) removeCount((Integer)vTemp);
				else if (vTemp instanceof DatedCount) removeCount((DatedCount)vTemp);
				else throw new Exception("06; rCos,ZiE");
			}
		}else throw new Exception("04; rCos,ZiE");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	20.01.2024
	 * 
	 */
	public ZineElement clone(int pID) throws Exception{
		ZineElement vRet;
		
		if (pID >= -1) vRet = new ZineElement(pID, name, extracoverprint, quota, distributedOffset, categoryID, filePath, getCountsCopy());
		else throw new Exception("02; clo,ZiE");
		
		return vRet;
	}
	
	//-----
	
	/**	Wf	02.09.2023
	 * 
	 * @return
	 */
	@JsonIgnore
	public boolean isFilePathValid() {
		return isFilePathValid(filePath);
	}
	/**	Wf	10.10.2023
	 * 
	 * @param pFilePath
	 * @return
	 */
	public static boolean isFilePathValid(String pFilePath) {
		boolean vRet = false;
		File vTemp;
		
		if (pFilePath != null) {
			vTemp = new File(pFilePath);
			
			vRet = ((vTemp.exists() && vTemp.isFile() && vTemp.canRead() && pFilePath.endsWith(".pdf")) || (pFilePath.equals("")));
		}
		
		return vRet;
	}
	
//--------------------------------------------------------------------------------------------------------


}
