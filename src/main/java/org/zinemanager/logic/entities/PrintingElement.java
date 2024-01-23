/**	ZineManager v0.2	Wf	23.01.2024
 * 
 * 	logic.entities
 * 	IDElement
 * 	  NameElement
 * 		PrintingElement
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

import javax.print.attribute.standard.Sides;

public class PrintingElement extends NameElement {
	protected boolean extracoverprint, printingcover, finishedprinting;
	protected int quota, current, printing;
	protected String filePath;
	
	protected Sides doublesidePrintart;
	
	/**	Wf	23.01.2024
	 * 
	 */
	public PrintingElement() {
		super();
		
		extracoverprint  = true;
		printingcover	 = true;
		finishedprinting = false;
		
		quota    = -1;
		current  = -1;
		printing = -1;
		
		filePath = "";
		
		doublesidePrintart = null;
	}
	
	/**	Wf	23.01.2024
	 * 
	 * @param pID
	 * @param pName
	 * @param pFilePath
	 * @param pDoublesidePrintart
	 * @param pPrinting
	 */
	public PrintingElement(int pID, String pName, String pFilePath, Sides pDoublesidePrintart, int pPrinting) {
		this(pID, pName, pFilePath, pDoublesidePrintart, -1, -1, pPrinting);
	}
	/**	Wf	23.01.2024
	 * 
	 * @param pID
	 * @param pName
	 * @param pFilePath
	 * @param pDoublesidePrintart
	 * @param pQuota
	 * @param pCurrent
	 * @param pPrinting
	 */
	public PrintingElement(int pID, String pName, String pFilePath, Sides pDoublesidePrintart, int pQuota, int pCurrent, int pPrinting) {
		this(pID, pName, pFilePath, pDoublesidePrintart, pQuota, pCurrent, pPrinting, true, true);
	}
	/**	Wf	23.01.2024
	 * 
	 * @param pID
	 * @param pName
	 * @param pFilePath
	 * @param pDoublesidePrintart
	 * @param pQuota
	 * @param pCurrent
	 * @param pPrinting
	 * @param pHasExtracoverPrint
	 * @param pIsPrintingCover
	 */
	public PrintingElement(int pID, String pName, String pFilePath, Sides pDoublesidePrintart, int pQuota, int pCurrent, int pPrinting, boolean pHasExtracoverPrint, boolean pIsPrintingCover) {
		super(pID, pName);
		
		extracoverprint  = pHasExtracoverPrint;
		printingcover	 = pIsPrintingCover;
		finishedprinting = false;
		
		quota    = pQuota;
		current  = pCurrent;
		printing = pPrinting;
		
		filePath = pFilePath;
		
		doublesidePrintart = pDoublesidePrintart;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	17.01.2024
	 * 
	 * @return
	 */
	public boolean hasExtracoverPrint() {
		return extracoverprint;
	}
	/**	Wf	17.01.2024
	 * 
	 * @return
	 */
	public boolean isPrintingCover() {
		return printingcover;
	}
	/**	Wf	16.01.2024
	 * 
	 * @return
	 */
	public boolean hasFinishedPrinting() {
		return finishedprinting;
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
	
	/**	Wf	03.12.2023
	 * 
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**	Wf	23.01.2024
	 * 
	 * @return
	 */
	public Sides getDoublesidePrintart() {
		return doublesidePrintart;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	17.01.2024
	 * 
	 * @param pHasExtracoverPrint
	 */
	public void setExtracoverPrint(boolean pHasExtracoverPrint) {
		extracoverprint = pHasExtracoverPrint;
	}
	/**	Wf	17.01.2024
	 * 
	 * @param pIsPrintingCover
	 */
	public void setPrintingCover(boolean pIsPrintingCover) {
		printingcover = pIsPrintingCover;
	}
	/**	Wf	16.01.2024	
	 * 
	 * @param pHasFinishedPrinting
	 */
	public void setFinishedPrinting(boolean pHasFinishedPrinting) {
		finishedprinting = pHasFinishedPrinting;
	}
	
	/**	Wf	03.12.2023
	 * 
	 * @param pQuota
	 * @throws Exception
	 */
	public void setQuota(int pQuota) throws Exception{
		if (pQuota >= 0) quota = pQuota;
		else throw new Exception("02; sQu,PrE");
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pCurrent
	 * @throws Exception
	 */
	public void setCurrent(int pCurrent) throws Exception{
		if (pCurrent >= 0) current = pCurrent;
		else throw new Exception("02; sCu,PrE");
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pPrinting
	 * @throws Exception
	 */
	public void setPrinting(int pPrinting) throws Exception{
		if (pPrinting >= 0) printing = pPrinting;
		else throw new Exception("02; sPr,PrE");
	}
	
	/**	Wf	03.12.2023
	 * 
	 * @param pFilePath
	 * @throws Exception
	 */
	public void setFilePath(String pFilePath) throws Exception{
		if (pFilePath != null) filePath = pFilePath;
		else throw new Exception("02; sFP,PrE");
	}
		
//--------------------------------------------------------------------------------------------------------

	/**	Wf	23.01.2024
	 * 
	 */
	public PrintingElement clone(int pID) throws Exception{
		PrintingElement vRet;
		
		if (pID >= -1) vRet = new PrintingElement(pID, name, filePath, doublesidePrintart, quota, current, printing, extracoverprint, printingcover);
		else throw new Exception("02; clo,PrE");
		
		return vRet;
	}
	
	//-----
	
	/**	Wf	03.12.2023
	 * 
	 * @return
	 */
	public boolean isFilePathValid() {
		return ZineElement.isFilePathValid(filePath);
	}
	
}
