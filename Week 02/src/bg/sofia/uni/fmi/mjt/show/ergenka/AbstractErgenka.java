package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public abstract class AbstractErgenka implements Ergenka {
    private final String name;
    private final short age;
    private final int romanceLevel;
    private final int humorLevel;
    protected int rating;

    public AbstractErgenka(String name, short age, int romanceLevel, int humorLevel, int rating) {
        this.name = name;
        this.age = age;
        this.romanceLevel = romanceLevel;
        this.humorLevel = humorLevel;
        this.rating = rating;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public short getAge() {
        return age;
    }

    @Override
    public int getRomanceLevel() {
        return romanceLevel;
    }

    @Override
    public int getHumorLevel() {
        return humorLevel;
    }

    @Override
    public int getRating() {
        return rating;
    }

    protected abstract int getBonus(DateEvent dateEvent);
}
