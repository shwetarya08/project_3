package in.co.sunrays.proj3.dto;

import java.util.Date;

public class TimeTableDTO  extends BaseDTO
{

	/**
     * CourseId of TimeTable
     */

	private long courseId;

	/**
     *Course Name  of TimeTable
     */

	private String courseName;

	/**
     *SubjectId  of TimeTable
     */

	private long subjectId;

	/**
     *Subject Name  of TimeTable
     */

	private String subjectName; 

	/**
     * Semester of TimeTable
     */

	private String semester;

	/**
     *ExamDate  of TimeTable
     */

	private Date examDate;

	/**
     *Time  of TimeTable
     */

	private String time;

	
	
	
	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
