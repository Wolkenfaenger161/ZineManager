/**	ZineManager v0.0		Wf	15.10.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
 * 		ChildStage
 * 		  SelectionStage
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

import java.util.ArrayList;

import org.zinemanager.gui.controller.SelectionController;
import org.zinemanager.gui.tableelements.NameTableElement;

public class SelectionStage	extends BasicStage<SelectionController> {

	/**	Wf	15.10.2023
	 * 
	 * @param pTitle
	 * @param pParentController
	 * @param pSelectionObjects
	 */
	public SelectionStage(String pTitle, ArrayList<NameTableElement> pSelectionObjects) {
		super("org/zinemanager/gui/scenes/SelectionScene.fxml", pTitle, false);
		
		controller.setUp(pTitle, pSelectionObjects, this);
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	13.10.2023
	 * 
	 * @return
	 */
	public SelectionController getController() {
		return controller;
	}
	
}
