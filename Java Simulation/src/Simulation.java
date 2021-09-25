import java.util.*;

public class Simulation {
    private final int populationNumber;

    Simulation(int populationNumber) {
        this.populationNumber = populationNumber;
    }

    public int populationNumber() {
        return populationNumber;
    }

    private Person[] initializePopulation(int populationNumber, double complianceRate, long seed) {
        int complying = (int) (populationNumber * complianceRate);

        Person[] population = new Person[populationNumber];

        int i;

        Random random = new Random(seed);

        for (i = 0; i < complying; i++) {
            population[i] = new Person(0.166 + random.nextDouble());
        }

        for (; i < populationNumber; i++) {
            population[i] = new Person(0.678 + random.nextDouble());
        }

        return population;
    }

    private boolean[][] createRandomAdjacencyMatrix(int populationNumber) {
        boolean[][] adjacencyMatrix = new boolean[populationNumber][populationNumber];
        Random random = new Random();
        for (int i = 0; i < populationNumber; i++) {
            for (int j = i; j < populationNumber; j++) {
                if (i != j) {
                    boolean connection = random.nextBoolean();
                    adjacencyMatrix[i][j] = connection;
                    adjacencyMatrix[j][i] = connection;
                }
            }
        }
        return adjacencyMatrix;
    }

    private int[] traversePopulation(Person[] population, boolean[][] matrix, int start) {
        // Uses BFS algorithm
        int populationSize = population.length;

        boolean[] visitedBool = new boolean[populationSize];
        Queue<Integer> toVisit = new LinkedList<>();
        int infected = 0;
        int visited = -1;

        population[start].setInfected();

        toVisit.add(start);

        while (!toVisit.isEmpty()) {
            int current = toVisit.remove();
            if (current != start) {
                population[current].expose();
            }
            if (population[current].isInfected()) {
                infected++;
                for (int i = 0; i < populationSize; i++) {
                    if (i != current && matrix[current][i] && !visitedBool[i]) {
                        visitedBool[i] = true;
                        toVisit.add(i);
                    }
                }
            }
            visited++;
        }

        return new int[]{infected, visited};
    }


    public int[][] conductSimulation() {
        long seed = System.nanoTime();

        int[][] results = new int[2][2];

        // create populations
        Person[] population1 = initializePopulation(populationNumber, 0.2, seed);
        Person[] population2 = initializePopulation(populationNumber, 0.8, seed);

        // shuffle both populations together
        Collections.shuffle(Arrays.asList(population1), new Random(seed));
        Collections.shuffle(Arrays.asList(population2), new Random(seed));

        // create random connections between people
        boolean[][] adjacencyMatrix = createRandomAdjacencyMatrix(populationNumber);

        // randomize which person is infected first
        Random random = new Random();
        int start = random.nextInt(populationNumber);

        // traverse the populations
        results[0] = traversePopulation(population1, adjacencyMatrix, start);
        results[1] = traversePopulation(population2, adjacencyMatrix, start);

        return results;
    }
}
