/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Graph<T> {

    private final Set<T> nodes;
    protected final Set<Arc<T>> arcs;

    public Graph(final Set<T> nodes, final Set<Arc<T>> arcs) {
        this.nodes = nodes;
        this.arcs = arcs;
    }

    public Set<T> getNodes() {
        return nodes;
    }

    public Set<Arc<T>> getArcs() {
        return arcs;
    }

    protected Set<T> successors(final T node) {
        final HashSet<T> successors = new HashSet<>();

        for (final Arc<T> arc : arcs) {
            if (arc.isStart(node)) {
                successors.add(arc.getEnd());
            }
        }

        return successors;
    }

    public Set<Arc<T>> startingAt(final T node) {
        final HashSet<Arc<T>> startingAt = new HashSet<>();
        for (final Arc<T> arc : arcs) {
            if (arc.isStart(node)) {
                startingAt.add(arc);
            }
        }
        return startingAt;
    }

    public Collection<Arc<T>> endingAt(final T node) {
        final Collection<Arc<T>> endingAt = new HashSet<>();

        for (final Arc<T> arc : arcs) {
            if (arc.isEnd(node)) {
                endingAt.add(arc);
            }
        }

        return endingAt;
    }

    private boolean succeeds(final T n1, final T n2) {
        return successors(n1).contains(n2);
    }

    public int distance(final T n1, final T n2) throws IllegalArgumentException {
        if (succeeds(n1, n2)) {
            final Iterable<Arc<T>> arcs = startingAt(n1);
            int distance = 0;
            for (final Arc<T> arc : arcs) {
                if (arc.isEnd(n2)) {
                    distance = arc.getWeight();
                }
            }
            return distance;
        } else {
            throw new IllegalArgumentException("Nodes not adjacent");
        }
    }
}
