/**	ZineManager v0.0		Wf	20.10.2023
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  BasicParentEditorController
 * 			 	SingleZinePathEditorController
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

import java.io.File;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.stages.zineinventory.singleeditor.SingleZinePathEditorStage;
import org.zinemanager.logic.manager.LogManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class SingleZinePathEditorController<ParentController extends ParentControllerInterface>
			extends BasicParentEditorController<ParentController> {
	protected SingleZinePathEditorStage<? extends SingleZinePathEditorController<ParentController>, ParentController> stage;
	
	@FXML
	protected TextField tfName, tfPath;
	@FXML
	protected Button btFileSelector, btNewCount, btRemoveCount;
	
	/**	Wf	19.10.2023
	 * 
	 */
	public SingleZinePathEditorController() {
		super();
		
		stage = null;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	20.10.2023
	 * 
	 * @param pStage
	 * @throws Exception
	 */
	public void setUp(SingleZinePathEditorStage<? extends SingleZinePathEditorController<ParentController>, ParentController> pStage) throws Exception {
		stage = pStage;
		
		tfName.setText(basicManager.getZineName(editObjectID));
		tfPath.setText(basicManager.getZineFilePath(editObjectID));
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	19.10.2023
	 * 
	 */
	protected void setEnabledObjectInformations(boolean pEnable) {
		tfName.setDisable( !pEnable );
		tfPath.setDisable( !pEnable );
		
		btFileSelector.setDisable( !pEnable );
	}
	
	//----------------------------------------------------------------------------------------------------
	
	protected void updateAll() {}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	20.10.2023
	 * 
	 */
	@FXML
	public void openFileSelector() {
		File vFile;
		FileChooser vFileChooser = new FileChooser();
		
		vFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		vFileChooser.setTitle("Wähle Zine Datei");
		
		vFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("pdf", "*.pdf"));
		
		setDisabled();
		vFile = vFileChooser.showOpenDialog(stage);
		setEnabled();
		
		if (vFile != null) {
			if (basicManager.isFilePathValid(vFile.getAbsolutePath())) tfPath.setText(vFile.getAbsolutePath());
		}
		
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	20.10.2023
	 * 
	 */
	@FXML
	public void progress() {
		String vFilePath;
		
		if (isInputValid()) {
			try {
				vFilePath = tfPath.getText();
				
				basicManager.setZineFilePath(editObjectID, vFilePath);
				
				parentController.closeChildStage();
			} catch(Exception ex) {LogManager.handleException(ex);}
		} else LogManager.handleMessage("Eingaben nicht gültig!");
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	19.10.2023
	 * 
	 */
	protected boolean isInputValid() {
		return basicManager.isFilePathValid(tfPath.getText());
	}
}
