/**	ZineManager v0.0		Wf	20.10.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			BasicSingleEditorStage
 * 			  SingleZineEditorStage
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

package org.zinemanager.gui.stages.zineinventory.singleeditor;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.controller.zineinventory.singleeditor.SingleZineEditorController;
import org.zinemanager.logic.manager.ZineManager;

public class SingleZineEditorStage<ParentController extends ParentControllerInterface>
			extends SingleZinePathEditorStage<SingleZineEditorController<ParentController>, ParentController>{
	
	/**	Wf	20.10.2023
	 * 
	 * @param pManager
	 * @param pParentController
	 * @param pEditorObjectID
	 */
	public SingleZineEditorStage(ZineManager pManager, ParentController pParentController, int pEditorObjectID) {
		super("org/zinemanager/gui/scenes/zineinventory/singleeditor/SingleZineEditorScene.fxml", "Zine Bearbeitung", pManager, pParentController, pEditorObjectID, true);
	}

}
