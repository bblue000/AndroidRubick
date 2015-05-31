package androidrubicktest;

import androidrubick.net.MediaType;
import androidrubick.text.Charsets;
import androidrubick.text.SimpleTokenizer;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/21.
 *
 * @since 1.0
 */
public class TokenizerTest {

    public static void main(String args[]) {
        testChar();
        testChars();
        testString();

        testNonChar();
        testNonString();
    }

    public static void testChar() {
        System.out.println("===================testChar");
        SimpleTokenizer tokenizer = new SimpleTokenizer("aapp/t");
        System.out.println("is a = " + tokenizer.consumeChar('a'));
        tokenizer.reset();
        System.out.println("is p = " + tokenizer.consumeChar('p'));
        tokenizer.reset();
        System.out.println("is b = " + tokenizer.consumeChar('b'));
    }

    public static void testChars() {
        System.out.println("===================testChars");
        SimpleTokenizer tokenizer = new SimpleTokenizer("app/t");
        System.out.println("is a = " + tokenizer.consumeTokens('a'));
        tokenizer.reset();
        System.out.println("is ap = " + tokenizer.consumeTokens('a', 'p'));
        tokenizer.reset();
        System.out.println("is ap/ = " + tokenizer.consumeTokens('a', 'p', '/'));
        tokenizer.reset();
        System.out.println("is ap/t = " + tokenizer.consumeTokens('a', 'p', '/', 't'));
        tokenizer.reset();
        System.out.println("is a = " + tokenizer.consumeTokens("a"));
        tokenizer.reset();
        System.out.println("is ap = " + tokenizer.consumeTokens("ap"));
        tokenizer.reset();
        System.out.println("is ap/ = " + tokenizer.consumeTokens("ap/"));
        tokenizer.reset();
        System.out.println("is ap/t = " + tokenizer.consumeTokens("ap/t"));
    }

    public static void testString() {
        System.out.println("===================testString");
        SimpleTokenizer tokenizer = new SimpleTokenizer("aapp/t");
        System.out.println("is a = " + tokenizer.consumeTarget("a"));
        tokenizer.reset();
        System.out.println("is aa = " + tokenizer.consumeTarget("aa"));
        tokenizer.reset();
        System.out.println("is aap = " + tokenizer.consumeTarget("aap"));
        tokenizer.reset();
        System.out.println("is ab = " + tokenizer.consumeTarget("ab"));
    }

    public static void testNonChar() {
        System.out.println("===================testNonChar");
        SimpleTokenizer tokenizer = new SimpleTokenizer("app/t");
        System.out.println("non A = " + tokenizer.consumeNonChar('a'));
        tokenizer.reset();
        System.out.println("non P = " + tokenizer.consumeNonChar('p'));
        tokenizer.reset();
        System.out.println("non A P = " + tokenizer.consumeNonTokens('p', '/'));
        tokenizer.reset();
        System.out.println("non B = " + tokenizer.consumeNonChar('b'));
    }

    public static void testNonString() {
        System.out.println("===================testNonString");
        SimpleTokenizer tokenizer = new SimpleTokenizer("aapp/t");
        System.out.println("is a = " + tokenizer.consumeNonTarget("a"));
        tokenizer.reset();
        System.out.println("is aa = " + tokenizer.consumeNonTarget("aa"));
        tokenizer.reset();
        System.out.println("is ap = " + tokenizer.consumeNonTarget("ap"));
        tokenizer.reset();
        System.out.println("is ab = " + tokenizer.consumeNonTarget("ab"));
    }

}
