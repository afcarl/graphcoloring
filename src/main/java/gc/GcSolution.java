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

public class GcSolution implements Comparable<GcSolution> {
  private int[] nodeColors;
  private int cost;
  
//  public GcSolution(int[] nodeColors) {
//    this.nodeColors = nodeColors;
//  }
  
  public GcSolution(int[] nodeColors, int cost) {
    this.nodeColors = nodeColors;
    this.cost = cost;
  }
  
  public GcSolution(int[] nodeColors, int[][] nodeNeighbors) {
    this.nodeColors = nodeColors;
    this.cost = calcCost(nodeNeighbors);
  }
  
  public int getCost() {
    return cost;
  }
  
  public int[] getNodeColors() {
    return nodeColors;
  }
  
  public int calcCost(int[][] nodeNeighbors) {
    int total = 0;
    for (int i = 0; i < nodeColors.length; i++) {
      for (int neighbor : nodeNeighbors[i]) {
        if (nodeColors[i] == nodeColors[neighbor]) {
          total++;
        }
      }
    }
    return cost = total / 2;
  }
  
  public int calcCost(GcProblem problem) {
    return calcCost(problem.getNodeNeighbors());
  }
  
  public int compareTo(GcSolution other) {
    return this.cost - other.cost;
  }
}
