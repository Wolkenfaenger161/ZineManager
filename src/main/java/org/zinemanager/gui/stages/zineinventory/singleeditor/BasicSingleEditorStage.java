/**	ZineManager v0.0		Wf	20.10.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			BasicSingleEditorStage
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
import org.zinemanager.gui.controller.zineinventory.singleeditor.BasicSingleEditorController;
import org.zinemanager.gui.stages.BasicManagerStage;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZineManager;

public abstract class BasicSingleEditorStage<Controller extends BasicSingleEditorController<ParentController>,
											ParentController extends ParentControllerInterface>
						extends BasicManagerStage<ZineManager, Controller, ParentController> {

	/**	Wf	20.10.2023
	 * 
	 * @param pStageFile
	 * @param pTitle
	 * @param pManager
	 * @param pParentController
	 * @param pEditorObjectID
	 */
	public BasicSingleEditorStage(String pStageFile, String pTitle, ZineManager pManager, ParentController pParentController, int pEditorObjectID) {
		this(pStageFile, pTitle, pManager, pParentController, pEditorObjectID, true);
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
	public BasicSingleEditorStage(String pStageFile, String pTitle, ZineManager pManager, ParentController pParentController, int pEditorObjectID, boolean pShowDirectly) {
		super(pStageFile, pTitle, pManager, pParentController, pShowDirectly);
		
		try {controller.setEditObjectID(pEditorObjectID);}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
}
