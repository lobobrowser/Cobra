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
import org.w3c.dom.css.CSSRuleList;

import cz.vutbr.web.css.RuleBlock;
import cz.vutbr.web.css.RuleFontFace;
import cz.vutbr.web.css.RuleMedia;
import cz.vutbr.web.css.RulePage;
import cz.vutbr.web.css.RuleSet;
import cz.vutbr.web.css.StyleSheet;

final class CSSRuleListImpl implements CSSRuleList {

  final protected JStyleSheetWrapper parentStyleSheet;
  final private StyleSheet jSheet;

  CSSRuleListImpl(final StyleSheet jSheet, final JStyleSheetWrapper parentStyleSheet) {
    this.parentStyleSheet = parentStyleSheet;
    this.jSheet = jSheet;
  }

  /**
   * @return The number of <code>CSSRules</code> in the list. The range of valid
   *         child rule indices is <code>0</code> to <code>length-1</code>
   *         inclusive.
   */
  public int getLength() {
    return this.jSheet.size();
  }

  /**
   * Used to retrieve a CSS rule by ordinal index. The order in this collection
   * represents the order of the rules in the CSS style sheet. If index is
   * greater than or equal to the number of rules in the list, this returns
   * <code>null</code>.
   *
   * @param index
   *          Index into the collection
   * @return The style rule at the <code>index</code> position in the
   *         <code>CSSRuleList</code>, or <code>null</code> if that is not a
   *         valid index.
   */
  public CSSRule item(final int index) {
    try {
      final RuleBlock<?> ruleBlock = jSheet.asList().get(index);
      if (ruleBlock instanceof RuleSet) {
        final RuleSet ruleSet = (RuleSet) ruleBlock;
        return new CSSStyleRuleImpl(ruleSet, parentStyleSheet);
      } else if (ruleBlock instanceof RuleFontFace) {
        final RuleFontFace ruleFontFace = (RuleFontFace) ruleBlock;
        return new CSSFontFaceRuleImpl(ruleFontFace, parentStyleSheet);
      } else if (ruleBlock instanceof RulePage) {
        final RulePage rulePage = (RulePage) ruleBlock;
        return new CSSPageRuleImpl(rulePage, parentStyleSheet);
      } else if (ruleBlock instanceof RuleMedia) {
        final RuleMedia mediaRule = (RuleMedia) ruleBlock;
        return new CSSMediaRuleImpl(mediaRule, parentStyleSheet);
      } else {
        // TODO need to return the other types of RuleBlocks as well.
        // * Import Rule
        // * Charset Rule
        // Currently returning Unknown rule
        return new CSSUnknownRuleImpl(parentStyleSheet);
      }
    } catch (final ArrayIndexOutOfBoundsException e) {
      return null;
    }
  }

}
