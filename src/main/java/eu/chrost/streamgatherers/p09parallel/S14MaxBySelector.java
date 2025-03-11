package eu.chrost.streamgatherers.p09parallel;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

@RequiredArgsConstructor
class MaxBySelectorGatherer<T, B extends Comparable<B>> implements Gatherer<T, AtomicReference<T>, T> {
    private final Function<T, B> extractor;

    @Override
    public Supplier<AtomicReference<T>> initializer() {
        return () -> new AtomicReference<>(null);
    }

    @Override
    public Integrator<AtomicReference<T>, T, T> integrator() {
        return Integrator.ofGreedy((state, item, _) -> {
            if (state.get() == null) {
                state.set(item);
                return true;
            }

            B currentItemValue = extractor.apply(item);
            B maxItemValue = extractor.apply(state.get());

            if (currentItemValue.compareTo(maxItemValue) > 0) {
                state.set(item);
            }

            return true;
        });
    }

    @Override
    public BinaryOperator<AtomicReference<T>> combiner() {

        return (first, second) -> {
            if (first.get() == null && second.get() == null) {
                return null;
            } else if (first.get() == null) {
                return second;
            } else if (second.get() == null) {
                return first;
            }

            B firstMaxValue = extractor.apply(first.get());
            B secondMaxValue = extractor.apply(second.get());

            if (firstMaxValue.compareTo(secondMaxValue) > 0) {
                return first;
            } else {
                return second;
            }
        };
    }

    @Override
    public BiConsumer<AtomicReference<T>, Downstream<? super T>> finisher() {
        return (state, downstream) -> downstream.push(state.get());
    }
}

class S14MaxBySelector {
    public static void main(String[] args) {
        List<String> words = List.of("Ala", "ma", "kota");
        List<String> longestWord = words.stream()
                .parallel()
                .gather(new MaxBySelectorGatherer<>(String::length))
                .toList();
        System.out.println(longestWord);
    }
}
