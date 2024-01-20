/**	ZineManager v0.2	Wf	19.01.2024
 * 
 * 	BasicSetting
 * 	  ZineManagerSettings
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

package org.zinemanager.logic.settings;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZineManagerSettings extends BasicSetting {
	@JsonProperty("lastDataSetID")
	private int lastDataSetID;
	@JsonProperty("currentDataSetPath")
	private String currentDataSetPath;
	@JsonProperty("pdfReaderFilePath")
	private String pdfReaderFilePath;
	
	/**	Wf	19.01.2024
	 * 
	 */
	public ZineManagerSettings() {
		lastDataSetID = -1;
		
		currentDataSetPath = "";
		pdfReaderFilePath = "";
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 * @return
	 */
	public int getLastDataSetID() {
		return lastDataSetID;
	}
	
	/**	Wf	11.11.2023
	 * 
	 * @return
	 */
	public String getCurrentDataSetPath() {
		return currentDataSetPath;
	}
	/**	Wf	19.01.2024
	 * 
	 * @return
	 */
	public String getPdfReaderFilePath() {
		return pdfReaderFilePath;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	07.10.2023
	 * 
	 * @param pLastDataSetID
	 * @throws Exception
	 */
	public void setLastDataSetID(int pLastDataSetID) throws Exception {
		if (pLastDataSetID >= -1) lastDataSetID = pLastDataSetID;
		else throw new Exception("02; sLDSID,ZMS");
	}
	
	/**	Wf	11.11.2032
	 * 
	 * @param pCurrentDataSetID
	 * @throws Exception
	 */
	public void setCurrentDataSetPath(String pCurrentDataSetPath) throws Exception{
		File vPathFile;
		
		if (pCurrentDataSetPath != null) {
			vPathFile = new File(pCurrentDataSetPath);
			
			if (vPathFile.exists() || pCurrentDataSetPath.equals("")) currentDataSetPath = pCurrentDataSetPath;
			else throw new Exception("02; sCDSP,ZMS");
		}else throw new Exception("04; sCDSP,ZMS");
	}
	/**	Wf	19.01.2024
	 * 
	 * @param pPDFReaderFilePath
	 * @throws Exception
	 */
	public void setPdfReaderFilePath(String pPDFReaderFilePath) throws Exception{
		if (pPDFReaderFilePath != null) {
			if ( isPDFReaderFilePathValied(pPDFReaderFilePath) ) pdfReaderFilePath = pPDFReaderFilePath;
			else throw new Exception("02; sPRFP,ZMS");
		}else throw new Exception("04; sPRFP,ZMS");
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	07.10.2023
	 * 
	 */
	public void increaseLastDataSetID() {
		lastDataSetID ++;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	/**	Wf	19.01.2024
	 * 
	 * @param pPDFReaderFilePath
	 * @return
	 */
	public boolean isPDFReaderFilePathValied(String pPDFReaderFilePath) {
		boolean vRet = false;
		File vPDFReaderFile;
		
		if (pPDFReaderFilePath != null) {
			if (pPDFReaderFilePath.equals("")) vRet = true;
			else {
				vPDFReaderFile = new File(pPDFReaderFilePath);
				
				vRet = vPDFReaderFile.exists(); 
			}
		}
		
		return vRet;
	}
	
//--------------------------------------------------------------------------------------------------------

	/**	Wf	12.11.2023
	 * 
	 */
	public ZineManagerSettings clone() {
		ZineManagerSettings vRet = new ZineManagerSettings();
		vRet.lastDataSetID = this.lastDataSetID;
		vRet.currentDataSetPath = this.currentDataSetPath;
		
		return vRet;
	}
	
}
