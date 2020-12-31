package in.co.sunrays.proj3.dto;

public class CourseDTO extends BaseDTO
{

	/**
     * Name of Course
     */

	private String name;
	/**
     * Description of Course
     */

	private String description;
	/**
     * Duration of Course
     */

	private String duration;

	
	
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
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
