/**	ZineManager v0.0	Wf	01.09.2023
 * 
 * 	logic.entities
 * 	IDElement
 * 	  DatedCount
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

import java.util.Date;

import org.zinemanager.logic.manager.LogManager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "_type")

public class DatedCount extends IDElement {
	@JsonProperty("count")
	protected int count;
	@JsonProperty("date")
	protected Date date;
	
	/**	Wf	21.08.2023
	 * 
	 */
	public DatedCount() {
		super();
		
		count = 0;
		date = new Date();
	}
	/**	Wf	21.08.2023
	 * 
	 * @param pID
	 * @param pCount
	 * @param pDate
	 */
	public DatedCount(int pID, int pCount, Date pDate) {
		super(pID);
		
		try {
			setCount(pCount);
			setDate(pDate);
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	21.08.2023
	 * 
	 * @return
	 */
	public int getCount() {
		return count;
	}
	/**	Wf	21.08.2023
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	21.08.2023
	 * 
	 * @param pCount
	 * @throws Exception
	 */
	public void setCount(int pCount) throws Exception{
		if (pCount >= 0) count = pCount;
		else throw new Exception("02; sCo,DaC");
	}
	/**	Wf	21.08.2023
	 * 
	 * @param pDate
	 * @throws Exception
	 */
	public void setDate(Date pDate) throws Exception {
		if (pDate != null) date = pDate;
		else throw new Exception("04; sDa,DaC");
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	31.08.2023
	 * 
	 * @param pCompareObject
	 * @return
	 */
	public boolean isEqual(DatedCount pCompareObject) {
		boolean vRet = false;
		
		if (pCompareObject != null) vRet = (pCompareObject.getCount() == count) && (pCompareObject.getDate().equals(date));
		
		return vRet;
	}
	
	/**	Wf	01.09.2023
	 * 
	 * @param pCompareObject
	 * @return
	 * @throws Exception
	 */
	public boolean isGreaterThan(DatedCount pCompareObject) throws Exception {
		if (pCompareObject != null) return (pCompareObject.getCount() < count);
		else throw new Exception("05; iGt,DaC");
	}
	/**	Wf	01.09.2023
	 * 
	 * @param pCompareObject
	 * @return
	 * @throws Exception
	 */
	public boolean isLesserThan(DatedCount pCompareObject) throws Exception {
		if (pCompareObject != null) return (pCompareObject.getCount() > count);
		else throw new Exception("05; iGt,DaC");
	}
	//-----
	/**	Wf	31.08.2023
	 * 
	 * @param pCompareObject
	 * @return
	 * @throws Exception
	 */
	public boolean isEqualInValueTo(DatedCount pCompareObject) throws Exception{
		if (pCompareObject != null) return (pCompareObject.getCount() == count);
		else throw new Exception("05; iGt,DaC");
	}
	
	/**	Wf	31.08.2023
	 * 
	 * @param pCompareObject
	 * @return
	 * @throws Exception
	 */
	public boolean isEarlierThan(DatedCount pCompareObject) throws Exception {
		if (pCompareObject != null) return pCompareObject.getDate().after(date);
		else throw new Exception("05; iGt,DaC");
	}
	/**	Wf	31.08.2023
	 * 	
	 * @param pCompareObject
	 * @return
	 * @throws Exception
	 */
	public boolean isLaterThan(DatedCount pCompareObject) throws Exception {
		if (pCompareObject != null) return pCompareObject.getDate().before(date);
		else throw new Exception("05; iGt,DaC");
	}
	//-----
	/**	Wf	31.08.2023
	 * 	
	 * @param pCompareObject
	 * @return
	 * @throws Exception
	 */
	public boolean isSimultaneTo(DatedCount pCompareObject) throws Exception{
		if (pCompareObject != null) return (pCompareObject.getDate().compareTo(date) == 0);
		else throw new Exception("05; iGt,DaC");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	31.08.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public DatedCount clone(int pID) throws Exception {
		DatedCount vRet;
		
		if (pID >= -1) {
			vRet = new DatedCount(pID, count, (Date)date.clone());
		}else throw new Exception("02; clo,DaC");
		
		return vRet;
	}
}
