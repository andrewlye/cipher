/* CS3: Project 1 starter file with tests for the Cipher class.
 * This class has a main method that runs the tests and prints results.
 * Add some more tests of your own.
 */
public class Tests {
    private static int totalRun = 0;
    private static int totalCorrect = 0;

    public static void reportResult(String input, String expected, String actual) {
        totalRun++;
        if (expected.equals(actual)) {
            totalCorrect++;
            System.out.println("Test passed: " + input + " -> " + actual);
        } else {
            System.out.println("Test failed: " + input + " -> " + actual + ", expected " + expected);
        }
    }

    public static void testEncipher(int offset, int blockLength, String clear, String expected) {
        String actual = null;
        try {
            Cipher cipher = new Cipher(offset, blockLength);
            actual = cipher.encipher(clear);
        }
        catch (Exception e)
        {
            actual = e.toString();
        }
        reportResult(clear, expected, actual);
    }

    public static void testDecipher(int offset, int blockLength, String enciphered, String expected) {
        String actual = null;
        try {
            Cipher cipher = new Cipher(offset, blockLength);
            actual = cipher.decipher(enciphered);
        }
        catch (Exception e)
        {
            actual = e.toString();
        }
        reportResult(enciphered, expected, actual);
    }

    public static void testRoundtrip(int offset, int blockLength, String clear) {
        // Tests that encipher and decipher gives the original, assuming only supported characters are used.
        String actual = null;
        try {
            Cipher cipher = new Cipher(offset, blockLength);
            String enciphered = cipher.encipher(clear);
            actual = cipher.decipher(enciphered);
        }
        catch (Exception e)
        {
            actual = e.toString();
        }
        reportResult(clear, clear, actual);
    }

    public static void testCrack(String enciphered, String expected) {
        // Tests for the static crack method, which relies on punctuation conventions to decipher
        String actual = null;
        try {
            actual = Cipher.crack(enciphered);
        }
        catch (Exception e)
        {
            actual = e.toString();
        }
        reportResult(enciphered, expected, actual);
    }

    public static void main(String[] args)
    {
        testEncipher(3, 2, "Test offset three, block length two.", "hWwvr;iihv;wkwhu.he;ronfo;qhwj;kzwAr");
        testDecipher(3, 2, "hWwvr;iihv;wkwhu.he;ronfo;qhwj;kzwAr", "Test offset three, block length two.");
        testEncipher(0, 1, "Test unchanged.", "Test unchanged.");
        testDecipher(0, 1, "Test unchanged.", "Test unchanged.");
        testEncipher(4, 3, "Test unrecognized characters like *.", "wiXy:xivrksg;mr:hielggevvixp:wiomB$:");
        testDecipher(4, 3, "wiXy:xivrksg;mr:hielggevvixp:wiomB$:", "Test unrecognized characters like $.");
        testEncipher(59, 1000, "Test max offset and huge block length.", ":gsfmdkzjbnkazdftgzcmZzsdreenzwZlzsrdS");
        testDecipher(59, 1000, ":gsfmdkzjbnkazdftgzcmZzsdreenzwZlzsrdS", "Test max offset and huge block length.");
        testEncipher(3, 2, "Test \n,;:.!? and ABYZ and abyz.", "hWwv:;!.A?CBd;gqD;bE;cqd;ged,\nA");
        testDecipher(3, 2, "hWwv:;!.A?CBd;gqD;bE;cqd;ged,\nA", "Test \n,;:.!? and ABYZ and abyz.");
        testRoundtrip(2, 4, "Test roundtrip.");
        testCrack("rHA?tiHJzrp!HwrxwD?tx t!B:H,;HApBAr,;rH,;xxA,tC,N?,;", "Test crack, which relies on punctuation conventions!");
        testCrack("jwmy.f.xn.jwjm\n;sf.jhnyxzosNjb.Cjwjm\n;wj j.jhnyxzo.ty.yfqgfufhxjsn.sf.sn.ymlzfh.jwf.ijny.?;ynqfzyzr.kt.pwt\nyjs.jyxji.kt.ysjrwfl.jqlsnx.f.sn.C;sn", "Injustice anywhere is a threat to justice everywhere. We are caught in an inescapable network of mutuality, tied in a single garment of destiny."); // TODO: add the correct expected output to make this pass
        testEncipher(0, 5, "Test reverse of a partial block, at the end.", " tseTreverfo esap a laitrcolb ta ,k eht .dne");
        testDecipher(0, 5, " tseTreverfo esap a laitrcolb ta ,k eht .dne", "Test reverse of a partial block, at the end.");
        testEncipher(1, 1, "Test offset one.", "Uftu\npggtfu\npof!");
        testDecipher(1, 1, "Uftu\npggtfu\npof!", "Test offset one.");
        System.out.println("Finished tests, " + totalCorrect + " out of " + totalRun + " passed.");
    }
}
