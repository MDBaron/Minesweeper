package cs1302.mineweeper;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class Prompt extends JPanel implements ActionListener {
    protected static JFrame frame = new JFrame("Minesweeper");
    private int row, col, mines;
    private String sRow, sCol, sMine;
    private JButton start;
    private JTextField rField, cField, mField;
    private JLabel rLabel, cLabel, mLabel, eLabel;
 
   public Prompt() {

       initialize();

	    }//Prompt Constructor

    
    public void initialize() {
	JPanel contentPane = new JPanel();
	contentPane.setLayout(new BorderLayout());
	GridBagConstraints c = new GridBagConstraints();
	frame.add(contentPane);
	JPanel panel1 = new JPanel();
	panel1.setLayout(new BorderLayout());
	JPanel panel2 = new JPanel();
	panel2.setLayout(new BorderLayout());
	JPanel panel3 = new JPanel();
	panel3.setLayout(new BorderLayout());
	JPanel panela = new JPanel();
	panela.setLayout(new BorderLayout());
	JPanel panelb = new JPanel();
	panelb.setLayout(new BorderLayout());
	
	this.rLabel = new JLabel("Rows:");
	this.cLabel = new JLabel("Columns:");
	this.mLabel = new JLabel("Mines:");
	this.eLabel = new JLabel("Input number of rows, columns, and mines.");
	this.rField = new JTextField("10", 3);
	this.cField = new JTextField("10", 3);
	this.mField = new JTextField("10", 3);
	this.start = new JButton("Start");
	
	this.start.addActionListener(this);
	
	c.gridx = 0;
	c.gridy = 0;
	panela.add(this.rLabel, BorderLayout.LINE_START);
	c.gridx = 1;
        c.gridy = 0;
	panela.add(this.cLabel, BorderLayout.CENTER);
        c.gridx = 2;
        c.gridy = 0;
	panela.add(this.mLabel, BorderLayout.LINE_END);
	c.gridx = 0;
        c.gridy = 1;
	panelb.add(this.rField, BorderLayout.LINE_START);
	c.gridx = 1;
        c.gridy = 1;
	panelb.add(this.cField, BorderLayout.CENTER);
	c.gridx = 2;
        c.gridy = 1;
	panelb.add(this.mField, BorderLayout.EAST);
	c.gridx = 1;
        c.gridy = 2;
	panel2.add(this.eLabel, BorderLayout.CENTER);
	c.gridx = 1;
        c.gridy = 3;
	panel3.add(this.start, BorderLayout.CENTER);
	
	panel1.add(panela, BorderLayout.NORTH);
	panel1.add(panelb, BorderLayout.SOUTH);
	contentPane.add(panel1, BorderLayout.NORTH);
	contentPane.add(panel2, BorderLayout.CENTER);
	contentPane.add(panel3, BorderLayout.SOUTH);
	
	frame.pack();
    }//Initialize 
    
    public void setRows(int r){

	row = r;

    }//setRows    
    
    public int getRows() {
	
	return row;
	
    }//getRows
    
    public void setCols(int c){

	col = c;

    }//setCols
 
    public int getCols() {

	return col; 

    }//getCols

    public void setMines(int m){

	mines = m;

    }//setMines

    public int getMines() {

	return mines;

    }//getMines
    
    public void actionPerformed(ActionEvent e) {
	
	sRow = rField.getText();
	sCol = cField.getText();
	sMine = mField.getText();
	
	try{ row = Integer.parseInt(sRow);
	}
	catch(NumberFormatException exception){
	    eLabel.setText(sRow + " is not a number");
	}
	
	try{ col = Integer.parseInt(sCol);
	}
	catch(NumberFormatException exception){
	    eLabel.setText(sCol + " is not a number");
	}
	
	try{ mines = Integer.parseInt(sMine);
	}
	catch(NumberFormatException exception){
	    eLabel.setText(sMine + " is not a number");
	}
	if(row == 0 || col == 0){}
	else if(mines < 4) {
	    eLabel.setText("Set more than four mines!");
	} else if(mines > ((row*col)/3)) { 
	    eLabel.setText("ERROR, too many mines!");
	} else {
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
		    @Override
			public void run() {
			Minesweeper.createAndShowGUI(getRows(), getCols(), getMines());
		    }
		});
	    this.setVisible(false);
	}
    }//actionPerformed
    
    public static void createAndShowGUI() {
	//frame = new JFrame("Minesweeper");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Prompt prompt = new Prompt();
	frame.setVisible(true);
	
    }//createAndShowGUI
    
}//prompt