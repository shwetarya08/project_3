package in.co.sunrays.proj3.dto;

public class MarksheetDTO extends BaseDTO
{
	private String rollNo;
	private long studentId;
	private String name;
	private int physics;
	private int chemistry;
	private int maths;
	
	
	 /**
     * Physics marks of Student
     */
    private String physicsS;
    /**
     * Chemistry marks of Student
     */
    private String chemistryS;
    /**
     * Mathematics marks of Student
     */
    private String mathsS;

    /**
     * accessor
     */


	
	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPhysics() {
		return physics;
	}

	public void setPhysics(int physics) {
		this.physics = physics;
	}

	public int getChemistry() {
		return chemistry;
	}

	public void setChemistry(int chemistry) {
		this.chemistry = chemistry;
	}

	public int getMaths() {
		return maths;
	}

	public void setMaths(int maths) {
		this.maths = maths;
	}

	
	
	public String getPhysicsS() {
		return physicsS;
	}

	public void setPhysicsS(String physicsS) {
		this.physicsS = physicsS;
	}

	public String getChemistryS() {
		return chemistryS;
	}

	public void setChemistryS(String chemistryS) {
		this.chemistryS = chemistryS;
	}

	public String getMathsS() {
		return mathsS;
	}

	public void setMathsS(String mathsS) {
		this.mathsS = mathsS;
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
