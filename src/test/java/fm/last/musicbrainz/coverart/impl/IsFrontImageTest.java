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
public class IsFrontImageTest {

  private final Predicate<CoverArtImage> predicate = IsFrontImage.INSTANCE;
  @Mock
  private CoverArtImage coverArtImage;

  @Test
  public void frontImageReturnsTrue() {
    when(coverArtImage.isFront()).thenReturn(true);
    assertThat(predicate.apply(coverArtImage), is(true));
  }

  @Test
  public void notFrontImageReturnsFalse() {
    when(coverArtImage.isFront()).thenReturn(false);
    assertThat(predicate.apply(coverArtImage), is(false));
  }
}
