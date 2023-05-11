/* -*-             c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/

package com.mcal.xmlpull.v1.xsd;

import org.xmlpull.v1.XmlPullParserException;
import com.mcal.xmlpull.v1.wrapper.XmlPullParserWrapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;

//TODO: add mapping ofr QName

/**
 * This class provides XSD typed reading from XML stream
 *
 * @author <a href="http://www.extreme.indiana.edu/~aslom/">Aleksander Slominski</a>
 */
public interface XsdPullParser extends XmlPullParserWrapper {

    //---- xsd:anyURI

    public URI nextXsdAnyUriElement(String namespace, String name)
            throws XmlPullParserException, IOException;

    public URI readXsdAnyUriAttribute(String namespace, String name) throws XmlPullParserException, IOException;

    public URI readXsdAnyUriAttribute(String name) throws XmlPullParserException, IOException;

    public URI readXsdAnyUriElement() throws XmlPullParserException, IOException;

    //---- xsd:base64Binary

    public byte[] nextXsdBase64Element(String namespace, String name)
            throws XmlPullParserException, IOException;

    public byte[] readXsdBase64Attribute(String namespace, String name) throws XmlPullParserException, IOException;

    public byte[] readXsdBase64Attribute(String name) throws XmlPullParserException, IOException;

    public byte[] readXsdBase64Element() throws XmlPullParserException, IOException;

    //---- xsd:boolean

    public boolean nextXsdBooleanElement(String namespace, String name)
            throws XmlPullParserException, IOException;

    public boolean readXsdBooleanAttribute(String namespace, String name) throws XmlPullParserException, IOException;

    public boolean readXsdBooleanAttribute(String name) throws XmlPullParserException, IOException;

    public boolean readXsdBooleanElement() throws XmlPullParserException, IOException;

    //---- xsd:byte

    public byte nextXsdByteElement(String namespace, String name)
            throws XmlPullParserException, IOException;

    public byte readXsdByteAttribute(String namespace, String name) throws XmlPullParserException, IOException;

    public byte readXsdByteAttribute(String name) throws XmlPullParserException, IOException;

    public byte readXsdByteElement() throws XmlPullParserException, IOException;

    //---- xsd:decimal

    public BigDecimal nextXsdDecimalElement(String namespace, String name)
            throws XmlPullParserException, IOException;

    public BigDecimal readXsdDecimalAttribute(String namespace, String name) throws XmlPullParserException, IOException;

    public BigDecimal readXsdDecimalAttribute(String name) throws XmlPullParserException, IOException;

    public BigDecimal readXsdDecimalElement() throws XmlPullParserException, IOException;

    //------ xsd:double

    /**
     * Calls parser to get next event. Checks that it is START_TAG
     * and has namespace and name given (checks are only if arguments are not null).
     * Then calls readXsdDoubleElement().
     */
    public double nextXsdDoubleElement(String namespace, String name)
            throws XsdException, XmlPullParserException, IOException;

    /**
     * Read string content of attribute name and try to convert it to xsd:double.
     * Take special care of INF, Infinity and NaN.
     * Parser must be on START_TAG.
     */
    public double readXsdDoubleAttribute(String name)
            throws XsdException, XmlPullParserException, IOException;

    public double readXsdDoubleAttribute(String namesoace, String name)
            throws XsdException, XmlPullParserException, IOException;

    /**
     * Read string content of element and try to convert it to xsd:double.
     * Take special care of INF, Infinity and NaN.
     * Parser must be positioned on START_TAG or
     * After this method executed the parser is positioned on END_TAG.
     */
    public double readXsdDoubleElement()
            throws XsdException, XmlPullParserException, IOException;


    //------ xsd:float

    /**
     * Read string content of elment and convert it to float.
     * Take special care of INF, Infinity and NaN.
     * After this method executed the parser is positioned on END_TAG.
     */
    public float nextXsdFloatElement(String namespace, String name)
            throws XsdException, XmlPullParserException, IOException;

    public float readXsdFloatAttribute(String namespace, String name) throws XmlPullParserException, IOException;

    public float readXsdFloatAttribute(String name) throws XmlPullParserException, IOException;

    public float readXsdFloatElement() throws XmlPullParserException, IOException;

    //---- xsd:int

    /**
     * Read string content of elment and try to convert it to int.
     * Take special care of INF, Infinity and NaN.
     * After this method executed the parser is positioned on END_TAG.
     */
    public int nextXsdIntElement(String namespace, String name)
            throws XsdException, XmlPullParserException, IOException;

    public int readXsdIntAttribute(String name)
            throws XsdException, XmlPullParserException, IOException;

    public int readXsdIntAttribute(String namesoace, String name)
            throws XsdException, XmlPullParserException, IOException;

    public int readXsdIntElement()
            throws XsdException, XmlPullParserException, IOException;

    //---- xsd:integer

    public BigInteger nextXsdIntegerElement(String namespace, String name) throws XsdException, XmlPullParserException, IOException;

    public BigInteger readXsdIntegerAttribute(String namespace, String name) throws XmlPullParserException, IOException;

    public BigInteger readXsdIntegerAttribute(String name) throws XmlPullParserException, IOException;

    public BigInteger readXsdIntegerElement() throws XmlPullParserException, IOException;

    //---- xsd:long

    public long nextXsdLongElement(String namespace, String name) throws XsdException, XmlPullParserException, IOException;

    public long readXsdLongAttribute(String namespace, String name) throws XmlPullParserException, IOException;

    public long readXsdLongAttribute(String name) throws XmlPullParserException, IOException;

    public long readXsdLongElement() throws XmlPullParserException, IOException;

    //---- xsd:short

    public short nextXsdShortElement(String namespace, String name) throws XsdException, XmlPullParserException, IOException;

    public short readXsdShortAttribute(String namespace, String name) throws XmlPullParserException, IOException;

    public short readXsdShortAttribute(String name) throws XmlPullParserException, IOException;

    public short readXsdShortElement() throws XmlPullParserException, IOException;

    //---- xsd:strin

    /**
     * Check for xsi:nil and if it has value 'true' returns null
     * as described in
     * <a href="http://www.w3.org/TR/xmlschema-1/#Instance_Document_Constructions">XML Schemas
     * Part 1</a>
     * otherwise it calls nextText().
     * After this method executed the parser is positioned on END_TAG.
     */
    public String nextXsdStringElement(String namespace, String name)
            throws XsdException, XmlPullParserException, IOException;

    public String readXsdStringAttribute(String name)
            throws XsdException, XmlPullParserException, IOException;

    public String readXsdStringAttribute(String namesoace, String name)
            throws XsdException, XmlPullParserException, IOException;

    public String readXsdStringElement()
            throws XsdException, XmlPullParserException, IOException;


}

