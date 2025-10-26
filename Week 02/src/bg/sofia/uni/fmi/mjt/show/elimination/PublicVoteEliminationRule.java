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

        String majorityVote = getMajorityVote();

        // if no one has major vote, we remove no one
        if (majorityVote == null) {
            return ergenkas;
        }

        Ergenka[] res = new Ergenka[ergenkas.length - 1];
        int write = 0;

        for (Ergenka ergenka : ergenkas) {
            if (!ergenka.getName().equals(majorityVote)) {
                res[write++] = ergenka;
            }
        }

        return res;
    }

    private String getMajorityVote() {
        if (votes == null || votes.length == 0) {
            return null;
        }

        String majorityVote = null;
        int count = 0;

        for (String vote : votes) {
            if (count == 0) {
                majorityVote = vote;
                count = 1;
            } else if (majorityVote.equals(vote)) {
                count++;
            } else {
                count--;
            }
        }

        if (majorityVote != null) {
            int voteCount = 0;
            for (String vote : votes) {
                if (majorityVote.equals(vote)) {
                    voteCount++;
                }
            }

            return voteCount > votes.length / 2 ? majorityVote : null;
        }

        return null;
    }
}
