package command;

import interfaces.Command;
import model.Person;
import model.SceneObject;

public class Pull implements Command {
    private final Person subject;
    private final SceneObject target;
    private final SceneObject destination;

    public Pull(Person subject, SceneObject target, SceneObject destination) {
        this.subject = subject;
        this.target = target;
        this.destination = destination;
    }

    @Override
    public void execute() {
        subject.pull(target, destination);
    }
}
