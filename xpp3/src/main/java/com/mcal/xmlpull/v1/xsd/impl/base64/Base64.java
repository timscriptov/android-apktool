/* -*- mode: Java; c-basic-offset: 4; indent-tabs-mode: nil; -*-  //------100-columns-wide------>|*/
/*
 * Copyright (c) 2002-2004 Extreme! Lab, Indiana University. All rights reserved.
 *
 * This software is open source. See the bottom of this file for the licence.
 *
 * $Id: Base64.java,v 1.4 2003/04/06 00:04:25 aslom Exp $
 */

package com.mcal.xmlpull.v1.xsd.impl.base64;

import org.jetbrains.annotations.NotNull;

/**
 * This is utility class to handle BAS64 encoding and decoding.
 * For deatils see <a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>.
 *
 * <p>NOTE: Decoding is forgiving: spaces etc. are ignored at the beginngin and
 * the end of input and as well inside encoded data - there is also no limit
 * on maximum size of one input line - all data may be just encoded in one line
 * - good for SOAP base64 encoding.
 * <p>Copied from
 * http://www.extreme.indiana.edu/viewcvs/~checkout~/xsoap-java/src/java/soaprmi/util/soaprmi/util/base64/
 * </p>
 *
 * @author <a href="http://www.extreme.indiana.edu/~aslom/">Aleksander Slominski</a>
 * @version $Revision: 1.4 $ $Date: 2003/04/06 00:04:25 $ (GMT)
 */

public class Base64 {
    //static private final Base64Codec statelessCodec = new Base64Codec();

    // from http://www.ietf.org/rfc/rfc2045.txt
    //
    //                    Table 1: The Base64 Alphabet
    //
    //     Value Encoding  Value Encoding  Value Encoding  Value Encoding
    //         0 A            17 R            34 i            51 z
    //         1 B            18 S            35 j            52 0
    //         2 C            19 T            36 k            53 1
    //         3 D            20 U            37 l            54 2
    //         4 E            21 V            38 m            55 3
    //         5 F            22 W            39 n            56 4
    //         6 G            23 X            40 o            57 5
    //         7 H            24 Y            41 p            58 6
    //         8 I            25 Z            42 q            59 7
    //         9 J            26 a            43 r            60 8
    //        10 K            27 b            44 s            61 9
    //        11 L            28 c            45 t            62 +
    //        12 M            29 d            46 u            63 /
    //        13 N            30 e            47 v
    //        14 O            31 f            48 w         (pad) =
    //        15 P            32 g            49 x
    //        16 Q            33 h            50 y

    // this array is use to convert 6-bit value (0..63) to ASCII character
    static private final char[] base64alphabet = //new char[64]
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                    .toCharArray();


    // this array is use to convert input ASCII char (0..127) into 6-bit value
    // -1 is used to signal improper ASCII character
    static private final byte[] base64lookup = new byte[128];

    // initilaize lookup table
    static {
        if (base64alphabet.length != 64) throw new IllegalStateException();
        for (int i = 0; i < 128; i++) base64lookup[i] = -1;
        for (int i = 'A'; i <= 'Z'; i++) base64lookup[i] = (byte) (i - 'A');
        for (int i = 'a'; i <= 'z'; i++) base64lookup[i] = (byte) (i - 'a' + 26);
        for (int i = '0'; i <= '9'; i++) base64lookup[i] = (byte) (i - '0' + 52);
        base64lookup['+'] = 62;
        base64lookup['/'] = 63;
    }


    /**
     *
     */


    public static byte @NotNull [] decode(
            Base64DecodingState state,
            char[] data,
            int off,
            int len,
            boolean finalize) throws RuntimeException {
        try {
            int inputEnd = 0;
            byte[] input = new byte[4];
            if (state != null) {
                inputEnd = state.inputEnd;
                System.arraycopy(state.input, 0, input, 0, state.input.length);
            }

            // skip initial white caharacters in input to better estimate output array size
            int end = off + len;
            for (int i = off; i < end; ++i) {
                if (!Character.isWhitespace(data[i])) {
                    off = i;
                    break;
                }
            }
            // skip trailing white caharacters
            for (int i = end - 1; i > off; --i) {
                if (!Character.isWhitespace(data[i])) {
                    end = i + 1;
                    break;
                }
            }

            // determine maximum length of output data - may be needed to prune later
            len = end - off;
            //NOTE: adjusted with state from previous run
            long dataBits = 6L * (len + inputEnd);
            long bytesToOutputLong = (dataBits / 8);


            //TODO take advantage of check that input len must be divided by 4!!!
            // TODO check for possibly last one or tow padding characters ==

            //if(bytesToOutputLong > Integer.MAX_VALUE) {
            // throw new RuntimeException("input data is too big to output one byte array");
            //}
            int bytesToOutput = (int) bytesToOutputLong;
            byte[] b = new byte[bytesToOutput];

            // start decoding by breaking data into 4-character chunks
            int bytesSoFar = 0; //GLOBAL
            for (int i = off; i < end; ++i) {
                char ch = data[i];
                if (Character.isWhitespace(ch)) continue;
                // now convert ASCII cahracter to value
                if (ch != '=') {
                    if ((int) ch > 127) {
                        throw new Base64EncodingException(
                                "invalid base64 encoding character > 127 '" + ch + "''");
                    }
                    byte bv = base64lookup[ch];
                    if (bv == -1) {
                        throw new Base64EncodingException(
                                "invalid base64 encoding character '" + ch + "''");
                    }
                    input[inputEnd++] = bv;
                }

                // we have the chunk - now work on it
                if (inputEnd == 4) {
                    inputEnd = 0;
                    // no markers
                    b[bytesSoFar++] = (byte) (input[0] << 2 | input[1] >> 4);
                    b[bytesSoFar++] = (byte) (((input[1] & 0xf) << 4) | ((input[2] >> 2) & 0xf));
                    b[bytesSoFar++] = (byte) (input[2] << 6 | input[3]);

                } else if (ch == '=') {
                    // got marker - try extract final data
                    finalize = true;
                    if (inputEnd == 3) {
                        // got three character and at least one end marker
                        // extract two bytes of data
                        b[bytesSoFar++] = (byte) (input[0] << 2 | input[1] >> 4);
                        b[bytesSoFar++] = (byte) (((input[1] & 0xf) << 4) | ((input[2] >> 2) & 0xf));

                    } else if (inputEnd == 2) {
                        // got two character and at least one end marker
                        // extract one byte of data
                        b[bytesSoFar++] = (byte) (input[0] << 2 | input[1] >> 4);


                    } else {
                        throw new RuntimeException(
                                "end marker in wrong place to finish decoding base64");
                    }
                    inputEnd = 0;
                    break;
                }
            }

            if (finalize && inputEnd != 0) {
                throw new RuntimeException("missing base64 encoded data");
            }
            if (bytesSoFar < b.length) {
                byte[] bb = new byte[bytesSoFar];
                // logger.fine("ary adjustment "
                System.arraycopy(b, 0, bb, 0, bb.length);
                b = bb;
            }
            return b;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace(); //TODO FIXME
            throw new RuntimeException("illegal Base64 character");
        }
    }

    public static byte @NotNull [] decode(char[] data, int off, int len) throws RuntimeException {
        return decode(null, data, off, len, true);
    }

    public static byte @NotNull [] decode(char[] data) throws RuntimeException {
        return decode(data, 0, data.length);
    }

    public static char @NotNull [] encode(
            Base64EncodingState state,
            byte[] data,
            int off,
            int len,
            boolean finalize) throws RuntimeException {
        try {
            //int outputEnd = (state != null) ? state.outputEnd : 0;
            int outputEnd = 0;
            int[] output = new int[4];
            if (state != null) {
                outputEnd = state.outputEnd;
                System.arraycopy(state.output, 0, output, 0, state.output.length);
            }

            // assert (outputEnd >= 0 && outputEnd <= 2)
            int end = off + len;

            long dataTriples = (len + outputEnd + 2) / 3; //NOTE: adjusted with state from previous run
            long charsToOutputLong = 4 * dataTriples;

            if (charsToOutputLong > Integer.MAX_VALUE) {
                throw new RuntimeException("input data is too big to output in one char array");
            }
            int charsToOutput = (int) charsToOutputLong;
            char[] b = new char[charsToOutput];

            // start decoding by breaking data into 4-character chunks
            int charsSoFar = 0;
            for (int i = off; i < end; ++i) {
                //output[ outputEnd++ ] = data[i] < 0 ? ((int)data[i] + 256) : data[i] ;
                output[outputEnd++] = (int) data[i] & 0xFF;
                // we have the chunk - now work on it
                if (outputEnd == 3) {
                    outputEnd = 0;
                    // no markers
                    b[charsSoFar++] = base64alphabet[output[0] >> 2];
                    b[charsSoFar++] = base64alphabet[((output[0] & 0x3) << 4) | (output[1] >> 4)];
                    b[charsSoFar++] = base64alphabet[((output[1] & 0xf) << 2) | (output[2] >> 6)];
                    b[charsSoFar++] = base64alphabet[output[2] & 0x3f];
                }
            }

            if (finalize) {
                if (outputEnd == 2) {
                    b[charsSoFar++] = base64alphabet[output[0] >> 2];
                    b[charsSoFar++] = base64alphabet[((output[0] & 0x3) << 4) | output[1] >> 4];
                    b[charsSoFar++] = base64alphabet[((output[1] & 0xf) << 2)];
                    b[charsSoFar++] = '=';


                } else if (outputEnd == 1) {
                    b[charsSoFar++] = base64alphabet[output[0] >> 2];

                    //b[charsSoFar++] = base64alphabet [ (output[0] & 0x3) << 4 ];
                    int t = (output[0] & 0x3);
                    t <<= 4;
                    b[charsSoFar++] = base64alphabet[t];
                    b[charsSoFar++] = '=';
                    b[charsSoFar++] = '=';

                }

            }
            return b;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace(); //TODO FIXME
            throw new RuntimeException("illegal Base64 character");
        }
    }

    public static char @NotNull [] encode(byte[] data, int off, int len) throws RuntimeException {
        return encode(null, data, off, len, true);
    }

    public static char @NotNull [] encode(byte[] data) throws RuntimeException {
        return encode(data, 0, data.length);
    }

}

/*
 * Indiana University Extreme! Lab Software License, Version 1.2
 *
 * Copyright (c) 2002-2004 The Trustees of Indiana University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1) All redistributions of source code must retain the above
 *    copyright notice, the list of authors in the original source
 *    code, this list of conditions and the disclaimer listed in this
 *    license;
 *
 * 2) All redistributions in binary form must reproduce the above
 *    copyright notice, this list of conditions and the disclaimer
 *    listed in this license in the documentation and/or other
 *    materials provided with the distribution;
 *
 * 3) Any documentation included with all redistributions must include
 *    the following acknowledgement:
 *
 *      "This product includes software developed by the Indiana
 *      University Extreme! Lab.  For further information please visit
 *      http://www.extreme.indiana.edu/"
 *
 *    Alternatively, this acknowledgment may appear in the software
 *    itself, and wherever such third-party acknowledgments normally
 *    appear.
 *
 * 4) The name "Indiana University" or "Indiana University
 *    Extreme! Lab" shall not be used to endorse or promote
 *    products derived from this software without prior written
 *    permission from Indiana University.  For written permission,
 *    please contact http://www.extreme.indiana.edu/.
 *
 * 5) Products derived from this software may not use "Indiana
 *    University" name nor may "Indiana University" appear in their name,
 *    without prior written permission of the Indiana University.
 *
 * Indiana University provides no reassurances that the source code
 * provided does not infringe the patent or any other intellectual
 * property rights of any other entity.  Indiana University disclaims any
 * liability to any recipient for claims brought by any other entity
 * based on infringement of intellectual property rights or otherwise.
 *
 * LICENSEE UNDERSTANDS THAT SOFTWARE IS PROVIDED "AS IS" FOR WHICH
 * NO WARRANTIES AS TO CAPABILITIES OR ACCURACY ARE MADE. INDIANA
 * UNIVERSITY GIVES NO WARRANTIES AND MAKES NO REPRESENTATION THAT
 * SOFTWARE IS FREE OF INFRINGEMENT OF THIRD PARTY PATENT, COPYRIGHT, OR
 * OTHER PROPRIETARY RIGHTS.  INDIANA UNIVERSITY MAKES NO WARRANTIES THAT
 * SOFTWARE IS FREE FROM "BUGS", "VIRUSES", "TROJAN HORSES", "TRAP
 * DOORS", "WORMS", OR OTHER HARMFUL CODE.  LICENSEE ASSUMES THE ENTIRE
 * RISK AS TO THE PERFORMANCE OF SOFTWARE AND/OR ASSOCIATED MATERIALS,
 * AND TO THE PERFORMANCE AND VALIDITY OF INFORMATION GENERATED USING
 * SOFTWARE.
 */
