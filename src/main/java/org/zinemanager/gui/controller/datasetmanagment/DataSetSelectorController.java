/**	ZineManager v0.0		Wf	20.10.2023
 * 	
 * 	gui.controller.datasetmanagment
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  BasicParentEditorController
 * 			  	BasicMultiEditorController
 * 				  DataSetSelectorController
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


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.zinemanager.gui.InvalidZinePathCorrector;
import org.zinemanager.gui.Selector;
import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.controller.zineinventory.multieditor.BasicMultiEditorController;
import org.zinemanager.gui.stages.datasetmanagment.DataSetEditorStage;
import org.zinemanager.gui.stages.datasetmanagment.DataSetSelectorStage;
import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.logic.exceptions.InvalidZinePathFileException;
import org.zinemanager.logic.manager.DataSetManager;
import org.zinemanager.logic.manager.LogManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

public class DataSetSelectorController<ParentController extends ParentControllerInterface>
				extends BasicMultiEditorController<ParentController>{
	private int currentDataSetID;
	private DataSetManager dataSetManager;
	private DataSetSelectorStage<ParentController> stage;
	
	@FXML
	private MenuItem miNew, miImport, miExport, miBack,
					 miEdit, miDelete, miAbout;
	@FXML
	private Button btNew, btDataSetExport, btDataSetImport;
	
	/**	Wf	19.10.2023
	 * 
	 */
	public DataSetSelectorController() {
		super();
		
		dataSetManager = null;
		currentDataSetID = -1;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf 19.10.2023
	 * 
	 */
	public void setUp() throws Exception{
		dataSetManager = basicManager.getDataSetManager();
		
		currentDataSetID = basicManager.getCurrentDataSetID();
		super.setUp();
		
		miAbout.setDisable(true);
	}
	
	/**	Wf	15.10.2023
	 * 
	 * @param pStage
	 */
	public void setStage(DataSetSelectorStage<ParentController> pStage) {
		stage = pStage;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	19.10.2023
	 * 
	 * @param pEnable
	 */
	protected void setEnabledMenuItems(boolean pEnable) {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		miNew.setDisable( !pEnable );
		miImport.setDisable( !pEnable );
		//-----
		miBack.setDisable( (currentDataSetID != -1) ? !pEnable : true );
		//miAbout.setDisable( !pEnable );
		
		miExport.setDisable( (vCurSel != null) ? !pEnable : true );
		//-----
		miEdit.setDisable( (vCurSel != null) ? !pEnable : true );
		miDelete.setDisable( (vCurSel != null) ? !pEnable : true );
	}
	
	/**	Wf 10.10.2023
	 * 
	 */
	protected void setEnabledObjectInformations(boolean pEnable) {}
	/**	Wf	19.10.2023
	 * 
	 */
	protected void setEnabledProcessButtons(boolean pEnable) {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		btNew.setDisable( !pEnable );
		
		btDataSetExport.setDisable( (vCurSel != null) ? !pEnable : true );
		btDataSetImport.setDisable( !pEnable );
		
		btProgress.setDisable( (vCurSel != null) ? !pEnable : true);
		btBack.setDisable( (currentDataSetID != -1) ? !pEnable : true );
	}
	
	/**	Wf	11.10.2023
	 * 
	 */
	protected void setEnabled(boolean pEnable) {
		setEnabledMenuItems(pEnable);
		super.setEnabled(pEnable);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf 10.10.2023
	 * 
	 */
	protected void updateObjectInformation() {}
	
	/**	Wf	10.10.2023
	 * 
	 */
	protected void updateList() {
		try { updateNameTableElementList(liObjects, dataSetManager.getDataSetIDs(), false, 
				 pID -> {return dataSetManager.getDataSetName(pID);}, lvObjects.getSelectionModel()); }
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	10.10.2023
	 * 
	 */
	@FXML
	protected void newObject() {
		childStage = new DataSetEditorStage<DataSetSelectorController<ParentController>>(dataSetManager, this, -1);
		
		setDisabled();
	}
	/**	Wf	10.10.2023
	 * 
	 */
	@FXML
	protected void editObject() {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			try {
				childStage = new DataSetEditorStage<DataSetSelectorController<ParentController>>(dataSetManager, this, vCurSel.getId());
				
				setDisabled();
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Es ist kein DataSet ausgewählt.");
	}
	/**	Wf	19.10.2023
	 * 
	 */
	@FXML
	protected void removeObject() {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			try {
				liObjects.remove(vCurSel);
				lvObjects.getSelectionModel().clearSelection();
				
				if (currentDataSetID == vCurSel.getId()) currentDataSetID = -1;
				dataSetManager.removeDataSet(vCurSel.getId());
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Kein DataSet entfernt!\nEs ist kein (gültiges) DataSet ausgewählt.");
	}
	
	//-----
	
	/**	Wf	11.10.2023
	 * 
	 */
	@FXML
	protected void exportDataSet() {
		File vFile;
		FileChooser vFileChooser = new FileChooser();
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			vFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			vFileChooser.setTitle("Wähle Export Datei");
			
			vFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xml", "*.xml"));
			
			setDisabled();
			vFile = vFileChooser.showSaveDialog(stage);
			
			if (vFile != null) {
				if (!vFile.getAbsolutePath().contains(".xml")) vFile = new File(vFile.getAbsolutePath()+"/"+vCurSel.getName());
				
				try {
					dataSetManager.exportDataSet(vCurSel.getId(), vFile.getAbsolutePath());
					
					LogManager.handleMessage("Dataset exportiert.");
				}catch(Exception ex) {LogManager.handleException(ex);}
			}
			setEnabled();
		}else LogManager.handleMessage("Kein DataSet ausgewählt.");
	}
	
	/**	Wf	20.10.2023
	 * 
	 */
	@FXML
	protected void importDataSet() {
		int vDataSetID = -1;
		int vImportArtSelction = 0;
		
		File vFile;
		FileChooser vFileChooser = new FileChooser();
		
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		Selector vSel;
		
		setDisabled();
		if (vCurSel != null) {
			vSel = new Selector("Import Auswahl", new ArrayList<>(Arrays.asList(new NameTableElement(0, "Als neu importieren"), new NameTableElement(1, "Ausgewähltes ersetzten"))));
			
			vImportArtSelction = vSel.getSelection();
		}
		
		if (vImportArtSelction != -1) {
			vFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			vFileChooser.setTitle("Wähle Import Datei");
			
			vFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xml", "*.xml"));
			
			vFile = vFileChooser.showOpenDialog(stage);
			
			if (vFile != null) {
				try {
					if ((vImportArtSelction == 0) || (vCurSel == null)) {
						try { vDataSetID = dataSetManager.importDataSet(vFile.getAbsolutePath(), -1); }
						catch(InvalidZinePathFileException izpfex) {
							InvalidZinePathCorrector vCor = new InvalidZinePathCorrector(basicManager, izpfex.getInvalidFilePathZineIDs());
							
							vCor.correctZinePaths();
						}catch(Exception ex) { throw ex; }
						
						LogManager.handleMessage("Neues DataSet - "+ dataSetManager.getDataSetName(vDataSetID) +" - importiert.");
					}
					else {
						try { dataSetManager.importDataSet(vFile.getAbsolutePath(), vCurSel.getId()); }
						catch(InvalidZinePathFileException izpfex) {
							InvalidZinePathCorrector vCor = new InvalidZinePathCorrector(basicManager, izpfex.getInvalidFilePathZineIDs());
							
							vCor.correctZinePaths();
						}catch(Exception ex) { throw ex; }
						
						LogManager.handleMessage("DataSet ersetzt.");
					}
					
					updateAll();
				}catch(Exception ex) {LogManager.handleException(ex);}
			}
		}
		setEnabled();
	}
	
	//-----
	
	/**	Wf	20.10.2023
	 * 
	 */
	@FXML
	public void progress() {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			try {
				try { basicManager.loadDataSet(vCurSel.getId()); }
				catch(InvalidZinePathFileException izpfex) {
					InvalidZinePathCorrector vCor = new InvalidZinePathCorrector(basicManager, izpfex.getInvalidFilePathZineIDs());
					
					vCor.correctZinePaths();
				}catch(Exception ex) { throw ex; }
				
				parentController.closeChildStage();
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Kein DataSet ausgewählt.");
	}
	
	/**	Wf	19.10.2023
	 * 
	 */
	@FXML
	public void back() {
		if (currentDataSetID != -1) super.back();
		else LogManager.handleMessage("Zurück nicht möglich!\nBitte ein Dataset auswählen.");
	}
	
	//-----
	
	
	@FXML
	protected void openAbout() {
		
	}
}
