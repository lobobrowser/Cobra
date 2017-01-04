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
import org.w3c.dom.css.CSSFontFaceRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;

import cz.vutbr.web.css.RuleFontFace;

final class CSSFontFaceRuleImpl extends AbstractCSSRule implements CSSFontFaceRule {

  final RuleFontFace rule;

  CSSFontFaceRuleImpl(final RuleFontFace rule, final JStyleSheetWrapper containingStyleSheet) {
    super(containingStyleSheet);
    this.rule = rule;
  }

  public String getCssText() {
    return rule.toString();
  }

  public CSSStyleDeclaration getStyle() {
    return new CSSStyleDeclarationImpl(rule.asList(), this);
  }

  public short getType() {
    return CSSRule.FONT_FACE_RULE;
  }

  public void setCssText(final String cssText) throws DOMException {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

}
