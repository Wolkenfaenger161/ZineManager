/**	ZineManager v0.1		Wf	20.11.2023
 * 	
 * 	gui.controller
 * 	  BasicController
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

import org.zinemanager.gui.callables.EmptyElementCreater;
import org.zinemanager.gui.callables.GUIListElementFormater;
import org.zinemanager.gui.callables.GUITableElementSetter;
import org.zinemanager.gui.callables.NameGetter;
import org.zinemanager.gui.tableelements.BasicTableElement;
import org.zinemanager.gui.tableelements.NameTableElement;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionModel;

public abstract class BasicController {
		
	/**	Wf	27.09.2023
	 * 
	 */
	@FXML
	public abstract void back();
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	05.10.2023
	 * 
	 * @param pNewList
	 * @param pWithExtraEmptyElement
	 * @param pNameGetter
	 * @return
	 * @throws Exception
	 */
	protected ArrayList<NameTableElement> generateNewNameTableElementList (ArrayList<Integer> pNewList, boolean pWithExtraEmptyElement,
														NameGetter pNameGetter, SelectionModel<NameTableElement> pSelectionModel)throws Exception{
		return generateNewGUIList(pNewList, (pID) -> {return new NameTableElement(pID, pNameGetter.getName(pID));},
							 ((pWithExtraEmptyElement) ? () -> {return new NameTableElement(-1, "");} : null), pSelectionModel);
	}
	/**	Wf	05.10.2023
	 * 
	 * @param <T>
	 * @param pNewListIDs
	 * @param pGUIListElementFormater
	 * @param pEmptyElementCreater
	 * @return
	 * @throws Exception
	 */
	protected <T extends BasicTableElement> ArrayList<T> generateNewGUIList(ArrayList<Integer> pNewListIDs, GUIListElementFormater<T> pGUIListElementFormater,
																		EmptyElementCreater<T> pEmptyElementCreater, SelectionModel<T> pSelectionModel) throws Exception{
		ArrayList<T> vRet = new ArrayList<T>();
		T vCurSel = (pSelectionModel != null) ? pSelectionModel.getSelectedItem() : null;
		Integer vSelID = (vCurSel != null) ? vCurSel.getId() : null ;
		
		
		if (pEmptyElementCreater != null) vRet.add(pEmptyElementCreater.createEmptyElement());
		
		if (pGUIListElementFormater != null) {
			for (Integer vID : pNewListIDs) {
				try {
					vRet.add(pGUIListElementFormater.formatGUIListElement(vID.intValue()));
				}catch(Exception ex) {throw ex;}
			}
		}
		
		if (vSelID != null) {
			for (T vTemp : vRet) {
				if (vTemp.getId() == vSelID.intValue()) pSelectionModel.select(vTemp);
			}
		}
		
		return vRet;
	}
	
	/**	Wf	20.11.2023
	 * 	
	 * @param pUpdatedList
	 * @param pUpdatingIDList
	 * @param pWithExtraEmptyElement
	 * @param pNameGetter
	 * @param pSelectionModel
	 * @throws Exception
	 */
	protected void updateNameTableElementList(ObservableList<NameTableElement> pUpdatedList, ArrayList<Integer> pUpdatingIDList, boolean pWithExtraEmptyElement,
												NameGetter pNameGetter, SelectionModel<NameTableElement> pSelectionModel) throws Exception{
		updateNameTableElementList(pUpdatedList, pUpdatingIDList, ((pWithExtraEmptyElement) ? () -> {return new NameTableElement(-1, "");} : null), pNameGetter, pSelectionModel);
	}
	
	/**	Wf	20.11.2023
	 * 
	 * @param pUpdatedList
	 * @param pUpdatingIDList
	 * @param pExtraElement
	 * @param pNameGetter
	 * @param pSelectionModel
	 * @throws Exception
	 */
	protected void updateNameTableElementList(ObservableList<NameTableElement> pUpdatedList, ArrayList<Integer> pUpdatingIDList,  EmptyElementCreater<NameTableElement> pExtraElementCreater,
			NameGetter pNameGetter, SelectionModel<NameTableElement> pSelectionModel) throws Exception{
		updateGUIList(pUpdatedList, pUpdatingIDList, (pNameElement) -> {pNameElement.setName( pNameGetter.getName(pNameElement.getId()) );},
					(pID) -> {return new NameTableElement(pID, pNameGetter.getName(pID));},
					pExtraElementCreater, pSelectionModel);
}
	
	/**	Wf	20.11.2023
	 * 
	 * @param <T>
	 * @param pUpdatedList
	 * @param pUpdatingIDList
	 * @param pSetter
	 * @param pGUIListElementFormater
	 * @param pEmptyElementCreater
	 * @param pSelectionModel
	 * @throws Exception
	 */
	protected <T extends BasicTableElement> void updateGUIList(ObservableList<T> pUpdatedList, ArrayList<Integer> pUpdatingIDList, GUITableElementSetter<T> pSetter, 
																GUIListElementFormater<T> pGUIListElementFormater, EmptyElementCreater<T> pEmptyElementCreater,
																SelectionModel<T> pSelectionModel) throws Exception{
		ArrayList<T> vToRemove = new ArrayList<T>();
		
		T vCurSel = (pSelectionModel != null) ? pSelectionModel.getSelectedItem() : null;
		Integer vSelID = (vCurSel != null) ? vCurSel.getId() : null ;
		
		
		if ((pEmptyElementCreater != null) && (!containsListID(pUpdatedList, -1))) pUpdatedList.add(pEmptyElementCreater.createEmptyElement());
		
		if (pGUIListElementFormater != null) {
			for (T vTemp : pUpdatedList) {
				if (!pUpdatingIDList.contains(Integer.valueOf(vTemp.getId())) && (vTemp.getId() != -1)) vToRemove.add(vTemp);

			}
			for (T vTemp : vToRemove) {
				pUpdatedList.remove(vTemp);
			}
			
			for (Integer vID : pUpdatingIDList) {
				if (containsListID(pUpdatedList, vID.intValue())) pSetter.setGUITableElement( getElementFromList(pUpdatedList, vID.intValue()) );
				else pUpdatedList.add( pGUIListElementFormater.formatGUIListElement(vID.intValue()) );
			}
		}
		
		if (vSelID != null) {
			for (T vTemp : pUpdatedList) {
				if (vTemp.getId() == vSelID.intValue()) pSelectionModel.select(vTemp);
			}
		}
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	01.10.2023
	 * 
	 * @param <T>
	 * @param pIDElementList
	 * @return
	 */
	protected <T extends BasicTableElement> int calculateNewID(ObservableList<T> pIDList) {
		int vRet = 0;
		
		for (int i=0; i<pIDList.size(); i++) {
			if (vRet == pIDList.get(i).getId()) {
				i = -1;
				vRet ++;
			}
		}
		
		return vRet;
	}
	
	/**	Wf	03.10.2023
	 * 
	 * @param <T>
	 * @param pListToConvert
	 * @return
	 */
	protected <T extends BasicTableElement> ArrayList<Integer> convertToIDList(ObservableList<T> pListToConvert){
		ArrayList<Integer> vRet = new ArrayList<Integer>();
		
		if (pListToConvert != null) {
			for (T vTemp : pListToConvert) {
				vRet.add(Integer.valueOf(vTemp.getId()));
			}
		}
		
		return vRet;
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	05.10.2023
	 * 
	 * @param <T>
	 * @param pList
	 * @param pID
	 * @return
	 */
	protected <T extends BasicTableElement> T getElementFromList(ObservableList<T> pList, int pID) {
		T vRet = null;
		
		for (T vTemp : pList) {
			if (vTemp.getId() == pID) vRet = vTemp;
		}
		
		return vRet;
	}
	
	/**	Wf	05.10.2023
	 * 
	 * @param <T>
	 * @param pList
	 * @param pID
	 * @return
	 */
	protected <T extends BasicTableElement> boolean containsListID(ObservableList<T> pList, int pID) {
		boolean vRet = false;
		
		for (T vTemp : pList) {
			if (vTemp.getId() == pID) vRet = true;
		}
		
		return vRet;
	}
	
}
