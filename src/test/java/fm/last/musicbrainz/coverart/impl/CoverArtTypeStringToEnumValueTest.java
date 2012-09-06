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
import static org.junit.Assert.assertThat;

import org.junit.Test;

import fm.last.musicbrainz.coverart.CoverArtType;

public class CoverArtTypeStringToEnumValueTest {

  private static final CoverArtTypeStringToEnumValue function = CoverArtTypeStringToEnumValue.INSTANCE;

  @Test
  public void casingOfStringIsIrrelevant() {
    assertThat(function.apply("front"), is(CoverArtType.FRONT));
    assertThat(function.apply("BACK"), is(CoverArtType.BACK));
    assertThat(function.apply("mEdIUm"), is(CoverArtType.MEDIUM));
  }

}
