package com.burpsuitcrack.burploaderkeygen;

import javax.crypto.spec.*;
import javax.crypto.*;
import java.util.*;
import java.nio.charset.*;
import java.security.interfaces.*;
import java.math.*;
import java.security.*;
import java.security.spec.*;

public class Filter
{
    private static byte[] encryption_key;
    
    private static byte[] decrypt(final byte[] data) {
        try {
            final SecretKeySpec spec = new SecretKeySpec(Filter.encryption_key, "DES");
            final Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, spec);
            return cipher.doFinal(data);
        }
        catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3);
        }
    }
    
    public static void BurpFilter(final Object[] obj) {
        final byte[] data = (byte[])obj[0];
        final byte[] decode = Base64.getDecoder().decode(data);
        final byte[] decrypt = decrypt(decode);
        final String str = new String(decrypt);
        final String[] strs = str.split("\u0000");
        obj[0] = Arrays.copyOf(strs, strs.length - 2);
    }
    
    public static byte[] BountyFilter(final String url, final byte[] data) throws NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        if (url.equals("https://api.licensespring.com/api/v4/activate_license")) {
            final String doSign = new String(data);
            String json = getText(doSign, "hardware_id") + "#" + getText(doSign, "license_key") + "#2099-12-31t14:58:33.213z";
            json = "{\"license_signature\":\"" + getsign(json.toLowerCase()) + "\",\"license_type\":\"perpetual\",\"is_trial\":false,\"validity_period\":\"2099-12-31T20:28:33.213+05:30\",\"max_activations\":99,\"times_activated\":99,\"transfer_count\":99,\"prevent_vm\":false,\"customer\":{\"email\":\"taiwan@china.cn\",\"first_name\":\"taiwan\",\"last_name\":\"china\",\"company_name\":\"alibaba\",\"phone\":\"+86\",\"reference\":\"love\"},\"product_details\":{\"product_name\":\"Burp Bounty Pro\",\"short_code\":\"burpbountyprostripe\",\"allow_trial\":false,\"trial_days\":0,\"authorization_method\":\"license-key\"},\"allow_overages\":false,\"max_overages\":0,\"is_floating_cloud\":false,\"floating_users\":0,\"floating_timeout\":0}\n";
            return json.getBytes();
        }
        if (url.equals("https://api.licensespring.com/api/v4/product_details?product=burpbountyprostripe")) {
            final String doSign = "{\"product_name\":\"Burp Bounty Pro\",\"short_code\":\"burpbountyprostripe\",\"allow_trial\":false,\"trial_days\":0,\"authorization_method\":\"license-key\"}";
            return doSign.getBytes();
        }
        if (url.startsWith("https://api.licensespring.com/api/v4/check_license?app_name=Burp")) {
            final String doSign = getparam(url, "hardware_id") + "#" + getparam(url, "license_key") + "#2099-12-31t14:58:33.213z";
            final String json = "{\"license_signature\":\"" + getsign(doSign.toLowerCase()) + "\",\"license_type\":\"perpetual\",\"is_trial\":false,\"validity_period\":\"2099-12-31T20:28:33.213+05:30\",\"max_activations\":0,\"times_activated\":0,\"transfer_count\":0,\"prevent_vm\":false,\"customer\":{\"email\":\"taiwan@china.cn\",\"first_name\":\"taiwan\",\"last_name\":\"china\",\"company_name\":\"alibaba\",\"phone\":\"+86\",\"reference\":\"love\"},\"product_details\":{\"product_name\":\"Burp Bounty Pro\",\"short_code\":\"burpbountyprostripe\",\"allow_trial\":false,\"trial_days\":0,\"authorization_method\":\"license-key\"},\"allow_overages\":false,\"max_overages\":0,\"is_floating_cloud\":false,\"floating_users\":0,\"floating_timeout\":0,\"license_active\":true,\"license_enabled\":true,\"is_expired\":false}";
            return json.getBytes();
        }
        return null;
    }
    
    public static String getText(final String json, final String label) {
        final String before = "\"" + label + "\":\"";
        final String after = "\"";
        int start = json.indexOf(before);
        if (start != -1) {
            start += before.length();
            final int end = json.indexOf(after, start);
            return json.substring(start, end);
        }
        return null;
    }
    
    public static String getparam(final String url, final String label) {
        final String before = label + "=";
        final String[] split;
        final String[] strs = split = url.substring(url.indexOf(63) + 1).split("&");
        for (final String str : split) {
            if (str.startsWith(before)) {
                return str.substring(before.length());
            }
        }
        return null;
    }
    
    public static String getsign(final String dover) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        final String n = "772466485993532616265602004787593990571998354762769517556824947462364733846988896764791786418284048308408035898123800088385145599914482866982121337242267111996730589833008965586904619624210600143815034555107626281306790690247380277401223658213686836260051740513369525229945146545746232288644834011082272373923840674183841152721282934896147636659685196110193170859036230613942473057350558696079781329724723538612065451271117946653171891463701206005132474249143377280956637274812017722966962038177658826682928177419967120615723412715280627140352666513761887655683881127941062521608622150057375302773817128043341551104335925307524362463158532014345920401529131689972511711145366384313357331219918007902162272947810092434401421074436297130543420857679173250879221703239461167245128718027210585470097741338476167664231386224393829173753597329684981549442031004886309176804274536524421269896699613148427017011648805968328213879561704664823249805341430850708344955922886822191380206543393525391519722222586339612770531074577303349625790208047047035933996137831086461496793235410445769659982355567180073509405077094784975713575937671560399734544873967339281900443458904052345698752817504449813508664835247206727493828740500751727305948870197";
        final String d = "174973907633458835290948040970319556739571777415098547204343597434713283701703620435377482481337667228257584157157755349071173328512603539380038623241244720960548478051650488947275570720683070008314908951745925387887747498309693153760794134705314266510222745745471574866694168187002804802248082165715799218623058956135604649467437404344619248153150536891463106663448019339670354342377115886343658602617811632065796597709992003266343237084069218962512650567290743179208710809841530785624068105905783683752812438680431083289445871214098004331881766550144120454836614665674124130388635363498508268148943577914817665229458767012826384170293442481353885513942286386871422229921740675582649721394004737713146579092336428734122223073166081868987285302826609579340630764882890999436799814488695761863240374337775451144202336101045005062769951606330931316728620859403340949196048544016831331215882019606910236181120045619447482208762438586388152397180820131522205873078063910211185803468590525972907163613199413004903986557077623758471248380271780715344796370488885220077075150724843009236460156571650622249128893865976236126703679474153844433204143676260363864471833141144466790653534160506805002383442253160746350344972938442854944399195933";
        final Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(getPriKeyByND(n, d));
        final byte[] data = dover.getBytes(StandardCharsets.UTF_8);
        sign.update(data);
        final byte[] signature = sign.sign();
        return Base64.getEncoder().encodeToString(signature);
    }
    
    public static RSAPrivateKey getPriKeyByND(final String n, final String d) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final RSAPrivateKeySpec spec = new RSAPrivateKeySpec(new BigInteger(n), new BigInteger(d));
        final KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey)kf.generatePrivate(spec);
    }
    
    static {
        Filter.encryption_key = "burpr0x!".getBytes();
    }
}
