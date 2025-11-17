package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class LowestRatingEliminationRule implements EliminationRule {

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        if (ergenkas == null || ergenkas.length == 0) {
            return new Ergenka[0];
        }

        int lowest = getLowestRating(ergenkas);
        if (lowest == Integer.MAX_VALUE) {
            return new Ergenka[0];
        }

        int remaining = 0;
        for (Ergenka e : ergenkas) {
            if (e != null && e.getRating() != lowest) {
                remaining++;
            }
        }

        Ergenka[] res = new Ergenka[remaining];
        int write = 0;

        for (Ergenka e : ergenkas) {
            if (e != null && e.getRating() != lowest) {
                res[write++] = e;
            }
        }

        return res;
    }

    private int getLowestRating(Ergenka[] ergenkas) {
        int lowest = Integer.MAX_VALUE;

        for (Ergenka e : ergenkas) {
            if (e != null) {
                lowest = Math.min(lowest, e.getRating());
            }
        }

        return lowest;
    }
}
