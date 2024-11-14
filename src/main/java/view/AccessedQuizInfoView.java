package view;

import javax.swing.*;

import interface_adapter.access_quiz.AccessedQuizInfoViewModel;
import interface_adapter.change_password.LoggedInViewModel;

/**
 * The view for when a quiz has been accessed and its information must be shown.
 */
public class AccessedQuizInfoView extends JPanel {

    private final AccessedQuizInfoViewModel accessedQuizInfoViewModel;

    private final JButton testButton;

    public AccessedQuizInfoView(AccessedQuizInfoViewModel accessedQuizInfoViewModel) {
        this.accessedQuizInfoViewModel = accessedQuizInfoViewModel;

        final JPanel buttons0 = new JPanel();
        testButton = new JButton("Test!!");
        buttons0.add(testButton);

        this.add(buttons0);
    }

    public Object getViewName() {
        return "Quiz Info";
    }
}
