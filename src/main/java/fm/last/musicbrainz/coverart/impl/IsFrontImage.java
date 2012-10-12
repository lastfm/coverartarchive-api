package fm.last.musicbrainz.coverart.impl;

import com.google.common.base.Predicate;

import fm.last.musicbrainz.coverart.CoverArtImage;

enum IsFrontImage implements Predicate<CoverArtImage> {
  /* */
  INSTANCE;

  @Override
  public boolean apply(CoverArtImage coverArtImage) {
    return coverArtImage.isFront();
  }

}
