package model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirborneRodentsTest {

    @Test
    void rodentsShouldHypnotizePerson() {
        AirborneRodents rodents = new AirborneRodents("грызуны");
        Person arthur = new Person("Артур");

        rodents.hypnotize(arthur);

        assertTrue(arthur.isHypnotized());
    }
}