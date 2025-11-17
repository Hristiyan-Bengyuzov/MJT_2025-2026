package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class LowestRatingEliminationRule implements EliminationRule {
@Override
public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
    if (ergenkas == null || ergenkas.length == 0) {
        return new Ergenka[0];
    }

    int lowest = getLowestRating(ergenkas);

    boolean removeHappened = false;
    for (Ergenka e : ergenkas) {
        if (e != null && e.getRating() == lowest) {
            removeHappened = true;
            break;
        }
    }

    if (!removeHappened) return ergenkas;

    int remaining = 0;
    for (Ergenka e : ergenkas) {
        if (e != null && e.getRating() != lowest) {
            remaining++;
        }
    }

    Ergenka[] res = new Ergenka[remaining];
    int w = 0;

    for (Ergenka e : ergenkas) {
        if (e != null && e.getRating() != lowest) {
            res[w++] = e;
        }
    }

    return res;
}


    private int getLowestRating(Ergenka[] ergenkas) {
        int lowest = Integer.MAX_VALUE;
        boolean foundValid = false;
        
        for (Ergenka ergenka : ergenkas) {
            if (ergenka != null) {
                foundValid = true;
                lowest = Math.min(lowest, ergenka.getRating());
            }
        }
        
        return foundValid ? lowest : Integer.MAX_VALUE;
    }
}