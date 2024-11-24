package hk.ust.comp3021;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ParEvaluator<T> implements Evaluator<T> {
  private HashMap<FunNode<T>, List<Consumer<T>>> listeners = new HashMap<>();
  private TaskPool pool;

  public ParEvaluator(int numThreads) { pool = new TaskPool(numThreads); }

  public void addDependency(FunNode<T> a, FunNode<T> b, int i) {
    // part 4: parallel function evaluator
    listeners.putIfAbsent(a, new ArrayList<>());

    listeners.get(a).add((result) -> {
      synchronized (b) {
        b.setInput(i, result);
          pool.addTask(b::eval);
      }
    });
//    throw new UnsupportedOperationException();
  }

  public void terminate() { pool.terminate(); }

  public void start(List<FunNode<T>> nodes) {
    // part 4: parallel function evaluator
    nodes.forEach(node -> pool.addTask(node::eval));
//    throw new UnsupportedOperationException();
  }
}
