/* -*-             c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/
// for license please see accompanying LICENSE.txt file (available also at http://www.xmlpull.org/)

package com.mcal.xmlpull.v1.xsd;

import com.mcal.xmlpull.v1.wrapper.XmlSerializerWrapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;

/**
 * Set of method to write XSD types into XML stream.
 *
 * @author <a href="http://www.extreme.indiana.edu/~aslom/">Aleksander Slominski</a>
 */
public interface XsdSerializer extends XmlSerializerWrapper {

    /**
     * Write lexical representation for XSD type value as serialized text.
     */
    public void writeXsdAnyUri(URI value) throws XsdException, IOException;

    /**
     * Calls writeXsdAnyUriAttribute(null, name, value) method
     */
    public void writeXsdAnyUriAttribute(String name, URI value) throws XsdException, IOException;

    /**
     * Write as attribute with namespace and name and XSD value inside
     * (looks like ns:name='value' where ns is prefix
     * for namespace automatically declared if needed).
     */
    public void writeXsdAnyUriAttribute(String namespace, String name, URI value) throws XsdException, IOException;

    /**
     * Calls writeXsdAnyUriElement(namespace, name, value, false) method
     */
    public void writeXsdAnyUriElement(String namespace, String name, URI value) throws XsdException, IOException;

    /**
     * Write as element with namespace and name and XSD value inside
     * (looks like &lt;ns:name>value&lt;/ns:name> where ns is prefix
     * for namespace automatically declared if needed).
     * If addXsiType is true then xsi:type="..." will be writtten as well.
     */
    public void writeXsdAnyUriElement(String namespace, String name, URI value, boolean addXsiType) throws XsdException, IOException;

    //---- xsd:base64Binary

    public void writeXsdBase64(byte[] value) throws XsdException, IOException;

    public void writeXsdBase64Attribute(String name, byte[] value) throws XsdException, IOException;

    public void writeXsdBase64Attribute(String namespace, String name, byte[] value) throws XsdException, IOException;

    public void writeXsdBase64Element(String namespace, String name, byte[] value) throws XsdException, IOException;

    public void writeXsdBase64Element(String namespace, String name, byte[] value, boolean addXsiType) throws XsdException, IOException;

    //---- xsd:boolean

    public void writeXsdBoolean(boolean value) throws XsdException, IOException;

    public void writeXsdBooleanAttribute(String name, boolean value) throws XsdException, IOException;

    public void writeXsdBooleanAttribute(String namespace, String name, boolean value) throws XsdException, IOException;

    public void writeXsdBooleanElement(String namespace, String name, boolean value) throws XsdException, IOException;

    public void writeXsdBooleanElement(String namespace, String name, boolean value, boolean addXsiType) throws XsdException, IOException;

    //---- xsd:byte

    public void writeXsdByte(byte value) throws XsdException, IOException;

    public void writeXsdByteAttribute(String name, byte value) throws XsdException, IOException;

    public void writeXsdByteAttribute(String namespace, String name, byte value) throws XsdException, IOException;

    public void writeXsdByteElement(String namespace, String name, byte value) throws XsdException, IOException;

    public void writeXsdByteElement(String namespace, String name, byte value, boolean addXsiType) throws XsdException, IOException;

    //---- xsd:decimal

    public void writeXsdDecimal(BigDecimal value) throws XsdException, IOException;

    public void writeXsdDecimalAttribute(String name, BigDecimal value) throws XsdException, IOException;

    public void writeXsdDecimalAttribute(String namespace, String name, BigDecimal value) throws XsdException, IOException;

    public void writeXsdDecimalElement(String namespace, String name, BigDecimal value) throws XsdException, IOException;

    public void writeXsdDecimalElement(String namespace, String name, BigDecimal value, boolean addXsiType) throws XsdException, IOException;

    //---- xsd:double

    public void writeXsdDouble(double d)
            throws XsdException, IOException;

    public void writeXsdDoubleElement(String namespace, String name, double d)
            throws XsdException, IOException;

    public void writeXsdDoubleElement(String namespace, String name, double d, boolean addXsiType)
            throws XsdException, IOException;

    public void writeXsdDoubleAttribute(String name, double d)
            throws XsdException, IOException;

    public void writeXsdDoubleAttribute(String namespace, String name, double d)
            throws XsdException, IOException;

    // ----- xsd:float

    public void writeXsdFloat(float value) throws XsdException, IOException;

    public void writeXsdFloatAttribute(String name, float value) throws XsdException, IOException;

    public void writeXsdFloatAttribute(String namespace, String name, float value) throws XsdException, IOException;

    public void writeXsdFloatElement(String namespace, String name, float value) throws XsdException, IOException;

    public void writeXsdFloatElement(String namespace, String name, float value, boolean addXsiType) throws XsdException, IOException;

    // ----- xsd:int

    public void writeXsdInt(int value) throws XsdException, IOException;

    public void writeXsdIntAttribute(String name, int value) throws XsdException, IOException;

    public void writeXsdIntAttribute(String namespace, String name, int value) throws XsdException, IOException;

    public void writeXsdIntElement(String namespace, String name, int value) throws XsdException, IOException;

    public void writeXsdIntElement(String namespace, String name, int value, boolean addXsiType) throws XsdException, IOException;

    //---- xsd:integer

    public void writeXsdBigIntegereger(BigInteger value) throws XsdException, IOException;

    public void writeXsdBigIntegeregerAttribute(String name, BigInteger value) throws XsdException, IOException;

    public void writeXsdBigIntegeregerAttribute(String namespace, String name, BigInteger value) throws XsdException, IOException;

    public void writeXsdBigIntegeregerElement(String namespace, String name, BigInteger value) throws XsdException, IOException;

    public void writeXsdBigIntegeregerElement(String namespace, String name, BigInteger value, boolean addXsiType) throws XsdException, IOException;

    //---- xsd:long

    public void writeXsdLong(int value) throws XsdException, IOException;

    public void writeXsdLongAttribute(String name, int value) throws XsdException, IOException;

    public void writeXsdLongAttribute(String namespace, String name, int value) throws XsdException, IOException;

    public void writeXsdLongElement(String namespace, String name, int value) throws XsdException, IOException;

    public void writeXsdLongElement(String namespace, String name, int value, boolean addXsiType) throws XsdException, IOException;

    //---- xsd:short

    public void writeXsdShort(short value) throws XsdException, IOException;

    public void writeXsdShortAttribute(String name, short value) throws XsdException, IOException;

    public void writeXsdShortAttribute(String namespace, String name, short value) throws XsdException, IOException;

    public void writeXsdShortElement(String namespace, String name, short value) throws XsdException, IOException;

    public void writeXsdShortElement(String namespace, String name, short value, boolean addXsiType) throws XsdException, IOException;

    // ----- xsd:string

    public void writeXsdString(String value) throws XsdException, IOException;

    public void writeXsdStringAttribute(String name, String value) throws XsdException, IOException;

    public void writeXsdStringAttribute(String namespace, String name, String value) throws XsdException, IOException;

    public void writeXsdStringElement(String namespace, String name, String value) throws XsdException, IOException;

    public void writeXsdStringElement(String namespace, String name, String value, boolean addXsiType) throws XsdException, IOException;


}

