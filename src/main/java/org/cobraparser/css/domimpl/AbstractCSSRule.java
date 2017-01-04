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

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;

abstract class AbstractCSSRule implements CSSRule {

  final protected JStyleSheetWrapper containingStyleSheet;

  AbstractCSSRule(final JStyleSheetWrapper containingStyleSheet) {
    this.containingStyleSheet = containingStyleSheet;
  }

  /**
   * @return The style sheet that contains this rule.
   */
  public CSSStyleSheet getParentStyleSheet() {
    return containingStyleSheet;
  }

  /**
   * If this rule is contained inside another rule (e.g. a style rule inside an @media
   * block), this is the containing rule. If this rule is not nested inside any
   * other rules, this returns <code>null</code>.
   */
  public CSSRule getParentRule() {
    // TODO needs to be overridden in MediaRule
    return null;
  }

}
