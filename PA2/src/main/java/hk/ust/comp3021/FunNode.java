package hk.ust.comp3021;

import java.util.*;
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

    inputs.set(i, Optional.of(value));
    if (inputs.stream().allMatch(Optional::isPresent)) {
      return Optional.of(this);
    } else {
      return Optional.empty();
    }
//    throw new UnsupportedOperationException();
  }

  public T getResult() { return output.get(); }

  public void eval(){
    // part 1: function data dependency graph node
    List<T> values = inputs.stream()
            .map(Optional::get)
            .toList();
    output = Optional.of(f.apply(values));

//    throw new UnsupportedOperationException();
  }
}
