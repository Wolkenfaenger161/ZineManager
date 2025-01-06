/**	ZineManager v0.21	Wf	16.09.2024
 * 
 * 	BasicSetting
 * 	  PrintManagerSettings
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

package org.zinemanager.logic.settings;

import javax.print.PrintService;
import javax.print.attribute.standard.Sides;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PrintManagerSettings extends BasicSetting {
	@JsonProperty("defaultExtracoverPrint")
	private boolean defaultExtracoverPrint;
	@JsonProperty("canPrintDoubleSide")
	private Boolean canPrintDoubleSide;
	@JsonIgnore
	private Sides doublesidePrintart;
	//@JsonProperty("defaultPrinter")
	@JsonIgnore
	private PrintService defaultPrinter;
	
	/**	Wf	16.09.2024
	 * 
	 */
	public PrintManagerSettings() {
		defaultExtracoverPrint = true;
		canPrintDoubleSide 	   = null;
		
		doublesidePrintart = Sides.DUPLEX;
		
		defaultPrinter = null;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	18.01.2024
	 * 
	 * @return
	 */
	public boolean isDefaultExtracoverPrint() {
		return defaultExtracoverPrint;
	}
	/**	Wf	16.09.2024
	 * 
	 * @return
	 */
	public Boolean canPrintDoubleSide() {
		return canPrintDoubleSide;
	}
	
	/**	Wf	18.01.2024
	 * 
	 * @return
	 */
	public Sides getDoublesidePrintart() {
		return doublesidePrintart;
	}
	/**	Wf	19.01.2024
	 * 
	 * @return
	 */
	@JsonProperty("doublesidePrintart")
	public int getDoublesidePrintartSerialitazion() {
		return (doublesidePrintart == Sides.TUMBLE ? 1 : 0 ); 
	}
	
	/**	Wf	16.09.2024
	 * 
	 * @return
	 */
	public PrintService getDefaultPrinter() {
		return defaultPrinter;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	18.01.2024
	 * 
	 * @param pHasDefaultExtracoverPrint
	 */
	public void setDefaultExtracoverPrint(boolean pHasDefaultExtracoverPrint) {
		defaultExtracoverPrint = pHasDefaultExtracoverPrint;
	}
	/**	Wf	16.09.2024
	 * 
	 * @param pCanPrintDoubleSide
	 */
	public void setCanPrintDoubleSide(Boolean pCanPrintDoubleSide) {
		canPrintDoubleSide = ( pCanPrintDoubleSide != null ? pCanPrintDoubleSide : Boolean.valueOf(true) );
	}
	
	/**	Wf	18.01.2024
	 * 
	 * @param pDoublesidePrintart
	 * @throws Exception
	 */
	public void setDoublesidePrintart(Sides pDoublesidePrintart) throws Exception{
		if (pDoublesidePrintart != null) {
			if ((pDoublesidePrintart == Sides.DUPLEX) || (pDoublesidePrintart == Sides.TUMBLE)) doublesidePrintart = pDoublesidePrintart;
			else throw new Exception("02; sDsPa,PMS");
		} else throw new Exception("04; sDsPa,PMS");
	}
	/**	Wf	19.01.2024
	 * 
	 * @param pDerializationNumber
	 */
	public void setDoublesidePrintartSerialitazion(int pDerializationNumber) {
		if (pDerializationNumber == 1) doublesidePrintart = Sides.TUMBLE;
		else 						   doublesidePrintart = Sides.DUPLEX;
	}
	
	/**	Wf	16.09.2024
	 * 
	 * @param pDefaultPrinter
	 */
	public void setDefaultPrinter(PrintService pDefaultPrinter) {
		defaultPrinter = pDefaultPrinter;
	}
		
}
