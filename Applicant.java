public class Applicant {
    private char id;
    private int groupID;
    private String name;
    private String email;
    //TODO add whatever other fields are needed

    Applicant(int groupID, String name, String email){
        this.groupID = groupID;
        this.name = name;
        this.email = email;
    }

    public void createID(int id) {
        this.id = (char) (id + 'A');
    }

    public String getID() {
        return groupID + "-" + id;
    }

}
