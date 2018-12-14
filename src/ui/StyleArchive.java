package ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class StyleArchive {


    protected static String font = "Arial Nova";

    //FONTS
    public static final Font TITLEFONT = new Font(font, Font.PLAIN, 40);
    public static final Font HEADER = new Font(font, Font.BOLD, 26);
    public static final Font NORMAL = new Font(font, Font.PLAIN, 14);
    public static final Font SMALL_BUTTON = new Font(font, Font.PLAIN, 12);

    //COLORS
    public static final Color COLOR_BACKGROUND = Color.WHITE;

    //BORDERS
    public static final Border UNDER_LINE_BORDER = BorderFactory.createMatteBorder(0,0,4,0, Color.LIGHT_GRAY);;
}
