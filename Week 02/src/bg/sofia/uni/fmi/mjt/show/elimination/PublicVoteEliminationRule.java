package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class PublicVoteEliminationRule implements EliminationRule {
    private final String[] votes;

    public PublicVoteEliminationRule(String[] votes) {
        this.votes = votes;
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        if (ergenkas == null || ergenkas.length == 0) {
            return new Ergenka[0];
        }

        String majority = getMajorityVote();
        if (majority == null) {
            return ergenkas;
        }

        boolean exists = false;
        for (Ergenka e : ergenkas) {
            if (e != null && majority.equals(e.getName())) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            return ergenkas; 
        }

        int removeCount = 0;
        for (Ergenka e : ergenkas) {
            if (e != null && majority.equals(e.getName())) {
                removeCount++;
            }
        }

        Ergenka[] result = new Ergenka[ergenkas.length - removeCount];
        int write = 0;

        for (Ergenka e : ergenkas) {
            if (e == null || !majority.equals(e.getName())) {
                result[write++] = e;
            } 
        }

        return result;
    }


    private String getMajorityVote() {
        if (votes == null || votes.length == 0) {
            return null;
        }

        String candidate = null;
        int count = 0;

        for (String v : votes) {
            if (v == null) continue;

            if (count == 0) {
                candidate = v;
                count = 1;
            } else if (candidate.equals(v)) {
                count++;
            } else {
                count--;
            }
        }

        if (candidate == null) {
            return null;
        }

        int total = 0;
        int matches = 0;

        for (String v : votes) {
            if (v != null) {
                total++;
                if (candidate.equals(v)) {
                    matches++;
                }
            }
        }

        return (matches > total / 2) ? candidate : null;
    }
}
