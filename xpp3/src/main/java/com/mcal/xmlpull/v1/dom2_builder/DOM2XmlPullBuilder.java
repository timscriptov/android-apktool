/* -*-             c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/
// for license please see accompanying LICENSE.txt file (available also at http://www.xmlpull.org/)

package com.mcal.xmlpull.v1.dom2_builder;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

//TOOD add parse methodthat will usenextToken() to reconstruct complete XML infoset

/**
 * <strong>Simplistic</strong> DOM2 builder that should be enough to do support most cases.
 * Requires JAXP DOMBuilder to provide DOM2 implementation.
 * <p>NOTE:this class is stateless factory and it is safe to share between multiple threads.
 * </p>
 *
 * @author <a href="http://www.extreme.indiana.edu/~aslom/">Aleksander Slominski</a>
 */

public class DOM2XmlPullBuilder {

    //protected XmlPullParser pp;
    //protected XmlPullParserFactory factory;

    public DOM2XmlPullBuilder() { //throws XmlPullParserException {
        //factory = XmlPullParserFactory.newInstance();
    }
    //public DOM2XmlPullBuilder(XmlPullParser pp) throws XmlPullParserException {
    //public DOM2XmlPullBuilder(XmlPullParserFactory factory)throws XmlPullParserException
    //{
    //    this.factory = factory;
    //}

    private static void assertEquals(String expected, String s) {
        if ((expected != null && !expected.equals(s)) || (expected == null && s == null)) {
            throw new RuntimeException("expected '" + expected + "' but got '" + s + "'");
        }
    }

    private static void assertNotNull(Object o) {
        if (o == null) {
            throw new RuntimeException("expected no null value");
        }
    }

    /**
     * Minimal inline test
     */
    public static void main(String[] args) throws Exception {
        DOM2XmlPullBuilder builder = new DOM2XmlPullBuilder();

        final String XML = "<n:foo xmlns:n='uri1'><bar n:attr='test' xmlns='uri2'>baz</bar></n:foo>";
        StringReader reader = new StringReader(XML);

        // create document

        Element el1 = builder.parse(reader);
        //System.out.println("doc="+doc);


        // serialize and deserialzie ...
        StringWriter sw = new StringWriter();

        // requires JAXP
        //        TransformerFactory xformFactory
        //            = TransformerFactory.newInstance();
        //        Transformer idTransform = xformFactory.newTransformer();
        //        Source input = new DOMSource(doc1);
        //        Result output = new StreamResult(sw);
        //        idTransform.transform(input, output);

        //OutputFormat fmt = new OutputFormat();
        //XMLSerializer serializer = new XMLSerializer(sw, null);
        //serializer.serialize(doc1);
        //sw.close();
        //String serialized = sw.toString();
        //System.out.println("serialized="+serialized);

        reader = new StringReader(XML);

        // reparse
        // check that what was written is OK
        Element root = builder.parse(reader);
        ; //doc2.getDocumentElement();
        //System.out.println("root="+root);
        System.out.println("root ns=" + root.getNamespaceURI() + ", localName=" + root.getLocalName());
        assertEquals("uri1", root.getNamespaceURI());
        assertEquals("foo", root.getLocalName());

        NodeList children = root.getElementsByTagNameNS("*", "bar");
        Element bar = (Element) children.item(0);
        System.out.println("bar ns=" + bar.getNamespaceURI() + ", localName=" + bar.getLocalName());
        assertEquals("uri2", bar.getNamespaceURI());
        assertEquals("bar", bar.getLocalName());

        //
        String attrValue = bar.getAttributeNS("uri1", "attr");
        assertEquals("test", attrValue);
        Attr attr = bar.getAttributeNodeNS("uri1", "attr");
        assertNotNull(attr);
        assertEquals("uri1", attr.getNamespaceURI());
        assertEquals("attr", attr.getLocalName());
        assertEquals("test", attr.getValue());


        Text text = (Text) bar.getFirstChild();
        System.out.println("text=" + text.getNodeValue());
        assertEquals("baz", text.getNodeValue());

    }

    protected Document newDoc() throws XmlPullParserException {
        try {
            // if you see dreaded "java.lang.NoClassDefFoundError: org/w3c/dom/ranges/DocumentRange"
            // add the newest xercesImpl and apis JAR file to JRE/lib/endorsed
            // for more deatils see: http://java.sun.com/j2se/1.4.2/docs/guide/standards/index.html
            DocumentBuilderFactory domFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            DOMImplementation impl = builder.getDOMImplementation();
            return builder.newDocument();
        } catch (FactoryConfigurationError ex) {
            throw new XmlPullParserException(
                    "could not configure factory JAXP DocumentBuilderFactory: " + ex, null, ex);
        } catch (ParserConfigurationException ex) {
            throw new XmlPullParserException(
                    "could not configure parser JAXP DocumentBuilderFactory: " + ex, null, ex);
        }
    }

    protected XmlPullParser newParser() throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        return factory.newPullParser();
    }

    public Element parse(Reader reader) throws XmlPullParserException, IOException {
        Document docFactory = newDoc();
        return parse(reader, docFactory);
    }

    public Element parse(Reader reader, Document docFactory)
            throws XmlPullParserException, IOException {
        XmlPullParser pp = newParser();
        pp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
        pp.setInput(reader);
        pp.next();
        return parse(pp, docFactory);
    }

    public Element parse(XmlPullParser pp, Document docFactory)
            throws XmlPullParserException, IOException {
        return parseSubTree(pp, docFactory);
    }

    public Element parseSubTree(XmlPullParser pp) throws XmlPullParserException, IOException {
        Document doc = newDoc();
        return parseSubTree(pp, doc);
    }

    public Element parseSubTree(XmlPullParser pp, Document docFactory)
            throws XmlPullParserException, IOException {
        BuildProcess process = new BuildProcess();
        return process.parseSubTree(pp, docFactory);
    }

    static class BuildProcess {
        private XmlPullParser pp;
        private Document docFactory;
        private boolean scanNamespaces = true;

        private BuildProcess() {
        }

        public Element parseSubTree(XmlPullParser pp, Document docFactory)
                throws XmlPullParserException, IOException {
            this.pp = pp;
            this.docFactory = docFactory;
            return parseSubTree();
        }

        private Element parseSubTree()
                throws XmlPullParserException, IOException {
            pp.require(XmlPullParser.START_TAG, null, null);
            String name = pp.getName();
            String ns = pp.getNamespace();
            String prefix = pp.getPrefix();
            String qname = prefix != null ? prefix + ":" + name : name;
            Element parent = docFactory.createElementNS(ns, qname);

            //declare namespaces - quite painful and easy to fail process in DOM2
            declareNamespaces(pp, parent);

            // process attributes
            for (int i = 0; i < pp.getAttributeCount(); i++) {
                String attrNs = pp.getAttributeNamespace(i);
                String attrName = pp.getAttributeName(i);
                String attrValue = pp.getAttributeValue(i);
                if (attrNs == null || attrNs.isEmpty()) {
                    parent.setAttribute(attrName, attrValue);
                } else {
                    String attrPrefix = pp.getAttributePrefix(i);
                    String attrQname = attrPrefix != null ? attrPrefix + ":" + attrName : attrName;
                    parent.setAttributeNS(attrNs, attrQname, attrValue);
                }
            }

            // process children
            while (pp.next() != XmlPullParser.END_TAG) {
                if (pp.getEventType() == XmlPullParser.START_TAG) {
                    Element el = parseSubTree(pp, docFactory);
                    parent.appendChild(el);
                } else if (pp.getEventType() == XmlPullParser.TEXT) {
                    String text = pp.getText();
                    Text textEl = docFactory.createTextNode(text);
                    parent.appendChild(textEl);
                } else {
                    throw new XmlPullParserException(
                            "unexpected event " + XmlPullParser.TYPES[pp.getEventType()], pp, null);
                }
            }
            pp.require(XmlPullParser.END_TAG, ns, name);
            return parent;
        }

        private void declareNamespaces(XmlPullParser pp, Element parent)
                throws DOMException, XmlPullParserException {
            if (scanNamespaces) {
                scanNamespaces = false;
                int top = pp.getNamespaceCount(pp.getDepth()) - 1;
                // this loop computes list of all in-scope prefixes
                LOOP:
                for (int i = top; i >= pp.getNamespaceCount(0); --i) {
                    // make sure that no prefix is duplicated
                    String prefix = pp.getNamespacePrefix(i);
                    for (int j = top; j > i; --j) {
                        if (prefix.equals(pp.getNamespacePrefix(j))) {
                            // prefix is already declared -- skip it
                            continue LOOP;
                        }
                    }
                    declareOneNamespace(pp, i, parent);
                }
            } else {
                for (int i = pp.getNamespaceCount(pp.getDepth() - 1);
                     i < pp.getNamespaceCount(pp.getDepth());
                     ++i) {
                    declareOneNamespace(pp, i, parent);
                }
            }
        }

        private void declareOneNamespace(@NotNull XmlPullParser pp, int i, @NotNull Element parent)
                throws DOMException, XmlPullParserException {
            String xmlnsPrefix = pp.getNamespacePrefix(i);
            String xmlnsUri = pp.getNamespaceUri(i);
            String xmlnsDecl = (xmlnsPrefix != null) ? "xmlns:" + xmlnsPrefix : "xmlns";
            parent.setAttributeNS("http://www.w3.org/2000/xmlns/", xmlnsDecl, xmlnsUri);
        }
    }
}
