package cs1302.mineweeper;
// Importation of the Scanner and Random classes from the java.util package.
// Importation of the File class from the java.io package.
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;




public class Minesweeper extends JPanel implements ActionListener {
    
    // The number of rows and columns in the game board
    private int rows, cols;
    
    // The number of mines on the game board
    private int mines;
    
    // The number of coordinates that have been clicked so far
    private int clicks;
    
    // The game board
    public boolean[][] clickedBoard;
    public boolean[][] mineBoard;
    private JButton[][] display;
    
    // Is the game running?
    private boolean isRunning;
        
    // Creates labels for the user to read
    private JLabel topLeftLabel;
    private JLabel topRightLabel;
    private JLabel winLoseLabel;
    
    // Creates a button to quit the game
    private JButton quitButton = new JButton("Quit");
    
    public Minesweeper(int tRow, int tCol, int tMines) {

    	rows = tRow;
		cols = tCol;
		mines = tMines;
	
    	initialize();
	
    }//constructor
    
    public void initialize() {
	

	JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
	this.add(contentPane);
	
        JPanel p1 = new JPanel();
	p1.setLayout(new BorderLayout());

        contentPane.add(p1, BorderLayout.NORTH);

        JPanel p2 = new JPanel();
	p2.setLayout(new GridLayout(rows,cols));
	
        contentPane.add(p2, BorderLayout.CENTER);

        JPanel p3 = new JPanel();
	p3.setLayout(new BorderLayout());

        contentPane.add(p3, BorderLayout.SOUTH);
       
	topRightLabel = new JLabel(""+rows+"x"+cols);
	p1.add(topRightLabel, BorderLayout.WEST);
	topLeftLabel = new JLabel(""+this.mines);
	p1.add(topLeftLabel, BorderLayout.EAST);
	winLoseLabel = new JLabel("");
	p1.add(winLoseLabel, BorderLayout.CENTER);
	
        isRunning = true;
        clicks = 0;

	//instantiate clickedBoard
        clickedBoard = new boolean[rows][cols];
	for(int rowc = 0; rowc < rows; rowc++) {
	    for(int colc = 0; colc < cols; colc++) {

		clickedBoard[rowc][colc] = false;

	    }
	}
	
	//instantiate mineBoard
	mineBoard = new boolean[rows][cols];
	for(int rowc = 0; rowc < rows; rowc++) {
	    for(int colc = 0; colc < cols; colc++) {

		mineBoard[rowc][colc] = false;

	    }
	}
	
	// Creates and sets the actions for the button display for the user to interact with. 
	display = new JButton[rows][cols];
	for(int rowc = 0; rowc < rows; rowc++) {
	    for(int colc = 0; colc < cols; colc++) {

		display[rowc][colc] = new JButton(" ");
		display[rowc][colc].setSize(50,50);
		display[rowc][colc].addActionListener(this);
		p2.add(this.display[rowc][colc]);

	    }
	}
	
	p3.add(this.quitButton);
	
	quitButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {

		    System.exit(1);

		} // actionPerformed
	    }); // actionListener
	
	
	Random rand = new Random(); 
	
	for(int x = 0; x < mines; x++) {

	    int randomRow = rand.nextInt(this.rows); 
	    int randomCol = rand.nextInt(this.cols);

	    while(mineBoard[randomRow][randomCol] == true) {

		randomRow = rand.nextInt(this.rows); 
		randomCol = rand.nextInt(this.cols);

	    }

	    mineBoard[randomRow][randomCol] = true;

	}
	
    
	
    } // initialize method
    
    /**
     * 
     * Action performed if a button in the display board is clicked.
     * Recursively calls itself if a neigboring button has no adjacent mines.
     * While doing so the button with no adjacent mines displays the number of mines adjacent to is neighbors.
     * This clears up unnecessary clicks while providing useful information about the game.
     *
     * @param ActionEvent e
     * @return void
     *
     **/


    public void actionPerformed(ActionEvent e) {
	
	
	if(isRunning){

	    for(int rw = 0; rw < getRows(); rw++) {
		for( int cl = 0; cl < getCols(); cl++) {

		    if(e.getSource() == display[rw][cl]) {
			if(mineBoard[rw][cl]){
			    isRunning = false;
			    winLoseLabel.setText("     Game Over");
			    display[rw][cl].setBackground(Color.red);
			    display[rw][cl].setText("X");
			} else if(clickedBoard[rw][cl]){
			   
			    break;

			} else {
			    clickedBoard[rw][cl] = true; ++clicks;

			    if(getNumAdjacentMines(rw,cl) == 0) {
			
				display[rw][cl].setText("");
				display[rw][cl].setBackground(new Color(255,204,153));//Beige?

      			      } else {

				    display[rw][cl].setText(Integer.toString(getNumAdjacentMines(rw,cl)));

				}	  

			    int rowx = rw; int colx = cl;
			    int u = rowx - 1; int d = rowx + 1; int l = colx -1; int r = colx +1; // up down left right
			    rowx = rw; colx = cl; rowx = u; colx = l; //iterate up left
			    
			    if(inBounds(rowx,colx)) { 
				if(gameOn(rowx,colx) == true) { 
				   
				    display[rowx][colx].doClick();
			
				} else if(moreGameOn(rowx,colx,rw,cl) == true) {

				    display[rowx][colx].setText(Integer.toString(getNumAdjacentMines(rowx, colx)));
				    clickedBoard[rowx][colx] = true; ++clicks;

				}
			    }//if inBounds

			    rowx = rw; colx = cl; rowx = u;  //iterate up

			    if(inBounds(rowx,colx)) { 
				if(gameOn(rowx,colx) == true) { 
				   
				    display[rowx][colx].doClick();
			
				} else if(moreGameOn(rowx,colx,rw,cl) == true) {

				    display[rowx][colx].setText(Integer.toString(getNumAdjacentMines(rowx, colx)));
				    clickedBoard[rowx][colx] = true; ++clicks;

				}
			    }//if inBounds

			    rowx = rw; colx = cl; rowx = u; colx = r; //iterate up right
	
			    if(inBounds(rowx,colx)) { 
				if(gameOn(rowx,colx) == true) { 
				
				    display[rowx][colx].doClick();
			
				} else if(moreGameOn(rowx,colx,rw,cl) == true) {
				   
				    display[rowx][colx].setText(Integer.toString(getNumAdjacentMines(rowx, colx)));
				    clickedBoard[rowx][colx] = true; ++clicks;
				    
				}
			    }//if inbounds

			    rowx = rw; colx = cl; colx = l; //iterate left

			    if(inBounds(rowx,colx)) { 
				if(gameOn(rowx,colx) == true) { 
				   
				    display[rowx][colx].doClick();
			
				} else if(moreGameOn(rowx,colx,rw,cl) == true) {
				  
				    display[rowx][colx].setText(Integer.toString(getNumAdjacentMines(rowx, colx)));
				    clickedBoard[rowx][colx] = true; ++clicks;

				}
			    }//if inbounds

			    rowx = rw; colx = cl; colx = r; //iterate right
			    if(inBounds(rowx,colx)) { 
				if(gameOn(rowx,colx) == true) { 
				  
				    display[rowx][colx].doClick();

				} else if(moreGameOn(rowx,colx,rw,cl) == true) {

				    display[rowx][colx].setText(Integer.toString(getNumAdjacentMines(rowx, colx)));
				    clickedBoard[rowx][colx] = true; ++clicks;

				}
			    }//if inBounds

			    rowx = rw; colx = cl; rowx = d; colx = l; //iterate down left
			   
			    if(inBounds(rowx,colx)) {
				if(gameOn(rowx,colx) == true) { 
				
				    display[rowx][colx].doClick();
			
				} else if(moreGameOn(rowx,colx,rw,cl) == true) {
			
				    display[rowx][colx].setText(Integer.toString(getNumAdjacentMines(rowx, colx)));
				    clickedBoard[rowx][colx] = true; ++clicks;

				}
			    }//if inBounds

			    rowx = rw; colx = cl; rowx = d; //iterate down
			   
			    if(inBounds(rowx,colx)) {
				if(gameOn(rowx,colx) == true) { 
				
				    display[rowx][colx].doClick();

				} else if(moreGameOn(rowx,colx,rw,cl) == true) {

				    display[rowx][colx].setText(Integer.toString(getNumAdjacentMines(rowx, colx)));
				    clickedBoard[rowx][colx] = true; ++clicks;

				}//else if
			    }//if inBounds

			    rowx = rw; colx = cl; rowx = d; colx = r; //iterate down right

			    if(inBounds(rowx,colx)) { 
				if(gameOn(rowx,colx) == true) {
 
				    display[rowx][colx].doClick();

				} else if(moreGameOn(rowx,colx,rw,cl) == true) {

				    display[rowx][colx].setText(Integer.toString(getNumAdjacentMines(rowx, colx)));
				    clickedBoard[rowx][colx] = true; ++clicks;

		     }//else if
			}//if inBounds
		   }//if getNumAdjacentMines				  
		  }//else
	     }//if e.getSource()
        }//for loop
	   }//for loop
	update();
	}//if isRunning
    
    
    public void update() {

	
	if(((cols*rows)-mines) == clicks) {
	    isRunning = false;
	    winLoseLabel.setText("     Congratulations, you won!");
	}
	
    } // update
    
    /**
     * Returns true if and only if the coordinate is both in bounds and hasn't 
     * been already been clicked.
     */
    public boolean inBounds(int val0, int val1) {
	
	return (((val0 >= 0) && (val0 < rows)) && ((val1 >= 0) && (val1 < cols)));
	
    } // inBounds
    
    /**
     * Returns the number of mines adjacent to the desired coordinate.
     */
    public int getNumAdjacentMines(int rw, int cl) { 
	
	int adj = 0;
	
        int row = rw; int col = cl;
        int u = row - 1; int d = row + 1; int l = col -1; int r = col +1; // up down left right
        row = rw; col = cl; row = u; col = l;
 
       if(inBounds(row,col)) {
	   if(mineBoard[row][col]) {
	       adj++;
	   }
       }
       
	row = rw; col = cl; row = u;
        
	if(inBounds(row,col)) {
	    if(mineBoard[row][col]) {
		adj++;
	    } 
	}
        
	row = rw; col = cl; row = u; col = r;

        if(inBounds(row,col)) {
	    if(mineBoard[row][col]) {
		adj++;
	    }
	}

        row = rw; col = cl; col = l;

        if(inBounds(row,col)) {
	    if(mineBoard[row][col]) {
		adj++;
	    }
	}

        row = rw; col = cl; col = r;

        if(inBounds(row,col)) {
	    if(mineBoard[row][col]) {
		adj++;
	    }
	}

        row = rw; col = cl; row = d; col = l;

        if(inBounds(row,col)) {
	    if(mineBoard[row][col]) {
		adj++;
	    }
	}

        row = rw; col = cl; row = d;

        if(inBounds(row,col)) {
	    if(mineBoard[row][col]) {
		adj++;
	    }
	}

        row = rw; col = cl; row = d; col = r;

        if(inBounds(row,col)) {
	    if(mineBoard[row][col]) {
		adj++;
	    }
	}

        return adj;
	
    } // getNumAdjacentMines

    /**
     * Returns the number of rows in the gameboard.
     */
    public int getRows() {
	
	return rows;
	
    } // getRowns
    
    /**
     * Returns the number of columns in the gameboard.
     */
    public int getCols() {
	
	return cols;
	
    } // getCols
    
    /** 
     * Returns whether or not the game is currently running.
     */
    public boolean isRunning() {
	
	return isRunning;
	
	
    } // isRunning
    public boolean gameOn(int rowx,int colx){ 
	if((!(clickedBoard[rowx][colx])) && (getNumAdjacentMines(rowx, colx)==0) && (!(mineBoard[rowx][colx])) == true){
				   
    	     return true;
			
       	}else{
				
	     return false;
			
	 }
    }//gameOn
	
    public boolean moreGameOn(int rowx,int colx, int rw,int cl) {
	 if((getNumAdjacentMines(rw, cl)==0) && (!(clickedBoard[rowx][colx])) && (!(mineBoard[rowx][colx]))==true){
				
	     return true;
			
	 }else{
				
	     return false;
	      }
         }//moreGameOn

    
    public static void createAndShowGUI(int r, int c, int m) {
	JFrame gframe = new JFrame("Minesweeper");
	gframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	Minesweeper game = new Minesweeper(r, c, m);
	gframe.add(game);
	
	gframe.pack();
	gframe.setVisible(true);
	
    }
    
} // Minesweeper
