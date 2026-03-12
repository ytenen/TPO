package command;


import interfaces.Command;
import model.Person;
import model.SceneObject;

public class Grab implements Command {
    private final Person subject;
    private final SceneObject target;

    public Grab(Person subject, SceneObject target) {
        this.subject = subject;
        this.target = target;
    }

    @Override
    public void execute() {
        subject.grab(target);
    }
}