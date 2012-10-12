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

import java.util.Set;

import com.google.common.collect.Sets;

import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtImage;

class CoverArtBeanDecorator implements CoverArt {

  private final CoverArtBean delegate;
  private final DefaultCoverArtArchiveClient client;
  private final Set<CoverArtImage> images = Sets.newHashSet();

  public CoverArtBeanDecorator(CoverArtBean delegate, DefaultCoverArtArchiveClient client) {
    this.delegate = delegate;
    this.client = client;
  }

  @Override
  public Set<CoverArtImage> getImages() {
    return getProxiedCoverArtImages();
  }

  @Override
  public String getMusicBrainzReleaseUrl() {
    return delegate.getRelease();
  }

  @Override
  public CoverArtImage getById(long id) {
    for (CoverArtImage coverArtImage : getProxiedCoverArtImages()) {
      if (coverArtImage.getId() == id) {
        return coverArtImage;
      }
    }
    return null;
  }

  @Override
  public CoverArtImage getFront() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public CoverArtImage getBack() {
    // TODO Auto-generated method stub
    return null;
  }

  private Set<CoverArtImage> getProxiedCoverArtImages() {
    if (images.isEmpty()) {
      for (CoverArtImageBean image : delegate.getImages()) {
        images.add(new ProxiedCoverArtImageBeanDecorator(image, client));
      }
    }
    return images;
  }

}
