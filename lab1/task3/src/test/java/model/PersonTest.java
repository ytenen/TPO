package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void personShouldNotBeHypnotizedByDefault() {
        Person person = new Person("Артур");
        assertFalse(person.isHypnotized());
    }

    @Test
    void personShouldBecomeHypnotized() {
        Person person = new Person("Артур");
        person.setHypnotized(true);

        assertTrue(person.isHypnotized());
    }
}