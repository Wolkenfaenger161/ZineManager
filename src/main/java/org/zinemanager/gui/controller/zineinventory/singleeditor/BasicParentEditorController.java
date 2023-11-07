/**	ZineManager v0.0		Wf	01.11.2023
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  BasicParentEditorController
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

package org.zinemanager.gui.controller.zineinventory.singleeditor;

import org.zinemanager.gui.controller.ChildController;
import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.stages.ChildStage;

public abstract class BasicParentEditorController<ParentController extends ParentControllerInterface>
						extends BasicSingleEditorController<ParentController>
						implements ParentControllerInterface{
	
	protected ChildStage<? extends ChildController<? extends BasicParentEditorController<ParentController>>,
						 ? extends BasicParentEditorController<ParentController>> childStage;
	
	/**	Wf	29.09.2023
	 * 
	 */
	public BasicParentEditorController() {
		super();
		
		childStage = null;
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	29.09.2023
	 * 
	 */
	protected abstract void updateAll();
	
	/**	Wf	01.11.2023
	 * 
	 */
	public void closeChildStage() {
		if (childStage != null) childStage.hide();
		
		setEnabled();
		updateAll();
	}
	
}
