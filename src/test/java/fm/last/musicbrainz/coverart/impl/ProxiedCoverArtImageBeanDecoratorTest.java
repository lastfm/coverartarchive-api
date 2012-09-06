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
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import fm.last.musicbrainz.coverart.CoverArtImage;
import fm.last.musicbrainz.coverart.CoverArtType;
import fm.last.musicbrainz.coverart.impl.CoverArtImageBean.CoverArtImageThumbnailsBean;

@RunWith(MockitoJUnitRunner.class)
public class ProxiedCoverArtImageBeanDecoratorTest {

  private static final String URL_IMAGE = "http://coverartarchive.org/release/123/1.jpg";
  private static final String URL_LARGE = "http://coverartarchive.org/release/123/1-500.jpg";
  private static final String URL_SMALL = "http://coverartarchive.org/release/123/1-250.jpg";

  private CoverArtImageBean imageBean;
  private CoverArtImageThumbnailsBean thumbnailsBean;

  @Mock
  private DefaultCoverArtArchiveClient client;

  @Before
  public void initialise() {
    List<String> types = Lists.newArrayList("Front", "Back", "Medium");

    thumbnailsBean = new CoverArtImageThumbnailsBean();
    thumbnailsBean.setLarge(URL_LARGE);
    thumbnailsBean.setSmall(URL_SMALL);

    imageBean = new CoverArtImageBean();
    imageBean.setApproved(true);
    imageBean.setBack(false);
    imageBean.setFront(true);
    imageBean.setComment("Comment");
    imageBean.setEdit(123);
    imageBean.setId(456);
    imageBean.setImage(URL_IMAGE);
    imageBean.setThumbnails(thumbnailsBean);
    imageBean.setTypes(types);
  }

  @Test
  public void gettersDelegateToBeans() {
    CoverArtImage image = new ProxiedCoverArtImageBeanDecorator(imageBean, client);
    assertThat(image.isApproved(), is(imageBean.isApproved()));
    assertThat(image.isBack(), is(imageBean.isBack()));
    assertThat(image.isFront(), is(imageBean.isFront()));
    assertThat(image.getComment(), is(imageBean.getComment()));
    assertThat(image.getEdit(), is(imageBean.getEdit()));
    assertThat(image.getId(), is(imageBean.getId()));
  }

  @Test
  public void coverArtTypesAreConvertedFromStringsToEnumValues() {
    CoverArtImage image = new ProxiedCoverArtImageBeanDecorator(imageBean, client);
    assertThat(image.getTypes(), containsInAnyOrder(CoverArtType.FRONT, CoverArtType.BACK, CoverArtType.MEDIUM));
  }

  @Test
  public void gettingAnImageIsDelegatedToClient() throws IOException {
    CoverArtImage image = new ProxiedCoverArtImageBeanDecorator(imageBean, client);
    image.getImage();
    image.getLargeThumbnail();
    image.getSmallThumbnail();

    verify(client, times(1)).getImageData(URL_IMAGE);
    verify(client, times(1)).getImageData(URL_LARGE);
    verify(client, times(1)).getImageData(URL_SMALL);
  }

}
