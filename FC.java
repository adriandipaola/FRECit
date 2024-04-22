public class FC {
    private char gender;
    private String username;
    private int panelNumber;
    private int rowOnSheet;

    public FC (String username, char gender, int rowOnSheet) {
        this.username = username;
        this.gender = gender;
        this.rowOnSheet = rowOnSheet;
    }

    public void setPanelNumber(int panelNumber) {
        this.panelNumber = panelNumber;
    }

    public char getGender() {return gender;}

    public String getUsername() {return username;}

    public int getRowOnSheet() {
        return rowOnSheet;
    }


}
