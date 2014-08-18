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
package fm.last.musicbrainz.coverart.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtArchiveClient;
import fm.last.musicbrainz.coverart.CoverArtException;
import fm.last.musicbrainz.coverart.CoverArtImage;

public class DefaultCoverArtArchiveClient implements CoverArtArchiveClient {

  private static final Logger log = LoggerFactory.getLogger(DefaultCoverArtArchiveClient.class);

  private static final String API_DOMAIN = "coverartarchive.org/";
  private static final String API_ROOT = "http://" + API_DOMAIN;
  private static final String API_ROOT_HTTPS = "https://" + API_DOMAIN;

  private final HttpClient client;
  private final ProxiedCoverArtFactory factory = new ProxiedCoverArtFactory(this);

  private final ResponseHandler<String> fetchJsonListingHandler = FetchJsonListingResponseHandler.INSTANCE;
  private final ResponseHandler<InputStream> fetchImageDataHandler = FetchImageDataResponseHandler.INSTANCE;

  private boolean isUsingHttps;

  public DefaultCoverArtArchiveClient() {
    // Only use HTTPS if explicitly requested
    this(false);
  }

  /**
   * Creates a client that explicitly communicates with or without secure HTTP.
   * 
   * @param isUsingHttps <code>true</code> uses HTTPS to connect to coverartarchive. <br/>
   *          <i>Note:</i> this only applies to communication with coverartarchive.org. They might return plain HTTP
   *          links to image files. You might want to handle this yourself using {@link CoverArtImage}.
   *          <code>getXYZUrl()</code> methods, such as {@link CoverArtImage#getImageUrl()}.
   */
  public DefaultCoverArtArchiveClient(boolean isUsingHttps) {
    this(isUsingHttps, null);
  }

  /**
   * Allows for using a custom HTTP client. This might be necessary when the default client used here does not suit the
   * needs. For example to replace the outdated version shipped with android.
   * 
   * @param isUsingHttps <code>true</code> uses HTTPS to connect to coverartarchive. <br/>
   *          <i>Note:</i> this only applies to communication with coverartarchive.org. They might return plain HTTP
   *          links to image files. You might want to handle this yourself using {@link CoverArtImage}.
   *          <code>getXYZUrl()</code> methods, such as {@link CoverArtImage#getImageUrl()}.
   * @param client custom client. <code>null</code> results in using the default client
   */
  public DefaultCoverArtArchiveClient(boolean isUsingHttps, HttpClient client) {
    if (client == null) {
      this.client = new DefaultHttpClient();
    } else {
      this.client = client;
    }
    this.isUsingHttps = isUsingHttps;
  }

  @Override
  public CoverArt getByMbid(UUID mbid) throws CoverArtException {
    return getByMbid(CoverArtArchiveEntity.RELEASE, mbid);
  }

  @Override
  public CoverArt getReleaseGroupByMbid(UUID mbid) throws CoverArtException {
    return getByMbid(CoverArtArchiveEntity.RELEASE_GROUP, mbid);
  }

  private CoverArt getByMbid(CoverArtArchiveEntity entity, UUID mbid) {
    log.info("mbid={}", mbid);
    HttpGet getRequest = getJsonGetRequest(entity, mbid);
    CoverArt coverArt = null;
    try {
      String json = client.execute(getRequest, fetchJsonListingHandler);
      coverArt = factory.valueOf(json);
    } catch (IOException e) {
      throw new CoverArtException(e);
    }
    return coverArt;
  }

  InputStream getImageData(String location) throws IOException {
    log.info("location={}", location);
    HttpGet getRequest = getJpegGetRequest(location);
    return client.execute(getRequest, fetchImageDataHandler);
  }

  private HttpGet getJpegGetRequest(String location) {
    HttpGet getRequest = new HttpGet(location);
    getRequest.addHeader("accept", "image/jpeg");
    return getRequest;
  }

  private HttpGet getJsonGetRequest(CoverArtArchiveEntity entity, UUID mbid) {
    String url;
    if (isUsingHttps) {
      url = API_ROOT_HTTPS;
    } else {
      url = API_ROOT;
    }
    url += entity.getUrlParam() + mbid;
    HttpGet getRequest = new HttpGet(url);
    getRequest.addHeader("accept", "application/json");
    return getRequest;
  }

  /**
   * The entity (vocabulary of MusicBrainz) a cover art belongs to.
   * 
   * @author schnatterer
   */
  private static enum CoverArtArchiveEntity {

    /** The basic entity that has a cover art. */
    RELEASE("release/"),
    /**
     * A group of releases (where each can have its own art). A release group does not have its own cover art but Cover
     * Art Archive maps one of the releases' cover art to the release group.
     */
    RELEASE_GROUP("release-group/");

    /** The API URL parameter that is used for querying this entity. */
    private String urlParam;

    private CoverArtArchiveEntity(String urlParam) {
      this.urlParam = urlParam;
    }

    /**
     * @return the API URL parameter that is used for querying this entity
     */
    public String getUrlParam() {
      return urlParam;
    }
  }
}
