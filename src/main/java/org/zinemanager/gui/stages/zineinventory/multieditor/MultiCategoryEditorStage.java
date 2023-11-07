/**	ZineManager v0.0		Wf	29.09.2023
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
import org.zinemanager.gui.controller.zineinventory.multieditor.MultiCategoryEditorController;
import org.zinemanager.logic.manager.ZineManager;

public class MultiCategoryEditorStage<ParentController extends ParentControllerInterface>
			extends BasicMultiEditorStage<MultiCategoryEditorController<ParentController>, ParentController> {

	/**	Wf	29.09.2023
	 * 
	 * @param pManager
	 * @param pParentController
	 */
	public MultiCategoryEditorStage(ZineManager pManager, ParentController pParentController) {
		super("org/zinemanager/gui/scenes/zineinventory/multieditor/MultiCategoryEditorScene.fxml", "Kategorie Bearbeitung", pManager, pParentController);
	}
}
