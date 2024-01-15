/* -*-             c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/

package com.mcal.xmlpull.v1.xsd.impl;

import com.mcal.xmlpull.v1.wrapper.classic.StaticXmlPullParserWrapper;
import com.mcal.xmlpull.v1.xsd.XsdException;
import com.mcal.xmlpull.v1.xsd.XsdPullParser;
import com.mcal.xmlpull.v1.xsd.impl.base64.Base64;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;

/**
 * Implementation.
 *
 * @author <a href="http://www.extreme.indiana.edu/~aslom/">Aleksander Slominski</a>
 */
public class XsdTypePullParserImpl extends StaticXmlPullParserWrapper implements XsdPullParser {
    public XsdTypePullParserImpl(XmlPullParser pp) {
        super(pp);
    }

    //---- xsd:anyURI

    public URI nextXsdAnyUriElement(String namespace, String name)
            throws XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdAnyUriElement();
    }

    public URI readXsdAnyUriAttribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdAnyUri(getAttributeValue(namespace, name));
    }

    public URI readXsdAnyUriAttribute(String name) throws XmlPullParserException, IOException {
        return parseXsdAnyUri(getAttributeValue(name));
    }

    public URI readXsdAnyUriElement() throws XmlPullParserException, IOException {
        return parseXsdAnyUri(nextText());
    }

    //---- xsd:base64Binary

    public byte[] nextXsdBase64Element(String namespace, String name)
            throws XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdBase64Element();
    }

    public byte[] readXsdBase64Attribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdBase64(getAttributeValue(namespace, name));
    }

    public byte[] readXsdBase64Attribute(String name) throws XmlPullParserException, IOException {
        return parseXsdBase64(getAttributeValue(name));
    }

    public byte[] readXsdBase64Element() throws XmlPullParserException, IOException {
        return parseXsdBase64(nextText());
    }

    //---- xsd:boolean

    public boolean nextXsdBooleanElement(String namespace, String name)
            throws XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdBooleanElement();
    }

    public boolean readXsdBooleanAttribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdBoolean(getAttributeValue(namespace, name));
    }

    public boolean readXsdBooleanAttribute(String name) throws XmlPullParserException, IOException {
        return parseXsdBoolean(getAttributeValue(name));
    }

    public boolean readXsdBooleanElement() throws XmlPullParserException, IOException {
        return parseXsdBoolean(nextText());
    }

    //---- xsd:byte

    public byte nextXsdByteElement(String namespace, String name)
            throws XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdByteElement();
    }

    public byte readXsdByteAttribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdByte(getAttributeValue(namespace, name));
    }

    public byte readXsdByteAttribute(String name) throws XmlPullParserException, IOException {
        return parseXsdByte(getAttributeValue(name));
    }

    public byte readXsdByteElement() throws XmlPullParserException, IOException {
        return parseXsdByte(nextText());
    }

    //---- xsd:decimal

    public BigDecimal nextXsdDecimalElement(String namespace, String name)
            throws XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdDecimalElement();
    }

    public BigDecimal readXsdDecimalAttribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdDecimal(getAttributeValue(namespace, name));
    }

    public BigDecimal readXsdDecimalAttribute(String name) throws XmlPullParserException, IOException {
        return parseXsdDecimal(getAttributeValue(name));
    }

    public BigDecimal readXsdDecimalElement() throws XmlPullParserException, IOException {
        return parseXsdDecimal(nextText());
    }

    //---- xsd:double

    public double nextXsdDoubleElement(String namespace, String name)
            throws XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdDoubleElement();
    }

    public double readXsdDoubleAttribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdDouble(getAttributeValue(namespace, name));
    }

    public double readXsdDoubleAttribute(String name) throws XmlPullParserException, IOException {
        return parseXsdDouble(getAttributeValue(name));
    }

    public double readXsdDoubleElement() throws XmlPullParserException, IOException {
        return parseXsdDouble(nextText());
    }


    //---- xsd:float

    public float nextXsdFloatElement(String namespace, String name) throws XsdException, XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdFloatElement();
    }

    public float readXsdFloatAttribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdFloat(getAttributeValue(namespace, name));
    }

    public float readXsdFloatAttribute(String name) throws XmlPullParserException, IOException {
        return parseXsdFloat(getAttributeValue(name));
    }

    public float readXsdFloatElement() throws XmlPullParserException, IOException {
        return parseXsdFloat(nextText());
    }


    //---- xsd:int

    public int nextXsdIntElement(String namespace, String name) throws XsdException, XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdIntElement();
    }

    public int readXsdIntAttribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdInt(getAttributeValue(namespace, name));
    }

    public int readXsdIntAttribute(String name) throws XmlPullParserException, IOException {
        return parseXsdInt(getAttributeValue(name));
    }

    public int readXsdIntElement() throws XmlPullParserException, IOException {
        return parseXsdInt(nextText());
    }


    //---- xsd:integer

    public BigInteger nextXsdIntegerElement(String namespace, String name) throws XsdException, XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdIntegerElement();
    }

    public BigInteger readXsdIntegerAttribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdInteger(getAttributeValue(namespace, name));
    }

    public BigInteger readXsdIntegerAttribute(String name) throws XmlPullParserException, IOException {
        return parseXsdInteger(getAttributeValue(name));
    }

    public BigInteger readXsdIntegerElement() throws XmlPullParserException, IOException {
        return parseXsdInteger(nextText());
    }

    //---- xsd:long

    public long nextXsdLongElement(String namespace, String name) throws XsdException, XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdLongElement();
    }

    public long readXsdLongAttribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdLong(getAttributeValue(namespace, name));
    }

    public long readXsdLongAttribute(String name) throws XmlPullParserException, IOException {
        return parseXsdLong(getAttributeValue(name));
    }

    public long readXsdLongElement() throws XmlPullParserException, IOException {
        return parseXsdLong(nextText());
    }

    //---- xsd:short

    public short nextXsdShortElement(String namespace, String name) throws XsdException, XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdShortElement();
    }

    public short readXsdShortAttribute(String namespace, String name) throws XmlPullParserException, IOException {
        return parseXsdShort(getAttributeValue(namespace, name));
    }

    public short readXsdShortAttribute(String name) throws XmlPullParserException, IOException {
        return parseXsdShort(getAttributeValue(name));
    }

    public short readXsdShortElement() throws XmlPullParserException, IOException {
        return parseXsdShort(nextText());
    }

    //---- xsd:string

    public String nextXsdStringElement(String namespace, String name) throws XsdException, XmlPullParserException, IOException {
        nextStartTag(namespace, name);
        return readXsdStringElement();
    }

    public String readXsdStringAttribute(String name)
            throws XsdException, XmlPullParserException, IOException {
        return getAttributeValue(name);
    }

    public String readXsdStringAttribute(String namespace, String name)
            throws XsdException, XmlPullParserException, IOException {
        return getAttributeValue(namespace, name);
    }

    public String readXsdStringElement() throws XmlPullParserException, IOException {
        String xsiNil = pp.getAttributeValue(XSI_NS, "nil");
        if (xsiNil != null && ("1".equals(xsiNil) || "true".equals(xsiNil))) {
            nextEndTag();
            return null;
        }
        return nextText();
    }

    //------------ XSD values PARSING -----------------------
    //TODO: should include QName?

    URI parseXsdAnyUri(String value) {
        return URI.create(value);
    }

    byte[] parseXsdBase64(String value)
            throws XsdException {
        byte[] byteArr;
        try {
            //byteArr = Base64.decode(value.toCharArray());
            byteArr = Base64.decode(value.toCharArray());
        } catch (Exception ex) {
            throw new XsdException(
                    "can't parse BAS64 value '" + value + "'" + pp.getPositionDescription(), ex);
        }
        return byteArr;
    }


    boolean parseXsdBoolean(String s) {
        // http://www.w3.org/TR/2001/REC-xmlschema-2-20010502/datatypes.html#boolean
        if ("1".equals(s)) {
            return true;
        } else if ("0".equals(s)) {
            return false;
        } else if ("true".equals(s)) {
            return true;
        } else if ("false".equals(s)) {
            return false;
        } else {
            throw new XsdException("couldnot parse boolean " + s + pp.getPositionDescription());
        }
    }

    byte parseXsdByte(String s) {
        return Byte.parseByte(s);
    }

    BigDecimal parseXsdDecimal(String s) {
        return new BigDecimal(s);
    }

    double parseXsdDouble(String value) throws XsdException {
        //return Double.parseDouble(s);
        double d;
        try {
            d = Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            if (value.equals("INF") || value.equalsIgnoreCase("infinity")) {
                d = Double.POSITIVE_INFINITY;
            } else if (value.equals("-INF")
                    || value.equalsIgnoreCase("-infinity")) {
                d = Double.NEGATIVE_INFINITY;
            } else if (value.equals("NaN")) {
                d = Double.NaN;
            } else {
                throw new XsdException(
                        "can't parse xsd:double value '" + value + "'" + pp.getPositionDescription(), ex);
            }
        }
        return d;
    }

    float parseXsdFloat(String value) throws XsdException {
        float f;
        try {
            f = Float.parseFloat(value);
        } catch (NumberFormatException ex) {
            if (value.equals("INF") || value.equalsIgnoreCase("infinity")) {
                f = Float.POSITIVE_INFINITY;
            } else if (value.equals("-INF")
                    || value.equalsIgnoreCase("-infinity")) {
                f = Float.NEGATIVE_INFINITY;
            } else if (value.equals("NaN")) {
                f = Float.NaN;
            } else {
                throw new XsdException(
                        "can't parse xsd:float value '" + value + "'" + pp.getPositionDescription(), ex);
            }
        }
        return f;
    }

    int parseXsdInt(String s) {
        return Integer.parseInt(s);
    }

    BigInteger parseXsdInteger(String s) {
        return new BigInteger(s);
    }

    long parseXsdLong(String s) {
        return Long.parseLong(s);
    }

    short parseXsdShort(String s) {
        return Short.parseShort(s);
    }

    String parseXsdString(String value) throws XmlPullParserException, IOException {
        if (value == null) {
            throw new XsdException(
                    "can not parse xsd:string when Stringis null" + pp.getPositionDescription());
        }
        return value;
    }
}
