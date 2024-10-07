package group.chatting.application;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class LoginScreen {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;

    public void showLoginScreen() {
       frame = new JFrame("Login");
        frame.setLayout(new GridLayout(4, 2, 5, 5)); // 4 rows, 2 columns, with gaps
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Login validation 
                   //Any non-empty username and matching passwords are valid
               
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (!username.isEmpty() && !password.isEmpty()) {
                    frame.dispose(); // Closing the login screen
                    openChatApp(username);
                    openChat(username);
                    open(username);
                    
                    
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password");
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Opening the sign-up screen
                frame.dispose(); // Closing the login screen
                openSignUpScreen();
                
            }
        });

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(signUpButton);

        frame.setVisible(true);
    }
//Opening that Chats
    private void openSignUpScreen() {
        SignUpScreen signUpScreen = new SignUpScreen(this);
        signUpScreen.showSignUpScreen();
    }

    private void openChatApp(String username) {
        UserOne userOne = new UserOne(username);
        Thread t1 = new Thread(userOne);
        t1.start();
    }
     private void openChat(String usernametwo) {
        UserTwo userTwo = new UserTwo(usernametwo);
        Thread t2 = new Thread(userTwo);
        t2.start();
    }
      private void open(String usernamethree) {
        UserThree userThree = new UserThree(usernamethree);
        Thread t3 = new Thread(userThree);
        t3.start();
    }
}
