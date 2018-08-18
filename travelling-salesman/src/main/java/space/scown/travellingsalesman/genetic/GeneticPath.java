/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.scown.travellingsalesman.genetic;

import space.scown.travellingsalesman.salesman.Parser;
import space.scown.travellingsalesman.salesman.Path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GeneticPath extends Path {

    private final Parser parser;

    private GeneticPath(final Parser parser) {
        super();
        this.parser = parser;
    }

    private GeneticPath(final GeneticPath path) {
        super();
        this.parser = path.getParser();
        this.path = new ArrayList<>(path.getPath());
        this.length = path.getLength();
    }

    private void setPath(final ArrayList<Integer> path) {
        this.path = path;
        length = recomputeLength();
    }

    private Parser getParser() {
        return parser;
    }

    public static GeneticPath generateRandomPath(final Parser parser) {
        final GeneticPath path = new GeneticPath(parser);
        final Set<Integer> nodes = parser.getGraph().getNodes();
        final int size = nodes.size();
        final Random randomGen = new Random();
        final int start = randomGen.nextInt(size) + 1;
        path.add(start, 0);
        while (path.size() < size) {
            final int next = randomGen.nextInt(size) + 1;
            if (!path.contains(next)) {
                final Integer n1 = path.getLast();
                final Integer n2 = next;
                final int distance = parser.getGraph().distance(n1, n2);
                path.add(next, distance);
            }
        }
        final Integer n1 = start;
        final Integer n2 = path.getLast();
        path.add(start, parser.getGraph().distance(n1, n2));
        return path;
    }

    public GeneticPath breed(final GeneticPath p2) {
        final List<Integer> path1 = new ArrayList<>(path);
        final List<Integer> path2 = new ArrayList<>(p2.getPath());
        final Random random = new Random();
        path1.remove(path1.size() - 1);
        path2.remove(path2.size() - 1);
        final int breedLength = random.nextInt(path1.size());
        ArrayList<Integer> newPath1 = new ArrayList<>();
        ArrayList<Integer> newPath2 = new ArrayList<>();
        for (int i = 0; i < breedLength; i++) {
            newPath1.add(i, path1.get(i));
            newPath2.add(i, path2.get(i));
        }
        for (int i = breedLength; i < path1.size(); i++) {
            newPath1.add(i, path2.get(i));
            newPath2.add(i, path1.get(i));
        }
        newPath1 = fix(newPath1);
        newPath2 = fix(newPath2);
        final GeneticPath child1 = new GeneticPath(parser);
        child1.setPath(newPath1);
        final GeneticPath child2 = new GeneticPath(parser);
        child2.setPath(newPath2);
        final Collection<GeneticPath> paths = new HashSet<>();
        paths.add(child1);
        paths.add(child2);
        return Collections.min(paths);
    }

    private ArrayList<Integer> fix(final ArrayList<Integer> p) {
        final List<Integer> missing = new ArrayList<>();
        final List<Integer> doubled = new ArrayList<>();
        for (int i = 1; i <= p.size(); i++) {
            int count = 0;
            for (final Integer aP : p) {
                if (aP == i) {
                    count++;
                }
            }
            if (count == 0) {
                missing.add(i);
            } else if (count == 2) {
                doubled.add(i);
            }
        }
        for (int i = 0; i < missing.size(); i++) {
            p.set(p.indexOf(doubled.get(i)), missing.get(i));
        }
        p.add(p.get(0));
        return new ArrayList<>(p);
    }

    public GeneticPath swap(final int i, final int j) {
        final GeneticPath newPath = new GeneticPath(this);
        final ArrayList<Integer> p = newPath.getPath();
        final int a = p.get(i);
        p.set(i, p.get(j));
        p.set(j, a);
        if (!p.get(0).equals(p.get(p.size() - 1))) {
            p.set(size() - 1, p.get(0));
        }
        newPath.setPath(p);
        return newPath;
    }

    private int recomputeLength() {
        int newLength = 0;
        for (int i = 0; i < size() - 1; i++) {
            final Integer start = path.get(i);
            final Integer end = path.get(i + 1);
            newLength = parser.getGraph().distance(start, end);
        }
        return newLength;
    }
}
