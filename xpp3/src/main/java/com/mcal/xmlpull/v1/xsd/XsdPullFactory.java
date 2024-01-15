/* -*-             c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/
// for license please see accompanying LICENSE.txt file (available also at http://www.xmlpull.org/)

package com.mcal.xmlpull.v1.xsd;

import com.mcal.xmlpull.v1.wrapper.XmlPullWrapperFactory;
import com.mcal.xmlpull.v1.xsd.impl.XsdTypePullParserImpl;
import com.mcal.xmlpull.v1.xsd.impl.XsdTypeSerializerImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/**
 * Factory to access implementation of XML Schema Data Types for XML Pull Parser.
 * <p>NOTE: even though factory is mult-thread safe
 * created objects returned from this factory are <strong>not</strong> multi-thread safe
 *
 * @author <a href="http://www.extreme.indiana.edu/~aslom/">Aleksander Slominski</a>
 */

public class XsdPullFactory {
    private final static boolean DEBUG = false;
    protected XmlPullParserFactory f;
    protected XmlPullWrapperFactory wf;

    protected XsdPullFactory(XmlPullParserFactory factory) throws XmlPullParserException {
        if (factory != null) {
            this.f = factory;
        } else {
            this.f = XmlPullParserFactory.newInstance();
        }
        this.wf = XmlPullWrapperFactory.newInstance(f);
    }

    @Contract(" -> new")
    public static @NotNull XsdPullFactory newInstance() throws XmlPullParserException {
        //TODO: make into real pluggable factory service (later ...)?
        return new XsdPullFactory(null);
    }

    @Contract("_ -> new")
    public static @NotNull XsdPullFactory newInstance(XmlPullParserFactory factory)
            throws XmlPullParserException {
        return new XsdPullFactory(factory);
    }


    // ------------ IMPLEMENTATION

    public static @NotNull XsdPullFactory newInstance(String classNames, Class context)
            throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance(classNames, context);
        return new XsdPullFactory(factory);
    }

    public XmlPullParserFactory getFactory() throws XmlPullParserException {
        return f;
    }

    public void setFeature(String name,
                           boolean state) throws XmlPullParserException {
        f.setFeature(name, state);
    }


    public boolean getFeature(String name) {
        return f.getFeature(name);
    }

    public boolean isNamespaceAware() {
        return f.isNamespaceAware();
    }

    public void setNamespaceAware(boolean awareness) {
        f.setNamespaceAware(awareness);
    }

    public boolean isValidating() {
        return f.isValidating();
    }

    public void setValidating(boolean validating) {
        f.setValidating(validating);
    }

    public XsdPullParser newXsdPullParser() throws XmlPullParserException {
        XmlPullParser pp = f.newPullParser();
        return newXsdPullParser(pp);
    }

    public XsdPullParser newXsdPullParser(XmlPullParser pp) throws XmlPullParserException {
        return new XsdTypePullParserImpl(pp);
    }

    public XsdSerializer newXsdSerializer() throws XmlPullParserException {
        XmlSerializer xs = f.newSerializer();
        return newXsdSerializer(xs);
    }

    public XsdSerializer newXsdSerializer(XmlSerializer xs) throws XmlPullParserException {
        return new XsdTypeSerializerImpl(xs, wf);
    }
}
