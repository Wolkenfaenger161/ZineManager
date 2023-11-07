/**	ZineManager v0.0	Wf	11.10.2023
 * 
 * 	logic.manager
 * 	LogManager
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

package org.zinemanager.logic.manager;

import java.time.LocalDate;
import java.time.LocalTime;

import org.zinemanager.MainManager;

public abstract class LogManager {
	private static double version = 0.0;
	private static String logfile;
	
	private static MainManager mainManager;
	private static DatabaseManager databaseManager;
	
	
	/**	Wf	11.10.2023
	 * 
	 * @param pMainManager
	 */
	public static void initManager(MainManager pMainManager) {
		LocalDate vNow = LocalDate.now();
		LocalTime vCurTime = LocalTime.now();
		
		mainManager = pMainManager;
		databaseManager = null;
		
		logfile = vNow.toString() +"_"+ vCurTime.getHour() +"-"+ vCurTime.getMinute() +"_logfile.txt";
	}
	
	/**	Wf	11.10.2023
	 * 
	 * @param pDatabaseManager
	 */
	public static void setDatabaseManager(DatabaseManager pDatabaseManager) {
		databaseManager = pDatabaseManager;
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	21.08.2023
	 * 
	 * @return
	 */
	public static double getVersion() {
		return version;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	21.08.2023
	 * 
	 */
	public static void closeApp() {
		if (mainManager != null) mainManager.closeApp();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	11.10.2023
	 * 
	 * @param pLogEntry
	 */
	public static void createLogEntry(String pLogEntry) {
		LocalTime vNow;
		
		if (logfile != null) {
			vNow = LocalTime.now();
			try {
				if (databaseManager != null) {
					databaseManager.writeLogFile(logfile, "[Log] "+vNow.toString() +" "+ pLogEntry);
				}
			}catch(Exception ex) {LogManager.handleException(ex);}
		}
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	11.10.2023
	 * 
	 * @param ex
	 */
	public static void handleException(Exception ex) {
		if (mainManager != null) {
			createLogEntry("[Error] " + ex.getMessage() +"\n"+ ex.getCause() + "\n"+ ex.getStackTrace());
			mainManager.handleException(ex);
		}
	}
	/**	Wf	11.10.2023
	 * 
	 * @param pMessage
	 */
	public static void handleMessage(String pMessage) {
		if (mainManager != null) {
			createLogEntry("[Message] " + pMessage);
			mainManager.handleMessage(pMessage);
		}
	}
	
}
