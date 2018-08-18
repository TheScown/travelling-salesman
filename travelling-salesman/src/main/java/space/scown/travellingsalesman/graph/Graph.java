/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Graph<T> {

    private final Map<T, Set<Arc<T>>> table = new HashMap<>();

    private final Set<T> nodes;

    public Graph(final Set<T> nodes, final Set<Arc<T>> arcs) {
        this.nodes = nodes;

        for (final Arc<T> arc : arcs) {
            if (!table.containsKey(arc.getStart())) {
                table.put(arc.getStart(), new HashSet<>());
            }

            table.get(arc.getStart()).add(arc);
        }
    }

    public Set<T> getNodes() {
        return nodes;
    }

    public Set<Arc<T>> startingAt(final T node) {
        return table.get(node);
    }

    public int distance(final T n1, final T n2) throws IllegalArgumentException {
        final Optional<Integer> weight = startingAt(n1).stream()
                .filter(a -> a.getEnd().equals(n2))
                .findFirst()
                .map(Arc::getWeight);

        return weight.orElseThrow(() -> new IllegalArgumentException("Nodes not adjacent"));
    }

}
