/**	ZineManager v0.0		Wf	12.11.2023
 * 	
 * 	gui.controller
 * 	  BasicController
 * 		ChildController
 * 		  SelectionController
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

import java.util.ArrayList;

import org.zinemanager.gui.stages.SelectionStage;
import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.logic.manager.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;

public class SelectionController extends BasicController {
	private int selectedID;
	
	private SelectionStage selStage;
	
	@FXML
	private Label lTitle;
	@FXML
	private Button btSelect, btBack;
	
	@FXML
	private ListView<NameTableElement> lvSelectionObjects;
	
	private ObservableList<NameTableElement> liSelectionObjects;

	/**	Wf	15.10.2023
	 * 
	 */
	public SelectionController() {
		super();
		
		selectedID = -1;
	}
	
	//----------------------------------------------------------------------------------------------------
		
	/**	Wf	12.11.2023
	 * 
	 * @param pTitle
	 * @param pSelectionObjects
	 */
	public void setUp(String pTitle, ArrayList<NameTableElement> pSelectionObjects, SelectionStage pSelStage) {
		selStage = pSelStage;
		lTitle.setText(pTitle);
		
		liSelectionObjects = FXCollections.observableArrayList();
		
		lvSelectionObjects.getSelectionModel().selectedItemProperty().addListener((pObs, pOldValue, pNewValue) -> {
			if (pNewValue != pOldValue) {
				if (pNewValue == null) selectedID = -1;
				else selectedID = pNewValue.getId();
				
				setEnabled(true);
			}
		});
		lvSelectionObjects.setOnMouseClicked(pEvent -> {
			if ((pEvent.getButton() == MouseButton.PRIMARY) && (pEvent.getClickCount() == 2) && (lvSelectionObjects.getSelectionModel().getSelectedItem() != null)) {
				select();
				
				pEvent.consume();
			}
		});
		
		lvSelectionObjects.setItems(liSelectionObjects);
		liSelectionObjects.setAll(pSelectionObjects);
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	13.10.2023
	 * 
	 * @param pEnable
	 */
	protected void setEnabledButtons(boolean pEnable) {
		btSelect.setDisable( (lvSelectionObjects.getSelectionModel().getSelectedItem() != null) ? !pEnable : true );
		btBack.setDisable( !pEnable );
	}
	
	/**	Wf	13.10.2023
	 * 
	 */
	protected void setEnabled(boolean pEnable) {
		setEnabledButtons(pEnable);
		
		lvSelectionObjects.setDisable( !pEnable );
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	13.10.2023
	 * 
	 * @return
	 */
	public int getSelectedID() {		
		return selectedID;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	15.10.2023
	 * 
	 */
	@FXML
	public void select() {
		NameTableElement vCurSel = lvSelectionObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			selectedID = vCurSel.getId();
			
			selStage.hide();
		}else LogManager.handleMessage("Keine Auswahl selektiert.");
	}
	
	/**	Wf	15.10.2023
	 * 
	 */
	@FXML
	public void back() {		
		selectedID = -1;
		selStage.hide();
	}
	
}
