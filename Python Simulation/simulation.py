"""
FIT2083 research project simulation
Martin Measic
"""

import random
import gc


class Person:
    def __init__(self, infect_prob):
        self.probablity_of_infection = infect_prob
        self.infected = True if random.random() <= infect_prob else False


def initialize_people(population_number, compliance_rate):
    complying = int(population_number * compliance_rate)
    not_complying = population_number - complying

    population = [Person(0.166) for _ in range(complying)]
    for _ in range(not_complying):
        population.append(Person(0.678))

    return population


def create_adjacency_matrix(population_number):
    matrix = [[0] * population_number for _ in range(population_number)]

    for i in range(population_number):
        for j in range(i + 1, population_number):
            if i != j:
                connection = random.randint(0, 1)
                matrix[i][j] = connection
                matrix[j][i] = connection

    return matrix


def traverse_matrix(population, matrix, start):
    to_visit = set()
    infected = set()
    visited = set()

    to_visit.add(start)

    # traverses the population
    while to_visit:
        current = to_visit.pop()
        # if the current person is infected we add its neighbors (people they know) to the people to visit
        if population[current].infected:
            infected.add(current)

            # look at neighbors
            for i in range(len(matrix[current])):
                if i != current and matrix[current][i] and i not in visited:
                    to_visit.add(i)

        visited.add(current)

    return len(infected), len(visited)


def simulation(population):
    results = []

    population_1 = initialize_people(population, 0.2)
    population_2 = initialize_people(population, 0.8)

    # shuffle both populations together
    populations = list(zip(population_1, population_2))
    random.shuffle(populations)
    population_1, population_2 = zip(*populations)

    # create random connections between people
    matrix = create_adjacency_matrix(population)

    # randomize which person is infected first
    start = random.randint(0, population - 1)

    # traverse the populations
    results.append(traverse_matrix(population_1, matrix, start))
    results.append(traverse_matrix(population_2, matrix, start))

    return results


if __name__ == '__main__':
    f = open("results.txt", "w")
    f.write("[")
    f.close()

    for _ in range(100):
        print(simulation(10000))
