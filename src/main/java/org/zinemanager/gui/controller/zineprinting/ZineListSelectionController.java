/**	ZineManager v0.21		Wf	06.01.2025
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  BasicParentEditorController
 * 			  	BasicMultiEditorController
 * 				  MultiZineEditorController
 * 					ZineListSelectionController
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

import java.util.ArrayList;

import org.zinemanager.gui.Printer;
import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.controller.zineinventory.multieditor.BasicMultiEditorController;
import org.zinemanager.gui.stages.zineinventory.singleeditor.SingleZineListEditorStage;
import org.zinemanager.gui.stages.zineprinting.ZinePrintSelectionStage;
import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZinePrintingManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

public class ZineListSelectionController<ParentController extends ParentControllerInterface>
			extends BasicMultiEditorController<ParentController> {
	@FXML
	Button btFastPrint;
	
	/**	Wf	20.11.2023
	 * 
	 */
	public ZineListSelectionController() {
		super();
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	06.01.2025
	 * 
	 */
	public void setUp() throws Exception {
		super.setUp();
		
		lvObjects.setOnMouseClicked(pEvent -> {
			if ((pEvent.getButton() == MouseButton.PRIMARY) && (pEvent.getClickCount() == 2) && (lvObjects.getSelectionModel().getSelectedItem() != null)) {
				this.fastPrint();
				
				pEvent.consume();
			}
		});
		
		setEnabled();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	20.11.2023
	 * 
	 */
	protected void setEnabledObjectInformations(boolean pEnable) {}
	
	/**	Wf	06.01.2025
	 * 
	 * @param pEnable
	 */
	protected void setEnabledButtons(boolean pEnable) {
		NameTableElement vSel = lvObjects.getSelectionModel().getSelectedItem();
		
		btFastPrint.setDisable( ((vSel == null) ? true : !pEnable) );
		btProgress.setDisable( ((vSel == null) ? true : !pEnable) );
		
		btEdit.setDisable( (((vSel == null) || (vSel.getId() == -1)) ? true : !pEnable));
		btRemove.setDisable( (((vSel == null) || (vSel.getId() == -1)) ? true : !pEnable));
		
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
		try {updateNameTableElementList(liObjects, basicManager.getZineListIDs(), () -> {return new NameTableElement(-1, "Alle Zines");}, pID -> {return basicManager.getZineListName(pID);}, lvObjects.getSelectionModel());}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	06.01.2025
	 * 
	 */
	@FXML
	public void fastPrint() {
		Printer vPrinter;
		ZinePrintingManager vZinePrintingManager;
		ArrayList<Integer> vZineIDs;
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			try {
				vZinePrintingManager = basicManager.getZinePrintingManager();
				vZinePrintingManager.clearPrintingElements();
				
				vZineIDs =  vCurSel.getId() >= 0 ? basicManager.getZineListZineIDs(vCurSel.getId()) : basicManager.getZineIDs();				
				
				for (Integer vTempZineID : vZineIDs) {
					basicManager.addZineToPrintingManager(vTempZineID);
				}
				
				vPrinter = new Printer(vZinePrintingManager);
				
				setDisabled();
				vPrinter.startPrinting();
				setEnabled();
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Keine Zineliste ausgewählt.");
	}
	
	/**	Wf	20.11.2023
	 * 
	 */
	@FXML
	public void progress(){
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			childStage = new ZinePrintSelectionStage<ZineListSelectionController<ParentController>>(basicManager, this, vCurSel.getId());
			setDisabled();
		}else LogManager.handleMessage("Keine Zineliste ausgewählt.");
	}
	
	/**	Wf	20.11.2023
	 * 
	 */
	@FXML
	public void editObject() {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			if (vCurSel.getId() != -1) {
				childStage = new SingleZineListEditorStage<ZineListSelectionController<ParentController>>(basicManager, this, vCurSel.getId());
				setDisabled();
			} else LogManager.handleMessage("Ungültige ZineID.");
		}else LogManager.handleMessage("Keine Zineliste ausgewählt.");
	}
	
	/**	Wf	20.11.2023
	 * 
	 */
	@FXML
	protected void removeObject() {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			if (vCurSel.getId() != -1) {
				try {
					liObjects.remove(vCurSel);
					lvObjects.getSelectionModel().clearSelection();
					
					basicManager.removeZineList( vCurSel.getId() );
				}catch(Exception ex) {LogManager.handleException(ex);}
			}else LogManager.handleMessage("Ungültige ZineID");
		}else LogManager.handleMessage("Keine Zineliste entfernt!\nEs ist keine Zineliste ausgewählt.");
	}

	
}
