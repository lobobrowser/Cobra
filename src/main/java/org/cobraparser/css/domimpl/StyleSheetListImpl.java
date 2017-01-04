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

import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;

final class StyleSheetListImpl implements StyleSheetList {

  final private StyleSheetBridge bridge;

  StyleSheetListImpl(final StyleSheetBridge bridge) {
    this.bridge = bridge;
  }

  /**
   * @return The number of <code>style sheets</code> in the list. The range of
   *         valid child stylesheet indices is <code>0</code> to
   *         <code>length-1</code> inclusive.
   */
  public int getLength() {
    return this.bridge.getDocStyleSheets().size();
  }

  /**
   * Used to retrieve a style sheet by ordinal index. If index is greater than
   * or equal to the number of style sheets in the list, this returns
   * <code>null</code>.
   *
   * @param index
   *          Index into the collection
   * @return The style sheet at the <code>index</code> position in the
   *         <code>StyleSheetList</code>, or <code>null</code> if that is not a
   *         valid index.
   */
  public CSSStyleSheet item(final int index) {
    return this.bridge.getDocStyleSheets().get(index);
  }

}
