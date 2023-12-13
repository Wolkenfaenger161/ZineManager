/**	ZineManager v0.1		Wf	08.12.2023
 * 	
 * 	gui
 * 	  Printer
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

package org.zinemanager.gui;

import org.zinemanager.gui.stages.PrinterStage;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZinePrintingManager;

public class Printer {
	private PrinterStage printerStage;
	private ZinePrintingManager zineprintmanager;
	
	/**	Wf	08.12.2023
	 * 
	 */
	public Printer(ZinePrintingManager pZinePrintManager) {
		printerStage = null;
		zineprintmanager = pZinePrintManager;
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	08.12.2023
	 * 
	 */
	public void startPrinting() {
		
		try {
			if (zineprintmanager.settingUpPrinting(new PrinterSelector())) {
				printerStage = new PrinterStage(zineprintmanager);
				printerStage.showAndWait();
			}
		}catch(Exception ex) {LogManager.handleException(ex);}
		
	}

}
