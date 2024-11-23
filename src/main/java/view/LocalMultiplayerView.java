package view;

import interface_adapter.local_multiplayer.LocalMultiplayerController;
import interface_adapter.local_multiplayer.LocalMultiplayerViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is playing Local Multiplayer.
 */
public class LocalMultiplayerView extends JPanel implements PropertyChangeListener {

    private LocalMultiplayerController localMultiplayerController;

    private final String viewName = "local multiplayer";
    private final LocalMultiplayerViewModel localMultiplayerViewModel;

    public LocalMultiplayerView(LocalMultiplayerViewModel localMultiplayerViewModel) {
        this.localMultiplayerViewModel = localMultiplayerViewModel;
        this.localMultiplayerViewModel.addPropertyChangeListener(this);

        final JButton testButton = new JButton(viewName);

        testButton.addActionListener(evt -> {
            if (evt.getSource().equals(testButton)) {
                System.out.println("test button clicked");
            }
        });

        this.add(testButton);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("hi");
    }

    public String getViewName() {
        return viewName;
    }

    public void setLocalMultiplayerController(LocalMultiplayerController localMultiplayerController) {
        this.localMultiplayerController = localMultiplayerController;
    }

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Local Multiplayer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1200, 500));
        frame.setLocationRelativeTo(null);

        final LocalMultiplayerView localMultiplayerView = new LocalMultiplayerView(new LocalMultiplayerViewModel());
        frame.add(localMultiplayerView);
        frame.setVisible(true);
    }
}
