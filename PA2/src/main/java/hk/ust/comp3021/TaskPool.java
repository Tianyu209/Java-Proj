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
    public synchronized void addTasks(List<Runnable> tasks) {
      // part 3: tasks pool
      if (!terminated) {
        queue.addAll(tasks);
        notifyAll();
      }
    }
    public synchronized void terminate() {
      // part 3: task pool
      terminated = true;
      notifyAll();
    }
    public synchronized void finishTask() {
      working--;
      if (working == 0 && queue.isEmpty()) {
        idle.release();
      }
      notifyAll();
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
            throw new RuntimeException(e);
        }
    }
  }

  public void terminate() {
    queue.terminate();
    for (Thread thread : workers) {
      try {
        // this will send an InterruptedException to the thread to wake it up
        // from blocking operations such as Thread.sleep.
        if (thread.isAlive())
          thread.interrupt();
        thread.join();
      } catch (InterruptedException ex) {
      }
    }
  }
}
