/**	ZineManager v0.1		Wf	08.12.2023
 * 	
 * 	gui
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

package org.zinemanager.gui;

import java.util.ArrayList;

import javax.print.PrintService;

import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.logic.GUIInterfaces.PrinterSelectorInterface;

public class PrinterSelector extends Selector implements PrinterSelectorInterface {
	private PrintService[] printservices;
	
	/**	Wf	08.12.2023
	 * 
	 * @param pPrintServices
	 */
	public PrinterSelector() {
		super("Drucker Auswahl", new ArrayList<NameTableElement>());
	}
	
	/**	Wf	08.12.2023
	 * 
	 * @return
	 */
	//public static PrinterSelectorInterface getPrinterSelector(PrintService[] pPrintServices) {return new PrinterSelector();}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	08.12.2023
	 * 
	 * @param pPrintServices
	 */
	public void setPrintServiceSelection(PrintService[] pPrintServices) throws Exception{
		int vInd = 0;
		
		if (pPrintServices != null) {
			printservices = pPrintServices;
			
			for (PrintService vPrintservices : printservices) {
				super.selectionObjects.add(new NameTableElement(vInd, vPrintservices.getName()));
				vInd ++;
			}
		} else throw new Exception("04; sPSS,PrS");
		
	}
	
	/**	Wf	08.12.2023
	 * 
	 * @return
	 */
	public PrintService getPrintServiceSelection() {
		PrintService vRet = null;
		int vInd = super.getSelection();
		
		if ((0 <= vInd) && (vInd < printservices.length)) vRet = printservices[vInd];
		
		return vRet;
	}
	
}
