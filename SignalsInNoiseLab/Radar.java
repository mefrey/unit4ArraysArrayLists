import java.util.ArrayList;
/**
 * The model for radar scan and accumulator
 * 
 * @author @gcschmit @mefrey
 * @version 12/18/14
 */
public class Radar
{
    
    /** stores whether each cell triggered detection for the current scan of the radar*/
    private boolean[][] currentScan;
    
    /**2D array representing the different possible velocities*/
    int[][] possibleDxDy = new int[11][11];
    
    /** value of each cell is incremented for each scan in which that cell triggers detection*/ 
    private boolean[][] accumulator;
    
    /** y location of the monster*/
    private int monsterLocationRow;
    /** x location of the monster*/
    private int monsterLocationCol;
    /** x velocity of monster*/
    private int monsterDx;
    /** y velocity of monster*/
    private int monsterDy;

    /** probability that a cell will trigger a false detection (must be >= 0 and < 1)*/
    private double noiseFraction;
    
    /** number of scans of the radar since construction*/
    private int numScans;

    /**
     * Constructor for objects of class Radar
     * @param   rows    the number of rows in the radar grid
     * @param   cols    the number of columns in the radar grid
     */
    public Radar(int rows, int cols)
    {
        // initialize instance variables
        currentScan = new boolean[rows][cols]; // elements will be set to false
        accumulator = new boolean[rows][cols]; // elements will be set to false
        
        // randomly set the location of the monster (can be explicity set through the
        //  setMonsterLocation method
        monsterLocationRow = rows-1;//(int)(Math.random() * rows);
        monsterLocationCol = 0;//(int)(Math.random() * cols);
        
        noiseFraction = 0.05;
        numScans= 0;
    }
    
    /**
     * Performs a scan of the radar. Noise is injected into the grid and the previous scan is saved.
     * updates the possible slope array
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
        //update accumulator for looks
        for(int row = 0; row < accumulator.length; row++)
        {
            for(int col = 0; col < accumulator[0].length; col++)
            {
                accumulator[row][col] = false;
            }
        }
        
        // detect the monster
        monsterLocationRow += monsterDx;
        monsterLocationCol += monsterDy;
        // if location is out of grid it won't run
        if ((monsterLocationRow < currentScan.length && monsterLocationRow>=0) && 
        (monsterLocationCol < currentScan[0].length && monsterLocationCol>=0))
        {
            currentScan[monsterLocationRow][monsterLocationCol] = true;
            accumulator[monsterLocationRow][monsterLocationCol] = true;
        }
        // inject noise into the grid
        injectNoise();
        
        // udpate the possible slope
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
        numScans++;
    }

    /**
     * Sets the location of the monster
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
     * Sets velocity of Monster
     * @pre     |both parameters| are less than 5 
     * @param   dy   velocity in y direction
     * @param  dx   velocity in x direction
     */
    public void setMonsterVelocity(int dy, int dx)
    {
        monsterDx = dx;
        monsterDy = dy;
    }

    
     /**
     * Sets the probability that a given cell will generate a false detection
     * @param   fraction    the probability that a given cell will generate a flase detection expressed
     *                      as a fraction (must be >= 0 and < 1)
     */
    public void setNoiseFraction(double fraction)
    {
        noiseFraction = fraction;
    }
    
    /**
     * Returns true if the specified location in the radar grid triggered a detection.
     * @param   row     the row of the location to query for detection
     * @param   col     the column of the location to query for detection
     * @return true if the specified location in the radar grid triggered a detection
     */
    public boolean isDetected(int row, int col)
    {
        return currentScan[row][col];
    }
    
    /**
     * Returns true if the specified location in the accumulator grid triggered a detection.
     * @param   row     the row of the location to query for detection
     * @param   col     the column of the location to query for detection
     * @return true if the specified location in the accumulator grid triggered a detection
     */
    public boolean getAccumulatedDetection(int row, int col)
    {
        return accumulator[row][col];
    }
    
    /**
     * Returns the number of rows in the radar grid
     * @return the number of rows in the radar grid
     */
    public int getNumRows()
    {
        return currentScan.length;
    }
    
    /**
     * Returns the number of columns in the radar grid
     * @return the number of columns in the radar grid
     */
    public int getNumCols()
    {
        return currentScan[0].length;
    }
    
    /**
     * Returns the number of scans that have been performed since the radar object was constructed
     * @return the number of scans that have been performed since the radar object was constructed
     */
    public int getNumScans()
    {
        return numScans;
    }
    
    /**
     * Returns the y location of monster
     * @return y location of monster
     */
    public int getMonsterLocationRow()
    {
        return this.monsterLocationRow;
    }
    
    /**
     * Returns the x location of monster
     * @return x location of monster
     */
    public int getMonsterLocationCol()
    {
        return this.monsterLocationCol;
    }

     /**
     * Returns the x velocity
     * @return predicted x velocity of monster
     */
    public int getActualDx()
    {
        int max = possibleDxDy[0][0];
        int actualDx = 0;
        for (int i = 0; i < possibleDxDy.length; i++) 
        {
            for (int j = 0; j < possibleDxDy[0].length; j++) 
            {
                if (possibleDxDy[i][j] > max) 
                {
                    max = possibleDxDy[i][j];
                    actualDx = i-5;
                }
            }
        }
        return actualDx;
    }
    
     /**
     * Returns the y velocity
     * @return predicted y velocity of monster
     */
    public int getActualDy()
    {
        int max = possibleDxDy[0][0];
        int actualDy = 0;
        for (int i = 0; i < possibleDxDy.length; i++) 
        {
            for (int j = 0; j < possibleDxDy[0].length; j++) 
            {
                if (possibleDxDy[i][j] > max) 
                {
                    max = possibleDxDy[i][j];
                    actualDy = j-5;
                }
            }
        }
        return actualDy;
    }
    
    /**
     * Sets cells as falsely triggering detection based on the specified probability
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
