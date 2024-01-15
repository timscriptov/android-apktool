/* -*-             c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/
// for license please see accompanying LICENSE.txt file (available also at http://www.xmlpull.org/)


package com.mcal.xmlpull.v1.xmlrpc;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Stefan Haustein
 */

public class XmlRpcParserME {

    XmlPullParser parser;

    /**
     * Creates a new XmlRpcParser, using the given XmlPullParser.
     */

    public XmlRpcParserME(XmlPullParser parser) {
        this.parser = parser;
    }

    /**
     * Parses an XML RPC method call response.
     * The return values are collected in a Vector.
     *
     * @return The return values collected in a Vector.
     */

    public Vector<Object> parseResponse() throws XmlPullParserException, IOException {

        Vector<Object> result = new Vector<>();

        parser.nextTag();
        parser.require(XmlPullParser.START_TAG, "", "methodResponse");
        parser.nextTag();
        parser.require(XmlPullParser.START_TAG, "", "params");

        while (parser.nextTag() == XmlPullParser.START_TAG) {
            parser.require(XmlPullParser.START_TAG, "", "param");
            parser.nextTag();
            result.addElement(parseValue());
            parser.nextTag();
            parser.require(XmlPullParser.END_TAG, "", "param");
        }

        parser.require(XmlPullParser.END_TAG, "", "params");
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, "", "methodResponse");
        parser.next();
        parser.require(XmlPullParser.END_DOCUMENT, null, null);

        return result;
    }

    protected Object parseType(@NotNull String name) throws IOException, XmlPullParserException {
        //	System.out.println("type:"+name);
        return switch (name) {
            case "int", "i4" -> Integer.parseInt(parser.nextText());
            case "array" -> parseArray();
            case "struct" -> parseStruct();
            default -> parser.nextText();
        };
    }

    /**
     * Parses an XML-RPC value element. Returns the
     * content of the element as a corresponding Java object.
     * <p>
     * <b>precondition:</b> parser is on a "value" start tag<br />
     * <b>postcondition:</b> parser is on a "value" end tag</p>
     */

    Object parseValue() throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, "", "value"); // precondition
        parser.next();

        Object result = null;

        if (parser.getEventType() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }


        if (parser.getEventType() == XmlPullParser.START_TAG) {

            if (result != null && !((String) result).trim().isEmpty())
                throw new RuntimeException("illegal mixed content!");

            String name = parser.getName();
            result = parseType(name);

            parser.require(XmlPullParser.END_TAG, "", name);
            parser.nextTag();
        }

        parser.require(XmlPullParser.END_TAG, "", "value"); // postcond.
        return result;
    }

    /**
     * Parses an XML-RPC array and returns it as a Java Vector
     *
     * <p>
     * <b>Precondition:</b> On "array" start tag<br />
     * <b>Postcondition:</b> On "array" end tag
     * </p>
     */

    Vector<Object> parseArray() throws IOException, XmlPullParserException {
        Vector<Object> v = new Vector<>();

        parser.require(XmlPullParser.START_TAG, "", "array");
        parser.nextTag();
        parser.require(XmlPullParser.START_TAG, "", "data");

        while (parser.nextTag() == XmlPullParser.START_TAG) {
            v.addElement(parseValue());
        }

        parser.require(XmlPullParser.END_TAG, "", "data");
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, "", "array");

        return v;
    }

    Hashtable<String, Object> parseStruct() throws IOException, XmlPullParserException {
        Hashtable<String, Object> struct = new Hashtable<>();
        parser.require(XmlPullParser.START_TAG, "", "struct");
        while (parser.nextTag() == XmlPullParser.START_TAG) {
            parser.require(XmlPullParser.START_TAG, "", "member");
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, "", "name");
            String name = parser.nextText();
            parser.require(XmlPullParser.END_TAG, "", "name");
            parser.nextTag();
            struct.put(name, parseValue());
            parser.nextTag();
            parser.require(XmlPullParser.END_TAG, "", "member");
        }
        parser.require(XmlPullParser.END_TAG, "", "struct");
        return struct;
    }

    /*main method, temporarily included for simple testing only 
    
    
    public static void main(String[] argv)
        throws IOException, XmlPullParserException {
    
        String test =
            "<?xml version=\"1.0\"?>\n"
                + "<methodResponse><params>\n"
                + " <param>\n"
                + "  <value><string>South Dakota</string></value>\n"
                + " </param><param>\n"
                + "  <value><struct>\n"
                + "   <member><name>foo</name><value>bar</value></member>\n"
                + "   <member><name>v</name><value><array></array></value></member>\n"
                + "  </struct></value>\n"
                + " </param><param>\n"
                + "   <value><double>3.14</double></value>\n"
                + " </param>\n"
                + "</params></methodResponse>\n";
    
        System.out.println("test input:\n" + test);
        XmlPullParser xp = new org.kxml2.io.KXmlParser();
        xp.setInput(new java.io.StringReader(test));
        System.out.println(
            "parsing result: " + new XmlRpcParser(xp).parseResponse());
    
    }
    */
}
