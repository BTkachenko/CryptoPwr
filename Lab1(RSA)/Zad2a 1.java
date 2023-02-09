import java.math.BigInteger;

public class Zad2a {
    public static BigInteger encrypt(BigInteger x, BigInteger n, BigInteger e) {
        BigInteger result;
        result = x.modPow(e, n);
        return result;
    }


    public static void main(String[] args) {
        if (args.length == 3) {
            BigInteger x = new BigInteger(args[0]);
            BigInteger n = new BigInteger(args[1]);
            BigInteger e = new BigInteger(args[2]);

            BigInteger cryptogram = encrypt(x, n, e);
            System.out.println(cryptogram);

        }
        else
        {System.out.println("Wrong number of arguments!");
            System.exit(-1);}
    }
}
