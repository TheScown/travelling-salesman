/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.bestfirst;

import space.scown.travellingsalesman.graph.Arc;
import space.scown.travellingsalesman.graph.Node;
import space.scown.travellingsalesman.salesman.Parser;
import space.scown.travellingsalesman.salesman.Path;
import space.scown.travellingsalesman.salesman.Writer;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class GreedyBestFirstSearch {

    /**
     * A greedy best first search algorithm.  From each city,
     * takes the shortest available route until a tour is found.
     * Returns the shortest tour in a file.
     * @param args Input filename
     */
    public static void main(final String[] args) throws IOException {
        final Parser parser = new Parser(args[0]);

        final Collection<Path> paths = new HashSet<>();

        for(final Node<Integer> node : parser.getGraph().getNodes()){
            final Path path = new Path();
            final int i = node.getStored();
            path.add(i,0);

            while(path.size()<parser.getSize()){
                final Node<Integer> n = new Node<>(path.getLast());
                final Set<Arc<Integer>> arcs = parser.getGraph().startingAt(n);
                boolean added = false;

                while(!added){
                    final Arc<Integer> arc = Collections.min(arcs);

                    if(!path.contains(arc.getEnd().getStored())){
                        path.add(arc.getEnd().getStored(),arc.getWeight());
                        added = true;
                    }
                    else{
                        arcs.remove(arc);
                    }
                }
            }

            final Node<Integer> n = new Node<>(path.getLast());
            final Iterable<Arc<Integer>> arcs = parser.getGraph().startingAt(n);

            for(final Arc<Integer> arc : arcs){
                if(arc.isEnd(node)){
                    path.add(i, arc.getWeight());
                }
            }

            paths.add(path);
        }

        Writer.writeToFile(parser.getName(), parser.getSize(), Collections.min(paths));
    }
}
