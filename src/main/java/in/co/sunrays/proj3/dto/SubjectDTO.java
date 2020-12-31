package in.co.sunrays.proj3.dto;

public class SubjectDTO extends BaseDTO
{

	
	/**
     * Name of Subject
     */

	private String name;
	/**
     * Description of Subject
     */

	private String description;
	/**
     * 
     * CourseId of Subject
     */

	private long courseId;
	/**
     * Course Name of Subject
     */

	private String courseName;
	

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
