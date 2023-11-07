/**	ZineManager v0.0		Wf	03.10.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			BasicSingleEditorStage
 * 			  SingleCategoryEditorStage
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
import org.zinemanager.gui.controller.zineinventory.singleeditor.SingleCategoryEditorController;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZineManager;

public class SingleCategoryEditorStage<ParentController extends ParentControllerInterface>
				extends BasicSingleEditorStage<SingleCategoryEditorController<ParentController>, ParentController> {
	
	/**	Wf	03.10.2023
	 * 
	 * @param pManager
	 * @param pParentController
	 * @param pEditorObjectID
	 */
	public SingleCategoryEditorStage( ZineManager pManager, ParentController pParentController, int pEditorObjectID) {
		super("org/zinemanager/gui/scenes/zineinventory/singleeditor/SingleCategoryEditorScene.fxml", "Kategorie Bearbeitung", pManager, pParentController, pEditorObjectID);
	
		try {controller.setUp();}
		catch(Exception ex) {LogManager.handleException(ex);}
	}

}
