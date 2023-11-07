/**	ZineManager v0.0		Wf	27.09.2023
 * 	
 * 	gui.controller
 * 	  ParentControllerInterface
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

public interface ParentControllerInterface {
	public abstract void closeChildStage();
	
	public abstract void setDisabled();
	public abstract void setEnabled();
}
