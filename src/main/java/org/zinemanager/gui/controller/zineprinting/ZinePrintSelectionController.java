/**	ZineManager v0.1		Wf	03.12.2023
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  BasicParentEditorController
 * 			  	BasicMultiEditorController
 * 				  ZinePrintSelectionController
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

package org.zinemanager.gui.controller.zineprinting;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.controller.zineinventory.multieditor.BasicMultiEditorController;
import org.zinemanager.gui.stages.zineprinting.ZineNumberSelectionStage;
import org.zinemanager.gui.tableelements.BasicTableElement;
import org.zinemanager.logic.manager.LogManager;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;

public class ZinePrintSelectionController<ParentController extends ParentControllerInterface> 
			extends BasicMultiEditorController<ParentController> {
	private int selectedZineListID;
	
	@FXML
	private Button btTotalSelection;

	/**	Wf	20.11.2023
	 * 
	 */
	public ZinePrintSelectionController() {
		selectedZineListID = -2;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	20.11.2023
	 * 
	 */
	public void setUp() throws Exception{
		super.setUp();
		
		lvObjects.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		setEnabled();
	}
	
	/**	Wf	20.11.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void setSelectedZineListID(int pID) throws Exception {
		if (pID >= -2) selectedZineListID = pID;
		else throw new Exception("02; sSZLID,ZPSC");
		
		updateAll();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	20.11.2023
	 * 
	 */
	protected void setEnabledObjectInformations(boolean pEnabled) {}
	
	/**	Wf	20.11.2023
	 * 
	 */
	protected void setEnabledButtons(boolean pEnable) {
		btProgress.setDisable(lvObjects.getSelectionModel().getSelectedItems() != null ? !pEnable : false);
		
		btTotalSelection.setDisable( !pEnable );
		btBack.setDisable( !pEnable );
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	20.11.2023
	 * 
	 */
	protected void updateObjectInformation() {}
	
	/**	Wf	20.11.2023
	 * 
	 */
	protected void updateList() {
		if (selectedZineListID >= -1) {
			try {
				updateNameTableElementList(liObjects, (selectedZineListID != -1) ? basicManager.getZineListZineIDs(selectedZineListID) : basicManager.getZineIDs(),
						false, pID -> {return basicManager.getZineName(pID);}, lvObjects.getSelectionModel());
			} catch(Exception ex) {LogManager.handleException(ex);}
		}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	03.12.2023
	 * 
	 */
	@FXML
	public void progress() {
		try {			
			createZinePrintingElements(lvObjects.getSelectionModel().getSelectedItems());
			
			startZineNumberSelection();
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	/**	Wf	03.12.2023
	 * 
	 */
	@FXML
	public void selectAll() {
		try {
			createZinePrintingElements(liObjects);
			
			startZineNumberSelection();
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
	/**	Wf	20.11.2023
	 * 
	 */
	public void editObject() {}
	/**	Wf	20.11.2023
	 * 
	 */
	public void removeObject() {}
	
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	03.12.2023
	 * 
	 * @param pPrintingZineIDs
	 * @throws Exception
	 */
	private void createZinePrintingElements(ObservableList<? extends BasicTableElement> pPrintingZineIDs) throws Exception {
		basicManager.getZinePrintingManager().clearPrintingElements();
		
		for (BasicTableElement vTempZine : pPrintingZineIDs) {
			basicManager.addZineToPrintingManager(vTempZine.getId());
		}
	}
	/**	Wf	03.12.2023
	 * 
	 */
	private void startZineNumberSelection() {
		if (basicManager.getZinePrintingManager().getPrintingElementIDs().size() >= 1) {
			childStage = new ZineNumberSelectionStage<ZinePrintSelectionController<ParentController>>(basicManager.getZinePrintingManager(), this);
		
			setDisabled();
		}
	}
	
}
