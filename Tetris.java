import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*******************************************
*  Name: Diana Chung & Mahshad Jalali      *
*  Course: ICS 4M  Pd. 4                   *
*  Summative Project                       *
*  Purpose: Displays the game's main menu  *                                          		   *
*  Due Date: May 24th, 2018            	 *
*******************************************/
class Tetris implements ActionListener
{
   JFrame frame = new JFrame();
   JButton timelessMode = new JButton();
   JButton challenge = new JButton();
   JButton speed = new JButton();
   
   public static void main(String args[]) 
   {
      new Tetris();
   }
   public Tetris()
   {
      ImageIcon icon = new ImageIcon("Start.png");
      JLabel label = new JLabel(icon);
      frame.add(label);
      JPanel panel = new JPanel();
      timelessMode.setText("<html>Timeless<br/><center>Mode</center></html>");
      challenge.setText("<html>The 3 Minute<br/><center>Challenge</center></html>");
      speed.setText("<html>Speed<br/><center>Mode</center></html>");
      timelessMode.addActionListener(this);
      challenge.addActionListener(this);
      speed.addActionListener(this);
      panel.add(timelessMode);
      panel.add(challenge);
      panel.add(speed);
      panel.setBackground(new Color(23, 41, 69));
      frame.add(panel,BorderLayout.SOUTH);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);

   }
   
   /*** actionPerformed*************************************************
   * Purpose: displays buttons that access different modes of the game *
   * Parameters: e - details about the button event                    *
   * Returns: none                                                     *
   ********************************************************************/

   public void actionPerformed(ActionEvent e)
   {
      if (e.getSource() == timelessMode)
      {
         frame.setVisible(false);
         final TimelessMode game = new TimelessMode();
         new Thread() 
         {
			   @Override public void run() 
            {
			  	   while (true) 
               {
			      	try 
                  {
			      	   Thread.sleep(1000);
			      		game.dropDown();
					   } 
                  catch ( InterruptedException e ) 
                  {
                  }
				   }
			   }
		   }.start();
      }   
      else if (e.getSource() == challenge)
      {
         frame.setVisible(false);
         final ThreeMin game = new ThreeMin();
         new Thread() 
         {
			   @Override public void run() 
            {
			  	   while (true) 
               {
			      	try 
                  {
			      	   Thread.sleep(1000);
			      		game.dropDown();
					   } 
                  catch ( InterruptedException e ) 
                  {
                  }
				   }
			   }
		   }.start();

      }
      else if (e.getSource() == speed)
      {
         frame.setVisible(false);
         final Speed game = new Speed();           
         new Thread() 
         {
			   @Override public void run() 
            {
			  	   while (true) 
               {
			      	try 
                  {
			      	   Thread.sleep(1000);
			      		game.dropDown();
					   } 
                  catch ( InterruptedException e ) 
                  {
                  }
				   }
			   }
		   }.start();

      }
   }
}
        
