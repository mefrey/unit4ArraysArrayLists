
public class Array2D
{

    private int[][] table =
      {
         {1, 2, 3},
         {4, 5, 6},
         {7, 8, 9},
         {10, 11, 12}
      };
    
    public Array2D()
    {
        
    }

    public String toString()
    {
        String str = "";
        
        // table.length is the number of rows in the table
        for (int row = 0; row < table.length; row++)
        {
            //table[row].length is the number of columns in row
            for (int col = 0; col < table[row].length; col++)
            {
                str += table[row][col] + "\t";
            }
            str += "\n";
        }
        return str;
    }
    
    
    public String extractRow( int row )
    {
        String str = "";
        
        for (int col = 0; col < table[row].length; col++){str += table[row][col] + "\t";}
        for (int col : table[row]){str += col + "\t";}

        return str;
    }
    

    public String extractCol(int col)
    {
        String str = "";
        
        for (int row = 0; row < table.length; row++){str += table[row][col] + "\n";}
        for (int[] rowOfInts : table){str += rowOfInts[col] + "\n";}
        return str;
    }


    
    
    public static void main(String[] args)
    
    {
        Array2D array = new Array2D();
        System.out.println(array.toString());
        System.out.println(array.extractRow(1));
        System.out.println(array.extractCol(1));
    }


}
