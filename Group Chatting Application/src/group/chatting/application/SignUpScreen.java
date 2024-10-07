//SignUpScreen.java
package group.chatting.application;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


class SignUpScreen {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signUpButton;
    private JButton backButton;
    private final LoginScreen loginScreen;

    public SignUpScreen(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;
    }

    public void showSignUpScreen() {
        frame = new JFrame("Sign Up");
        frame.setLayout(new GridLayout(5, 2, 5, 5)); // 5 rows, 2 columns, with gaps
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);

        signUpButton = new JButton("Sign Up");
        backButton = new JButton("Back to Login");

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Login validation 
                   //Any non-empty username and matching passwords are valid
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                if (!username.isEmpty() && !password.isEmpty() && password.equals(confirmPassword)) {
                    frame.dispose(); // Closing the sign-up screen
                    loginScreen.showLoginScreen(); // Opening the login screen
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid input or passwords do not match");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Going back to the login screen
                frame.dispose(); // Closing the sign-up screen
                loginScreen.showLoginScreen(); // Opening the login screen
            }
        });

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(confirmPasswordLabel);
        frame.add(confirmPasswordField);
        frame.add(signUpButton);
        frame.add(backButton);

        frame.setVisible(true);
    }
}
