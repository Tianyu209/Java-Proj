package hk.ust.comp3021;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Optional;

public class FunNode<T> {
  private List<Optional<T>> inputs;
  private Optional<T> output = Optional.empty();
  private Function<List<T>, T> f;

  public FunNode(int arity, Function<List<T>, T> fun) {
    // part 1: function data dependency graph node
    this.inputs = new ArrayList<>(Collections.nCopies(arity, Optional.empty()));
    this.f = fun;
//    throw new UnsupportedOperationException(); testing
  }

  public Optional<FunNode<T>> setInput(int i, T value) {
    // part 1: function data dependency graph node
    if (i < 0 || i >= inputs.size()) {
      return Optional.empty();
    }
    inputs.set(i, Optional.of(value));
    return inputs.stream().allMatch(Optional::isPresent) ? Optional.of(this) : Optional.empty();
//    throw new UnsupportedOperationException();
  }
  public List<Optional<T>> getInputs() {
    return inputs;
  }
  public T getResult() {
    if (output.isPresent()) {
      return output.get();
    } else {
      throw new IllegalStateException("Result not available. Ensure eval() is called first.");
    }
  }

  public synchronized void eval(){
    // part 1: function data dependency graph node
    List<T> values = inputs.stream().map(Optional::get).toList();
    output = Optional.of(f.apply(values));
//    throw new UnsupportedOperationException();
  }
}
