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
import org.w3c.dom.css.CSSMediaRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.stylesheets.MediaList;

import cz.vutbr.web.css.RuleMedia;
import cz.vutbr.web.css.RuleSet;

final class CSSMediaRuleImpl extends AbstractCSSRule implements CSSMediaRule {

  protected RuleMedia mediaRule;

  CSSMediaRuleImpl(final RuleMedia mediaRule, final JStyleSheetWrapper containingStyleSheet) {
    super(containingStyleSheet);
    this.mediaRule = mediaRule;
  }

  public short getType() {
    return CSSRule.MEDIA_RULE;
  }

  public String getCssText() {
    return mediaRule.toString();
  }

  public void setCssText(final String cssText) throws DOMException {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

  public MediaList getMedia() {
    return new MediaListImpl(mediaRule.getMediaQueries(), this.containingStyleSheet);
  }

  public CSSRuleList getCssRules() {
    return new CSSMediaRuleList();
  }

  public int insertRule(final String rule, final int index) throws DOMException {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

  public void deleteRule(final int index) throws DOMException {
    // TODO implement this method
    throw new UnsupportedOperationException();
  }

  final private class CSSMediaRuleList implements CSSRuleList {

    /**
     * @return The number of <code>CSSRules</code> in the list. The range of
     *         valid child rule indices is <code>0</code> to
     *         <code>length-1</code> inclusive.
     */
    public int getLength() {
      return mediaRule.size();
    }

    /**
     * Used to retrieve a CSS rule by ordinal index. The order in this
     * collection represents the order of the rules in the CSS style sheet. If
     * index is greater than or equal to the number of rules in the list, this
     * returns <code>null</code>.
     *
     * @param index
     *          Index into the collection
     * @return The style rule at the <code>index</code> position in the
     *         <code>CSSRuleList</code>, or <code>null</code> if that is not a
     *         valid index.
     */
    public CSSRule item(final int index) {
      try {
        // according to JStyle Parser RuleMedia is a list of RuleSet
        final RuleSet ruleSet = mediaRule.asList().get(index);
        return new CSSStyleRuleImpl(ruleSet, containingStyleSheet);
      } catch (final ArrayIndexOutOfBoundsException e) {
        return null;
      }
    }
  }
}
