package in.co.sunrays.proj3.dto;

import java.util.Date;

public class FacultyDTO extends BaseDTO
{

	 /**
     * CollegeId of Faculty
     */
	private long collegeId;
	/**
     * CourseId of Faculty
     */
	private long courseId ;
	/**
     * Course Name of Faculty
     */
	private String courseName;
	/**
     *First Name  of Faculty
     */
	private String firstName;
	/**
     *Last Name  of Faculty
     */
	private String lastName;
	/**
     *SubjectId  of Faculty
     */
	private long subjectId;
	/**
     *Subject Name  of Faculty
     */
	private String subjectName;
	/**
     *College Name  of Faculty
     */
	private String collegeName;
	/**
     *Qualification  of Faculty
     */
	private String qualification;
	/**
     *EmailId  of Faculty
     */
	private String emailId;
	/**
     *Date of Birth  of Faculty
     */
	private Date dob;
	/**
     *MobileNo. of Faculty
     */
	private String mobNo;

	
	
	
	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
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
