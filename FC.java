public class FC {
    private char gender;
    private String username;
    private int genderValue;

    public FC (String username, char gender) {
        this.username = username;
        this.gender = gender;
        if (gender == 'M') {
            genderValue = 1;
        } else {
            genderValue = 0;
        }
    }


    public char getGender() {return gender;}

    public String getUsername() {return username;}

    public int getGenderValue() {
        return genderValue;
    }
}
