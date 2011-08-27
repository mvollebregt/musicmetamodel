package com.github.mvollebregt.musicmetamodel.parser;

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

import com.github.mvollebregt.musicmetamodel.Artist;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Michel Vollebregt
 */
public class ArtistBuilder extends DefaultHandler implements ObjectBuilder<Artist> {

    private Artist artist = new Artist();

    @Override
    public String getElementName() {
        return "artist";
    }

    @Override
    public Artist getObject() {
        return artist;
    }

    @Override
    public void setProperty(String qname, String value) {
        if ("name".equals(qname)) {
            artist.setName(value);
        }
    }

    @Override
    public void addChild(String qname, Object object) {
        // not supported yet
    }

    @Override
    public void setSingleProperty(String value) {
        artist.setName(value);
    }

}
