package hk.ust.comp3021;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskPool {
  public class TaskQueue {
    private final ArrayDeque<Runnable> queue = new ArrayDeque<>();
    private boolean terminated = false;
    private int working ;
    private final Semaphore idle;

    public TaskQueue(int numThreads, Semaphore idle) {
      working = 0;
      this.idle = idle;
    }

    public synchronized Optional<Runnable> getTask() {
      // part 3: task pool
      if (terminated && queue.isEmpty()) {
        return Optional.empty();
      }

      if (queue.isEmpty()) {
        try {
          wait();
        } catch (InterruptedException e) {
          return Optional.empty();
        }
        if (terminated && queue.isEmpty()) {
          return Optional.empty();
        }
      }

      working++;
      return Optional.ofNullable(queue.poll());
//      throw new UnsupportedOperationException();
    }

    public synchronized void addTask(Runnable task) {
      // part 3: task pool
      if (!terminated) {
        queue.offer(task);
        notify();
      }
//      throw new UnsupportedOperationException();
    }

    public synchronized void terminate() {
      // part 3: task pool
      terminated = true;
      queue.clear();
      notifyAll();
    }
    public synchronized void finishTask() {
      working--;
      if (working == 0 && queue.isEmpty()) {
        idle.release();
      }
    }
    public synchronized boolean isTerminated() {
      return terminated && queue.isEmpty() && working == 0;
    }
  }

  private final TaskQueue queue;
  private final Thread[] workers;
  private Semaphore idle = new Semaphore(0);

  public TaskPool(int numThreads) {
    // part 3: task pool
    idle = new Semaphore(0);
    queue = new TaskQueue(numThreads, idle);
    workers = new Thread[numThreads];

    Runnable workerTask = () -> {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          queue.getTask().ifPresentOrElse(
                  task -> {
                    try {
                      task.run();
                    } finally {
                      queue.finishTask();
                    }
                  },
                  () -> Thread.currentThread().interrupt()
          );
        } catch (Exception e) {
          // Log the exception if needed
          Thread.currentThread().interrupt();
        }
      }
    };

    Arrays.setAll(workers, i -> {
      Thread worker = new Thread(workerTask);
      worker.start();
      return worker;
    });
//    throw new UnsupportedOperationException();
  }

  public void addTask(Runnable task) { queue.addTask(task); }

  public void addTasks(List<Runnable> tasks) {
    // part 3: task pool
    if (tasks.isEmpty()) {
      return;
    }
    final int BATCH_SIZE = 64;
    IntStream.range(0, tasks.size())
            .boxed()
            .collect(Collectors.groupingBy(i -> i / BATCH_SIZE))
            .values()
            .forEach(batch -> batch.forEach(i -> queue.addTask(tasks.get(i))));
    try {
      idle.acquire();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public void terminate() {
    queue.terminate();
    for (Thread worker : workers) {
      worker.interrupt();
    }
    for (Thread worker : workers) {
      try {
        worker.join(50);
      } catch (InterruptedException ignored) {
      }
    }
  }
}
