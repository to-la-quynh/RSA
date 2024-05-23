/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proj.rsacryptosystem;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JTextField;

/**
 *
 * @author caoth
 */
public class AlgorithmRSA {
    private BigInteger p, q, n, a, b;

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getA() {
        return a;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public BigInteger getB() {
        return b;
    }

    public void setB(BigInteger b) {
        this.b = b;
    }

    public AlgorithmRSA() {
    }

    public AlgorithmRSA(BigInteger p, BigInteger q, BigInteger n, BigInteger a, BigInteger b) {
        this.p = p;
        this.q = q;
        this.n = n;
        this.a = a;
        this.b = b;
    }

    public void taoKhoa(int bits){
        SecureRandom r = new SecureRandom(); //create BigInteger r random
        this.p = new BigInteger(bits, 100, r);
        this.q = new BigInteger(bits, 100, r);
        this.n = this.p.multiply(this.q);
        BigInteger phiN = (this.p.subtract(BigInteger.ONE)).multiply(this.q.subtract(BigInteger.ONE));
        
        boolean found = false;
        do{
            this.b = new BigInteger(bits, 100, r);
            if(phiN.gcd(this.b).equals(BigInteger.ONE) && this.b.compareTo(phiN) < 0){
                found = true;
            }
        }while(!found);
        this.a = this.b.modInverse(phiN);
    }
    
    public void taoKhoa(BigInteger p, BigInteger q, BigInteger b){
        this.p = p;
        this.q = q;
        this.b = b;
        this.n = this.p.multiply(this.q);
        BigInteger phiN = (this.p.subtract(BigInteger.ONE)).multiply(this.q.subtract(BigInteger.ONE));
        this.a = this.b.modInverse(phiN);
    }
    
    public String encrypt(String plaintText){
        return (new BigInteger(plaintText.getBytes())).modPow(this.b, this.n).toString();
    }
    
    public BigInteger encrypt(BigInteger plaintText){
        return plaintText.modPow(this.b, this.n);
    }
    
    public String decrypt(String cipherText){
        return new String((new BigInteger(cipherText)).modPow(this.a, this.n).toByteArray());
    }
    
    public BigInteger decrypt(BigInteger cipherText){
        return cipherText.modPow(this.a, this.n);
    }
    
    
}
