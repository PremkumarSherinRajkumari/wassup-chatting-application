package group.chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UserOne implements ActionListener, Runnable {

    JTextField text;
    JPanel panelContainer;
    static Box vertical = Box.createVerticalBox();
    static JFrame frame = new JFrame();
    static DataOutputStream dout;
     JLabel typingStatus; 
    JButton send;

    BufferedReader reader;
    BufferedWriter writer;
    String name = "UserOne:";
     String username;
 // Constructor to initialize the UserOne instance
    UserOne(String username) {
         this.username = username;

        frame.setLayout(null);
 // Setting up the GUI
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        frame.add(p1);

       ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/mirzapur.png"));
        Image i5 = i4.getImage().getScaledInstance(60,60, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 5, 60, 60);
        p1.add(profile);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);
        
        JLabel name = new JLabel("Friends");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);
        
       
        
     typingStatus = new JLabel(" You are typing...");
        typingStatus.setBounds(110, 35, 160, 18);
        typingStatus.setForeground(Color.WHITE);
        typingStatus.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        p1.add(typingStatus);

       

        panelContainer = new JPanel();
        panelContainer.setBounds(5, 75, 440, 500); // Adjusted the height
        panelContainer.setBackground(Color.WHITE);
        frame.add(panelContainer);
// Initializing text field and add DocumentListener
        text = new JTextField();
        text.setBounds(5, 580, 310, 40); // Adjusted the position
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(text);

   
        text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtonState();
            }
        });
        
         // Initializing send button
        send = new JButton("Send");
        send.setBounds(320, 580, 123, 40); // Adjusted the position
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(send);

        frame.setSize(450, 650); // Adjusted the height
        frame.setLocation(10, Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight() - 50);
frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.WHITE);
        
        frame.setVisible(true);
        // Setting up the server connection
        try {
            Socket socket = new Socket("localhost", 2003);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ActionListener implementation for the send button
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = "<html><p>" + name + "</p><p>" + text.getText() + "</p></html>";
 out = convertEmojis(out);
            JPanel p2 = formatLabel(out);

            panelContainer.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.setBackground(Color.WHITE);
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            panelContainer.add(vertical, BorderLayout.PAGE_START);

            try {
                writer.write(out);
                writer.write("\r\n");
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

            text.setText("");

            frame.repaint();
            frame.invalidate();
            frame.validate();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Updating the state of the send button and typing status label
    private void updateButtonState() {
        if (text.getText().isEmpty()) {
            send.setEnabled(false);
            typingStatus.setText("Status: You are Online");
        } else {
            send.setEnabled(true);
            typingStatus.setText("You are typing...");
        }
    }
    // Formatting the message label and add delete functionality
  public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(0, 15, 0, 50));

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Removing the message panel from the UI
                Container parent = panel.getParent();
                parent.remove(panel);
                parent.revalidate();
                parent.repaint();
            }
        });

        panel.add(output);
        panel.add(deleteButton);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }
 // Runnable implementation for handling incoming messages
  public void run() {
    try {
        String msg = "";
        while (true) {
            msg = reader.readLine();
            if (msg.contains(name)) {
                continue;
            }

            // Showing a message panel when a new message is received
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "UserOne: You've got a message!");
            });

            JPanel panel = formatLabel(msg);

            JPanel left = new JPanel(new BorderLayout());
            left.setBackground(Color.WHITE);
            left.add(panel, BorderLayout.LINE_START);
            vertical.add(left);

            panelContainer.add(vertical, BorderLayout.PAGE_START);

            frame.repaint();
            frame.invalidate();
            frame.validate();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
 // Converting textual emoticons to emoji characters
    private String convertEmojis(String message) {
   
    message = message.replace(":)", "\uD83D\uDE42");  // Smiling face emoji
    message = message.replace(":(", "\uD83D\uDE41");  // Frowning face emoji
    message = message.replace(":D", "\uD83D\uDE00");  // Grinning face emoji
    message = message.replace(":-)", "\uD83D\uDE03");  // Smiling face with open mouth emoji
    message = message.replace("<3", "\uD83D\uDC9C");  // Heart emoji
    message = message.replace(";)", "\uD83D\uDE09");  // Winking face emoji
    message = message.replace(":P", "\uD83D\uDE1B");  // Tongue sticking out emoji
    message = message.replace(":O", "\uD83D\uDE2E");  // Open mouth emoji
    message = message.replace(":-(", "\uD83D\uDE14");  // Pensive face emoji
    message = message.replace(":|", "\uD83D\uDE10");  // Neutral face emoji
    message = message.replace(":'(", "\uD83D\uDE22");  // Crying face emoji
    message = message.replace(":*", "\uD83D\uDC8B");  // Kiss mark emoji
    message = message.replace(":^)", "\uD83D\uDE0A");  // Smiling face with a raised eyebrow emoji
    message = message.replace(">:(", "\uD83D\uDE21");  // Angry face emoji
    message = message.replace("8-)", "\uD83D\uDE0E");  // Sunglasses emoji
    message = message.replace(":3", "\uD83D\uDE0A");  // Cat face emoji
    message = message.replace(":s", "\uD83E\uDD2D");  // Confused face emoji
    message = message.replace(":x", "\uD83D\uDE31");  // Face with open mouth and cold sweat emoji
    message = message.replace(":poop:", "\uD83D\uDCA9");  // Pile of poo emoji
    message = message.replace(":fire:", "\uD83D\uDD25");  // Fire emoji
    message = message.replace(":thumbsup:", "\uD83D\uDC4D");  // Thumbs up emoji
    message = message.replace(":thumbsdown:", "\uD83D\uDC4E");  // Thumbs down emoji
    message = message.replace(":beer:", "\uD83C\uDF7A");  // Beer mug emoji
    message = message.replace(":pizza:", "\uD83C\uDF55");  // Pizza emoji
    message = message.replace(":rocket:", "\uD83D\uDE80");  // Rocket emoji
   

    return message;
}
    
    
      // Main method to start the application
   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.showLoginScreen();
        });
    }
}
  


