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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

/**
 * @author Michel Vollebregt
 */
public class MusicMetaHandler<T> extends DefaultHandler {

    private T objectTree;
    private Stack<ObjectBuilder> objectBuilderStack = new Stack<ObjectBuilder>();
    private StringBuilder characterBuffer;
    private boolean currentElementHasChildren;

    public T getObjectTree() {
        return objectTree;
    }

    @Override
    public void startElement(String uri, String name, String qname, Attributes attributes) throws SAXException {
        if ("artist".equals(qname)) {
            pushOnStack(new ArtistBuilder(), attributes);
        } else if ("album".equals(qname)) {
            pushOnStack(new AlbumBuilder(), attributes);
        } else if (objectBuilderStack.empty()) {
            pushOnStack(new ListBuilder(qname), attributes);
        }
        characterBuffer = new StringBuilder();
    }
    
    @SuppressWarnings({"unchecked"})
    @Override
    public void endElement(String uri, String name, String qname) throws SAXException {
        ObjectBuilder currentBuilder = objectBuilderStack.peek();
        if (characterBuffer != null)
        if (currentBuilder.getElementName().equals(qname)) {
            if (!currentElementHasChildren) currentBuilder.setSingleProperty(characterBuffer.toString());
            Object result = currentBuilder.getObject();
            objectBuilderStack.pop();
            if (!objectBuilderStack.empty()) {
                objectBuilderStack.peek().addChild(qname, result);
            } else {
                objectTree = (T) result;
            }
        } else {
            currentBuilder.setProperty(qname, characterBuffer.toString());
            currentElementHasChildren = true;
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        characterBuffer.append(ch, start, length);
    }

    private void pushOnStack(ObjectBuilder handler, Attributes attributes) {
        objectBuilderStack.push(handler);
        for (int i = 0; i < attributes.getLength(); i++) {
            handler.setProperty(attributes.getQName(i), attributes.getValue(i));
        }
        currentElementHasChildren = false;
    }
}
