/**	ZineManager v0.0	Wf	11.10.2023
 * 
 * 	logic.manager
 * 	DatabaseManager
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
 */

package org.zinemanager.logic.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.zinemanager.logic.entities.DataSet;
import org.zinemanager.logic.settings.ZineManagerSettings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class DatabaseManager {
	protected String appPath = "/ZineManager";
	private String	dataSetPath = "/dataSets",
				   	settingPath = "/settings",
					logPath     = "/logs";
	private String zineManagerSettingPath;
	
	protected File systemFile;
	private File dataSetDirectory, settingDirectory, logDirectory;
	
	private HashMap<Integer, String> dataSetFilePaths;
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 */
	private void initSettingPaths() {
		zineManagerSettingPath = settingDirectory.getAbsolutePath() + "/zineManagerSettings.xml";
	}
	/**	Wf	10.10.2023
	 * 
	 */
	private void initDataSetPaths() {
		String vDataSetDirectoryPath = dataSetDirectory.getAbsolutePath();
		DataSet vTemp;
		dataSetFilePaths = new HashMap<Integer, String>();
		
		for (String vDataSetFile : dataSetDirectory.list((pDir, pName) -> {return (pName.endsWith(".xml"));} )) {
			try {
				vTemp = loadObjectFromXML(vDataSetDirectoryPath + "/" + vDataSetFile, DataSet.class);
				
				if (!dataSetFilePaths.containsKey(Integer.valueOf(vTemp.getId())))
					dataSetFilePaths.put(Integer.valueOf(vTemp.getId()), vDataSetDirectoryPath + "/" + vDataSetFile);
				else throw new Exception("08; iDSP,DaM");
			}catch(Exception ex) {LogManager.handleException(ex);}
		}
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception{
		systemFile = getFileSystem();
		
		dataSetDirectory = new File(systemFile.getAbsolutePath() + dataSetPath);
		settingDirectory = new File(systemFile.getAbsolutePath() + settingPath);
		logDirectory     = new File(systemFile.getAbsolutePath() + logPath);
		
		initSettingPaths();
		initDataSetPaths();
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
	/**	Wf	07.10.2023
	 * 
	 * @param vOS
	 * @param vHomeFile
	 * @throws Exception
	 */
	private void proofFileSystem(String pOS, File pHomeFile) throws Exception {
		File vTemp;
		
		ArrayList<String> vDirectoryPaths = new ArrayList<String>(Arrays.asList( dataSetPath, settingPath, logPath));
		
		if (pOS.contains("Windows") || pOS.contains("Linux")) {
			if (!pHomeFile.exists()) pHomeFile.mkdir();
			
			for (String vDirectoryPath : vDirectoryPaths) {
				vTemp = new File(pHomeFile.getAbsolutePath() + vDirectoryPath);
				
				if (!vTemp.exists()) vTemp.mkdir();
			}
		} else throw new Exception("20; mFS,DaCon");
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

	/**	Wf	07.10.2023
	 * 
	 * @param pZineManagerSettings
	 * @throws Exception
	 */
	public void saveZineManagerSettings(ZineManagerSettings pZineManagerSettings) throws Exception {
		saveObjectAsXML(zineManagerSettingPath, pZineManagerSettings);
	}
	
	//-----
	
	/**	Wf	10.10.2023
	 * 
	 * @param pDataSet
	 * @throws Exception
	 */
	public void saveDataSet(DataSet pDataSet) throws Exception {
		Integer vID;
		String vTempPath;
		
		if (pDataSet != null) {
			vID = Integer.valueOf(pDataSet.getId());
			vTempPath = dataSetDirectory.getAbsolutePath()+"/" + vID.toString()+"_"+pDataSet.getName()+".xml";
			
			if (!dataSetFilePaths.containsKey(vID)) dataSetFilePaths.put(vID, vTempPath);
			else if (!dataSetFilePaths.get(vID).equals(vTempPath)) {
				removeFile(dataSetFilePaths.get(vID));
				dataSetFilePaths.put(vID, vTempPath);
			}
			
			saveObjectAsXML(vTempPath, pDataSet);
		}else throw new Exception("04; sDS,DaM");
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
	
	/**	Wf	07.10.2023
	 * 
	 * @param pID
	 * @return
	 * @throws Exception
	 */
	public DataSet loadDataSet(int pID) throws Exception{
		if (dataSetFilePaths.containsKey(Integer.valueOf(pID))) return loadObjectFromXML(dataSetFilePaths.get(Integer.valueOf(pID)), DataSet.class);
		else throw new Exception("02; lDS,DaM");
	}
	/**	Wf	07.10.2023
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<DataSet> loadAllDataSets() throws Exception{
		ArrayList<DataSet> vRet = new ArrayList<DataSet>();
		DataSet vTemp;
		
		for (String vDataSetPath : dataSetFilePaths.values()) {
			vTemp = loadObjectFromXML(vDataSetPath, DataSet.class);
			
			if (vTemp != null) vRet.add(vTemp);
		}
		
		return vRet;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @param pDataSet
	 * @param pFilePath
	 * @throws Exception
	 */
	public void exportDataSet(DataSet pDataSet, String pFilePath) throws Exception{
		saveObjectAsXML(pFilePath, pDataSet);
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @param pFilePath
	 * @return
	 * @throws Exception
	 */
	public DataSet importDataSet(String pFilePath) throws Exception{
		return loadObjectFromXML(pFilePath, DataSet.class);
	}
		
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @param pID
	 * @throws Exception
	 */
	public void removeDataSet(int pID) throws Exception{
		if (dataSetFilePaths.containsKey(Integer.valueOf(pID))) removeFile(dataSetFilePaths.get(Integer.valueOf(pID)));
		else throw new Exception("02; lDS,DaM");
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
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
				vFile = new File(pFilePath);
				
				vObjectMapper = new XmlMapper();
				
				vXMLText = vObjectMapper.writeValueAsString(pObject);
				
				if (!vFile.exists()) vFile.createNewFile();
				
				Files.write(vFile.toPath(), vXMLText.getBytes("UTF-8"));
			} else throw new Exception("02; sOaXML,DaM");
		} else throw new Exception("04; sOaXML,DaM");
	}
	/**	Wf	07.10.2023
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
			vXMLText = Files.readString(vFile.toPath(), Charset.forName("UTF-8"));  // ISO-8859-1 UTF-8
			vObjectMapper = new XmlMapper();
			
			vRet = vObjectMapper.readValue(vXMLText, pClass);
		} else throw new Exception("21; lOfXML,DaM");
		
		return vRet;
	}
	
	/**	Wf	07.10.2023
	 * 
	 * @param pFilePath
	 * @throws Exception
	 */
	private void removeFile(String pFilePath) throws Exception {
		File vTemp;
		
		if (pFilePath != null) {
			vTemp = new File(pFilePath);
			
			if ((vTemp != null) && (vTemp.exists())) {
				if (vTemp.canWrite()) vTemp.delete();
				else throw new Exception("22; reF,DaM");
			}
		}
	}
	
}
