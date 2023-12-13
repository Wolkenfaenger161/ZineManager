/**	ZineManager v0.1	Wf	03.12.2023
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

public class PrintingElement extends NameElement {
	protected int quota, current, printing;
	protected String filePath;
	
	/**	Wf	03.12.2023
	 * 
	 */
	public PrintingElement() {
		super();
		
		quota    = -1;
		current  = -1;
		printing = -1;
		
		filePath = "";
	}
	
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @param pName
	 * @param pFilePath
	 * @param pPrinting
	 */
	public PrintingElement(int pID, String pName, String pFilePath, int pPrinting) {
		this(pID, pName, pFilePath, -1, -1, pPrinting);
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @param pName
	 * @param pFilePath
	 * @param pQuota
	 * @param pCurrent
	 * @param pPrinting
	 */
	public PrintingElement(int pID, String pName, String pFilePath, int pQuota, int pCurrent, int pPrinting) {
		super(pID, pName);
		
		quota    = pQuota;
		current  = pCurrent;
		printing = pPrinting;
		
		filePath = pFilePath;
	}
	
//--------------------------------------------------------------------------------------------------------
	
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
	
	//----------------------------------------------------------------------------------------------------
	
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

	/**	Wf	03.12.2023
	 * 
	 */
	public PrintingElement clone(int pID) throws Exception{
		PrintingElement vRet;
		
		if (pID >= -1) vRet = new PrintingElement(pID, name, filePath, quota, current, printing);
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
