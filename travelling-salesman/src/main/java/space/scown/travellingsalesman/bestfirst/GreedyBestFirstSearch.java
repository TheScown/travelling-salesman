/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.bestfirst;

import space.scown.travellingsalesman.graph.Arc;
import space.scown.travellingsalesman.graph.Graph;
import space.scown.travellingsalesman.salesman.Parser;
import space.scown.travellingsalesman.salesman.Path;
import space.scown.travellingsalesman.salesman.Writer;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GreedyBestFirstSearch {

    /**
     * A greedy best first search algorithm.  From each city,
     * takes the shortest available route until a tour is found.
     * Returns the shortest tour in a file.
     *
     * @param args Input filename
     */
    public static void main(final String[] args) throws IOException {
        final Parser parser = new Parser(args[0]);

        final Collection<Path> paths = new HashSet<>();

        final Graph<Integer> graph = parser.getGraph();

        for (final Integer node : graph.getNodes()) {
            final Path path = new Path();
            path.add(node, 0);

            while (path.size() < parser.getSize()) {
                final Integer n = path.getLast();
                final Set<Arc<Integer>> arcs = graph.startingAt(n);

                final Optional<Arc<Integer>> shortestEdge = arcs.stream()
                        .filter(arc -> !path.contains(arc.getEnd()))
                        .findFirst();

                shortestEdge.ifPresent(arc -> path.add(arc.getEnd(), arc.getWeight()));
            }

            path.add(node, graph.distance(path.getLast(), node));

            paths.add(path);
        }

        Writer.writeToFile(parser.getName(), parser.getSize(), Collections.min(paths));
    }
}
