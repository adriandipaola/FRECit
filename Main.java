import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
import java.time.LocalTime;

//maybe change this * to the actual required classes to import; apparently it's "bad practice"
import org.apache.poi.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Main {

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//this sets a dimension variable to have W and H components of the screen size
    final static int screenHeight = screenSize.height;//this gets H and assigns it to a variable (oh these are global because we are considering that your screen size does not change)
    final static int screenWidth = screenSize.width;//this gets W of screen and assigns it to a variable

    final static int WIDTH = 1024;
    final static int HEIGHT = 638;

    static final java.awt.Color COOL_BLUE = new java.awt.Color(30, 162, 236);

    static final java.awt.Color COOL_YELLOW = new java.awt.Color(255, 219, 51);

    static final java.awt.Color COOL_RED = new java.awt.Color(227, 58, 54);
    final static java.awt.Font largeTitleFont = new java.awt.Font("League Spartan", java.awt.Font.BOLD, 25);
    final static java.awt.Font smallTitleFont = new java.awt.Font("League Spartan", java.awt.Font.BOLD, 16);

    final static java.awt.Font veryTitleFont = new java.awt.Font("League Spartan", Font.BOLD, 100);
    public static int inc = 0;

    static String userName;

    public static JPanel grid2 = new JPanel();

    public static ArrayList<MyPanel> allPanels = new ArrayList<>();

    public static Collection<Applicant> getApplicantsFromExcel(String excelFile){
        Collection<Applicant> applicants=new ArrayList<>();
        String[]applicantData=new String[5]; //this array will be used to temporarily store each applicant's data
        try{
            //create input stream to Excel file
            //this will need to be changed once SharePoint file storage is implemented
            FileInputStream fis=new FileInputStream(excelFile);
            //Create Workbook instance for xlsx/xls file input stream
            Workbook workbook=new XSSFWorkbook(fis); //maybe check if file ends with ".xslx" or ".xls" or smthg else

            Sheet sheet=workbook.getSheetAt(0);
            //iterate through each row inside the sheet!
            for(Row row:sheet){
                //iterate through all the cells in a row now
                Iterator<Cell> cellIterator=row.cellIterator();
                while(cellIterator.hasNext()){

                    //Get the Cell object
                    Cell cell=cellIterator.next();
                    //get the column letter: we need this to figure out what data is in the column
                    //since we likely will not use column AA or later, we can just get the char value of the column
                    //TODO check if this shit returns in lower case, cause if it does it will destroy the code
                    char cellColumn=CellReference.convertNumToColString(cell.getColumnIndex()).charAt(0);
                    //now add the data into the applicant data array at the correct index
                    applicantData[cellColumn-'A']=cell.getStringCellValue().trim();
                }
                //finally after all data is collected make an applicant object!
                //TODO FIGURE OUT HOW GROUPS WORK!!!!
                applicants.add(new Applicant(applicantData[0],applicantData[1],Integer.parseInt(applicantData[2]),applicantData[3]));
            }
        }catch(IOException e){ //maybe throw IOException instead? then catch FileNotFoundException
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
    public static ApplicantGroup addApplicantGroup(int groupID,int numApplicants){
        Applicant[]newGroup=new Applicant[numApplicants];
        for(int i=0;i<newGroup.length;i++){
            //TODO FIGURE OUT HOW GROUPS WORK!!!! THIS METHOD DOES NOT DO ANYTHING RN
        }
        return new ApplicantGroup(groupID,newGroup);
    }

    //Login page
    public static void main(String[] args) {
        //Start frontend code here.

        //Add ALL MyFrame components to frameElements array list. Make sure to set bounds or define layout!
        ArrayList<JComponent> frameElements1 = new ArrayList<>();
        ArrayList<JComponent> frameElements2 = new ArrayList<>();
        ArrayList<JComponent> frameElements3 = new ArrayList<>();
        ArrayList<JComponent> frameElements4 = new ArrayList<>();
        ArrayList<JComponent> menuElements = new ArrayList<>();

        //MAIN JFRAME
        JFrame frame = new JFrame("FRECit");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocation(screenWidth / 4, screenHeight / 4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(WIDTH, HEIGHT);

        //FILLING MENU ITEMS
        //CHECK GUI DEMO PROJECT FOR UNCERTAINTIES, THIS JUST CREATES GUI
        JPanel home = new JPanel();
        JPanel faves = new JPanel();
        JPanel settingBtn = new JPanel();
        JPanel red1 = new JPanel();
        JPanel red2 = new JPanel();
        JPanel red3 = new JPanel();

        JLabel name = new JLabel("FRECit");
        JPanel titlePane = new JPanel();
        titlePane.setBounds(0, 15, 200, 30);
        titlePane.setBackground(COOL_BLUE);
        titlePane.setLayout(new GridBagLayout());
        titlePane.add(name);
        name.setFont(largeTitleFont);
        name.setForeground(java.awt.Color.WHITE);
        menuElements.add(titlePane);

        home.setBounds(0, 85, 250, 50);
        home.setBackground(COOL_BLUE);
        menuElements.add(home);

        JLabel houseDisplay = new JLabel();//container for the image
        Dimension size = houseDisplay.getPreferredSize();
        houseDisplay.setBounds(24, 12, size.width, size.height);
        home.setName("HOME");
        home.setLayout(null);
        home.add(houseDisplay);

        red1.setBackground(COOL_RED);
        red1.setBounds(0, 4, 6, 42);
        home.add(red1);

        JLabel homeText = new JLabel("Home");
        homeText.setForeground(java.awt.Color.WHITE);
        homeText.setFont(smallTitleFont);
        JPanel homeTextBox = new JPanel();
        homeTextBox.setLayout(new GridBagLayout());
        homeTextBox.setBounds(50, 0, 70, 50);

        homeTextBox.add(homeText);
        home.add(homeTextBox);
        homeTextBox.setName("HOME BOX");
        homeTextBox.setBackground(COOL_BLUE);


        faves.setBounds(0, 135, 250, 50);
        faves.setBackground(COOL_BLUE);
        menuElements.add(faves);

        JLabel starDisplay = new JLabel();//container for the image

        Dimension size1 = starDisplay.getPreferredSize();
        starDisplay.setBounds(20, 12, size1.width, size1.height);
        faves.setLayout(null);
        faves.add(starDisplay);

        red2.setBackground(COOL_RED);
        red2.setBounds(0, 4, 6, 42);
        faves.add(red2);
        red2.setVisible(false);

        JLabel faveText = new JLabel("Favourites");
        faveText.setForeground(java.awt.Color.WHITE);
        faveText.setFont(smallTitleFont);
        JPanel faveTextBox = new JPanel();
        faveTextBox.setLayout(new GridBagLayout());
        faveTextBox.setBounds(50, 0, 105, 50);
        faveTextBox.setBackground(COOL_BLUE);
        faveTextBox.add(faveText);
        faves.add(faveTextBox);

        settingBtn.setBounds(0, 185, 250, 50);
        settingBtn.setBackground(COOL_BLUE);
        menuElements.add(settingBtn);

        JLabel gearDisplay = new JLabel();//container for the image

        Dimension size2 = gearDisplay.getPreferredSize();
        gearDisplay.setBounds(23, 14, size2.width, size2.height);
        settingBtn.setLayout(null);
        settingBtn.add(gearDisplay);

        red3.setBackground(COOL_RED);
        red3.setBounds(0, 4, 6, 42);
        settingBtn.add(red3);
        red3.setVisible(false);


        JLabel settingText = new JLabel("Settings");
        settingText.setForeground(java.awt.Color.WHITE);
        settingText.setFont(smallTitleFont);
        JPanel settingTextBox = new JPanel();
        settingTextBox.setLayout(new GridBagLayout());
        settingTextBox.setBounds(50, 0, 88, 50);
        settingTextBox.setBackground(COOL_BLUE);
        settingTextBox.add(settingText);
        settingBtn.add(settingTextBox);

        JPanel bg = new JPanel();
        bg.setBounds(0, 0, 200, HEIGHT);
        bg.setBackground(COOL_BLUE);
        menuElements.add(bg);

        home.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        faves.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        settingBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        //FRAME ELEMENTS 2
        JTextField searchBar = new JTextField("  enter a ski resort...");
        searchBar.setForeground(COOL_BLUE);
        searchBar.setFont(largeTitleFont);
        searchBar.setBorder(new LineBorder(COOL_BLUE, 2));
        searchBar.setBounds(50, 50, 400, 75);
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBounds(50, 90, WIDTH - 450, 64);
        searchPanel.add(searchBar);
        frameElements1.add(searchPanel);

        JPanel searchBtn = new JPanel();
        searchBtn.setBounds(WIDTH - 400, 90, 80, 64);
        searchBtn.setBackground(COOL_BLUE);
        frameElements1.add(searchBtn);

        JLabel searchDisplay = new JLabel();//container for the image
        searchBtn.add(searchDisplay);

        Dimension size3 = searchDisplay.getPreferredSize();
        searchDisplay.setBounds(15, 14, size3.width, size3.height);
        searchBtn.setLayout(null);

        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel resorts = new JLabel("Search resorts");
        resorts.setFont(largeTitleFont);
        resorts.setForeground(COOL_BLUE);
        JPanel resortPanel = new JPanel();
        resortPanel.setBackground(java.awt.Color.WHITE);
        resortPanel.setLayout(new GridBagLayout());
        resortPanel.setBounds(0, 0, 200, 50);
        resortPanel.add(resorts);
        JPanel line = new JPanel();
        line.setBounds(11, 46, 178, 5);
        line.setBackground(COOL_RED);
        frameElements1.add(line);
        frameElements1.add(resortPanel);


        //FAVE ONE NOW:
        JLabel faveName = new JLabel("Favourites");
        faveName.setFont(largeTitleFont);
        faveName.setForeground(COOL_BLUE);
        JPanel favePanel = new JPanel();
        favePanel.setBackground(java.awt.Color.WHITE);
        favePanel.setLayout(new GridBagLayout());
        favePanel.setBounds(0, 0, 200, 50);
        favePanel.add(faveName);
        JPanel line3 = new JPanel();
        line3.setBounds(11, 46, 178, 5);
        line3.setBackground(COOL_RED);
        frameElements2.add(line3);
        frameElements2.add(favePanel);


        grid2.setBackground(java.awt.Color.WHITE);
        grid2.setLayout(new GridLayout(0, 1));

        System.out.println(userName);


        grid2.setBorder(new LineBorder(COOL_BLUE, 2));
        grid2.setBounds(50, 75, WIDTH - 325, HEIGHT - 175);
        frameElements2.add(grid2);

        JLabel resortName = new JLabel();
        resortName.setFont(largeTitleFont);
        resortName.setForeground(COOL_BLUE);
        JPanel resortNamePanel = new JPanel();
        resortNamePanel.setBackground(java.awt.Color.WHITE);
        resortNamePanel.setLayout(new GridBagLayout());
        resortNamePanel.setBounds(0, 0, 200, 50);
        resortNamePanel.add(resortName);
        JPanel line2 = new JPanel();
        line2.setBounds(11, 46, 178, 5);
        line2.setBackground(COOL_RED);
        frameElements4.add(line2);
        frameElements4.add(resortNamePanel);

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(3, 2));
        grid.setBackground(java.awt.Color.WHITE);
        grid.setBounds(50, 75, WIDTH - 325, HEIGHT - 175);

        JPanel snowStuff = new JPanel();
        snowStuff.setLayout(new GridLayout(3, 1));

        JPanel hourlyStuff = new JPanel();
        hourlyStuff.setLayout(new GridLayout(5, 1));

        JPanel dailyStuff = new JPanel();
        dailyStuff.setLayout(new GridLayout(3, 1));


        JLabel snow1 = new JLabel("Top snow depth: ");
        JLabel snow2 = new JLabel("Fresh snowfall: ");
        JLabel snow3 = new JLabel("Last snowfall date: ");
        snowStuff.add(snow1);
        snowStuff.add(snow2);
        snowStuff.add(snow3);

        JLabel hour1 = new JLabel("1");
        JLabel hour2 = new JLabel("2");
        JLabel hour3 = new JLabel("3");
        JLabel hour4 = new JLabel("4");
        JLabel hour5 = new JLabel("5");
        hourlyStuff.add(hour1);
        hourlyStuff.add(hour2);
        hourlyStuff.add(hour3);
        hourlyStuff.add(hour4);
        hourlyStuff.add(hour5);


        JLabel day1 = new JLabel("1");
        JLabel day2 = new JLabel("2");
        JLabel day3 = new JLabel("3");
        dailyStuff.add(day1);
        dailyStuff.add(day2);
        dailyStuff.add(day3);


        JLabel snow = new JLabel("  Snow conditions");
        snow.setFont(largeTitleFont);
        snow.setForeground(COOL_BLUE);

        JLabel hourly = new JLabel("  Hourly weather");
        hourly.setFont(largeTitleFont);
        hourly.setForeground(COOL_BLUE);

        JLabel daily = new JLabel("  Daily weather");
        daily.setFont(largeTitleFont);
        daily.setForeground(COOL_BLUE);

        JPanel snowy = new JPanel();
        snowy.setLayout(new GridLayout(1, 2));
        snowy.setBorder(new LineBorder(COOL_BLUE, 2));
        snowy.add(snow);
        snowy.add(snowStuff);

        JPanel hourWeather = new JPanel();
        hourWeather.setLayout(new GridLayout(1, 2));
        hourWeather.setBorder(new LineBorder(COOL_BLUE, 2));
        hourWeather.add(hourly);
        hourWeather.add(hourlyStuff);

        JPanel dailyWeatherPane = new JPanel();
        dailyWeatherPane.setLayout(new GridLayout(1, 2));
        dailyWeatherPane.setBorder(new LineBorder(COOL_BLUE, 2));
        dailyWeatherPane.add(daily);
        dailyWeatherPane.add(dailyStuff);

        grid.add(snowy);
        grid.add(hourWeather);
        grid.add(dailyWeatherPane);
        frameElements4.add(grid);


        String txt = "Favourite";
        String utxt = String.join("\u0332", txt.split("", -1));
        JLabel favourite = new JLabel(utxt);
        favourite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        favourite.setFont(smallTitleFont);
        favourite.setForeground(COOL_BLUE);

        JPanel faveBox = new JPanel();
        faveBox.setBackground(java.awt.Color.WHITE);
        faveBox.setLayout(new GridBagLayout());
        faveBox.setBounds(650, 20, 100, 50);
        faveBox.add(favourite);
        frameElements4.add(faveBox);

        //Settings stuff
        JLabel settingLabel = new JLabel("Settings");
        settingLabel.setFont(largeTitleFont);
        settingLabel.setForeground(COOL_BLUE);
        JPanel settingPanel = new JPanel();
        settingPanel.setBackground(java.awt.Color.WHITE);
        settingPanel.setLayout(new GridBagLayout());
        settingPanel.setBounds(0, 0, 200, 50);
        settingPanel.add(settingLabel);
        JPanel line4 = new JPanel();
        line4.setBounds(11, 46, 178, 5);
        line4.setBackground(COOL_RED);
        frameElements3.add(line4);
        frameElements3.add(settingPanel);

        JPanel logPane = new JPanel();
        logPane.setBounds(670, 550, 100, 50);
        logPane.setBackground(java.awt.Color.white);
        frameElements3.add(logPane);


        MyPanel leftPanel = new MyPanel(-1, 0, 0, 200, HEIGHT, menuElements);
        MyPanel panel1 = new MyPanel(0, 200, 0, WIDTH - 200, HEIGHT, frameElements1);
        MyPanel panel2 = new MyPanel(1, 200, 0, WIDTH - 200, HEIGHT, frameElements2);
        MyPanel panel3 = new MyPanel(2, 200, 0, WIDTH - 200, HEIGHT, frameElements3);
        MyPanel report = new MyPanel(3, 200, 0, WIDTH - 200, HEIGHT, frameElements4);


        allPanels.add(panel1);
        allPanels.add(panel2);
        allPanels.add(report);
        allPanels.add(leftPanel);
        allPanels.add(panel3);

        frame.add(leftPanel);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(report);
        panel2.setVisible(false);
        report.setVisible(false);
        panel3.setVisible(false);

        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //If you focus on text field:
                if (searchBar.getText().equals("  enter a ski resort...")) {
                    searchBar.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //If you do not focus on text field:
                if (searchBar.getText().equals("  enter a ski resort...") || searchBar.getText().equals("")) {
                    searchBar.setText("  enter a ski resort...");
                }
            }
        });

        //Home menu button listeners
        home.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Adjust panels visible
                red1.setVisible(true);
                red2.setVisible(false);
                red3.setVisible(false);
                panel1.setVisible(true);
                panel2.setVisible(false);
                panel3.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //Favourite button listeners
        faves.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //adjust what panels are visible
                red1.setVisible(false);
                red2.setVisible(true);
                red3.setVisible(false);
                panel1.setVisible(false);
                panel2.setVisible(true);
                panel3.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //Settings button listener
        settingBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Adjusts what frames are visible
                red1.setVisible(false);
                red2.setVisible(false);
                red3.setVisible(true);
                panel1.setVisible(false);
                panel2.setVisible(false);
                panel3.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //Search button listener
        searchBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //Adjust panel visibility
                panel1.setVisible(false);
                panel2.setVisible(false);
                panel3.setVisible(false);
                report.setVisible(true);
                resortName.setText(searchBar.getText());
                //Adjust text that displays snow details
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //Favourite button mouse listener
        favourite.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //Panel visibility
                panel1.setVisible(false);
                panel2.setVisible(false);
                panel3.setVisible(false);
                report.setVisible(true);



            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

}
//ApplicantGroup grp = addApplicantGroup(groupID, numApplicants);

//System.out.println(ReadExcel("Sheet1", 6, 1));


/**
 * Takes in an Excel file containing a list of applicants and using Apache POI reads the whole thing
 * and returns the applicants in code.
 *
 * @param excelFile the file being read in
 * @return a collection of the applicants: kept as a collection in case it needs to be turned into a TreeSet
 */
    /*
    public String ReadExcel(String SheetName, int rNum, int cNum) {
        String data = "";
        try {
            FileInputStrem fPath = new FileInputStream("https://queensuca-my.sharepoint.com/personal/22bmb7_queensu_ca/Documents/FREC.xlsx?web=1");//Put file path here, don't know how it will work with a shared file
            Workbook wb = WorknookFactory.create(fPath);
            Sheet s = wb.getSheet(SheetName);
            Row r = s.getRow(rNum);
            Cell c = r.getCell(cNum);
            data = c.getStringCellValue();

        } catch (Exception e) {
            System.out.println("Read Exception catch");
        }
        return data; //Returns cell data at rNum and cNum
    }

     */
