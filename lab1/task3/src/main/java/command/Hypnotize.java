package command;

import interfaces.Command;
import model.AirborneRodents;
import model.Person;

public class Hypnotize implements Command {
    private final AirborneRodents rodents;
    private final Person target;

    public Hypnotize(AirborneRodents rodents, Person target) {
        this.rodents = rodents;
        this.target = target;
    }

    @Override
    public void execute() {
        rodents.hypnotize(target);
    }
}