package interfaces;

import model.Person;

public interface Openable {
    void open(Person person);
    boolean isOpen();
}