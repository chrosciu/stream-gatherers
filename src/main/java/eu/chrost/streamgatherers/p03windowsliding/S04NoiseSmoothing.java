package eu.chrost.streamgatherers.p03windowsliding;

import java.util.stream.Gatherers;
import java.util.stream.Stream;

class S04NoiseSmoothing {
    public static void main(String[] args) {
        Stream.of(10, 15, 13, 18, 22, 20, 25)
                .gather(Gatherers.windowSliding(3))
                .map(integers -> integers.stream().mapToInt(i -> i).average().orElse(0))
                .forEach(System.out::println);
    }
}
