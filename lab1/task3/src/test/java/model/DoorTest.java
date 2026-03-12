package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoorTest {

    @Test
    void doorShouldBeClosedByDefault() {
        Door door = new Door("дверь");
        assertFalse(door.isOpen());
    }

    @Test
    void doorShouldOpenAfterOpenMethodCall() {
        Door door = new Door("дверь");
        Person ford = new Person("Форд");

        door.open(ford);

        assertTrue(door.isOpen());
    }

    @Test
    void repeatedOpenShouldKeepDoorOpen() {
        Door door = new Door("дверь");
        Person ford = new Person("Форд");
        Person zaphod = new Person("Зафод");

        door.open(ford);
        door.open(zaphod);

        assertTrue(door.isOpen());
    }
}