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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtException;
import fm.last.musicbrainz.coverart.impl.util.TestUtil;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCoverArtArchiveClientTest {

  private static final String API_ROOT = "http://coverartarchive.org/release/";
  private static final String API_ROOT_HTTPS = "https://coverartarchive.org/release/";
  private static final String API_ROOT_RELEASEGROUP = "http://coverartarchive.org/release-group/";
  private static final UUID MBID = UUID.fromString("2ba4396d-c0be-4a56-b4ea-0438306eb3be");

  @Mock
  private HttpClient httpClient;

  private DefaultCoverArtArchiveClient client;
  private ArgumentCaptor<HttpGet> httpGetCaptor;

  @Before
  public void initialise() {
    httpGetCaptor = ArgumentCaptor.forClass(HttpGet.class);
    // Use default constructor. Specific constructors are tested later on
    client = new DefaultCoverArtArchiveClient();
    TestUtil.setFinalField(client, "client", httpClient);
  }

  @Test
  public void mbidWithCoverArtReturnsCoverArt() throws Exception {
    mbidWithCoverArtReturnsCoverArt(API_ROOT);
  }

  @Test
  public void mbidWithoutCoverArtReturnsNull() throws Exception {
    when(httpClient.execute(any(HttpGet.class), eq(FetchJsonListingResponseHandler.INSTANCE))).thenReturn(null);
    CoverArt coverArt = client.getByMbid(MBID);
    assertThat(coverArt, is(nullValue()));
  }

  @Test(expected = CoverArtException.class)
  public void nullMbidThrowsCoverArtException() throws Exception {
    doThrow(new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "")).when(httpClient).execute(any(HttpGet.class),
        eq(FetchJsonListingResponseHandler.INSTANCE));
    client.getByMbid(null);
  }

  @Test
  public void correctImageCanBeRequested() throws Exception {
    InputStream data = mock(InputStream.class);
    when(httpClient.execute(httpGetCaptor.capture(), eq(FetchImageDataResponseHandler.INSTANCE))).thenReturn(data);
    InputStream actual = client.getImageData("exists.jpg");
    assertThat(actual, is(data));
    HttpGet httpGet = httpGetCaptor.getValue();
    assertThat(httpGet.getURI().toString(), is("exists.jpg"));
    assertThat(httpGet.getHeaders("accept")[0].toString(), is("accept: image/jpeg"));
  }

  @Test(expected = IOException.class)
  public void nonExistantImageThrowsIoException() throws Exception {
    doThrow(new HttpResponseException(HttpStatus.SC_NOT_FOUND, "")).when(httpClient).execute(any(HttpGet.class),
        eq(FetchImageDataResponseHandler.INSTANCE));
    client.getImageData("doesnotexist.jpg");
  }

  @Test
  public void releaseGroupMbidWithCoverArtReturnsCoverArt() throws Exception {
    when(httpClient.execute(httpGetCaptor.capture(), eq(FetchJsonListingResponseHandler.INSTANCE))).thenReturn("{}");
    CoverArt coverArt = client.getReleaseGroupByMbid(MBID);
    assertThat(coverArt, is(not(nullValue())));
    HttpGet httpGet = httpGetCaptor.getValue();
    assertThat(httpGet.getURI().toString(), is(API_ROOT_RELEASEGROUP + MBID));
    assertThat(httpGet.getHeaders("accept")[0].toString(), is("accept: application/json"));
  }

  @Test
  public void releaseGroupMbidWithoutCoverArtReturnsNull() throws Exception {
    when(httpClient.execute(any(HttpGet.class), eq(FetchJsonListingResponseHandler.INSTANCE))).thenReturn(null);
    CoverArt coverArt = client.getReleaseGroupByMbid(MBID);
    assertThat(coverArt, is(nullValue()));
  }

  @Test(expected = CoverArtException.class)
  public void releaseGroupNullMbidThrowsCoverArtException() throws Exception {
    doThrow(new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "")).when(httpClient).execute(any(HttpGet.class),
        eq(FetchJsonListingResponseHandler.INSTANCE));
    client.getReleaseGroupByMbid(null);
  }

  @Test
  public void doNotUseHttps() throws Exception {
    client = new DefaultCoverArtArchiveClient(false);
    TestUtil.setFinalField(client, "client", httpClient);

    // Expect default behavior
    mbidWithCoverArtReturnsCoverArt();
  }

  @Test
  public void useHttps() throws Exception {
    client = new DefaultCoverArtArchiveClient(true);
    TestUtil.setFinalField(client, "client", httpClient);

    mbidWithCoverArtReturnsCoverArt(API_ROOT_HTTPS);
  }

  @SuppressWarnings("unchecked")
  @Test(expected = CoverArtException.class)
  public void customClient() throws Exception {
    HttpClient customHttpClient = mock(HttpClient.class);
    when(customHttpClient.execute(any(HttpUriRequest.class), any(ResponseHandler.class))).thenThrow(new IOException());
    new DefaultCoverArtArchiveClient(false, customHttpClient).getByMbid(MBID);
  }

  private void mbidWithCoverArtReturnsCoverArt(String exepctedApiRoot) throws Exception {
    when(httpClient.execute(httpGetCaptor.capture(), eq(FetchJsonListingResponseHandler.INSTANCE))).thenReturn("{}");
    CoverArt coverArt = client.getByMbid(MBID);
    assertThat(coverArt, is(not(nullValue())));
    HttpGet httpGet = httpGetCaptor.getValue();
    assertThat(httpGet.getURI().toString(), is(exepctedApiRoot + MBID));
    assertThat(httpGet.getHeaders("accept")[0].toString(), is("accept: application/json"));
  }

}
