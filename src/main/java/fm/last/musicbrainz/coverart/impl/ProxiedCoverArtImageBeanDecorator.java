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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import fm.last.musicbrainz.coverart.CoverArtImage;
import fm.last.musicbrainz.coverart.CoverArtType;

class ProxiedCoverArtImageBeanDecorator implements CoverArtImage {

  private final CoverArtImageBean delegate;
  private final DefaultCoverArtArchiveClient client;

  public ProxiedCoverArtImageBeanDecorator(CoverArtImageBean delegate, DefaultCoverArtArchiveClient client) {
    this.delegate = delegate;
    this.client = client;
  }

  @Override
  public long getId() {
    return delegate.getId();
  }

  @Override
  public long getEdit() {
    return delegate.getEdit();
  }

  @Override
  public Set<CoverArtType> getTypes() {
    List<String> types = delegate.getTypes();
    return Sets.newHashSet(Lists.transform(types, CoverArtTypeStringToEnumValue.INSTANCE));
  }

  @Override
  public InputStream getImage() throws IOException {
    return client.getImageData(delegate.getImage());
  }

  @Override
  public String getImageUrl() {
    return delegate.getImage();
  }

  @Override
  public boolean isFront() {
    return delegate.isFront();
  }

  @Override
  public boolean isBack() {
    return delegate.isBack();
  }

  @Override
  public String getComment() {
    return delegate.getComment();
  }

  @Override
  public boolean isApproved() {
    return delegate.isApproved();
  }

  @Override
  public InputStream getLargeThumbnail() throws IOException {
    return client.getImageData(delegate.getThumbnails().getLarge());
  }

  @Override
  public String getLargeThumbnailUrl() {
    return delegate.getThumbnails().getLarge();
  }

  @Override
  public InputStream getSmallThumbnail() throws IOException {
    return client.getImageData(delegate.getThumbnails().getSmall());
  }

  @Override
  public String getSmallThumbnailUrl() {
    return delegate.getThumbnails().getSmall();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ProxiedCoverArtImage [id=");
    builder.append(getId());
    builder.append(", edit=");
    builder.append(getEdit());
    builder.append(", types=");
    builder.append(getTypes());
    builder.append(", front=");
    builder.append(isFront());
    builder.append(", back=");
    builder.append(isBack());
    builder.append(", comment=");
    builder.append(getComment());
    builder.append(", approved=");
    builder.append(isApproved());
    builder.append(", image=");
    builder.append(delegate.getImage());
    builder.append("]");
    return builder.toString();
  }

}
