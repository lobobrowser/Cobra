package org.cobraparser.html.domimpl;

import org.cobraparser.html.BrowserFrame;

/**
 * Tag interface for frame nodes.
 */
public interface FrameNode {
  public BrowserFrame getBrowserFrame();

  public void setBrowserFrame(BrowserFrame frame);
}
