/**	ZineManager v0.0		Wf	01.10.2023
 * 	
 * 	gui.callables
 * 	 GUIListElementFormater
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

package org.zinemanager.gui.callables;

import org.zinemanager.gui.tableelements.BasicTableElement;

public interface GUIListElementFormater<T extends BasicTableElement> {
	public T formatGUIListElement(int pID) throws Exception;
}
