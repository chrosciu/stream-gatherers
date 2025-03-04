package eu.chrost.streamgatherers.p05mapconcurrent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Gatherers;
import java.util.stream.Stream;

@Slf4j
class S05MapConcurrent {
    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4, 5, 6)
                .gather(Gatherers.mapConcurrent(2, v -> squareWithDelay(v)))
                .forEach(v -> log.info("{}", v));
    }

    @SneakyThrows
    private static int squareWithDelay(int value) {
        log.info("Square with delay - start {}", value);
        Thread.sleep(1000 * (7 - value));
        log.info("Square with delay - end {}", value);
        return value * value;
    }
}
