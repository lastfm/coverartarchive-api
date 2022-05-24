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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Predicate;

import fm.last.musicbrainz.coverart.CoverArtImage;

@RunWith(MockitoJUnitRunner.class)
public class IsBackImageTest {

  private final Predicate<CoverArtImage> predicate = IsBackImage.INSTANCE;
  @Mock
  private CoverArtImage coverArtImage;

  @Test
  public void backImageReturnsTrue() {
    when(coverArtImage.isBack()).thenReturn(true);
    assertThat(predicate.apply(coverArtImage), is(true));
  }

  @Test
  public void notBackImageReturnsFalse() {
    when(coverArtImage.isBack()).thenReturn(false);
    assertThat(predicate.apply(coverArtImage), is(false));
  }
}
