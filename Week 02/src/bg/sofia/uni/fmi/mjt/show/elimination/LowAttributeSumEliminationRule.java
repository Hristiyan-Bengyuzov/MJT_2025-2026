package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class LowAttributeSumEliminationRule implements EliminationRule {
    private final int threshold;

    public LowAttributeSumEliminationRule(int threshold) {
        this.threshold = threshold;
    }

   @Override
public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
    if (ergenkas == null || ergenkas.length == 0) {
        return new Ergenka[0];
    }

    boolean removeHappened = false;

    for (Ergenka e : ergenkas) {
        if (e != null && getErgenkaScore(e) < threshold) {
            removeHappened = true;
            break;
        }
    }

    if (!removeHappened) {
        return ergenkas; // 
    }

    int remaining = 0;
    for (Ergenka e : ergenkas) {
        if (e != null && getErgenkaScore(e) >= threshold) {
            remaining++;
        }
    }

    Ergenka[] res = new Ergenka[remaining];
    int w = 0;

    for (Ergenka e : ergenkas) {
        if (e != null && getErgenkaScore(e) >= threshold) {
            res[w++] = e;
        }
    }

    return res;
}


    private int getErgenkaScore(Ergenka ergenka) {
        return ergenka.getHumorLevel() + ergenka.getRomanceLevel();
    }
}