package command;

import model.AirborneRodents;
import model.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HypnotizeTest {

    @Test
    void executeShouldHypnotizeTargetPerson() {
        AirborneRodents rodents = new AirborneRodents("грызуны");
        Person arthur = new Person("Артур");
        Hypnotize command = new Hypnotize(rodents, arthur);

        command.execute();

        assertTrue(arthur.isHypnotized());
    }
}
