/**	ZineManager v0.1		Wf	08.12.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  PrinterStage
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

package org.zinemanager.gui.stages;

import org.zinemanager.gui.controller.PrinterController;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZinePrintingManager;

public class PrinterStage extends BasicStage<PrinterController> {

	/**	Wf	08.12.2023
	 * 
	 */
	public PrinterStage(ZinePrintingManager pZinePrintManager) {
		super("org/zinemanager/gui/scenes/PrinterScene.fxml", "Druckprozess", false);
		
		try { controller.setUp(title, pZinePrintManager, this); }
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
	//--------------------------------------------------------------------------------------------------------

		/**	Wf	08.12.2023
		 * 
		 * @return
		 */
		public PrinterController getController() {
			return controller;
		}
}
