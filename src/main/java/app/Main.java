package app;

import javax.swing.*;
import java.awt.*;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        // sets look and feel for entire application to classic (Metal)
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                                            .addChangePasswordView()
                                            .addLoginView()
                                            .addSignupView()
                                            .addLoggedInView()
                                            .addQuizGenerationView()
                                            .addLocalMultiplayerView()
                                            .addAccessedQuizInfoView()
                                            .addPlaythroughView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addLogoutUseCase()
                                            .addLocalMultiplayerUseCase()
                                            .addAccessQuizUseCase()
                                            .addQuizGenerationUseCase()
                                            .build();

        application.setSize(new Dimension(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT));
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
