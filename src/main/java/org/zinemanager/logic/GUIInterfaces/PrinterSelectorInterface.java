/**	ZineManager v0.1		Wf	08.12.2023
 * 	
 * 	logic.GUIInterfaces
 * 	  Selector
 * 		PrinterSelector
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

package org.zinemanager.logic.GUIInterfaces;

import javax.print.PrintService;

public interface PrinterSelectorInterface {

	//public static PrinterSelectorInterface getPrinterSelector(PrintService[] pPrintServices) {return null;}
	
	public void setPrintServiceSelection(PrintService[] pPrintServices) throws Exception;
	public PrintService getPrintServiceSelection();
}
