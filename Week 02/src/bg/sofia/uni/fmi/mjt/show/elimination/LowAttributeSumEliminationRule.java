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

        int keptNonNull = 0;
        int nullCount = 0;

        for (Ergenka e : ergenkas) {
            if (e == null) {
                nullCount++;
            } else if (getErgenkaScore(e) >= threshold) {
                keptNonNull++;
            }
        }

        Ergenka[] result = new Ergenka[keptNonNull + nullCount];
        int write = 0;

        for (Ergenka e : ergenkas) {
            if (e != null && getErgenkaScore(e) >= threshold) {
                result[write++] = e;
            }
        }

        while (nullCount-- > 0) {
            result[write++] = null;
        }

        return result;
    }

    private int getErgenkaScore(Ergenka e) {
        return e.getHumorLevel() + e.getRomanceLevel();
    }
}
