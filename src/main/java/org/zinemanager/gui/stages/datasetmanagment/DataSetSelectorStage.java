/**	ZineManager v0.0		Wf	19.10.2023
 * 	
 * 	gui.stages.datasetmanagment
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			BasicSingleEditorStage
 * 			  BasicMultiEditorStage
 * 				DataSetSelectorStage
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

package org.zinemanager.gui.stages.datasetmanagment;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.controller.datasetmanagment.DataSetSelectorController;
import org.zinemanager.gui.stages.zineinventory.multieditor.BasicMultiEditorStage;
import org.zinemanager.logic.manager.ZineManager;

public class DataSetSelectorStage<ParentController extends ParentControllerInterface>
			extends BasicMultiEditorStage<DataSetSelectorController<ParentController>, ParentController> {

	/**	Wf	19.10.2023
	 * 
	 * @param pManager
	 * @param pParentController
	 * @param pDeletingLockID
	 */
	public DataSetSelectorStage(ZineManager pManager, ParentController pParentController) {
		super("org/zinemanager/gui/scenes/datasetmanagment/DataSetSelectorScene.fxml", "DataSet Auswahl", pManager, pParentController);
		
		controller.setStage(this);
	}
	
}
