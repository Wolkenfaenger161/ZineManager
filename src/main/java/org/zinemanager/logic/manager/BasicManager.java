/**	ZineManager v0.0	Wf	07.10.2023
 * 
 * 	logic.manager
 * 	BasicManager
 * 
 * 	Exceptions:
 * 	  01 Wrong length
 * 	  02 Wrong Value
 * 	  03 Calculation Error
 * 	  04 Nullpointer Error
 * 	  05 Empty List Error
 * 	  06 Wrong Type Error
 * 	  07 Index Error
 * 	  08 Equal Object Error
 * 	  09 Wrong Selection
 */

package org.zinemanager.logic.manager;

import java.util.ArrayList;

import org.zinemanager.logic.entities.IDElement;

public abstract class BasicManager {

//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 * @param <T>
	 * @param pIDElementList
	 * @return
	 */
	protected <T extends IDElement> ArrayList<Integer> createIDList(ArrayList<T> pIDElementList){
		ArrayList<Integer> vRet = new ArrayList<Integer>();
		
		if (pIDElementList != null) {
			for (IDElement vIDElement : pIDElementList) {
				vRet.add(vIDElement.getId());
			}
		}
		
		return vRet;
	}
	
}
