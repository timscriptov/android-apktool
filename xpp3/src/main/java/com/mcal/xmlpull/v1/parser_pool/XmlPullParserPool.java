/* -*-             c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/
// for license please see accompanying LICENSE.txt file (available also at http://www.xmlpull.org/)

package com.mcal.xmlpull.v1.parser_pool;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;
import java.util.List;

// add aging so parser used more than X times is not resused ...

/**
 * Very simple utility to make pooling of XmlPull parsers easy.
 *
 * @author <a href="http://www.extreme.indiana.edu/~aslom/">Aleksander Slominski</a>
 */
public class XmlPullParserPool {
    protected List pool = new ArrayList();
    protected XmlPullParserFactory factory;


    public XmlPullParserPool() throws XmlPullParserException {
        this(XmlPullParserFactory.newInstance());
    }

    public XmlPullParserPool(XmlPullParserFactory factory) {
        if (factory == null) throw new IllegalArgumentException();
        this.factory = factory;
    }

    // simple inline test
    public static void main(String[] args) throws Exception {
        //XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        //XmlPullParserPool pool = new XmlPullParserPool(factory);
        XmlPullParserPool pool = new XmlPullParserPool();
        XmlPullParser p1 = pool.getPullParserFromPool();
        pool.returnPullParserToPool(p1);
        XmlPullParser p2 = pool.getPullParserFromPool();
        //assert p1 == p2;
        if (p1 != p2) throw new RuntimeException();
        pool.returnPullParserToPool(p2);
        System.out.println(pool.getClass() + " OK");
    }

    protected XmlPullParser newParser() throws XmlPullParserException {
        return factory.newPullParser();
    }

    public XmlPullParser getPullParserFromPool()
            throws XmlPullParserException {
        XmlPullParser pp = null;
        if (pool.size() > 0) {
            synchronized (pool) {
                if (pool.size() > 0) {
                    pp = (XmlPullParser) pool.remove(pool.size() - 1);
                }
            }
        }
        if (pp == null) {
            pp = newParser();
            //System.err.println("new parser instance created pp="+pp);
        }
        return pp;
    }

    public void returnPullParserToPool(XmlPullParser pp) {
        if (pp == null) throw new IllegalArgumentException();
        synchronized (pool) {
            pool.add(pp);
        }
    }
}

