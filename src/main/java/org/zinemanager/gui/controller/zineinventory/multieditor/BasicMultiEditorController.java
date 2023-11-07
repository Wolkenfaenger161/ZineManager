/**	ZineManager v0.0		Wf	03.10.2023
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  BasicParentEditorController
 * 			  	BasicMultiEditorController
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
import org.zinemanager.gui.controller.zineinventory.singleeditor.BasicParentEditorController;
import org.zinemanager.gui.tableelements.NameTableElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public abstract class BasicMultiEditorController<ParentController extends ParentControllerInterface>
				extends BasicParentEditorController<ParentController>
				implements ParentControllerInterface {

	@FXML
	protected Button btEdit, btRemove;
	@FXML
	protected ListView<NameTableElement> lvObjects;
	
	protected ObservableList<NameTableElement> liObjects;
	
	
	/**	Wf	03.10.2023
	 * 
	 */
	public BasicMultiEditorController() {
		super();
		
		liObjects = null;
	}
	
	//------------------------------------------------------------------------------------------------
	
	/**	Wf	03.10.2023
	 * 
	 * @throws Exception
	 */
	public void setUp() throws Exception{
		liObjects = FXCollections.observableArrayList();
		
		lvObjects.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		lvObjects.getSelectionModel().selectedItemProperty().addListener((pObs, pOldSel, pNewSel) -> {
			if (pNewSel == null) super.editObjectID = -1;
			else super.editObjectID = pNewSel.getId();
			
			setEnabled();
			updateObjectInformation();
		});
		
		updateList();;
		lvObjects.setItems(liObjects);
		
		setEnabled();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	03.10.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledButtons(boolean pEnable) {
		setEnabledProcessButtons(pEnable);
		
		btEdit.setDisable( ((lvObjects.getSelectionModel().getSelectedItem() == null) ? true : !pEnable));
		btRemove.setDisable( ((lvObjects.getSelectionModel().getSelectedItem() == null) ? true : !pEnable));
	}
	
	/**	Wf	28.09.2023
	 * 
	 * @param pEnable
	 */
	protected void setEnabled(boolean pEnable) {
		setEnabledObjectInformations(pEnable);
		setEnabledButtons(pEnable);
	}
	
	//------------------------------------------------------------------------------------------------
	
	/**	Wf	28.09.2023
	 * 
	 */
	protected abstract void updateObjectInformation();
	/**	Wf	28.09.203
	 * 
	 */
	protected abstract void updateList();
	
	/**	Wf	03.10.2023
	 * 
	 */
	protected void updateAll() {
		updateObjectInformation();
		updateList();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	03.10.2023
	 * 
	 */
	protected abstract void editObject();
	/**	Wf	28.09.2023
	 * 
	 */
	protected abstract void removeObject();
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	03.10.2023
	 * 
	 * @return
	 */
	protected boolean isInputValid() {return true;}
	
}
