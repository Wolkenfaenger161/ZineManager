/**	ZineManager v0.0		Wf	03.10.2023
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  SingleCategoryEditorController
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

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.logic.manager.LogManager;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SingleCategoryEditorController<ParentController extends ParentControllerInterface> extends BasicSingleEditorController<ParentController> {
	@FXML
	private TextField tfName;
	
	/**	Wf	29.09.2023
	 * 
	 */
	public SingleCategoryEditorController() {
		super();
	}
	
	//------------------------------------------------------------------------------------------------
	
	/**	Wf	03.10.2023
	 * 
	 */
	public void setUp() throws Exception {
		if (editObjectID != -1) {
			btProgress.setText("Ändern");
			tfName.setText( basicManager.getCategoryName(editObjectID) );
		}
		
		tfName.setOnAction(pEvent -> {
			progress();
		});
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	29.09.2023
	 * 
	 */
	protected void setEnabledObjectInformations(boolean pEnable) {
		tfName.setDisable(!pEnable);
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	29.09.2023
	 * 
	 */
	@FXML
	public void progress() {
		if (isInputValid()) {
			try {
				if (editObjectID == -1) basicManager.addCategory(tfName.getText());
				else 					basicManager.setCategoryName(editObjectID, tfName.getText());
			
				super.parentController.closeChildStage();
			} catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Eingabe ungültig!");
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	29.09.20203
	 * 
	 */
	protected boolean isInputValid() {
		boolean vRet = false;
		
		if (tfName.getText() != "") {
			vRet = true;
			for (Integer vCategoryID : basicManager.getCategoryIDs()) {
				try {	if ( basicManager.getCategoryName(vCategoryID).equals(tfName.getText()) 
						&& (vCategoryID != editObjectID)) vRet = false; }
				catch(Exception ex) {LogManager.handleException(ex);}
			}
		}
		
		return vRet;
	}
	
}
