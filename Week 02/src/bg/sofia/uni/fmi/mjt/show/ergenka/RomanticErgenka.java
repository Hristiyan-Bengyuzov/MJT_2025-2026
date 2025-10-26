package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public class RomanticErgenka extends AbstractErgenka {
    private final String favoriteDateLocation;

    public RomanticErgenka(String name, short age, int romanceLevel, int humorLevel, int rating, String favoriteDateLocation) {
        super(name, age, romanceLevel, humorLevel, rating);
        this.favoriteDateLocation = favoriteDateLocation;
    }


    @Override
    protected int getBonus(DateEvent dateEvent) {
        if (dateEvent == null) {
            return 0;
        }

        int bonus = 0;

        if (dateEvent.getLocation().equalsIgnoreCase(favoriteDateLocation)) {
            bonus += 5;
        }

        if (dateEvent.getDuration() < 30) {
            bonus -= 3;
        }

        if (dateEvent.getDuration() > 120) {
            bonus -= 2;
        }

        return bonus;
    }

    @Override
    public void reactToDate(DateEvent dateEvent) {
        if (dateEvent == null) {
            return;
        }

        this.rating += getRomanceLevel() * 7 / dateEvent.getTensionLevel() + Math.floorDiv(getHumorLevel(), 3) + getBonus(dateEvent);
    }
}
