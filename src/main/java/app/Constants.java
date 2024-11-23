package app;

import java.awt.Color;
/**
 * Defines project constants.
 */
public class Constants {
    // Frame constants
    public static final int FRAMEHEIGHT = 600;
    public static final int FRAMEWIDTH = 1000;
    public static final int FONTSIZE = FRAMEHEIGHT / 20;
    public static final int QUESTIONMARGINDIVISOR = 20;

    // Button constants
    public static final int BUTTONWIDTH = 400;
    public static final int BUTTONHEIGHT = 200;
    public static final int BUTTONMARGIN = 20;
    public static final int BUTTONFONTSIZE = FONTSIZE * 2 / 3;
    public static final int MARGINS = 10;

    // Other Button constants
    public static final int BUTTON1WIDTH = 200;
    public static final int BUTTON1HEIGHT = 50;
    public static final int TEXTPANELWIDTH = 400;
    public static final int TEXTPANELHEIGHT = 100;

    // Placeholder text
    public static final String USERNAMEPLACEHOLDER = "Enter your username here...";
    public static final int POSITIONX = 5;
    public static final int POSITIONY = 7;
    public static final int FIELDX = 500;
    public static final int FIELDY = 25;

    // UI Spacers
    public static final int STRUTSMALLSPACER = 20;

    // Box Constants
    public static final int BOXX = 400;
    public static final int BOXY = 100;

    // UI Colours
    public static final Color BGCOLOUR = new Color(56, 113, 194);

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
        "Entertainment: Cartoon & Animations",
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
    public static final Integer[] NUM_QUESTION = {5, 10, 20};

    public static final int RANDOMKEYSIZE = 10;
}
