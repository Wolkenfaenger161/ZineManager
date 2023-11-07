/**	ZineManager v0.0		Wf	28.09.2023
 * 	
 * 	gui.controller
 * 	  BasicController
 * 		InfoController
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

import org.zinemanager.gui.stages.InfoStage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class InfoController extends BasicController {
	@FXML
	private Label lTitle;
	@FXML
	private Button btBack;
	@FXML
	private TextArea taMessage;
	
	protected InfoStage infoStage;
	
	/**	Wf	28.09.202
	 * 
	 */
	public InfoController() {
		super();
		
		infoStage = null;
	}
	
	/**	Wf	28.09.2023
	 * 
	 * @param pMessage
	 * @param pInfoStage
	 */
	public void setUp(String pMessage, InfoStage pInfoStage) {
		infoStage = pInfoStage;
		
		lTitle.setText(infoStage.getTitle());
		taMessage.setText(pMessage);
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	28.09.2023
	 * 
	 */
	@FXML
	public void back() {
		infoStage.closeInfoStage();	
	}
}
