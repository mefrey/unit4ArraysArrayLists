import java.util.ArrayList;
public class ReverseRemove
{
    public static void searchAndDestroy( ArrayList<String> list, String match )
    {
        for (int i = list.size() -1; i >= 0; i--)
        {
            if (match.equals( list.get(i) ))
            {
                list.remove(i);
            }
        }
    }

}
