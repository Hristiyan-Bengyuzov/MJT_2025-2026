package bg.sofia.uni.fmi.mjt.show;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;
import bg.sofia.uni.fmi.mjt.show.elimination.EliminationRule;
import bg.sofia.uni.fmi.mjt.show.elimination.LowestRatingEliminationRule;
import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class ShowAPIImpl implements ShowAPI {
    private Ergenka[] ergenkas;
    private final EliminationRule[] defaultEliminationRules;

    public ShowAPIImpl(Ergenka[] ergenkas, EliminationRule[] defaultEliminationRules) {
        this.ergenkas = ergenkas;

        if (defaultEliminationRules == null || defaultEliminationRules.length == 0) {
            this.defaultEliminationRules = new EliminationRule[]{new LowestRatingEliminationRule()};
        } else {
            this.defaultEliminationRules = defaultEliminationRules;
        }
    }


    @Override
    public Ergenka[] getErgenkas() {
        return ergenkas;
    }

    @Override
    public void playRound(DateEvent dateEvent) {
        if (dateEvent == null) {
            return;
        }

        for (Ergenka ergenka : ergenkas) {
            organizeDate(ergenka, dateEvent);
        }
    }

    @Override
    public void eliminateErgenkas(EliminationRule[] eliminationRules) {
        var rules = (eliminationRules == null || eliminationRules.length == 0) ? defaultEliminationRules : eliminationRules;

        for (EliminationRule rule : rules) {
            this.ergenkas = rule.eliminateErgenkas(ergenkas);
        }
    }

    @Override
    public void organizeDate(Ergenka ergenka, DateEvent dateEvent) {
        if (ergenka == null) {
            return;
        }

        ergenka.reactToDate(dateEvent);
    }
}
