package app;

import java.awt.*;

/**
 * Defines UI constants for frame and button dimensions and font sizes.
 */
public class Constants {
    public static final int FRAMEHEIGHT = 800;
    public static final int FRAMEWIDTH = 1000;
    public static final int FONTSIZE = FRAMEHEIGHT / 20;
    public static final int QUESTIONMARGINDIVISOR = 20;

    public static final int BUTTONWIDTH = 400;
    public static final int BUTTONHEIGHT = 100;
    public static final int BUTTONMARGIN = 20;
    public static final int BUTTONFONTSIZE = FONTSIZE * 2 / 3;

    public static final String[] CATEGORIES = {"General Knowledge",
            "Entertainment: Books",
            "Entertainment: Film",
            "Entertainment: Music",
            "Entertainment: Musicals & Theatres",
            "Entertainment: Television",
            "Entertainment: Video Games",
            "Entertainment: Board Games",
            "Science & Nature",
            "Science: Computers",
            "Science: Mathematics",
            "Mythology",
            "Sports",
            "Geography",
            "History",
            "Politics",
            "Art",
            "Celebrities",
            "Animals",
            "Vehicles",
            "Entertainment: Comics",
            "Science: Gadgets",
            "Entertainment: Japanese Anime & Manga",
            "Entertainment: Cartoon & Animations"};
    public static final String[] DIFFICULTIES = {"Easy", "Medium", "Hard"};
    public static final Integer[] NUM_QUESTION = {5, 10, 20};

    public static final int RANDOMKEYSIZE = 10;
}
