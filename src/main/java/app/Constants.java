package app;

import java.awt.*;

/**
 * Defines UI constants for frame and button dimensions and font sizes.
 */
public class Constants {
    public static final int FRAMEHEIGHT = 600;
    public static final int FRAMEWIDTH = 1000;
    public static final int FONTSIZE = FRAMEHEIGHT / 20;
    public static final int QUESTIONMARGINDIVISOR = 20;

    public static final int BUTTONWIDTH = 400;
    public static final int BUTTONHEIGHT = 200;
    public static final int BUTTONMARGIN = 20;
    public static final int BUTTONFONTSIZE = FONTSIZE * 2 / 3;
    public static final int MARGINS = 10;
    public static final int THREE = 3;
    public static final int FOUR = 4;

    public static final String[] CATEGORIES = new String[]{
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
            "Vehicles"
    };
    public static final String[] DIFFICULTIES = {"Easy", "Medium", "Hard"};
    public static final Integer[] NUM_QUESTION = {5, 10, 20};

    public static final int RANDOMKEYSIZE = 10;
}
