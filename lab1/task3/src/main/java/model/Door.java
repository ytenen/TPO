package model;


import interfaces.Openable;

public class Door extends SceneObject implements Openable {
    private boolean open;

    public Door(String name) {
        super(name);
        this.open = false;
    }

    @Override
    public void open(Person person) {
        if (!open) {
            System.out.println(person.getName() + " пытается открыть " + name);
            open=true;
        } else {
            System.out.println(name + " уже открыта");
        }
    }

    @Override
    public boolean isOpen() {
        return open;
    }
}