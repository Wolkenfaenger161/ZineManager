/**	ZineManager v0.1		Wf	03.12.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			ZineNumberSelectionStage
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

package org.zinemanager.gui.stages.zineprinting;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.controller.zineprinting.ZineNumberSelectionController;
import org.zinemanager.gui.stages.BasicManagerStage;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZinePrintingManager;

public class ZineNumberSelectionStage<ParentController extends ParentControllerInterface>
									extends BasicManagerStage<ZinePrintingManager, ZineNumberSelectionController<ParentController>, ParentController> {

	/**	Wf	03.12.2023
	 * 
	 * @param pManager
	 * @param pParentController
	 */
	public ZineNumberSelectionStage(ZinePrintingManager pManager, ParentController pParentController) {
		super("org/zinemanager/gui/scenes/zineprinting/ZineNumberSelectionScene.fxml", "Zine Druck Anzahl Auswahl", pManager, pParentController);
	
		try { controller.setUp(); }
		catch(Exception ex) {LogManager.handleException(ex);}
	}
}
