package com.example.lib;



public class EnCode {

    private static final String TAG = "EnCode";

    /**
     * 帧头
     */
    private static final String FH = "7E 0A";

    /**
     * 卡号
     */
    private static final String CN = " 11 22 33";

    /**
     * 卡类
     */
    private static final String CT = " 22";

    /**
     * 起止年月
     */
    private static final String DATE = " 01 01 01 99 12 31";

    /**
     * 分隔符1
     */
    private static final String SEPARATOR1 = " AA";

    /**
     * 密码
     */
    private static final String PWD = " 11 22 33";

    /**
     * 分隔符2
     */
    private static final String SEPARATOR2 = " FF FF";

    /**
     * 四个时间段
     */
    private static final String FOUR_DATE = " 00 00 23 59 00 00 23 59 00 00 23 59 00 00 23 59";

    // 40 00 00 00 00 00 00 00
    private static final String BACK = " 00 00 00 00 00 00 00 00";

    /**
     * 异或校验
     */
    private static final String CHECKOUT1 = " 73";

    /**
     * 和校验
     */
    private static final String CHECKOUT2 = " A5";

    /**
     * 帧尾
     */
    private static final String FR = " E7";

    public static String getMsgStr(int floor) {
        StringBuilder sb = new StringBuilder();
        sb.append(FH)
                .append(CN)
                .append(CT)
                .append(DATE)
                .append(SEPARATOR1)
                .append(PWD)
                .append(SEPARATOR2)
                .append(FOUR_DATE)
                .append(getEnCodeFloor(floor))
                .append(BACK);
        String dataArea = sb.toString().substring(6, sb.toString().length()).replace(" ", "");
      //  Log.d(TAG, "dataArea: " + dataArea);
        String xor = getXor(dataArea);
        String checsum = getchecksum(dataArea);

        sb.append(xor)
                .append(checsum)
                .append(FR);
       // Log.d("EnCode", "getMsgStr: " + sb.toString());
        return sb.toString();
    }

    private static String getEnCodeFloor(int floor) {
        String cover = "00";
        int x = floor % 8;
        int y = floor / 8;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 8; i++) {
            if (i == x) {
                sb.append(1);
            } else {
                sb.append(0);
            }
        }

     //   Log.d(TAG, "getEnCodeFloor: 8 :" + sb.toString());

        int keys = Integer.parseInt(sb.toString(), 2);//数字2代表进制，可以是各种进制，转换成10进制
    //    Log.d(TAG, "getEnCodeFloor: 10  :" + keys);
//把10进制keys转成16进制result，toUpperCase()是把小写字母转换成大写字母
        String result = Integer.toHexString(keys).toUpperCase();
   //     Log.d(TAG, "getEnCodeFloor: 16  :" + result);

        if (keys < 16) {
            result = "0" + result;
        }

        StringBuilder sb1 = new StringBuilder();
        for (int j = 0; j < 8; j++) {
            if (j == y) {
                sb1.append(result);
            } else {
                sb1.append(cover);
            }
        }

    //    Log.d(TAG, "getEnCodeFloor.result: 16  :" + sb1.toString());
        return sb1.toString();

    }

    public static String getXor(String hexstr) {
        String result = Byte2Hex(getXor(hexStrToByte(hexstr)));
     //   Log.d(TAG, "getXor: " + result);
        return result;
    }

    public static String getchecksum(String hexstr) {
        String str = hexstr.replace(" ", "");
        int sum = 0;
        for (int i = 0; i < str.length(); i = i + 2) {
            String x = str.substring(i, i + 2);
          //  Log.d(TAG, "getchecksum: i:" + i + "  ,x:" + x);
            int num = Integer.parseInt(x, 16);
            sum = sum + num;
        }

        String sums = Integer.toHexString(sum);

        String result = sums.substring(sums.length() - 2, sums.length()).toUpperCase();
      //  Log.d(TAG, "getchecksum: " + result);
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
