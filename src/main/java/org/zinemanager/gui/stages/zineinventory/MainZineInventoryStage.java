/**	ZineManager v0.0		Wf	12.10.2023
 * 	
 * 	gui.stages.zineinventory
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			MainZineInventoryStage
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

package org.zinemanager.gui.stages.zineinventory;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.controller.zineinventory.MainZineInventoryController;
import org.zinemanager.gui.stages.BasicManagerStage;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZineManager;

public class MainZineInventoryStage <ParentController extends ParentControllerInterface> 
			 extends BasicManagerStage<ZineManager, MainZineInventoryController<ParentController>, ParentController>{

	/**	Wf	12.10.2023
	 * 
	 * @param pZineManager
	 * @param pParentController
	 */
	public MainZineInventoryStage(ZineManager pZineManager, ParentController pParentController) {
		super("org/zinemanager/gui/scenes/zineinventory/MainZineInventoryScene.fxml", "Zine Manager v"+LogManager.getVersion(), pZineManager, pParentController);
		
		controller.setUp(this);
	}
}
