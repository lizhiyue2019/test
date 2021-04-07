package com.example.lib;

import org.omg.Messaging.SyncScopeHelper;

public class MyClass {

    public static void main(String[] args) {
        String str = "1A 5A 00 01 10 01 00 03 01 02 03";
        MyClass myClass = new MyClass();


       // System.out.println( myClass.getXor( getchecksum(str)));


        EnCode enCode = new EnCode();
      //  System.out.println(enCode.getMsgStr(64));


        String room =to16(2) +"01" + to16(2)+"01";
        System.out.println("room"+room);
    }



    public static String to16(int val){

        return String.format("%02X",val);
    }

    public static String getXor(String hexstr) {
        String result = Byte2Hex(getXor(hexStrToByte(hexstr)));
        return result;
    }

    public static String getchecksum(String hexstr) {
        String str = hexstr.replace(" ", "");
        int sum = 0;
        for (int i = 0; i < str.length(); i = i + 2) {
            String x = str.substring(i, i + 2);
            int num = Integer.parseInt(x, 16);
            sum = sum + num;
        }

        String sums = Integer.toHexString(sum);

        String result = sums.substring(sums.length() - 2, sums.length()).toUpperCase();
        return result;
    }

    public static byte[] hexStrToByte(String hexstr) {
        int len = (hexstr.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hexstr.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (((byte) "0123456789ABCDEF".indexOf(achar[pos])) << 4 | ((byte) "0123456789ABCDEF".indexOf(achar[pos + 1])));
        }
        return result;
    }

    //1字节转2个Hex字符
    public static String Byte2Hex(Byte inByte) {
        return String.format("%02x", new Object[]{inByte}).toUpperCase();
    }

    public static byte getXor(byte[] datas) {
        byte temp = datas[0];
        for (int i = 1; i < datas.length; i++) {

            temp ^= datas[i];
        }
        return temp;
    }
}

