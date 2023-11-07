/**	ZineManager v0.0		Wf	02.10.2023
 * 	
 * 	gui.tableelements
 * 	  BasicTableElement
 * 		NameElement
 * 		  ZineListTableElement
 * 		
 * 
 * Exceptions:
 * 	  01 Wrong length
 * 	  02 Wrong Value
 * 	  03 Calculation Error
 * 	  04 Nullpointer Error
 * 	  05 Empty List Error
 * 	  06 Wrong Type Error
 * 	  07 Index Error
 * 	  08 Equal Object Error
 */

package org.zinemanager.gui.tableelements;

import org.zinemanager.logic.manager.LogManager;

public class ZineListTableElement extends NameTableElement {
	private int quota, totalDistributed;
	private String categoryName;
	
	private Integer lastCount, currentCount;
	
	/**	Wf	02.10.2023
	 * 
	 * @param pID
	 * @param pName
	 * @param pCategoryName
	 * @param pQuota
	 * @param pLastCount
	 * @param pCurrentCount
	 * @param pTotalDistributed
	 */
	public ZineListTableElement(int pID, String pName, String pCategoryName, int pQuota, Integer pLastCount, Integer pCurrentCount, int pTotalDistributed) {
		super(pID, pName);
		
		try {
			setQuota(pQuota);
			setTotalDistributed(pTotalDistributed);
			
			setCategoryName(pCategoryName);
			
			setLastCount(pLastCount);
			setCurrentCount(pCurrentCount);
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	02.10.2023
	 * 
	 * @return
	 */
	public int getQuota() {
		return quota;
	}
	/**	Wf	02.10.2023
	 * 
	 * @return
	 */
	public int getTotalDistributed() {
		return totalDistributed;
	}
	
	/**	Wf	02.10.2023
	 * 
	 * @return
	 */
	public String getCategoryName() {
		return categoryName;
	}
	
	/**	Wf	02.10.2023
	 * 
	 * @return
	 */
	public Integer getLastCount() {
		return lastCount;
	}
	/**	Wf	02.10.2023
	 * 
	 * @return
	 */
	public Integer getCurrentCount() {
		return currentCount;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	02.10.2023
	 * 
	 * @param pQuota
	 * @throws Exception
	 */
	public void setQuota(int pQuota) throws Exception{
		if (pQuota >= 0) quota = pQuota;
		else throw new Exception("02; sQu,ZLTE");
	}
	/**	Wf	02.10.2023
	 * 
	 * @param pTotalDistributed
	 * @throws Exception
	 */
	public void setTotalDistributed(int pTotalDistributed) throws Exception {
		if (pTotalDistributed >= 0) totalDistributed = pTotalDistributed;
		else throw new Exception("02; sTD,ZLTE");
	}
	
	/**	Wf	02.10.2023
	 * 
	 * @param pCategoryName
	 * @throws Exception
	 */
	public void setCategoryName(String pCategoryName) throws Exception{
		if (pCategoryName != null) categoryName = pCategoryName;
		else throw new Exception("04; sCN,ZLTE");
	}
	
	/**	Wf	02.10.2023
	 * 
	 * @param pLastCount
	 * @throws Exception
	 */
	public void setLastCount(Integer pLastCount) throws Exception {
		if ((pLastCount == null) || (pLastCount.intValue() >= 0)) lastCount = pLastCount;
		else throw new Exception("02; sLC,ZLTE");
	}
	/**	Wf	02.10.2023
	 * 
	 * @param pCurrentCount
	 * @throws Exception
	 */
	public void setCurrentCount(Integer pCurrentCount) throws Exception {
		if ((pCurrentCount == null) || (pCurrentCount.intValue() >= 0)) currentCount = pCurrentCount;
		else throw new Exception("02; sCC,ZLTE");
		
	}
	
}
