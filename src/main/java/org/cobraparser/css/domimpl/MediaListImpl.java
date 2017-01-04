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
import org.w3c.dom.stylesheets.MediaList;

import cz.vutbr.web.css.MediaQuery;

final class MediaListImpl implements MediaList {

  final private JStyleSheetWrapper containingStyleSheet;
  final private List<String> mediaList;

  MediaListImpl(final String mediaListStr, final JStyleSheetWrapper containingStyleSheet) {
    this.mediaList = splitMediaList(mediaListStr);
    this.containingStyleSheet = containingStyleSheet;
  }

  MediaListImpl(final List<MediaQuery> mediaQueries, final JStyleSheetWrapper containingStyleSheet) {
    final List<String> mediaList = new ArrayList<String>();
    for (final MediaQuery mediaQuery : mediaQueries) {
      mediaList.add(mediaQuery.getType());
    }
    this.mediaList = mediaList;
    this.containingStyleSheet = containingStyleSheet;
  }

  public String getMediaText() {
    return this.mediaList.toString();
  }

  public void setMediaText(final String mediaText) throws DOMException {
    //TODO send a notification about the change
    /*
    this.mediaList.clear();
    this.mediaList.addAll(splitMediaList(mediaText));
     */
    throw new UnsupportedOperationException();
  }

  public int getLength() {
    return this.mediaList.size();
  }

  public String item(final int index) {
    return this.mediaList.get(index);
  }

  public void deleteMedium(final String oldMedium) throws DOMException {
    final int index = this.mediaList.indexOf(oldMedium);
    this.mediaList.remove(index);
    this.containingStyleSheet.informChanged();
  }

  public void appendMedium(final String newMedium) throws DOMException {
    this.mediaList.add(newMedium);
    this.containingStyleSheet.informChanged();
  }

  private List<String> splitMediaList(final String mediaListStr) {
    if ((mediaListStr != null) && (mediaListStr.length() > 0)) {
      final String[] mediaArray = mediaListStr.split(",");
      final List<String> mediaList = new ArrayList<String>();
      for (final String media : mediaArray) {
        mediaList.add(media);
      }
      return mediaList;
    }
    return new ArrayList<String>();
  }

  @Override
  public String toString() {
    return mediaList.toString();
  }

}
