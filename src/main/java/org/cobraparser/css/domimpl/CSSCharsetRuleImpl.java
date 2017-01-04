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

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSCharsetRule;
import org.w3c.dom.css.CSSRule;

final class CSSCharsetRuleImpl extends AbstractCSSRule implements CSSCharsetRule {

  CSSCharsetRuleImpl(final JStyleSheetWrapper containingStyleSheet) {
    super(containingStyleSheet);
  }

  public short getType() {
    return CSSRule.CHARSET_RULE;
  }

  public String getCssText() {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

  public void setCssText(final String cssText) throws DOMException {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

  public String getEncoding() {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

  public void setEncoding(final String encoding) throws DOMException {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

}
