package app;

import javax.swing.JFrame;
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
