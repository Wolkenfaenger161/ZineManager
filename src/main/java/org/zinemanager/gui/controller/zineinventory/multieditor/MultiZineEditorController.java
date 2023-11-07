/**	ZineManager v0.0		Wf	05.10.2023
 * 	
 * 	gui.controller.zineinventory.multieditor
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			BasicSingleEditorController
 * 			  BasicParentEditorController
 * 			  	BasicMultiEditorController
 * 				  MultiZineEditorController
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

package org.zinemanager.gui.controller.zineinventory.multieditor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.stages.zineinventory.singleeditor.SingleZineEditorStage;
import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.gui.tableelements.ZineCountTableElement;
import org.zinemanager.logic.manager.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MultiZineEditorController<ParentController extends ParentControllerInterface>
				extends BasicMultiEditorController<ParentController> {
	
	@FXML
	private TextField tfName, tfPath, tfCategory, tfQuota, tfDistributedOffset;
	
	@FXML
	private TableView<ZineCountTableElement> tvCounts;
	@FXML
	private TableColumn<ZineCountTableElement, Integer> tcCount;
	@FXML
	private TableColumn<ZineCountTableElement, Date> tcDate;
	
	private ObservableList<ZineCountTableElement> liCounts;
	
	/**	Wf	03.10.2023
	 * 
	 */
	public MultiZineEditorController() {
		super();
		
		liCounts     = null;
	}
	
	//------------------------------------------------------------------------------------------------
	
	/**	Wf	03.10.2023
	 * 
	 * @param pStage
	 * @throws Exception
	 */
	public void setUp() throws Exception{
		super.setUp();
		liCounts     = FXCollections.observableArrayList();
		
		tcCount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tcDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter(new SimpleDateFormat("dd.MM.yyyy"))));
		//-----
		tcCount.setCellValueFactory(new PropertyValueFactory<ZineCountTableElement, Integer>("count"));
		tcDate.setCellValueFactory(new PropertyValueFactory<ZineCountTableElement, Date>("date"));
		
		tvCounts.setItems(liCounts);
		
		setEnabled();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	03.10.2023
	 * 
	 */
	protected void setEnabledObjectInformations(boolean pEnable) {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		tfName.setDisable( ((vCurSel == null) ? true : !pEnable) );
		tfPath.setDisable( ((vCurSel == null) ? true : !pEnable) );
		
		tfCategory.setDisable( ((vCurSel == null) ? true : !pEnable) );
		tfQuota.setDisable( ((vCurSel == null) ? true : !pEnable) );
		tfDistributedOffset.setDisable( ((vCurSel == null) ? true : !pEnable) );
		
		tvCounts.setDisable( ((vCurSel == null) ? true : !pEnable) );
	}
	
	//------------------------------------------------------------------------------------------------
	
	/**	Wf	05.10.2023
	 * 
	 */
	protected void updateObjectInformation() {
		int vTempID;
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			editObjectID = vCurSel.getId();
			
			try {
				tfName.setText(basicManager.getZineName(editObjectID));
				tfPath.setText(basicManager.getZineFilePath(editObjectID));
			
				vTempID = basicManager.getZineCategoryID(editObjectID);
				tfCategory.setText( ( vTempID != -1 ? basicManager.getCategoryName(vTempID) : "" ) );
				
				tfQuota.setText( ""+basicManager.getZineQuota(editObjectID) );
				tfDistributedOffset.setText( ""+basicManager.getZineDistributedOffset(editObjectID) );
				
				liCounts.setAll( generateNewGUIList(basicManager.getZineCountIDs(editObjectID), 
						pID -> {return new ZineCountTableElement(pID, basicManager.getZineCountValue(editObjectID, pID),
								 basicManager.getZineCountDate(editObjectID, pID));	}, null, null) );
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
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else {
			editObjectID = -1;
			
			tfName.setText("");
			tfPath.setText("");
			
			tfCategory.setText( "" );
			
			tfQuota.setText( "" );
			tfDistributedOffset.setText( "" );
			
			liCounts.setAll(new ArrayList<ZineCountTableElement>());
		}
	}
		
	/**	Wf	05.10.2023
	 * 
	 */
	protected void updateList() {
		try {updateNameTableElementList(liObjects, basicManager.getZineIDs(), false, pID -> {return basicManager.getZineName(pID);}, lvObjects.getSelectionModel());}
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	03.10.2023
	 * 
	 */
	@FXML
	public void progress() {
		childStage = new SingleZineEditorStage<MultiZineEditorController<ParentController>>(basicManager, this, -1);
		setDisabled();
	}
	/**	Wf	03.10.2023
	 * 
	 */
	public void editObject() {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			childStage = new SingleZineEditorStage<MultiZineEditorController<ParentController>>(basicManager, this, vCurSel.getId());
			setDisabled();
		}else LogManager.handleMessage("Kein Zine ausgewählt.");
	}
	
	/**	Wf	29.09.2023
	 * 
	 */
	@FXML
	protected void removeObject() {
		NameTableElement vCurSel = lvObjects.getSelectionModel().getSelectedItem();
		
		if (vCurSel != null) {
			try {
				liObjects.remove(vCurSel);
				lvObjects.getSelectionModel().clearSelection();
				
				basicManager.removeZine( vCurSel.getId() );
			}catch(Exception ex) {LogManager.handleException(ex);}
		}else LogManager.handleMessage("Kein Zine entfernt!\nEs ist kein Zine ausgewählt.");
	}
	
//--------------------------------------------------------------------------------------------------------
	
	
}
