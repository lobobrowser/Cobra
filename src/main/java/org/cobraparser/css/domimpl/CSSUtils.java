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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.DOMException;

import cz.vutbr.web.css.CSSException;
import cz.vutbr.web.css.CSSFactory;
import cz.vutbr.web.css.CombinedSelector;
import cz.vutbr.web.css.RuleSet;
import cz.vutbr.web.css.StyleSheet;

final class CSSUtils {

  static StyleSheet parse(final String css) {
    try {
      return CSSFactory.parse(css);
    } catch (IOException | CSSException e) {
      throw new DOMException(DOMException.SYNTAX_ERR, "");
    }
  }

  static List<CombinedSelector> createCombinedSelectors(final String selectorText) {
    final StyleSheet jSheet = parse(selectorText + "{}");
    if (jSheet.size() > 0) {
      final RuleSet ruleSet = (RuleSet) jSheet.get(0);
      return Arrays.asList(ruleSet.getSelectors());
    }
    return new ArrayList<CombinedSelector>();
  }

  static String removeBrackets(final String str) {
    return str.substring(1, str.length() - 1);
  }

}
