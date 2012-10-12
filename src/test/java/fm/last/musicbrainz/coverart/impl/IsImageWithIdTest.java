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
public class IsImageWithIdTest {

  private static final long ID = 123;

  private final Predicate<CoverArtImage> predicate = new IsImageWithId(ID);
  @Mock
  private CoverArtImage coverArtImage;

  @Test
  public void imageWithCorrectIdReturnsTrue() {
    when(coverArtImage.getId()).thenReturn(ID);
    assertThat(predicate.apply(coverArtImage), is(true));
  }

  @Test
  public void imageWithDifferentIdReturnsFalse() {
    when(coverArtImage.getId()).thenReturn(3L);
    assertThat(predicate.apply(coverArtImage), is(false));
  }
}
