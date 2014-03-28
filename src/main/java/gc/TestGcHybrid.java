/**
 * Copyright 2012 Sandy Ryza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gc;

import java.io.File;
import java.util.Random;

import org.apache.log4j.Logger;

public class TestGcHybrid {
  
  private static final Logger LOG = Logger.getLogger(TestGcHybrid.class);
  
  public static void main(String[] args) throws Exception {
    int k = 50;
    File inputFile = new File("../gctests/dsjc500.5.col");
    int popSize = 10;
    int timeToImprove = 1500;
    
    Random rand = new Random();
    GcProblem problem = GcReader.read(inputFile);
    GcInitializer initializer = new GcInitializer(rand);
    GcTabuSearchRunner lsRunner = new GcTabuSearchRunner(40, .6, rand);

    //generate population
    GcSolution[] population = new GcSolution[popSize];
    for (int i = 0; i < popSize; i++) {
      population[i] = initializer.makeInitialColoring(problem, k);
    }
    
    for (GcSolution sol : population) {
      LOG.info("sol cost before LS: " + sol.getCost());
    }
    
    for (int i = 0; i < popSize; i++) {
      population[i] = lsRunner.run(problem, population[i], k, timeToImprove);
      LOG.info("sol cost after LS: " + population[i].getCost());
    }
    
    GcBreeder breeder = new GcBreeder(rand);
    while (true) {
      int parent1Index = rand.nextInt(popSize);
      int parent2Index = parent1Index;
      while (parent2Index == parent1Index) {
        parent2Index = rand.nextInt(popSize);
      }
      
      GcSolution child = breeder.cross(population[parent1Index], population[parent2Index], problem.getNodeNeighbors(), k);
      LOG.info("child cost: " + child.getCost());
      child = lsRunner.run(problem, child, k, timeToImprove);
      LOG.info("child cost after ls: " + child.getCost());
      if (child.getCost() == 0) {
        break;
      }
      
      //evict the worst solution
      int worstSolCost = Integer.MIN_VALUE;
      int worstSolIndex = -1;
      for (int i = 0; i < popSize; i++) {
        if (population[i].getCost() > worstSolCost) {
          worstSolCost = population[i].getCost();
          worstSolIndex = i;
        }
      }
      
      population[worstSolIndex] = child;
    }
  }
}
