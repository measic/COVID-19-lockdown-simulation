import java.util.Arrays;

public class SimulationDriver {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Simulation sim = new Simulation(10_000);
            System.out.println((Arrays.deepToString(sim.conductSimulation())));
        }
    }
}
