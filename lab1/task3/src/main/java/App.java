import command.Grab;
import command.Hypnotize;
import command.Open;
import command.Pull;
import model.AirborneRodents;
import model.Door;
import model.Person;

public class App {
    public static void main(String[] args) {
        Person trillian = new Person("Триллиан");
        Person arthur = new Person("Артур");
        Person ford = new Person("Форд");
        Person zafod = new Person("Зафод");

        Door door = new Door("дверь");
        AirborneRodents rodents = new AirborneRodents("Воздухоплавающие грызуны");

        Scenario scenario = new Scenario();

        scenario.addCommand(new Hypnotize(rodents, arthur));
        scenario.addCommand(new Grab(trillian, arthur));
        scenario.addCommand(new Pull(trillian, arthur, door));
        scenario.addCommand(new Open(ford, door));
        scenario.addCommand(new Open(zafod, door));

        scenario.execute();
    }
}