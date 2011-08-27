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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michel Vollebregt
 */
public class ListBuilder implements ObjectBuilder<List> {

    private List<Object> list = new ArrayList<Object>();
    private String elementName;

    public ListBuilder(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public String getElementName() {
        return elementName;
    }

    @Override
    public List getObject() {
        return list;
    }

    @Override
    public void setProperty(String qname, String value) {
        // not supported
    }

    @Override
    public void addChild(String qname, Object object) {
        list.add(object);
    }

    @Override
    public void setSingleProperty(String value) {
        // not supported
    }
}
