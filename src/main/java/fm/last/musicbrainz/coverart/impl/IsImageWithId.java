package fm.last.musicbrainz.coverart.impl;

import com.google.common.base.Predicate;

import fm.last.musicbrainz.coverart.CoverArtImage;

class IsImageWithId implements Predicate<CoverArtImage> {

  private final long id;

  IsImageWithId(long id) {
    this.id = id;
  }

  @Override
  public boolean apply(CoverArtImage coverArtImage) {
    return coverArtImage.getId() == id;
  }
}
