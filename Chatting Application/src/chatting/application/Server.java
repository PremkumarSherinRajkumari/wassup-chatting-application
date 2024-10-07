// Server.java
package chatting.application;
// Importing necessary libraries
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
// Server class implements ActionListener interface
public class Server implements ActionListener {

    // GUI components
    private JTextField messageTextField;
    private static JPanel messagePanelContainer;
    private static Box messageBox = Box.createVerticalBox();
    private static JFrame frame = new JFrame();
    private static DataOutputStream dataOutputStream;
    private JLabel typingLabel;
    private static JLabel statusLabel;
    private JButton sendButton;
    private static boolean isFirstMessageReceived = false;
    private static boolean isTyping = false;
 // Constructor for initializing the server GUI
    Server() {
        frame.setLayout(null);
// Creating the top panel with user information
       JPanel panel = new JPanel();
        panel.setBackground(new Color(7, 94, 84));
        panel.setBounds(0, 0, 450, 60);
        panel.setLayout(null);
        frame.add(panel);
 // Adding icons and labels to the top panel
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        panel.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        panel.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        panel.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        panel.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        panel.add(morevert);

        JLabel name = new JLabel("Server");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        panel.add(name);
// Typing and status labels in the top panel
        typingLabel = new JLabel();
        typingLabel.setBounds(110, 35, 100, 18);
typingLabel.setForeground(Color.WHITE);
typingLabel.setFont(new Font("SAN_SERIF", Font.ITALIC, 12));
panel.add(typingLabel);

 statusLabel = new JLabel("You are online");
        statusLabel.setBounds(110, 35, 100, 18);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        panel.add(statusLabel);
 // Creating the message panel container
        messagePanelContainer = new JPanel();
        messagePanelContainer.setBounds(5, 65, 440, 470);
        frame.add(messagePanelContainer);


        messageTextField = new JTextField();
        messageTextField.setBounds(5, 540, 310, 40);
        messageTextField.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        frame.add(messageTextField);
// Setting up the message input text field with a document listener
        messageTextField.getDocument().addDocumentListener(new DocumentListener() {
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

        // Creating the send button and initializing it as disabled
        sendButton = new JButton("Send");
        sendButton.setBounds(320, 540, 123, 40);
        sendButton.setBackground(new Color(7, 94, 84));
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(this);
        sendButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        frame.add(sendButton);
        sendButton.setEnabled(false);

        // Setting up the frame properties
        frame.setSize(450, 600);
        frame.setLocation(200, 50);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.WHITE);
  // Making the frame visible
        frame.setVisible(true);
    }

  // Method to update the button state based on the text input

    private void updateButtonState() {
        boolean isTextEmpty = messageTextField.getText().trim().isEmpty();

        sendButton.setEnabled(!isTextEmpty);

        SwingUtilities.invokeLater(() -> {
            if (isTextEmpty) {
                typingLabel.setText("");
                statusLabel.setText("You are online");
            } else {
                typingLabel.setText("You are typing...");
                statusLabel.setText("");
                sendTypingIndication(); // Send typing indication
            }
        });
    }
 // Method to send a typing indication to the client
    private void sendTypingIndication() {
        if (!isTyping) {
            try {
                dataOutputStream.writeUTF("Server is typing...");
            } catch (IOException e) {
                e.printStackTrace();
            }
            isTyping = true;
        }
    }
      
// Method invoked when the send button is clicked
public void actionPerformed(ActionEvent ae) {
    try {
        String out = messageTextField.getText().trim();

out = convertEmojis(out);
        JPanel p2 = formatLabel(out);
        messagePanelContainer.setLayout(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        messageBox.add(right);
        messageBox.add(Box.createVerticalStrut(15));
        messagePanelContainer.add(messageBox, BorderLayout.PAGE_START);

        dataOutputStream.writeUTF(out);
        typingLabel.setText("");
        messageTextField.setText("");
        updateButtonState();

        frame.repaint();
        frame.invalidate();
        frame.validate();
        if (!isFirstMessageReceived) {
            isFirstMessageReceived = true;
            JOptionPane.showMessageDialog(null, " Client: You've got a message!");
        }
        
       
    } catch (Exception e) {
        e.printStackTrace();
    }
}
  


  // Method to convert text-based emojis to corresponding Unicode emojis
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




// Method to format the message label with delete button

public static JPanel formatLabel(String out) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    // Checking if the message is a typing indication
    boolean isTypingIndication = out.toLowerCase().contains(" is typing...");
    
    JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
    output.setFont(new Font("Tahoma", Font.PLAIN, 16));
    output.setBackground(new Color(37, 211, 102));
    output.setOpaque(true);
    output.setBorder(new EmptyBorder(15, 15, 15, 50));

    // Adding delete button only if it's not a typing indication
    if (!isTypingIndication) {
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
        panel.add(deleteButton);
    }

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    JLabel time = new JLabel();
    time.setText(sdf.format(cal.getTime()));

    panel.add(output);
    panel.add(time);

    return panel;
}



  // Main method to launch the server
    public static void main(String[] args) {
        // Creating an instance of the server GUI
        SwingUtilities.invokeLater(() -> new Server());
        isFirstMessageReceived = false;

        try {
            // Creating an instance of the server GUI
            ServerSocket socket = new ServerSocket(6001);
            while (true) {
                 // Accepting client connections
                Socket s = socket.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dataOutputStream = new DataOutputStream(s.getOutputStream());
   
                while (true) {
                    messagePanelContainer.setLayout(new BorderLayout());
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
          

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    messageBox.add(left);
                    
                     messageBox.add(Box.createVerticalStrut(15));
                    messagePanelContainer.add(messageBox, BorderLayout.PAGE_START);
                
                    frame.validate();
       
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
