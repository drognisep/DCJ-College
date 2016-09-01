package util;

import inval.object.ObjValidator;

import java.util.List;

public class TransformHtml {
	private String attributes = "";
	private int columns = 1;
	private String headerString = "";
	private String[] tableData = new String[0];
	
	public static String getListItems(String... items) {
		StringBuffer sb = new StringBuffer();
		for(String i : items) sb.append("<li>").append(i).append("</li>\n");
		return sb.toString();
	}
	
	public static String getOptionList(String... items) {
		StringBuffer sb = new StringBuffer();
		for(String i : items) sb.append("<option value=\"").append(i).append("\">").append(i).append("\n");
		return sb.toString();
	}
	
	public static String getOptionList(List<?> items) {
		StringBuilder sb = new StringBuilder();
		
		for(Object o : items) {
			sb.append("<option>").append(o.toString()).append("</option>\n");
		}
		
		return sb.toString();
	}
	
	public String getTableRows() {
		StringBuffer sb = new StringBuffer();
		boolean newRow = true;
		
		for(int i = 0; i < tableData.length; i++) {
			if(newRow) {
				sb.append("<tr>\n");
				newRow = false;
			}
			sb.append("<td>").append(tableData[i]).append("</td>\n");
			if((i + 1) % columns == 0) {
				sb.append("</tr>\n");
				newRow = true;
			}
		}
		
		return sb.toString();
	}
	
	public static String getTableRows(int columns, String[] data) {
		TransformHtml trans = new TransformHtml();
		
		trans.setColumns(columns);
		trans.setTableData(data);
		
		return trans.getTableRows();
	}
	
	public static String getTableRows(int columns, String data) {
		return getTableRows(columns, data.split("|"));
	}
	
	public static String getTableRows(int columns, List<?> list) {
		StringBuilder sb = new StringBuilder();
		
		boolean newRow = true;
		for(int i = 0; i < list.size(); i++) {
			if(newRow) sb.append("<tr>");
			sb.append("<td>").append(list.get(i).toString()).append("</td>");
			if((i + 1) % columns == 0) {
				sb.append("</tr>\n");
				newRow = true;
			} else newRow = false;
		}
		
		return sb.toString();
	}
	
	public static String getTable(String attributes, int columns, String headerString, String[] data) {
		TransformHtml trans = new TransformHtml();
		
		trans.setAttributes(attributes);
		trans.setColumns(columns);
		trans.setTableData(data);
		trans.setHeaderString(headerString);
		
		return trans.getTable();
	}
	
	public static String getTable(String attributes, int columns, String headerString, String data) {
		return getTable(attributes, columns, headerString, data.split("|"));
	}
	
	public String getTable() {
		StringBuffer sb = new StringBuffer();
		sb.append("<table " + attributes + ">\n");
		String[] headers = headerString.split("|");
		
		if(headers != null && headers.length > 0) {
			sb.append("<tr>\n");
			for(String h : headers) {
				sb.append("<th>").append(h).append("</th>");
			}
			sb.append("</tr>");
		}
		
		boolean newRow = true;
		for(int i = 0; i < tableData.length; i++) {
			if(newRow) {
				sb.append("<tr>\n");
				newRow = false;
			}
			sb.append("<td>").append(tableData[i]).append("</td>\n");
			if((i + 1) % columns == 0) {
				sb.append("</tr>\n");
				newRow = true;
			}
		}
		
		sb.append("</table>\n");
		
		return null;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		if(ObjValidator.notEmptyStrings(attributes)) this.attributes = attributes;
		else this.attributes = "";
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		if(columns > 0) this.columns = columns;
		else columns = 1;
	}

	public String getHeaderString() {
		return headerString;
	}

	public void setHeaderString(String headerString) {
		if(ObjValidator.notEmptyStrings(headerString)) this.headerString = headerString;
		else this.headerString = "";
	}

	public String[] getHeaderData() {
		return tableData;
	}

	public String[] getTableData() {
		return tableData;
	}

	public void setTableData(String[] tableData) {
		this.tableData = tableData;
	}
	
	public void setTableData(String tableData) {
		this.tableData = tableData.split("|");
	}
	
	public void setTableData(List<?> tableData) {
		String[] listData = new String[tableData.size()];
		
		for(int i = 0; i < tableData.size(); i++) {
			listData[i] = tableData.get(i).toString();
		}
		
		this.tableData = listData;
	}
}
