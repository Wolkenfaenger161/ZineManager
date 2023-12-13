/**	ZineManager v0.1		Wf	08.12.2023
 * 	
 * 	gui.controller
 * 	  BasicController
 * 		ChildController
 * 		  BasicManagerController
 * 			ZineNumberSelectionController
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

package org.zinemanager.gui.controller.zineprinting;

import org.zinemanager.gui.Printer;
import org.zinemanager.gui.PrinterSelector;
import org.zinemanager.gui.callables.GUIListElementFormater;
import org.zinemanager.gui.callables.GUITableElementSetter;
import org.zinemanager.gui.controller.BasicManagerController;
import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.tableelements.ZinePrintNumberTableElement;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.ZinePrintingManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.converter.IntegerStringConverter;

public class ZineNumberSelectionController<ParentController extends ParentControllerInterface>
											extends BasicManagerController<ZinePrintingManager, ParentController> {

	@FXML
	private Button btPrint, btBack;
	
	@FXML
	private TableView<ZinePrintNumberTableElement> tvZines;
	@FXML
	private TableColumn<ZinePrintNumberTableElement, String> tcName;
	@FXML
	private TableColumn<ZinePrintNumberTableElement, Integer> tcQuota, tcCurrent, tcPrinting;
	
	private ObservableList<ZinePrintNumberTableElement> liZines;
	
	private GUIListElementFormater<ZinePrintNumberTableElement> pZinePrintNumberTableElementGenerater;
	private GUITableElementSetter<ZinePrintNumberTableElement> pZinePrintNumberTableElementSetter;
	
	/**	Wf	03.12.2023
	 * 
	 */
	public ZineNumberSelectionController() {
		super();
		
		liZines = null;
		pZinePrintNumberTableElementGenerater = (pID) -> {
			return new ZinePrintNumberTableElement(pID, basicManager.getPrintingElementName(pID), basicManager.getPrintingElementQuota(pID),
													basicManager.getPrintingElementCurrent(pID), basicManager.getPrintingElementPrinting(pID));
		};
		pZinePrintNumberTableElementSetter = pTableElement -> {
			int vElementID;
			
			vElementID = pTableElement.getId();
			
			pTableElement.setName(basicManager.getPrintingElementName(vElementID));
			
			pTableElement.setQuota(basicManager.getPrintingElementQuota(vElementID));
			pTableElement.setCurrent(basicManager.getPrintingElementCurrent(vElementID));
			pTableElement.setPrinting(basicManager.getPrintingElementPrinting(vElementID));
		};
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	03.12.2023
	 * 
	 * @throws Exception
	 */
	public void setUp() throws Exception{
		liZines = FXCollections.observableArrayList();
		
		tcName.setCellValueFactory(new PropertyValueFactory<ZinePrintNumberTableElement, String>("name"));
		tcQuota.setCellValueFactory(new PropertyValueFactory<ZinePrintNumberTableElement, Integer>("quota"));
		tcCurrent.setCellValueFactory(new PropertyValueFactory<ZinePrintNumberTableElement, Integer>("current"));
		tcPrinting.setCellValueFactory(new PropertyValueFactory<ZinePrintNumberTableElement, Integer>("printing"));
		
		tcPrinting.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tcPrinting.setOnEditCommit(pEvent -> {
			int vSelInd = pEvent.getTablePosition().getRow();
			int vValue = (pEvent.getNewValue() != null) && (pEvent.getNewValue().intValue() >= 0) ? pEvent.getNewValue().intValue() : pEvent.getOldValue().intValue();
			TableView<ZinePrintNumberTableElement> vTableView = pEvent.getTableView();
			
			try { vTableView.getItems().get(pEvent.getTablePosition().getRow()).setPrinting(vValue); }
			catch(Exception ex) {LogManager.handleException(ex);}
			
			vTableView.refresh();
			vTableView.getSelectionModel().select((liZines.size() > vSelInd + 1) ? vSelInd + 1 : vSelInd);
			vTableView.requestFocus();
		});
		
		tcName.setSortable(true);
		
		tvZines.setOnKeyPressed(pKeyEvent -> {
			int pInd;
			
			if ((pKeyEvent.getCode() == KeyCode.ENTER) && (tvZines.getSelectionModel().getSelectedItem() != null)) {
				pInd = tvZines.getSelectionModel().getSelectedIndex();
				
				if (tvZines.getEditingCell() == null) {
					tvZines.edit(pInd, tcPrinting);
					pKeyEvent.consume();
				}				
			}
		} );
		tvZines.setItems(liZines);
		
		updateTable();
		setEnabled();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	03.12.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledButton(boolean pEnable) {
		btPrint.setDisable( !pEnable );
		btBack.setDisable( !pEnable );
	}
	/**	Wf	03.12.2023
	 * 
	 * @param pEnable
	 */
	private void setEnabledTableElements(boolean pEnable) {
		tvZines.setDisable( !pEnable );
	}
	
	/**	Wf	03.12.2023
	 * 
	 */
	protected void setEnabled(boolean pEnable) {
		setEnabledButton(pEnable);
		setEnabledTableElements(pEnable);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	03.12.2023
	 * 
	 */
	private void updateTable() {
		try { updateGUIList(liZines, basicManager.getPrintingElementIDs(), pZinePrintNumberTableElementSetter, pZinePrintNumberTableElementGenerater, null, tvZines.getSelectionModel()); }
		catch(Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	08.12.2023
	 * 
	 */
	@FXML
	public void print() {
		Printer vPrinter;
		
		try {
			for (ZinePrintNumberTableElement vPrintTElement : liZines) {
				if (vPrintTElement.getPrinting() >= 0) basicManager.setPrintingElementPrinting(vPrintTElement.getId(), 
																							   vPrintTElement.getPrinting());
			}
			
			vPrinter = new Printer(basicManager);
			
			setDisabled();
			vPrinter.startPrinting();
			setEnabled();
			//basicManager.printZines(new PrinterSelector());
		}catch(Exception ex) {LogManager.handleException(ex);}
	}
	
	/**	Wf	03.12.2023
	 * 
	 */
	@FXML
	public void back() {
		basicManager.clearPrintingElements();
		super.back();
	}
	
}
