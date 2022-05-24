# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [2.2.0] - TBD
### Changed
- Upgraded `jackson` to `2.13.3` (was `2.0.5`).
- Upgraded `slf4j` to `1.7.36` (was `1.6.6`).
- Upgraded `guava` to `19.0` (was `12.0`).
- Upgraded `httpclient` to `4.5.13` (was `4.2.1`).

## [2.1.1] - 2018-04-26
### Added
- Add 3 missing cover art types - `poster`, `watermark` and `liner`.

## [2.1.0] - 2014-08-20
### Added
- Extended API to provide a methods to find cover art for release group.
- Implemented optional usage of HTTPS.
- Increased compatibility with android.

- Migrated project to lastfm-oss-parent.

## [2.0.0] - 2012-10-12
### Added
- Added new methods to CoverArt (getImageById, getFrontImage and getBackImage).
### Changed
- Changed return type of CoverArt#getImages to List as images in JSON response from CoverArtArchive are ordered.

## [1.0.0] - 2012-09-06
### Changed
- Initial release. Provides access to cover art through the /release/{mbid}/ end point. 
