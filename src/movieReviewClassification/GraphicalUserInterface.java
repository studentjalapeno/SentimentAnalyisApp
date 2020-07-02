package movieReviewClassification;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.List;

public class GraphicalUserInterface {

    //width and height of the monitor
    private static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    //width and height of the window (JFrame)
    private static int windowsWidth = 800;
    private static int windowsHeight = 600;

    //Components for the layout
    private final JPanel topPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();
    private final JLabel commandLabel = new JLabel("Please select the command",JLabel.RIGHT);
    private final JComboBox comboBox = new JComboBox();


    //Output area. Set as global to be edit in different methods.
    private final JTextArea outputArea = new JTextArea();
    private final JScrollPane outputScrollPane = new JScrollPane(outputArea);


    //Global reviewhandler object to be just in different methods
    private static final ReviewHandler reviewHandler = new ReviewHandler();

    public void createAndShowGUI() {

        createTopPanel();
        createBottomPanel();

        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new GridLayout(2,0));
        panelContainer.add(topPanel);
        panelContainer.add(bottomPanel);

        JFrame frame = new JFrame("Sentiment Analysis App");

        // Save on database on quit
        frame.addWindowListener(new MainWindowAdapter());

        //panelContainer.setOpaque(true);
        frame.setBounds((width - windowsWidth) / 2, (height - windowsHeight) / 2, windowsWidth, windowsHeight);
        frame.setContentPane(panelContainer);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    private void createBottomPanel() {
        outputArea.setText("OUTPUT AREA TEST.\n\n");
        outputArea.setEditable(false);

        outputScrollPane.createVerticalScrollBar();
        outputScrollPane.createHorizontalScrollBar();

        bottomPanel.setLayout(new GridLayout(1,0));
        bottomPanel.add(outputScrollPane);
    }

    private void createTopPanel() {

        //Adds a new item to the list of values displayed by comboBox
        comboBox.addItem("Please select...");
        comboBox.addItem("1. Load new move review collection (given a folder or its file path");
        comboBox.addItem("2. Delete movie review from database ( given its id.)");
        comboBox.addItem("3.Search movie reviews in database by id");
        comboBox.addItem("4. Search movie reviews in database by substring");
        comboBox.addItem("0. Exit Program");

        //Default displayed item
        comboBox.setSelectedIndex(0);

        comboBox.addItemListener(new MainComboBoxListener());

        //Set layout manager
        GridLayout gridLayout = new GridLayout(0,2,10,10);

        topPanel.setLayout(gridLayout);

        resetTopPanel();


    }

    private void resetTopPanel() {
        topPanel.removeAll();

        topPanel.add(commandLabel);
        topPanel.add(comboBox);
        //Keep the comboBox at the first line.
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());

        topPanel.updateUI();
    }


    private class MainComboBoxListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            //e = GUI object that has changed states
            if(e.getStateChange() == 1) {

                if (e.getItem().equals("Please select...")) {
                    outputArea.append("");
                    outputArea.append("Please select a command to continue \n");
                    topPanel.updateUI();
                    resetTopPanel();

                } else if (e.getItem().equals("1. Load new move review collection (given a folder or its file path")) {
                    loadReviews();

                } else if (e.getItem().equals("2. Delete movie review from database ( given its id.)")) {
                    deleteReviews();

                } else if (e.getItem().equals("3.Search movie reviews in database by id")) {
                    searchReviewsID();

                } else if (e.getItem().equals("4. Search movie reviews in database by substring")) {
                    searchReviewsSubStrings();

                } else if (e.getItem().equals("0. Exit Program")) {
                    exitGUI();
                }
            }
        }
    }

    /**
     * closes the application when selected
     */
    private void exitGUI() { System.exit(0);}


    private void searchReviewsSubStrings() {
        topPanel.removeAll();
        topPanel.add(commandLabel);
        topPanel.add(comboBox);

        JLabel enterSearch = new JLabel("Enter a review you wish to search for: (By String)", JLabel.RIGHT);
        topPanel.add(enterSearch);

        outputArea.setText(""); //reset output area
        outputArea.append("SearchReviews (substring)- Command\n");
        outputArea.append("Please enter a review you wish to search for (By String)\n");

        JTextField searchInputField = new JTextField("");
        topPanel.add(searchInputField);

        topPanel.add(new JLabel());

        JButton searchButton = new JButton("Search");
        topPanel.add(searchButton);

        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());

        topPanel.updateUI();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        String searchForThis = searchInputField.getText();
                        MovieReview movieReview = (MovieReview) reviewHandler.searchBySubstring(searchForThis);
                        List<MovieReview> reviewList = reviewHandler.searchBySubstring(searchForThis);
                        if (reviewList != null){
                            //TODO print table

                            //         for (MovieReview movieReview : reviewList){
                 //           }
                            outputArea.append(reviewList.size() + " reviews found.");
                        }else{
                            outputArea.append("Review not found");
                        }
                    }

                };

                Thread thread = new Thread(runnable);
                thread.start();
            }
        });

    }

    /**
     * Method 3 - Search Review by ID
     *
     */

    private void searchReviewsID() {
        topPanel.removeAll();
        topPanel.add(commandLabel);
        topPanel.add(comboBox);

        JLabel enterSearch = new JLabel("Enter a review you wish to search for: (By ID)", JLabel.RIGHT);
        topPanel.add(enterSearch);

        outputArea.setText(""); //reset output area
        outputArea.append("SearchReviews(ID) - Command\n");
        outputArea.append("Please enter a review you wish to search for (By ID)\n");

        JTextField searchInputField = new JTextField("");
        topPanel.add(searchInputField);

        topPanel.add(new JLabel());

        JButton searchButton = new JButton("Search");
        topPanel.add(searchButton);

        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());

        topPanel.updateUI();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        String idStr = searchInputField.getText();
                        if(!idStr.matches("-?(0|[1-9]\\d*)")){
                            outputArea.append("Illegal Input \n");
                        }else{
                            int ID = Integer.parseInt(idStr);
                            MovieReview movieReview = reviewHandler.searchById(ID);
                            if (movieReview != null)
                            {
                                //TODO print movieReview Info

                            }else
                            {
                                outputArea.append("Movie Review not found");
                            }

                        }

                    }
                };

                Thread thread = new Thread(runnable);
                thread.start();
            }
        });



    }

    /**
     * Method 2: delete new Reviews
     */

    private void deleteReviews() {
        topPanel.removeAll();

        topPanel.add(commandLabel);
        topPanel.add(comboBox);

        //bottom panel
        outputArea.setText(""); //reset output area
        outputArea.append("Delete Reviews - Command \n");
        outputArea.append("Size of database: " + reviewHandler.database.size() + "\n");
        outputArea.append("Please enter an ID of the review you wish to delete\n");

        JLabel selectReview = new JLabel("Enter the ID of the review you wish to delete", JLabel.RIGHT);
        topPanel.add(selectReview);

        JTextField IDInputField = new JTextField("");
        topPanel.add(IDInputField);

        topPanel.add(new JLabel());

        JButton deleteButton = new JButton("Delete");
        topPanel.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                    String idStr = IDInputField.getText();
                    if(!idStr.matches("-?(0|[1-9]\\d*)")){
                        outputArea.append("Illegal Input \n");
                    }else{
                        int ID = Integer.parseInt(idStr);
                        reviewHandler.deleteReview(ID);
                        outputArea.append("Review ID: " + ID + " deleted\n");
                    }

                    }
                };

                Thread thread = new Thread(runnable);
                thread.start();

            }
        });

        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());

        topPanel.updateUI();

    }

    /**
     * Method 1: load new reviews
     */
    static int realClass = 0;
    private void loadReviews() {
        topPanel.removeAll();

        topPanel.add(commandLabel);
        topPanel.add(comboBox);

        //bottom panel
        outputArea.setText(""); //reset output area
        outputArea.append("Load Reviews - Command\n");
        outputArea.append("Size of database: " + reviewHandler.database.size() + "\n");
        outputArea.append("Please input the path of file or folder\n");

        //Components
        final JLabel pathLabel = new JLabel("File path",JLabel.RIGHT);
        final JTextField pathInput = new JTextField("");

        final JLabel realClassLabel = new JLabel("Real Class",JLabel.RIGHT);
        final JComboBox realClassComboBox = new JComboBox();
        realClassComboBox.addItem("Negative");
        realClassComboBox.addItem("Positive");
        realClassComboBox.addItem("Unknown");


        //Combobox listener - if state is changed
        realClassComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == 1){
                    if (e.getItem().equals("Negative")){
                        realClass = 0;
                    } else if (e.getItem().equals("Positive")){
                        realClass = 1;
                    }else if (e.getItem().equals("Unknown")){
                        realClass = 2;
                    }
                }
            }
        });


        //Confirm Button - Listener
        final JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                      String path = pathInput.getText();
                      reviewHandler.loadReviews(path, realClass);
                    }
                };

                Thread thread = new Thread(runnable);
                thread.start();
            }
        });

        topPanel.add(pathLabel);
        topPanel.add(pathInput);
        topPanel.add(confirmButton);

        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());
        topPanel.add(new JLabel());

        topPanel.updateUI(); //refresh

    }

    /**
     * Called once UI is closing. Saves the database
     */
    private class MainWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) { //invoked when a window is in the process of being closed
            super.windowClosing(e);
            reviewHandler.saveSerialDB();
        }
    }
}
