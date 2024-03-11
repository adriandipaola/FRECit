import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MyPanel extends JLayeredPane {
    //Instance variables
    ArrayList<JComponent> elements = new ArrayList(); //stores all components

    int x;
    int y;
    int width;
    int height;

    int id;

    //MyPanel contructor
    MyPanel(int id, int x, int y, int width, int height, ArrayList<JComponent> elements) {

        //assigns instance variables
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;

        //Sets size, location on screen
        setSize(width , height);
        setLocation(x, y);
        setLayout(null);

        JPanel bg = new JPanel();
        bg.setBounds(0, 0, width, height);
        bg.setBackground(Color.WHITE);
        elements.add(bg);

        //Adds all components to the panel
        for (JComponent element : elements) {
            this.elements.add(element);
            add(element);
        }

    }
}
