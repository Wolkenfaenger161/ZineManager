/**	ZineManager v0.0		Wf	01.11.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
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

import org.zinemanager.gui.controller.ChildController;
import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.logic.manager.LogManager;

public abstract class ChildStage <Controller extends ChildController<ParentController>, ParentController extends ParentControllerInterface>
					extends BasicStage<Controller>{

	/**	Wf	20.10.2023
	 * 
	 * @param pStageFile
	 * @param pTitle
	 */
	public ChildStage(String pStageFile, String pTitle, ParentController pParentController) {
		this(pStageFile, pTitle, pParentController, true);
	}
	
	/**	Wf	20.10.2023
	 * 
	 * @param pStageFile
	 * @param pTitle
	 * @param pParentController
	 * @param pShowDirectly
	 */
	public ChildStage(String pStageFile, String pTitle, ParentController pParentController, boolean pShowDirectly) {
		super(pStageFile, pTitle, pShowDirectly);
		
		try {controller.setParentController(pParentController);}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	01.11.2023
	 * 
	 */
	public void closeStage() {
		if (controller instanceof ParentControllerInterface) ((ParentControllerInterface)controller).closeChildStage();
		this.hide();
	}
	
}
