/**	ZineManager v0.0		Wf	28.09.2023
 * 	
 * 	gui.controller
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
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

import org.zinemanager.logic.manager.BasicManager;

public abstract class BasicManagerController<Manager extends BasicManager, ParentController extends ParentControllerInterface>
					  extends ChildController<ParentController> {
	protected Manager basicManager;
	
	/**	Wf 28.09.2023
	 * 
	 */
	public BasicManagerController() {
		basicManager = null;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	28.09.2023
	 * 
	 * @param pBasicController
	 * @throws Exception
	 */
	public void setBasicManager(Manager pBasicManager) throws Exception {
		if (pBasicManager != null) basicManager = pBasicManager;
		else throw new Exception("04; sBM,BMC");
	}
}
