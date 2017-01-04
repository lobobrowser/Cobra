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

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSPageRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;

import cz.vutbr.web.css.Declaration;
import cz.vutbr.web.css.Rule;
import cz.vutbr.web.css.RulePage;

final class CSSPageRuleImpl extends AbstractCSSRule implements CSSPageRule {

  private final RulePage rule;

  CSSPageRuleImpl(final RulePage rule, final JStyleSheetWrapper containingStyleSheet) {
    super(containingStyleSheet);
    this.rule = rule;
  }

  public short getType() {
    return CSSRule.PAGE_RULE;
  }

  public String getCssText() {
    return rule.toString();
  }

  public String getSelectorText() {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

  public CSSStyleDeclaration getStyle() {
    final List<Declaration> declarations = new ArrayList<Declaration>();
    for (final Rule<?> r : rule) {
      if (r instanceof Declaration) {
        final Declaration declaration = (Declaration) r;
        declarations.add(declaration);
      }
    }
    return new CSSStyleDeclarationImpl(declarations, this);
  }

  public void setCssText(final String cssText) throws DOMException {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

  public void setSelectorText(final String selectorText) throws DOMException {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

}
