/**	ZineManager v0.0		Wf	13.11.2023
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  BasicParentEditorController
 * 			   SingleZineEditorController
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

package org.zinemanager.gui.controller.zineinventory.singleeditor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.stages.zineinventory.singleeditor.SingleZinePathEditorStage;
import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.gui.tableelements.ZineCountTableElement;
import org.zinemanager.logic.manager.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class SingleZineEditorController<ParentController extends ParentControllerInterface>
			extends SingleZinePathEditorController<ParentController>{

	@FXML
	private Button btNewCount, btRemoveCount;
	
	@FXML
	private Spinner<Integer> spQuota, spDistributedOffset;
	@FXML
	private ChoiceBox<NameTableElement> cbCategory;
	@FXML
	private TableView<ZineCountTableElement> tvCounts;
	@FXML
	private TableColumn<ZineCountTableElement, Integer> tcCount;
	@FXML
	private TableColumn<ZineCountTableElement, Date> tcDate;
	
	private ObservableList<NameTableElement> liCategories;
	private ObservableList<ZineCountTableElement> liCounts;
	
	
	/**	Wf	30.09.2023
	 * 
	 */
	public SingleZineEditorController() {
		super();
		
		stage = null;
		
		liCategories = null;
		liCounts     = null;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	13.11.2023
	 * 
	 * @param pStage
	 * @throws Exception
	 */
	public void setUp(SingleZinePathEditorStage<? extends SingleZinePathEditorController<ParentController>, ParentController> pStage) throws Exception{
		int vTempID;
		
		stage = pStage;
		
		liCategories = FXCollections.observableArrayList();
		liCounts     = FXCollections.observableArrayList();
		
		spQuota.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
		spDistributedOffset.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
		
		cbCategory.setItems(liCategories);
		
		tcCount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tcDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter(new SimpleDateFormat("dd.MM.yyyy"))));
		//-----
		tcCount.setCellValueFactory(new PropertyValueFactory<ZineCountTableElement, Integer>("count"));
		tcDate.setCellValueFactory(new PropertyValueFactory<ZineCountTableElement, Date>("date"));
		//-----
		tcCount.setOnEditCommit(pEvent -> {
			int vValue = (pEvent.getNewValue() != null) && (pEvent.getNewValue().intValue() >= 0) ? pEvent.getNewValue().intValue() : pEvent.getOldValue().intValue();
			
			try { pEvent.getTableView().getItems().get(pEvent.getTablePosition().getRow()).setCount(vValue); }
			catch(Exception ex) {LogManager.handleException(ex);}
			
			pEvent.getTableView().refresh();
		});
		tcDate.setOnEditCommit(pEvent -> {
			Date vValue = (pEvent.getNewValue() != null) ? pEvent.getNewValue() : pEvent.getOldValue();
			
			try { pEvent.getTableView().getItems().get(pEvent.getTablePosition().getRow()).setDate(vValue); }
			catch(Exception ex) {LogManager.handleException(ex);}
			
			pEvent.getTableView().refresh();
		});
		
		tvCounts.setItems(liCounts);
		tvCounts.getSelectionModel().selectedItemProperty().addListener((pObs, pOldValue, pNewValue) -> {
			if (pNewValue != pOldValue) setEnabledCountButtons(true);
		});
		
		liCategories.setAll( generateNewNameTableElementList(basicManager.getCategoryIDs(), true, pID -> {return basicManager.getCategoryName(pID);}, null) );
		
		if (editObjectID != -1) {
			btProgress.setText("Ändern");

			tfName.setText(basicManager.getZineName(editObjectID));
			tfPath.setText(basicManager.getZineFilePath(editObjectID));
			
			spQuota.getValueFactory().setValue(basicManager.getZineQuota(editObjectID));
			spDistributedOffset.getValueFactory().setValue(basicManager.getZineDistributedOffset(editObjectID));
			
			vTempID = basicManager.getZineCategoryID(editObjectID);
			for (int i=0; (i<liCategories.size()) && (i!=-2); i++) {
				if (liCategories.get(i).getId() == vTempID) {
					cbCategory.getSelectionModel().select(i);
					
					vTempID = -2;
				}
			}
			
			liCounts.setAll(generateNewGUIList(basicManager.getZineCountIDs(editObjectID), 
					pID -> {return new ZineCountTableElement(pID, basicManager.getZineCountValue(editObjectID, pID),
															 basicManager.getZineCountDate(editObjectID, pID));	}, null, null));
			liCounts.sort((pObject1, pObject2) -> {
					int vRet;
					Date vDate1, vDate2;
					
					vDate1 = pObject1.getDate();
					vDate2 = pObject2.getDate();
					
					if ((vDate1 != null) && (vDate2 != null)) vRet = vDate1.compareTo(vDate2);
					else if ((vDate1 != null) && (vDate2 == null)) vRet = Integer.MAX_VALUE;
					else if ((vDate1 == null) && (vDate2 != null)) vRet = Integer.MIN_VALUE;
					else vRet = 0;
						
					return vRet;});
		}
			
		setEnabledObjectInformations(true);
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	01.10.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledCountButtons(boolean pEnable) {
		btNewCount.setDisable( !pEnable );
		
		btRemoveCount.setDisable( (tvCounts.getSelectionModel().getSelectedItem() != null) ? !pEnable : true);
	}
	
	/**	Wf	20.10.2023
	 * 
	 */
	protected void setEnabledObjectInformations(boolean pEnable) {
		super.setEnabledObjectInformations(pEnable);
		
		cbCategory.setDisable( !pEnable );
		
		spQuota.setDisable( !pEnable );
		spDistributedOffset.setDisable( !pEnable );
		
		tvCounts.setDisable( !pEnable );
		setEnabledCountButtons(pEnable);
	}
	
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	01.10.2023
	 * 
	 */
	public void createCount() {
		liCounts.add(new ZineCountTableElement(calculateNewID(liCounts), 0, Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)) ));
	}
	
	/**	Wf	01.10.2023
	 * 
	 */
	public void removeCount() {
		ZineCountTableElement vCurSel = tvCounts.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) liCounts.remove(vCurSel);
		else LogManager.handleMessage("Kein Eintrag entfernt!\nEs wurde keiner ausgewählt.");
	}
	
	//---------------------------------------------------------------------------------------------------
	
	/**	Wf	01.10.2023
	 * 	
	 */
	@FXML
	public void progress() {
		boolean vIDStillExists;
		NameTableElement vCurSelCat;
		String vName, vFilePath;
		int vCategoryID, vQuota, vDistributedOffset, vCurCountID, vCurID;
		
		ArrayList<Integer> vZineCountIDs;
		
		if (isInputValid()) {
			try {
				vName = tfName.getText();
				vFilePath = tfPath.getText();
				
				vCurSelCat = cbCategory.getSelectionModel().getSelectedItem();
				vCategoryID = ((vCurSelCat != null) ? vCurSelCat.getId() : -1);
				
				vQuota = spQuota.getValue().intValue();
				vDistributedOffset = spDistributedOffset.getValue().intValue();
				
				if (editObjectID == -1) {
					vCurID = basicManager.addZine(vName, vQuota, vDistributedOffset, vCategoryID, vFilePath);
					
					for (ZineCountTableElement vZineCount : liCounts) {
						basicManager.addCountToZine(vCurID, vZineCount.getCount(), vZineCount.getDate());
					}
				}else {
					basicManager.setZineName(editObjectID, vName);
					basicManager.setZineFilePath(editObjectID, vFilePath);
					
					basicManager.setZineCategoryID(editObjectID, vCategoryID);
					
					basicManager.setZineQuota(editObjectID, vQuota);
					basicManager.setZineDistributedOffset(editObjectID, vDistributedOffset);
										
					vZineCountIDs = basicManager.getZineCountIDs(editObjectID);
					for (Integer vOldID : vZineCountIDs) {
						vIDStillExists = false;
						
						for (ZineCountTableElement vZineCount : liCounts) {
							if (vZineCount.getId() == vOldID.intValue()) vIDStillExists = true;
						}
						
						if (!vIDStillExists) basicManager.removeCountFromZine(editObjectID, vOldID.intValue());
					}
					
					for (ZineCountTableElement vZineCount : liCounts) {
						vCurCountID = vZineCount.getId();
						
						if (basicManager.hasZineCount(editObjectID, vCurCountID)) {
							basicManager.setZineCountValue(editObjectID, vCurCountID, vZineCount.getCount());
							basicManager.setZineCountDate(editObjectID, vCurCountID, vZineCount.getDate());
						}else basicManager.addCountToZine(editObjectID, vZineCount.getCount(), vZineCount.getDate());
					}
				}
				
				parentController.closeChildStage();
			} catch(Exception ex) {LogManager.handleException(ex);}
		} else LogManager.handleMessage("Eingaben nicht gültig!");
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	12.10.2023
	 * 
	 */
	protected boolean isInputValid() {
		boolean vRet = false;
		String vName, vFilePath;
		
		vName = tfName.getText();
		vFilePath = tfPath.getText();
		
		if (!(vName.equals("")) && (basicManager.isFilePathValid(vFilePath))){
			vRet = true;
			
			for (Integer vZineID : basicManager.getZineIDs()) {
				try {
					if ( basicManager.getZineName(vZineID).equals(vName) && (vZineID != editObjectID)) {
						vRet = false;
						
						LogManager.handleMessage("Zine Name schon vorhanden.");
					}
					if ( basicManager.getZineFilePath(vZineID).equals(vFilePath) && (vZineID != editObjectID) && (!vFilePath.equals(""))) {
						vRet = false;
						
						LogManager.handleMessage("Ein anderes Zine nutzt schon diesen Dateipfad.");
					}
				}catch (Exception ex) {LogManager.handleException(ex);}
			}
		}
		
		return vRet;
	}
	
}
