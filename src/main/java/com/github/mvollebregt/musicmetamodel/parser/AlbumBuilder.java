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

import com.github.mvollebregt.musicmetamodel.Album;
import com.github.mvollebregt.musicmetamodel.Artist;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Michel Vollebregt
 */
public class AlbumBuilder extends DefaultHandler implements ObjectBuilder<Album> {

    private Album album = new Album();

    @Override
    public String getElementName() {
        return "album";
    }

    @Override
    public Album getObject() {
        return album;
    }

    @Override
    public void setProperty(String qname, String value) {
        if ("href".equals(qname)) {
            album.setId(value);
        } else if ("name".equals(qname)) {
            album.setName(value);
        }
    }

    @Override
    public void addChild(String qname, Object object) {
        if ("artist".equals(qname)) {
            album.setArtist((Artist) object);
        }
    }

    @Override
    public void setSingleProperty(String value) {
        album.setName(value);
    }
}
