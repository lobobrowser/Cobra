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
import org.w3c.dom.Node;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;
import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;

/**
 * This is a wrapper for CSS DOM API implementation.
 *
 * Acts as a base Class for <code>CSSNodeStyleSheet</code> and
 * <code>CSSRuleStyleSheet</code>.
 *
 * This class implements the common methods between the two class mentioned.
 *
 */
public final class JStyleSheetWrapper implements CSSStyleSheet {

  private volatile cz.vutbr.web.css.StyleSheet jStyleSheet;
  private final CSSStyleSheet parentStyleSheet;
  final private String mediaStr;
  final private StyleSheetBridge bridge;
  private volatile boolean disabled;
  final private String href;
  private Node ownerNode;
  final private String type;
  final private String title;

  JStyleSheetWrapper(final cz.vutbr.web.css.StyleSheet jStyleSheet, final String mediaStr, final String href, final Node ownerNode,
      final CSSStyleSheet parentStyleSheet, final String type, final String title, final StyleSheetBridge bridge) {
    this.jStyleSheet = jStyleSheet;
    this.mediaStr = mediaStr;
    this.href = href;
    this.bridge = bridge;
    this.ownerNode = ownerNode;
    this.type = type;
    this.title = title;
    this.parentStyleSheet = parentStyleSheet;
  }

  /**
   * @param jStyleSheet
   *          parsed style sheet from jStyleParser
   * @param mediaStr
   *          The intended destination media for style information. The media is
   *          often specified in the <code>ownerNode</code>. If no media has
   *          been specified, the <code>MediaList</code> will be empty. See the
   *          media attribute definition for the <code>LINK</code> element in
   *          HTML 4.0, and the media pseudo-attribute for the XML style sheet
   *          processing instruction . Modifying the media list may cause a
   *          change to the attribute <code>disabled</code>.
   * @param href
   *          If the style sheet is a linked style sheet, the value of its
   *          attribute is its location. For inline style sheets, the value of
   *          this attribute is <code>null</code>. See the href attribute
   *          definition for the <code>LINK</code> element in HTML 4.0, and the
   *          href pseudo-attribute for the XML style sheet processing
   *          instruction.
   * @param parentStyleSheet
   *          For style sheet languages that support the concept of style sheet
   *          inclusion, this attribute represents the including style sheet, if
   *          one exists. If the style sheet is a top-level style sheet, or the
   *          style sheet language does not support inclusion, the value of this
   *          attribute is <code>null</code>.
   * @param bridge
   *          callback to notify any changes in the style sheet or to
   *          dynamically get data from the caller.
   */
  public JStyleSheetWrapper(final cz.vutbr.web.css.StyleSheet jStyleSheet, final String mediaStr, final String href,
      final CSSStyleSheet parentStyleSheet, final StyleSheetBridge bridge) {
    this(jStyleSheet, mediaStr, href, null, parentStyleSheet, null, null, bridge);
  }

  /**
   * @param jStyleSheet
   *          parsed style sheet from jStyleParser
   * @param mediaStr
   *          The intended destination media for style information. The media is
   *          often specified in the <code>ownerNode</code>. If no media has
   *          been specified, the <code>MediaList</code> will be empty. See the
   *          media attribute definition for the <code>LINK</code> element in
   *          HTML 4.0, and the media pseudo-attribute for the XML style sheet
   *          processing instruction . Modifying the media list may cause a
   *          change to the attribute <code>disabled</code>.
   * @param href
   *          If the style sheet is a linked style sheet, the value of its
   *          attribute is its location. For inline style sheets, the value of
   *          this attribute is <code>null</code>. See the href attribute
   *          definition for the <code>LINK</code> element in HTML 4.0, and the
   *          href pseudo-attribute for the XML style sheet processing
   *          instruction.
   * @param type
   *          the type of the style sheet. e.g. "text/css"
   * @param title
   *          The advisory title. The title is often specified in the
   *          <code>ownerNode</code>. See the title attribute definition for the
   *          <code>LINK</code> element in HTML 4.0, and the title
   *          pseudo-attribute for the XML style sheet processing instruction.
   * @param ownerNode
   *          The node that associates this style sheet with the document. For
   *          HTML, this may be the corresponding <code>LINK</code> or
   *          <code>STYLE</code> element. For XML, it may be the linking
   *          processing instruction. For style sheets that are included by
   *          other style sheets, the value of this attribute is
   *          <code>null</code>.
   * @param bridge
   *          callback to notify any changes in the style sheet or to
   *          dynamically get data from the caller.
   */
  public JStyleSheetWrapper(final cz.vutbr.web.css.StyleSheet jStyleSheet, final String mediaStr, final String href, final String type,
      final String title, final Node ownerNode, final StyleSheetBridge bridge) {
    this(jStyleSheet, mediaStr, href, ownerNode, null, type, title, bridge);
  }

  /**
   * @return The state of the style sheet <code>true</code> if the style sheet
   *         is disabled, <code>false</code> otherwise
   */
  public boolean getDisabled() {
    return this.disabled;
  }

  /**
   * This will enable/disable the style sheet and also send a notification to
   * all the listeners about this change in the style sheet.
   *
   * @param disabled
   *          state of the style sheet <code>true</code> if the style sheet is
   *          disabled, <code>false</code> otherwise
   */
  public void setDisabled(final boolean disabled) {
    this.disabled = disabled;
    this.informChanged();
  }

  /**
   * @return If the style sheet is a linked style sheet, the value returned is
   *         its location. For inline style sheets, the value returned will be
   *         <code>null</code>. For <code>@import</code> rules the returned
   *         value will be the url specified in the rule.
   */
  public String getHref() {
    return this.href;
  }

  /**
   * @return A list of media to which this style sheet is applicable
   */
  public MediaList getMedia() {
    return new MediaListImpl(mediaStr, this);
  }

  /**
   * @return style sheet language for this style sheet. <code>null</code> for
   *         <code>@import</code> rule as it does not have a type associated
   *         with it.
   */
  public String getType() {
    return this.type;
  }

  /**
   * @return The node that associates this style sheet with the document. For
   *         HTML, this may be the corresponding <code>LINK</code> or
   *         <code>STYLE</code> element. For XML, it may be the linking
   *         processing instruction. <code>null</code> for <code>@import</code>
   *         as the <code>@import</code> rule is not associated any
   *         <code>Node</code>.
   */
  public Node getOwnerNode() {
    return this.ownerNode;
  }

  // TODO hide it from JS
  public void setOwnerNode(final Node ownerNode) {
    this.ownerNode = ownerNode;
  }

  /**
   * @return The containing <code>Style Sheet</code>, applicable only for
   *         <code>@import</code> rules. <code>null</code> for nodes as the
   *         style sheet is a top-level style sheet, either from
   *         <code>LINK</code> or <code>STYLE</code> element.
   */
  public StyleSheet getParentStyleSheet() {
    return this.parentStyleSheet;
  }

  /**
   * @return The advisory title. The title specified in the
   *         <code>ownerNode</code>. e.g. the title attribute of the
   *         <code>LINK</code> element in HTML 4.0, and the title
   *         pseudo-attribute for the XML style sheet processing instruction.
   *         <code>null</code> for Import rules as the <code>@import</code> rule
   *         does not have a title associated with it.
   */
  public String getTitle() {
    return this.title;
  }

  public CSSRule getOwnerRule() {
    // TODO Import Rules are not yet supported, once supported this method should return
    // the @import rule which gets this style sheet
    // null for other cases
    throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "this operation is not supported");
  }

  /**
   * @return A list of rules contained in this style sheet.
   */
  public CSSRuleList getCssRules() {
    if (this.jStyleSheet != null) {
      return new CSSRuleListImpl(jStyleSheet, this);
    }
    throw new DOMException(DOMException.INVALID_ACCESS_ERR, "A parameter or an operation is not supported by the underlying object");
  }

  /**
   * Used to insert a new rule into the style sheet. The new rule now becomes
   * part of the cascade.
   *
   * @param rule
   *          The parsable text representing the rule. For rule sets this
   *          contains both the selector and the style declaration. For
   *          at-rules, this specifies both the at-identifier and the rule
   *          content.
   * @param index
   *          The index within the style sheet's rule list of the rule before
   *          which to insert the specified rule. If the specified index is
   *          equal to the length of the style sheet's rule collection, the rule
   *          will be added to the end of the style sheet.
   * @return The index within the style sheet's rule collection of the newly
   *         inserted rule.
   * @exception DOMException
   *              HIERARCHY_REQUEST_ERR: Raised if the rule cannot be inserted
   *              at the specified index e.g. if an <code>@import</code> rule is
   *              inserted after a standard rule set or other at-rule. <br>
   *              INDEX_SIZE_ERR: Raised if the specified index is not a valid
   *              insertion point. <br>
   *              NO_MODIFICATION_ALLOWED_ERR: Raised if this style sheet is
   *              readonly. <br>
   *              SYNTAX_ERR: Raised if the specified rule has a syntax error
   *              and is unparsable.
   */
  //TODO handle all the different types of exceptions as mentioned above
  public int insertRule(final String rule, final int index) throws DOMException {
    final cz.vutbr.web.css.StyleSheet jSheet = CSSUtils.parse(rule);
    if (jSheet.size() > 0) {
      this.jStyleSheet.add(index, jSheet.get(0));
      bridge.notifyStyleSheetChanged(this);
      return index;
    }
    return -1;
  }

  /**
   * Used to delete a rule from the style sheet.
   *
   * @param index
   *          The index within the style sheet's rule list of the rule to
   *          remove.
   * @exception DOMException
   *              INDEX_SIZE_ERR: Raised if the specified index does not
   *              correspond to a rule in the style sheet's rule list. <br>
   *              NO_MODIFICATION_ALLOWED_ERR: Raised if this style sheet is
   *              read-only.
   */
  public void deleteRule(final int index) throws DOMException {
    this.jStyleSheet.remove(index);
    bridge.notifyStyleSheetChanged(this);
  }

  // TODO hide it from JS
  public cz.vutbr.web.css.StyleSheet getJStyleSheet() {
    return this.jStyleSheet;
  }

  // TODO hide it from JS
  public void setJStyleSheet(final cz.vutbr.web.css.StyleSheet jStyleSheet) {
    this.jStyleSheet = jStyleSheet;
  }

  protected void informChanged() {
    bridge.notifyStyleSheetChanged(this);
  }

  /**
   * @param styleSheets
   *          Creates an StyleSheetList instance from these style sheets
   * @return StyleSheetList object constructed from the list of style sheets
   */
  public static StyleSheetList getStyleSheets(final StyleSheetBridge bridge) {
    return new StyleSheetListImpl(bridge);
  }
}
