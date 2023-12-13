/**	ZineManager v0.0		Wf	14.11.2023
 * 	
 * 	gui
 * 	  DatasetPorter
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.zinemanager.gui.controller.BasicController;
import org.zinemanager.gui.stages.BasicStage;
import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.logic.exceptions.InvalidZinePathFileException;
import org.zinemanager.logic.manager.DataSetManager;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZineManager;

import javafx.stage.FileChooser;

public class DatasetPorter {
	private ZineManager zinemanager;
	
	/**	Wf	11.11.2023
	 * 
	 * @param pDatasetManager
	 */
	public DatasetPorter(ZineManager pZineManager) {
		super();
		
		zinemanager = pZineManager;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	11.11.2023
	 * 
	 * @param pStage
	 */
	public void loadDataset(BasicStage<? extends BasicController> pStage) throws Exception {
		Selector vSel = new Selector("Auswahl Dataset", new ArrayList<>(Arrays.asList(new NameTableElement(0, "Neues"), new NameTableElement(1, "Laden"), new NameTableElement(2, "Importieren"))));
			
		importDataset(pStage, vSel.getSelection());
	}
	/**	Wf	11.11.2023
	 * 
	 * @param pStage
	 */
	public void importDataset(BasicStage<? extends BasicController> pStage) throws Exception {
		Selector vSel = new Selector("Import Auswahl", new ArrayList<>(Arrays.asList(new NameTableElement(2, "Als neu importieren"), new NameTableElement(3, "Aktuelles ersetzten"))));
			
		importDataset(pStage, vSel.getSelection());
	}
	/**	Wf	13.11.2023
	 * 
	 * @param pStage
	 * @param pImportArt
	 */
	public void importDataset(BasicStage<? extends BasicController> pStage, int pImportArt) throws Exception {
		File vFile;
		FileChooser vFileChooser = new FileChooser();
		
		if (pImportArt == 0) zinemanager.createNewDataset();
		else if ((1 <= pImportArt) && (pImportArt <= 3)) {
			vFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			vFileChooser.setTitle("Wähle Dataset Datei");
			
			vFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xml", "*.xml"));
			
			vFile = vFileChooser.showOpenDialog(pStage);
			
			if (vFile != null) {
				if (pImportArt == 1) {
					try {zinemanager.loadDataSet(vFile.getAbsolutePath());}
					catch(InvalidZinePathFileException izpfex) {
						InvalidZinePathCorrector vCor = new InvalidZinePathCorrector(zinemanager, izpfex.getInvalidFilePathZineIDs());
							
						vCor.correctZinePaths();
					}catch(Exception ex) { throw ex; }
						
					LogManager.handleMessage("Dataset " + zinemanager.getDataSetManager().getDataSetName() +  " geladen.");
				}else if (pImportArt == 2) {
					try {
						zinemanager.importDataSet(vFile.getAbsolutePath(), true);
					}catch(InvalidZinePathFileException izpfex) {
						InvalidZinePathCorrector vCor = new InvalidZinePathCorrector(zinemanager, izpfex.getInvalidFilePathZineIDs());
							
						vCor.correctZinePaths();
					}catch(Exception ex) { throw ex; }
						
					LogManager.handleMessage("Neues DataSet - "+ zinemanager.getDataSetManager().getDataSetName() +" - importiert und geladen.");
				}else {
					try {
						zinemanager.importDataSet(vFile.getAbsolutePath(), false);
					}catch(InvalidZinePathFileException izpfex) {
						InvalidZinePathCorrector vCor = new InvalidZinePathCorrector(zinemanager, izpfex.getInvalidFilePathZineIDs());
							
						vCor.correctZinePaths();
					}catch(Exception ex) { throw ex; }
					
					LogManager.handleMessage("DataSet ersetzt und geladen.");
				}
			}
		}
	}
	
	/**	Wf	14.11.2023
	 * 
	 * @param pStage
	 * @throws Exception
	 */
	public void saveDataset(BasicStage<? extends BasicController> pStage) throws Exception{
		Selector vSel = new Selector("Speicherart Auswahl", new ArrayList<>(Arrays.asList(new NameTableElement(0, "Lokal"), new NameTableElement(1, "Extern"))));
		
		exportDataset(pStage, vSel.getSelection());
	}
	/**	Wf	11.11.2023
	 * 
	 * @param pStage
	 */
	public void exportDataset(BasicStage<? extends BasicController> pStage) throws Exception {
		Selector vSel = new Selector("Export Auswahl", new ArrayList<>(Arrays.asList(new NameTableElement(1, "Speichern"), new NameTableElement(2, "Exportieren"))));
		
		importDataset(pStage, vSel.getSelection());
	}
	/**	Wf	14.11.2023
	 * 
	 * @param pStage
	 * @param pExportArt
	 */
	public void exportDataset(BasicStage<? extends BasicController> pStage, int pExportArt) throws Exception {
		File vFile = null;
		FileChooser vFileChooser = new FileChooser();
		DataSetManager vDataSetManager = zinemanager.getDataSetManager();
		
		if (pExportArt == 0) {
			zinemanager.saveDataSet(vDataSetManager.getCurrentDirectoryFilePath());
			
			LogManager.handleMessage("Dataset gespeichert");
		} else if ((1 <= pExportArt) && (pExportArt <= 2)) {
			vFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			vFileChooser.setTitle("Wähle Dataset Datei");
			
			vFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xml", "*.xml"));
			
			vFile = vFileChooser.showSaveDialog(pStage);
			
			if (vFile != null) {
				if (!vFile.getAbsolutePath().contains(".xml")) vFile = new File(vFile.getAbsolutePath()+"/"+vDataSetManager.getDataSetName());
				
				if (pExportArt == 1) {
					zinemanager.saveDataSet(vFile.getAbsolutePath());
						
					LogManager.handleMessage("Dataset gespeichert");
				}else {
					zinemanager.exportDataSet(vFile.getAbsolutePath());
						
					LogManager.handleMessage("Dataset exportiert.");
				}
			}
		}
		
	}
	
//--------------------------------------------------------------------------------------------------------
	
}
