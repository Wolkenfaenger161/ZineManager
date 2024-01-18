/**	ZineManager v0.1		Wf	17.01.2024
 * 	
 * 	gui.tableelements
 * 	  BasicTableElement
 * 		NameElement
 * 		  ZinePrintNumberTableElement
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

public class ZinePrintNumberTableElement extends NameTableElement {
	protected boolean extracoverprint, printingcover;
	protected int quota, current, printing;
	
	/**	Wf	17.01.2024
	 * 
	 * @param pID
	 * @param pName
	 * @param pQuota
	 * @param pCurrent
	 * @param pPrinting
	 * @param pExtracoverPrint
	 * @param pPrintingCover
	 */
	public ZinePrintNumberTableElement(int pID, String pName, int pQuota, int pCurrent, int pPrinting, boolean pExtracoverPrint, boolean pPrintingCover) {
		super(pID, pName);
		
		try {
			setQuota(pQuota);
			setCurrent(pCurrent);
			setPrinting(pPrinting);
			
			setExtracoverprint(pExtracoverPrint);
			setPrintingcover(pPrintingCover);
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	17.01.2024
	 * 
	 * @return
	 */
	public boolean isExtracoverprint() {
		return extracoverprint;
	}
	/**	Wf	17.01.2024
	 * 
	 * @return
	 */
	public boolean isPrintingcover() {
		return printingcover;
	}
	
	/**	Wf	03.12.2023
	 * 
	 * @return
	 */
	public int getQuota() {
		return quota;
	}
	/**	Wf	03.12.2023
	 * 
	 * @return
	 */
	public int getCurrent() {
		return current;
	}
	/**	Wf	03.12.2023
	 * 
	 * @return
	 */
	public int getPrinting() {
		return printing;
	}
	
	//-----
	
	/**	Wf	17.01.2024
	 * 
	 * @param pHasExtracoverPrint
	 */
	public void setExtracoverprint(boolean pHasExtracoverPrint) {
		extracoverprint = pHasExtracoverPrint;
	}
	/**	Wf	17.01.2024
	 * 
	 * @param pPrintingCover
	 */
	public void setPrintingcover(boolean pPrintingCover) {
		printingcover = pPrintingCover;
	}
	
	/**	Wf	03.12.2023
	 * 
	 * @param pQuota
	 * @throws Exception
	 */
	public void setQuota(int pQuota) throws Exception{
		if (pQuota >= 0) quota = pQuota;
		else throw new Exception("02; sQu,ZPNTE");
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pCurrent
	 * @throws Exception
	 */
	public void setCurrent(int pCurrent) throws Exception{
		if (pCurrent >= 0) current = pCurrent;
		else throw new Exception("02; sCu,ZPNTE");
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pPrinting
	 * @throws Exception
	 */
	public void setPrinting(int pPrinting) throws Exception{
		if (pPrinting >= 0) printing = pPrinting;
		else throw new Exception("02; sPr,ZPNTE");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	17.01.2024
	 * 
	 */
	public String toString() {
		return super.toString() +": "+ quota +","+ current +","+ printing +","+ extracoverprint +","+ printingcover;
	}
}
