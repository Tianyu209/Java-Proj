package hk.ust.comp3021;

import java.util.*;
import java.util.function.Consumer;

public class SeqEvaluator<T> implements Evaluator<T> {
  private HashMap<FunNode<T>, List<Consumer<T>>> listeners = new HashMap<>();

  public void addDependency(FunNode<T> a, FunNode<T> b, int i) {
    // part 2: sequential function evaluator
    listeners.computeIfAbsent(a, k -> new ArrayList<>()).add(value -> b.setInput(i, value));

//    throw new UnsupportedOperationException();
  }

  public void start(List<FunNode<T>> nodes) {
    // part 2: sequential function evaluator
    nodes.forEach(this::evaluate);
//    throw new UnsupportedOperationException();
  }
  private void evaluate(FunNode<T> node) {
    // Implement recursive evaluation logic here
    node.eval();
    T result = node.getResult();
    List<Consumer<T>> nodeListeners = listeners.get(node);
    if (nodeListeners != null) {
      for (Consumer<T> listener : nodeListeners) {
        listener.accept(result);
        // Find the dependent node and recursively evaluate it
        for (Map.Entry<FunNode<T>, List<Consumer<T>>> entry : listeners.entrySet()) {
          if (entry.getValue().contains(listener)) {
            evaluate(entry.getKey());
            break;
          }
        }
      }
    }
  }
}
