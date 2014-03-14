import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class MainGUI {

	static TravelAgent agent;
	static String botName = "Travel Bot";
	static int turnCount = 0;
	
    MainGUI mainGUI;
    JFrame newFrame = new JFrame("The TravelBot started at " + Utils.getCurrentDateFull() + "\r\n" + "Powered by Google");
    JButton sendMessage;
    JTextField messageBox;
    JTextArea chatBox;
    JTextField usernameChooser;
    JFrame preFrame;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        agent = new TravelAgent();
        MainGUI mainGUI = new MainGUI();
        mainGUI.preDisplay();
        readArguments(args);
        
    }
     
    // Reads the program arguments for automated conversation input/output files
    private static void readArguments(String[] args) {
        String executingPath = Utils.getExecutingPath();
        String inputFilePath = "";
        String outputFilePath = "";

        for (String arg : args) {
            if (arg.contains("=")) {
                int indexOfEquals = arg.indexOf('=');

                String option = arg.substring(0, indexOfEquals).trim();
                String value = arg.substring(indexOfEquals + 1).trim();

                if (value.isEmpty()) {
                    displayInvalidArgument(arg);
                    continue;
                }

                switch (option) {
                    case "in":
                        inputFilePath = executingPath + value;
                        break;
                    case "out":
                        outputFilePath = executingPath + value;
                        break;
                    default:
                        displayInvalidArgument(arg);
                }
            } else {
                displayInvalidArgument(arg);
            }
        }

        setupStreams(inputFilePath, outputFilePath);
    }

    private static void displayInvalidArgument(String arg) {
        IORW.writeLine("Invalid argument: '" + arg + "'");
    }

    // Redirect IO if input/output files are given
    private static void setupStreams(String inputFilePath, String outputFilePath) {
        try {
            if (!StringUtils.isNullOrEmpty(inputFilePath)) {
                IORW.setInputFile(inputFilePath, true);
            }

            if (!StringUtils.isNullOrEmpty(outputFilePath)) {
                IORW.setOutputFile(outputFilePath, true);
            }
        } catch (Exception e) {
            IORW.writeLine("Error opening input file. Message: " + e.getMessage());
            System.exit(1);
        }
    }

    public void preDisplay() {
    	newFrame.setVisible(false);
        preFrame = new JFrame("The TravelBot started at " + Utils.getCurrentDateFull() + "\r\n" + "Powered by Google");
        usernameChooser = new JTextField(15);
        usernameChooser.setFont(new Font("Serif", Font.PLAIN,24));
        
        JLabel chooseUsernameLabel = new JLabel("Enter Your Username: ");
        chooseUsernameLabel.setFont(new Font("Serif", Font.PLAIN,24));
        
        JButton enterServer = new JButton("Enter Chat Room");
        enterServer.setFont(new Font("Serif", Font.PLAIN,24));
        enterServer.addActionListener(new enterServerButtonListener());
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        // preRight.weightx = 2.0;
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        prePanel.add(chooseUsernameLabel, preLeft);
        prePanel.add(usernameChooser, preRight);
        preFrame.add(BorderLayout.CENTER, prePanel);
        preFrame.add(BorderLayout.SOUTH, enterServer);
        preFrame.setSize(800, 600);
        preFrame.setVisible(true);
    }

    public void display() {
    	JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.GRAY);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.setFont(new Font("Serif", Font.PLAIN,24));
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        sendMessage.setFont(new Font("Serif", Font.PLAIN,24));
        
        sendMessage.addActionListener(new sendMessageButtonListener());

        chatBox = new JTextArea();
        chatBox.append("<Travel Bot >: We plan trips to cities in Canada, and Mexico. " +  agent.getResponseMaker().getGreeting(null) +"\n");
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 24));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(1024, 800);
        newFrame.setVisible(true);
    }

    class sendMessageButtonListener implements ActionListener {
    	
    	String userInput = "";
    	String response = "";
    	
        public void actionPerformed(ActionEvent event) {
        	 // Read from console
            userInput = messageBox.getText();

            // Parse input
            ParsedInput parsedInput = Parser.parseUserMessage(userInput);

            // Send parsed messaged to agent
            response = agent.getResponse(parsedInput);

            // Display response to user
           

            turnCount++;
        	
        	
            if (messageBox.getText().length() < 1) {
                // do nothing 
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
            	
                chatBox.append("<" + username + ">:  " + userInput + "\n");
                messageBox.setText("");
                chatBox.append("<Travel Bot >: " + response +"\n");
                
            }
        }
    }

    String username;

    class enterServerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            username = usernameChooser.getText();
            if (username.length() < 1) {System.out.println("You have to enter a character to start!"); }
            else {
            preFrame.setVisible(false);
            display();
            }
        }

    }
}