/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proj.rsacryptosystem;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
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
        this.p = new BigInteger(bits/2, 100, r);
        this.q = new BigInteger(bits/2, 100, r);
        this.n = this.p.multiply(this.q);
        BigInteger phiN = (this.p.subtract(BigInteger.ONE)).multiply(this.q.subtract(BigInteger.ONE));
        
        boolean found = false;
        do{
            this.b = new BigInteger(bits/2, 100, r);
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
    
//    public String encrypt(String message) {
//        return (new BigInteger(1, message.getBytes())).modPow(b, n).toString(16);
//    }
//
//   
//    //Encrypt the given plaintext message.Use public key decrypt
// 
//    public BigInteger encrypt(BigInteger message) {
//        return message.modPow(b, n);
//    }
//
//  
//     // Decrypt the given ciphertext message.Use private key decrypt
//   
//    public String decrypt(String message) {
//        return new String((new BigInteger(message)).modPow(a, n).toByteArray(), StandardCharsets.UTF_8);
//    }
//
//  
//     // Decrypt the given ciphertext message.Use private key decrypt
// 
//    public BigInteger decrypt(BigInteger message) {
//        return message.modPow(a, n);
//    }
    
    public String encrypt(String message) {
    try {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        BigInteger messageBigInt = new BigInteger(1, messageBytes);
        BigInteger encryptedBigInt = messageBigInt.modPow(b, n);
        return encryptedBigInt.toString(16); // Mã hóa thành chuỗi hex
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

public String decrypt(String encryptedMessage) {
    try {
        BigInteger encryptedBigInt = new BigInteger(encryptedMessage, 16);
//        BigInteger encryptedBigInt = new BigInteger(encryptedMessage);
        BigInteger decryptedBigInt = encryptedBigInt.modPow(a, n);
        byte[] decryptedBytes = decryptedBigInt.toByteArray();
        // Đảm bảo bỏ byte 0 không cần thiết do BigInteger
        if (decryptedBytes[0] == 0) {
            decryptedBytes = Arrays.copyOfRange(decryptedBytes, 1, decryptedBytes.length);
        }
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
    
    
    
}
