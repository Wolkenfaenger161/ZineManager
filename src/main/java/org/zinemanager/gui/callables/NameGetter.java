/**	ZineManager v0.0		Wf	30.09.2023
 * 	
 * 	gui.callables
 * 	 NameGetter
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

public interface NameGetter {
	public String getName(int pID) throws Exception;
}
