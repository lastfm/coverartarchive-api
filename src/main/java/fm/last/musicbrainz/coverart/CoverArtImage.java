/*
 * Copyright 2012 Last.fm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fm.last.musicbrainz.coverart;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface CoverArtImage {

  long getId();

  long getEdit();

  Set<CoverArtType> getTypes();

  /**
   * @throws IOException when the image could not be downloaded.
   */
  InputStream getImage() throws IOException;

  boolean isFront();

  boolean isBack();

  String getComment();

  boolean isApproved();

  /**
   * @throws IOException when the image could not be downloaded.
   */
  InputStream getLargeThumbnail() throws IOException;

  /**
   * @throws IOException when the image could not be downloaded.
   */
  InputStream getSmallThumbnail() throws IOException;

  String getImageUrl();

  String getLargeThumbnailUrl();

  String getSmallThumbnailUrl();

}