
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RadarTest.
 *
 * @author  @mefrey
 * @version 12/18/14
 */
public class RadarTest
{
    /**
     * Default constructor for test class RadarTest
     */
    public RadarTest()
    {
    }

    /**
     * tests finding monster velocity and start position
     */
    @Test
    public void monsterTest()
    {
        //start at position (0,0) with positive dx and dy
        Radar radar1 = new Radar(100, 100);
        radar1.setMonsterLocation(0,0);
        radar1.setMonsterVelocity(1,1);
        radar1.setNoiseFraction(0.010);
        radar1.scan();
        boolean onScreen = true;
        while(onScreen == true)
        {
            radar1.scan();
            if ((radar1.getMonsterLocationRow() > 100 || radar1.getMonsterLocationRow() < 0) || 
            (radar1.getMonsterLocationCol() > 100 || radar1.getMonsterLocationRow() < 0))
            {
                onScreen = false;
            }
        }
        assertEquals(1,radar1.getActualDx());
        
        assertEquals(1,radar1.getActualDy());
        
        assertEquals(0,radar1.getMonsterLocationRow()-(radar1.getActualDy()*radar1.getNumScans()));
        
        assertEquals(0,radar1.getMonsterLocationCol()-(radar1.getActualDx()*radar1.getNumScans()));
        
        //start at position (99,99) with negative dx and dy
        Radar radar2 = new Radar(100, 100);
        radar2.setMonsterLocation(99,99);
        radar2.setMonsterVelocity(-1,-1);
        radar2.setNoiseFraction(0.010);
        radar2.scan();
        onScreen = true;
        while(onScreen == true)
        {
            radar2.scan();
            if ((radar2.getMonsterLocationRow() > 100 || radar2.getMonsterLocationRow() < 0) || 
            (radar2.getMonsterLocationCol() > 100 || radar2.getMonsterLocationRow() < 0))
            {
                onScreen = false;
            }
        }
        assertEquals(-1,radar2.getActualDx());

        assertEquals(-1,radar2.getActualDy());

        assertEquals(99,radar2.getMonsterLocationRow()-(radar2.getActualDy()*radar2.getNumScans()));

        assertEquals(99,radar2.getMonsterLocationCol()-(radar2.getActualDx()*radar2.getNumScans()));

        //start at position (49,49) with positive dx and no dy
        Radar radar3 = new Radar(100, 100);
        radar3.setMonsterLocation(49,49);
        radar3.setMonsterVelocity(1,0);
        radar3.setNoiseFraction(0.010);
        radar3.scan();
        onScreen = true;
        while(onScreen == true)
        {
            radar3.scan();
            if ((radar3.getMonsterLocationRow() > 100 || radar3.getMonsterLocationRow() < 0) || 
            (radar3.getMonsterLocationCol() > 100 || radar3.getMonsterLocationRow() < 0))
            {
                onScreen = false;
            }
        }
        assertEquals(1,radar3.getActualDx());

        assertEquals(0,radar3.getActualDy());

        assertEquals(49,radar3.getMonsterLocationRow()-(radar3.getActualDy()*radar3.getNumScans()));

        assertEquals(49,radar3.getMonsterLocationCol()-(radar3.getActualDx()*radar3.getNumScans()));
    }
}
