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

import java.util.List;

class CoverArtImageBean {

  static class CoverArtImageThumbnailsBean {
    private String large;
    private String small;

    public String getLarge() {
      return large;
    }

    public void setLarge(String large) {
      this.large = large;
    }

    public String getSmall() {
      return small;
    }

    public void setSmall(String small) {
      this.small = small;
    }
  }

  private CoverArtImageThumbnailsBean thumbnails;
  private long id;
  private long edit;
  private List<String> types;
  private String image;
  private String comment;
  private boolean approved;
  private boolean front;
  private boolean back;

  public boolean isFront() {
    return front;
  }

  public void setFront(boolean front) {
    this.front = front;
  }

  public boolean isBack() {
    return back;
  }

  public void setBack(boolean back) {
    this.back = back;
  }

  public List<String> getTypes() {
    return types;
  }

  public CoverArtImageThumbnailsBean getThumbnails() {
    return thumbnails;
  }

  public void setThumbnails(CoverArtImageThumbnailsBean thumbnails) {
    this.thumbnails = thumbnails;
  }

  public void setTypes(List<String> types) {
    this.types = types;
  }

  public long getEdit() {
    return edit;
  }

  public void setEdit(long edit) {
    this.edit = edit;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public boolean isApproved() {
    return approved;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
