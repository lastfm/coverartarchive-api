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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
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
public class FetchImageDataResponseHandlerTest {

  private static final byte[] DATA = new byte[] { 123 };

  @Mock
  private HttpResponse response;

  @Mock
  private HttpEntity entity;

  @Mock
  private StatusLine statusLine;

  private final ResponseHandler<InputStream> handler = FetchImageDataResponseHandler.INSTANCE;

  @Before
  public void initialise() throws IllegalStateException, IOException {
    InputStream inputStream = new ByteArrayInputStream(DATA);
    when(entity.getContent()).thenReturn(inputStream);
    when(response.getStatusLine()).thenReturn(statusLine);
  }

  @Test
  public void inputStreamIsReturnedWhenImageExists() throws IOException {
    when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
    when(response.getEntity()).thenReturn(entity);
    InputStream actual = handler.handleResponse(response);
    assertThat(IOUtils.toByteArray(actual), is(DATA));
  }

  @Test(expected = IOException.class)
  public void ioExceptionIsThrownWhenRequestCannotBeFulfilled() throws IOException {
    when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_NOT_FOUND);
    InputStream stream = handler.handleResponse(response);
  }

}
