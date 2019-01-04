import java.math.BigInteger;
import java.util.Random;

public class RSA {
    class Key {
        BigInteger n;
        BigInteger e;
        BigInteger d;

        Key() { }
    }

    private Key key;
    // max length of combination of chars
    private int maxComLen;
    // one decimal shift right MULTIPLE
    private final int MULTIPLE = (int)Math.pow(10, 5);

    public RSA(int maxComLen) {
        final int BIT_LENGTH = 40;
        final int CERTAINTY = 10;
        this.maxComLen = maxComLen;
        key = new Key();
        // generate p and q
        Random random = new Random();
        BigInteger p = new BigInteger(BIT_LENGTH, CERTAINTY, random);
        BigInteger q = new BigInteger(BIT_LENGTH, CERTAINTY, random);

        while (!isPrime(p)) {
            p = new BigInteger(BIT_LENGTH, CERTAINTY, random);
        }
        while (!isPrime(q) || p.equals(q)) {
            q = new BigInteger(BIT_LENGTH, CERTAINTY, random);
        }
        // n = p * q;
        key.n = p.multiply(q);

        // r = phi(n) = phi(p*q) = phi(p) * phi(q) = (p-1) * (q-1);
        BigInteger r =  p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        // 1 < e < r and
        // e and r are mutually prime
        BigInteger e = new BigInteger(BIT_LENGTH-5, CERTAINTY, random);
        while (!BigInteger.ONE.equals(r.gcd(e))) {
            e = new BigInteger(BIT_LENGTH-5, CERTAINTY, random);
        }
        key.e = e;

        // e * d mod n = 2
        key.d = e.modInverse(r);
    }

    public RSA() {
        this(1);
    }

    /**
     * Combine some(1-4) chars to one BigInteger
     */
    private BigInteger combine(String s) {
        BigInteger rst = BigInteger.ZERO;
        for (int i = 0; i < maxComLen && i < s.length(); i++) {
            BigInteger cur = BigInteger.valueOf((int)s.charAt(i));
            rst = rst.multiply(BigInteger.valueOf(MULTIPLE)).add(cur);
        }
        return rst;
    }

    /**
     * separate BigInteger to original string
     */
    private String separate(BigInteger b) {
        StringBuilder rst = new StringBuilder("");
        for (int i = 0; i < maxComLen && !b.equals(BigInteger.ZERO); i++) {
            BigInteger single = b.mod(BigInteger.valueOf(MULTIPLE));
            b = b.divide(BigInteger.valueOf(MULTIPLE));
//            System.out.println(single);
            rst.insert(0, (char) Integer.parseInt(single.toString()));
        }
        return rst.toString();
    }

    private static boolean isPrime(BigInteger number) {
        //check via BigInteger.isProbablePrime(certainty)
        if (!number.isProbablePrime(5))
            return false;

        //check if even
        BigInteger two = new BigInteger("2");
        if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
            return false;

        //find divisor if any from 3 to 'number'
        //start from 3, 5, etc. the odd number, and look for a divisor if any
        for (BigInteger i = new BigInteger("3");
             i.multiply(i).compareTo(number) < 1; i = i.add(two)) {
            //check if 'i' is divisor of 'number'
            if (BigInteger.ZERO.equals(number.mod(i)))
                return false;
        }
        return true;
    }

    // c = m^e mod n
    private BigInteger encodeSingle(BigInteger m) {
        return m.modPow(key.e, key.n);
    }

    // m = c^d mod n
    private BigInteger decodeSingle(BigInteger c) {
        return c.modPow(key.d, key.n);
    }

    public BigInteger[] encode(String str) {
        int len;
        if (str.length() % maxComLen == 0) {
            len = str.length()/maxComLen;
        } else {
            len = str.length()/maxComLen + 1;
        }
        BigInteger[] cs = new BigInteger[len];
        for (int i = 0; i < len; i++) {
            int begIndex = i* maxComLen, endIndex = i* maxComLen + maxComLen;
            String s;
            if (endIndex > str.length()) {
                s = str.substring(begIndex);
            } else {
                s = str.substring(begIndex, endIndex);
            }
            BigInteger c = combine(s);
            if (!BigInteger.ZERO.equals(c)) {
                cs[i] = encodeSingle(c);
            }
        }
        return cs;
    }

    public String decode(BigInteger[] cs) {
        StringBuilder rst = new StringBuilder();
        for (BigInteger c : cs) {
            BigInteger m = decodeSingle(c);
            String s = separate(m);
            rst.append(s);
        }
        return rst.toString();
    }

    public BigInteger getKeyn() {
        return key.n;
    }

    public BigInteger getKeye() {
        return key.e;
    }

    public BigInteger getKeyd() {
        return key.d;
    }

    public void setMaxComLen(int maxComLen) {
        this.maxComLen = maxComLen;
    }

    public int getMaxComLen() {
        return this.maxComLen;
    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
    }
}
