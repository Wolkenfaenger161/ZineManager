/**	ZineManager v0.0		Wf	29.09.2023
 * 	
 * 	gui.controller
 * 	  BasicController
 * 		ChildController
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

public abstract class ChildController<ParentController extends ParentControllerInterface> extends BasicController {
	protected ParentController parentController;
	
	/**	Wf 28.09.2023
	 * 
	 */
	public ChildController() {
		parentController = null;
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	29.09.2023
	 * 
	 */
	public void setDisabled() {
		setEnabled(false);
	}
	/**	Wf	29.09.2023
	 * 
	 */
	public void setEnabled() {
		setEnabled(true);
	}
	
	/**	Wf	29.09.2023
	 * 
	 * @param pEnable
	 */
	protected abstract void setEnabled(boolean pEnable);
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	27.09.2023
	 * 
	 * @param pParentController
	 * @throws Exception
	 */
	public void setParentController(ParentController pParentController) throws Exception{
		if (pParentController != null) parentController = pParentController;
		else throw new Exception("04; sPC,ChiC");
	}

	/**	Wf	27.09.2023
	 * 
	 */
	public void back() {
		parentController.closeChildStage();
	}
}
