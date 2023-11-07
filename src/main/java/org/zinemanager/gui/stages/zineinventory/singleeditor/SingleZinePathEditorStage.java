/**	ZineManager v0.0		Wf	20.10.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			BasicSingleEditorStage
 * 			  SingleZinePathEditorStage
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
import org.zinemanager.gui.controller.zineinventory.singleeditor.SingleZinePathEditorController;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZineManager;

public class SingleZinePathEditorStage<Controller extends SingleZinePathEditorController<ParentController>, 
										ParentController extends ParentControllerInterface>
			extends BasicSingleEditorStage<Controller, ParentController> {
	
	/**	Wf	20.10.2023
	 * 
	 * @param pManager
	 * @param pParentController
	 * @param pEditorObjectID
	 * @param pShowDirectly
	 */
	public SingleZinePathEditorStage(ZineManager pManager, ParentController pParentController, int pEditorObjectID) {
		this("org/zinemanager/gui/scenes/zineinventory/singleeditor/SingleZinePathEditorScene.fxml", "Zine Dateiffad Korrektur", pManager, pParentController, pEditorObjectID, false);
	}
	
	/**	Wf	20.10.2023
	 * 
	 * @param pStageFile
	 * @param pTitle
	 * @param pManager
	 * @param pParentController
	 * @param pEditorObjectID
	 * @param pShowDirectly
	 */
	public SingleZinePathEditorStage(String pStageFile, String pTitle, ZineManager pManager, ParentController pParentController, int pEditorObjectID, boolean pShowDirectly) {
		super(pStageFile, pTitle, pManager, pParentController, pEditorObjectID, pShowDirectly);
		
		try { controller.setUp(this); }
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
}
