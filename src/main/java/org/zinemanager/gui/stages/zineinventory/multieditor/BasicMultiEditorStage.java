/**	ZineManager v0.0		Wf	28.09.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			BasicSingleEditorStage
 * 			  BasicMultiEditorStage
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
import org.zinemanager.gui.controller.zineinventory.multieditor.BasicMultiEditorController;
import org.zinemanager.gui.stages.zineinventory.singleeditor.BasicSingleEditorStage;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZineManager;

public abstract class BasicMultiEditorStage<Controller extends BasicMultiEditorController<ParentController>,
											ParentController extends ParentControllerInterface>
						extends BasicSingleEditorStage<Controller, ParentController> {
	
	/**	Wf	28.09.2023
	 * 
	 * @param pStageFile
	 * @param pTitle
	 * @param pManager
	 * @param pParentController
	 */
	public BasicMultiEditorStage(String pStageFile, String pTitle, ZineManager pManager, ParentController pParentController) {
		super(pStageFile, pTitle, pManager, pParentController, -1);
		
		try {controller.setUp();}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
}
