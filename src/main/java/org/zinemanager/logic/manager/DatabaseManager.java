/**	ZineManager v0.0	Wf	13.11.2023
 * 
 * 	logic.manager
 * 	  DatabaseManager
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
 * 	  20 Wrong OS
 * 	  21 File dosn't exist
 * 	  22 Wrong Right Error
 *    23 No Directory Error
 */

package org.zinemanager.logic.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import org.zinemanager.logic.entities.DataSet;
import org.zinemanager.logic.exceptions.NoFileLoadedException;
import org.zinemanager.logic.settings.ZineManagerSettings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class DatabaseManager {
	protected String appPath = "/ZineManager";
	private String	settingPath = "/settings",
					logPath     = "/logs";
	private String zineManagerSettingPath;
	
	protected File systemFile;
	private File settingDirectory, logDirectory;
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 */
	private void initSettingPaths() {
		zineManagerSettingPath = settingDirectory.getAbsolutePath() + "/zineManagerSettings.xml";
	}
	
	/**	Wf	11.11.2023
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception{
		systemFile = getFileSystem();
		
		settingDirectory = new File(systemFile.getAbsolutePath() + settingPath);
		logDirectory     = new File(systemFile.getAbsolutePath() + logPath);
		
		initSettingPaths();
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	03.09.2023
	 * 
	 * @return
	 * @throws Exception
	 */
	private File getFileSystem() throws Exception {
		String vOS, vArch, vHome;
		File vHomeFile;
		
		vOS = System.getProperty("os.name");
		vArch = System.getProperty("os.arch");
		vHome = System.getProperty("user.home");
		
		if (vOS.contains("Windows")) vHome = vHome+ "/AppData/Roaming"+appPath;
		else if (vOS.contains("Linux")) {
			if (!vArch.contains("aarch64")) vHome = vHome+"/.local"+appPath;
			
		} else throw new Exception("20; gFS,DaM: " + vOS + "; "+vHome);
		
		vHomeFile = new File(vHome);
		proofFileSystem(vOS, vHomeFile);
		
		return vHomeFile;
	}
	/**	Wf	11.11.2023
	 * 
	 * @param vOS
	 * @param vHomeFile
	 * @throws Exception
	 */
	private void proofFileSystem(String pOS, File pHomeFile) throws Exception {
		File vTemp;
		
		ArrayList<String> vDirectoryPaths = new ArrayList<String>(Arrays.asList( settingPath, logPath));
		
		if (pOS.contains("Windows") || pOS.contains("Linux")) {
			if (!pHomeFile.exists()) pHomeFile.mkdir();
			
			for (String vDirectoryPath : vDirectoryPaths) {
				vTemp = new File(pHomeFile.getAbsolutePath() + vDirectoryPath);
				
				if (!vTemp.exists()) vTemp.mkdir();
			}
		} else throw new Exception("20; mFS,DaCon");
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	11.11.2023
	 * 
	 * @return
	 */
	public String getCurrentDirectoryFilePath() {
		return new File("").getAbsolutePath();
	}
	
//--------------------------------------------------------------------------------------------------------
	
	/**	Wf	11.10.2023
	 * 
	 * @param pLogFile
	 * @param pLogEntrie
	 * @throws Exception
	 */
	public void writeLogFile(String pLogFile, String pLogEntrie) throws Exception {
		File vLogFile;
		FileWriter vFileWriter;
		BufferedWriter vBufferedWriter;
		
		if (logDirectory != null) {
			vLogFile = new File(logDirectory.getAbsolutePath()+"/"+pLogFile);
			
			if (!vLogFile.exists()) vLogFile.createNewFile(); 
			
			if (vLogFile.canWrite()) {
				vFileWriter = new FileWriter(vLogFile, true);
				vBufferedWriter = new BufferedWriter(vFileWriter);
				
				vBufferedWriter.append(pLogEntrie);
				vBufferedWriter.newLine();
				vBufferedWriter.close();
			} else throw new Exception("22; wLF,DaM");
		}else throw new Exception("04; wLF,DaM");
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	12.11.2023
	 * 
	 * @param pZineManagerSettings
	 * @throws Exception
	 */
	public void saveZineManagerSettings(ZineManagerSettings pZineManagerSettings) throws Exception {
		ZineManagerSettings vCloned = pZineManagerSettings.clone();
		
		if (!pZineManagerSettings.getCurrentDataSetPath().endsWith(".xml")) vCloned.setCurrentDataSetPath( loadZineManagerSettings().getCurrentDataSetPath() );
		
		saveObjectAsXML(zineManagerSettingPath, vCloned);
	}
	
	//-----
	
	/**	Wf	12.11.2023
	 * 
	 * @param pDataSet
	 * @throws Exception
	 */
	public void saveDataSet(DataSet pDataSet, String pFilePath) throws Exception {
		File vDir, vTempFile;
		File[] vFiles;
		DataSet vTempDataSet = null;
		
		if (pFilePath != null) {
			if (pFilePath.endsWith(".xml")) saveObjectAsXML(pFilePath, pDataSet);
			else {
				vDir = new File(pFilePath);
				vTempFile = null;
				
				if (vDir.isDirectory()) {
					vFiles = vDir.listFiles((pDir, pName) -> {
						return ((pDir.equals(vDir)) && (pName.endsWith(".xml")));
					});
					
					if (vFiles != null) {
						for (File pTempFile : vFiles) {
							if (vTempFile == null) {
								try{
									vTempDataSet = loadObjectFromXML(pTempFile.getAbsolutePath(), DataSet.class);
										
									if ((vTempDataSet != null) && (vTempDataSet.getId() == pDataSet.getId())) vTempFile = pTempFile;
								}catch(Exception ex) {}
							}
						}
					}
					
					if (vTempFile == null) saveObjectAsXML(vDir.getAbsolutePath()+"/dataset.xml", pDataSet);
					else saveObjectAsXML(vTempFile.getAbsolutePath(), pDataSet);
				}else throw new Exception("23; sDS,DaM");
			}
		} else throw new Exception("04; sDS,DaM");
		
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @return
	 * @throws Exception
	 */
	public ZineManagerSettings loadZineManagerSettings() throws Exception {
		return loadObjectFromXML(zineManagerSettingPath, ZineManagerSettings.class);
	}
	
	/**	Wf	12.11.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public DataSet loadDataSet(String pFilePath) throws Exception{
		DataSet vRet = null;
		File vDir;
		File[] vFiles;
		
		if (pFilePath != null) {
			if (pFilePath.endsWith(".xml")) vRet = loadObjectFromXML(pFilePath, DataSet.class);
			else {
				vDir = new File(pFilePath);
				
				if (vDir.isDirectory()) {
					vFiles = vDir.listFiles((pDir, pName) -> {
						return ((pDir.equals(vDir)) && (pName.endsWith(".xml")));
					});
					
					if (vFiles != null) {
						for (File pTempFile : vFiles) {
							try {
								if (vRet == null) vRet = loadObjectFromXML(pTempFile.getAbsolutePath(), DataSet.class);
							}catch(Exception ex) {} 
						}
						
						if (vRet == null ) throw new NoFileLoadedException("05; lDS,Dam");
					}
				}else throw new Exception("23; lDS,DaM");
			}
		} else throw new Exception("04; lDS,DaM");
		
		return vRet;
	}
		
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	12.11.2023
	 * 
	 * @param pFilePath
	 * @throws Exception
	 */
	public void removeDataSet(String pFilePath) throws Exception{
		LogManager.createLogEntry("Start deleting Dataset");
		if ((pFilePath != null) && (pFilePath != "")) removeFile(pFilePath);
		else throw new Exception("04/02; lDS,DaM");
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	12.11.2023
	 * 
	 * @param pFilePath
	 * @param pObject
	 * @throws Exception
	 */
	private <T extends Object> void saveObjectAsXML(String pFilePath, T pObject) throws Exception {
		String vXMLText;
		File vFile;
		ObjectMapper vObjectMapper;
		
		if (pObject != null) {
			if (!pFilePath.equals("")) {
				LogManager.createLogEntry("Start saving File to: " + pFilePath);
				vFile = new File(pFilePath);
				
				vObjectMapper = new XmlMapper();
				
				vXMLText = vObjectMapper.writeValueAsString(pObject);
				
				if (!vFile.exists()) vFile.createNewFile();
				
				Files.write(vFile.toPath(), vXMLText.getBytes("UTF-8"));
				LogManager.createLogEntry("Object saved.");
			} else throw new Exception("02; sOaXML,DaM");
		} else throw new Exception("04; sOaXML,DaM");
	}
	/**	Wf	12.11.2023
	 * 
	 * @param pFilePath
	 * @param pClass
	 * @return
	 * @throws Exception
	 */
	private <T extends Object> T loadObjectFromXML(String pFilePath, Class<T> pClass) throws Exception {
		T vRet = null;
		String vXMLText;
		File vFile;
		ObjectMapper vObjectMapper;
		
		vFile = new File(pFilePath);
				
		if (vFile.exists()) {
			LogManager.createLogEntry("Start Loading File: " + pFilePath);
			vXMLText = Files.readString(vFile.toPath(), Charset.forName("UTF-8"));  // ISO-8859-1 UTF-8
			vObjectMapper = new XmlMapper();
			
			try {
				vRet = vObjectMapper.readValue(vXMLText, pClass);
			}catch(Exception ex) { throw new NoFileLoadedException(ex.getMessage()); } 
			if (vRet == null) throw new NoFileLoadedException("04; lOfXML,DaM");
			
			LogManager.createLogEntry("Loading File finished");
		} else throw new NoFileLoadedException("21; lOfXML,DaM");
		
		return vRet;
	}
	
	/**	Wf	12.11.2023
	 * 
	 * @param pFilePath
	 * @throws Exception
	 */
	private void removeFile(String pFilePath) throws Exception {
		File vTemp;
		
		if (pFilePath != null) {
			LogManager.createLogEntry("Start deleting file: " + pFilePath);
			vTemp = new File(pFilePath);
			
			if ((vTemp != null) && (vTemp.exists())) {
				if (vTemp.canWrite()) vTemp.delete();
				else throw new Exception("22; reF,DaM");
				
				LogManager.createLogEntry("Deleting File was successfull.");
			}
		}
	}
	
}
