import java.util.Comparator;

public class CostComparator implements Comparator<State>
{
    @Override
    public int compare(State x, State y)
    {
        // Assume neither string is null. Real code should
        // probably be more robust
        // You could also just return x.length() - y.length(),
        // which would be more efficient.
        if (x.cost < y.cost)
        {
            return -1;
        }
        if (x.cost > y.cost)
        {
            return 1;
        }
        return 0;
    }
}
