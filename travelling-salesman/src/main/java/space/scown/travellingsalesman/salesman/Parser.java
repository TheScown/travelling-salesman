/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.salesman;

import space.scown.travellingsalesman.graph.Arc;
import space.scown.travellingsalesman.graph.Graph;
import space.scown.travellingsalesman.graph.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Parser {

    private final String name;
    private final int size;
    private final Graph<Integer> graph;

    @SuppressWarnings("unchecked")
    public Parser(final String filename) throws IOException {
        final String data = Files.lines(Paths.get(filename))
                .collect(Collectors.joining());

        final List<String> parts = Arrays.stream(data.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        final String[] nameParts = parts.get(0).split("=");
        name = nameParts[1].trim();

        final String[] sizeParts = parts.get(1).split("=");
        final String sizeString = sizeParts[1].trim();
        size = Integer.parseInt(sizeString);

        final List<Node<Integer>> nodes = IntStream.range(1, size + 1)
                .boxed()
                .map(Node::new)
                .collect(Collectors.toList());

        final Iterator<Integer> nodeIndices = parts.subList(2, parts.size()).stream()
                .map(Integer::parseInt)
                .iterator();

        final Set<Arc<Integer>> arcs = IntStream.range(0, size)
                .boxed()
                .flatMap(k -> IntStream.range(k + 1, size)
                        .boxed()
                        .flatMap(l -> {
                            final int weight = nodeIndices.next();
                            return Stream.of(
                                    new Arc<>(nodes.get(k), nodes.get(l), weight),
                                    new Arc<>(nodes.get(l), nodes.get(k), weight)
                            );
                        }))
                .collect(Collectors.toSet());

        graph = new Graph<>(new HashSet<>(nodes), arcs);
    }

    public String getName(){
        return name;
    }

    public int getSize() {
        return size;
    }

    public Graph<Integer> getGraph(){
        return graph;
    }
}
