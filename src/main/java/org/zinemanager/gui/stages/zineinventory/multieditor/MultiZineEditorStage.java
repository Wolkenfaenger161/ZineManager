/**	ZineManager v0.0		Wf	03.10.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			BasicSingleEditorStage
 * 			  BasicMultiEditorStage
 * 				MultiCategoryEditorStage
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

package org.zinemanager.gui.stages.zineinventory.multieditor;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.controller.zineinventory.multieditor.MultiZineEditorController;
import org.zinemanager.logic.manager.ZineManager;

public class MultiZineEditorStage<ParentController extends ParentControllerInterface>
			  extends BasicMultiEditorStage<MultiZineEditorController<ParentController>, ParentController> {

	/**	Wf	03.10.2023
	 * 
	 * @param pManager
	 * @param pParentController
	 */
	public MultiZineEditorStage(ZineManager pManager, ParentController pParentController) {
		super("org/zinemanager/gui/scenes/zineinventory/multieditor/MultiZineEditorScene.fxml", "Zine Bearbeitung", pManager, pParentController);
	}
	
}
