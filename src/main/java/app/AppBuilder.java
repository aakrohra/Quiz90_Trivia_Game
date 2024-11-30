package app;

import java.awt.*;

import javax.swing.*;

import data_access.DBCustomQuizDataAccessObject;
import data_access.DBTriviaDataAccessObject;
import data_access.DBUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.access_database.AccessDatabaseController;
import interface_adapter.access_database.AccessDatabasePresenter;
import interface_adapter.access_database.AccessedDatabaseInfoViewModel;
import interface_adapter.access_quiz.AccessQuizController;
import interface_adapter.access_quiz.AccessQuizPresenter;
import interface_adapter.access_quiz.AccessedQuizInfoViewModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.ChangePasswordViewModel;
import interface_adapter.local_multiplayer.LocalMultiplayerController;
import interface_adapter.local_multiplayer.LocalMultiplayerPresenter;
import interface_adapter.local_multiplayer.LocalMultiplayerViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.playthrough.PlaythroughViewModel;
import interface_adapter.quiz_generation.QuizGenerationController;
import interface_adapter.quiz_generation.QuizGenerationPresenter;
import interface_adapter.quiz_generation.QuizGenerationViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.summary.SummaryController;
import interface_adapter.summary.SummaryPresenter;
import interface_adapter.summary.SummaryViewModel;
import use_case.access_database.AccessDatabaseInputBoundary;
import use_case.access_database.AccessDatabaseInteractor;
import use_case.access_database.AccessDatabaseOutputBoundary;
import use_case.access_quiz.AccessQuizInputBoundary;
import use_case.access_quiz.AccessQuizInteractor;
import use_case.access_quiz.AccessQuizOutputBoundary;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.local_multiplayer.LocalMultiplayerInputBoundary;
import use_case.local_multiplayer.LocalMultiplayerInteractor;
import use_case.local_multiplayer.LocalMultiplayerOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.quiz_generation.QuizGenerationInputBoundary;
import use_case.quiz_generation.QuizGenerationInteractor;
import use_case.quiz_generation.QuizGenerationOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.summary.SummaryInputBoundary;
import use_case.summary.SummaryInteractor;
import use_case.summary.SummaryOutputBoundary;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(userFactory);
    private final DBTriviaDataAccessObject triviaDataAccessObject = new DBTriviaDataAccessObject();
    private final DBCustomQuizDataAccessObject customQuizDataAccessObject = new DBCustomQuizDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private AccessedDatabaseInfoViewModel accessedDatabaseInfoViewModel;
    private AccessedQuizInfoViewModel accessedQuizInfoViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private AccessedQuizInfoView accessedQuizInfoView;
    private QuizGenerationViewModel quizGenerationViewModel;
    private QuizGenerationView quizGenerationView;
    private LocalMultiplayerViewModel localMultiplayerViewModel;
    private LocalMultiplayerView localMultiplayerView;
    private PlaythroughViewModel playthroughViewModel;
    private PlaythroughView playthroughView;
    private QuizDatabaseView quizDatabaseView;
    private ChangePasswordViewModel changePasswordViewModel;
    private ChangePasswordView changePasswordView;
    private SummaryViewModel summaryViewModel;
    private SummaryView summaryView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the ChangePassword View to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordView() {
        changePasswordViewModel = new ChangePasswordViewModel();
        changePasswordView = new ChangePasswordView(changePasswordViewModel);
        cardPanel.add(changePasswordView, changePasswordView.getViewName());
        return this;
    }

    /**
     * Adds the QuizGeneration View to the application.
     * @return this builder
     */
    public AppBuilder addQuizGenerationView() {
        quizGenerationViewModel = new QuizGenerationViewModel();
        quizGenerationView = new QuizGenerationView(quizGenerationViewModel);
        cardPanel.add(quizGenerationView, quizGenerationView.getViewName());
        return this;
    }

    /**
     * Adds the Local Multiplayer View to the application.
     * @return this builder
     */
    public AppBuilder addLocalMultiplayerView() {
        localMultiplayerViewModel = new LocalMultiplayerViewModel();
        localMultiplayerView = new LocalMultiplayerView(localMultiplayerViewModel);
        cardPanel.add(localMultiplayerView, localMultiplayerView.getViewName());
        return this;
    }

    /**
     * Adds the Playthrough View to the application.
     * @return this builder
     */
    public AppBuilder addPlaythroughView() {
        playthroughViewModel = new PlaythroughViewModel();
        playthroughView = new PlaythroughView(playthroughViewModel);
        cardPanel.add(playthroughView, playthroughView.getViewName());
        return this;
    }

    public AppBuilder addDatabaseView() {
        accessedDatabaseInfoViewModel = new AccessedDatabaseInfoViewModel();
        quizDatabaseView = new QuizDatabaseView(accessedDatabaseInfoViewModel);
        cardPanel.add(quizDatabaseView, quizDatabaseView.getViewName());
        return this;
    }

    /**
     * Adds the Local Multiplayer Use Case to the application.
     * This method initializes and wires up the components required for the
     * Local Multiplayer feature, including its presenter, interactor, and controller.
     * It sets the controller for the associated views to enable interaction
     * between the user interface and the underlying use case logic.
     *
     * @return the current instance of {@code AppBuilder} for method chaining.
     */

    public AppBuilder addLocalMultiplayerUseCase() {
        final LocalMultiplayerOutputBoundary localMultiplayerPresenter = new LocalMultiplayerPresenter(
                viewManagerModel, localMultiplayerViewModel, loggedInViewModel, playthroughViewModel);

        final LocalMultiplayerInputBoundary localMultiplayerInteractor =
                new LocalMultiplayerInteractor(localMultiplayerPresenter, triviaDataAccessObject);

        final LocalMultiplayerController localMultiplayerController =
                new LocalMultiplayerController(localMultiplayerInteractor);
        loggedInView.setLocalMultiplayerController(localMultiplayerController);
        localMultiplayerView.setLocalMultiplayerController(localMultiplayerController);
        return this;
    }

    /**
     * Adds the AccessedQuizInfo View to the application.
     * @return this builder
     */
    public AppBuilder addAccessedQuizInfoView() {
        accessedQuizInfoViewModel = new AccessedQuizInfoViewModel();
        accessedQuizInfoView = new AccessedQuizInfoView(accessedQuizInfoViewModel);
        cardPanel.add(accessedQuizInfoView, accessedQuizInfoView.getViewName());
        return this;
    }

    /**
     * Adds the Summary View to the application.
     * @return this builder
     */
    public AppBuilder addSummaryView() {
        summaryViewModel = new SummaryViewModel();
        summaryView = new SummaryView(summaryViewModel);
        cardPanel.add(summaryView, summaryView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loginViewModel, loggedInViewModel, signupViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(viewManagerModel, changePasswordViewModel, loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        changePasswordView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
    }

    public AppBuilder addAccessQuizDatabaseUseCase() {

        final AccessDatabaseOutputBoundary accessDatabaseOutputBoundary = new AccessDatabasePresenter(
                viewManagerModel, loggedInViewModel, accessedDatabaseInfoViewModel);

        final AccessDatabaseInputBoundary accessDatabaseInteractor =
                new AccessDatabaseInteractor(customQuizDataAccessObject, accessDatabaseOutputBoundary);

        final AccessDatabaseController accessDatabaseController = new AccessDatabaseController(accessDatabaseInteractor);
        loggedInView.setAccessedQuizDatabaseController(accessDatabaseController);
        return this;
    }

    /**
     * Adds the Access Quiz Use Case to the application.
     * @return this builder
     */
    public AppBuilder addAccessQuizUseCase() {
        final AccessQuizOutputBoundary accessQuizOutputBoundary = new AccessQuizPresenter(
                viewManagerModel, loggedInViewModel, accessedQuizInfoViewModel, playthroughViewModel
        );

        final AccessQuizInputBoundary accessQuizInteractor = new AccessQuizInteractor(
                customQuizDataAccessObject, accessQuizOutputBoundary);

        final AccessQuizController accessQuizController = new AccessQuizController(accessQuizInteractor);
        loggedInView.setAccessQuizController(accessQuizController);
        accessedQuizInfoView.setAccessQuizController(accessQuizController);
        return this;
    }

    // TODO: Add instance variable for LocalMultiplayerPlaythroughViewModel
    /**
     * Adds the Quiz Generation Use Case to the application.
     * @return this builder
     */
    public AppBuilder addQuizGenerationUseCase() {
        loggedInViewModel = new LoggedInViewModel();
        final QuizGenerationOutputBoundary quizGenerationPresenter =
                new QuizGenerationPresenter(viewManagerModel, quizGenerationViewModel, loggedInViewModel,
                        playthroughViewModel);

        final QuizGenerationInputBoundary quizGenerationInteractor =
                new QuizGenerationInteractor(quizGenerationPresenter, triviaDataAccessObject);

        final QuizGenerationController quizGenerationController =
                new QuizGenerationController(quizGenerationInteractor);
        loggedInView.setQuizGenerationController(quizGenerationController);
        quizGenerationView.setQuizGenerationController(quizGenerationController);
        return this;

    }

    /**
     * Adds the Summary Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSummaryUseCase() {
        final SummaryOutputBoundary summaryPresenter = new SummaryPresenter(
                viewManagerModel, summaryViewModel, loggedInViewModel);
        final SummaryInputBoundary summaryInteractor = new SummaryInteractor(summaryPresenter);

        final SummaryController summarycontroller = new SummaryController(summaryInteractor);
        playthroughView.setSummaryController(summarycontroller);
        summaryView.setSummaryController(summarycontroller);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Quiz90 Trivia Game");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
