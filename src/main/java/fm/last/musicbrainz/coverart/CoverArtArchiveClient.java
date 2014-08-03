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

import java.util.UUID;

/**
 * Provides access to the Cover Art Archive.
 * 
 * @see <a href="http://musicbrainz.org/doc/Cover_Art_Archive/API">http://musicbrainz.org/doc/Cover_Art_Archive/API</a>
 */
public interface CoverArtArchiveClient {

  /**
   * Finds cover art of a MusicBrainz release.
   * 
   * @param mbid the MusicBrainz ID of the release
   * @return Returns null when no cover art exists for the given {@code mbid}.
   * @throws CoverArtException when {@code mbid} is not a valid UUID or the Cover Art Archive could not be accessed.
   */
  CoverArt getByMbid(UUID mbid) throws CoverArtException;

  /**
   * Finds cover art of a MusicBrainz release group.
   * 
   * @param mbid the MusicBrainz ID of the release group
   * @return Returns null when no cover art exists for the given {@code mbid}.
   * @throws CoverArtException when {@code mbid} is not a valid UUID or the Cover Art Archive could not be accessed.
   */
  CoverArt getReleaseGroupByMbid(UUID mbid) throws CoverArtException;
}
