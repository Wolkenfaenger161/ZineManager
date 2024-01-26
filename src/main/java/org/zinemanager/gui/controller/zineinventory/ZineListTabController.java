/**	ZineManager v0.2		Wf	26.01.2024
 * 	
 * 	gui.controller
 * 	  BasicController
 * 		ZineListTabController
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

import java.util.ArrayList;
import java.util.function.Predicate;

import org.zinemanager.gui.controller.ChildController;
import org.zinemanager.gui.controller.ParentControllerInterface;
import org.zinemanager.gui.tableelements.NameTableElement;
import org.zinemanager.gui.tableelements.ZineListTableElement;
import org.zinemanager.logic.manager.LogManager;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

public class ZineListTabController extends ChildController<MainZineInventoryController<? extends ParentControllerInterface>>
{
	private int zineListID;
	
	@FXML
	private Label lTabTitle, lZineListCount;
	
	@FXML
	private TextField tfSearchField;
	@FXML
	private Button btSearch;
	@FXML
	private ChoiceBox<NameTableElement> cbSearchCategory;
	
	@FXML
	private Separator seSeparator;
	
	@FXML
	private TableView<ZineListTableElement> tvZineList;
	@FXML
	private TableColumn<ZineListTableElement, String> tcZines, tcCategories;
	@FXML
	private TableColumn<ZineListTableElement, Integer> tcQuotas, tcLasts, tcCurrents, tcTotals;
	
	private ObservableList<ZineListTableElement> liZineList;
	private FilteredList<ZineListTableElement> liFilteredZineList;
	private ObservableList<NameTableElement> liSearchCategoryList;
	
	/**	Wf	20.01.2024
	 * 
	 */
	public ZineListTabController() {
		zineListID = -1;
		
		liZineList 			 = null;
		liFilteredZineList   = null;
		liSearchCategoryList = FXCollections.observableArrayList();
		
		liSearchCategoryList.add(new NameTableElement(0, ""));
		liSearchCategoryList.add(new NameTableElement(1, "Zine Name"));
		liSearchCategoryList.add(new NameTableElement(2, "Zine Kategorie"));
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	20.01.2024
	 * 
	 * @param pZineListID
	 */
	public void setUp(int pZineListID) {
		zineListID = pZineListID;
		
		liZineList = FXCollections.observableArrayList();
		liFilteredZineList = new FilteredList<ZineListTableElement>(liZineList);
		cbSearchCategory.setItems(liSearchCategoryList);
		
		cbSearchCategory.getSelectionModel().select(0);
		
		tfSearchField.setOnKeyPressed(pEvent -> {
			if (pEvent.getCode() == KeyCode.ENTER) search();
		} );
		cbSearchCategory.getSelectionModel().selectedItemProperty().addListener((pObservable, pOldValue, pNewValue) -> {
			if (pOldValue != pNewValue) search();
		});
		
		tcZines.setCellValueFactory(new PropertyValueFactory<ZineListTableElement, String>("name"));
		tcCategories.setCellValueFactory(new PropertyValueFactory<ZineListTableElement, String>("categoryName"));
		tcQuotas.setCellValueFactory(new PropertyValueFactory<ZineListTableElement, Integer>("quota"));
		tcLasts.setCellValueFactory(new PropertyValueFactory<ZineListTableElement, Integer>("lastCount"));
		tcCurrents.setCellValueFactory(new PropertyValueFactory<ZineListTableElement, Integer>("currentCount"));
		tcTotals.setCellValueFactory(new PropertyValueFactory<ZineListTableElement, Integer>("totalDistributed"));
		
		tcCurrents.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		tcCurrents.setOnEditCommit(pEvent -> {
			int vSelInd = pEvent.getTablePosition().getRow();
			int vValue = (pEvent.getNewValue() != null) && (pEvent.getNewValue().intValue() >= 0) ? pEvent.getNewValue().intValue() : pEvent.getOldValue().intValue();
			TableView<ZineListTableElement> vTableView = pEvent.getTableView();
			
			try { vTableView.getItems().get(pEvent.getTablePosition().getRow()).setCurrentCount(vValue); }
			catch(Exception ex) {LogManager.handleException(ex);}
			
			vTableView.refresh();
			vTableView.getSelectionModel().select((liZineList.size() > vSelInd + 1) ? vSelInd + 1 : vSelInd);
			vTableView.requestFocus();
		});
		
		tcZines.setSortable(true);
		tcCategories.setSortable(true);
		
		tvZineList.getSelectionModel().selectedItemProperty().addListener((pObs, pOldValue, pNewValue) -> {
			if (pNewValue != pOldValue) parentController.setEnabled();
		});
		tvZineList.setOnMouseClicked(pEvent -> {
			if ((pEvent.getButton() == MouseButton.PRIMARY) && (pEvent.getClickCount() == 2) && (tvZineList.getSelectionModel().getSelectedItem() != null)) {
				parentController.editZine();
				
				pEvent.consume();
			}
		});
		tvZineList.setOnKeyPressed(pKeyEvent -> {
			int pInd;
			
			if ((pKeyEvent.getCode() == KeyCode.ENTER) && (tvZineList.getSelectionModel().getSelectedItem() != null)) {
				pInd = tvZineList.getSelectionModel().getSelectedIndex();
				
				if (tvZineList.getEditingCell() == null) {
					tvZineList.edit(pInd, tcCurrents);
					pKeyEvent.consume();
				}				
			}
		} );
		
		//tvZineList.setItems(liZineList);
		tvZineList.setItems(liFilteredZineList);
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	02.10.2023
	 * 
	 * @param pVisibility
	 */
	public void setCategoriesColumnVisibility(boolean pVisibility) {
		tcCategories.setVisible(pVisibility);
	}
	
	/**	Wf	02.10.2023
	 * 
	 * @param pVisibility
	 */
	public void setCountColumnsVisibility(boolean pVisibility) {
		setQuotaColumnVisibility(pVisibility);
		setLastsColumnVisibility(pVisibility);
		setCurrentsColumnVisibility(pVisibility);
	}
	//-----
	/**	Wf	02.10.2023
	 * 
	 * @param pVisibility
	 */
	public void setQuotaColumnVisibility(boolean pVisibility) {
		tcQuotas.setVisible(pVisibility);
	}
	/**	Wf	02.10.2023
	 * 
	 * @param pVisibility
	 */
	public void setLastsColumnVisibility(boolean pVisibility) {
		tcLasts.setVisible(pVisibility);
	}
	/**	Wf	02.10.2023
	 * 	
	 * @param pVisibility
	 */
	public void setCurrentsColumnVisibility(boolean pVisibility) {
		tcCurrents.setVisible(pVisibility);
	}
	
	/**	Wf	02.10.2023
	 * 
	 * @param pVisibility
	 */
	public void setTotalsColumnVisibility(boolean pVisibility) {
		tcTotals.setVisible(pVisibility);
	}
	
	
	//-----
	
	/**	Wf	02.10.2023
	 * 
	 */
	protected void setEnabled(boolean pEnable) {
		tvZineList.setDisable(!pEnable);
	}
	
	//-----
	
	/**	Wf	13.11.2023
	 * 
	 */
	public void update() {
		tvZineList.refresh();
		lZineListCount.setText("Anzahl Zines: "+ liZineList.size());
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	02.10.2023
	 * 	
	 * @return
	 */
	public int getZineListID() {
		return zineListID;
	}
	
	/**	Wf	02.10.2023
	 * 
	 * @return
	 */
	public SelectionModel<ZineListTableElement> getSelectionModel(){
		return tvZineList.getSelectionModel();
	}
	
	/**	Wf	20.01.2024
	 * 
	 * @return
	 */
	public ObservableList<ZineListTableElement> getZineListTable(){
		return liZineList;
	}
	
	//-----
	
	/**	Wf	26.01.2024
	 * 	
	 * @param pTitle
	 * @throws Exception
	 */
	public void setZineListTitle(String pTitle) throws Exception {		
		if (pTitle != null) lTabTitle.setText(pTitle);
		else throw new Exception("04, sZLT,ZLTC");
		Platform.runLater(() -> {
			HBox vHBox = (HBox) lTabTitle.getParent();
			VBox vVBox = (VBox) vHBox.getParent();
			
			double vPaddings = vVBox.getPadding().getLeft()+ vVBox.getPadding().getRight();
			
			if (vVBox.getWidth() > 580) vVBox.setPrefWidth(580);
			
			if ((vHBox.getWidth()) > (580 - vPaddings )) vHBox.setPrefWidth( 580 - vPaddings );
			
			if ( (lTabTitle.getWidth()) > (580 - vPaddings - (2*lZineListCount.getWidth()) - (2*seSeparator.getWidth()) ) )				
				lTabTitle.setPrefWidth( 580 - vPaddings - (2*lZineListCount.getWidth()) - (2*seSeparator.getWidth()) );
			
			/*Platform.runLater(() -> {
				System.out.println("VBox: "+vVBox.getWidth());
				System.out.println("HBox: "+vHBox.getWidth());
			});*/
		});
		
	}
	
	/**	Wf	20.01.2024
	 * 
	 * @param pZineListTableElements
	 */
	public void setZineListTable(ArrayList<ZineListTableElement> pZineListTableElements) {
		liZineList.setAll(pZineListTableElements);
		liFilteredZineList.setPredicate( createZineListPredicate(tfSearchField.getText(), cbSearchCategory.getSelectionModel().getSelectedItem().getId()) );
		
		lZineListCount.setText("Anzahl: " + liZineList.size());
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	02.10.2023
	 * 
	 * @return
	 */
	public ZineListTableElement getZineListSelection() {
		return tvZineList.getSelectionModel().getSelectedItem();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	20.01.2024
	 * 
	 */
	@FXML
	public void search() {
		if (cbSearchCategory.getSelectionModel().getSelectedItem() != null) {
			liFilteredZineList.setPredicate(createZineListPredicate(tfSearchField.getText(), cbSearchCategory.getSelectionModel().getSelectedItem().getId()));
			tvZineList.refresh();
		}else LogManager.handleException(new Exception("04; sea,ZLTC"));
	}
	
	/**	Wf	23.01.2024
	 * 
	 * @param pSearchText
	 * @param pSearchCategory
	 * @return
	 */
	private Predicate<ZineListTableElement> createZineListPredicate(String pSearchText, int pSearchCategory){
		return pZineListTableElement -> {
			boolean vRet = true;
			
			if (((pSearchCategory == 1) || (pSearchCategory == 2)) && (!pSearchText.equals(""))){
				if (pSearchCategory == 1) vRet = pZineListTableElement.getName().toLowerCase().contains(pSearchText.toLowerCase());
				else					  vRet = pZineListTableElement.getCategoryName().toLowerCase().contains(pSearchText.toLowerCase()); 
			}
			
			return vRet;
		};
	}
	
}
