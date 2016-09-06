package util;

import inval.object.ObjValidator;

import java.util.List;

import data.util.Course;
import data.util.ScheduleEntry;
import data.util.Section;

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
	
	public static String prettyPrintCatalog(List<Course> list) {
		if(list == null) {
			return "<table><tr><td>Error retrieving catalog data</td></tr></table>";
		} else if(list.size() == 0) {
			return "<table><tr><td>No results to return</td></tr></table>";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<h3>Course Catalog</h3>\n<table style='text-align:center'>");
		sb.append("<tr><th>Course ID</th><th>Course Name</th><th>Hours</th>"
				+ "<th>Section ID</th><th>Room</th></tr>");
		for(Course c : list) {
			for(Section s : c.getSections()) {
				sb.append("<tr>");
				sb.append("<td>").append(c.getCourse_id()).append("</td>");
				sb.append("<td>").append(c.getCourse_name()).append("</td>");
				sb.append("<td>").append(c.getHours()).append("</td>");
				sb.append("<td>").append(s.getSection_id()).append("</td>");
				sb.append("<td>").append(s.getRoom()).append("</td>");
				sb.append("</tr>");
			}
		}
		return sb.toString();
	}
	
	public static String prettyPrintSchedule(List<ScheduleEntry> entries) {
		StringBuilder sb = new StringBuilder();
		sb.append("<h3>Your Schedule</h3>\n<table style='text-align:center'><tr><th>Course ID</th><th colspan='2'>Monday</th><th colspan='2'>Tuesday</th><th colspan='2'>Wednesday</th>"
				+ "<th colspan='2'>Thursday</th><th colspan='2'>Friday</th></tr>\n");
		if(entries == null) {
			sb.append("<tr><td colspan='11'>Error retrieving results</td></tr></table>");
			return sb.toString();
		} else if(entries.size() == 0) {
			sb.append("<tr><td colspan='11'>No results found</td></tr></table>");
			return sb.toString();
		}
		for(ScheduleEntry e : entries) {
			String[] times = {
					e.getMon_start() == 0 ? "" : ScheduleEntry.toTimeString(e.getMon_start()),
					e.getMon_end() == 0 ? "" : ScheduleEntry.toTimeString(e.getMon_end()),
					e.getTues_start() == 0 ? "" : ScheduleEntry.toTimeString(e.getTues_start()),
					e.getTues_end() == 0 ? "" : ScheduleEntry.toTimeString(e.getTues_end()),
					e.getWed_start() == 0 ? "" : ScheduleEntry.toTimeString(e.getWed_start()),
					e.getWed_end() == 0 ? "" : ScheduleEntry.toTimeString(e.getWed_end()),
					e.getThur_start() == 0 ? "" : ScheduleEntry.toTimeString(e.getThur_start()),
					e.getThur_end() == 0 ? "" : ScheduleEntry.toTimeString(e.getThur_end()),
					e.getFri_start() == 0 ? "" : ScheduleEntry.toTimeString(e.getFri_start()),
					e.getFri_end() == 0 ? "" : ScheduleEntry.toTimeString(e.getFri_end())
			};
			sb.append("<tr><td>").append(e.getCourse_id()).append("</td>");
			for(int i = 0; i < 10; i+=2) {
				sb.append("<td>").append(times[i]).append("</td>");
				sb.append("<td>").append(times[i+1]).append("</td>");
			}
			sb.append("</tr>\n");
		}
		sb.append("</table><br />\n");
		return sb.toString();
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
