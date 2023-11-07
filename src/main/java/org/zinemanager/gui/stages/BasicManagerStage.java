/**	ZineManager v0.0		Wf	20.10.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
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

import org.zinemanager.gui.controller.BasicManagerController;
import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.logic.manager.BasicManager;
import org.zinemanager.logic.manager.LogManager;

public abstract class BasicManagerStage<Manager 		 extends BasicManager,
										Controller 		 extends BasicManagerController<Manager, ParentController>,
										ParentController extends ParentControllerInterface>
						extends ChildStage<Controller, ParentController> {

	/**	Wf	20.10.2023
	 * 
	 * @param pStageFile
	 * @param pTitle
	 * @param pManager
	 * @param pParentController
	 */
	public BasicManagerStage(String pStageFile, String pTitle, Manager pManager, ParentController pParentController) {
		this(pStageFile, pTitle, pManager, pParentController, true);
	}
	
	/**	Wf	20.10.2023
	 * 
	 * @param pStageFile
	 * @param pTitle
	 * @param pManager
	 * @param pParentController
	 * @param pShowDirectly
	 */
	public BasicManagerStage(String pStageFile, String pTitle, Manager pManager, ParentController pParentController, boolean pShowDirectly) {
		super(pStageFile, pTitle, pParentController, pShowDirectly);
		
		try {controller.setBasicManager(pManager);}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
}
