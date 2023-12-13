/**	ZineManager v0.1		Wf	12.12.2023
 * 	
 * 	gui.controller
 * 	  BasicController
 * 		ChildController
 * 		  PrinterController
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

import java.util.ArrayList;

import org.zinemanager.gui.stages.PrinterStage;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZinePrintingManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class PrinterController extends BasicController {
	private boolean isPrinting, repeatingCurrent;
	private int totalPrintingNumber, curPrintElementIndx, printStatus;
	private PrinterStage printerStage;
	
	private ZinePrintingManager zineprintingManager;
	
	@FXML
	private Label lTitle, lProcessName, lPrintStatus;
	@FXML
	private Button btPause, btRepeat, btBack;
	@FXML
	private ProgressBar pbPrintProgress;
	
	private ArrayList<Integer> liZinePrintingIDs;

	/**	Wf	12.12.2023
	 * 
	 */
	public PrinterController() {
		super();
		
		isPrinting 		 = false;
		repeatingCurrent = false;
		
		printStatus 		= 0;
		
		totalPrintingNumber = -1;
		curPrintElementIndx = -1;
		
		printerStage = null;
		
		liZinePrintingIDs = new ArrayList<Integer>();
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	12.12.2023
	 * 
	 * @param pTitle
	 * @param pZinePrintManager
	 * @param pPrinterStage
	 */
	public void setUp(String pTitle, ZinePrintingManager pZinePrintManager, PrinterStage pPrinterStage) {
		printerStage = pPrinterStage;
		zineprintingManager = pZinePrintManager;
		lTitle.setText(pTitle);
		
		totalPrintingNumber = zineprintingManager.getTotalZinePrintingNumber();
		liZinePrintingIDs = zineprintingManager.getPrintingElementIDs();
		
		zineprintingManager.initiatePrinterListener(() -> { finishedPrint();}, () -> {  LogManager.handleMessage("Attention, dieser Druck gibt Infos."); });
		
		printStatus = 1;
		
		setEnabled(true);
		startNextPrint();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	12.12.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledButtons(boolean pEnable) {
		btPause.setDisable( (printStatus == 1) ? !pEnable : true );
		btRepeat.setDisable( ((printStatus == 1) && (!repeatingCurrent)) ? !pEnable : true );
		btBack.setDisable( !pEnable );
	}
	/**	Wf	08.18.2023
	 * 
	 * @param pEnable
	 */
	protected void setEnabled(boolean pEnable) {
		setEnabledButtons(pEnable);
		
		pbPrintProgress.setDisable( !pEnable );
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	12.12.2023
	 * 
	 */
	private void updateTexts() {
		int vCurID;
		String vPrintStatusText = "Drucke: ";
		
		lProcessName.setText( (printStatus <= 0) ? "" : ( (printStatus == 1) ? "Drucke " + zineprintingManager.getTotalZinePrintingNumber() +" Dateien" : "Fertig" ) );
		try {
			vCurID = liZinePrintingIDs.get(curPrintElementIndx);
			vPrintStatusText += zineprintingManager.getPrintingElementName(vCurID);
			vPrintStatusText += "("+ zineprintingManager.getPrintingElementPrinting(vCurID) +")";
			
			lPrintStatus.setText( (printStatus == 1) ? vPrintStatusText : "" );
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	/**	Wf	12.12.2023
	 * 
	 */
	private void updateButtons() {
		btPause.setText( (isPrinting) ? "Pausieren" : "Fortsetzten" );
		btBack.setText( (printStatus == 2) ? "Fertig" : "Abbrechen" );
	}
	
	/**	Wf	12.12.2023
	 * 
	 */
	private void update() {
		updateTexts();
		updateButtons();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	12.12.2023
	 * 
	 */
	@FXML
	public void pause() {
		if (printStatus == 1) {
			if (!isPrinting && !zineprintingManager.isPrinting()) startNextPrint();
			
			isPrinting = !isPrinting;
			setEnabled(true);
			update();
		}
	}
	
	/**	Wf	12.12.2023
	 * 
	 */
	@FXML
	public void repeat() {		
		if (printStatus == 1) repeatingCurrent = !repeatingCurrent;
		
		setEnabled(true);
	}
	
	/**	Wf	12.12.2023
	 * 
	 */
	@FXML
	public void back() {	
		if (printStatus != 2) zineprintingManager.canclePrinting();
			
		printerStage.hide();
	}
	

//--------------------------------------------------------------------------------------------------------
	

	/**	Wf	12.12.2023
	 * 
	 */
	public void startNextPrint() {
		int vCurID;
		
		if (!repeatingCurrent) curPrintElementIndx ++;
		
		try {
			vCurID = liZinePrintingIDs.get(curPrintElementIndx);
			
			zineprintingManager.printElement(vCurID);
			isPrinting = true;
			repeatingCurrent = false;
			update();
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
	/**	Wf	12.12.2023
	 * 
	 */
	public void finishedPrint() {
		int vCurID = liZinePrintingIDs.get(curPrintElementIndx);
		double vProgressValue;
		
		try {
			if (!repeatingCurrent) {
				vProgressValue = ((double)zineprintingManager.getPrintingElementPrinting(vCurID))/((double)totalPrintingNumber);
				pbPrintProgress.setProgress(pbPrintProgress.getProgress() + vProgressValue);
			}
			
			if ((curPrintElementIndx < (liZinePrintingIDs.size()-1)) || (repeatingCurrent)) {
				if (isPrinting)	startNextPrint();
			}else{
				isPrinting = false;
				printStatus = 2;
				
				setEnabled(true);
				update();
			}
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
}
