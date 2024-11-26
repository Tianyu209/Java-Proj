package hk.ust.comp3021;

import java.util.*;
import java.util.concurrent.Semaphore;

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
      while (queue.isEmpty() && !terminated) {
        try {
          wait();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return Optional.empty();
        }
      }
      if (queue.isEmpty()) {
        return Optional.empty();
      }
      working++;
      return Optional.of(queue.poll());
//      throw new UnsupportedOperationException();
    }

    public synchronized void addTask(Runnable task) {
      // part 3: task pool
      if (!terminated) {
        queue.offer(task);
        notifyAll();
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
      notifyAll();
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

    Arrays.setAll(workers, i -> new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        Optional<Runnable> task = queue.getTask();
        if (task.isEmpty()) {
          if (queue.isTerminated()) {
            break;
          }
          continue;
        }
        try {
          task.get().run();
        } finally {
          queue.finishTask();
        }
      }
    }));
    Arrays.stream(workers).forEach(Thread::start);
//    throw new UnsupportedOperationException();
  }

  public void addTask(Runnable task) { queue.addTask(task); }

  public void addTasks(List<Runnable> tasks) {
    // part 3: task pool
    if (!tasks.isEmpty()) {
      tasks.forEach(this::addTask);
        try {
            idle.acquire();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
    }
  }

  public void terminate() {
    queue.terminate();
    for (Thread thread : workers) {
      thread.interrupt();
    }
    for (Thread thread : workers) {
      try {
        thread.join();
      } catch (InterruptedException ignored) {
        Thread.currentThread().interrupt();
      }
    }
    for (Thread thread : workers) {
      if (thread.isAlive()) {
        thread.interrupt();
      }
    }
  }
}
