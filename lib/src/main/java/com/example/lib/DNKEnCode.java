package com.example.lib;


public class DNKEnCode {

    private static final String TAG = "EnCode";

    /**
     * 帧头
     */
    private static final String FH = "1A5A";

    /**
     * 源地址
     */
    private static final String LOCAL  = "00";

    /**
     * 目的地址
     */
    private static final String TARGET = "FF";

    /**
     * 命令码
     */
    private static final String COMMAND = "0402";

    /**
     * 长度
     */
    private static final String LENGTH = "0005";


    private static final String UP = "01";

    /**
     * roomid
     */
    private static final String ROOMID = "01";

    public static String getMsgStr(int floor) {
        String room =tenTo16(floor) + ROOMID +tenTo16(floor)+ROOMID;
        StringBuilder sb = new StringBuilder();
        sb.append(FH)
                .append(LOCAL)
                .append(TARGET)
                .append(COMMAND)
                .append(LENGTH)
                .append(room)
                .append(UP);
        String dataArea = sb.toString();
        String xor = getXor(dataArea);
        sb.append(xor);
        return sb.toString();
    }

    public static String tenTo16(int val){
        return String.format("%02X",val).toUpperCase();
    }

    public static String getXor(String hexstr) {
        System.out.println("hexstr>>>>"+hexstr);
        String result = Byte2Hex(getXor(hexStrToByte(hexstr)));
        //Log.d(TAG, "getXor: " + result);
        System.out.println("getXor: " + result);
        return result;
    }

    //1字节转2个Hex字符
    public static String Byte2Hex(Byte inByte) {
        return String.format("%02x", new Object[]{inByte}).toUpperCase();
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

    public static byte getXor(byte[] datas) {
        byte temp = datas[0];
        for (int i = 1; i < datas.length; i++) {
            System.out.println("temp>>>>"+temp);
            temp ^= datas[i];
        }
        return temp;
    }

    public static void main(String[] args) {
       String code =  DNKEnCode.getMsgStr(10);
       System.out.println("code>>>>"+code);
    }
}