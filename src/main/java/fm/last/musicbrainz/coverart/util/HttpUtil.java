/**
 * Copyright (C) 2012-2022 Last.fm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fm.last.musicbrainz.coverart.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

  /**
   * Ensures that the entity content is fully consumed and the content stream, if exists, is closed.<br/>
   * <br/>
   * This method is copied from Apache HttpClient version 4.1 {@link EntityUtils#consume(HttpEntity)}, in order to keep
   * compatibility with lower HttpClient versions, such as the "pre-BETA snapshot" used in android.
   *
   * @param entity
   * @throws IOException if an error occurs reading the input stream
   * @since 4.1
   */
  public static void consumeEntity(final HttpEntity entity) throws IOException {
    if (entity == null) {
      return;
    }
    if (entity.isStreaming()) {
      InputStream instream = entity.getContent();
      if (instream != null) {
        instream.close();
      }
    }
  }

}
