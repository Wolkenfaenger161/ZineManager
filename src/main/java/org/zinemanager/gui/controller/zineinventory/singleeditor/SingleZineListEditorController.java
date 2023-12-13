/**	ZineManager v0.0		Wf	05.10.2023
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  BasicParentEditorController
 * 			   SingleZineListEditorController
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
import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.logic.manager.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SingleZineListEditorController <ParentController extends ParentControllerInterface>
			extends BasicSingleEditorController<ParentController> {
	@FXML
	private Label lTitle;
	@FXML
	private TextField tfName;
	@FXML
	private Button btAddZine, btRemoveZine;
	
	@FXML
	private ListView<NameTableElement> lvSelectedZines, lvPotentialZines;
	
	private ObservableList<NameTableElement> liSelectedZines, liPotentialZines;
	
	/**	Wf	03.10.2023
	 * 
	 */
	public SingleZineListEditorController() {
		super();
		
		liSelectedZines  = null;
		liPotentialZines = null;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	05.10.2023
	 * 
	 */
	public void setUp() {
		if (editObjectID != -1) {
			lTitle.setText("Zineliste ändern");
			btProgress.setText("Ändern");
		}
		
		liSelectedZines  = FXCollections.observableArrayList();
		liPotentialZines = FXCollections.observableArrayList();
		
		try {
			liPotentialZines.setAll( generateNewNameTableElementList(basicManager.getZineIDs() , false, pID -> {return basicManager.getZineName(pID);}, null) );
			if (editObjectID != -1) {
				tfName.setText(basicManager.getZineListName(editObjectID));
				liSelectedZines.setAll( generateNewNameTableElementList(basicManager.getZineListZineIDs(editObjectID), false, pID -> {return basicManager.getZineName(pID);}, null) );
				
				for (NameTableElement vSelElement : liSelectedZines) {
					if (containsListID(liPotentialZines, vSelElement.getId())) liPotentialZines.remove(getElementFromList(liPotentialZines, vSelElement.getId()));
				}
			}
		}catch(Exception ex) {LogManager.handleException(ex);}
		
		lvSelectedZines.getSelectionModel().selectedItemProperty().addListener((pObs, pOldValue, pNewValue) -> {
			if ((pNewValue != null) && (lvPotentialZines.getSelectionModel().getSelectedItem() != null)) lvPotentialZines.getSelectionModel().clearSelection();
			
			setEnabledButtons(true);
		});
		lvPotentialZines.getSelectionModel().selectedItemProperty().addListener((pObs, pOldValue, pNewValue) -> {
			if ((pNewValue != null) && (lvSelectedZines.getSelectionModel().getSelectedItem() != null)) lvSelectedZines.getSelectionModel().clearSelection();
			
			setEnabledButtons(true);
		});
		
		lvSelectedZines.setItems(liSelectedZines);
		lvPotentialZines.setItems(liPotentialZines);
		
		setEnabled();
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	03.10.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledButtons(boolean pEnable) {
		btAddZine.setDisable( (lvPotentialZines.getSelectionModel().getSelectedItem() != null ? !pEnable : true) );
		btRemoveZine.setDisable( (lvSelectedZines.getSelectionModel().getSelectedItem() != null ? !pEnable : true) );
	}
	/**	Wf	03.10.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledLists(boolean pEnable) {
		lvSelectedZines.setDisable( !pEnable );
		lvPotentialZines.setDisable( !pEnable );
	}
	
	/**	Wf	03.10.2023
	 * 
	 */
	protected void setEnabledObjectInformations(boolean pEnable) {
		setEnabledButtons(pEnable);
		setEnabledLists(pEnable);
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	03.10.2023
	 * 
	 */
	@FXML
	public void addZine() {
		NameTableElement vPotSel = lvPotentialZines.getSelectionModel().getSelectedItem();
		
		if (vPotSel != null) {
			liSelectedZines.add(vPotSel);
			liPotentialZines.remove(vPotSel);
		} else LogManager.handleMessage("Kein mögliches Zine ausgewählt.");
	}
	/**	Wf	03.10.2023
	 * 
	 */
	@FXML
	public void removeZine() {
		NameTableElement vPotSel = lvSelectedZines.getSelectionModel().getSelectedItem();
		
		if (vPotSel != null) {
			liPotentialZines.add(vPotSel);
			liSelectedZines.remove(vPotSel);
		} else LogManager.handleMessage("Kein ausgewähltes Zine ausgewählt.");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	03.10.2023
	 * 
	 */
	@FXML
	public void progress() {
		if (isInputValid()) {
			try {
				if (editObjectID == -1) basicManager.addZineList( tfName.getText(), convertToIDList(liSelectedZines));
				else {
					basicManager.setZineListName(editObjectID, tfName.getText());
					basicManager.setZineListZindIDs(editObjectID, convertToIDList(liSelectedZines));
				}
				
				parentController.closeChildStage();
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Ungültige Eingabe!");
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	03.10.2023
	 * 
	 */
	protected boolean isInputValid() {
		return !tfName.getText().equals("");
	}
	
}
