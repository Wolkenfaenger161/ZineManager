/**	ZineManager v0.0		Wf	10.10.2023
 * 	
 * 	gui.controller.datasetmanagment
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			DataSetEditorController
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

package org.zinemanager.gui.controller.datasetmanagment;

import org.zinemanager.gui.controller.BasicManagerController;
import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.logic.manager.DataSetManager;
import org.zinemanager.logic.manager.LogManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DataSetEditorController<ParentController extends ParentControllerInterface>
			extends BasicManagerController<DataSetManager, ParentController> {
	private int editDataSetID;
	
	@FXML
	private Label lTitle;
	@FXML
	private TextField tfName;
	@FXML
	private Button btProgress, btBack;
	
	/**	Wf	10.10.2023
	 * 
	 */
	public DataSetEditorController() {
		
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	10.10.2023
	 * 
	 * @param pDataSetID
	 */
	public void setUp(int pDataSetID) throws Exception {
		editDataSetID = pDataSetID;
		
		tfName.setOnAction(pEvent -> {
			progress();
		});
		
		if (editDataSetID != -1) {
			lTitle.setText("DataSet Bearbeiten");
			btProgress.setText("Bearbeiten");
			
			tfName.setText(basicManager.getDataSetName(editDataSetID));
		}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	10.10.2023
	 * 
	 */
	protected void setEnabled(boolean pEnable) {
		tfName.setDisable( !pEnable );
		
		btProgress.setDisable( !pEnable );
		btBack.setDisable( !pEnable );
	}

//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	10.10.2023
	 * 
	 */
	@FXML
	public void progress() {
		if (isInputValid()) {
			try {
				if (editDataSetID == -1) basicManager.addDataSet(tfName.getText());
				else basicManager.setDataSetName(editDataSetID, tfName.getText());
				
				parentController.closeChildStage();
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Eingaben nicht gültig.");
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	10.1.02023
	 * 
	 * @return
	 */
	protected boolean isInputValid() {
		boolean vRet = false;
		String vName = tfName.getText();
		
		vRet = !vName.equals("");
		
		if (vRet) {
			for (Integer vDataSetID : basicManager.getDataSetIDs()) {
				try {
					if ((vDataSetID.intValue() != editDataSetID) && (vName.equals( basicManager.getDataSetName(vDataSetID.intValue()) ))) {
						vRet = false;
						LogManager.handleMessage("Name schon vergeben.");
					}
				}catch(Exception ex) {LogManager.handleException(ex);}
			}
		}
		
		return vRet;
	}
	
}
