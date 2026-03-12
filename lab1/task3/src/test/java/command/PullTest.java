package command;


import model.Door;
import model.Person;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PullTest {

    @Test
    void executeShouldRunWithoutExceptionsForNormalTarget() {
        Person trillian = new Person("Триллиан");
        Person arthur = new Person("Артур");
        Door door = new Door("дверь");

        Pull command = new Pull(trillian, arthur, door);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        command.execute();

        assertEquals("Триллиан тянет Артур к дверь", outputStream.toString().trim());
    }

    @Test
    void pullShouldPrintCantMoveMessageWhenPersonIsHypnotized() {
        Person trillian = new Person("Триллиан");
        Person arthur = new Person("Артур");
        Door door = new Door("дверь");

        trillian.setHypnotized(true);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        trillian.pull(arthur, door);

        System.setOut(originalOut);

        String result = outputStream.toString().trim();
        assertTrue(result.contains("Триллиан не может двигаться, так как загипнотизирован(а)."));
    }
}
