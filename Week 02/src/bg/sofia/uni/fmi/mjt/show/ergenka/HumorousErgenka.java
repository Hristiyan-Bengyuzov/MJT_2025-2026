package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public class HumorousErgenka extends AbstractErgenka {
    public HumorousErgenka(String name, short age, int romanceLevel, int humorLevel, int rating) {
        super(name, age, romanceLevel, humorLevel, rating);
    }

    @Override
    protected int getBonus(DateEvent dateEvent) {
        if (dateEvent == null) {
            return 0;
        }

        int duration = dateEvent.getDuration();

        if (duration < 30) {
            return -2;
        }

        if (duration <= 90) {
            return 4;
        }

        if (duration > 120) {
            return -3;
        }

        return 0;
    }

    @Override
    public void reactToDate(DateEvent dateEvent) {
        this.rating += getHumorLevel() * 5 / dateEvent.getTensionLevel() + Math.floorDiv(getRomanceLevel(), 3) + getBonus(dateEvent);
    }
}
