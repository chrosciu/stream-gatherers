package eu.chrost.streamgatherers.p04windowfixed;

import java.util.List;
import java.util.stream.Gatherers;

public class S08StreamToMatrix {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<List<Integer>> matrix = numbers.stream()
                .gather(Gatherers.windowFixed(3))
                .toList();
        System.out.println(matrix);
    }
}
