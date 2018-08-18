/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.minedge;

import space.scown.travellingsalesman.graph.Arc;
import space.scown.travellingsalesman.graph.Graph;

import java.util.Iterator;
import java.util.Set;

class MinGraph<T> extends Graph<T> {

	public MinGraph(final Set<T> nodes, final Set<Arc<T>> arcs) {
		super(nodes,arcs);
	}
	
	public void addArc(final Arc<T> arc) {
		arcs.add(arc);
	}
	
	public T getNextCity(final T city) {
		final Set<T> nextNode = successors(city);
		final Iterator<T> it = nextNode.iterator();
		return it.next();
	}
	
	public Arc<T> getNextLeg(final T city) {
		final Set<Arc<T>> nextLeg = startingAt(city);
		final Iterator<Arc<T>>it = nextLeg.iterator();
		return it.next();
	}
}
