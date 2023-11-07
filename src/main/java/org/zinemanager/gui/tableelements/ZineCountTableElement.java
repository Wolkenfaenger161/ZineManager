/**	ZineManager v0.0		Wf	01.10.2023
 * 	
 * 	gui.tableelements
 * 	  BasicTableElement
 * 		ZineCountTableElement
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

import java.text.DateFormat;
import java.util.Date;

import org.zinemanager.logic.manager.LogManager;

public class ZineCountTableElement extends BasicTableElement {
	private int count;
	private Date date;
	
	/**	Wf	01.10.2023
	 * 
	 * @param pID
	 * @param pCount
	 * @param pDate
	 */
	public ZineCountTableElement(int pID, int pCount, Date pDate) {
		super(pID);
		
		try {
			setCount(pCount);
			setDate(pDate);
		}catch(Exception ex) { LogManager.handleException(ex); }
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	01.10.2023
	 * 
	 * @return
	 */
	public int getCount() {
		return count;
	}
	/**	Wf	01.10.2023
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}
	
	
	/**	Wf	01.10.2023
	 * 
	 * @param pCount
	 * @throws Exception
	 */
	public void setCount(int pCount) throws Exception{
		if (pCount >= 0) count = pCount;
		else throw new Exception("02; sCo,ZCTE");
	}
	/**	Wf	01.10.2023
	 * 
	 * @param pDate
	 * @throws Exception
	 */
	public void setDate(Date pDate) throws Exception{
		if (pDate != null) date = pDate;
		else throw new Exception("04; sDa,ZCTE");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	01.10.2023
	 * 
	 */
	public String toString() {
		return "" + super.id + ":" + count + "," + DateFormat.getInstance().format(date);
	}
	
}
