/**	ZineManager v0.0	Wf	11.10.2023
 * 
 * 	MainManager
 * 
 * 	Exceptions:
 * 	  01 Wrong length
 * 	  02 Wrong Value
 * 	  03 Calculation Error
 * 	  04 Nullpointer Error
 * 	  05 Empty List Error
 * 	  06 Wrong Type Error
 * 	  07 Index Error
 * 	  08 Equal Object Error
 * 	  09 Wrong Selection
 */

package org.zinemanager;

import java.util.ArrayList;

import org.zinemanager.gui.stages.InfoStage;
import org.zinemanager.gui.stages.MainMenuStage;
import org.zinemanager.logic.manager.DataSetManager;
import org.zinemanager.logic.manager.DatabaseManager;
import org.zinemanager.logic.manager.LogManager;
import org.zinemanager.logic.manager.SettingManager;
import org.zinemanager.logic.manager.ZineManager;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainManager extends Application {
	private static Stage mainStage, primaryStage;
	
	private DatabaseManager databaseManager;
	private SettingManager settingManager;
	private DataSetManager dataSetManager;
	private ZineManager zineManager;
	
	private ArrayList<InfoStage> infoStages;
	
	/**	Wf	21.08.2023
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**	Wf	11.10.2023
	 * 
	 */
	public void start(Stage pPrimaryStage) {
		primaryStage = pPrimaryStage;
		
		infoStages = new ArrayList<InfoStage>();
		LogManager.initManager(this);
		
		try {
			databaseManager = new DatabaseManager();
			databaseManager.init();
			
			LogManager.setDatabaseManager(databaseManager);
			LogManager.createLogEntry("Start Log");
			
			settingManager = new SettingManager(databaseManager);
			dataSetManager = new DataSetManager(databaseManager, settingManager);
			
			LogManager.createLogEntry("Setting- + DataSet-Manager initialized");
			
			zineManager = new ZineManager(settingManager, dataSetManager);
			
			LogManager.createLogEntry("Start GUI");
			mainStage = new MainMenuStage(zineManager);
		} catch (Exception ex) {LogManager.handleException(ex);}
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	11.10.2023
	 * 
	 */
	public void closeApp() {
		LogManager.createLogEntry("Start closing App");
		
		try {
			if (dataSetManager != null) dataSetManager.saveAllDataSets();
			if (settingManager != null) {
				if (zineManager != null) settingManager.setCurrentDataSetID(zineManager.getCurrentDataSetID());
				settingManager.saveAllSettings();
			}
			
			LogManager.createLogEntry("Saving complete");
		}catch(Exception ex) {LogManager.handleException(ex);}
		
		mainStage.close();
		primaryStage.close();
		
		LogManager.createLogEntry("Close Log");
		System.exit(0);
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	30.09.2023
	 * 
	 * @return
	 */
	public static Stage getMainStage() {
		return mainStage;
	}
		
//--------------------------------------------------------------------------------------------------------

	/**	Wf	27.09.2023
	 * 
	 * @param ex
	 */
	public void handleException(Exception ex) {
		System.out.println(ex.getCause() + " : " + ex.getMessage());
		infoStages.add(new InfoStage(true, ex.getCause() + " : " + ex.getMessage(), this));
	}
	/**	Wf	03.10.2023
	 * 
	 * @param pMessage
	 */
	public void handleMessage(String pMessage) {
		System.out.println(pMessage);
		infoStages.add(new InfoStage(false, pMessage, this));
	}
	
	/**	Wf	27.09.2023
	 * 
	 * @param pInfoStage
	 */
	public void removeInfoStage(InfoStage pInfoStage) {
		infoStages.remove(pInfoStage);
		pInfoStage.hide();
	}
}
