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

public class TestGcLocalSearch {
  private static final Logger LOG = Logger.getLogger(TestGcLocalSearch.class);
  
  public static void main(String[] args) throws Exception {
    File inputFile = new File("../gctests/dsjc250.5.col");
    int k = 28;
    
    Random rand = new Random();
    GcProblem problem = GcReader.read(inputFile);
    GcInitializer initializer = new GcInitializer(rand);
    GcSolution initSol = initializer.makeInitialColoring(problem, k);
    
    LOG.info("Initial sol cost: " + initSol.getCost());
    LOG.info("Calculated cost: " + initSol.calcCost(problem));
    
    GcTabuSearchRunner lsRunner = new GcTabuSearchRunner(40, .6, rand);
    GcSolution improved = lsRunner.run(initSol.getNodeColors(), problem.getNodeNeighbors(), k, initSol.getCost(), 
        System.currentTimeMillis() + 3000);
    LOG.info("Final cost: " + improved.getCost());
    LOG.info("Calculated cost: " + improved.calcCost(problem));
  }
}
