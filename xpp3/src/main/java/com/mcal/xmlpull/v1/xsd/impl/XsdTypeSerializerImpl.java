/* -*-             c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/
// for license please see accompanying LICENSE.txt file (available also at http://www.xmlpull.org/)

package com.mcal.xmlpull.v1.xsd.impl;

import org.xmlpull.v1.XmlSerializer;
import com.mcal.xmlpull.v1.wrapper.XmlPullWrapperFactory;
import com.mcal.xmlpull.v1.wrapper.classic.StaticXmlSerializerWrapper;
import com.mcal.xmlpull.v1.xsd.XsdException;
import com.mcal.xmlpull.v1.xsd.XsdSerializer;
import com.mcal.xmlpull.v1.xsd.impl.base64.Base64;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;

//TODO: add QName

/**
 * Implementation.
 *
 * @author <a href="http://www.extreme.indiana.edu/~aslom/">Aleksander Slominski</a>
 */
public class XsdTypeSerializerImpl extends StaticXmlSerializerWrapper implements XsdSerializer {

    public XsdTypeSerializerImpl(XmlSerializer xs, XmlPullWrapperFactory wf) {
        super(xs, wf);
    }

    // ----- xsd:anyURI

    public void writeXsdAnyUri(URI value) throws XsdException, IOException {
        xs.text(serializeXsdAnyUri(value));
    }

    public void writeXsdAnyUriAttribute(String name, URI value) throws XsdException, IOException {
        writeXsdAnyUriAttribute(null, name, value);
    }

    public void writeXsdAnyUriAttribute(String namespace, String name, URI value) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdAnyUri(value));
    }

    public void writeXsdAnyUriElement(String namespace, String name, URI value) throws XsdException, IOException {
        writeXsdAnyUriElement(namespace, name, value, false);
    }

    public void writeXsdAnyUriElement(String namespace, String name, URI value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("anyURI");
        writeXsdAnyUri(value);
        xs.endTag(namespace, name);
    }

    //---- xsd:base64Binary

    public void writeXsdBase64(byte[] value) throws XsdException, IOException {
        xs.text(serializeXsdBase64(value));
    }

    public void writeXsdBase64Attribute(String name, byte[] value) throws XsdException, IOException {
        writeXsdBase64Attribute(null, name, value);
    }

    public void writeXsdBase64Attribute(String namespace, String name, byte[] value) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdBase64(value));
    }

    public void writeXsdBase64Element(String namespace, String name, byte[] value) throws XsdException, IOException {
        writeXsdBase64Element(namespace, name, value, false);
    }

    public void writeXsdBase64Element(String namespace, String name, byte[] value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("base64Binary");
        writeXsdBase64(value);
        xs.endTag(namespace, name);
    }

    //---- xsd:boolean

    public void writeXsdBoolean(boolean value) throws XsdException, IOException {
        xs.text(serializeXsdBoolean(value));
    }

    public void writeXsdBooleanAttribute(String name, boolean value) throws XsdException, IOException {
        writeXsdBooleanAttribute(null, name, value);
    }

    public void writeXsdBooleanAttribute(String namespace, String name, boolean value) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdBoolean(value));
    }

    public void writeXsdBooleanElement(String namespace, String name, boolean value) throws XsdException, IOException {
        writeXsdBooleanElement(namespace, name, value, false);
    }

    public void writeXsdBooleanElement(String namespace, String name, boolean value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("boolean");
        writeXsdBoolean(value);
        xs.endTag(namespace, name);
    }

    //---- xsd:byte

    public void writeXsdByte(byte value) throws XsdException, IOException {
        xs.text(serializeXsdByte(value));
    }

    public void writeXsdByteAttribute(String name, byte value) throws XsdException, IOException {
        writeXsdByteAttribute(null, name, value);
    }

    public void writeXsdByteAttribute(String namespace, String name, byte value) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdByte(value));
    }

    public void writeXsdByteElement(String namespace, String name, byte value) throws XsdException, IOException {
        writeXsdByteElement(namespace, name, value, false);
    }

    public void writeXsdByteElement(String namespace, String name, byte value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("byte");
        writeXsdByte(value);
        xs.endTag(namespace, name);
    }


    //---- xsd:decimal

    public void writeXsdDecimal(BigDecimal value) throws XsdException, IOException {
        xs.text(serializeXsdDecimal(value));
    }

    public void writeXsdDecimalAttribute(String name, BigDecimal value) throws XsdException, IOException {
        writeXsdDecimalAttribute(null, name, value);
    }

    public void writeXsdDecimalAttribute(String namespace, String name, BigDecimal value) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdDecimal(value));
    }

    public void writeXsdDecimalElement(String namespace, String name, BigDecimal value) throws XsdException, IOException {
        writeXsdDecimalElement(namespace, name, value, false);
    }

    public void writeXsdDecimalElement(String namespace, String name, BigDecimal value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("BigDecimal");
        writeXsdDecimal(value);
        xs.endTag(namespace, name);
    }

    // ----- xsd:double

    public void writeXsdDouble(double d) throws XsdException, IOException {
        xs.text(serializeXsdDouble(d));
    }

    public void writeXsdDoubleAttribute(String name, double d) throws XsdException, IOException {
        writeXsdDoubleAttribute(null, name, d);
    }

    public void writeXsdDoubleAttribute(String namespace, String name, double d) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdDouble(d));
    }

    public void writeXsdDoubleElement(String namespace, String name, double d) throws XsdException, IOException {
        writeXsdDoubleElement(namespace, name, d, false);
    }

    public void writeXsdDoubleElement(String namespace, String name, double d, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("double");
        writeXsdDouble(d);
        xs.endTag(namespace, name);
    }


    // ----- xsd:float

    public void writeXsdFloat(float value) throws XsdException, IOException {
        xs.text(serializeXsdFloat(value));
    }

    public void writeXsdFloatAttribute(String name, float value) throws XsdException, IOException {
        writeXsdFloatAttribute(null, name, value);
    }

    public void writeXsdFloatAttribute(String namespace, String name, float value) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdFloat(value));
    }

    public void writeXsdFloatElement(String namespace, String name, float value) throws XsdException, IOException {
        writeXsdFloatElement(namespace, name, value, false);
    }

    public void writeXsdFloatElement(String namespace, String name, float value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("float");
        writeXsdFloat(value);
        xs.endTag(namespace, name);
    }

    // ----- xsd:int

    public void writeXsdInt(int value) throws XsdException, IOException {
        xs.text(serializeXsdInt(value));
    }

    public void writeXsdIntAttribute(String name, int value) throws XsdException, IOException {
        writeXsdIntAttribute(null, name, value);
    }

    public void writeXsdIntAttribute(String namespace, String name, int value) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdInt(value));
    }

    public void writeXsdIntElement(String namespace, String name, int value) throws XsdException, IOException {
        writeXsdIntElement(namespace, name, value, false);
    }

    public void writeXsdIntElement(String namespace, String name, int value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("int");
        writeXsdInt(value);
        xs.endTag(namespace, name);
    }

    //---- xsd:integer

    public void writeXsdBigIntegereger(BigInteger value) throws XsdException, IOException {
        xs.text(serializeXsdInteger(value));
    }

    public void writeXsdBigIntegeregerAttribute(String name, BigInteger value) throws XsdException, IOException {
        writeXsdBigIntegeregerAttribute(null, name, value);
    }

    public void writeXsdBigIntegeregerAttribute(String namespace, String name, BigInteger value) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdInteger(value));
    }

    public void writeXsdBigIntegeregerElement(String namespace, String name, BigInteger value) throws XsdException, IOException {
        writeXsdBigIntegeregerElement(namespace, name, value, false);
    }

    public void writeXsdBigIntegeregerElement(String namespace, String name, BigInteger value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("integer");
        writeXsdBigIntegereger(value);
        xs.endTag(namespace, name);
    }

    //---- xsd:long

    public void writeXsdLong(int value) throws XsdException, IOException {
        xs.text(serializeXsdLong(value));
    }

    public void writeXsdLongAttribute(String name, int value) throws XsdException, IOException {
        writeXsdLongAttribute(null, name, value);
    }

    public void writeXsdLongAttribute(String namespace, String name, int value) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdLong(value));
    }

    public void writeXsdLongElement(String namespace, String name, int value) throws XsdException, IOException {
        writeXsdLongElement(namespace, name, value, false);
    }

    public void writeXsdLongElement(String namespace, String name, int value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("long");
        writeXsdLong(value);
        xs.endTag(namespace, name);
    }

    //---- xsd:short

    public void writeXsdShort(short value) throws XsdException, IOException {
        xs.text(serializeXsdShort(value));
    }

    public void writeXsdShortAttribute(String name, short value) throws XsdException, IOException {
        writeXsdShortAttribute(null, name, value);
    }

    public void writeXsdShortAttribute(String namespace, String name, short value) throws XsdException, IOException {
        xs.attribute(namespace, name, serializeXsdShort(value));
    }

    public void writeXsdShortElement(String namespace, String name, short value) throws XsdException, IOException {
        writeXsdShortElement(namespace, name, value, false);
    }

    public void writeXsdShortElement(String namespace, String name, short value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("short");
        writeXsdShort(value);
        xs.endTag(namespace, name);
    }

    // ----- xsd:string

    public void writeXsdString(String value) throws XsdException, IOException {
        if (value == null) {
            xs.attribute(XSI_NS, "nil", "1");
        } else {
            xs.text(value);
        }
    }

    public void writeXsdStringAttribute(String name, String value) throws XsdException, IOException {
        writeXsdStringAttribute(null, name, value);
    }

    public void writeXsdStringAttribute(String namespace, String name, String value) throws XsdException, IOException {
        xs.attribute(namespace, name, value);
    }

    public void writeXsdStringElement(String namespace, String name, String value) throws XsdException, IOException {
        writeXsdStringElement(namespace, name, value, false);
    }

    public void writeXsdStringElement(String namespace, String name, String value, boolean addXsiType) throws XsdException, IOException {
        xs.startTag(namespace, name);
        if (addXsiType) serializeXsiType("string");
        writeXsdString(value);
        xs.endTag(namespace, name);
    }

    // --------------- utility methods

    String serializeXsdAnyUri(URI value) {
        return value.toASCIIString();
    }

    String serializeXsdBase64(byte[] value)
            throws XsdException {
        try {
            //byteArr = Base64.decode(value.toCharArray());
            char[] carr = Base64.encode(value);
            return new String(carr);
        } catch (Exception ex) {
            throw new XsdException(
                    "can't serialize BAS64 value '" + value + "'", ex);
        }
    }

    // http://www.w3.org/TR/2001/REC-xmlschema-2-20010502/datatypes.html#boolean
    String serializeXsdBoolean(boolean s) {
        return s ? "1" : "0";
    }

    String serializeXsdByte(byte s) {
        return Byte.toString(s);
    }

    String serializeXsdDecimal(BigDecimal s) {
        return s.toString();
    }

    String serializeXsdDouble(double value) throws XsdException {
        return Double.toString(value); //TODO check for INF and NaN representation
    }

    String serializeXsdFloat(float value) throws XsdException {
        return Float.toString(value); //TODO check for INF and NaN representation
    }

    String serializeXsdInt(int s) {
        return Integer.toString(s);
    }

    String serializeXsdInteger(BigInteger s) {
        return s.toString();
    }

    String serializeXsdLong(long s) {
        return Long.toString(s);
    }

    String serializeXsdShort(short s) {
        return Short.toString(s);
    }

    //    String serializeXsdString(String value)
    //        throws IOException, XsdException
    //    {
    //        return value;
    //    }

    void serializeXsiType(String xsdTypeName) throws IOException {
        String prefix = xs.getPrefix(XSD_NS, true);
        xs.attribute(XSI_NS, "type", prefix + ":" + xsdTypeName);
    }

}

