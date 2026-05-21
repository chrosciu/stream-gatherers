package eu.chrost.streamgatherers.p08finishing;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

@RequiredArgsConstructor
class MaxBySelectorGatherer<T, P extends Comparable<P>> implements Gatherer<T, AtomicReference<T>, T> {
    private final Function<T, P> extractor;

    @Override
    public Supplier<AtomicReference<T>> initializer() {
        return () -> new AtomicReference<>(null);
    }

    @Override
    public Integrator<AtomicReference<T>, T, T> integrator() {
        return Integrator.ofGreedy((maxItemRef, item, _) -> {
            Optional.ofNullable(maxItemRef.get()).ifPresentOrElse(
                    maxItem -> {
                        P currentProperty = extractor.apply(item);
                        P maxProperty = extractor.apply(maxItem);
                        if (currentProperty.compareTo(maxProperty) > 0) {
                            maxItemRef.set(item);
                        }
                    },
                    () -> maxItemRef.set(item)
            );
            return true;
        });
    }

    @Override
    public BiConsumer<AtomicReference<T>, Downstream<? super T>> finisher() {
        return (maxItemRef, downstream) ->
                Optional.ofNullable(maxItemRef.get()).ifPresent(downstream::push);
    }
}

class S08MaxBySelector {
    private static final String TEXT = "Alice has a cat, and the cat owns Alice";

    public static void main(String[] args) {
        List<String> longestWord = Arrays.stream(TEXT.split("\\W+"))
                .gather(new MaxBySelectorGatherer<>(String::length))
                .toList();
        System.out.println(longestWord);
    }
}

