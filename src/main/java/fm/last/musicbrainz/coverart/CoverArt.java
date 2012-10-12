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

import java.util.List;

public interface CoverArt {

  /**
   * @return Empty set when no {@link CoverArtImage}s exist.
   */
  List<CoverArtImage> getImages();

  String getMusicBrainzReleaseUrl();

  /**
   * @return Null when no image with {@code id} exists.
   */
  CoverArtImage getImageById(long id);

  /**
   * @return Null when the community have not chosen an image to represent the front of a release.
   */
  CoverArtImage getFrontImage();

  /**
   * @return Null when the community have not chosen an image to represent the back of a release.
   */
  CoverArtImage getBackImage();

}