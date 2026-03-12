package model;

public class Person extends SceneObject {
    private boolean hypnotized;

    public Person(String name) {
        super(name);
        this.hypnotized = false;
    }

    public boolean isHypnotized() {
        return hypnotized;
    }

    public void setHypnotized(boolean hypnotized) {
        this.hypnotized = hypnotized;
    }

    public void grab(SceneObject target) {
        System.out.println(name + " схватил " + target.getName());
    }

    public void pull(SceneObject target, SceneObject destination) {
        if (hypnotized) {
            System.out.println(name + " не может двигаться, так как загипнотизирован(а).");
            return;
        }
        System.out.println(name + " тянет " + target.getName() + " к " + destination.getName());
    }
}