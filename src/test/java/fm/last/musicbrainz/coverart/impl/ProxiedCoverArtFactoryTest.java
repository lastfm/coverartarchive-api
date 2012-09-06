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

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtImage;
import fm.last.musicbrainz.coverart.CoverArtType;

@RunWith(MockitoJUnitRunner.class)
public class ProxiedCoverArtFactoryTest {

  private static final String MUSICBRAINZ_URL = "http://musicbrainz.org/release/76df3287-6cda-33eb-8e9a-044b5e15ffdd";
  private static final String URL_IMAGE = "http://coverartarchive.org/release/76df3287-6cda-33eb-8e9a-044b5e15ffdd/829521842.jpg";
  private static final String URL_THUMBNAIL_LARGE = "http://coverartarchive.org/release/76df3287-6cda-33eb-8e9a-044b5e15ffdd/829521842-500.jpg";
  private static final String URL_THUMBNAIL_SMALL = "http://coverartarchive.org/release/76df3287-6cda-33eb-8e9a-044b5e15ffdd/829521842-250.jpg";

  private ProxiedCoverArtFactory factory;
  private String json;

  @Mock
  private DefaultCoverArtArchiveClient client;

  @Mock
  private InputStream inputStream;

  @Before
  public void initialise() throws IOException {
    json = FileUtils.readFileToString(new File("src/test/data/response_release-mbid.json"));
    factory = new ProxiedCoverArtFactory(client);
    when(client.getImageData(URL_IMAGE)).thenReturn(inputStream);
    when(client.getImageData(URL_THUMBNAIL_LARGE)).thenReturn(inputStream);
    when(client.getImageData(URL_THUMBNAIL_SMALL)).thenReturn(inputStream);
  }

  @Test
  public void emptyJsonStringReturnsNull() throws IOException {
    CoverArt coverArt = factory.valueOf("");
    assertThat(coverArt, is(nullValue()));
  }

  @Test
  public void validJsonResponseReturnsPopulatedCoverArt() throws IOException {
    CoverArt coverArt = factory.valueOf(json);
    assertThat(coverArt.getMusicBrainzReleaseUrl(), is(MUSICBRAINZ_URL));
    assertThat(coverArt.getImages(), hasSize(1));

    CoverArtImage coverArtImage = coverArt.getImages().iterator().next();
    assertThat(coverArtImage.getId(), is(829521842L));
    assertThat(coverArtImage.getEdit(), is(17462565L));
    assertThat(coverArtImage.getTypes(), hasSize(1));
    assertThat(coverArtImage.getTypes(), is(hasItem(CoverArtType.FRONT)));
    assertThat(coverArtImage.getImage(), is(inputStream));
    assertThat(coverArtImage.isFront(), is(true));
    assertThat(coverArtImage.isBack(), is(false));
    assertThat(coverArtImage.getComment(), is(""));
    assertThat(coverArtImage.isApproved(), is(true));
    assertThat(coverArtImage.getLargeThumbnail(), is(inputStream));
    assertThat(coverArtImage.getSmallThumbnail(), is(inputStream));
  }
}
