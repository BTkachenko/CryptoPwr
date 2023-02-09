package primenum;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;


class Pair
{
    //N = pq
    //d = e^(-1) mod φ(N) (gdzie e = 65537)
    //dp = d mod (p-1)
    //dq = d mod (q-1)
    //qi = q^(-1) mod p
    BigInteger e = new BigInteger("65537");
    BigInteger p;
    BigInteger q;
    BigInteger N;
    BigInteger d;
    BigInteger dp;
    BigInteger dq;
    BigInteger qi;
    BigInteger phiN;
    Pair(BigInteger p,BigInteger q)
    {
        this.p = p;
        this.q = q;
        N = p.multiply(q);
        phiN = EulerFunc(p,q);
        d = e.modInverse(phiN);
        dp = d.mod(p.subtract(BigInteger.ONE));
        dq = d.mod(q.subtract(BigInteger.ONE));
        qi = q.modInverse(p);
    }


    public  BigInteger  encrypt(BigInteger x)
    {
        BigInteger result;
        result = x.modPow(e,N);
        return result;
    }

    public  BigInteger  decryptionNONCRT(BigInteger x)
    {
        BigInteger result;
        result = x.modPow(d,N);
        return result;
    }

    public  BigInteger  decryptionCRT(BigInteger c)
    {
        BigInteger result;
        BigInteger m1 = c.modPow(dp,p);
        BigInteger m2 = c.modPow(dq,q);
        BigInteger h = qi.multiply((m1.subtract(m2))).mod(p);
        result = m2.add(h.multiply(q));
        return result;
    }
    public BigInteger EulerFunc(BigInteger p,BigInteger q)
    {
        return p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE)));
    }
}



public class RSA2 {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("128.txt");
        Scanner sc = new Scanner(fis);    //file to be scanned
        int counter = 0;
        Pair[] pairs = new Pair[1000];
        while (counter < 1000) {
            pairs[counter] = new Pair(new BigInteger(sc.nextLine()), new BigInteger(sc.nextLine()));
            counter++;
        }



        long startTime;
        long diffTime;
        long endTime;


        BigInteger randomNumber;
        System.out.println("NONCRT decryption:");
        startTime = System.nanoTime();
        for (Pair p: pairs) {

            randomNumber = new BigInteger(p.N.bitLength()-1, new Random());


        BigInteger decrypt = p.decryptionNONCRT(randomNumber);
        }
        endTime = System.nanoTime();
        diffTime = endTime - startTime;
        System.out.println("avgTime " + diffTime+"ns");
        System.out.println("avgTime " + (diffTime/1000000) +"msec");

        System.out.println("CRT decryption:");

        startTime = System.nanoTime();
        for (Pair p: pairs) {


            randomNumber = new BigInteger(p.N.bitLength()-1, new Random());


            BigInteger decrypt = p.decryptionCRT(randomNumber);
         }
         endTime = System.nanoTime();
         diffTime = endTime - startTime;
        System.out.println("Time " + diffTime+"ns");
        System.out.println("avgTime " + (diffTime/1000000) +"msec");


    }

}
