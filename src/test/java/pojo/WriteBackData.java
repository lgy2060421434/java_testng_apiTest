package pojo;

/**
 * 回写数据对象
 * 
 * @author Administrator
 *
 */
public class WriteBackData {
	private String sheetName;
	private String rowIdentifier;
	private String cellName;
	private String result;
	public WriteBackData() {
		// TODO Auto-generated constructor stub
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getRowIdentifier() {
		return rowIdentifier;
	}
	public void setRowIdentifier(String rowIdentifier) {
		this.rowIdentifier = rowIdentifier;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "WriteBackData [sheetName=" + sheetName + ", rowIdentifier=" + rowIdentifier + ", cellName=" + cellName
				+ ", result=" + result + "]";
	}
	public WriteBackData(String sheetName, String rowIdentifier, String cellName, String result) {
		super();
		this.sheetName = sheetName;
		this.rowIdentifier = rowIdentifier;
		this.cellName = cellName;
		this.result = result;
	}

	

}
