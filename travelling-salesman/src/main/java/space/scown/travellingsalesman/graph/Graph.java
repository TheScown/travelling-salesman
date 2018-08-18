/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.graph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class Graph<T extends Comparable<T>> {

    private final Map<T, Set<Arc<T>>> table = new HashMap<>();

    private final Set<T> nodes;

    public Graph(final Set<T> nodes, final Set<Arc<T>> arcs) {
        this.nodes = nodes;

        for (final Arc<T> arc : arcs) {
            if (!table.containsKey(arc.getStart())) {
                table.put(arc.getStart(), new TreeSet<>(new ArcComparator()));
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

    private class ArcComparator implements Comparator<Arc<T>> {
        @Override
        public int compare(final Arc<T> o1, final Arc<T> o2) {
            final int weightDiff = Integer.compare(o1.getWeight(), o2.getWeight());

            if (weightDiff != 0) {
                return weightDiff;
            }

            final int startDiff = Comparator.<Arc<T>, T>comparing(Arc::getStart).compare(o1, o2);

            if (startDiff != 0) {
                return startDiff;
            }

            return Comparator.<Arc<T>, T>comparing(Arc::getEnd).compare(o1, o2);
        }
    }

}
