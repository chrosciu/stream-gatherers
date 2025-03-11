package eu.chrost.streamgatherers.p10chaining;

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class S15WindowCount {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stream<Integer> windowCount = numbers.stream()
                .gather(Gatherers.windowFixed(3))
                .gather(Gatherers.fold(() -> 0, (sum, _) -> sum + 1));
        windowCount.forEach(System.out::println);

        Stream<Integer> windowCountWithCompoundGatherer = numbers.stream()
                .gather(Gatherers.windowFixed(3).andThen(Gatherers.fold(() -> 0, (sum, _) -> sum + 1)));
        windowCountWithCompoundGatherer.forEach(System.out::println);
    }
}
