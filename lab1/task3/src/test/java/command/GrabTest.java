package command;

import model.Person;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GrabTest {

    @Test
    void executeShouldRunWithoutExceptions() {
        Person trillian = new Person("Триллиан");
        Person arthur = new Person("Артур");
        Grab command = new Grab(trillian, arthur);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        command.execute();

        assertEquals("Триллиан схватил Артур", outputStream.toString().trim());
    }
}