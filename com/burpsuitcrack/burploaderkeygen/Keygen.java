package com.burpsuitcrack.burploaderkeygen;

import java.io.*;
import java.security.interfaces.*;
import java.math.*;
import java.security.spec.*;
import java.util.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;

public class Keygen
{
    private static final String privateKey2048 = "30820238020100300D06092A864886F70D0101010500048202223082021E02010002820101009D9DF9EB49890DE8F193C89598584BC947BA83727B2D89AA8BE3A4689130FE2E948967D40B656762F9989E59C9655E28E33FD4B4A544126FDD90A566BB61C2D7C74A6829265767B56E28FD2214D4BEB3B1DA4722BC394E2E6AFA0F1689FA9DB442643DDDA84997C5AD15B57EE5BD1A357CABF6ED4CAAA5FB8872E07C8F5FAE1C573C1214DD273C6D8887D7E993208D75118CC2305D60AA337B0999B69988322A8FAA9FBFF49AB70B71723E1CBD79D12640AF19E6FBC28C05E6630414DBAD9AEF912D0AC53E40B7F48EE29BFE1DEFCFB0BDB1B6C5BF8B06DCCA15FA1FC3F468952D481070C92C386D3CE6187B062038A6CA822D352ECEBEAC195918F9BB5C3AC3020100028201005DAD71C754BA3F692E835E1903259F4D6EF33C82C3110A9C316E47DDDA455B1D062D306787AA6A2B1A1B8A29E517F941A5E6DF1DCA87CDC96CCF366EFB799C1B31185915F3F2C8F1BD1A61706B1F1284AC7506087004432235748F991EC2B40E59D3482DC08294D0E9115900A5BCA1A21E89FA45896677262B2FD39A54805273162D655F1AB4392CE4E01A4DD63F7EF387B79D53B73BBE45EA7D9BE64A627CFB3DAE2843E85ED3697672BD4832F5EEB4C18C4D15FEB550E0B5A7018A3CD39A9FD4BDA35A6F88BD00CCBC787419AD57C54FA823EC3D7662710B03C2622E9E2DE546B21CA1C76672B1CC6BD92871A0F96051E31CB060E0DDB4022BEB2897A88761020100020100020100020100020100";
    private static final String privateKey1024 = "30820136020100300D06092A864886F70D0101010500048201203082011C020100028181008D187233EB87AB60DB5BAE8453A7DE035428EB177EC8C60341CAB4CF487052751CA8AFF226EA3E98F0CEEF8AAE12E3716B8A20A24BDE20703865C9DBD9543F92EA6495763DFD6F7507B8607F2A14F52694BB9793FE12D3D9C5D1C0045262EA5E7FA782ED42568C6B7E31019FFFABAEFB79D327A4A7ACBD4D547ACB2DC9CD04030201000281807172A188DBAD977FE680BE3EC9E0E4E33A4D385208F0383EB02CE3DAF33CD520332DF362BA2588B58292710AC9D2882C4F329DF0C11DD66944FF9B21F98A031ED27C19FE2BCF8A09AD3E254A0FD7AB89E0D1E756BCF37ED24D42D1977EA7C1C78ABF4D13F752AE48B426A2DC98C5D13B2313609FAA6441E835DC61D17A01D1A9020100020100020100020100020100";
    private static final byte[] encryption_key;
    
    public static String generateActivation(final String activationRequest) {
        final ArrayList<String> request = decodeActivationRequest(activationRequest);
        if (request == null) {
            return "Error decoding activation request :-(";
        }
        final ArrayList<String> al = new ArrayList<String>();
        al.add("0.4315672535134567");
        al.add(request.get(0));
        al.add("activation");
        al.add(request.get(1));
        al.add("True");
        al.add("");
        al.add(request.get(2));
        al.add(request.get(3));
        al.add(getSign("30820238020100300D06092A864886F70D0101010500048202223082021E02010002820101009D9DF9EB49890DE8F193C89598584BC947BA83727B2D89AA8BE3A4689130FE2E948967D40B656762F9989E59C9655E28E33FD4B4A544126FDD90A566BB61C2D7C74A6829265767B56E28FD2214D4BEB3B1DA4722BC394E2E6AFA0F1689FA9DB442643DDDA84997C5AD15B57EE5BD1A357CABF6ED4CAAA5FB8872E07C8F5FAE1C573C1214DD273C6D8887D7E993208D75118CC2305D60AA337B0999B69988322A8FAA9FBFF49AB70B71723E1CBD79D12640AF19E6FBC28C05E6630414DBAD9AEF912D0AC53E40B7F48EE29BFE1DEFCFB0BDB1B6C5BF8B06DCCA15FA1FC3F468952D481070C92C386D3CE6187B062038A6CA822D352ECEBEAC195918F9BB5C3AC3020100028201005DAD71C754BA3F692E835E1903259F4D6EF33C82C3110A9C316E47DDDA455B1D062D306787AA6A2B1A1B8A29E517F941A5E6DF1DCA87CDC96CCF366EFB799C1B31185915F3F2C8F1BD1A61706B1F1284AC7506087004432235748F991EC2B40E59D3482DC08294D0E9115900A5BCA1A21E89FA45896677262B2FD39A54805273162D655F1AB4392CE4E01A4DD63F7EF387B79D53B73BBE45EA7D9BE64A627CFB3DAE2843E85ED3697672BD4832F5EEB4C18C4D15FEB550E0B5A7018A3CD39A9FD4BDA35A6F88BD00CCBC787419AD57C54FA823EC3D7662710B03C2622E9E2DE546B21CA1C76672B1CC6BD92871A0F96051E31CB060E0DDB4022BEB2897A88761020100020100020100020100020100", getSignatureBytes(al), "SHA256withRSA"));
        al.add(getSign("30820136020100300D06092A864886F70D0101010500048201203082011C020100028181008D187233EB87AB60DB5BAE8453A7DE035428EB177EC8C60341CAB4CF487052751CA8AFF226EA3E98F0CEEF8AAE12E3716B8A20A24BDE20703865C9DBD9543F92EA6495763DFD6F7507B8607F2A14F52694BB9793FE12D3D9C5D1C0045262EA5E7FA782ED42568C6B7E31019FFFABAEFB79D327A4A7ACBD4D547ACB2DC9CD04030201000281807172A188DBAD977FE680BE3EC9E0E4E33A4D385208F0383EB02CE3DAF33CD520332DF362BA2588B58292710AC9D2882C4F329DF0C11DD66944FF9B21F98A031ED27C19FE2BCF8A09AD3E254A0FD7AB89E0D1E756BCF37ED24D42D1977EA7C1C78ABF4D13F752AE48B426A2DC98C5D13B2313609FAA6441E835DC61D17A01D1A9020100020100020100020100020100", getSignatureBytes(al), "SHA1withRSA"));
        return prepareArray(al);
    }
    
    private static byte[] getSignatureBytes(final List<String> list) {
        try {
            final ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            for (final String s : list) {
                byteArray.write(s.getBytes());
                byteArray.write(0);
            }
            return byteArray.toByteArray();
        }
        catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3);
        }
    }
    
    private static String getSign(final String pri, final byte[] data, final String method) {
        try {
            final Signature sign = Signature.getInstance(method);
            sign.initSign(getPriKeyByHex(pri));
            sign.update(data);
            final byte[] signature = sign.sign();
            return Base64.getEncoder().encodeToString(signature);
        }
        catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }
    
    public static RSAPrivateKey getPriKeyByHex(final String hexStr) {
        final BigInteger hex = new BigInteger(hexStr, 16);
        final byte[] priData = hex.toByteArray();
        return getPriKeyByBytes(priData);
    }
    
    public static RSAPrivateKey getPriKeyByBytes(final byte[] priData) {
        try {
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priData);
            return (RSAPrivateKey)keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex2) {
            final GeneralSecurityException ex;
            final GeneralSecurityException var3 = ex;
            var3.printStackTrace();
            return null;
        }
    }
    
    public static String generateLicense(final String licenseName) {
        final ArrayList<String> al = new ArrayList<String>();
        al.add(getRandomString());
        al.add("license");
        al.add(licenseName);
        al.add("4102415999000");
        al.add("1");
        al.add("full");
        al.add(getSign("30820238020100300D06092A864886F70D0101010500048202223082021E02010002820101009D9DF9EB49890DE8F193C89598584BC947BA83727B2D89AA8BE3A4689130FE2E948967D40B656762F9989E59C9655E28E33FD4B4A544126FDD90A566BB61C2D7C74A6829265767B56E28FD2214D4BEB3B1DA4722BC394E2E6AFA0F1689FA9DB442643DDDA84997C5AD15B57EE5BD1A357CABF6ED4CAAA5FB8872E07C8F5FAE1C573C1214DD273C6D8887D7E993208D75118CC2305D60AA337B0999B69988322A8FAA9FBFF49AB70B71723E1CBD79D12640AF19E6FBC28C05E6630414DBAD9AEF912D0AC53E40B7F48EE29BFE1DEFCFB0BDB1B6C5BF8B06DCCA15FA1FC3F468952D481070C92C386D3CE6187B062038A6CA822D352ECEBEAC195918F9BB5C3AC3020100028201005DAD71C754BA3F692E835E1903259F4D6EF33C82C3110A9C316E47DDDA455B1D062D306787AA6A2B1A1B8A29E517F941A5E6DF1DCA87CDC96CCF366EFB799C1B31185915F3F2C8F1BD1A61706B1F1284AC7506087004432235748F991EC2B40E59D3482DC08294D0E9115900A5BCA1A21E89FA45896677262B2FD39A54805273162D655F1AB4392CE4E01A4DD63F7EF387B79D53B73BBE45EA7D9BE64A627CFB3DAE2843E85ED3697672BD4832F5EEB4C18C4D15FEB550E0B5A7018A3CD39A9FD4BDA35A6F88BD00CCBC787419AD57C54FA823EC3D7662710B03C2622E9E2DE546B21CA1C76672B1CC6BD92871A0F96051E31CB060E0DDB4022BEB2897A88761020100020100020100020100020100", getSignatureBytes(al), "SHA256withRSA"));
        al.add(getSign("30820136020100300D06092A864886F70D0101010500048201203082011C020100028181008D187233EB87AB60DB5BAE8453A7DE035428EB177EC8C60341CAB4CF487052751CA8AFF226EA3E98F0CEEF8AAE12E3716B8A20A24BDE20703865C9DBD9543F92EA6495763DFD6F7507B8607F2A14F52694BB9793FE12D3D9C5D1C0045262EA5E7FA782ED42568C6B7E31019FFFABAEFB79D327A4A7ACBD4D547ACB2DC9CD04030201000281807172A188DBAD977FE680BE3EC9E0E4E33A4D385208F0383EB02CE3DAF33CD520332DF362BA2588B58292710AC9D2882C4F329DF0C11DD66944FF9B21F98A031ED27C19FE2BCF8A09AD3E254A0FD7AB89E0D1E756BCF37ED24D42D1977EA7C1C78ABF4D13F752AE48B426A2DC98C5D13B2313609FAA6441E835DC61D17A01D1A9020100020100020100020100020100", getSignatureBytes(al), "SHA1withRSA"));
        return prepareArray(al);
    }
    
    private static String getRandomString() {
        final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        final StringBuilder str = new StringBuilder();
        final Random rnd = new Random();
        while (str.length() < 32) {
            final int index = (int)(rnd.nextFloat() * CHARS.length());
            str.append(CHARS.charAt(index));
        }
        return str.toString();
    }
    
    private static String prepareArray(final ArrayList<String> list) {
        try {
            final ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            for (int i = 0; i < list.size() - 1; ++i) {
                byteArray.write(list.get(i).getBytes());
                byteArray.write(0);
            }
            byteArray.write(list.get(list.size() - 1).getBytes());
            return new String(Base64.getEncoder().encode(encrypt(byteArray.toByteArray())));
        }
        catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3);
        }
    }
    
    private static byte[] encrypt(final byte[] arrayOfByte) {
        try {
            final SecretKeySpec localSecretKeySpec = new SecretKeySpec(Keygen.encryption_key, "DES");
            final Cipher localCipher = Cipher.getInstance("DES");
            localCipher.init(1, localSecretKeySpec);
            return localCipher.doFinal(arrayOfByte);
        }
        catch (Exception var4) {
            var4.printStackTrace();
            throw new RuntimeException(var4);
        }
    }
    
    private static ArrayList<String> decodeActivationRequest(final String activationRequest) {
        try {
            final ArrayList<String> ar = getParamsList(activationRequest);
            if (ar.size() != 5) {
                return null;
            }
            return ar;
        }
        catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }
    
    private static ArrayList<String> getParamsList(final String data) {
        final byte[] rawBytes = decrypt(Base64.getDecoder().decode(data));
        final ArrayList<String> ar = new ArrayList<String>();
        int from = 0;
        for (int i = 0; i < rawBytes.length; ++i) {
            if (rawBytes[i] == 0) {
                ar.add(new String(rawBytes, from, i - from));
                from = i + 1;
            }
        }
        ar.add(new String(rawBytes, from, rawBytes.length - from));
        return ar;
    }
    
    private static byte[] decrypt(final byte[] arrayOfByte) {
        try {
            final SecretKeySpec localSecretKeySpec = new SecretKeySpec(Keygen.encryption_key, "DES");
            final Cipher localCipher = Cipher.getInstance("DES");
            localCipher.init(2, localSecretKeySpec);
            return localCipher.doFinal(arrayOfByte);
        }
        catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3);
        }
    }
    
    static {
        encryption_key = "burpr0x!".getBytes();
    }
}