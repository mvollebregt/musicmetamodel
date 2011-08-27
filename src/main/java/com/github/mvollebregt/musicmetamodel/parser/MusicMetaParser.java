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

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Michel Vollebregt
 */
public class MusicMetaParser {

    public <T> T parse(InputStream inputStream) throws SAXException, IOException {
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        MusicMetaHandler<T> handler = new MusicMetaHandler<T>();
	    xmlReader.setContentHandler(handler);
	    xmlReader.setErrorHandler(handler);
        xmlReader.parse(new InputSource(inputStream));
        return handler.getObjectTree();
    }
}
