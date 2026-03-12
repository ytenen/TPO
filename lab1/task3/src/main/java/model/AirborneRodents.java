package model;

public class AirborneRodents extends SceneObject {
    public AirborneRodents(String name) {
        super(name);
    }

    public void hypnotize(Person person) {
        person.setHypnotized(true);
        System.out.println(name + " загипнотизировали " + person.getName());
    }
}