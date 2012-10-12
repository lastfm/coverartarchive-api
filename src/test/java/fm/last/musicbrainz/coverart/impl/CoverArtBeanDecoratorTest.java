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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtImage;

@RunWith(MockitoJUnitRunner.class)
public class CoverArtBeanDecoratorTest {

  private static final String URL_RELEASE = "http://musicbrainz.org/release/123";

  @Mock
  private CoverArtImageBean coverArtImageBeanA;
  @Mock
  private CoverArtImageBean coverArtImageBeanB;
  @Mock
  private DefaultCoverArtArchiveClient client;

  private CoverArtBean coverArtBean;
  private CoverArt coverArt;

  @Before
  public void initialise() {
    when(coverArtImageBeanA.getId()).thenReturn(1L);
    when(coverArtImageBeanB.getId()).thenReturn(2L);
    List<CoverArtImageBean> images = Lists.newArrayList(coverArtImageBeanB, coverArtImageBeanA);

    coverArtBean = new CoverArtBean();
    coverArtBean.setRelease(URL_RELEASE);
    coverArtBean.setImages(images);

    coverArt = new CoverArtBeanDecorator(coverArtBean, client);
  }

  @Test
  public void gettersDelegateToBean() {
    assertThat(coverArt.getMusicBrainzReleaseUrl(), is(coverArtBean.getRelease()));
  }

  @Test
  public void gettingImagesReturnsProxiedCoverArtImages() {
    assertThat(coverArt.getImages(), hasSize(2));
    for (CoverArtImage coverArtImage : coverArt.getImages()) {
      assertThat(coverArtImage, is(instanceOf(ProxiedCoverArtImageBeanDecorator.class)));
      coverArtImage.getId();
    }
    verify(coverArtImageBeanA, times(1)).getId();
    verify(coverArtImageBeanB, times(1)).getId();
  }

  @Test
  public void gettingExistingImageByIdReturnsProxiedCoverArtImage() {
    CoverArtImage coverArtImage = coverArt.getById(1);
    assertThat(coverArtImage.getId(), is(1L));
    assertThat(coverArtImage, is(instanceOf(ProxiedCoverArtImageBeanDecorator.class)));
  }

  @Test
  public void gettingNotExistingImageByIdReturnsNull() {
    assertThat(coverArt.getById(1234), is(nullValue()));
  }
}
