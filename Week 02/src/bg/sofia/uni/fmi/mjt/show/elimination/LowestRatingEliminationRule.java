package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class LowestRatingEliminationRule implements EliminationRule {
    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        if (ergenkas == null || ergenkas.length == 0) {
            return new Ergenka[0];
        }

        int lowest = getLowestRating(ergenkas);
        int ergenkasCount = 0;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka.getRating() != lowest) {
                ergenkasCount++;
            }
        }

        Ergenka[] res = new Ergenka[ergenkasCount];
        int write = 0;

        for (Ergenka ergenka : ergenkas) {
            if (ergenka.getRating() != lowest) {
                res[write++] = ergenka;
            }
        }

        return res;
    }

    private int getLowestRating(Ergenka[] ergenkas) {
        int lowest = Integer.MAX_VALUE;
        for (Ergenka ergenka : ergenkas) {
            lowest = Math.min(lowest, ergenka.getRating());
        }
        return lowest;
    }
}
