# COVID-19-lockdown-simulation

Simple graph-based approach for simulating a network of agents interacting with each other.
Was used in an article I wrote on the effectiveness of lockdowns in preventing the spread of COVID-19.

Algorithm:
- Set the population size n=10,000 and the compliance rates c=0.2,0.8.
- For c=0.2,0.8, repeat the following t=1,000 times
- Create a random collection of n people objects – each person either obeys the lockdown or does not
    - c*n people will obey lockdown orders
	  - (1-c)n people will not obey lockdown orders
-	Create a random, dense n*n adjacency matrix, P_ij={(1 if i knows j; 0 if i does not know j)}
  -	This will represent a neighborhood – some vertices will be connected while others will not be
	- The same random matrix will be used in both c values for consistency
- Infect one random person who is not staying home
- Starting at this person, go through the matrix and look at all the person’s neighbors
  - If the person is staying home, they have an x=0.166+rand(0,1) probability of being infected
	- If the person is not home, they have an y=0.678+rand(0,1)  probability of being infected
- Keep track of people objects who are infected
  - A person who is infected can continue to infect others (keep looking at neighbors)
  - A person who is not infected cannot infect other people (path ends)
