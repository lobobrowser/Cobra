package org.cobraparser.util;

public interface SimpleThreadPoolTask extends Runnable {
  public void cancel();
}
