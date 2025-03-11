package eu.chrost.streamgatherers.p02scan;

import java.util.stream.Gatherers;
import java.util.stream.Stream;

class S02AccumulatedSum {
    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4)
                .gather(Gatherers.scan(
                        () -> 0,
                        (state, value) -> state + value
                ))
                .forEach(System.out::println);
    }
}
