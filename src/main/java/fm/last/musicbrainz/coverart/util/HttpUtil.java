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
