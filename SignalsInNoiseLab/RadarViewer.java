import javax.swing.JFrame;
import java.util.Scanner;
/**
 * Class that contains the main method for the program and creates the frame containing the component.
 * 
 * @author @gcschmit @mefrey
 * @version 12/18/14
 */
public class RadarViewer
{
    /**
     * main method for the program which creates and configures the frame for the program
     */
    public static void main(String[] args) throws InterruptedException
    {
        // create the radar, set the monster location, and perform the initial scan
        Scanner input = new Scanner(System.in);
        System.out.println("please enter a starting X location for monster between 0-99: ");
        int xLoc = input.nextInt();
        System.out.println("please enter a starting Y location for monster between 0-99: ");
        int yLoc = input.nextInt();
        System.out.println("please enter a dX where |dX| is less than or equal to 5:");
        int dX = input.nextInt();
        System.out.println("please enter a dY where |dY| is less than or equal to 5:");
        int dY = input.nextInt();
        final int ROWS = 100;
        final int COLS = 100;
        Radar radar = new Radar(ROWS, COLS);
        radar.setMonsterLocation(yLoc,xLoc);
        radar.setMonsterVelocity(dX,dY);
        radar.setNoiseFraction(0.010);
        radar.scan();
        
        JFrame frame = new JFrame();
        
        frame.setTitle("Signals in Noise Lab");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // a frame contains a single component; create the radar component and add it to the frame
        RadarComponent component = new RadarComponent(radar);
        frame.add(component);
        
        // set the size of the frame to encompass the contained component
        frame.pack();
        
        // make the frame visible which will result in the paintComponent method being invoked on the
        //  component.
        frame.setVisible(true);
        
        // perform 100 scans of the radar wiht a slight pause between each
        // after each scan, instruct the Java Run-Time to redraw the window
        boolean onScreen = true;
        while(onScreen == true)
        {
            Thread.sleep(100); // sleep 100 milliseconds (1/10 second)
            
            radar.scan();
            
            frame.repaint();
            if ((radar.getMonsterLocationRow() > ROWS || radar.getMonsterLocationRow() < 0) || 
            (radar.getMonsterLocationCol() > COLS || radar.getMonsterLocationRow() < 0))
            {
                onScreen = false;
            }
        }
        //find velocity
        System.out.println("Monster's velocity is "+radar.getActualDx()+" in the x direction and "
        +radar.getActualDy()+" in the y direction");
        //find start position
        int monsterYStart = radar.getMonsterLocationRow()-(radar.getActualDy()*radar.getNumScans());
        int monsterXStart = radar.getMonsterLocationCol()-(radar.getActualDx()*radar.getNumScans());
        System.out.print("The monster's starting position was ("+monsterXStart+", "+monsterYStart+")");
    }

}
