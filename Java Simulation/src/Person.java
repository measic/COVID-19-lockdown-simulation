import java.util.Random;

public class Person {
    private final double infectionProbability;
    private boolean infected;

    public Person(double infectionProbability) {
        this.infectionProbability = infectionProbability;
    }

    public void expose() {
        Random random = new Random();
        infected = random.nextDouble() <= infectionProbability;
    }

    public boolean isInfected() {
        return infected;
    }

    public void setInfected() {
        infected = true;
    }
}