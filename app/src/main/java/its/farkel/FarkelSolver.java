package its.farkel;

/**
 * Created by Its on 6/16/2015.
 */
public class FarkelSolver {

    private static boolean initialized = false;
    public static float[][][][][][][] farkel_tree;

    private static void popFarkely(int numZeros) {
        int c, i, j, k, l, m, n;
        Dice hand = new Dice();

        hand.held = 0;

        for (i=0;i<7;i++)
        {
            hand.die[0] = i;
            for (j=0;j<7;j++)
            {
                hand.die[1] = j;
                for (k=0;k<7;k++)
                {
                    hand.die[2] = k;
                    for (l=0;l<7;l++)
                    {
                        hand.die[3] = l;
                        for (m=0;m<7;m++)
                        {
                            hand.die[4] = m;
                            for (n=0;n<7;n++)
                            {
                                hand.die[5] = n;
                                c = 0;
                                if (0==i) c++;
                                if (0==j) c++;
                                if (0==k) c++;
                                if (0==l) c++;
                                if (0==m) c++;
                                if (0==n) c++;
                                if (c == numZeros)
                                    FarkelSolver.farkel_tree[1][hand.die[0]][hand.die[1]][hand.die[2]][hand.die[3]][hand.die[4]][hand.die[5]] = hand.expectedValue();
                            }}}}}}
    }

    private static void popFarkelx() {
        int i, j, k, l, m, n;
        Dice hand = new Dice();

        hand.held = 0;

        for (i=0;i<7;i++)
        {
            hand.die[0] = i;
            for (j=0;j<7;j++)
            {
                hand.die[1] = j;
                for (k=0;k<7;k++)
                {
                    hand.die[2] = k;
                    for (l=0;l<7;l++)
                    {
                        hand.die[3] = l;
                        for (m=0;m<7;m++)
                        {
                            hand.die[4] = m;
                            for (n=0;n<7;n++)
                            {
                                hand.die[5] = n;
                                FarkelSolver.farkel_tree[0][hand.die[0]][hand.die[1]][hand.die[2]][hand.die[3]][hand.die[4]][hand.die[5]] = hand.value();
                            }}}}}}
    }

    private static void popFarkel() {
        popFarkelx();
        popFarkely(0);
        popFarkely(1);
        popFarkely(2);
        popFarkely(3);
        popFarkely(4);
        popFarkely(5);
        popFarkely(6);
    }

    public static void initFarkel() {
        if (!initialized) {
            farkel_tree = new float[2][7][7][7][7][7][7];
            popFarkel();
            initialized = true;
        }
    }
}
