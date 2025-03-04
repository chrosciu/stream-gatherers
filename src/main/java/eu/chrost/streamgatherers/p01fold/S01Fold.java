package eu.chrost.streamgatherers.p01fold;

import java.util.stream.Gatherers;
import java.util.stream.Stream;

class S01Fold {
    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4)
                .gather(Gatherers.fold(
                        () -> 0, // Initial state supplier
                        (state, value) -> state + value // Sum
                ))
                .forEach(System.out::println); // Emits one item
    }
}
