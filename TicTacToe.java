import java.util.*;
import java.awt.*;import java.awt.event.*;
import javax.swing.*;

/**

// change the font of a shortcut key mask
// should the reset be for when the game is done or just whenever
// subclass problem

*/

/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game.
 * 
 * @author Lynn Marshall
 * @version November 8, 2012
 * 
 * @author Ricky Dam
 * @version June 11, 2015
 */

public class TicTacToe implements ActionListener
{   
   JButton[][] button; // a double array of buttons

   private int turnCounter; // keeps track of the number of turns completed
   
   private JMenuItem resetItem; // a menuItem for resetting which also includes a keyboard shortcut
   private JMenuItem quitItem; // a menuItem for quitting which also includes a keyboard shortcut

   private JTextField score; // a text field to display the integer
   private int scoreKeeper; // an integer that keepstrack of the score
   
   private boolean gameEnded; //changes once a winner is found
   
   private JLabel label;
   
   /** 
    * Constructs a new Tic-Tac-Toe board.
    */
   public TicTacToe()       
   {   
      turnCounter = 0;
      gameEnded = false;
      
      JFrame frame = new JFrame("Tic-Tac-Toe Interactive");      
      int height = 530;
      int width = 500;
      frame.setPreferredSize(new Dimension(width, height));
      
      Container contentPane = frame.getContentPane();
      BorderLayout borderPane = new BorderLayout();
      contentPane.setLayout(borderPane); // The number of panes
     
      JPanel buttonPanel = new JPanel();buttonPanel.setLayout(new GridLayout(3, 3)); // 3x3
      button = new JButton[3][3];
      for(int i=0; i<3; i++) { 
        for(int j=0; j<3; j++) { //initalizes all the buttons
            button[i][j] = new JButton();
            button[i][j].setText("");            
            button[i][j].setFont(new Font(null, Font.BOLD, 100));
            buttonPanel.add(button[i][j]);
            button[i][j].addActionListener(this); 
        }
      }     
      
      contentPane.add(buttonPanel, BorderLayout.CENTER);
      
      // Menu bar
      JMenuBar menuBar = new JMenuBar();
      frame.setJMenuBar(menuBar);
      
      JMenu fileMenu = new JMenu("Game");
      fileMenu.setFont(new Font(null, Font.BOLD, 20));
      menuBar.add(fileMenu);
      
      resetItem = new JMenuItem("Reset (CTRL+R)");
      resetItem.setFont(new Font(null, Font.BOLD, 20));
      fileMenu.add(resetItem);
      
      quitItem = new JMenuItem("Quit (CTRL+Q)");
      quitItem.setFont(new Font(null, Font.BOLD, 20));
      fileMenu.add(quitItem);
      
      final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
      resetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, SHORTCUT_MASK));
      quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
      
      resetItem.addActionListener(this);
      quitItem.addActionListener(this);//new ActionListener()
      /*{
          public void actionPerformed(ActionEvent event)
          {
              System.exit(0);
          }
      }
      );*/
      
      label = new JLabel();
      label.setText(" X Player's Turn");
      label.setFont(new Font(null, Font.BOLD, 20));
      contentPane.add(label, BorderLayout.SOUTH);
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // pressing the red x closes the game      
      frame.pack();
      frame.setResizable(true);
      frame.setVisible(true);      
   } 
   
   private void checkForWinner(int row, int col)
   {
       if(turnCounter > 4) { // there cannot be a winner there were at least 4 moves
           if(button[row][0].getText().equals(button[row][1].getText()) &&
              button[row][1].getText().equals(button[row][2].getText())) {
                gameEnded = true;
           }
           else if(button[0][col].getText().equals(button[1][col].getText()) &&
                   button[1][col].getText().equals(button[2][col].getText())) {
                gameEnded = true;
           }               
           else if(button[0][0].getText().equals(button[1][1].getText()) &&
                   button[1][1].getText().equals(button[2][2].getText())) {
                gameEnded = true;
           }
           else if(button[0][2].getText().equals(button[1][1].getText()) &&
                   button[1][1].getText().equals(button[2][0].getText())) {
                gameEnded = true;
            }
       }
   }
    
   public void actionPerformed(ActionEvent event)
   {
        if(!gameEnded) { // The game does not have a winner yet
            JButton theButton = (JButton) event.getSource();// find out which button has been pressed
            if(turnCounter % 2 == 0) { // Player X always starts the games
                for(int i=0; i<3; i++){                     
                    for(int j=0; j<3; j++) { // use for loops to find the button was pressed
                        if(theButton == button[i][j]) {
                            button[i][j].setText("X");
                            button[i][j].setEnabled(false);
                            turnCounter++;
                            checkForWinner(i, j);
                            label.setText(" O Player's Turn");
                            if(gameEnded) {
                                disableAll();
                            }
                        }
                    }
                }                
            }
            else { // if its an odd number, it is O's turn             
                for(int i=0; i<3; i++){
                    for(int j=0; j<3; j++) {
                        if(theButton == button[i][j]) {
                            button[i][j].setText("O");
                            button[i][j].setEnabled(false);
                            turnCounter++;
                            checkForWinner(i, j);
                            label.setText(" X Player's Turn");
                            if(gameEnded) {
                                disableAll();
                            }
                        }
                    }
                }               
            }
        }
        else { // The game has ended and can be reset
            Object thatThing = event.getSource(); // Find out which menuItem has been clicked            
            JMenuItem element = (JMenuItem) thatThing;
            if(element == resetItem) {
                // the player chose to reset the game
                clearEverything();            
            }
            else if (element == quitItem) {
                System.exit(0);            
            }
        }
   }
   
   /**
    * Clears the board to play the game again without having to make a new game.
    */
   private void clearEverything()
   {
      for(int i=0; i<3; i++) { // go through all buttons and reset the text and enable the button
        for(int j=0; j<3; j++) {
            button[i][j].setText("");  
            button[i][j].setEnabled(true);
            turnCounter = 0;
            gameEnded = false;
        }
      }     
   }
   
   /**
    * This method will be called once a winner is found and all the buttons need to be disabled
    * because the game is over.
    */
   private void disableAll()
   {
       for(int i=0; i<3; i++) { // go through all buttons and reset the text and enable the button
        for(int j=0; j<3; j++) {
            button[i][j].setEnabled(false);
        }
      }     
   }
}

/**
 * Mammal constructor
 * 
 * @param name : name of mammal
 * @param weight : weight of mammal (kg)
 * @param numLegs : number of legs this mammal has
 * 
   public Mammal(String name, double weight, int numLegs) 
   {
       super(name);
       this.weight = weight;
       this.numLegs = numLegs;
   }
   
  
 * Cat constructor
 * 
 * @param name : name of mammal
 * @param weight : weight of mammal (kg)
 * @param numLegs : number of legs this mammal has
 * @param colour : colour of the cat
 * 
   public Cat (String name, double weight, int numLegs) 
   {
       this,colour = colour
   }
   
   
   Horse's equals method
   
   @return true if the horses have the same name and weight and number of legs and age and false otherwise
   
   public boolean equals(object o) {
    if(this == o) return true; // same object
    if(o == null || !(o instanceof Horse)) return false; // can't possibly match
    Horse h = (Horse) o; // cast of horse
    return this.super.eqausl(h) && h.age == this.age;
   }
   
   
   
   
 */




































