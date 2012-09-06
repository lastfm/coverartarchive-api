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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FetchJsonListingResponseHandlerTest {

  @Mock
  private HttpResponse response;

  @Mock
  private HttpEntity entity;

  @Mock
  private StatusLine statusLine;

  private InputStream inputStream;

  private final ResponseHandler<String> handler = FetchJsonListingResponseHandler.INSTANCE;

  @Before
  public void initialise() throws IllegalStateException, IOException {
    inputStream = IOUtils.toInputStream("JSON");
    when(entity.getContent()).thenReturn(inputStream);
    when(response.getStatusLine()).thenReturn(statusLine);
  }

  @Test
  public void jsonIsReturnedWhenReleaseExists() throws IOException {
    when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
    when(response.getEntity()).thenReturn(entity);
    String json = handler.handleResponse(response);
    assertThat(json, is("JSON"));
  }

  @Test
  public void nullIsReturnedWhenNoReleaseExists() throws IOException {
    when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_NOT_FOUND);
    when(response.getEntity()).thenReturn(null);
    String json = handler.handleResponse(response);
    assertThat(json, is(nullValue()));
  }

  @Test(expected = IOException.class)
  public void ioExceptionIsThrownWhenRequestCannotBeFulfilled() throws IOException {
    when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_METHOD_NOT_ALLOWED);
    String json = handler.handleResponse(response);
  }

}
