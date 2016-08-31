package data.account;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.account.AccountBean;
import data.util.DbHelperException;
import data.util.TranscriptEntry;

public class DummyStudentFunctionHelper extends AbstractStudentFunctionHelper {
	Logger log = Logger.getLogger("DummyStudentFunctionHelper");
	
	private double fees = 123;
	private double paidFees = 0;
	
	private String[] courses = new String[] {
			"100 - English",
			"200 - French",
			"300 - German",
			"400 - Spanglish",
			"500 - Stuff"
	};
	
	private ArrayList<String> myCourses = new ArrayList<>();
	
	private String[][] sections = new String[][] {
			{"1001"},
			{"2001", "2002"},
			{"3001"},
			{"4001"},
			{"5001"}
	};
	
	private ArrayList<String> mySections = new ArrayList<>();
	
	public DummyStudentFunctionHelper() {
		myCourses.add(courses[1]);
		myCourses.add(courses[2]);
		myCourses.add(courses[4]);
		
		mySections.add(sections[1][0]);
		mySections.add(sections[2][0]);
		mySections.add(sections[4][0]);
	}

	@Override
	public List<String> getAvailableCourses(AccountBean act) {
		ArrayList<String> availCourses = new ArrayList<>();
		for(String s : courses) {
			boolean canAdd = true;
			for(int i = 0; i < myCourses.size(); i++) {
				if(s.equals(myCourses.get(i).split(" - ")[0])) {
					canAdd = false;
					break;
				}
			}
			if(canAdd) availCourses.add(s);
		}

		return availCourses;
	}

	@Override
	public List<String> getMyCourses(AccountBean act, int term) throws DbHelperException {
//		String[] retCourses = new String[myCourses.size()];
//		ArrayList<String> retCourses = new ArrayList<>();
//		
//		for(int i = 0; i < retCourses.length; i++) {
//			retCourses[i] = myCourses.get(i);
//		}
//		
//		return retCourses;
		return myCourses;
	}

	@Override
	public boolean addSection(AccountBean act, String courseID, String sectionID, int term) {
		int cid = -1;
		
		for(int i = 0; i < courses.length; i++) {
			log.info("Found courseID: " + courses[i].split(" - ")[0]);
			if(courseID.equals(courses[i].split(" - ")[0])) {
				cid = i;
				break;
			}
		}
		if(cid == -1) {
			log.log(Level.SEVERE, "Didn't find course: " + courseID);
			return false;
		}
		System.out.println("cid = " + cid + ", courses[" + cid + "] = " + courses[cid]);
		
		String[] courseSections = sections[cid];
		
		for(String section : courseSections) {
			if(section.equals(sectionID)) {
				if(mySections.contains(section)) {
					log.warning("Section is already registered");
					return true;
				}
				else {
					return mySections.add(sectionID);
				}
			}
		}
		
		log.log(Level.SEVERE, "Didn't find section: " + sectionID);
		return false;
	}

	@Override
	public boolean dropSection(AccountBean act, String courseID, String sectionID) {
		List<String> cSections = null;
		try {
			cSections = getCourseSections(courseID);
		} catch(DbHelperException dbhx) {
			log.log(Level.SEVERE, "Unable to get course sections for courseID: " + courseID);
			return false;
		}
		for(String s : cSections) {
			if(sectionID.equals(s)) {
				mySections.remove(s);
				return true;
			}
		}
		
		log.log(Level.SEVERE, "Didn't find sectionID: " + sectionID);
		return false;
	}

	@Override
	public double getTotalFees(AccountBean act) throws DbHelperException {
		return fees;
	}

	@Override
	public double payFees(AccountBean act, double amount)
			throws DbHelperException {
		if(amount > 0) {
			paidFees += amount;
		} else {
			log.log(Level.SEVERE, "Bad parameter " + String.format("$%0,1.2f",amount) + " passed as parameter");
		}
		return getTotalFees(act);
	}

	@Override
	public List<String> getCourseSections(String courseID) throws DbHelperException {
		ArrayList<String> list = new ArrayList<>();
		int cid = -1;
		courseID = courseID.split(" - ")[0];
		for(int i = 0; i < courses.length; i++) {
//			log.info("\t- Checking courseID: " + courses[i].split(" - ")[0] + " for " + courseID);
			if(courseID.equals(courses[i].split(" - ")[0])) {
				cid = i;
				break;
			}
		}
		if(cid == -1) {
			log.log(Level.SEVERE, "Unable to find section");
			return null;
		} else {
			for(String s : sections[cid]) {
				list.add(s);
			}
		}
		
		return list;
	}

	@Override
	public double getPaidFees(AccountBean act) throws DbHelperException {
		return paidFees;
	}

	@Override
	public List<TranscriptEntry> getTranscript(AccountBean act)
			throws DbHelperException {
		ArrayList<String> list = new ArrayList<>();
		list.add("Transcript is unavailable at this time");
		return null;
	}
}
