/**	ZineManager v0.0		Wf	11.11.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		MainMenuStage
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

import org.zinemanager.gui.controller.MainMenuController;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZineManager;

public class MainMenuStage extends BasicStage<MainMenuController> {

	/**	Wf	11.11.2023
	 * 
	 * @param pZineManager
	 */
	public MainMenuStage(ZineManager pZineManager) {
		super("org/zinemanager/gui/scenes/MainMenuScene.fxml", "Zine Manager v"+LogManager.getVersion());
		
		controller.setUp(pZineManager, this);
	}
}
