public class Applicant implements Comparable<Applicant> { //this class might have to inherit ApplicantGroup
    /*
    id, studentNumber, groupID, name, email could all be turned to constant variables
    BUT... maybe keep them this way to maintain editability? Will have to ask Chloe about this
     */
    public static int numAttributes = 9; //number of attributes for each applicant

    private String firstName;
    private String lastName;
    private String email;
    private String studentNumber; //might have to be a string instead: what if student number starts w/ 0?
    private String phoneNumber;
    private char gender;
    private char role;
    private char status;
    private int id;
    private int rating;

    //TODO add whatever other fields are needed

    public Applicant(String firstName, String lastName, char gender, String email, String phoneNumber, char role,  String studentNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.studentNumber = studentNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    //this constructor is the same as the one above, but takes in gender and role as Strings
    public Applicant(String firstName, String lastName, String gender, String studentNumber, String email, String phoneNumber, String role, String status, String rating,String id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender.charAt(0);
        this.studentNumber = studentNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role.charAt(0);
        this.status = status.charAt(0);
        this.rating = Integer.valueOf(rating);
        this.id = Integer.valueOf(id);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public char getRole() {
        return role;
    }

    public char getGender() {
        return gender;
    }

    public char getStatus() { return status; }

    public void setStatus(char status) { this.status = status;}

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getRating() {return rating;}
    public void setRating(int rating) {this.rating = rating;}

    public int getId() { return id; }

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
