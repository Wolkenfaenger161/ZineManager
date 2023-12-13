/**	ZineManager v0.0		Wf	05.10.2023
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  BasicParentEditorController
 * 			  	BasicMultiEditorController
 * 				  MultiCategoryEditorController
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

package org.zinemanager.gui.controller.zineinventory.multieditor;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.stages.zineinventory.singleeditor.SingleCategoryEditorStage;
import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.logic.manager.LogManager;

import javafx.fxml.FXML;

public class MultiCategoryEditorController<ParentController extends ParentControllerInterface> extends BasicMultiEditorController<ParentController> {
	
	/**	Wf	03.10.2023
	 * 
	 */
	public MultiCategoryEditorController() {
		super();
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	03.10.2023
	 * 
	 */
	protected void setEnabledObjectInformations(boolean pEnable) {}
	
	//------------------------------------------------------------------------------------------------
	
	/**	Wf	03.10.2023
	 * 
	 */
	protected void updateObjectInformation() {}
	/**	Wf	05.10.2023
	 * 
	 */
	protected void updateList() {
		try { updateNameTableElementList(liObjects, basicManager.getCategoryIDs(), false, 
										 pID -> {return basicManager.getCategoryName(pID);}, lvObjects.getSelectionModel()); }
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	03.10.2023
	 * 
	 */
	@FXML
	public void progress() {
		childStage = new SingleCategoryEditorStage<MultiCategoryEditorController<ParentController>>(basicManager, this, -1);
		setDisabled();
	}
	/**	Wf	03.10.2023
	 * 
	 */
	@FXML
	public void editObject() {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			childStage = new SingleCategoryEditorStage<MultiCategoryEditorController<ParentController>>(basicManager, this, vCurSel.getId());
			setDisabled();
		} else LogManager.handleMessage("Keine Kategorie ausgewählt.");
	}
	
	
	/**	Wf	29.09.2023
	 * 
	 */
	@FXML
	protected void removeObject() {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			try {
				liObjects.remove(vCurSel);
				lvObjects.getSelectionModel().clearSelection();
				
				basicManager.removeCategory(vCurSel.getId());
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Keine Kategorie entfernt!\nEs ist keine Kategorie ausgewählt.");
	}
	
//--------------------------------------------------------------------------------------------------------


}
