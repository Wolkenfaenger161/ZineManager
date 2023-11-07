/**	ZineManager v0.0		Wf	10.10.2023
 * 	
 * 	gui.stages.datasetmanagment
 * 	  BasicStage
 * 		ChildStage
 * 		  BasicManagerStage
 * 			DataSetEditorStage
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
import org.zinemanager.gui.controller.datasetmanagment.DataSetEditorController;
import org.zinemanager.gui.stages.BasicManagerStage;
import org.zinemanager.logic.manager.DataSetManager;
import org.zinemanager.logic.manager.LogManager;

public class DataSetEditorStage<ParentController extends ParentControllerInterface> 
			extends BasicManagerStage<DataSetManager, DataSetEditorController<ParentController>, ParentController> {

	/**	Wf	10.10.2023
	 * 
	 * @param pDataSetManager
	 * @param pParentController
	 * @param pEditedDataSetID
	 */
	public DataSetEditorStage(DataSetManager pDataSetManager, ParentController pParentController, int pEditedDataSetID) {
		super("org/zinemanager/gui/scenes/datasetmanagment/DataSetEditorScene.fxml", 
				(pEditedDataSetID == -1) ? "DataSet Erstellung" : "DataSet Bearbeitung" , pDataSetManager, pParentController);
		
		try {controller.setUp(pEditedDataSetID);}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
}
