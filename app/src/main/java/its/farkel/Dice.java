package its.farkel;

/**
 * Created by Its on 6/16/2015.
 */
public class Dice {
    int held;
    int total;
    int[] die;

    public Dice() {
        held = 0;
        total = 0;
        this.die = new int[6];
    }

    public Dice(Dice original) {
        this.die = new int[6];
        this.copy(original);
    }

    public void copy(Dice newDie) {
        this.held = newDie.held;
        this.total = newDie.total;
        for (int i = 0; i < 6; i++)
            this.die[i] = newDie.die[i];
    }

    public boolean addDie(int num) {
        if (total < 6) {
            die[total++] = num;
            return true;
        } else {
            return false;
        }
    }

    public boolean removeDie(int num) {
        for(int i = total - 1; i >= 0; i--) {
            if (die[i] == num) {
                if(i < held) held--;

                for(  ; i < total - 1; i++) {
                    die[i] = die[i+1];
                }
                total--;
                return true;
            }
        }
        return false;
    }

    public void empty() {
        held = 0;
        total = 0;
    }

    public int value() {

        if(total != 6) return 0;

        int i, c, r = 0;
        // d[0] = number non-zero inputs, aka number of dice evaluating
        // d[i] = number of i's rolled
        int[] d = {0,0,0,0,0,0,0};

        d[this.die[0]]++;
        d[this.die[1]]++;
        d[this.die[2]]++;
        d[this.die[3]]++;
        d[this.die[4]]++;
        d[this.die[5]]++;
        d[0] = 6 - d[0];

        for (i=1,c=0;i<=6;i++) // 6 of a kind
            if (6 == d[i]) c++;
        if (1 == c)
            return 3000;

        for (i=1,c=0;i<=6;i++) // two triplets
            if (3 == d[i]) c++;
        if (2 == c)
            return 2500;

        for (i=1,c=0;i<=6;i++) // three pairs
            if (2 == d[i]) c++;
        if (3 == c)
            return 1500;

        for (i=1,c=0;i<=6;i++) // straight
            if (1 == d[i]) c++;
        if (6 == c)
            return 1500;

        for (i=1,c=0;i<=6;i++) // 5 of a kind
            if (5 == d[i]) c++;
        if (1 == c) {
            r = 2000;
            if (d[1] < 5) r += 100*d[1];
            if (d[5] < 5) r += 50*d[5];
            return r;
        }

        for (i=1,c=0;i<=6;i++) // 4 of a kind
            if (4 == d[i]) c++;
        if (1 == c) {
            r = 1000;
            if (d[1] < 4) r += 100*d[1];
            if (d[5] < 4) r += 50*d[5];
            return r;
        }

        for (i=2,c=0;i<=6;i++) // 3 of a [2-6]
            if (3 == d[i]) {c++;r=i;}
        if (1 == c) {
            r = 100*r;
            if (d[1] < 3) r += 100*d[1];
            if (d[5] < 3) r += 50*d[5];
            return r;
        }

        r = 0;
        r += 100*d[1];
        r += 50*d[5];
        return r;
    }

    public float expectedValue() {

        if(total != 6) return 0.0f;

        int c, i, j, k, l, m, n;
        double curVal, expRollVal;
        Dice newHand = new Dice();

        c = 0;
        if (0==die[0]) c++;
        if (0==die[1]) c++;
        if (0==die[2]) c++;
        if (0==die[3]) c++;
        if (0==die[4]) c++;
        if (0==die[5]) c++;

        curVal = FarkelSolver.farkel_tree[0][this.die[0]][this.die[1]][this.die[2]][this.die[3]][this.die[4]][this.die[5]];

        if (0 == c) return (float)curVal;

        newHand.held = 6-c;
        expRollVal = 0.0;

        for (i = (die[0] == 0)?1:0; i<=6; i++)
        {
            if (0 == i) {
                newHand.die[0] = die[0];
                i = 7;
            } else {
                newHand.die[0] = i;
            }
            for (j = (die[1] == 0)?1:0; j<=6; j++)
            {
                if (0 == j) {
                    newHand.die[1] = die[1];
                    j = 7;
                } else {
                    newHand.die[1] = j;
                }
                for (k = (die[2] == 0)?1:0; k<=6; k++)
                {
                    if (0 == k) {
                        newHand.die[2] = die[2];
                        k = 7;
                    } else {
                        newHand.die[2] = k;
                    }
                    for (l = (die[3] == 0)?1:0; l<=6; l++)
                    {
                        if (0 == l) {
                            newHand.die[3] = die[3];
                            l = 7;
                        } else {
                            newHand.die[3] = l;
                        }
                        for (m = (die[4] == 0)?1:0; m<=6; m++)
                        {
                            if (0 == m) {
                                newHand.die[4] = die[4];
                                m = 7;
                            } else {
                                newHand.die[4] = m;
                            }
                            for (n = (die[5] == 0)?1:0; n<=6; n++)
                            {
                                if (0 == n) {
                                    newHand.die[5] = die[5];
                                    n = 7;
                                } else {
                                    newHand.die[5] = n;
                                }
                                if (FarkelSolver.farkel_tree[0][newHand.die[0]][newHand.die[1]][newHand.die[2]][newHand.die[3]][newHand.die[4]][newHand.die[5]] > curVal)
                                    expRollVal += FarkelSolver.farkel_tree[1][newHand.die[0]][newHand.die[1]][newHand.die[2]][newHand.die[3]][newHand.die[4]][newHand.die[5]];
                            }}}}}}

        for (i = 0; i < c; i++)
            expRollVal /= 6.0;

        if (expRollVal > curVal)
            return (float)expRollVal;
        else
            return (float)curVal;
    }

    public Dice bestHand() {

        if(total != 6) return this;

        int i, j, k, l, m, n;
        float bestVal, heldVal;
        Dice bestHand = new Dice(this);
        Dice newHand = new Dice();

        bestVal = FarkelSolver.farkel_tree[1][bestHand.die[0]][bestHand.die[1]][bestHand.die[2]][bestHand.die[3]][bestHand.die[4]][bestHand.die[5]];

        for (i=held; i < 6; i++)
            bestHand.die[i] = 0;

        heldVal = FarkelSolver.farkel_tree[0][bestHand.die[0]][bestHand.die[1]][bestHand.die[2]][bestHand.die[3]][bestHand.die[4]][bestHand.die[5]];


        for (i = (held > 0)?1:0; i<=1; i++)
        {
            newHand.die[0] = (0==i)?0:die[0];

            for (j = (held > 1)?1:0; j<=1; j++)
            {
                newHand.die[1] = (0==j)?0:die[1];

                for (k = (held > 2)?1:0; k<=1; k++)
                {
                    newHand.die[2] = (0==k)?0:die[2];

                    for (l = (held > 3)?1:0; l<=1; l++)
                    {
                        newHand.die[3] = (0==l)?0:die[3];

                        for (m = (held > 4)?1:0; m<=1; m++)
                        {
                            newHand.die[4] = (0==m)?0:die[4];

                            for (n = (held > 5)?1:0; n<=1; n++)
                            {
                                newHand.die[5] = (0==n)?0:die[5];

                                // if new hand is keepable, and has a better expected value
                                if (FarkelSolver.farkel_tree[0][newHand.die[0]][newHand.die[1]][newHand.die[2]][newHand.die[3]][newHand.die[4]][newHand.die[5]] > heldVal
                                        && FarkelSolver.farkel_tree[1][newHand.die[0]][newHand.die[1]][newHand.die[2]][newHand.die[3]][newHand.die[4]][newHand.die[5]] > bestVal) {
                                    bestVal = FarkelSolver.farkel_tree[1][newHand.die[0]][newHand.die[1]][newHand.die[2]][newHand.die[3]][newHand.die[4]][newHand.die[5]];
                                    bestHand.copy(newHand);
                                }
                            }}}}}}

        // put unrolled die at end of list
        i = 0;
        j = 6;
        while (i < 6) {
            if (0 == bestHand.die[i]) {
                j = i;
                i++;
                break;
            }
            i++;
        }
        while (i < 6) {
            if (0 != bestHand.die[i]) {
                bestHand.die[j] = bestHand.die[i];
                bestHand.die[i] = 0;
                j++;
            }
            i++;
        }
        bestHand.held = j;

        return bestHand;
    }
}
