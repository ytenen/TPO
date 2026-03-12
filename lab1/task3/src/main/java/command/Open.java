package command;

import interfaces.Command;
import interfaces.Openable;
import model.Person;

public class Open implements Command {
    private final Person subject;
    private final Openable target;

    public Open(Person subject, Openable target) {
        this.subject = subject;
        this.target = target;
    }

    @Override
    public void execute() {
        target.open(subject);
    }
}