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
import org.w3c.dom.css.CSSValue;

import cz.vutbr.web.css.Declaration;
import cz.vutbr.web.css.RuleBlock;
import cz.vutbr.web.css.RuleSet;
import cz.vutbr.web.css.StyleSheet;

final class CSSStyleDeclarationImpl implements CSSStyleDeclaration {

  private final List<Declaration> declarations;
  private final AbstractCSSRule parentRule;

  CSSStyleDeclarationImpl(final List<Declaration> declarations, final AbstractCSSRule parentRule) {
    this.declarations = declarations;
    this.parentRule = parentRule;
  }

  public String getCssText() {
    return declarations.toString();
  }

  public void setCssText(final String cssText) throws DOMException {
    final StyleSheet jSheet = CSSUtils.parse("*{" + cssText + "}");
    declarations.clear();
    for (final RuleBlock<?> rule : jSheet) {
      declarations.addAll((RuleSet) rule);
    }
    final JStyleSheetWrapper styleSheet = parentRule.containingStyleSheet;
    styleSheet.informChanged();
  }

  public String getPropertyValue(final String propertyName) {
    final Declaration propertyValueTerm = getPropertyValueTerm(propertyName);
    return propertyValueTerm == null ? null : propertyValueTerm.asList().toString();
  }

  private Declaration getPropertyValueTerm(final String propertyName) {
    for (final Declaration d : declarations) {
      if (d.getProperty().equalsIgnoreCase(propertyName)) {
        return d;
      }
    }
    return null;
  }

  public CSSValue getPropertyCSSValue(final String propertyName) {
    // TODO implement this method
    throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "This operation is not supported");
  }

  public String removeProperty(final String propertyName) throws DOMException {
    if (!declarations.isEmpty()) {
      final Declaration currDecl = getPropertyValueTerm(propertyName);
      if (currDecl != null) {
        final String val = currDecl.toString();
        final int index = declarations.indexOf(currDecl);
        declarations.remove(index);
        final JStyleSheetWrapper styleSheet = parentRule.containingStyleSheet;
        styleSheet.informChanged();
        return val;
      }
    }
    return "";
  }

  public String getPropertyPriority(final String propertyName) {
    // TODO implement this method
    throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "This operation is not supported");
  }

  // TODO check if priority is optional
  // see how priority can be used with respect to jStyle.
  // currently priority is not being used at all
  public void setProperty(final String propertyName, final String value, final String priority) throws DOMException {
    final StyleSheet jSheet = parseStyle(propertyName, value, priority);
    for (final RuleBlock<?> rule : jSheet) {
      final RuleSet rs = (RuleSet) rule;
      for (final Declaration decl : rs) {
        final Declaration currDec = getPropertyValueTerm(propertyName);
        if (currDec == null) {
          declarations.add(decl);
        } else {
          currDec.clear();
          currDec.addAll(decl);
        }
      }
    }
  }

  public int getLength() {
    return declarations.size();
  }

  public String item(final int index) {
    return declarations.get(index).getProperty();
  }

  public CSSRule getParentRule() {
    return this.parentRule;
  }

  private StyleSheet parseStyle(final String propertyName, final String value, final String priority) {
    return CSSUtils.parse("* { " + propertyName + ": " + value + "; }");
  }

  @Override
  public String toString() {
    return declarations.toString();
  }
}
