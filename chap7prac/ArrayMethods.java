
public class ArrayMethods
{
    private int[] values;
   
    public ArrayMethods(int[] initialValues) {this.values = initialValues;}
    
    public void string()
    {
        for (int i = 0; i < this.values.length; i++)
        {
            System.out.println(values[i]);
        }
    }
    //A.
    public void swapFirstAndLast() 
    {
        int index1 = this.values[0];
        this.values[0] = this.values[this.values.length - 1];
        this.values[this.values.length - 1] = index1;
    }
    //B.
    public void shiftRight() 
    {
        int lastIndex = this.values[this.values.length-1];
        for (int i = this.values.length-1; i >= 1; i--)
        {
            this.values[i] = this.values[i-1];
        }
        this.values[0] = lastIndex;
    }
    //C.
    public void replaceEven()
    {
        for (int i = 0; i < values.length; i++)
        {
            if (this.values[i] % 2 == 0)
            {
                this.values[i] = 0;
            }
        }
    }
    //D.
    public void replaceWithNeighbors()
    {
        int prevValue = values[0];
        for (int i = 1; i < this.values.length-1; i++)
        {
            int temp = values[i];
            if (prevValue > this.values[i])
            {
                this.values[i] = prevValue;
            }
            else
            {
                this.values[i] = this.values[i+1];
            }
            prevValue = temp;
        }
    }
    //E.
    public void removeMiddle()
    {
        int size = 0;
        if (this.values.length % 2 == 0)
        {
            size = this.values.length-2;
        }
        else
        {
            size = this.values.length-1;
        }
        int[] newArray = new int[size];
        if (this.values.length % 2 == 0)
        {
            int index = 0;
            for (int i = 0; i < this.values.length; i++)
            {
                if (i != (this.values.length / 2-1) && i != (this.values.length / 2))
                {
                    newArray[index] = this.values[i];
                    index++;
                }
                
            }
            
        }
        if (this.values.length % 2 != 0)
        {
            int index = 0;
            for (int i = 0; i < this.values.length; i++)
            {
                if (i != (this.values.length / 2))
                {
                    newArray[index] = this.values[i];
                    index++;
                }
                
            }
            
        }
        this.values = newArray;
    }
    
    //F.
    public void moveEven()
    {
        int[] newArray = new int[this.values.length];
        int index = 0;
        for (int i = 0; i < this.values.length; i++)
        {
            if (this.values[i] % 2 == 0)
            {
                newArray[index] = this.values[i];
                index++;
            }
            
        }
        for (int i = 0; i < this.values.length; i++)
        {
            if (this.values[i] % 2 != 0)
            {
                newArray[index] = this.values[i];
                index++;
            }
            
        }
        this.values = newArray;
    }
    
    //G.
    public int secondLargest()
    {
        int first = 0;
        int second = 0;
        for (int i = 0; i < this.values.length; i++)
        {
            if (this.values[i] > first)
            {
                second = first;
                first = this.values[i];
            }
        }
        return second;
    }
    
    //H.
    public boolean increasing()
    {
        boolean isTrue = true;
        for (int i = 1; i < this.values.length; i++)
        {
            if (this.values[i] < this.values[i-1])
            {
                isTrue = false;
            }
        }
        return isTrue;
    }
    
    //I.
    public boolean twoAdjecent()
    {
        boolean isTrue = false;
        for (int i = 1; i < this.values.length; i++)
        {
            if (this.values[i] == this.values[i-1])
            {
                isTrue = true;
            }
        }
        return isTrue;
    }
    
    //J.
    public boolean duplicates()
    {
        boolean isTrue = false;
        for (int i = 1; i < this.values.length; i++)
        {
            for (int j = i+1; j < this.values.length; j++)
            {
                if (this.values[i] == this.values[j])
                {
                    isTrue = true;
                }
            }
        }
        return isTrue;
    }

    public int[] getValues(){return this.values;}

}
