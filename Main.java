import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//maybe change this * to the actual required classes to import; apparently it's "bad practice"
import org.apache.poi.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
     * Takes in an Excel file containing a list of applicants and using Apache POI reads the whole thing
     * and returns the applicants in code.
     * @param excelFile the file being read in
     * @return a collection of the applicants: kept as a collection in case it needs to be turned into a TreeSet
     */
    public static Collection<Applicant> getApplicantsFromExcel(String excelFile) {
        Collection<Applicant> applicants = new ArrayList<>();
        String[] applicantData = new String[5]; //this array will be used to temporarily store each applicant's data
        try {
            //create input stream to Excel file
            //this will need to be changed once SharePoint file storage is implemented
            FileInputStream fis = new FileInputStream(excelFile);
            //Create Workbook instance for xlsx/xls file input stream
            Workbook workbook = new XSSFWorkbook(fis); //maybe check if file ends with ".xslx" or ".xls" or smthg else

            Sheet sheet = workbook.getSheetAt(0);
            //iterate through each row inside the sheet!
            for (Row row : sheet) {
                //iterate through all the cells in a row now
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    //Get the Cell object
                    Cell cell = cellIterator.next();
                    //get the column letter: we need this to figure out what data is in the column
                    //since we likely will not use column AA or later, we can just get the char value of the column
                    //TODO check if this shit returns in lower case, cause if it does it will destroy the code
                    char cellColumn = CellReference.convertNumToColString(cell.getColumnIndex()).charAt(0);
                    //now add the data into the applicant data array at the correct index
                    applicantData[cellColumn - 'A'] = cell.getStringCellValue().trim();
                }
                //finally after all data is collected make an applicant object!
                //TODO FIGURE OUT HOW GROUPS WORK!!!!
                applicants.add(new Applicant(applicantData[0], applicantData[1], Integer.parseInt(applicantData[2]), applicantData[3]));
            }
        } catch (IOException e) { //maybe throw IOException instead? then catch FileNotFoundException
            e.printStackTrace();
        }
        return applicants;
    }


    /**
     * This method will create a new ApplicantGroup object and populate it with Applicant objects
     *
     * @param groupID       id of the group; no two groups can have the same id
     *                      id will probably be the group's row in the Excel file?
     * @param numApplicants number of applicants in the group
     * @return new ApplicantGroup object containing the registered applicants
     */
    public static ApplicantGroup addApplicantGroup(int groupID, int numApplicants) {
        Applicant[] newGroup = new Applicant[numApplicants];
        for (int i = 0; i < newGroup.length; i++) {
            //TODO FIGURE OUT HOW GROUPS WORK!!!! THIS METHOD DOES NOT DO ANYTHING RN
        }
        return new ApplicantGroup(groupID, newGroup);
    }
}
