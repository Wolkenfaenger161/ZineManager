/**	ZineManager v0.21	Wf	07.01.2025
 * 
 * 	logic.manager
 * 	  BasicManager
 * 		ZinePrintingManager
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

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PageRanges;
import javax.print.attribute.standard.PrinterIsAcceptingJobs;
import javax.print.attribute.standard.QueuedJobCount;
import javax.print.event.PrintServiceAttributeEvent;
import javax.print.event.PrintServiceAttributeListener;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.zinemanager.gui.callables.printing.JobCanceledCallable;
import org.zinemanager.gui.callables.printing.JobCompletedCallable;
import org.zinemanager.logic.PrintSides;
import org.zinemanager.logic.GUIInterfaces.PrinterSelectorInterface;
import org.zinemanager.logic.entities.DatedCount;
import org.zinemanager.logic.entities.PrintingElement;
import org.zinemanager.logic.entities.ZineElement;

import javafx.application.Platform;

public class ZinePrintingManager extends BasicManager {
	private boolean isDuplex;
	private AtomicBoolean isPrinting;
	
	private SettingManager settingManager;
	
	private PrintService printservices;
	private PrinterJob printJob;
	private PrintRequestAttributeSet basicPrintAttributes;
	
	private ArrayList<PrintingElement> printingElements;
	
	/**	Wf	07.01.2025
	 * 
	 * @param pSettingManager
	 */
	public ZinePrintingManager(SettingManager pSettingManager) {
		isPrinting = new AtomicBoolean(false);
		isDuplex = false;
		
		settingManager = pSettingManager;
		LogManager.createLogEntry("Init Printer Stuff");
		try {
			printJob		 	 = PrinterJob.getPrinterJob();
		}catch(Exception ex) {LogManager.handleException(ex);}
		basicPrintAttributes = new HashPrintRequestAttributeSet();

		printingElements = new ArrayList<PrintingElement>();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	03.12.2023
	 * 
	 * @return
	 */
	public ArrayList<Integer> getPrintingElementIDs(){
		return createIDList(printingElements);
	}
	
	//-----
	
	/**	Wf	17.01.2024
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public boolean hasPrintingElementExtracoverPrint(int pID) throws Exception {
		return getPrintingElement(pID).hasExtracoverPrint();
	}
	/**	Wf	17.01.2024
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public boolean isPrintingElementPrintingCover(int pID) throws Exception{
		return getPrintingElement(pID).isPrintingCover();
	}
	/**	Wf	18.01.2024
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public boolean hasPrintingElementFinishedPrinting(int pID) throws Exception{
		return getPrintingElement(pID).hasFinishedPrinting();
	}
	
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public int getPrintingElementQuota(int pID) throws Exception{
		return getPrintingElement(pID).getQuota();
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public int getPrintingElementCurrent(int pID) throws Exception{
		return getPrintingElement(pID).getCurrent();
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public int getPrintingElementPrinting(int pID) throws Exception{
		return getPrintingElement(pID).getPrinting();
	}
	
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public String getPrintingElementName(int pID) throws Exception{
		return getPrintingElement(pID).getName();
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public String getPrintingElementFilePath(int pID) throws Exception{
		return getPrintingElement(pID).getFilePath();
	}
	
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	private PrintingElement getPrintingElement(int pID) throws Exception{
		PrintingElement vRet = null;
		
		if (pID >= 0) {
			for (PrintingElement vTemp : printingElements) {
				if (vTemp.getId() == pID) vRet = vTemp;
			}
			
			if (vRet == null) throw new Exception("04; gPE,ZPM");
		}else throw new Exception("02; gPE,ZPM");
		
		return vRet;
	}
	
	//----------------------------------------------------------------------------------------------------

	/**	Wf	17.01.2024
	 * 
	 * @param pID
	 * @param pHasExtrtacoverPrint
	 * @throws Exception
	 */
	public void setPrintingElementExtracoverPrint(int pID, boolean pHasExtrtacoverPrint) throws Exception{
		getPrintingElement(pID).setExtracoverPrint(pHasExtrtacoverPrint);
	}
	/**	Wf	17.01.2024
	 * 
	 * @param pID
	 * @param pIsPrintingCover
	 * @throws Exception
	 */
	public void setPrintingElementPrintingCover(int pID, boolean pIsPrintingCover) throws Exception{
		getPrintingElement(pID).setPrintingCover(pIsPrintingCover);
	}
	/**	Wf	17.01.2024
	 * 
	 * @param pID
	 * @param pHasFinishedPrinting
	 * @throws Exception
	 */
	public void setPrintingElementFinishedPrinting(int pID, boolean pHasFinishedPrinting) throws Exception{
		getPrintingElement(pID).setFinishedPrinting(pHasFinishedPrinting);
	}
	
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @param pQuota
	 * @throws Exception
	 */
	public void setPrintingElementQuota(int pID, int pQuota) throws Exception{
		getPrintingElement(pID).setQuota(pQuota);
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @param pCurrent
	 * @throws Exception
	 */
	public void setPrintingElementCurrent(int pID, int pCurrent) throws Exception{
		getPrintingElement(pID).setCurrent(pCurrent);
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @param pPrinting
	 * @throws Exception
	 */
	public void setPrintingElementPrinting(int pID, int pPrinting) throws Exception{
		getPrintingElement(pID).setPrinting(pPrinting);
	}
	
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @param pName
	 * @throws Exception
	 */
	public void setPrintingElementName(int pID, String pName) throws Exception{
		getPrintingElement(pID).setName(pName);
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @param pFilePath
	 * @throws Exception
	 */
	public void setPrintingElementFilePath(int pID, String pFilePath) throws Exception{
		getPrintingElement(pID).setFilePath(pFilePath);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 * @return
	 */
	public boolean hasPrintingElement(int pID) {
		boolean vRet = false;
		
		for (PrintingElement vTemp : printingElements) {
			if (!vRet && (vTemp.getId() == pID)) vRet = true;
		}
		
		return vRet;
	}
	
	/**	Wf	26.01.2024
	 * 
	 * @param pZineElement
	 * @throws Exception
	 */
	public void addPrintingElement(ZineElement pZineElement) throws Exception{
		PrintingElement vNewPrintingElement;
		int vCurNum = 0, vPrintNum;
		Date vCurLastDate = null;
		
		if (pZineElement != null) {
			if (!hasPrintingElement(pZineElement.getId())) {
				for (DatedCount vTemp : pZineElement.getCounts()) {
					if ((vCurLastDate == null) || (vCurLastDate.before(vTemp.getDate()))) {
						vCurLastDate = vTemp.getDate();
						vCurNum      = vTemp.getCount();
					}
				}
				
				vPrintNum = Math.max(pZineElement.getQuota() - vCurNum, 0);
				
				vNewPrintingElement = new PrintingElement(pZineElement.getId(), pZineElement.getName(), pZineElement.getFilePath(), 
														  pZineElement.getDoublesidePrintart(), pZineElement.getQuota(), vCurNum, vPrintNum);
				
				printingElements.add(vNewPrintingElement);
			} else throw new Exception ("02; aPE,ZPM");
		}else throw new Exception("04; aPE,ZPM");
	}
		
	/**	Wf	03.12.2023
	 * 
	 * @param pID
	 */
	public void removePrintingElement(int pID) {
		PrintingElement vTemp = null;
		try { vTemp = getPrintingElement(pID); } 
		catch(Exception ex) {;}
		
		if (vTemp != null) printingElements.remove(vTemp);
	}
	
	/**	Wf	03.12.2023
	 * 
	 */
	public void clearPrintingElements() {
		printingElements = new ArrayList<PrintingElement>();
	}
		
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	08.12.2023
	 * 
	 * @return
	 */
	public int getTotalZinePrintingNumber() {
		int vRet = 0;
		
		for (PrintingElement vPrintingElement : printingElements) {
			vRet += vPrintingElement.getPrinting();
		}
		
		return vRet;
	}
	/**	Wf	17.01.2024
	 * 
	 * @return
	 */
	public int getTotalCoverPrintingNumber() {
		int vRet = 0;
		
		for (PrintingElement vPrintingElement : printingElements) {
			if (vPrintingElement.hasExtracoverPrint() && vPrintingElement.isPrintingCover()) vRet += vPrintingElement.getPrinting();
		}
		
		return vRet;
	}
	
	/**	Wf	12.12.2023
	 * 
	 * @return
	 */
	public boolean isPrinting() {
		return isPrinting.get();
	}
	/**	Wf	16.01.2024
	 * 
	 * @return
	 */
	public boolean isDuplex() {
		return isDuplex;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	18.01.2024
	 * 
	 * @param <PS>
	 * @param pPrintselector
	 * @throws Exception
	 */
	public<PS extends PrinterSelectorInterface> boolean settingUpPrinting(PS pPrintselector) throws Exception {
		pPrintselector.setPrintServiceSelection( PrintServiceLookup.lookupPrintServices(null, basicPrintAttributes) );
		
		printservices = pPrintselector.getPrintServiceSelection();
		printJob.setPrintService(printservices);
		
		isDuplex = printservices.isAttributeValueSupported(settingManager.getDoublesidePrintart(), null, null);
		if (isDuplex) basicPrintAttributes.add(settingManager.getDoublesidePrintart());
		
		return printservices != null;
	}
	
	/**	Wf	07.01.2025
	 * 
	 * @param pJobAttentionCallable
	 * @param pJobFailedCallable
	 * @param pJobCompletedCallable
	 * @param pJobCanceledCallable
	 */
	public void initiatePrinterListener(JobCompletedCallable pJobCompletedCallable, JobCanceledCallable pJobFailedCallable) {
		printservices.addPrintServiceAttributeListener(new PrintServiceAttributeListener() {
			private int lastqueuedjobcount = 0;
			
			/**	Wf	07.01.2025
			 * 
			 */
			@Override
			public void attributeUpdate(PrintServiceAttributeEvent pPrintServiceAttributeEvent) {
				Platform.runLater(() -> {
					int vCurQueuedJobCount ;
					
					PrinterIsAcceptingJobs vIsAcceptingJobs;
					PrintServiceAttributeSet vPrintServieAttributes = pPrintServiceAttributeEvent.getAttributes();				
					
					if (vPrintServieAttributes.containsKey(QueuedJobCount.class)) {
						vCurQueuedJobCount = ((QueuedJobCount)vPrintServieAttributes.get(QueuedJobCount.class)).getValue();
						
						if ((vCurQueuedJobCount < lastqueuedjobcount) || (vCurQueuedJobCount == 0 && isPrinting.get())) {
							if (vPrintServieAttributes.containsKey(PrinterIsAcceptingJobs.class)) {
								vIsAcceptingJobs = (PrinterIsAcceptingJobs)vPrintServieAttributes.get(PrinterIsAcceptingJobs.class);
								
								if (vIsAcceptingJobs == PrinterIsAcceptingJobs.ACCEPTING_JOBS) pJobCompletedCallable.completedJob();
								else pJobFailedCallable.canceledJob();
							}else pJobCompletedCallable.completedJob();
							
							isPrinting.set(false);
						}else if (vCurQueuedJobCount > 0) isPrinting.set(true);
						
						lastqueuedjobcount = vCurQueuedJobCount;
					}else LogManager.handleMessage("Printer doesn't support queuedjobcount");
				});
			}
		});
	}
	
	/**	Wf	07.01.2025
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void printElement(int pID, PrintSides pPrintSide, boolean pCoverPrint) throws Exception {
		int[][] vSingleSidePrintPages;
		PrintingElement vPrintingElement;
		
		Paper vPaperA4 = new Paper();
		Paper vPaperA5 = new Paper();
		
		vPaperA4.setSize(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());
		vPaperA5.setSize(PDRectangle.A5.getWidth(), PDRectangle.A5.getHeight());
		
		PDDocument vDocument;
		Book vBook;
		PageFormat vPageFormat;
		PrintRequestAttributeSet vPrintAttributes = new HashPrintRequestAttributeSet();
		vPrintAttributes.addAll(basicPrintAttributes);
		
		vPrintingElement = getPrintingElement(pID);
		if (vPrintingElement != null) {
			if (!vPrintingElement.getFilePath().equals("") && vPrintingElement.getPrinting() > 0 
					&& (vPrintingElement.hasExtracoverPrint() || (!pCoverPrint))) {
				vDocument = Loader.loadPDF(new File(vPrintingElement.getFilePath()));
				vPageFormat = printJob.defaultPage();
				
				vPrintAttributes.add(new Copies(vPrintingElement.getPrinting()));
				
				if ((vPrintingElement.getDoublesidePrintart() != null) && (vPrintingElement.getDoublesidePrintart() != settingManager.getDoublesidePrintart())) {
					vPrintAttributes.remove(settingManager.getDoublesidePrintart());
					vPrintAttributes.add( vPrintingElement.getDoublesidePrintart() );
				}
				
				if (isRetanclesGreater(vDocument.getPage(0).getMediaBox(), PDRectangle.A5)) {
					if (isLandscape(vDocument.getPage(0).getMediaBox())) {
						vPrintAttributes.add(OrientationRequested.LANDSCAPE);
						vPageFormat.setOrientation(PageFormat.LANDSCAPE);
					}
					//vDocument.getPage(0).setMediaBox(PDRectangle.A4);
					//vPageFormat.setPaper(vPaperA4);
				}else {
					if (!isLandscape(vDocument.getPage(0).getMediaBox())) {
						vPrintAttributes.add(OrientationRequested.LANDSCAPE);
						vPageFormat.setOrientation(PageFormat.LANDSCAPE);
					}
					//vDocument.getPage(0).setMediaBox(PDRectangle.A5);
					//vPageFormat.setPaper(vPaperA5);
				}
				
				if (vPrintingElement.hasExtracoverPrint()) {
					if (pCoverPrint) {
						if      (pPrintSide == PrintSides.DUPLEX) 	  vPrintAttributes.add(new PageRanges(1, 2));
						else if (pPrintSide == PrintSides.ODD_PAGES)  vPrintAttributes.add(new PageRanges(1, 1));
						else if (pPrintSide == PrintSides.EVEN_PAGES) vPrintAttributes.add(new PageRanges(2, 2));
					}else {
						if      (pPrintSide == PrintSides.DUPLEX) 	  vPrintAttributes.add(new PageRanges(3, vDocument.getNumberOfPages()));
						else if (pPrintSide == PrintSides.ODD_PAGES) {
							vSingleSidePrintPages = new int[((int)Math.round(vDocument.getNumberOfPages()/2)) - 1][2];
						
							for (int i=0; i<vSingleSidePrintPages.length; i++) {
								vSingleSidePrintPages[i][0] = vSingleSidePrintPages[i][1] = (2*i)+3;
							}
						
							vPrintAttributes.add(new PageRanges(vSingleSidePrintPages));
						}else if (pPrintSide == PrintSides.EVEN_PAGES) {
							vSingleSidePrintPages = new int[((int)Math.round((vDocument.getNumberOfPages()/2)-0.5)) - 1][2];
						
							for (int i=0; i<vSingleSidePrintPages.length; i++) {
								vSingleSidePrintPages[i][0] = vSingleSidePrintPages[i][1] = (2*i)+4;
							}
						
							vPrintAttributes.add(new PageRanges(vSingleSidePrintPages));
						}
					}
				}else {
					if      (pPrintSide == PrintSides.DUPLEX) 	  vPrintAttributes.add(new PageRanges(1, vDocument.getNumberOfPages()));
					else if (pPrintSide == PrintSides.ODD_PAGES) {
						vSingleSidePrintPages = new int[((int)Math.round(vDocument.getNumberOfPages())/2)][2];
						
						for (int i=0; i<vSingleSidePrintPages.length; i++) {
							vSingleSidePrintPages[i][0] = vSingleSidePrintPages[i][1] = (2*i)+1;
						}
						
						vPrintAttributes.add(new PageRanges(vSingleSidePrintPages));
					}else if (pPrintSide == PrintSides.EVEN_PAGES) {
						vSingleSidePrintPages = new int[(int)Math.round((vDocument.getNumberOfPages()/2) - 0.5)][2];
						
						for (int i=0; i<vSingleSidePrintPages.length; i++) {
							vSingleSidePrintPages[i][0] = vSingleSidePrintPages[i][1] = (2*i)+2;
						}
						
						vPrintAttributes.add(new PageRanges(vSingleSidePrintPages));
					}
				}
				
				vBook = new Book();
				
				vBook.append(new PDFPrintable(vDocument, Scaling.SHRINK_TO_FIT, false, 0, false), vPageFormat, vDocument.getNumberOfPages());
				
				printJob.setPageable(vBook);
				printJob.setJobName(""+vPrintingElement.getName());
				
				startPrinting(vPrintAttributes);
			}
		}else throw new Exception("04; prE, ZPM");
	}
	
	/**	Wf	12.12.2023
	 * 
	 */
	public void canclePrinting() {
		printJob.cancel();
		isPrinting.set(false);
	}
	
	/**	Wf 16.01.2024
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void completedPrintingZine(int pID) throws Exception{
		PrintingElement vPrintingElement;
		
		vPrintingElement = getPrintingElement(pID);
		if (vPrintingElement != null) {
			vPrintingElement.setFinishedPrinting(true);
		}else throw new Exception("04; prE, ZPM");
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	12.12.2023
	 * 
	 * @param pRec1
	 * @param pRec2
	 * @return
	 */
	private boolean isRetanclesGreater(PDRectangle pRec1, PDRectangle pRec2) {
		boolean vRet = false;
		
		if (((int)pRec1.getHeight() > (int)pRec2.getHeight()) && ((int)pRec1.getWidth() > (int)pRec2.getWidth()))
			vRet = true;
		else if (((int)pRec1.getHeight() < (int)pRec2.getHeight()) && ((int)pRec1.getWidth() < (int)pRec2.getWidth()))
			vRet = false;
		else vRet = (int)(pRec1.getHeight()*pRec1.getWidth()) > (int)(pRec2.getHeight()*pRec2.getWidth());
		
		return vRet;
	}
	
	/**	Wf	06.01.2025
	 * 
	 * @param pRec
	 * @return
	 */
	private boolean isLandscape(PDRectangle pRec) {
		boolean vRet = false;
		
		if ( (int)pRec.getHeight() < (int)pRec.getWidth() ) vRet = true;
		
		return vRet;
	}
	
	/**	Wf	07.01.2025
	 * 
	 * @param pPrintAttributes
	 */
	private void startPrinting(PrintRequestAttributeSet pPrintAttributes){
		Platform.runLater(() -> {
			try {
				if (!isPrinting.get()) {
					printJob.print(pPrintAttributes);
					isPrinting.set(true);
				}else startPrinting(pPrintAttributes);
			}catch(Exception ex)  {LogManager.handleException(ex);}
		});
	}
	
	
}
