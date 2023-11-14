/**	ZineManager v0.0		Wf	13.11.2023
 * 	
 * 	gui.controller.zineinventory
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
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

package org.zinemanager.gui.controller.zineinventory;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.zinemanager.gui.DatasetPorter;
import org.zinemanager.gui.callables.GUIListElementFormater;
import org.zinemanager.gui.callables.GUITableElementSetter;
import org.zinemanager.gui.controller.BasicManagerController;
import org.zinemanager.gui.controller.ChildController;
import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.stages.ChildStage;
import org.zinemanager.gui.stages.zineinventory.MainZineInventoryStage;
import org.zinemanager.gui.stages.zineinventory.multieditor.MultiCategoryEditorStage;
import org.zinemanager.gui.stages.zineinventory.multieditor.MultiZineEditorStage;
import org.zinemanager.gui.stages.zineinventory.singleeditor.SingleZineEditorStage;
import org.zinemanager.gui.stages.zineinventory.singleeditor.SingleZineListEditorStage;
import org.zinemanager.gui.tableelements.ZineListTableElement;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZineManager;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class MainZineInventoryController<ParentController extends ParentControllerInterface> 
			 extends BasicManagerController<ZineManager, ParentController>
			 implements ParentControllerInterface{
	private MainZineInventoryStage<ParentController> stage;
	
	private HashMap<Integer, Tab> zineListTabs;
	private HashMap<Tab, Integer> tabsZineLists;
	private HashMap<Integer, ZineListTabController> zineListTabController;
	
	@FXML
	private MenuItem miNew, miSave, miLoad, miImport, miExport, miBack, miClose,
					 miNewZine, miEditZine, miRemoveZine, miZines,
					 miNewZinelist, miEditZinelist, miRemoveZinelist,
					 miOptions, miAbout;
	
	@FXML
	private Label lTitle, lVersion;
	@FXML
	private Button btNewZine, btEditZine, btRemoveZine, btNewZineList, btEditZineList, btRemoveZineList,
					btZines, btCategories, btBack, btZinesUpdate, btInputClean;
	@FXML
	private DatePicker dpSelectedDate;
	
	@FXML
	private TabPane tpZineLists;
		
	protected ChildStage<? extends ChildController<MainZineInventoryController<ParentController>>, MainZineInventoryController<ParentController>> childStage;
	
	protected DatasetPorter datasetPorter;
	
	private GUIListElementFormater<ZineListTableElement> pZineListTableElementGenerater;
	private GUITableElementSetter<ZineListTableElement> pZineListTableElementSetter;
	
	/**	Wf	12.11.2023
	 * 
	 */
	public MainZineInventoryController(){
		super();
		
		childStage = null;
		
		datasetPorter = null;
		
		zineListTabs 		  = new HashMap<Integer, Tab>();
		zineListTabController = new HashMap<Integer, ZineListTabController>();
		tabsZineLists         = new HashMap<Tab, Integer>();
		
		pZineListTableElementGenerater = (pID) -> {
			int vTotalDis, vTempID;
			String vCategoryName;
			Integer vLastCount, vCurrentCount;
			Date vLastDate = null;
			
			ArrayList<Integer> vZineCountIDs = basicManager.getZineCountIDs(pID);
			
			vTempID 	  = basicManager.getZineCategoryID(pID);
			vCategoryName = ( vTempID != -1 ? basicManager.getCategoryName(vTempID) : "" ); 
			
			
			for (Integer vZineCountID : vZineCountIDs) {
				if (vLastDate == null) {
					vLastDate = basicManager.getZineCountDate(pID, vZineCountID.intValue());
					vTempID   = vZineCountID.intValue();
				} else if (vLastDate.before(basicManager.getZineCountDate(pID, vZineCountID.intValue()))){
					vLastDate = basicManager.getZineCountDate(pID, vZineCountID.intValue());
					vTempID   = vZineCountID.intValue();
				}
			}
			vLastCount = (vLastDate != null ? basicManager.getZineCountValue(pID, vTempID) : null );
			vCurrentCount = null;
			
			vTotalDis = basicManager.calculateTotalDistributedValueOfZine(pID);
			
			return new ZineListTableElement(pID, basicManager.getZineName(pID), vCategoryName, basicManager.getZineQuota(pID), vLastCount, vCurrentCount, vTotalDis);
		};
		pZineListTableElementSetter = pTableElement -> {
			int vTempID, vElementID;
			Date vLastDate = null;
			
			vElementID = pTableElement.getId();
			
			ArrayList<Integer> vZineCountIDs = basicManager.getZineCountIDs(vElementID);
			
			pTableElement.setName(basicManager.getZineName(vElementID));
			
			vTempID 	  = basicManager.getZineCategoryID(vElementID);
			pTableElement.setCategoryName( vTempID != -1 ? basicManager.getCategoryName(vTempID) : "" ); 
			
			for (Integer vZineCountID : vZineCountIDs) {
				if (vLastDate == null) {
					vLastDate = basicManager.getZineCountDate(vElementID, vZineCountID.intValue());
					vTempID   = vZineCountID.intValue();
				} else if (vLastDate.before(basicManager.getZineCountDate(vElementID, vZineCountID.intValue()))){
					vLastDate = basicManager.getZineCountDate(vElementID, vZineCountID.intValue());
					vTempID   = vZineCountID.intValue();
				}
			}
			pTableElement.setLastCount(vLastDate != null ? basicManager.getZineCountValue(vElementID, vTempID) : null );
			
			pTableElement.setTotalDistributed(basicManager.calculateTotalDistributedValueOfZine(vElementID));
		};
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	11.11.2023
	 * 
	 */
	public void setUp(MainZineInventoryStage<ParentController> pStage) {
		stage = pStage;
		
		datasetPorter = new DatasetPorter(basicManager);
		
		miOptions.setDisable(true);
		miAbout.setDisable(true);
		
		lVersion.setText("v"+LogManager.getVersion());
		
		dpSelectedDate.setValue(LocalDate.now());
		
		tpZineLists.getSelectionModel().selectedItemProperty().addListener((pObs, pOldValue, pNewValue) -> {
			if (pOldValue != pNewValue) setEnabled();
		});
		
		try {
			createNewZineListTab(-1);
		
			updateAll();
		}catch(Exception ex) {LogManager.handleException(ex);}
		
		setEnabled();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	01.11.2023
	 * 
	 */
	public void closeChildStage() {
		if (childStage != null) childStage.closeStage();
		setEnabled();
		
		try {updateAll();}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
	//-----
	
	/**	Wf	13.11.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledFileMenuItems(boolean pEnable) {
		miNew.setDisable( !pEnable );
		
		miSave.setDisable( !pEnable );
		miLoad.setDisable( !pEnable );
		
		miImport.setDisable( !pEnable );
		miExport.setDisable( !pEnable );
		
		
		miBack.setDisable( !pEnable );
		miClose.setDisable( !pEnable );
	}
	/**	Wf	05.10.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledZineMenuItems(boolean pEnable) {
		ZineListTabController vController = zineListTabController.get( tabsZineLists.get( tpZineLists.getSelectionModel().getSelectedItem() ));
		
		miZines.setDisable( !pEnable );
		//-----
		miNewZine.setDisable( !pEnable );
		miEditZine.setDisable( ((vController != null) && (vController.getSelectionModel().getSelectedItem() != null)) ? !pEnable : true );
		miRemoveZine.setDisable( ((vController != null) && (vController.getSelectionModel().getSelectedItem() != null)) ? !pEnable : true );
	}
	/**	Wf	05.10.2023dv
	 * 
	 * @param pEnable
	 */
	private void setEnabledZinelistMenuItems(boolean pEnable) {
		miNewZinelist.setDisable( !pEnable );
		
		miEditZinelist.setDisable( (tpZineLists.getSelectionModel().getSelectedIndex() > 0) ? !pEnable : true );
		miRemoveZinelist.setDisable( (tpZineLists.getSelectionModel().getSelectedIndex() > 0) ? !pEnable : true );
	}
	
	private void setEnabledOtherMenuItems(boolean pEnable) {
		
	}
	//-----
	/**	Wf	03.10.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledZineButtons(boolean pEnable) {
		ZineListTabController vController = zineListTabController.get( tabsZineLists.get( tpZineLists.getSelectionModel().getSelectedItem() ));
		
		btNewZine.setDisable( !pEnable );
		btEditZine.setDisable( ((vController != null) && (vController.getSelectionModel().getSelectedItem() != null)) ? !pEnable : true );
		btRemoveZine.setDisable( ((vController != null) && (vController.getSelectionModel().getSelectedItem() != null)) ? !pEnable : true );
	}
	/**	Wf	03.10.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledZineListButtons(boolean pEnable) {
		btNewZineList.setDisable( !pEnable );
		
		btEditZineList.setDisable( (tpZineLists.getSelectionModel().getSelectedIndex() > 0) ? !pEnable : true );
		btRemoveZineList.setDisable( (tpZineLists.getSelectionModel().getSelectedIndex() > 0) ? !pEnable : true );
	}
	/**	Wf	05.10.2023
	 * 	
	 * @param pEnable
	 */
	private void setEnabledTableButtons(boolean pEnable) {
		btZinesUpdate.setDisable( !pEnable );
		btInputClean.setDisable( !pEnable );
	}
	/**	Wf	29.09.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledOtherButtons(boolean pEnable) {
		btZines.setDisable(!pEnable);
		btCategories.setDisable(!pEnable);
		btBack.setDisable(!pEnable);
	}
	
	/**	Wf	05.10.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledMenuItems(boolean pEnable) {
		setEnabledFileMenuItems(pEnable);
		setEnabledZineMenuItems(pEnable);
		setEnabledZinelistMenuItems(pEnable);
		setEnabledTableButtons(pEnable);
		setEnabledOtherMenuItems(pEnable);
	}
	/**	Wf	28.09.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledButtons(boolean pEnable) {
		setEnabledZineButtons(pEnable);
		setEnabledZineListButtons(pEnable);
		setEnabledOtherButtons(pEnable);
	}
	
	private void setEnabledTables(boolean pEnable) {
		
	}
	
	/**	Wf	28.09.2023
	 * 
	 * @param pEnable
	 */
	protected void setEnabled(boolean pEnable) {
		setEnabledMenuItems(pEnable);
		setEnabledButtons(pEnable);
		setEnabledTables(pEnable);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	13.11.2023
	 * 
	 */
	@FXML
	public void newDataset() {
		basicManager.createNewDataset();
		try{ updateAll(); }
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
	/**	Wf	14.11.2023
	 * 
	 */
	@FXML
	public void save() {
		try{
			if (!basicManager.hasCurrentDatasetPath()) {
				setDisabled();
				datasetPorter.saveDataset(stage);
			}else {
				basicManager.saveDataSet("");
				LogManager.handleMessage("DataSet gespeichert.");
			}
		} catch(Exception ex) {LogManager.handleException(ex);}
		
		setEnabled();
	}
	/**	Wf	12.11.2023
	 * 
	 */
	@FXML
	public void load() {
		try{
			setDisabled();
			
			datasetPorter.importDataset(stage, 1);
			updateAll();
		} catch(Exception ex) {LogManager.handleException(ex);}
		
		setEnabled();
	}
	
	/**	Wf	12.11.2023
	 * 
	 */
	@FXML
	public void importDataSet() {
		setDisabled();
		
		try { 
			datasetPorter.importDataset(stage);
			
			updateAll();
		} catch(Exception ex) {LogManager.handleException(ex);}
		
		setEnabled();
	}
	/**	Wf	12.11.2023
	 * 
	 */
	@FXML
	public void exportDataSet() {
		setDisabled();
		
		try { datasetPorter.exportDataset(stage, 1); }
		catch(Exception ex) {LogManager.handleException(ex);}
		
		setEnabled();
	}
	
	@FXML
	public void openOptions() {
		
	}
	
	@FXML
	public void about() {
		
	}
	
	//-----
	
	/**	Wf	03.10.2023
	 * 
	 */
	@FXML
	public void createZine() {
		childStage = new SingleZineEditorStage<MainZineInventoryController<ParentController>>(basicManager, this, -1);
		setDisabled();
	}
	/**	Wf	03.10.2023
	 * 
	 */
	@FXML
	public void editZine() {
		ZineListTabController vController = zineListTabController.get( tabsZineLists.get( tpZineLists.getSelectionModel().getSelectedItem() ));
		
		if ((vController != null) && (vController.getSelectionModel().getSelectedItem() != null)) {
			childStage = new SingleZineEditorStage<MainZineInventoryController<ParentController>>(basicManager, this, vController.getSelectionModel().getSelectedItem().getId());
			setDisabled();
		}else LogManager.handleMessage("Kein Zine ausgew�hlt!");
	}
	/**	Wf	03.10.2023
	 * 
	 */
	@FXML
	public void removeZine() {
		ZineListTabController vController = zineListTabController.get( tabsZineLists.get( tpZineLists.getSelectionModel().getSelectedItem() ));
		
		if ((vController != null) && (vController.getSelectionModel().getSelectedItem() != null)) {
			try {
				basicManager.removeZine( vController.getSelectionModel().getSelectedItem().getId() );
				updateAll();
				LogManager.handleMessage("Zine wurde erfolgreich entfernt.");
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Kein Zine ausgew�hlt!");
	}
	
	/**	Wf	03.10.2023
	 * 
	 */
	@FXML
	public void createZineList() {
		childStage = new SingleZineListEditorStage<MainZineInventoryController<ParentController>>(basicManager, this, -1);
		setDisabled();
	}
	/**	Wf	05.10.2023
	 * 
	 */
	@FXML
	public void editZineList() {
		ZineListTabController vController = zineListTabController.get( tabsZineLists.get( tpZineLists.getSelectionModel().getSelectedItem() ));
		
		if ((vController != null) && (vController.getZineListID() != -1)) {
			childStage = new SingleZineListEditorStage<MainZineInventoryController<ParentController>>(basicManager, this, vController.getZineListID());
			setDisabled();
		}else LogManager.handleMessage("Keine ZineListe ausgew�hlt!");
	}
	/**	Wf	05.10.2023
	 * 	
	 */
	@FXML
	public void removeZineList() {
		ZineListTabController vController = zineListTabController.get( tabsZineLists.get( tpZineLists.getSelectionModel().getSelectedItem() ));
		
		if ((vController != null) && (vController.getZineListID() != -1)) {
			try{
				basicManager.removeZineList(vController.getZineListID());
				unregistZineListTab(vController.getZineListID());
				updateAll();
				LogManager.handleMessage("Zineliste wurde erfolgreich entfernt.");
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Keine ZineListe ausgew�hlt!");
	}
	
	
	/**	Wf	29.09.2023
	 * 
	 */
	@FXML
	public void openZines() {
		childStage = new MultiZineEditorStage<MainZineInventoryController<ParentController>>(basicManager, this);
		setDisabled();
	}
	/**	Wf	29.09.2023
	 * 
	 */
	@FXML
	public void openCategories() {
		childStage = new MultiCategoryEditorStage<MainZineInventoryController<ParentController>>(basicManager, this);
		setDisabled();
	}
	
	/**	Wf	29.09.2023
	 * 
	 */
	@FXML
	public void back() {
		super.back();
	}
	/**	Wf	05.10.2023
	 * 
	 */
	@FXML
	public void close() {
		super.back();
		LogManager.closeApp();
	}
	
	//-----
	
	/**WF	07.10.2032
	 * 
	 */
	@FXML
	public void updateZines() {
		ZineListTabController vCurTabController = zineListTabController.get( tabsZineLists.get( tpZineLists.getSelectionModel().getSelectedItem() ) );
		
		if ((vCurTabController != null)  && (dpSelectedDate.getValue() != null)){
			
			for (ZineListTableElement vTableElement : vCurTabController.getZineListTable()) {
				try{ 
					if (vTableElement.getCurrentCount() != null) {
						basicManager.addCountToZine(vTableElement.getId(), vTableElement.getCurrentCount().intValue(),
													Date.from( dpSelectedDate.getValue().atStartOfDay().toInstant(ZoneOffset.UTC)));
						
						for (ZineListTabController vTabController : zineListTabController.values()) {
							if (vTabController != vCurTabController) {
								for (ZineListTableElement vTemp : vTabController.getZineListTable()) {
									if (vTableElement.getId() == vTemp.getId()) vTemp.setCurrentCount(null);
								}
							}
						}
						
						vTableElement.setCurrentCount(null);
					}
				} catch(Exception ex) {LogManager.handleException(ex);}
			}
		}
		
		try { updateAll(); }
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
	/**	Wf	20.10.2023
	 * 
	 */
	@FXML
	public void cleanInput() {
		for (ZineListTabController vTabController : zineListTabController.values()) {
			for (ZineListTableElement vTableElement : vTabController.getZineListTable()) {
				try{ vTableElement.setCurrentCount(null); }
				catch(Exception ex) {LogManager.handleException(ex);}
			}
			vTabController.update();
		}
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	12.11.2023
	 * 
	 * @throws Exception
	 */
	private void updateZineLists() throws Exception{
		int vTempID;
		ArrayList<Integer> vZineListIDs = basicManager.getZineListIDs();
		ArrayList<Integer> vRemovingZineListIDs = new ArrayList<Integer>();
		ZineListTabController vController;
		
		for (Integer vOldZineListID : zineListTabs.keySet()) {
			if ((!vZineListIDs.contains(vOldZineListID)) && (vOldZineListID.intValue() != -1)) vRemovingZineListIDs.add(vOldZineListID);
		}
		for (Integer vRemovingZineListID : vRemovingZineListIDs) {
			unregistZineListTab(vRemovingZineListID.intValue());
		}
		
		for (int i=-1; i < vZineListIDs.size(); i++) {
			vTempID = ( i != -1 ? vZineListIDs.get(i).intValue() : i );
			
			if ((zineListTabs.containsKey(vTempID)) || (vTempID == -1)) {
				if (vTempID != -1) zineListTabs.get(vTempID).setText(basicManager.getZineListName(vTempID));
				vController = zineListTabController.get(vTempID);
				
				if (vTempID != -1) updateZineTableList(vController.getZineListTable(), basicManager.getZineListZineIDs(vTempID), vController.getSelectionModel() );
				else 			   updateZineTableList(vController.getZineListTable(), basicManager.getZineIDs(), vController.getSelectionModel() );
				
				vController.update();
			}else createNewZineListTab(vTempID);
		}
	}
	
	/**	Wf	03.10.2023
	 * 	
	 * @throws Exception
	 */
	private void updateAll() throws Exception{
		updateZineLists();
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	03.10.2023
	 * 
	 * @param pZineListID
	 * @throws Exception
	 */
	private void createNewZineListTab(int pZineListID) throws Exception{
		Tab vNewTab = new Tab();
		String vZineListName;
		
		FXMLLoader vFXMLLoader;
		VBox vBox;
		ZineListTabController vTabController;
		
		vZineListName = (pZineListID != -1) ? basicManager.getZineListName(pZineListID) : "Alle Zines" ;
		vNewTab.setText(vZineListName);
		
		vFXMLLoader = new FXMLLoader(getClass().getResource("/org/zinemanager/gui/scenes/zineinventory/ZineListTabScene.fxml"));
		vBox = vFXMLLoader.load();
		vTabController = vFXMLLoader.getController();
		
		vTabController.setParentController(this);
		vTabController.setUp(pZineListID);
		vTabController.setZineListTitle(vZineListName);
		if (pZineListID != -1)	vTabController.setZineListTable( generateZineTableList(basicManager.getZineListZineIDs(pZineListID), null) );
		else					vTabController.setZineListTable( generateZineTableList(basicManager.getZineIDs(), null) );
		vNewTab.setContent(vBox);
		
		registZineListTab(pZineListID, vNewTab, vTabController);
	}
	
	/**	Wf 05.10.2023
	 * 
	 * @param pZineIDs
	 * @param pSelMod
	 * @return
	 * @throws Exception
	 */
	private ArrayList<ZineListTableElement> generateZineTableList(ArrayList<Integer> pZineIDs, SelectionModel<ZineListTableElement> pSelMod) throws Exception{
		return generateNewGUIList(pZineIDs, pZineListTableElementGenerater, null, pSelMod);
	}
	/**	Wf	05.10.2023
	 * 
	 * @param pUpdatedList
	 * @param pUpdatingIDs
	 * @param pSelMod
	 * @throws Exception
	 */
	private void updateZineTableList(ObservableList<ZineListTableElement> pUpdatedList, ArrayList<Integer> pUpdatingIDs, SelectionModel<ZineListTableElement> pSelMod)throws Exception{
		updateGUIList(pUpdatedList, pUpdatingIDs, pZineListTableElementSetter, pZineListTableElementGenerater, null, pSelMod);
	}
	
	//-----
	
	/**	Wf	03.10.2023
	 * 
	 * @param pZineListID
	 * @param pZineListTab
	 * @param pZineListTabController
	 */
	private void registZineListTab(int pZineListID, Tab pZineListTab, ZineListTabController pZineListTabController) {
		tpZineLists.getTabs().add(pZineListTab);
		
		zineListTabs.put(pZineListID, pZineListTab);
		zineListTabController.put(pZineListID, pZineListTabController);
		tabsZineLists.put(pZineListTab, pZineListID);
	}
	/**	Wf	03.10.2023
	 * 
	 * @param pZineListID
	 */
	private void unregistZineListTab(int pZineListID) {
		tpZineLists.getTabs().remove( zineListTabs.get(pZineListID) );
		
		tabsZineLists.remove( zineListTabs.get(pZineListID) );
		zineListTabController.remove(pZineListID);
		zineListTabs.remove(pZineListID);
	}
	
}
