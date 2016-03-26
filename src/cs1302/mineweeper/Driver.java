package cs1302.mineweeper;
/**
 * This class reprents the driver for the game.
 * Complete.
 **/
public class Driver {
   
    /**
     *
     *The main enty point int the game application.
     *
     *@param args command line arguments
     */
    
 
    public static void main(String[] args) {
	javax.swing.SwingUtilities.invokeLater(new Runnable() {
		@Override
		    public void run() {
		    Prompt.createAndShowGUI();
		}
		
	    }); // while
	
    } // main
    
} // Driver