# wassup-chatting-application
This Chatting Application is a real-time client-server messaging platform built in Java. It allows users to send and receive messages with emoji support, typing indicators, and includes user authentication and group chat functionalities.

*Features:*
Real-time Messaging: Communicate instantly with other users.
User Authentication: Secure user login to ensure that only registered users can access the chat.
Group Chat Functionality: Create and join group chats for multiple users to communicate simultaneously.
Emoji Support: Send text-based emojis that are converted to Unicode emojis.
Typing Indicators: Display when other users are typing a message.
Time Stamping: Each message displays the time it was sent.
Message Deletion: Users can delete their sent messages.
User Interface: A clean and intuitive GUI built with Java Swing.

*Technologies Used:*
Java, Java Swing, Socket Programming

*Requirements*
Java Development Kit (JDK) 8 or higher
Basic understanding of Java and networking concepts

*Usage:*
Start the server, then open multiple instances of the client.
Run a group chat or a private conversation.
Register or log in using your credentials for group chat.
Type your message in the input field and click the "Send" button or press Enter.
Use text-based emojis (e.g., :), :(, <3) to express emotions.

*Future Enhancements:*
1. Implement message encryption for added security.
2. Improve UI/UX with modern design practices.
3. Add file sharing capabilities.
4. Enhance user profile features.

*Troubleshooting:* 
Connection Issues: Ensure that the server is running and accessible from the client’s machine. Check firewalls and network configurations.
UI Issues: If the UI doesn't display correctly, try resizing the window or restarting the application.

*Additional Information:*
Feel free to reach out to me at sherinrajkumariis@gmail.com for any inquiries or collaborations.

# Setup and Installation
*Clone the Repository:*
git clone https://github.com/PremkumarSherinRajkumari/wassup-chatting-application.git
cd Chatting-Application

*Compile the Code:*
javac Chatting/Application/Server.java
javac Chatting/Application/Client.java

*Run the Server first and then the Client:*
java chatting.application.Server
java chatting.application.Client

