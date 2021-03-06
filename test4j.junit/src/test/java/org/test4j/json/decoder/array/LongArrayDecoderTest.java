package org.test4j.json.decoder.array;

import org.junit.Test;
import org.test4j.json.JSON;
import org.test4j.junit.Test4J;

public class LongArrayDecoderTest extends Test4J {

    @Test
    public void testParseFromJSONArray() {
        String json = "['12',124L,\"456l\"]";
        Long[] ints = JSON.toObject(json, long[].class);
        want.array(ints).sizeEq(3).reflectionEq(new long[] { 12L, 124L, 456L });
    }
}
