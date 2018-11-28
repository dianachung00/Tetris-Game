import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel;

/******************************************************
*  Name: Diana Chung & Mahshad Jalali               	*
*  Course: ICS 4M  Pd. 4                              *
*  Summative Project                                  *
*  Purpose: Clear 8 rows in the least amount of time  *                                           		   *
*  Due Date: May 24th, 2018                           *
******************************************************/


public class Speed implements KeyListener, ActionListener
{
   Drawing drawing = new Drawing();
	Draw draw = new Draw();
   JFrame fr = new JFrame("Tetris Template");
   TimeKeeper time = new TimeKeeper();
   int hours=0, minutes=0, seconds=0;
   JButton home = new JButton("Home");
   public Speed()
   {
       fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
       fr.setSize(500, 660);
       fr.setResizable(false);
       JPanel pane = (JPanel) fr.getContentPane();
       fr.getContentPane().setBackground(new Color (23, 41, 69) );
       fr.add(drawing);
       fr.setVisible(true);
       fr.setResizable(false);       
       fr.setVisible(true);
       fr.addKeyListener(this);
       JPanel panel = new JPanel();
       fr.add(panel,BorderLayout.NORTH);
       panel.setBackground(new Color (23, 41, 69) );
       panel.add(home);
       home.addActionListener(this);
       init();
       time.start();
       fr.requestFocus();

   }
   public static void main(String[] args) 
   {

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
   
   /*** init *********************************************
   * Purpose: Create a border around the well and        *
   *          initializes the dropping piece             *
   * Parameters: none                                    *
   * Returns: none                                       *
   ******************************************************/

	public void init() 
   {
	   backgroundwell = new Color[12][24];
      if(play)
      {
	      for (int i =0; i < 12; i++) 
         {
			   for (int j = 0; j < 23; j++)
            {
				   if (i ==3 || i == 11 || j == 22) 
               {
					   backgroundwell[i][j] = Color.BLACK;
				   }
               else
               {
					   backgroundwell[i][j] = Color.BLACK;
				   }
			   }
		   }
		   newPiece();
       }
            
	}


	private final Point[][][] Tetraminos = 
   {
			// I-Piece
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }
			},
			
			// J-Piece
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
			},
			
			// L-Piece
			{
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
				{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
			},
			
			// O-Piece
			{
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
			},
			
			// S-Piece
			{
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
				{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
			},
			
			// T-Piece
			{
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
				{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
				{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }
			},
			
			// Z-Piece
			{
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
				{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
				{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
			}
	};
	
	private final Color[] tetraminoColors = 
   {
		Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red
	};
	
	public Point pieceOrigin;
	public int currentPiece;
   public int holdPiece;
   public int aPiece;
	public int rotation;
	public ArrayList<Integer> nextPieces = new ArrayList<Integer>();
	public long score;
   public int level;   
	int rowsCleared = 0;
	public Color[][] backgroundwell;
   boolean play = true;
   boolean Hold = false;
   int next;
   int hold = 0;
   boolean holdEmpty = true;
   boolean  done = false;   
   
   /*** newPiece*************************************************
   * Purpose: Put a new, random piece into the dropping position*
   * Parameters: none                                           *
   * Returns: none                                              *
   *************************************************************/

	public void newPiece() 
   {
      done = false;
		pieceOrigin = new Point(5, 2);
		rotation = 0;
      if(play)
      {
		   if (nextPieces.isEmpty())
         {
			   Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
		 	   Collections.shuffle(nextPieces);
		   }
		   currentPiece = nextPieces.get(0);
		   nextPieces.remove(0);
         
      }
      
	}
   
	/*** hold*****************************************************
   * Purpose: Hold new tetramino pieces to the game             *
   * Parameters: none                                           *
   * Returns: none                                              *
   *************************************************************/

   public void hold()
   {
      pieceOrigin = new Point(7, 2);
		rotation = 0;
      if(Hold)
      {
         if(holdEmpty)
         {
            pieceOrigin.y++;	
		      holdPiece = currentPiece;
            currentPiece = nextPieces.get(0); 
            holdEmpty = false; 
         }
         else
         {		
            pieceOrigin.y++;
            aPiece = holdPiece;
            holdPiece = currentPiece;
            currentPiece = aPiece;
         } 
      }
   }

	/*** collidesAt***********************************************
   * Purpose:    Collision test for the dropping piece          *
   * Parameters: x-  the x coordinate                           *
   *             y- the y coordinate                            *
   *             rotation - which way it rotates                *
   * Returns: true/false                                        *
   *************************************************************/

	public boolean collidesAt(int x, int y, int rotation) 
   {
		for (Point p : Tetraminos[currentPiece][rotation]) 
      {
			if (backgroundwell[p.x + x][p.y + y] != Color.BLACK) 
         {
				if(done && p.y+y<=4)
               play = false;
            else 
               done = false;
               
            return true;
			}
         done = true;
		}
		return false;
	}
	
	/*** rotate **************************************************
   * Purpose: Rotate the piece clockwise or counterclockwise    *
   * Parameters: none                                           *
   * Returns: none                                              *
   *************************************************************/
	public void rotate(int i)
   {
		int newRotation = (rotation + i) % 4;
		if (newRotation < 0) 
      {
			newRotation = 3;
		}
		if (!collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) 
      {
			rotation = newRotation;
		}
		drawing.repaint();
	}
	
	/*** move** **************************************************
   * Purpose: Move the piece left or right                      *
   * Parameters: none                                           *
   * Returns: none                                              *
   *************************************************************/

	public void move(int i) 
   {
		if (!collidesAt(pieceOrigin.x + i, pieceOrigin.y, rotation)) 
      {
			pieceOrigin.x += i;	
		}
		drawing.repaint();
	}
	
	/*** dropDown***************************************************************
   * Purpose: Drops the piece one line or fixes it to the well if it can't drop*
   * Parameters: none                                                          *
   * Returns: none                                                             *
   ****************************************************************************/

	public void dropDown()
   {
		if(play)
      {
         if (!collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotation))
         {
			   pieceOrigin.y += 1;
		   }
         else
         {
			   fixToWell();
		   }	
		   drawing.repaint();
      }
	}
	
	/*** fixToWell************************************************
   * Purpose: Make the dropping piece part of the well so there *
   *          can be a collision detection                      *
   * Parameters: none                                           *
   * Returns: none                                              *
   *************************************************************/

	public void fixToWell()
   {
		for (Point p : Tetraminos[currentPiece][rotation])
      {
			backgroundwell[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = tetraminoColors[currentPiece];
		}
		clearRows();
		newPiece();
	}
   
   /*** showNext*******************************************
   * Purpose: Shows the next Piece that is about to fall  *
   * Parameters: graphics - in order to draw the piece    *
   * Returns: none                                        *
   ********************************************************/

   public void showNext(Graphics g)
   {
      Point Origin = new Point(24, 12);
      if (nextPieces.isEmpty())
      {
			Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
		 	Collections.shuffle(nextPieces);
		}
      if(play)
      { 
            g.setColor(tetraminoColors[nextPieces.get(0)]);
		      for (Point p : Tetraminos[nextPieces.get(0)][0]) 
            {
			      g.fillRect((p.x + Origin.x) * 14, 
			   		   (p.y + Origin.y) * 14,13, 13);
            }
      }

   }

	/*** showHold*******************************************
   * Purpose: Shows the piece being held(if any)          *
   * Parameters: graphics - in order to draw the piece    *
   * Returns: none                                        *
   ********************************************************/
   public void showHold(Graphics g)
   {
  
      Point origin = new Point(30, 12);
      if(!holdEmpty)
      {
      g.setColor(tetraminoColors[holdPiece]);
      
		   for (Point p : Tetraminos[holdPiece][0]) 
         {
			   g.fillRect((p.x + origin.x) * 14, 
			   		   (p.y + origin.y) * 14,13, 13);
         }
      }
    
      
   }
   
   /*** deleteRow************************************************
   * Purpose: Delete the entire row when it is full             *
   * Parameters: row - number of rows to be deleted             *
   * Returns: none                                              *
   *************************************************************/

	public void deleteRow(int row)
   {
		for (int j = row-1; j >= 0; j--) 
      {
			for (int i = 1; i < 11; i++) 
         {
				backgroundwell[i][j+1] = backgroundwell[i][j];
			}
		}
	}
	
	 /*** clearRows************************************************
   * Purpose: Clear completed rows from the field               *
   * Parameters: none                                           *
   * Returns: none                                              *
   *************************************************************/

	public void clearRows() 
   {
		boolean gap;
		for (int j = 21; j > 0; j--) 
      {
			gap = false;
			for (int i = 1; i < 11; i++) 
         {
				if (backgroundwell[i][j] == Color.BLACK)
            {
					gap = true;
					break;
				}
			}
			if (!gap) 
         {
				deleteRow(j);
				j += 1;
				rowsCleared++;
			}
		}
      if(rowsCleared!=0 && rowsCleared%5==0)
      {
         level++;
      }
		
		switch (rowsCleared) 
      {
		case 1:
			score += 100;
			break;
		case 2:
			score += 300;
			break;
		case 3:
			score += 500;
			break;
		case 4:
			score += 800;
			break;
		}
      gameOver();
	}
	
	/*** drawPiece ***********************************************
   * Purpose: Draw the falling piece                            *
   * Parameters: none                                           *
   * Returns: none                                              *
   *************************************************************/

	public void drawPiece(Graphics g) 
   {		
		done = false;
      g.setColor(tetraminoColors[currentPiece]);
      if(play)
      {
		   for (Point p : Tetraminos[currentPiece][rotation]) 
         {
			   g.fillRect((p.x + pieceOrigin.x) * 26, 
			   		   (p.y + pieceOrigin.y) * 26,25, 25);
         }
		}
	}
	
   //timer
   class TimeKeeper extends Thread
   {
      public void run()
      {
         try
         {
            seconds = 1;
            minutes = 0;
            while (minutes >= 0 && seconds>0)
            {
               sleep(1000);
               seconds++;
               if (seconds %60==0)
               {
                  seconds= 0;
                  minutes++;
                  seconds++;
               }
               drawing.repaint();
            }
            
         }
         catch (InterruptedException e)
         {
         }
      }
   }
   
   /*** gameOver******************************************
   * Purpose: Creates a Game Over JFrame (pop-up)        *
   * Parameters:none                                     *
   * Returns: none                                       *
   ******************************************************/

   int choice;
   public void gameOver()
   {
      if(rowsCleared==8)
      {
         JFrame over = new JFrame();
         choice = 1;
         over.add(draw);
         over.setSize(330,200);
         over.setVisible(true);
         play = false;       
      }
      if(!play)
      {
         JFrame over = new JFrame();
         choice = 2;
         over.add(draw);
         over.setSize(330,200);
         over.setVisible(true);
         play = false;  
      }
   }	
   
   /*** Drawing*******************************************
   * Purpose: Prints String and other graphics needed    *
   * Parameters: g - Graphics object for drawing on      *
   * Returns: none                                       *
   ******************************************************/

	class Drawing extends JComponent
   {
       public void paint (Graphics g)
       {
         g.setColor(Color.white);
         g.drawString("Rows Cleared",370,300);
         g.setColor(Color.orange);
         g.fillRect(380,305,50,26);
         g.setColor(Color.white);
         g.drawString("Next",345,145);
         g.drawString("Hold",435,145);
         g.drawString("Score",385,30);
         g.drawRect(335,35,130,20);
         g.fillRect(0, 27, 26*12, 26*23);
		   for (int i = 0; i < 12; i++) 
         {
			   for (int j = 1; j < 23; j++) 
            {
				   g.setColor(backgroundwell[i][j]);
				   g.fillRect(26*i, 26*j, 25, 25);
			   }
		   }
         g.setColor(Color.WHITE);
		   g.drawString("" + score,397, 50);
         g.setColor(Color.BLACK);
         g.drawString("" + rowsCleared,401, 323);
		   drawPiece(g);
         showNext(g);
         showHold(g);
         g.setColor(Color.white);
          if (seconds <10)
            g.drawString( minutes + ":0" + seconds, 395,400);
         else
            g.drawString( minutes + ":" + seconds, 395,400);
	    }
   }
   
   /*** Drawing*******************************************
   * Purpose: Prints String and other graphics needed    *
   * to indicate the game is over                        *
   * Parameters: g - Graphics object for drawing on      *
   * Returns: none                                       *
   ******************************************************/

   class Draw extends JComponent
   {
      public void paint (Graphics g)
      {
         if (choice == 1)
         {
            if(minutes<0)
            g.drawString("You finished in"+minutes+ "0:"+seconds,100,50);
            else
            {
               g.drawString("You finished in"+minutes+":"+seconds,100,50);
            }
               g.drawString("GAME OVER!",120,80);
         }
         else 
         {
            g.drawString("GAME OVER!",120,50);
            g.drawString("You could not finish the game",80,80);
         }
      }  

    }
   
   /*** actionPerformed**************************************
   * Purpose: displays a button to go back to the main menu *
   * Parameters: e - details about the button event         *
   * Returns: none                                          *
   *********************************************************/

   public void actionPerformed(ActionEvent e)
   {
      if(e.getSource() == home)
      {
         fr.setVisible(false);
         new Tetris();
         fr.requestFocus();
      }
   }
   
	
   public void keyTyped(KeyEvent e) 
   {
	}
	
    /*** keyPressed***************************************
   * Purpose: Deal with the button being pressed         *
   * Parameters: e - details about the button event      *
   * Returns: none                                       *
   ******************************************************/
		
	public void keyPressed(KeyEvent e) 
   {
	   switch (e.getKeyCode()) 
      {
			case KeyEvent.VK_SPACE:
         Hold = true;
			hold();
			break;
			case KeyEvent.VK_LEFT:
			move(-1);
			break;
			case KeyEvent.VK_RIGHT:
			move(+1);
			break;
		   case KeyEvent.VK_DOWN:
			dropDown();
			break;
         case KeyEvent.VK_UP:
			rotate(+1);
			break;
		} 
   }

			
	public void keyReleased(KeyEvent e) 
   {
	}
		
		
}