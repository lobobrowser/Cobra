/*
   Copyright 2014 Uproot Labs India Pvt Ltd

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.cobraparser.css.domimpl;

import java.util.List;

import org.w3c.dom.css.CSSStyleSheet;

/**
 * Interface for communicating the changes to the caller and getting data
 * dynamically from the caller.
 */
public interface StyleSheetBridge {

  /**
   * Notifies the listener that the style sheet has been changed.
   *
   * @param styleSheet
   *          The style sheet that has changed
   */
  public void notifyStyleSheetChanged(final CSSStyleSheet styleSheet);

  /**
   * @return a list of style sheet associated with the document to which this
   *         handler is attached.
   */
  public List<JStyleSheetWrapper> getDocStyleSheets();

}
