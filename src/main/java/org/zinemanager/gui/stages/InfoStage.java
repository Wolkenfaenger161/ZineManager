/**	ZineManager v0.0		Wf	20.10.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
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

package org.zinemanager.gui.stages;

import org.zinemanager.MainManager;
import org.zinemanager.gui.controller.InfoController;

public class InfoStage extends BasicStage<InfoController> {
	protected MainManager mainManager;
	
	/**	Wf	20.10.2023
	 * 
	 * @param pIsError
	 * @param pMessage
	 * @param pMainManager
	 */
	public InfoStage(boolean pIsError, String pMessage, MainManager pMainManager) {
		super("org/zinemanager/gui/scenes/InfoScene.fxml",(pIsError == true ? "Fehler!" : "Information" ));
		
		this.setAlwaysOnTop(true);
		
		mainManager = pMainManager;
		
		controller.setUp(pMessage, this);
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	27.09.202
	 * 
	 */
	public void closeInfoStage() {
		mainManager.removeInfoStage(this);
	}
	
}
