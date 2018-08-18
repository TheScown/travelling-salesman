/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.graph;

public class Arc<T> implements Comparable<Arc<T>> {
    private final T start;
    private final T end;
    private final int weight;

    public Arc(final T start, final T end, final int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }

    boolean isStart(final T n) {
        return start.equals(n);
    }

    public boolean isEnd(final T n) {
        return end.equals(n);
    }

    @SuppressWarnings("unchecked")
    public boolean equals(final Object o) {
        if (getClass().equals(o.getClass())) {
            final Arc<T> a = (Arc<T>) o;
            return (start.equals(a.start) && end.equals(a.end) && weight == a.getWeight());
        }
        return false;
    }

    public int hashCode() {
        return start.hashCode() + end.hashCode() + weight;
    }

    public String toString() {
        return "(" + start.toString() + "," + end.toString() + "," + weight + ")";
    }

    public int compareTo(final Arc<T> o) {
        return weight - o.weight;
    }


}
