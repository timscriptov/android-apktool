/* -*-             c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/
// for license please see accompanying LICENSE.txt file (available also at http://www.xmlpull.org/)

package com.mcal.xmlpull.v1.xmlrpc;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * @author Stefan Haustein
 *
 * <p>A simple XML RPC parser based on the XML PULL API,
 * intended to show the XMLPULL and KXml2 API usage with
 * a real application example.</p>
 *
 * <ul>
 * <li>For the XML RPC specification, please refer to
 * <a href="http://www.xmlrpc.com/spec">http://www.xmlrpc.com/spec</a></li>
 * <li>For the XmlPullParser API specification, please refer to
 * <a href="http://xmlpull.org/">xmlpull.org</a></li>
 * <li>For information about kXML 2, please refer to
 * <a href="http://kxml.org/">kxml.org</a></li>
 * </ul>
 */

public class XmlRpcParser extends XmlRpcParserME {

    public XmlRpcParser(XmlPullParser parser) {
        super(parser);
    }

    protected Object parseType(String name) throws IOException, XmlPullParserException {
        if (name.equals("double"))
            return new Double(parser.nextText());
        else
            return super.parseType(name);
    }


}