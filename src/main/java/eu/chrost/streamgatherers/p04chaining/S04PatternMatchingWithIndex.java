package eu.chrost.streamgatherers.p04chaining;

import java.util.List;
import java.util.stream.Gatherers;

record ItemWithIndex(int item, int index) {}

public class S04PatternMatchingWithIndex {
    private static final List<Integer> PATTERN = List.of(1, 0, 1);

    public static void main(String[] args) {
        List.of(1, 0, 1, 1, 0, 1, 0, 0, 1).stream()
                .gather(Gatherers.scan(
                        () -> new ItemWithIndex(0, -1),
                        (state, item) ->
                                new ItemWithIndex(item, state.index() + 1)))
                //[item: 1, index: 0], [item: 0, index: 1], [item: 1, index: 2], [item: 1, index: 3]...
                .gather(Gatherers.windowSliding(PATTERN.size()))
                //[[1, 0], [0, 1], [1, 2]], [[0, 1], [1, 2], [1, 3]], ...
                .filter(window ->
                        window.stream().map(ItemWithIndex::item).toList().equals(PATTERN))
                .forEach(pattern ->
                        System.out.println("Pattern " + PATTERN + " found at index: " + pattern.getFirst().index()));
    }
}
