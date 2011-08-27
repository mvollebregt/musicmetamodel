package com.github.mvollebregt.musicmetamodel.parser

import com.github.mvollebregt.musicmetamodel.Album
import com.github.mvollebregt.musicmetamodel.Artist
import spock.lang.Specification

// This file is part of SpotifyDiscoverer.
//
// SpotifyDiscoverer is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// SpotifyDiscoverer is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with SpotifyDiscoverer.  If not, see <http://www.gnu.org/licenses/>.

/**
 *
 *
 * @author Michel Vollebregt
 */
class LastFMParserSpec extends Specification {

    def parser = new com.github.mvollebregt.musicmetamodel.parser.MusicMetaParser();

    def "parse one artist should return one Artist"() {
        given:
            def xml = """<artist rank="1">
                            <name>Dream Theater</name>
                            <playcount>1337</playcount>
                            <mbid>28503ab7-8bf2-4666-a7bd-2644bfc7cb1d</mbid>
                            <url>http://www.last.fm/music/Dream+Theater</url>
                            <streamable>1</streamable>
                            <image size="small">...</image>
                            <image size="medium">...</image>
                            <image size="large">...</image>
                        </artist>"""
        when:
            def artist = parser.parse(stream(xml));
        then:
            assert match(new Artist(name: "Dream Theater"), artist)
    }

    def "parse two artists should return a list of two Artists"() {
        given:
            def xml = """<root><artist><name>First Artist</name></artist>
                     <artist><name>Second Artist</name></artist></root>"""
        when:
            def list = parser.parse(stream(xml));
        then:
            assert match([new Artist(name: "First Artist"), new Artist(name: "Second Artist")], list)
    }

    def "parse an album should return an album"() {
        given:
            def xml = """<album>
                          <name>Believe</name>
                          <id>2026126</id>
                        </album>"""
        when:
            def album = parser.parse(stream(xml));
        then:
            assert match(new Album(name: "Believe"), album)
    }

    def "nested elements should return an object tree"() {
        given:
            def xml = """<album>
                          <name>Believe</name>
                          <artist>Cher</artist>
                          <id>2026126</id>
                        </album>"""
        when:
            def album = parser.parse(stream(xml));
        then:
            assert match(new Album(name: "Believe", artist: new Artist(name:"Cher")), album)
    }

    def "parse a null value should not throw exception"() {

        given:
            def xml = """<artist>
                            <mbid></mbid>
                         </artist>"""
        when:
            parser.parse(stream(xml));
        then:
            notThrown(NullPointerException)
    }

    private static match(Collection expectedList, Collection observedList) {
        assert expectedList.size() == observedList.size()
        for (int i = 0; i < expectedList.size(); i++) {
            assert match(expectedList[i], observedList[i])
        }
        return true
    }

    private static match(Artist expected, Artist observed) {
        matchProperties(expected, observed)
    }

    private static match(Album expected, Album observed) {
        matchProperties(expected, observed)
    }

    private static match(expected, observed) {
        assert expected == observed
        return true
    }

    private static matchProperties(expected, observed) {
        assert expected.class == observed.class
        expected.properties.each {property, value ->
            assert match(value, observed[property])
        }
        return true
    }

    private static stream(text) {
        new ByteArrayInputStream(text.getBytes("UTF-8"))
    }


}
