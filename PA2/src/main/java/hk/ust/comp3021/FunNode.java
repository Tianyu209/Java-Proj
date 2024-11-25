package hk.ust.comp3021;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunNode<T> {
  private List<Optional<T>> inputs;
  private Optional<T> output = Optional.empty();
  private Function<List<T>, T> f;

  public FunNode(int arity, Function<List<T>, T> fun) {
    // part 1: function data dependency graph node
    if (arity < 0) {
      throw new IllegalArgumentException("Arity must be non-negative");
    }
    this.inputs = IntStream.range(0, arity)
            .mapToObj(i -> Optional.<T>empty())
            .collect(Collectors.toList());
    this.f = fun;
//    throw new UnsupportedOperationException();
  }

  public Optional<FunNode<T>> setInput(int i, T value) {
    // part 1: function data dependency graph node
    if (i < 0 || i >= inputs.size()) {
      throw new IllegalArgumentException("Input index out of bounds");
    }

    // Update the input at index i
    inputs.set(i, Optional.of(value));

    // Check if all inputs are ready
    boolean allInputsReady = inputs.stream().allMatch(Optional::isPresent);
    return allInputsReady ? Optional.of(this) : Optional.empty();
  }

  public T getResult() {
    return output.orElseThrow(() ->
            new IllegalStateException("Result is not available; node has not been evaluated.")
    );
  }

  public void eval() {
    // part 1: function data dependency graph node
    if (inputs.stream().anyMatch(Optional::isEmpty)) {
      throw new IllegalStateException("Cannot evaluate: some inputs are missing.");
    }
    List<T> inputValues = inputs.stream().map(Optional::get).collect(Collectors.toList());
    output = Optional.of(f.apply(inputValues));
  }

  public boolean isEvaluated() {
    return output.isPresent();
  }
}
