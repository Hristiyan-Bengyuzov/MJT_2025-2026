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

    if (majorityVote == null) {
        return ergenkas;
    }

    int removeIndex = -1;

    for (int i = 0; i < ergenkas.length; i++) {
        Ergenka e = ergenkas[i];
        if (e != null && e.getName() != null && e.getName().equals(majorityVote)) {
            removeIndex = i;
            break;
        }
    }

    if (removeIndex == -1) {
        return ergenkas;
    }

    Ergenka[] res = new Ergenka[ergenkas.length - 1];
    int w = 0;

    for (int i = 0; i < ergenkas.length; i++) {
        if (i == removeIndex) continue;
        res[w++] = ergenkas[i];
    }

    return res;
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

    if (candidate == null) return null;

    int total = 0, match = 0;

    for (String v : votes) {
        if (v != null) {
            total++;
            if (candidate.equals(v)) match++;
        }
    }

    return match > total / 2 ? candidate : null;
}
}