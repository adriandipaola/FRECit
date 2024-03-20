import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import javax.swing.*;
import javax.swing.border.LineBorder;

//maybe change this * to the actual required classes to import; apparently it's "bad practice"
import org.apache.poi.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Main {

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//this sets a dimension variable to have W and H components of the screen size
    final static int screenHeight = screenSize.height;//this gets H and assigns it to a variable (oh these are global because we are considering that your screen size does not change)
    final static int screenWidth = screenSize.width;//this gets W of screen and assigns it to a variable

    final static int WIDTH = 1024;
    final static int HEIGHT = 638;

    static final java.awt.Color COOL_BLUE = new java.awt.Color(122, 30, 201);

    static final java.awt.Color COOL_YELLOW = new java.awt.Color(255, 219, 51);

    static final java.awt.Color COOL_RED = new java.awt.Color(120, 101, 230);
    final static java.awt.Font largeTitleFont = new java.awt.Font("League Spartan", java.awt.Font.BOLD, 25);
    final static java.awt.Font smallTitleFont = new java.awt.Font("League Spartan", java.awt.Font.BOLD, 16);

    final static java.awt.Font veryTitleFont = new java.awt.Font("League Spartan", Font.BOLD, 100);
    public static int inc = 0;

    static String userName;


    public static ArrayList<MyPanel> allPanels = new ArrayList<>();

    public static int filteredAppsLength = 0;

    public static JPanel tablePane = new JPanel();
    public static JPanel statusPane = new JPanel();
    public static JPanel updateBtn = new JPanel();
    public static JComboBox statusBox = new JComboBox();
    public static JSlider slider = new JSlider();

    public static int id;
    public static int totalHiredApps;
    /**
     *
     * @param //gender 'M' for male or 'N' for non-male, 'B' for both
     * @param //role 'F' for FREC, 'P' for Plant, 'E' for either
     * @return
     */


    public static void createPanelGroups(FC[][] FCMembers) {
        int[] groupValues = {0, 0, 0, 0, 0};
        for (int i = 0; i < 5; i++) {
            for (FC f : FCMembers[i]) {
                groupValues[i] += f.getGenderValue();
            }
            if (groupValues[i] == 3) {
                //do something
            }
            else if (groupValues[i] < 2) {
                //do something else
            }
        }


    }



    public static void updateStatus(int idNum, String newStatus, int cNum) {
        //Write to excel,
        //Re-update the table
        int rNum = idNum - 5;
        //int cNum = 14;

        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\alexn\\Queen's University\\GROUP-FREC - General\\FREC-App-Info.xlsx");
            Workbook wb = WorkbookFactory.create(fis);
            Sheet s = wb.getSheet("Sheet1");
            Row r = s.getRow(rNum);
            Cell c = r.createCell(cNum);
            if (cNum == 14) {
                c.setCellValue(newStatus);
            } else {
                c.setCellValue(Integer.valueOf(newStatus));
            }

            FileOutputStream fos = new FileOutputStream("C:\\Users\\alexn\\Queen's University\\GROUP-FREC - General\\FREC-App-Info.xlsx");
            wb.write(fos);
            wb.close();
        } catch (Exception e) {
            System.out.println("Read exception");
            e.printStackTrace();
        }
        System.out.println("Write complete");
    }



    public static void searchApp(String firstName, String lastName, Collection<Applicant> applicants) {
        //Search by first and last name,
        statusPane.removeAll();
        char statusOf = 'a';
        int ratingOf = 0;

        //Search by
        TreeSet<Applicant> filteredApps = new TreeSet<>();
        for (Applicant a : applicants) {
            if (firstName.equals(a.getFirstName().toLowerCase()) && lastName.equals(a.getLastName().toLowerCase())) {
                filteredApps.add(a);
                id = a.getId();
                statusOf = a.getStatus();
                ratingOf = a.getRating();

            }
        }

        String[][] appsDisplay = applicantListToArray(filteredApps);
        String columns[] = {"Name", "Last Name", "Gender","Email", "Phone #", "Student #", "Role", "Rating","Status"}; //ADD STATUS
        JTable jt = new JTable(appsDisplay,columns);
        jt.setDefaultEditor(Object.class, null);

        statusPane.add(jt);
        jt.setBounds(0, 0, WIDTH - 200, HEIGHT);
        JScrollPane sp = new JScrollPane(jt);
        sp.setPreferredSize(new Dimension(WIDTH - 250, HEIGHT-200));
        statusPane.add(sp);
        statusPane.setBackground(Color.white);
        statusPane.setLayout(new FlowLayout());

        statusPane.setBounds(0, 0, WIDTH - 200, HEIGHT);
        System.out.println("infoSummaryDone");

        JLabel stat = new JLabel("The status of the current applicant is: ");

        String genders[]={"Not Hired", "Hired"};
        statusBox.removeAll();
        statusBox = new JComboBox(genders);

        if(statusOf == 'H') {
            statusBox.setSelectedIndex(1);
        } else {
            statusBox.setSelectedIndex(0);
        }

        statusBox.setSize(90, 20);

        statusPane.add(stat);
        statusPane.add(statusBox);


        updateBtn.removeAll();
        updateBtn.setPreferredSize(new Dimension(100, 50));
        updateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateBtn.setBackground(COOL_BLUE);
        JLabel upd = new JLabel("Update");
        upd.setForeground(Color.white);
        updateBtn.setLayout(new GridBagLayout());
        updateBtn.add(upd);
        statusPane.add(updateBtn);


        JLabel emptyBox = new JLabel("");
        emptyBox.setPreferredSize(new Dimension(3000,0));
        statusPane.add(emptyBox);

        JLabel rateText = new JLabel("The rating of the curent applicant is: ");

        slider.removeAll();
        slider.setMinimum(0);
        slider.setMaximum(5);
        slider.setValue(ratingOf);
        slider.setOrientation(JSlider.HORIZONTAL);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        statusPane.add(rateText);
        statusPane.add(slider);


        statusPane.repaint();
        statusPane.revalidate();


    }

    public static void infoSummary(char gender, char role, char status, Collection<Applicant> applicants) {
        tablePane.removeAll();

        TreeSet<Applicant> filteredApps = new TreeSet<>();
        for (Applicant a : applicants) {
            if ((gender == 'B' || gender == a.getGender()) && (role == a.getRole() || role == 'B') && (status == a.getStatus() || status == 'E')) {
                filteredApps.add(a);
            }
        }
        String[][] appsDisplay = applicantListToArray(filteredApps);
        String columns[] = {"Name", "Last Name", "Gender","Email", "Phone #", "Student #", "Role", "Rating","Status"};

        JTable jt = new JTable(appsDisplay,columns);
        jt.setDefaultEditor(Object.class, null);

        //JScrollPane sp = new JScrollPane(jt);
        //pane.add(sp);
        tablePane.add(jt);
        jt.setBounds(0, 0, WIDTH - 200, HEIGHT);
        JScrollPane sp = new JScrollPane(jt);
        sp.setPreferredSize(new Dimension(WIDTH - 250, HEIGHT-200));
        tablePane.add(sp);
        tablePane.setBackground(Color.white);
        tablePane.setLayout(new FlowLayout());

        tablePane.setBounds(0, 0, WIDTH - 200, HEIGHT);
        System.out.println("infoSummaryDone");

        int numApps = filteredAppsLength;
        JLabel num = new JLabel("Showing " + numApps + " out of " + applicants.size() + " applicants");
        tablePane.add(num);

        tablePane.repaint();
        tablePane.revalidate();
    }


    /**
     * Gives the value of a cell inside an Excel sheet given the row and column number.
     * @param SheetName Name of the sheet being accessed
     * @param rNum row number (starting at 0)
     * @param cNum column number (starting at 0)
     * @return cell data at rNum and cNum as a String
     */
    public static String readExcel(String SheetName, int rNum, int cNum) {
        String data = "";
        try {
            FileInputStream fPath = new FileInputStream("C:\\Users\\alexn\\Queen's University\\GROUP-FREC - General\\FREC-App-Info.xlsx");//Put file path here, don't know how it will work with a shared file
            Workbook wb = WorkbookFactory.create(fPath);
            Sheet s = wb.getSheet(SheetName);
            Row r = s.getRow(rNum);
            Cell c = r.getCell(cNum);
            if (c.getCellType() == CellType.NUMERIC) {
                // If the cell contains a numeric value, convert it to a string
                data = String.valueOf(c.getNumericCellValue());
            } else {
                // If the cell contains a non-numeric value, read it as a string directly
                data = c.getStringCellValue();
            }
            return data;
        } catch(NullPointerException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Read Exception catch");
            e.printStackTrace();
            return null;
        }
    }

    public static Collection<Applicant> getApplicantsFromExcel(){
        totalHiredApps = 0;
        Collection<Applicant> applicants=new ArrayList<>();
        //this array will be used to temporarily store each applicant's data
        String[] applicantData = new String[Applicant.numAttributes+1];
        int row = 1;
        while (readExcel("Sheet1", row, 0) != null) {
            //first name, last name, gender, studentNumber, email, phone number, role
            applicantData[0] = readExcel("Sheet1", row, 6);
            applicantData[1] = readExcel("Sheet1", row, 7);
            applicantData[2] = readExcel("Sheet1", row, 8);
            applicantData[3] = readExcel("Sheet1", row, 10);
            applicantData[4] = readExcel("Sheet1", row, 11);
            applicantData[5] = readExcel("Sheet1", row, 12);
            applicantData[6] = readExcel("Sheet1", row, 13);
            applicantData[7] = readExcel("Sheet1", row, 14);

            //check status, if hired then increment the hired counter
            if (applicantData[7].charAt(0) == 'H') {
                totalHiredApps++;
            }

            applicantData[8] = readExcel("Sheet1", row, 0);

            double temp = Double.valueOf(applicantData[8]);
            String newValue = Integer.toString((int)temp);

            applicantData[9] = readExcel("Sheet1", row, 15);
            temp = Double.valueOf(applicantData[9]);
            String newValue2 = Integer.toString((int)temp);

            //change "role" column to a readable variable
            switch (applicantData[6]) {
                case "Only FREC":
                    applicantData[6] = "F";
                    break;
                case "Only Plant":
                    applicantData[6] = "P";
                    break;
                default:
                    applicantData[6] = "B";
                    break;
            }

            //finally after all data is collected make an applicant object!
            applicants.add(new Applicant(applicantData[0], applicantData[1], applicantData[2], applicantData[3], applicantData[4], applicantData[5], applicantData[6], applicantData[7], newValue2, newValue));
            row++;
        }


        System.out.println("getApplicantsFromExcel Method finished");
        return applicants;
    }

    public static String[][] applicantListToArray(Collection<Applicant> applicants) {
        String[][] appsToString = new String[applicants.size()][Applicant.numAttributes];
        int i = 0;
        for (Applicant a : applicants) {
            appsToString[i][0] = a.getFirstName();
            appsToString[i][1] = a.getLastName();
            appsToString[i][2] = "" + a.getGender();
            appsToString[i][3] = a.getEmail();
            appsToString[i][4] = a.getPhoneNumber();
            appsToString[i][5] = a.getStudentNumber();
            appsToString[i][6] = "" + a.getRole();
            appsToString[i][7] = "" + a.getRating();
            appsToString[i][8] = "" + a.getStatus();
            i++;
        }
        filteredAppsLength = appsToString.length;
        System.out.println("applicantListToArray finished");
        return appsToString;
    }

    public static ImageIcon scaleImage(ImageIcon icon, int width, int height)//for scaling the image
    {
        int newWidth = icon.getIconWidth();//sets the icon width to the
        int newHeight = icon.getIconHeight();

        if(icon.getIconWidth() > width)//changes width to desired width
        {
            newWidth = width;
            newHeight = (newWidth * icon.getIconHeight()) / icon.getIconWidth();
        }

        if(newHeight > height)//changes height to desired height
        {
            newHeight = height;
            newWidth = (icon.getIconWidth() * newHeight) / icon.getIconHeight();
        }
        return new ImageIcon(icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));//returns scaled image
    }

    public static ImageIcon removeBg (ImageIcon logo) {
        BufferedImage bi = getBufferedImageFromIcon(logo);

        for (int y = 0; y < bi.getHeight(); y++) {
            for (int x = 0; x < bi.getWidth(); x++) {
                int pixel = bi.getRGB(x, y);
                Color ogColour = new Color(pixel, true);
                if (ogColour.getRed() == 0 && ogColour.getGreen() == 0 && ogColour.getBlue() == 0) {
                    ogColour = new Color(COOL_BLUE.getRGB());
                    bi.setRGB(x, y, ogColour.getRGB());
                }
            }
        }

        logo = new ImageIcon(bi);

        return logo;
    }

    public static BufferedImage getBufferedImageFromIcon(Icon icon) {
        BufferedImage buffer = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        icon.paintIcon(new JLabel(), g, 0, 0);
        g.dispose();
        return buffer;
    }


    //Login page
    public static void main(String[] args) {
        ArrayList<Applicant> applicants = new ArrayList<>(getApplicantsFromExcel());

        //panel4 = infoSummary('M', 'F', applicants);
        ArrayList<JComponent> frameElements5 = new ArrayList<>();
        frameElements5.add(tablePane);
        //panel4.setVisible(true);

        //Start frontend code here.
        //Add ALL MyFrame components to frameElements array list. Make sure to set bounds or define layout!
        ArrayList<JComponent> frameElements1 = new ArrayList<>();
        ArrayList<JComponent> frameElements2 = new ArrayList<>();
        ArrayList<JComponent> frameElements3 = new ArrayList<>();
        ArrayList<JComponent> frameElements4 = new ArrayList<>();
        frameElements4.add(statusPane);

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

//        JLabel houseDisplay = new JLabel();//container for the image
//        Dimension size = houseDisplay.getPreferredSize();
//        //houseDisplay.setBackground(COOL_BLUE);
//        houseDisplay.setBounds(24, 12, size.width, size.height);
        home.setName("HOME");
        home.setLayout(null);
        //home.add(houseDisplay);
        home.setBackground(COOL_BLUE);

        red1.setBackground(COOL_RED);
        red1.setBounds(0, 4, 6, 42);
        home.add(red1);

        JLabel homeText = new JLabel("Apps");
        homeText.setForeground(java.awt.Color.WHITE);
        homeText.setFont(smallTitleFont);
        JPanel homeTextBox = new JPanel();
        homeTextBox.setLayout(new GridBagLayout());
        homeTextBox.setBounds(46, 0, 70, 50);

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

        JLabel faveText = new JLabel("Filter Apps");
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


        JLabel settingText = new JLabel("Schedule");
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
        JTextField searchBar = new JTextField("  first and last name...");
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


        ImageIcon searchIcon = removeBg(new ImageIcon("icons/search.png"));
        JLabel searchDisplay = new JLabel();//container for the image
        searchIcon = scaleImage(searchIcon, 50, 50);//scales the image
        searchDisplay.setIcon(searchIcon);//places it inside the label
        searchBtn.add(searchDisplay);

        Dimension size3 = searchDisplay.getPreferredSize();
        searchDisplay.setBounds(15, 14, size3.width, size3.height);
        searchBtn.setLayout(null);

        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel resorts = new JLabel("Search apps");
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
        JLabel faveName = new JLabel("Filter Apps");
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

        JPanel faveBox = new JPanel();
        faveBox.setBackground(java.awt.Color.WHITE);
        faveBox.setLayout(new GridBagLayout());
        faveBox.setBounds(650, 20, 100, 50);
        frameElements4.add(faveBox);

        //Settings stuff
        JLabel settingLabel = new JLabel("Schedule");
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

        String genders[]={"Any","Male", "NonMale"};
        JComboBox genderCB = new JComboBox(genders);

        JLabel genderText = new JLabel("What gender would you like to filter for? ");
        genderText.setBounds(150, 120, genderText.getPreferredSize().width, genderText.getPreferredSize().height);
        genderCB.setBounds(425, 120, 90, 20);
        frameElements2.add(genderText);
        frameElements2.add(genderCB);

        String[] roles = {"Any", "Either", "FREC", "Plant"};
        JLabel roleText = new JLabel("What role would you like to filter for? ");
        roleText.setBounds(150, 200, roleText.getPreferredSize().width, roleText.getPreferredSize().height);
        JComboBox rolesCB = new JComboBox(roles);
        rolesCB.setBounds(425, 200,90,20);
        frameElements2.add(roleText);
        frameElements2.add(rolesCB);

        String[] statuses = {"Any", "Hired", "Not Hired"};
        JLabel statusText = new JLabel("What status would you like to filter for? ");
        statusText.setBounds(150, 280, statusText.getPreferredSize().width, statusText.getPreferredSize().height);
        JComboBox statusCB = new JComboBox(statuses);
        statusCB.setBounds(425, 280,90,20);
        frameElements2.add(statusText);
        frameElements2.add(statusCB);

        JPanel pan2btn = new JPanel();
        pan2btn.setBounds(150, 440, 200, 75);
        pan2btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pan2btn.setBackground(COOL_BLUE);
        JLabel pan2BtnText = new JLabel("Enter");
        pan2BtnText.setForeground(Color.white);
        pan2btn.setLayout(new GridBagLayout());
        pan2btn.add(pan2BtnText);
        frameElements2.add(pan2btn);

        JLabel hiredCountText = new JLabel(totalHiredApps + " out of " + applicants.size() + " have been hired.");
        //hiredCountText.setLocation(150, 360);
        hiredCountText.setBounds(150, 360, hiredCountText.getPreferredSize().width + 50, hiredCountText.getPreferredSize().height);
        frameElements2.add(hiredCountText);

        MyPanel leftPanel = new MyPanel(-1, 0, 0, 200, HEIGHT, menuElements);
        MyPanel panel1 = new MyPanel(0, 200, 0, WIDTH - 200, HEIGHT, frameElements1);
        MyPanel panel2 = new MyPanel(1, 200, 0, WIDTH - 200, HEIGHT, frameElements2);
        MyPanel panel3 = new MyPanel(2, 200, 0, WIDTH - 200, HEIGHT, frameElements3);
        MyPanel report = new MyPanel(3, 200, 0, WIDTH - 200, HEIGHT, frameElements4);
        MyPanel panel5 = new MyPanel(4, 200, 0, WIDTH - 200, HEIGHT, frameElements5);

        allPanels.add(panel1);
        allPanels.add(panel2);
        allPanels.add(report);
        allPanels.add(leftPanel);
        allPanels.add(panel3);
        allPanels.add(panel5);

        frame.add(leftPanel);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(report);
        frame.add(panel5);
        panel5.setVisible(false);
        panel2.setVisible(false);
        report.setVisible(false);
        panel3.setVisible(false);
        panel1.setVisible(false);
        leftPanel.setVisible(true);


        updateBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Update status and table
                String newStatus = statusBox.getSelectedItem().toString();
                int newRating = slider.getValue();

                switch (newStatus) {
                    case "Hired" :
                        newStatus = "H";
                        break;
                    default:
                        newStatus = "N";
                        break;
                }

                //update the status if and only if the status is different than before
                if (applicants.get(id - 6).getStatus() != newStatus.charAt(0)) {
                    updateStatus(id, newStatus, 14);
                    applicants.get(id - 6).setStatus(newStatus.charAt(0));
                    if (newStatus.charAt(0) == 'H') {
                        totalHiredApps++;
                    } else {
                        totalHiredApps--;
                    }
                    hiredCountText.setText(totalHiredApps + " out of " + applicants.size() + " have been hired.");
                }
                if (applicants.get(id - 6).getRating() != newRating) {
                    updateStatus(id, String.valueOf(newRating), 15);
                    applicants.get(id - 6).setRating(newRating);
                }




            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                updateBtn.setBorder(new LineBorder(Color.BLACK));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateBtn.setBorder(new LineBorder(Color.white));
            }
        });
        pan2btn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                char gender;

                String genderStr = String.valueOf(genderCB.getSelectedItem());
                switch (genderStr) {
                    case "Male":
                        gender = 'M';
                        break;
                    case "Any":
                        gender = 'B';
                        break;
                    default:
                        gender = 'N';
                        break;
                }

                char role;
                String roleStr = String.valueOf(rolesCB.getSelectedItem());
                switch (roleStr) {
                    case "FREC":
                        role = 'F';
                        break;
                    case "Plant":
                        role = 'P';
                        break;
                    case "Any":
                        role = 'B';
                        break;
                    default:
                        role = 'E';
                        break;
                }

                char status;
                String statusStr = String.valueOf(statusCB.getSelectedItem());
                switch (statusStr) {
                    case "Hired":
                        status = 'H';
                        break;
                    case "Not Hired":
                        status = 'N';
                        break;
                    default:
                        status = 'E';
                        break;
                }


                infoSummary(gender, role, status, applicants);
                //Set a.status again.


                panel5.setVisible(true);
                panel2.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pan2btn.setBorder(new LineBorder(Color.BLACK));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pan2btn.setBorder(new LineBorder(Color.WHITE));
            }
        });

        searchBar.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //If you focus on text field:
                if (searchBar.getText().equals("  first and last name...")) {
                    searchBar.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //If you do not focus on text field:
                if (searchBar.getText().equals("  first and last name...") || searchBar.getText().equals("")) {
                    searchBar.setText("  first and last name...");
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
                panel5.setVisible(false);
                report.setVisible(false);
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
                panel5.setVisible(true);
                panel3.setVisible(false);
                panel5.setVisible(false);
                report.setVisible(false);

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
                panel5.setVisible(false);
                report.setVisible(false);
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
                panel5.setVisible(false);
                report.setVisible(true);

                String fullName = searchBar.getText();

                StringBuilder tempFirst = new StringBuilder();
                StringBuilder tempLast = new StringBuilder();
                Boolean isFirst = true;

                for (int i = 0; i < fullName.length(); i++) {
                    if (fullName.charAt(i) == ' ') {
                        isFirst = false;
                        continue;
                    }
                    if (isFirst) {
                        tempFirst.append(fullName.charAt(i));
                    } else {
                        tempLast.append(fullName.charAt(i));
                    }
                }

                String firstName = tempFirst.toString().toLowerCase();
                String lastName = tempLast.toString().toLowerCase();


                System.out.println(firstName);
                System.out.println(lastName);

                searchApp(firstName, lastName, applicants);
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

    }

}