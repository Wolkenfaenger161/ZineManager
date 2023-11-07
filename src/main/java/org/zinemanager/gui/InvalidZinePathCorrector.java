/**	ZineManager v0.0		Wf	20.10.2023
 * 	
 * 	gui
 * 	  InvalidZinePathCorrector
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

package org.zinemanager.gui;

import java.util.ArrayList;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.controller.zineinventory.singleeditor.SingleZinePathEditorController;
import org.zinemanager.gui.stages.zineinventory.singleeditor.SingleZinePathEditorStage;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZineManager;

public class InvalidZinePathCorrector implements ParentControllerInterface {
	private ZineManager zineManager;
	private ArrayList<Integer> invalidFilePathZineIDs;
	
	private SingleZinePathEditorStage<SingleZinePathEditorController<InvalidZinePathCorrector> , InvalidZinePathCorrector> curStage;
	
	/**	Wf	20.10.2023
	 * 
	 * @param pInvalidFilePathZineIDs
	 */
	public InvalidZinePathCorrector(ZineManager pZineManager, ArrayList<Integer> pInvalidFilePathZineIDs) {
		zineManager = pZineManager;
		
		invalidFilePathZineIDs = pInvalidFilePathZineIDs;
		curStage = null;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	20.10.2023
	 * 
	 */
	public void setDisabled() {}
	/**	Wf	20.10.2023
	 * 
	 */
	public void setEnabled() {}
	
	/**	Wf	20.10.2023
	 * 
	 */
	public void closeChildStage() {
		if (curStage != null) curStage.hide();
	}
	
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	20.10.2023
	 * 
	 */
	public void correctZinePaths() {
		ArrayList<Integer> vRemovingIDs = new ArrayList<>();
		
		LogManager.handleMessage("Fehlerhafte Zine Dateipfade gefunden!\nBitte korrigieren.");
		
		for (Integer vZineID : invalidFilePathZineIDs) {
			curStage = new SingleZinePathEditorStage<SingleZinePathEditorController<InvalidZinePathCorrector> , InvalidZinePathCorrector>(zineManager, this, vZineID.intValue());
			
			curStage.showAndWait();
			try { if (zineManager.isZineFilePathValid(vZineID)) vRemovingIDs.add(vZineID); }
			catch(Exception ex) {LogManager.handleException(ex);}
		}
		
		for (Integer vRemovingID : vRemovingIDs) {
			invalidFilePathZineIDs.remove(vRemovingID);
		}
	}
}
