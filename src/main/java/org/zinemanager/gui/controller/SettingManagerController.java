/**	ZineManager v0.2		Wf	23.01.2024
 * 	
 * 	gui.controller
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			SettingManagerController
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

package org.zinemanager.gui.controller;

import java.io.File;

import javax.print.attribute.standard.Sides;

import org.zinemanager.gui.stages.SettingManagerStage;
import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.SettingManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class SettingManagerController<ParentController extends ParentControllerInterface>
			  extends BasicManagerController<SettingManager, ParentController> {
	private SettingManagerStage<ParentController> stage;
	
	@FXML
	private Label lTitle, lVersion;
	@FXML
	private TextField tfPDFReaderFilePath;
	@FXML
	private Button btPDFReaderFilePathImporter, btClearLogs, btSave, btBack;
	
	@FXML
	private ComboBox<NameTableElement> cbDoublesidePrintart;
	@FXML
	private CheckBox cbDefaultExtracoverPrint;
	
	private ObservableList<NameTableElement> liDoublesidePrintart;
	
	/**	Wf	23.01.2024
	 * 
	 */
	public SettingManagerController() {
		stage = null;
		
		liDoublesidePrintart = FXCollections.observableArrayList();
		
		liDoublesidePrintart.add(new NameTableElement(0, "Duplexdruck (lange Kante)"));
		liDoublesidePrintart.add(new NameTableElement(1, "Tumpledruck (kurze Kante)"));
	}
	
	//----------------------------------------------------------------------------------------------------
		
	/**	Wf	20.01.2024
	 * 
	 */
	public void setUp(SettingManagerStage<ParentController> pStage) {
		stage = pStage;
		lVersion.setText("v"+LogManager.getVersion());
		
		cbDoublesidePrintart.setItems(liDoublesidePrintart);
		
		tfPDFReaderFilePath.setText(basicManager.getPDFReaderFilePath());
		
		cbDoublesidePrintart.getSelectionModel().select( basicManager.getDoublesidePrintart() == Sides.TUMBLE ? 1 : 0);
		cbDefaultExtracoverPrint.setSelected(basicManager.isDeafultExtracoverPrint());
		
		
		setEnabled();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	19.01.2024
	 * 
	 * @param pEnable
	 */
	private void setEnabledTextFields(boolean pEnable) {
		tfPDFReaderFilePath.setDisable( !pEnable );
	}
	/**	Wf	19.01.2024
	 * 
	 * @param pEnable
	 */
	private void setEnabledButtons(boolean pEnable) {
		btClearLogs.setDisable( !pEnable );
		
		btSave.setDisable( !pEnable );
		btBack.setDisable( !pEnable );
	}
	/**	Wf	19.01.2024
	 * 
	 * @param pEnable
	 */
	private void setEnabledBoxElements(boolean pEnable) {
		cbDefaultExtracoverPrint.setDisable( !pEnable );
		
		cbDoublesidePrintart.setDisable( !pEnable );
	}
	
	/**	Wf	19.01.2024
	 * 
	 */
	protected void setEnabled(boolean pEnable) {
		setEnabledTextFields(pEnable);
		setEnabledButtons(pEnable);
		setEnabledBoxElements(pEnable);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	20.01.2024
	 * 
	 */
	@FXML
	public void choosePDFReaderFilePath() {
		File vFile;
		FileChooser vFileChooser = new FileChooser();
		
		vFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		vFileChooser.setTitle("Wähle einen PDF Reader");
		
		if ( System.getProperty("os.name").contains("Windows") ) vFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("exe", "*.exe"));
		
		setDisabled();
		vFile = vFileChooser.showOpenDialog(stage);
		setEnabled();
		
		if (vFile != null) {
			if (basicManager.isPDFReaderFilePathValid(vFile.getAbsolutePath())) tfPDFReaderFilePath.setText(vFile.getAbsolutePath());
		}
	}
	
	/**	Wf	19.01.2024
	 * 
	 */
	@FXML
	public void clearLogs() {
		try {
			LogManager.clearLogs();
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
	/**	Wf	19.01.2024
	 * 
	 */
	@FXML
	public void saveSettings() {
		try {
			if (basicManager.isPDFReaderFilePathValid( tfPDFReaderFilePath.getText() )) basicManager.setPDFReaderFilePath( tfPDFReaderFilePath.getText() );
			else LogManager.handleMessage("PDF Reader Datei Pfad Eingabe nicht gültig!");
			
			if (cbDoublesidePrintart.getSelectionModel().getSelectedItem() != null)
				basicManager.setDoublesidePrintart((cbDoublesidePrintart.getSelectionModel().getSelectedItem().getId() == 1 ? Sides.TUMBLE : Sides.DUPLEX)) ;
			basicManager.setDefaultExtracoverPrint( cbDefaultExtracoverPrint.isSelected() );
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
}
