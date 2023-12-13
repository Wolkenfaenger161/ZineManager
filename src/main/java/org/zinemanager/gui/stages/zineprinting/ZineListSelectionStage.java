/**	ZineManager v0.1		Wf	20.11.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			BasicSingleEditorStage
 * 			  BasicMultiEditorStage
 * 				ZineListSelectionStage
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
import org.zinemanager.gui.controller.zineprinting.ZineListSelectionController;
import org.zinemanager.gui.stages.zineinventory.multieditor.BasicMultiEditorStage;
import org.zinemanager.logic.manager.ZineManager;

public class ZineListSelectionStage<ParentController extends ParentControllerInterface>
				extends BasicMultiEditorStage<ZineListSelectionController<ParentController>, ParentController> {

	/**	Wf	20.11.2023
	 * 
	 * @param pManager
	 * @param pParentController
	 */
	public ZineListSelectionStage(ZineManager pManager, ParentController pParentController) {
		super("org/zinemanager/gui/scenes/zineprinting/ZineListSelectionScene.fxml", "Zinelist Auswahl", pManager, pParentController);
	}
	
}
