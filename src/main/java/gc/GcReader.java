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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GcReader {
  public static GcProblem read(File f) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(f));
    String line;
    while ((line = br.readLine()).startsWith("c"));
    
    String[] tokens = line.split("\\s");
    int nNodes = Integer.parseInt(tokens[2]);
    List<Integer>[] nodeNeighborLists = new List[nNodes];
    for (int i = 0; i < nNodes; i++) {
      nodeNeighborLists[i] = new ArrayList<Integer>();
    }
    while ((line = br.readLine()) != null) {
      tokens = line.split("\\s");
      int node1 = Integer.parseInt(tokens[1])-1;
      int node2 = Integer.parseInt(tokens[2])-1;
      nodeNeighborLists[node1].add(node2);
      nodeNeighborLists[node2].add(node1);
    }
    int[][] nodeNeighbors = new int[nNodes][];
    for (int i = 0; i < nNodes; i++) {
      nodeNeighbors[i] = new int[nodeNeighborLists[i].size()];
      for (int j = 0; j < nodeNeighborLists[i].size(); j++) {
        nodeNeighbors[i][j] = nodeNeighborLists[i].get(j);
      }
    }
    
    return new GcProblem(nodeNeighbors);
  }
}
