package org.cobraparser.js;

public interface JavaInstantiator {
  public Object newInstance(Object[] args) throws InstantiationException, IllegalAccessException;
}
