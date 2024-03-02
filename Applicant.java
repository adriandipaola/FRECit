public class Applicant implements Comparable<Applicant> { //this class might have to inherit ApplicantGroup
    /*
    id, studentNumber, groupID, name, email could all be turned to constant variables
    BUT... maybe keep them this way to maintain editability? Will have to ask Chloe about this
     */
    private char id;
    private int groupID;
    private String firstName;
    private String lastName;
    private String email;
    private int studentNumber; //might have to be a string instead: what if student number starts w/ 0?
    //TODO add whatever other fields are needed

    Applicant(int groupID, String firstName, String lastName, int studentNumber, String email){
        this.groupID = groupID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.email = email;
    }

    //temporary constructor before we figure out how group Ids are going to work
    Applicant(String firstName, String lastName, int studentNumber, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.email = email;
    }

    public void createID(int id) {
        this.id = (char) (id + 'A');
    }

    public String getID() {
        return groupID + "-" + id;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public int getGroupID() {
        return groupID;
    }

    /**
     * Default comparison method: comparing applicants by last name
     * @param a the object to be compared.
     * @return the last names compared to each other -- this will sort alphabetically by last name
     */
    @Override
    public int compareTo(Applicant a) {
        return this.lastName.compareTo(a.lastName);
    }
}
