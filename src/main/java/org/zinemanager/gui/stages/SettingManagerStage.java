/**	ZineManager v0.2		Wf	20.01.2024
 * 	
 * 	gui.stages.zineinventory
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			SettingManagerStage
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

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.controller.SettingManagerController;
import org.zinemanager.logic.manager.SettingManager;

public class SettingManagerStage<ParentController extends ParentControllerInterface>
			 extends BasicManagerStage<SettingManager, SettingManagerController<ParentController>, ParentController> {

	/**	Wf	20.01.2024
	 * 
	 * @param pSettingManager
	 * @param pParentController
	 */
	public SettingManagerStage(SettingManager pSettingManager, ParentController pParentController) {
		super("org/zinemanager/gui/scenes/SettingManagerScene.fxml", "Einstellungen", pSettingManager, pParentController);
		
		controller.setUp(this);
	}
	
}
