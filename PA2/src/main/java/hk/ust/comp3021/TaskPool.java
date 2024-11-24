package hk.ust.comp3021;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class TaskPool {
  public class TaskQueue {
    private ArrayDeque<Runnable> queue = new ArrayDeque<>();
    private boolean terminated = false;
    private int working;
    private Semaphore idle;

    public TaskQueue(int numThreads, Semaphore idle) {
      working = numThreads;
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
      if (terminated) {
        return Optional.empty();
      }
      working++;
      return Optional.of(queue.poll());
//      throw new UnsupportedOperationException();
    }

    public synchronized void addTask(Runnable task) {
      // part 3: task pool
      queue.offer(task);
      notify();
//      throw new UnsupportedOperationException();
    }

    public synchronized void terminate() {
      // part 3: task pool
      terminated = true;
      notifyAll();
//      throw new UnsupportedOperationException();
    }

    public synchronized void addTasks(List<Runnable> tasks) {
      //use foreach to replace for-loop
      tasks.forEach(queue::offer);
      notifyAll();

    }
    public synchronized void taskFinished() {
      working--;
      if (working == 0 && queue.isEmpty()) {
        idle.release();
      }
    }
    public synchronized boolean isIdle() {
      return queue.isEmpty() && working == 0;
    }
  }

  private TaskQueue queue;
  private Thread workers[];
  private Semaphore idle = new Semaphore(0);

  public TaskPool(int numThreads) {
    // part 3: task pool
    idle = new Semaphore(0);
    queue = new TaskQueue(numThreads, idle);
    workers = new Thread[numThreads];

    for (int i = 0; i < numThreads; i++) {
      workers[i] = new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
          Optional<Runnable> task = queue.getTask();
          if (task.isPresent()) {
            try {
              task.get().run();
            } finally {
              queue.taskFinished();
            }
          } else {
            break;
          }
        }
      });
      workers[i].start();
    }
//    throw new UnsupportedOperationException();
  }

  public void addTask(Runnable task) { queue.addTask(task); }

  public void addTasks(List<Runnable> tasks) {
    // part 3: task pool
    queue.addTasks(tasks);
    try {
      idle.acquire();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
//    throw new UnsupportedOperationException();
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
