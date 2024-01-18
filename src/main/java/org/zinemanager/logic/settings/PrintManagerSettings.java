/**	ZineManager v0.1	Wf	18.01.2024
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

import javax.print.attribute.standard.Sides;

public class PrintManagerSettings extends BasicSetting {
	private boolean defaultExtracoverPrint;
	private Sides doublesidePrintart;
	
	/**	Wf	18.01.2024
	 * 
	 */
	public PrintManagerSettings() {
		defaultExtracoverPrint = true;
		
		doublesidePrintart = Sides.DUPLEX;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	18.01.2024
	 * 
	 * @return
	 */
	public boolean isDefaultExtracoverPrint() {
		return defaultExtracoverPrint;
	}
	
	/**	Wf	18.01.2024
	 * 
	 * @return
	 */
	public Sides getDoublesidePrintart() {
		return doublesidePrintart;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	18.01.2024
	 * 
	 * @param pHasDefaultExtracoverPrint
	 */
	public void setDefaultExtracoverPrint(boolean pHasDefaultExtracoverPrint) {
		defaultExtracoverPrint = pHasDefaultExtracoverPrint;
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
		
}
