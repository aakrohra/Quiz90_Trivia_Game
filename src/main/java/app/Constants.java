package app;

import java.awt.*;

/**
 * Defines project constants.
 */

public class Constants {
    // Frame constants
    public static final int FRAMEHEIGHT = 720;
    public static final int FRAMEWIDTH = FRAMEHEIGHT * 16 / 9;
    public static final int PANELHEIGHT = FRAMEHEIGHT / 8;
    public static final int PANELWIDTH = FRAMEWIDTH / 100;
    public static final int QUESTIONFONTSIZE = FRAMEHEIGHT / 20;
    public static final int TITLEFONTSIZE = FRAMEHEIGHT / 18;
    public static final int QUESTIONMARGINDIVISOR = 20;

    // Button constants
    public static final int BUTTONWIDTH = FRAMEWIDTH / 3;
    public static final int BUTTONHEIGHT = BUTTONWIDTH / 3;
    public static final int BUTTONMARGIN = BUTTONHEIGHT / 5;
    public static final int BUTTONFONTSIZE = QUESTIONFONTSIZE * 2 / 3;
    public static final int MARGINS = 10;

    // Other Button constants
    public static final int BUTTON1WIDTH = 200;
    public static final int BUTTON1HEIGHT = 50;
    public static final int TEXTPANELWIDTH = 400;
    public static final int TEXTPANELHEIGHT = 100;

    // Combobox constants
    public static final int COMBOBOXWIDTH = BUTTONWIDTH;
    public static final int COMBOBOXHEIGHT = BUTTONHEIGHT / 3;
    public static final int COMBOBOXFONTSIZE = BUTTONFONTSIZE * 8 / 10;

    // Placeholder text
    public static final String USERNAMEPLACEHOLDER = "Enter your username here...";
    public static final int POSITIONX = 5;
    public static final int POSITIONY = 7;
    public static final int FIELDX = 500;
    public static final int FIELDY = 25;
    
    // Font
    public static final String FONTSTYLE = "Tahoma";

    // UI Spacers
    public static final int STRUTSMALLSPACER = 20;

    // Box Constants
    public static final int BOXX = 400;
    public static final int BOXY = 100;

    // UI Colours
    public static final Color BGCOLOUR = new Color(56, 113, 194);
    public static final Color LIGHTERBGCOLOUR = new Color(111, 161, 232);
    public static final Color CORRECTGREENBG = new Color(102, 191, 57);
    public static final Color INCORRECTREDBG = new Color(255, 51, 85);

    // Grid Coordinates
    public static final int THREE = 3;
    public static final int FOUR = 4;

    // Normal Play
    public static final String[] CATEGORIES = {
        "Animals",
        "Art",
        "Celebrities",
        "Entertainment: Board Games",
        "Entertainment: Books",
        "Entertainment: Animations & Cartoons",
        "Entertainment: Comics",
        "Entertainment: Film",
        "Entertainment: Japanese Anime & Manga",
        "Entertainment: Music",
        "Entertainment: Musicals & Theatres",
        "Entertainment: Television",
        "Entertainment: Video Games",
        "General Knowledge",
        "Geography",
        "History",
        "Mythology",
        "Politics",
        "Science & Nature",
        "Science: Computers",
        "Science: Gadgets",
        "Science: Mathematics",
        "Sports",
        "Vehicles",
    };
    public static final String[] DIFFICULTIES = {"Easy", "Medium", "Hard"};
    public static final Integer[] NUM_QUESTION = {5, 10, 15, 20};

    public static final int RANDOMKEYSIZE = 10;

}
