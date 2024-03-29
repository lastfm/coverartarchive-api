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
package fm.last.musicbrainz.coverart.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import fm.last.musicbrainz.coverart.util.HttpUtil;

enum FetchJsonListingResponseHandler implements ResponseHandler<String> {
  /* */
  INSTANCE;

  @Override
  public String handleResponse(HttpResponse response) throws IOException {
    StatusLine statusLine = response.getStatusLine();
    HttpEntity entity = response.getEntity();

    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
      return EntityUtils.toString(entity);
    } else if (statusLine.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
      return null;
    }
    HttpUtil.consumeEntity(entity);
    throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
  }
}
