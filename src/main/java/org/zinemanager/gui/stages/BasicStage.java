/**	ZineManager v0.0		Wf	22.10.2023
 * 	
 * 	gui.stages
 * 	  BasicStage
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

package org.zinemanager.gui.stages;

import org.zinemanager.gui.controller.BasicController;
import org.zinemanager.logic.manager.LogManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class BasicStage <Controller extends BasicController> extends Stage {
	protected String stageFile, title;
	protected Controller controller;
	
	/**	Wf	15.10.2023
	 * 
	 * @param pStageFile
	 * @param pTitle
	 */
	public BasicStage(String pStageFile, String pTitle) {
		this(pStageFile, pTitle, true);
	}
	/**	Wf	15.10.2023
	 * 
	 * @param pStageFile
	 * @param pTitle
	 * @param pShowDirectly
	 */
	public BasicStage(String pStageFile, String pTitle, boolean pShowDirectly) {
		super();
		
		stageFile = pStageFile;
		title = pTitle;
		
		init(pShowDirectly);
	}
	
	/**	Wf	22.10.2023
	 * 
	 * @param pShowDirectly
	 */
	private void init(boolean pShowDirectly) {
		FXMLLoader vFXMLLoader;
		Parent vRoot;
		Scene vScene;
		
		try {
			vFXMLLoader = new FXMLLoader(getClass().getResource("/"+stageFile));
			
			vRoot = vFXMLLoader.load();
			vScene = new Scene(vRoot);
			controller = vFXMLLoader.getController();
			
			this.setScene(vScene);
			this.sizeToScene();
			this.setResizable(false);
			
			this.setTitle(title);
			this.initModality(Modality.WINDOW_MODAL);
			this.setIconified(false);
			if (pShowDirectly)	this.show();
			//controller.setUp(pIsEdit, false, pParentController, pEditor);
			
			this.setOnCloseRequest(event -> {
				controller.back();
				event.consume();
			});
		} catch(Exception ex) {LogManager.handleException(ex);}
		
	}
}
