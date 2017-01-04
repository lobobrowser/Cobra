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

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;

import cz.vutbr.web.css.CombinedSelector;
import cz.vutbr.web.css.RuleSet;

final class CSSStyleRuleImpl extends AbstractCSSRule implements CSSStyleRule {

  final RuleSet ruleSet;

  CSSStyleRuleImpl(final RuleSet ruleSet, final JStyleSheetWrapper containingStyleSheet) {
    super(containingStyleSheet);
    this.ruleSet = ruleSet;
  }

  public short getType() {
    return CSSRule.STYLE_RULE;
  }

  public String getCssText() {
    return ruleSet.toString();
  }

  public String getSelectorText() {
    final String selector = ruleSet.getSelectors().toString();
    return CSSUtils.removeBrackets(selector);
  }

  public CSSStyleDeclaration getStyle() {
    return new CSSStyleDeclarationImpl(ruleSet.asList(), this);
  }

  public void setCssText(final String cssText) throws DOMException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException();
  }

  public void setSelectorText(final String selectorText) throws DOMException {
    final List<CombinedSelector> combinedSelectors = CSSUtils.createCombinedSelectors(selectorText);
    this.ruleSet.setSelectors(combinedSelectors);
    this.containingStyleSheet.informChanged();
  }

  @Override
  public String toString() {
    return ruleSet.toString();
  }

}
