

import java.math.BigInteger;

public class Zad2b {
    public static BigInteger decryptionCRT(BigInteger c, BigInteger p, BigInteger q, BigInteger dp, BigInteger dq, BigInteger qInv)
    {
        BigInteger result;
        BigInteger m1 = c.modPow(dp,p);
        BigInteger m2 = c.modPow(dq,q);
        BigInteger h = qInv.multiply((m1.subtract(m2))).mod(p);
        result = m2.add(h.multiply(q));
        return result;
    }

    public static void main(String[] args) {
         if (args.length == 6)
        {
            BigInteger c = new BigInteger(args[0]);
            BigInteger p= new BigInteger(args[1]);
            BigInteger q= new BigInteger(args[2]);
            BigInteger dp= new BigInteger(args[3]);
            BigInteger dq= new BigInteger(args[4]);
            BigInteger qInv= new BigInteger(args[5]);
            System.out.println(decryptionCRT(c,p,q,dp,dq,qInv));
        }
        else
        {System.out.println("Wrong number of arguments!");
            System.exit(-1);}
    }
}
