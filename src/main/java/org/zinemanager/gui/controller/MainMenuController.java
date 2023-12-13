/**	ZineManager v0.1		Wf	20.11.2023
 * 	
 * 	gui.controller
 * 	  BasicController		ParentControllerInterface
 * 		MainMenuController
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

import java.io.FileNotFoundException;

import org.zinemanager.gui.DatasetPorter;
import org.zinemanager.gui.stages.ChildStage;
import org.zinemanager.gui.stages.MainMenuStage;
import org.zinemanager.gui.stages.zineinventory.MainZineInventoryStage;
import org.zinemanager.gui.stages.zineprinting.ZineListSelectionStage;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZineManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class MainMenuController extends BasicController implements ParentControllerInterface {
	@FXML
	private Label lTitle, lVersion;
	@FXML
	private Button btInventory, btPrint, btOptions, btBack;
	@FXML
	private MenuItem miBack, miPrinter, miInventory, miDataSet, miOptions, miAbout;
	
	protected MainMenuStage stage;
	protected ChildStage<? extends ChildController<MainMenuController>, MainMenuController> childStage;
	
	protected ZineManager zineManager;
	protected DatasetPorter datasetporter;
	
	/**	Wf	11.11.2023
	 * 
	 */
	public MainMenuController() {
		super();
		
		stage = null;
		childStage = null;
		
		zineManager = null;
		datasetporter = null;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	20.11.2023
	 * 
	 * @param pZineManager
	 */
	public void setUp(ZineManager pZineManager, MainMenuStage pStage) {
		stage = pStage;
		zineManager = pZineManager;
		
		datasetporter = new DatasetPorter(pZineManager);
		
		lVersion.setText("v"+LogManager.getVersion());
		
		//btPrint.setDisable(true);
		btOptions.setDisable(true);
		//-----
		//miPrinter.setDisable(true);
		miOptions.setDisable(true);
		miAbout.setDisable(true);
		
		while(!zineManager.isDataSetLoaded()) {
			try {
				datasetporter.loadDataset(stage);
			}catch(FileNotFoundException fnfe) {}
			catch(Exception ex) {LogManager.handleException(ex);} 
		}		
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	01.11.2023
	 * 
	 */
	public void closeChildStage() {
		if (childStage != null) childStage.closeStage();
		setEnabled();
	}
	
	/**	Wf	27.09.2023
	 * 
	 */
	public void setDisabled() {
		setEnabled(false);
	}
	/**	Wf	27.09.2023
	 * 
	 */
	public void setEnabled() {
		setEnabled(true);
	}
	
	//-----
	
	/**	Wf	20.11.2023
	 * 
	 * @param pEnable
	 */
	private void setUpMenuItems(boolean pEnable) {		
		miInventory.setDisable(!pEnable);
		miPrinter.setDisable(!pEnable);
		miDataSet.setDisable(!pEnable);
		
		//miOptions.setDisable(!pEnable);
		//miAbout.setDisable(!pEnable);
		
		miBack.setDisable(!pEnable);
	}
	/**	Wf	20.11.2023
	 * 
	 * @param pEnable
	 */
	private void setUpButtons(boolean pEnable) {
		btInventory.setDisable(!pEnable);
		btPrint.setDisable(!pEnable);
		
		//btOptions.setDisable(!pEnable);
		
		btBack.setDisable(!pEnable);
	}
	
	/**	Wf	28.09.2023
	 * 
	 * @param pEnable
	 */
	protected void setEnabled(boolean pEnable) {
		setUpMenuItems(pEnable);
		setUpButtons(pEnable);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	28.09.2023
	 * 
	 */
	@FXML
	public void openZineInventory() {
		childStage = new MainZineInventoryStage<MainMenuController>(zineManager, this);
		setDisabled();
	}
	
	/**	Wf	20.11.2023
	 * 
	 */
	@FXML
	public void openZinePrinter() {
		childStage = new ZineListSelectionStage<MainMenuController>(zineManager, this);
		setDisabled();
	}
	
	/**	Wf	12.11.2023
	 * 
	 */
	@FXML
	public void openDataSetSelector() {
		setDisabled();
		try {
			datasetporter.loadDataset(stage);
		}catch(Exception ex) {LogManager.handleException(ex);}
		
		setEnabled();
	}
	
	//-----
	
	@FXML
	public void openOptions() {
		
	}
	
	@FXML
	public void openAbout() {
		
	}
	
	//-----
	
	/**	Wf	28.09.2023
	 * 
	 */
	@FXML
	public void back() {
		LogManager.closeApp();
	}
	
}
