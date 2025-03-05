package eu.chrost.streamgatherers.p03windowsliding;

import java.util.List;
import java.util.stream.Gatherers;

record IndexedValue(int index, int value) {}

public class S05PatternMatching {
    public static void main(String[] args) {
        List.of(1, 0, 1, 1, 0, 1, 0, 0, 1).stream()
                .gather(Gatherers.scan(
                        () -> new IndexedValue(-1, 0),
                        (state, value) -> new IndexedValue(state.index() + 1, value)))
                .gather(Gatherers.windowSliding(3))
                .filter(window -> window.stream().map(IndexedValue::value).toList().equals(List.of(1, 0, 1)))
                .forEach(pattern -> System.out.println("Pattern found at index: " + pattern.getFirst().index()));
    }
}
