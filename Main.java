//Remember to include the ApachePOI Library (MAVEN!)
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World...");

        //TODO Initialize an array of Applicant objects and make an ApplicantGroup object out of it
        //Applicant info will be stored in an Excel file with info separated by columns
        //example:
        int groupID = 1;
        int numApplicants = 1;
        ApplicantGroup grp = addApplicantGroup(groupID, numApplicants);
    }

    /**
     * This method will create a new ApplicantGroup object and populate it with Applicant objects
     * @param groupID id of the group; no two groups can have the same id
     * @param numApplicants number of applicants in the group
     * @return new ApplicantGroup object containing the registered applicants
     */
    public static ApplicantGroup addApplicantGroup(int groupID, int numApplicants) {
        Applicant[] newGroup = new Applicant[numApplicants];
        for (int i = 0; i < newGroup.length; i++) {
            //TODO read from Excel file and assign values to the following variables
            String name = "John Doe";
            String email = "22NKJ@queensu.ca";

            newGroup[i] = new Applicant(groupID, name, email);
        }
        return new ApplicantGroup(groupID, newGroup);
    }
}
