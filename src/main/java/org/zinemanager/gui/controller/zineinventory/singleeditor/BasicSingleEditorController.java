/**	ZineManager v0.0		Wf	28.09.2023
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
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

import org.zinemanager.gui.controller.BasicManagerController;
import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.logic.manager.ZineManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class BasicSingleEditorController<ParentController extends ParentControllerInterface>
				extends BasicManagerController<ZineManager, ParentController> {
	protected int editObjectID;
	
	@FXML
	protected Button btProgress, btBack;
	
	/**	Wf	28.09.2023
	 * 
	 */
	public BasicSingleEditorController() {
		super();
		
		editObjectID = -1;
	}
	
	//------------------------------------------------------------------------------------------------
	
	/**	Wf	28.09.2023
	 * 
	 * @param pEditObjectID
	 * @throws Exception
	 */
	public void setEditObjectID(int pEditObjectID) throws Exception{
		editObjectID = pEditObjectID;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	28.09.2023
	 * 
	 * @param pEnable
	 */
	protected abstract void setEnabledObjectInformations(boolean pEnable); 
	/**	Wf	28.09.2023
	 * 
	 * @param pEnable
	 */
	protected void setEnabledProcessButtons(boolean pEnable) {
		btProgress.setDisable(!pEnable);
		btBack.setDisable(!pEnable);
	}
	
	/**	Wf	28.09.2023
	 * 
	 * @param pEnable
	 */
	protected void setEnabled(boolean pEnable) {
		setEnabledObjectInformations(pEnable);
		setEnabledProcessButtons(pEnable);
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	28.09.2023
	 * 
	 * @return
	 */
	protected abstract boolean isInputValid();
	
//--------------------------------------------------------------------------------------------------------
	
}
