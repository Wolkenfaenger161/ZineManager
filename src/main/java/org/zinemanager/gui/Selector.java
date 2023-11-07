/**	ZineManager v0.0		Wf	15.10.2023
 * 	
 * 	gui
 * 	  Selector
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

import org.zinemanager.gui.stages.SelectionStage;
import org.zinemanager.gui.tableelements.NameTableElement;

public class Selector{
	private String title;
	private ArrayList<NameTableElement> selectionObjects;
	
	private SelectionStage selStage;
	
	/**	Wf	13.10.2023
	 * 
	 * @param pTitle
	 * @param pSelectionObjects
	 */
	public Selector(String pTitle, ArrayList<NameTableElement> pSelectionObjects) {
		title = pTitle;
		selectionObjects = pSelectionObjects;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	15.10.2023
	 * 
	 * @return
	 */
	public int getSelection() {
		int vRet;
		
		selStage = new SelectionStage(title, selectionObjects);
		selStage.showAndWait();
		
		vRet = selStage.getController().getSelectedID();
		selStage = null;
		
		return vRet;
	}

}
