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

        int ergenkasCount = 0;
        for (Ergenka ergenka : ergenkas) {
            if (getErgenkaScore(ergenka) >= threshold) {
                ergenkasCount++;
            }
        }

        Ergenka[] res = new Ergenka[ergenkasCount];
        int write = 0;
        for (Ergenka ergenka : ergenkas) {
            if (getErgenkaScore(ergenka) >= threshold) {
                res[write++] = ergenka;
            }
        }

        return res;
    }

    private int getErgenkaScore(Ergenka ergenka) {
        return ergenka.getHumorLevel() + ergenka.getRomanceLevel();
    }
}
