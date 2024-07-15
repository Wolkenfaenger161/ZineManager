/**	ZineManager v0.2		Wf	15.07.2024
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
import org.zinemanager.logic.PrintSides;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZinePrintingManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class PrinterController extends BasicController {
	private boolean isPrinting, isDuplex, repeatingCurrent;
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

	/**	Wf	16.01.2024
	 * 
	 */
	public PrinterController() {
		super();
		
		isPrinting 		 = false;
		isDuplex		 = false;
		repeatingCurrent = false;
		
		printStatus 		= 0;
		
		totalPrintingNumber = -1;
		curPrintElementIndx = -1;
		
		printerStage = null;
		
		liZinePrintingIDs = new ArrayList<Integer>();
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	15.07.2024
	 * 
	 * @param pTitle
	 * @param pZinePrintManager
	 * @param pPrinterStage
	 */
	public void setUp(String pTitle, ZinePrintingManager pZinePrintManager, PrinterStage pPrinterStage) {
		printerStage = pPrinterStage;
		zineprintingManager = pZinePrintManager;
		lTitle.setText(pTitle);
		
		totalPrintingNumber = zineprintingManager.getTotalCoverPrintingNumber() + zineprintingManager.getTotalZinePrintingNumber();
		liZinePrintingIDs = zineprintingManager.getPrintingElementIDs();
		
		zineprintingManager.initiatePrinterListener(() -> { finishedPrint();}, () -> {  LogManager.handleMessage("Attention, dieser Druck gibt Infos."); });
		
		if (zineprintingManager.getTotalCoverPrintingNumber() > 0) printStatus = 1;
		else 													   printStatus = 3;
		isDuplex = zineprintingManager.isDuplex();
		
		if (isDuplex) totalPrintingNumber = 2 * totalPrintingNumber;
		
		setEnabled(true);
		
		if (printStatus == 1) {
			isPrinting = false;
			
			LogManager.handleMessage("Bitte prüfen, ob genügend Coverpapier eingelegt ist.\nEs werden "+ zineprintingManager.getTotalCoverPrintingNumber() + " Covers gedruckt.");
		}else startNextPrint();
		
		update();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	16.01.2024
	 * 
	 * @param pEnable
	 */
	private void setEnabledButtons(boolean pEnable) {
		btPause.setDisable( ((1 <= printStatus) && (printStatus <= 4)) ? !pEnable : true );
		btRepeat.setDisable( (((1 <= printStatus) && (printStatus <= 4)) && (!repeatingCurrent)) ? !pEnable : true );
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
	
	/**	Wf	16.01.2024
	 * 
	 */
	private void updateTexts() {
		int vCurID;
		String vPrintStatusText = ((printStatus == 1) || (printStatus == 2)) ? "Drucke Cover: " : "Drucke: ";
		String vPrintStatusTitle = ((printStatus == 1) || (printStatus == 2)) ? "Drucke Cover " + zineprintingManager.getTotalCoverPrintingNumber() + " Dateien" 
																				: "Drucke " + zineprintingManager.getTotalZinePrintingNumber() + " Dateien";
		
		lProcessName.setText( (printStatus <= 0) ? "" : ( ((1 <= printStatus) && (printStatus <= 4)) ? vPrintStatusTitle : "Fertig" ) );
		try {
			vCurID = liZinePrintingIDs.get(curPrintElementIndx);
			vPrintStatusText += zineprintingManager.getPrintingElementName(vCurID);
			vPrintStatusText += "("+ zineprintingManager.getPrintingElementPrinting(vCurID) +")";
			
			if (!isDuplex) {
				if      ((printStatus == 1) || (printStatus == 3)) vPrintStatusText += " ungerade Seiten";
				else if ((printStatus == 2) || (printStatus == 4)) vPrintStatusText += " gerade Seiten";
			}
			
			lPrintStatus.setText( ((1 <= printStatus) && (printStatus <= 4)) ? vPrintStatusText : "" );
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	/**	Wf	16.01.2024
	 * 
	 */
	private void updateButtons() {
		btPause.setText( (isPrinting) ? "Pausieren" : "Fortsetzten" );
		btBack.setText( (printStatus == 5) ? "Fertig" : "Abbrechen" );
	}
	
	/**	Wf	12.12.2023
	 * 
	 */
	private void update() {
		updateTexts();
		updateButtons();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	16.01.2024
	 * 
	 */
	@FXML
	public void pause() {
		if ((1 <= printStatus) && (printStatus <= 4)) {
			if (!isPrinting && !zineprintingManager.isPrinting()) startNextPrint();
			
			isPrinting = !isPrinting;
			setEnabled(true);
			update();
		}
	}
	
	/**	Wf	16.01.2024
	 * 
	 */
	@FXML
	public void repeat() {		
		if ((1 <= printStatus) && (printStatus <= 4)) repeatingCurrent = !repeatingCurrent;
		
		setEnabled(true);
	}
	
	/**	Wf	16.01.2024
	 * 
	 */
	@FXML
	public void back() {	
		if (printStatus != 5) zineprintingManager.canclePrinting();
			
		printerStage.hide();
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	18.01.2024
	 * 
	 */
	public void startNextPrint() {
		int vCurID;
		
		try {
			if (!repeatingCurrent) {
				curPrintElementIndx ++;
				
				while( (((1 == printStatus) || (2 == printStatus)) && (!zineprintingManager.hasPrintingElementExtracoverPrint(liZinePrintingIDs.get(curPrintElementIndx))
						|| !zineprintingManager.isPrintingElementPrintingCover(liZinePrintingIDs.get(curPrintElementIndx))))
						&& (curPrintElementIndx < liZinePrintingIDs.size()))
					curPrintElementIndx ++;
			}
		
			vCurID = liZinePrintingIDs.get(curPrintElementIndx);
			
			if ((((3 == printStatus) || (4 == printStatus)) || (zineprintingManager.hasPrintingElementExtracoverPrint(vCurID) && zineprintingManager.isPrintingElementPrintingCover(vCurID)))) {
				if      (printStatus == 1) zineprintingManager.printElement(vCurID, (isDuplex) ? PrintSides.DUPLEX : PrintSides.ODD_PAGES, true );
				else if (printStatus == 2) zineprintingManager.printElement(vCurID, PrintSides.EVEN_PAGES, true );
				else if (printStatus == 3) zineprintingManager.printElement(vCurID, (isDuplex) ? PrintSides.DUPLEX : PrintSides.ODD_PAGES, false );
				else if (printStatus == 4) zineprintingManager.printElement(vCurID, PrintSides.EVEN_PAGES, false );
			}else {
				if ((printStatus == 1) || (printStatus == 2) || ((printStatus == 3) && (!isDuplex))) {
					curPrintElementIndx = -1;
					
					if (!isDuplex) printStatus ++;
					else printStatus = 3;
					
					if ((printStatus == 2) || (printStatus == 4)) LogManager.handleMessage("Bitte gedrucktes Papier umdrehen.\nZweite Seiten soll gedruckt werden.");
					else if (printStatus == 3) LogManager.handleMessage("Bitte normales Papier einlegen.\nEs werden "+ zineprintingManager.getTotalZinePrintingNumber() +" Dateien gedruckt." );
				}else printStatus = 5;
			}
				
			isPrinting = true;
			repeatingCurrent = false;
			update();
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
	/**	Wf	18.01.2024
	 * 
	 */
	public void finishedPrint() {
		int vCurID = liZinePrintingIDs.get(curPrintElementIndx);
		double vProgressValue;
		
		try {
			if (!repeatingCurrent) {
				vProgressValue = ((double)zineprintingManager.getPrintingElementPrinting(vCurID))/((double)totalPrintingNumber);
				pbPrintProgress.setProgress(pbPrintProgress.getProgress() + vProgressValue);
			}else if (((printStatus == 3) && (!isDuplex)) || (printStatus == 4)) zineprintingManager.completedPrintingZine(vCurID);
			
			if ((curPrintElementIndx < (liZinePrintingIDs.size()-1)) || (repeatingCurrent)) {
				if (isPrinting)	startNextPrint();
			}else{
				isPrinting = false;
				
				if ((printStatus == 1) || (printStatus == 2) || ((printStatus == 3) && (!isDuplex))) {
					curPrintElementIndx = -1;
					
					if (!isDuplex) printStatus ++;
					else printStatus = 3;
					
					if ((printStatus == 2) || (printStatus == 4)) LogManager.handleMessage("Bitte gedrucktes Papier umdrehen.\nZweite Seite soll gedruckt werden.");
					else if (printStatus == 3) LogManager.handleMessage("Bitte normales Papier einlegen.\nEs werden "+ zineprintingManager.getTotalZinePrintingNumber() +" Dateien gedruckt." );
				}else printStatus = 5;
				
				setEnabled(true);
				update();
			}
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
}
