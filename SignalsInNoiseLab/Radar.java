import java.util.ArrayList;
/**
 * The model for radar scan and accumulator
 * 
 * @author @gcschmit
 * @version 19 July 2014
 */
public class Radar
{
    
    // stores whether each cell triggered detection for the current scan of the radar
    private boolean[][] currentScan;
    
    // value of each cell is incremented for each scan in which that cell triggers detection 
    private int[][] accumulator;
    
    // location of the monster
    private int monsterLocationRow;
    private int monsterLocationCol;
    // velocity of monster
    private int monsterDx;
    private int monsterDy;

    // probability that a cell will trigger a false detection (must be >= 0 and < 1)
    private double noiseFraction;
    
    // number of scans of the radar since construction
    private int numScans;

    /**
     * Constructor for objects of class Radar
     * 
     * @param   rows    the number of rows in the radar grid
     * @param   cols    the number of columns in the radar grid
     */
    public Radar(int rows, int cols)
    {
        // initialize instance variables
        currentScan = new boolean[rows][cols]; // elements will be set to false
        accumulator = new int[rows][cols]; // elements will be set to 0
        
        // randomly set the location of the monster (can be explicity set through the
        //  setMonsterLocation method
        monsterLocationRow = rows-1;//(int)(Math.random() * rows);
        monsterLocationCol = 0;//(int)(Math.random() * cols);
        
        noiseFraction = 0.05;
        numScans= 0;
    }
    
    /**
     * Performs a scan of the radar. Noise is injected into the grid and the accumulator is updated.
     * 
     */
    public void scan()
    {
        //save the current scan for comparison
        boolean[][] prevScan = new boolean[currentScan.length][currentScan[0].length];
         for(int row = 0; row < prevScan.length; row++)
        {
            for(int col = 0; col < prevScan[0].length; col++)
            {
                prevScan[row][col] = currentScan[row][col];
            }
        }
        // zero the current scan grid
        for(int row = 0; row < currentScan.length; row++)
        {
            for(int col = 0; col < currentScan[0].length; col++)
            {
                currentScan[row][col] = false;
            }
        }
        
        // detect the monster
        monsterLocationRow += monsterDx;
        monsterLocationCol += monsterDy;
        if (monsterLocationRow < currentScan.length && monsterLocationCol < currentScan[0].length)
        {
            currentScan[monsterLocationRow][monsterLocationCol] = true;
        }
        // inject noise into the grid
        injectNoise();
        
        // udpate the accumulator
        int[][] possibleDxDy = new int[11][11];
        for(int prevRow = 0; prevRow < prevScan.length; prevRow++)
        {
            for(int prevCol = 0; prevCol < prevScan[0].length; prevCol++)
            {
                if(prevScan[prevRow][prevCol] == true)
                {
                   for(int currentRow = 0; currentRow < currentScan.length; currentRow++)
                    {
                        for(int currentCol = 0; currentCol < currentScan[0].length; currentCol++)
                        {
                            if(currentScan[currentRow][currentCol] == true &&
                                Math.abs(prevRow-currentRow) <= 5 &&
                                Math.abs(prevCol-currentCol) <= 5)
                            {
                                int changeY = 0;
                                int changeX = 0;
                                if(currentRow < prevCol)
                                {
                                    changeY = -(prevCol-currentCol);
                                }
                                else
                                {
                                    changeY = currentCol-prevCol;
                                }
                                if(currentCol < prevRow)
                                {
                                    changeX = -(prevRow-currentRow);
                                }
                                else
                                {
                                    changeX = currentRow-prevRow;
                                }
                                possibleDxDy[changeY+5][changeX+5] += 1;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < possibleDxDy.length; i++)
        {
            for (int j = 0; i < possibleDxDy.length; i++)
            {
                System.out.print(possibleDxDy[i][j] + " ");
            }
            System.out.println();
        }
        // keep track of the total number of scans
        numScans++;
    }

    /**
     * Sets the location of the monster
     * 
     * @param   row     the row in which the monster is located
     * @param   col     the column in which the monster is located
     * @pre row and col must be within the bounds of the radar grid
     */
    public void setMonsterLocation(int row, int col)
    {
        // remember the row and col of the monster's location
        monsterLocationRow = row;
        monsterLocationCol = col;
        
        // update the radar grid to show that something was detected at the specified location
        currentScan[row][col] = true;
    }



    /**
     * An example of a method - replace this comment with your own
     *  that describes the operation of the method
     *
     * @pre     preconditions for the method
     *          (what the method assumes about the method's parameters and class's state)
     * @post    postconditions for the method
     *          (what the method guarantees upon completion)
     * @param   y   description of parameter y
     * @return  description of the return value
     */
    public void setMonsterVelocity(int dy, int dx)
    {
        monsterDx = dx;
        monsterDy = dy;
    }

    
     /**
     * Sets the probability that a given cell will generate a false detection
     * 
     * @param   fraction    the probability that a given cell will generate a flase detection expressed
     *                      as a fraction (must be >= 0 and < 1)
     */
    public void setNoiseFraction(double fraction)
    {
        noiseFraction = fraction;
    }
    
    /**
     * Returns true if the specified location in the radar grid triggered a detection.
     * 
     * @param   row     the row of the location to query for detection
     * @param   col     the column of the location to query for detection
     * @return true if the specified location in the radar grid triggered a detection
     */
    public boolean isDetected(int row, int col)
    {
        return currentScan[row][col];
    }
    
    /**
     * Returns the number of times that the specified location in the radar grid has triggered a
     *  detection since the constructor of the radar object.
     * 
     * @param   row     the row of the location to query for accumulated detections
     * @param   col     the column of the location to query for accumulated detections
     * @return the number of times that the specified location in the radar grid has
     *          triggered a detection since the constructor of the radar object
     */
    public int getAccumulatedDetection(int row, int col)
    {
        return accumulator[row][col];
    }
    
    /**
     * Returns the number of rows in the radar grid
     * 
     * @return the number of rows in the radar grid
     */
    public int getNumRows()
    {
        return currentScan.length;
    }
    
    /**
     * Returns the number of columns in the radar grid
     * 
     * @return the number of columns in the radar grid
     */
    public int getNumCols()
    {
        return currentScan[0].length;
    }
    
    /**
     * Returns the number of scans that have been performed since the radar object was constructed
     * 
     * @return the number of scans that have been performed since the radar object was constructed
     */
    public int getNumScans()
    {
        return numScans;
    }
    
    /**
     * Sets cells as falsely triggering detection based on the specified probability
     * 
     */
    private void injectNoise()
    {
        for(int row = 0; row < currentScan.length; row++)
        {
            for(int col = 0; col < currentScan[0].length; col++)
            {
                // each cell has the specified probablily of being a false positive
                if(Math.random() < noiseFraction)
                {
                    currentScan[row][col] = true;
                }
            }
        }
    }
    
}
