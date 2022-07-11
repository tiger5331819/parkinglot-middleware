package com.yfkyplatform.parkinglotmiddleware.universal;

import cn.hutool.core.lang.Assert;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

import static com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.LifangAES.*;


/**
 * @author Suhuyuan
 */
@SpringBootTest
public class LifangAESTest {

    @Test
    public void initSecretKeyTest() {
        byte[] key = initSecretKey();
        Assert.notNull(key);
        System.out.println("key：" + Base64.encodeBase64String(key));
        System.out.println("key：" + showByteArray(key));
    }

    @ParameterizedTest
    @CsvSource({"9iEepr1twrizIEKrs1hs2A=="})
    public void encryptDataAESTest(String key) throws Exception {
        System.out.println("key:" + showByteArray(Base64.decodeBase64(key)));


        String data = "{\"requestName\":\"BeforeIn\",\"requestValue\":{\"carCode\":\"浙AD0V07\",\"inTime\":\"2016-09-29 10:06:03\",\"inChannelId\":\"4\",\"GUID\":\"1403970b-4eb2-46bc-8f2b-eeec91ddcd5f\",\"inOrOut\":\"0\"},\"Type\":\"0\"}";
        System.out.println("加密前数据: string:" + data);
        System.out.println("加密前数据: byte[]:" + showByteArray(data.getBytes(StandardCharsets.UTF_8)));
        System.out.println();

        byte[] encryptData = encrypt(data.getBytes(StandardCharsets.UTF_8), key, null);
        String encryptStr = parseByte2HexStr(encryptData);

        System.out.println("加密后数据: byte[]:" + showByteArray(encryptData));
        System.out.println("加密后数据: Byte2HexStr:" + encryptStr);
        System.out.println();
    }

    @ParameterizedTest
    @CsvSource({"9iEepr1twrizIEKrs1hs2A==,96C7F3AA5346EDC06B5D8B5453ED6E9A2BF0B63A8733382D622C1F7113A2EE031570CEB018B3A27EABCC872542744C7B986EE1E719BF177D205566DE1D5BD6A603E61F51BBAC06572D6E02FEAD1E559AB05F8883F87CDFF8A1547C21DB17561CCCB9878A30939EB991983578A1C021CBD85D442AC55B801C9B89910A2FC5F0A4D88BF6EB9756E41D8ED8DCC93893C29763BD94A1EF5A826DC3F5F82FBE8941604E9C628250B633ADD0B51649080271C393EF38A68DD89725D8CB21CE5CAE9D4D"})
    public void decryptDataAESTest(String key, String encryptStr) throws Exception {
        System.out.println("key:" + showByteArray(Base64.decodeBase64(key)));

        byte[] decryptData = decrypt(parseHexStr2Byte(encryptStr), key, null);
        System.out.println("解密后数据: byte[]:" + showByteArray(decryptData));
        System.out.println("解密后数据: string:" + new String(decryptData, StandardCharsets.UTF_8));
    }
}
