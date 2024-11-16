package app;

import interface_adapter.access_quiz.AccessQuizController;

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
                                            .addLoginView()
                                            .addSignupView()
                                            .addLoggedInView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addLogoutUseCase()
                                            .addAccessQuizUseCase()
                                            .addQuizGenerationView()
                                            .build();

        application.setSize(new Dimension(Constants.FRAMEWIDTH, Constants.FRAMEHEIGHT));
        application.setVisible(true);
    }
}
