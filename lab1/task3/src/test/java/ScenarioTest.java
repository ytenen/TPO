
import command.Grab;
import command.Hypnotize;
import command.Open;
import command.Pull;
import model.AirborneRodents;
import model.Door;
import model.Person;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {

    @Test
    void scenarioShouldExecuteAllCommandsInSequence() {
        Person trillian = new Person("Триллиан");
        Person arthur = new Person("Артур");
        Person ford = new Person("Форд");

        Door door = new Door("дверь");
        AirborneRodents rodents = new AirborneRodents("грызуны");

        Scenario scenario = new Scenario();
        scenario.addCommand(new Hypnotize(rodents, arthur));
        scenario.addCommand(new Grab(trillian, arthur));
        scenario.addCommand(new Pull(trillian, arthur, door));
        scenario.addCommand(new Open(ford, door));

        assertDoesNotThrow(scenario::execute);
        assertTrue(arthur.isHypnotized());
        assertTrue(door.isOpen());
    }
}