/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkt.client.swingexp.app.virtualkey.text;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author longnt
 */
public class VietNameseUnicode {

    private static VietNameseUnicode vietname;
    /**index này làm cái j đây*/
    public static int index;

    public static VietNameseUnicode getInstance() {
        if (vietname == null) {
            vietname = new VietNameseUnicode();
        }
        return vietname;
    }

    public VietNameseUnicode() {
    }

    public String conver(String str, JTextField textField, int caretPosition) {
        String name = str;
        String a = "";
        String k;
        int caret = caretPosition;
        int textFieldLength = textField.getText().length();
        if (textFieldLength > 0) {
            String subTextField = textField.getText().substring(0, caretPosition);
            int subTextFieldLength = subTextField.length();
//            String[] arrStr = textField.getText().split(" ");
            String[] arrStr = subTextField.split(" ");
            /**ở đây */
//            System.out.println("arrStr.length: " + arrStr.length);
            if (arrStr.length == 0) {
                return str;
            } else {
                k = arrStr[arrStr.length - 1];
            }
//            System.out.println("k là gi: " + k);
            /**Thêm vào khoảng trắng thì ko làm j*/
            if (subTextFieldLength == 0) {
                return str;
            } else {
                if (subTextField.substring(subTextFieldLength - 1).equals(" ")) {
                    return str;
                }
            }
            for (int i = 0; i < k.length(); i++) {
                if (k.contains("d")) {
                    a = "d";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them d: " + index);
                        }
                    }
                }
                if (k.contains("ă")) {
                    a = "ă";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them ă: " + index);
                        }
                    }
                }
                if (k.contains("â")) {
                    a = "â";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them â: " + index);
                        }
                    }
                }
                if (k.contains("i")) {
                    a = "i";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them i: " + index);
                        }
                    }
                }
                if (k.contains("y")) {
                    a = "y";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them y: " + index);
                        }
                    }
                }
                if (k.contains("D")) {
                    a = "D";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them D: " + index);
                        }
                    }
                }

                if (k.contains("Ă")) {
                    a = "Ă";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Ă: " + index);
                        }
                    }
                }
                if (k.contains("Â")) {
                    a = "Â";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Â: " + index);
                        }
                    }
                }

                if (k.contains("I")) {
                    a = "I";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them I: " + index);
                        }
                    }
                }
                if (k.contains("Y")) {
                    a = "Y";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Y: " + index);
                        }
                    }
                }
                if (k.contains("U")) {
                    a = "U";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them U: " + index);
                        }
                    }
                }
                if (k.contains("u")) {
                    a = "u";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them u: " + index);
                        }
                    }
                }
                if (k.contains("Ô")) {
                    a = "Ô";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                        System.out.println("them Ô: " + index);
                        };
                    }
                }
                if (k.contains("ô")) {
                    a = "ô";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them ô: " + index);
                        }

                    }
                }
                if (k.contains("E")) {
                    a = "E";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Ê: " + index);
                        }
                    }
                }
                if (k.contains("e")) {
                    a = "e";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them e: " + index);
                        }
                    }
                }

                if (k.contains("Ê")) {
                    a = "Ê";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Ê: " + index);
                        }
                    }
                }
                if (k.contains("ê")) {
                    a = "ê";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them ê: " + index);
                        }
                    }
                }
                if (k.contains("Ư")) {
                    a = "Ư";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Ư: " + index);
                        }
                    }
                }
                if (k.contains("ư")) {
                    a = "ư";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them ư: " + index);
                        }
                    }
                }
                if (k.contains("O")) {
                    a = "O";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them O: " + index);
                        }
                    }
                }
                if (k.contains("o")) {
                    a = "o";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them o: " + index);
                        }
                    }
                }
                if (k.contains("Ơ")) {
                    a = "Ơ";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Ơ: " + index);
                        }
                    }
                }
                if (k.contains("ơ")) {
                    a = "ơ";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them ơ: " + index);
                        }
                    }
                }
                if (k.contains("A")) {
                    a = "A";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them A: " + index);
                        }
                    }
                }
                /**Để lấy ra index ở vị trí đó*/
                if (k.contains("a")) {
                    a = "a";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them a: " + index);
                        }
                    }
                }

                //------------------------  f  ------------------------
                if (str.equals("f")) {
                    //------------------------  à  ------------------------
                    if (k.contains("á")) {
                        a = "á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ả")) {
                        a = "ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ã")) {
                        a = "ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ạ")) {
                        a = "ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  ằ  ------------------------
                    if (k.contains("ắ")) {
                        a = "ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẳ")) {
                        a = "ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẵ")) {
                        a = "ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ặ")) {
                        a = "ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  ầ  ------------------------
                    if (k.contains("ấ")) {
                        a = "ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẩ")) {
                        a = "ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẫ")) {
                        a = "ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ậ")) {
                        a = "ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  è  ------------------------
                    if (k.contains("é")) {
                        a = "é";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẻ")) {
                        a = "ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẽ")) {
                        a = "ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẹ")) {
                        a = "ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  ề  ------------------------
                    if (k.contains("ế")) {
                        a = "ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ể")) {
                        a = "ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ễ")) {
                        a = "ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ệ")) {
                        a = "ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  ò  ------------------------
                    if (k.contains("ó")) {
                        a = "ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỏ")) {
                        a = "ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("õ")) {
                        a = "õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ọ")) {
                        a = "ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //------------------------  ồ  ------------------------
                    if (k.contains("ố")) {
                        a = "ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ổ")) {
                        a = "ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỗ")) {
                        a = "ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ộ")) {
                        a = "ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  ờ  ------------------------
                    if (k.contains("ớ")) {
                        a = "ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ở")) {
                        a = "ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỡ")) {
                        a = "ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ợ")) {
                        a = "ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //--------------------  ù  --------------------
                    if (k.contains("ú")) {
                        a = "ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ủ")) {
                        a = "ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ũ")) {
                        a = "ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ụ")) {
                        a = "ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //--------------------  ừ  --------------------
                    if (k.contains("ứ")) {
                        a = "ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ử")) {
                        a = "ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ữ")) {
                        a = "ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ự")) {
                        a = "ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ì  --------------------
                    if (k.contains("í")) {
                        a = "í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỉ")) {
                        a = "ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ĩ")) {
                        a = "ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ị")) {
                        a = "ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ỳ  --------------------
                    if (k.contains("ý")) {
                        a = "ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỷ")) {
                        a = "ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỹ")) {
                        a = "ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỵ")) {
                        a = "ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //--------------------  dấu F  --------------------
                if (str.equals("F")) {
                    //--------------------  À  --------------------
                    if (k.contains("Á")) {
                        a = "Á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ả")) {
                        a = "Ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ã")) {
                        a = "Ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ạ")) {
                        a = "Ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //--------------------  Ầ  --------------------
                    if (k.contains("Ấ")) {
                        a = "Ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẩ")) {
                        a = "Ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẫ")) {
                        a = "Ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                                System.out.println("Chữ Ẫ: " + index);
                            }
                        }
                    }
                    if (k.contains("Ậ")) {
                        a = "Ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //--------------------  Ằ  --------------------
                    if (k.contains("Ắ")) {
                        a = "Ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẳ")) {
                        a = "Ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẵ")) {
                        a = "Ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ặ")) {
                        a = "Ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //--------------------  È  --------------------
                    if (k.contains("É")) {
                        a = "É";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẻ")) {
                        a = "Ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẽ")) {
                        a = "Ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẹ")) {
                        a = "Ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ề  --------------------
                    if (k.contains("Ế")) {
                        a = "Ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ể")) {
                        a = "Ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ễ")) {
                        a = "Ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ệ")) {
                        a = "Ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ò  --------------------
                    if (k.contains("Ó")) {
                        a = "Ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỏ")) {
                        a = "Ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Õ")) {
                        a = "Õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ọ")) {
                        a = "Ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ồ  --------------------
                    if (k.contains("Ố")) {
                        a = "Ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ổ")) {
                        a = "Ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỗ")) {
                        a = "Ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ộ")) {
                        a = "Ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ờ  --------------------
                    if (k.contains("Ớ")) {
                        a = "Ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ở")) {
                        a = "Ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỡ")) {
                        a = "Ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ợ")) {
                        a = "Ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ù  --------------------
                    if (k.contains("Ú")) {
                        a = "Ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ủ")) {
                        a = "Ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ũ")) {
                        a = "Ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ụ")) {
                        a = "Ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ừ  --------------------
                    if (k.contains("Ứ")) {
                        a = "Ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ử")) {
                        a = "Ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ữ")) {
                        a = "Ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ự")) {
                        a = "Ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ì  --------------------
                    if (k.contains("Í")) {
                        a = "Í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỉ")) {
                        a = "Ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ĩ")) {
                        a = "Ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ị")) {
                        a = "Ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỳ  --------------------
                    if (k.contains("Ý")) {
                        a = "Ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỷ")) {
                        a = "Ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỹ")) {
                        a = "Ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỵ")) {
                        a = "Ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //--------------------  dấu s  --------------------
                if (str.equals("s")) {
                    //--------------------  á  --------------------
                    if (k.contains("à")) {
                        a = "à";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ả")) {
                        a = "ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ã")) {
                        a = "ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ạ")) {
                        a = "ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ắ  --------------------
                    if (k.contains("ằ")) {
                        a = "ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẳ")) {
                        a = "ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẵ")) {
                        a = "ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ặ")) {
                        a = "ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ấ  --------------------
                    if (k.contains("ầ")) {
                        a = "ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẩ")) {
                        a = "ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẫ")) {
                        a = "ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ậ")) {
                        a = "ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  é  --------------------
                    if (k.contains("è")) {
                        a = "è";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẻ")) {
                        a = "ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẽ")) {
                        a = "ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẹ")) {
                        a = "ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ế  --------------------
                    if (k.contains("ề")) {
                        a = "ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ể")) {
                        a = "ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ễ")) {
                        a = "ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ệ")) {
                        a = "ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ó  --------------------
                    if (k.contains("ò")) {
                        a = "ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỏ")) {
                        a = "ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("õ")) {
                        a = "õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ọ")) {
                        a = "ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ố  --------------------
                    if (k.contains("ồ")) {
                        a = "ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ổ")) {
                        a = "ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỗ")) {
                        a = "ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ộ")) {
                        a = "ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ớ  --------------------
                    if (k.contains("ờ")) {
                        a = "ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ở")) {
                        a = "ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỡ")) {
                        a = "ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ợ")) {
                        a = "ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ú  --------------------
                    if (k.contains("ù")) {
                        a = "ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ủ")) {
                        a = "ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ũ")) {
                        a = "ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ụ")) {
                        a = "ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ứ  --------------------
                    if (k.contains("ừ")) {
                        a = "ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ử")) {
                        a = "ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ữ")) {
                        a = "ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ự")) {
                        a = "ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  í  --------------------
                    if (k.contains("ì")) {
                        a = "ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỉ")) {
                        a = "ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ĩ")) {
                        a = "ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ị")) {
                        a = "ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ý  --------------------
                    if (k.contains("ỳ")) {
                        a = "ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỷ")) {
                        a = "ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỹ")) {
                        a = "ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỵ")) {
                        a = "ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //------------------------  dấu S  --------------------
                if (str.equals("S")) {
                    //--------------------  Á  --------------------
                    if (k.contains("À")) {
                        a = "À";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ả")) {
                        a = "Ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ã")) {
                        a = "Ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ạ")) {
                        a = "Ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ấ  --------------------
                    if (k.contains("Ầ")) {
                        a = "Ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẩ")) {
                        a = "Ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẫ")) {
                        a = "Ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ậ")) {
                        a = "Ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ắ  --------------------
                    if (k.contains("Ằ")) {
                        a = "Ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẳ")) {
                        a = "Ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẵ")) {
                        a = "Ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ặ")) {
                        a = "Ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  É  --------------------
                    if (k.contains("È")) {
                        a = "È";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẻ")) {
                        a = "Ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẽ")) {
                        a = "Ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẹ")) {
                        a = "Ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ế  --------------------
                    if (k.contains("Ề")) {
                        a = "Ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ể")) {
                        a = "Ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ễ")) {
                        a = "Ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ệ")) {
                        a = "Ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ó  --------------------
                    if (k.contains("Ò")) {
                        a = "Ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỏ")) {
                        a = "Ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Õ")) {
                        a = "Õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ọ")) {
                        a = "Ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ố  --------------------
                    if (k.contains("Ồ")) {
                        a = "Ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ổ")) {
                        a = "Ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỗ")) {
                        a = "Ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ộ")) {
                        a = "Ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ớ  --------------------
                    if (k.contains("Ờ")) {
                        a = "Ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ở")) {
                        a = "Ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỡ")) {
                        a = "Ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ợ")) {
                        a = "Ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ú  --------------------
                    if (k.contains("Ù")) {
                        a = "Ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ủ")) {
                        a = "Ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ũ")) {
                        a = "Ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ụ")) {
                        a = "Ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ứ  --------------------
                    if (k.contains("Ừ")) {
                        a = "Ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ử")) {
                        a = "Ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ữ")) {
                        a = "Ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ự")) {
                        a = "Ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Í  --------------------
                    if (k.contains("Ì")) {
                        a = "Ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỉ")) {
                        a = "Ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ĩ")) {
                        a = "Ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ĩ")) {
                        a = "Ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ị")) {
                        a = "Ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }


                    //--------------------  Ý  --------------------
                    if (k.contains("Ỳ")) {
                        a = "Ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỷ")) {
                        a = "Ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỹ")) {
                        a = "Ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỵ")) {
                        a = "Ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //---------------------  dấu r  --------------------
                if (str.equals("r")) {
                    //--------------------  ả  --------------------
                    if (k.contains("à")) {
                        a = "à";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("á")) {
                        a = "á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ã")) {
                        a = "ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ạ")) {
                        a = "ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẳ  --------------------
                    if (k.contains("ằ")) {
                        a = "ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ắ")) {
                        a = "ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẵ")) {
                        a = "ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ặ")) {
                        a = "ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẩ  --------------------
                    if (k.contains("ầ")) {
                        a = "ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ấ")) {
                        a = "ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẫ")) {
                        a = "ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ậ")) {
                        a = "ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẻ  --------------------
                    if (k.contains("è")) {
                        a = "è";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("é")) {
                        a = "é";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẽ")) {
                        a = "ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẹ")) {
                        a = "ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ể  --------------------
                    if (k.contains("ề")) {
                        a = "ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ế")) {
                        a = "ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ễ")) {
                        a = "ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ệ")) {
                        a = "ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ỏ  --------------------
                    if (k.contains("ò")) {
                        a = "ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ó")) {
                        a = "ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("õ")) {
                        a = "õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ọ")) {
                        a = "ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ổ  --------------------
                    if (k.contains("ồ")) {
                        a = "ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ố")) {
                        a = "ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỗ")) {
                        a = "ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ộ")) {
                        a = "ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ở  --------------------
                    if (k.contains("ờ")) {
                        a = "ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ớ")) {
                        a = "ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỡ")) {
                        a = "ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ợ")) {
                        a = "ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ủ  --------------------
                    if (k.contains("ù")) {
                        a = "ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ú")) {
                        a = "ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ũ")) {
                        a = "ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ụ")) {
                        a = "ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ử  --------------------
                    if (k.contains("ừ")) {
                        a = "ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ứ")) {
                        a = "ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ữ")) {
                        a = "ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ự")) {
                        a = "ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ỉ  --------------------
                    if (k.contains("ì")) {
                        a = "ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("í")) {
                        a = "í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ĩ")) {
                        a = "ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ị")) {
                        a = "ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ỷ  --------------------
                    if (k.contains("ỳ")) {
                        a = "ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ý")) {
                        a = "ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỹ")) {
                        a = "ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỵ")) {
                        a = "ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //----------------------  dấu R  --------------------
                if (str.equals("R")) {
                    //--------------------  Ả  --------------------
                    if (k.contains("À")) {
                        a = "À";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Á")) {
                        a = "Á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ã")) {
                        a = "Ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ạ")) {
                        a = "Ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẩ  --------------------
                    if (k.contains("Ầ")) {
                        a = "Ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ấ")) {
                        a = "Ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẫ")) {
                        a = "Ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ậ")) {
                        a = "Ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẳ  --------------------
                    if (k.contains("Ằ")) {
                        a = "Ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ắ")) {
                        a = "Ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẵ")) {
                        a = "Ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ặ")) {
                        a = "Ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẻ  --------------------
                    if (k.contains("È")) {
                        a = "È";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("É")) {
                        a = "É";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẽ")) {
                        a = "Ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẹ")) {
                        a = "Ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ể  --------------------
                    if (k.contains("Ề")) {
                        a = "Ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ế")) {
                        a = "Ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ễ")) {
                        a = "Ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ệ")) {
                        a = "Ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỏ  --------------------
                    if (k.contains("Ò")) {
                        a = "Ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ó")) {
                        a = "Ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Õ")) {
                        a = "Õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ọ")) {
                        a = "Ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ổ  --------------------
                    if (k.contains("Ồ")) {
                        a = "Ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ố")) {
                        a = "Ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỗ")) {
                        a = "Ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ộ")) {
                        a = "Ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ở  ------------------
                    if (k.contains("Ờ")) {
                        a = "Ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ớ")) {
                        a = "Ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỡ")) {
                        a = "Ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ợ")) {
                        a = "Ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ủ  -------------
                    if (k.contains("Ù")) {
                        a = "Ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ú")) {
                        a = "Ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ũ")) {
                        a = "Ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ụ")) {
                        a = "Ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ử  -------------
                    if (k.contains("Ừ")) {
                        a = "Ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ứ")) {
                        a = "Ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ữ")) {
                        a = "Ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ự")) {
                        a = "Ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỉ  ---------------
                    if (k.contains("Ì")) {
                        a = "Ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Í")) {
                        a = "Í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ĩ")) {
                        a = "Ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ị")) {
                        a = "Ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỷ  -------------
                    if (k.contains("Ỳ")) {
                        a = "Ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ý")) {
                        a = "Ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỹ")) {
                        a = "Ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỵ")) {
                        a = "Ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //------------------- dấu x  --------------------
                if (str.equals("x")) {
                    //--------------------  ã  --------------------
                    if (k.contains("à")) {
                        a = "à";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("á")) {
                        a = "á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ả")) {
                        a = "ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ạ")) {
                        a = "ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẵ  ----------------
                    if (k.contains("ằ")) {
                        a = "ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ắ")) {
                        a = "ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẳ")) {
                        a = "ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ặ")) {
                        a = "ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẫ  -------------------
                    if (k.contains("ầ")) {
                        a = "ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ấ")) {
                        a = "ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẩ")) {
                        a = "ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ậ")) {
                        a = "ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẽ  --------------------
                    if (k.contains("è")) {
                        a = "è";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("é")) {
                        a = "é";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẻ")) {
                        a = "ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẹ")) {
                        a = "ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ễ  --------------------
                    if (k.contains("ề")) {
                        a = "ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ế")) {
                        a = "ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ể")) {
                        a = "ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ệ")) {
                        a = "ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  õ  --------------------
                    if (k.contains("ò")) {
                        a = "ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ó")) {
                        a = "ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỏ")) {
                        a = "ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ọ")) {
                        a = "ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------   ỗ   -----------------
                    if (k.contains("ồ")) {
                        a = "ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ố")) {
                        a = "ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ổ")) {
                        a = "ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ộ")) {
                        a = "ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  ỡ  ----------------
                    if (k.contains("ờ")) {
                        a = "ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ớ")) {
                        a = "ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ở")) {
                        a = "ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ợ")) {
                        a = "ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  ũ  -------------
                    if (k.contains("ù")) {
                        a = "ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ú")) {
                        a = "ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ủ")) {
                        a = "ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ụ")) {
                        a = "ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  ữ  -------------
                    if (k.contains("ừ")) {
                        a = "ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ứ")) {
                        a = "ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ử")) {
                        a = "ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ự")) {
                        a = "ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------   ĩ   ------------------
                    if (k.contains("ì")) {
                        a = "ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("í")) {
                        a = "í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỉ")) {
                        a = "ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ị")) {
                        a = "ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------   ỹ  -------------
                    if (k.contains("ỳ")) {
                        a = "ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ý")) {
                        a = "ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỷ")) {
                        a = "ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỵ")) {
                        a = "ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //------------------------  Dấu X  ------------------------
                if (str.equals("X")) {

                    //--------------------  Ã  --------------------
                    if (k.contains("À")) {
                        a = "À";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Á")) {
                        a = "Á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ả")) {
                        a = "Ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ạ")) {
                        a = "Ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẫ  --------------------
                    if (k.contains("Ầ")) {
                        a = "Ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ấ")) {
                        a = "Ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẩ")) {
                        a = "Ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ậ")) {
                        a = "Ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẵ  --------------------
                    if (k.contains("Ằ")) {
                        a = "Ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ắ")) {
                        a = "Ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẳ")) {
                        a = "Ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ặ")) {
                        a = "Ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //------------------  Ẽ  --------------------
                    if (k.contains("È")) {
                        a = "È";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("É")) {
                        a = "É";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẻ")) {
                        a = "Ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẹ")) {
                        a = "Ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ễ  --------------------
                    if (k.contains("Ề")) {
                        a = "Ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ế")) {
                        a = "Ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ể")) {
                        a = "Ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ệ")) {
                        a = "Ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Õ  --------------------
                    if (k.contains("Ò")) {
                        a = "Ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ó")) {
                        a = "Ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỏ")) {
                        a = "Ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ọ")) {
                        a = "Ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ỗ  --------------------
                    if (k.contains("Ồ")) {
                        a = "Ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ố")) {
                        a = "Ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ổ")) {
                        a = "Ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ộ")) {
                        a = "Ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỡ  --------------------
                    if (k.contains("Ờ")) {
                        a = "Ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ớ")) {
                        a = "Ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ở")) {
                        a = "Ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ợ")) {
                        a = "Ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //------------------  Ũ  -------------
                    if (k.contains("Ù")) {
                        a = "Ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ú")) {
                        a = "Ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ủ")) {
                        a = "Ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ụ")) {
                        a = "Ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ữ  --------------------
                    if (k.contains("Ừ")) {
                        a = "Ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ứ")) {
                        a = "Ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ử")) {
                        a = "Ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ự")) {
                        a = "Ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ĩ  --------------------
                    if (k.contains("Ì")) {
                        a = "Ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Í")) {
                        a = "Í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỉ")) {
                        a = "Ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ị")) {
                        a = "Ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỹ  --------------------
                    if (k.contains("Ỳ")) {
                        a = "Ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ý")) {
                        a = "Ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỷ")) {
                        a = "Ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỵ")) {
                        a = "Ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //-------------------- dấu j  --------------------
                if (str.equals("j")) {
                    //---------------------  ạ  --------------------
                    if (k.contains("à")) {
                        a = "à";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("á")) {
                        a = "á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ả")) {
                        a = "ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ã")) {
                        a = "ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ặ  --------------------
                    if (k.contains("ằ")) {
                        a = "ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ắ")) {
                        a = "ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẳ")) {
                        a = "ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẵ")) {
                        a = "ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ậ  ------------------
                    if (k.contains("ầ")) {
                        a = "ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ấ")) {
                        a = "ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẩ")) {
                        a = "ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẫ")) {
                        a = "ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẹ  --------------
                    if (k.contains("è")) {
                        a = "è";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("é")) {
                        a = "é";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẻ")) {
                        a = "ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẽ")) {
                        a = "ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  ệ  --------------------
                    if (k.contains("ề")) {
                        a = "ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ế")) {
                        a = "ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ể")) {
                        a = "ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ễ")) {
                        a = "ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ọ  --------------
                    if (k.contains("ò")) {
                        a = "ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ó")) {
                        a = "ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỏ")) {
                        a = "ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("õ")) {
                        a = "õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ộ  ------------------
                    if (k.contains("ồ")) {
                        a = "ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ố")) {
                        a = "ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ổ")) {
                        a = "ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỗ")) {
                        a = "ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ợ  ----------------
                    if (k.contains("ờ")) {
                        a = "ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ớ")) {
                        a = "ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ở")) {
                        a = "ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỡ")) {
                        a = "ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ụ  ------------
                    if (k.contains("ù")) {
                        a = "ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ú")) {
                        a = "ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ủ")) {
                        a = "ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ũ")) {
                        a = "ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ự  --------------
                    if (k.contains("ừ")) {
                        a = "ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ứ")) {
                        a = "ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ử")) {
                        a = "ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ữ")) {
                        a = "ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ị  -----------------
                    if (k.contains("ì")) {
                        a = "ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("í")) {
                        a = "í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỉ")) {
                        a = "ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ĩ")) {
                        a = "ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ỵ  --------------------
                    if (k.contains("ỳ")) {
                        a = "ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ý")) {
                        a = "ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỷ")) {
                        a = "ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỹ")) {
                        a = "ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //--------------------  dấu J  --------------
                if (str.equals("J")) {
                    //--------------------  Ạ  --------------------
                    if (k.contains("À")) {
                        a = "À";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Á")) {
                        a = "Á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ả")) {
                        a = "Ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ã")) {
                        a = "Ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ậ  --------------------
                    if (k.contains("Ầ")) {
                        a = "Ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ấ")) {
                        a = "Ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẩ")) {
                        a = "Ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẫ")) {
                        a = "Ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ặ  --------------------
                    if (k.contains("Ằ")) {
                        a = "Ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ắ")) {
                        a = "Ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẳ")) {
                        a = "Ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẵ")) {
                        a = "Ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẹ  ----------------
                    if (k.contains("È")) {
                        a = "È";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("É")) {
                        a = "É";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẻ")) {
                        a = "Ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẽ")) {
                        a = "Ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ệ  --------------
                    if (k.contains("Ề")) {
                        a = "Ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ế")) {
                        a = "Ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ể")) {
                        a = "Ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ễ")) {
                        a = "Ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ọ  ----------------
                    if (k.contains("Ò")) {
                        a = "Ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ó")) {
                        a = "Ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỏ")) {
                        a = "Ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Õ")) {
                        a = "Õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ộ  ----------------
                    if (k.contains("Ồ")) {
                        a = "Ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ố")) {
                        a = "Ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ổ")) {
                        a = "Ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỗ")) {
                        a = "Ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ợ  -----------------
                    if (k.contains("Ờ")) {
                        a = "Ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ớ")) {
                        a = "Ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ở")) {
                        a = "Ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỡ")) {
                        a = "Ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ụ  -----------------
                    if (k.contains("Ù")) {
                        a = "Ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ú")) {
                        a = "Ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ủ")) {
                        a = "Ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ũ")) {
                        a = "Ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ự  ------------------
                    if (k.contains("Ừ")) {
                        a = "Ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ứ")) {
                        a = "Ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ử")) {
                        a = "Ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ữ")) {
                        a = "Ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ị  --------------------
                    if (k.contains("Ì")) {
                        a = "Ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Í")) {
                        a = "Í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỉ")) {
                        a = "Ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ĩ")) {
                        a = "Ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỵ  -------------------
                    if (k.contains("Ỳ")) {
                        a = "Ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ý")) {
                        a = "Ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỷ")) {
                        a = "Ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỹ")) {
                        a = "Ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }
            }

            if (arrStr.length == 1) {
                k = str;
            }
        }
//        System.out.println("Cuoi cung a & index: " + a + " " + index);
        /**Dấu huyền f*/
        if (str.equals("f")) {
            /**à*/
            //------------------  à  ------------------
            if (a.equals("a") || a.equals("á") || a.equals("ả") || a.equals("ã") || a.equals("ạ")) {
                name = "\u00e0";
            }

            //------------------  ằ  ------------------
            if (a.equals("ă") || a.equals("ắ") || a.equals("ẳ") || a.equals("ẵ") || a.equals("ặ")) {
                name = "\u1eb1";
            }

            //------------------  ầ  ------------------
            if (a.equals("â") || a.equals("ấ") || a.equals("ẩ") || a.equals("ẫ") || a.equals("ậ")) {
                name = "\u1ea7";
            }

            //------------------  è  ------------------
            if (a.equals("e") || a.equals("é") || a.equals("ẻ") || a.equals("ẽ") || a.equals("ẹ")) {
                name = "\u00e8";
            }

            //------------------  ề  ------------------
            if (a.equals("ê") || a.equals("ế") || a.equals("ể") || a.equals("ễ") || a.equals("ệ")) {
                name = "\u1ec1";
            }

            //------------------  ò  ------------------
            if (a.equals("o") || a.equals("ó") || a.equals("ỏ") || a.equals("õ") || a.equals("ọ")) {
                name = "\u00f2";
            }

            //------------------  ồ  ------------------
            if (a.equals("ô") || a.equals("ố") || a.equals("ổ") || a.equals("ỗ") || a.equals("ộ")) {
                name = "\u1ed3";
            }

            //------------------  ờ  ------------------
            if (a.equals("ơ") || a.equals("ớ") || a.equals("ở") || a.equals("ỡ") || a.equals("ợ")) {
                name = "\u1edd";
            }

            //------------------  ù  ------------------
            if (a.equals("u") || a.equals("ú") || a.equals("ủ") || a.equals("ũ") || a.equals("ụ")) {
                name = "\u00f9";
            }

            //------------------  ừ  ------------------
            if (a.equals("ư") || a.equals("ứ") || a.equals("ử") || a.equals("ữ") || a.equals("ự")) {
                name = "\u1eeb";
            }

            //------------------  ì  ------------------
            if (a.equals("i") || a.equals("í") || a.equals("ỉ") || a.equals("ĩ") || a.equals("ị")) {
                name = "\u00ec";
            }

            //------------------  ỳ  ------------------
            if (a.equals("y") || a.equals("ý") || a.equals("ỷ") || a.equals("ỹ") || a.equals("ỵ")) {
                name = "\u1ef3";
            }
        }
        /**Dấu huyền F*/
        if (str.equals("F")) {
            //------------------  À  ------------------
            if (a.equals("A") || a.equals("Á") || a.equals("Ả") || a.equals("Ã") || a.equals("Ạ")) {
                name = "\u00c0";
            }

            //------------------  Ầ  ------------------
            if (a.equals("Â") || a.equals("Ấ") || a.equals("Ẩ") || a.equals("Ẫ") || a.equals("Ậ")) {
                name = "\u1ea6";
            }

            //------------------  Ằ  ------------------
            if (a.equals("Ă") || a.equals("Ắ") || a.equals("Ẳ") || a.equals("Ẵ") || a.equals("Ặ")) {
                name = "\u1eb0";
            }

            //------------------  È  ------------------
            if (a.equals("E") || a.equals("É") || a.equals("Ẻ") || a.equals("Ẽ") || a.equals("Ẹ")) {
                name = "\u00c8";
            }

            //------------------  Ề  ------------------
            if (a.equals("Ê") || a.equals("Ế") || a.equals("Ể") || a.equals("Ễ") || a.equals("Ệ")) {
                name = "\u1ec0";
            }

            //------------------  Ò  ------------------
            if (a.equals("O") || a.equals("Ó") || a.equals("Ỏ") || a.equals("Õ") || a.equals("Ọ")) {
                name = "\u00d2";
            }

            //------------------  Ồ  ------------------
            if (a.equals("Ô") || a.equals("Ố") || a.equals("Ổ") || a.equals("Ỗ") || a.equals("Ộ")) {
                name = "\u1ed2";
            }

            //------------------  Ờ  ------------------
            if (a.equals("Ơ") || a.equals("Ớ") || a.equals("Ở") || a.equals("Ỡ") || a.equals("Ợ")) {
                name = "\u1edc";
            }

            //------------------  Ù  ------------------
            if (a.equals("U") || a.equals("Ú") || a.equals("Ủ") || a.equals("Ũ") || a.equals("Ụ")) {
                name = "\u00d9";
            }

            //------------------  Ừ  ------------------
            if (a.equals("Ư") || a.equals("Ứ") || a.equals("Ử") || a.equals("Ữ") || a.equals("Ự")) {
                name = "\u1eea";
            }

            //------------------  Ì  ------------------
            if (a.equals("I") || a.equals("Í") || a.equals("Ỉ") || a.equals("Ĩ") || a.equals("Ị")) {
                name = "\u00cc";
            }

            //------------------  Ỳ  ------------------
            if (a.equals("Y") || a.equals("Ý") || a.equals("Ỷ") || a.equals("Ỹ") || a.equals("Ỵ")) {
                name = "\u1ef2";
            }
        }
        /**Dấu sắc s*/
        if (str.equals("s")) {
            //------------------  á  ------------------
            if (a.equals("a") || a.equals("à") || a.equals("ả") || a.equals("ã") || a.equals("ạ")) {
                name = "\u00e1";
            }

            //------------------  ắ  ------------------
            if (a.equals("ă") || a.equals("ằ") || a.equals("ẳ") || a.equals("ẵ") || a.equals("ặ")) {
                name = "\u1eaf";
            }

            //------------------  ấ  ------------------
            if (a.equals("â") || a.equals("ầ") || a.equals("ẩ") || a.equals("ẫ") || a.equals("ậ")) {
                name = "\u1ea5";
            }

            //------------------  é  ------------------
            if (a.equals("e") || a.equals("è") || a.equals("ẻ") || a.equals("ẽ") || a.equals("ẹ")) {
                name = "\u00e9";
            }

            //------------------  ế  ------------------
            if (a.equals("ê") || a.equals("ề") || a.equals("ể") || a.equals("ễ") || a.equals("ệ")) {
                name = "\u1ebf";
            }

            //------------------  ó  ------------------
            if (a.equals("o") || a.equals("ò") || a.equals("ỏ") || a.equals("õ") || a.equals("ọ")) {
                name = "\u00f3";
            }

            //------------------  ố  ------------------
            if (a.equals("ô") || a.equals("ồ") || a.equals("ổ") || a.equals("ỗ") || a.equals("ộ")) {
                name = "\u1ed1";
            }

            //------------------  ớ  ------------------
            if (a.equals("ơ") || a.equals("ờ") || a.equals("ở") || a.equals("ỡ") || a.equals("ợ")) {
                name = "\u1edb";
            }

            //------------------  ú  ------------------
            if (a.equals("u") || a.equals("ù") || a.equals("ủ") || a.equals("ũ") || a.equals("ụ")) {
                name = "\u00fa";
            }

            //------------------  ứ  ------------------
            if (a.equals("ư") || a.equals("ừ") || a.equals("ử") || a.equals("ữ") || a.equals("ự")) {
                name = "\u1ee9";
            }

            //------------------  í  ------------------
            if (a.equals("i") || a.equals("ì") || a.equals("ỉ") || a.equals("ĩ") || a.equals("ị")) {
                name = "\u00ed";
            }

            //------------------  ý  ------------------
            if (a.equals("y") || a.equals("ỳ") || a.equals("ỷ") || a.equals("ỹ") || a.equals("ỵ")) {
                name = "\u00fd";
            }
        }
        /**Dấu sắc S*/
        if (str.equals("S")) {
            //------------------  Á  ------------------
            if (a.equals("A") || a.equals("À") || a.equals("Ả") || a.equals("Ã") || a.equals("Ạ")) {
                name = "\u00c1";
            }

            //------------------  Ấ  ------------------
            if (a.equals("Â") || a.equals("Ầ") || a.equals("Ẩ") || a.equals("Ẫ") || a.equals("Ậ")) {
                name = "\u1ea4";
            }

            //------------------  Ắ  ------------------
            if (a.equals("Ă") || a.equals("Ằ") || a.equals("Ẳ") || a.equals("Ẵ") || a.equals("Ặ")) {
                name = "\u1eae";
            }

            //------------------  É  ------------------
            if (a.equals("E") || a.equals("È") || a.equals("Ẻ") || a.equals("Ẽ") || a.equals("Ẹ")) {
                name = "\u00c9";
            }

            //------------------  Ế  ------------------
            if (a.equals("Ê") || a.equals("Ề") || a.equals("Ể") || a.equals("Ễ") || a.equals("Ệ")) {
                name = "\u1ebe";
            }

            //------------------  Ó  ------------------
            if (a.equals("O") || a.equals("Ò") || a.equals("Ỏ") || a.equals("Õ") || a.equals("Ọ")) {
                name = "\u00d3";
            }

            //------------------  Ố  ------------------
            if (a.equals("Ô") || a.equals("Ồ") || a.equals("Ổ") || a.equals("Ỗ") || a.equals("Ộ")) {
                name = "\u1ed0";
            }

            //------------------  Ớ  ------------------
            if (a.equals("Ơ") || a.equals("Ờ") || a.equals("Ở") || a.equals("Ỡ") || a.equals("Ợ")) {
                name = "\u1eda";
            }

            //------------------  Ú  ------------------
            if (a.equals("U") || a.equals("Ù") || a.equals("Ủ") || a.equals("Ũ") || a.equals("Ụ")) {
                name = "\u00da";
            }

            //------------------  Ứ  ------------------
            if (a.equals("Ư") || a.equals("Ừ") || a.equals("Ử") || a.equals("Ữ") || a.equals("Ự")) {
                name = "\u1ee8";
            }

            //------------------  Í  ------------------
            if (a.equals("I") || a.equals("Ì") || a.equals("Ỉ") || a.equals("Ĩ") || a.equals("Ị")) {
                name = "\u00cd";
            }

            //------------------  Ý  ------------------
            if (a.equals("Y") || a.equals("Ỳ") || a.equals("Ỷ") || a.equals("Ỹ") || a.equals("Ỵ")) {
                name = "\u00dd";
            }
        }
        /**Dấu hỏi r*/
        if (str.equals("r")) {
            //------------------  ả  ------------------
            if (a.equals("a") || a.equals("à") || a.equals("á") || a.equals("ã") || a.equals("ạ")) {
                name = "\u1ea3";
            }

            //------------------  ẳ  ------------------
            if (a.equals("ă") || a.equals("ằ") || a.equals("ắ") || a.equals("ẵ") || a.equals("ặ")) {
                name = "\u1eb3";
            }

            //------------------  ẩ  ------------------
            if (a.equals("â") || a.equals("ầ") || a.equals("ấ") || a.equals("ẫ") || a.equals("ậ")) {
                name = "\u1ea9";
            }

            //------------------  ẻ  ------------------
            if (a.equals("e") || a.equals("è") || a.equals("é") || a.equals("ẽ") || a.equals("ẹ")) {
                name = "\u1ebb";
            }

            //------------------  ể  ------------------
            if (a.equals("ê") || a.equals("ề") || a.equals("ế") || a.equals("ễ") || a.equals("ệ")) {
                name = "\u1ec3";
            }

            //------------------  ỏ  ------------------
            if (a.equals("o") || a.equals("ò") || a.equals("ó") || a.equals("õ") || a.equals("ọ")) {
                name = "\u1ecf";
            }

            //------------------  ổ  ------------------
            if (a.equals("ô") || a.equals("ồ") || a.equals("ố") || a.equals("ỗ") || a.equals("ộ")) {
                name = "\u1ed5";
            }

            //------------------  ở  ------------------
            if (a.equals("ơ") || a.equals("ờ") || a.equals("ớ") || a.equals("ỡ") || a.equals("ợ")) {
                name = "\u1edf";
            }

            //------------------  ủ  ------------------
            if (a.equals("u") || a.equals("ù") || a.equals("ú") || a.equals("ũ") || a.equals("ụ")) {
                name = "\u1ee7";
            }

            //------------------  ử  ------------------
            if (a.equals("ư") || a.equals("ừ") || a.equals("ứ") || a.equals("ữ") || a.equals("ự")) {
                name = "\u1eed";
            }

            //------------------  ỉ  ------------------
            if (a.equals("i") || a.equals("ì") || a.equals("í") || a.equals("ĩ") || a.equals("ị")) {
                name = "\u1ec9";
            }

            //------------------  ỷ  ------------------
            if (a.equals("y") || a.equals("ý") || a.equals("ỳ") || a.equals("ỹ") || a.equals("ỵ")) {
                name = "\u1ef7";
            }
        }
        /**Dấu hỏi R*/
        if (str.equals("R")) {
            //------------------  Ả  ------------------
            if (a.equals("A") || a.equals("À") || a.equals("Á") || a.equals("Ã") || a.equals("Ạ")) {
                name = "\u1ea2";
            }

            //------------------  Ẩ  ------------------
            if (a.equals("Â") || a.equals("Ầ") || a.equals("Ấ") || a.equals("Ẫ") || a.equals("Ậ")) {
                name = "\u1ea8";
            }

            //------------------  Ẳ  ------------------
            if (a.equals("Ă") || a.equals("Ằ") || a.equals("Ắ") || a.equals("Ẵ") || a.equals("Ặ")) {
                name = "\u1eb2";
            }

            //------------------  Ẻ  ------------------
            if (a.equals("E") || a.equals("È") || a.equals("É") || a.equals("Ẽ") || a.equals("Ẹ")) {
                name = "\u1eba";
            }

            //------------------  Ể  ------------------
            if (a.equals("Ê") || a.equals("Ề") || a.equals("Ế") || a.equals("Ễ") || a.equals("Ệ")) {
                name = "\u1ec2";
            }

            //------------------   Ỏ  -----------------
            if (a.equals("O") || a.equals("Ò") || a.equals("Ó") || a.equals("Õ") || a.equals("Ọ")) {
                name = "\u1ece";
            }

            //------------------   Ổ  ----------------
            if (a.equals("Ô") || a.equals("Ồ") || a.equals("Ố") || a.equals("Ỗ") || a.equals("Ộ")) {
                name = "\u1ed4";
            }

            //------------------  Ở  ------------------
            if (a.equals("Ơ") || a.equals("Ờ") || a.equals("Ớ") || a.equals("Ỡ") || a.equals("Ợ")) {
                name = "\u1ede";
            }

            //------------------   Ủ   -----------------
            if (a.equals("U") || a.equals("Ù") || a.equals("Ú") || a.equals("Ũ") || a.equals("Ụ")) {
                name = "\u1ee6";
            }

            //------------------  Ử  ------------------
            if (a.equals("Ư") || a.equals("Ừ") || a.equals("Ứ") || a.equals("Ữ") || a.equals("Ự")) {
                name = "\u1eec";
            }

            //------------------  Ỉ  ------------------
            if (a.equals("I") || a.equals("Ì") || a.equals("Í") || a.equals("Ĩ") || a.equals("Ị")) {
                name = "\u1ec8";
            }

            //------------------  Ỷ  ------------------
            if (a.equals("Y") || a.equals("Ỳ") || a.equals("Ý") || a.equals("Ỹ") || a.equals("Ỵ")) {
                name = "\u1ef6";
            }
        }
        /**Dấu ngã x*/
        if (str.equals("x")) {

            //------------------  ã  -------------------
            if (a.equals("a") || a.equals("à") || a.equals("á") || a.equals("ả") || a.equals("ạ")) {
                name = "\u00e3";
            }

            //------------------  ẵ  -----------------
            if (a.equals("ă") || a.equals("ằ") || a.equals("ắ") || a.equals("ẳ") || a.equals("ặ")) {
                name = "\u1eb5";
            }

            //------------------  ẫ  ----------------
            if (a.equals("â") || a.equals("ầ") || a.equals("ấ") || a.equals("ẩ") || a.equals("ậ")) {
                name = "\u1eab";
            }

            //------------------  ẽ  ---------------
            if (a.equals("e") || a.equals("è") || a.equals("é") || a.equals("ẻ") || a.equals("ẹ")) {
                name = "\u1ebd";
            }

            //------------------  ễ  ------------------
            if (a.equals("ê") || a.equals("ề") || a.equals("ế") || a.equals("ể") || a.equals("ệ")) {
                name = "\u1ec5";
            }

            //------------------  õ  ------------
            if (a.equals("o") || a.equals("ò") || a.equals("ó") || a.equals("ỏ") || a.equals("ọ")) {
                name = "\u00f5";
            }

            //------------------  ỗ  ---------------
            if (a.equals("ô") || a.equals("ồ") || a.equals("ố") || a.equals("ổ") || a.equals("ộ")) {
                name = "\u1ed7";
            }

            //------------------  ỡ  -----------------
            if (a.equals("ơ") || a.equals("ờ") || a.equals("ớ") || a.equals("ở") || a.equals("ợ")) {
                name = "\u1ee1";
            }

            //------------------  ũ   -------------------
            if (a.equals("u") || a.equals("ù") || a.equals("ú") || a.equals("ủ") || a.equals("ụ")) {
                name = "\u0169";
            }

            //------------------  ữ  -----------------
            if (a.equals("ư") || a.equals("ừ") || a.equals("ứ") || a.equals("ử") || a.equals("ự")) {
                name = "\u1eef";
            }

            //------------------  ĩ  ----------------
            if (a.equals("i") || a.equals("ì") || a.equals("í") || a.equals("ỉ") || a.equals("ị")) {
                name = "\u0129";
            }

            //------------------  ỹ   --------------
            if (a.equals("y") || a.equals("ỳ") || a.equals("ý") || a.equals("ỷ") || a.equals("ỵ")) {
                name = "\u1ef9";
            }
        }
        /**Dấu ngã X*/
        if (str.equals("X")) {
            //------------------  Ã  ------------------
            if (a.equals("A") || a.equals("À") || a.equals("Á") || a.equals("Ả") || a.equals("Ạ")) {
                name = "\u00c3";
            }

            //------------------  Ẫ  ------------------
            if (a.equals("Â") || a.equals("Ầ") || a.equals("Ấ") || a.equals("Ẩ") || a.equals("Ậ")) {
                name = "\u1eaa";
            }

            //------------------  Ẵ  ------------------
            if (a.equals("Ă") || a.equals("Ằ") || a.equals("Ắ") || a.equals("Ẳ") || a.equals("Ặ")) {
                name = "\u1eb4";
            }

            //------------------  Ẽ  ------------------
            if (a.equals("E") || a.equals("È") || a.equals("É") || a.equals("Ẻ") || a.equals("Ẹ")) {
                name = "\u1ebc";
            }

            //------------------  Ễ  ------------------
            if (a.equals("Ê") || a.equals("Ề") || a.equals("Ế") || a.equals("Ể") || a.equals("Ệ")) {
                name = "\u1ec4";
            }

            //------------------  Õ  ------------------
            if (a.equals("O") || a.equals("Ò") || a.equals("Ó") || a.equals("Ỏ") || a.equals("Ọ")) {
                name = "\u00d5";
            }

            //------------------  Ỗ  ------------------
            if (a.equals("Ô") || a.equals("Ồ") || a.equals("Ố") || a.equals("Ổ") || a.equals("Ộ")) {
                name = "\u1ed6";
            }

            //------------------  Ỡ  ------------------
            if (a.equals("Ơ") || a.equals("Ờ") || a.equals("Ớ") || a.equals("Ở") || a.equals("Ợ")) {
                name = "\u1ee0";
            }

            //------------------  Ũ  ------------------
            if (a.equals("U") || a.equals("Ù") || a.equals("Ú") || a.equals("Ủ") || a.equals("Ụ")) {
                name = "\u0168";
            }

            //------------------  Ữ  ------------------
            if (a.equals("Ư") || a.equals("Ừ") || a.equals("Ứ") || a.equals("Ử") || a.equals("Ự")) {
                name = "\u1eee";
            }

            //------------------  Ĩ  ------------------
            if (a.equals("I") || a.equals("Ì") || a.equals("Í") || a.equals("Ỉ") || a.equals("Ị")) {
                name = "\u0128";
            }

            //------------------  Ỹ  ------------------
            if (a.equals("Y") || a.equals("Ỳ") || a.equals("Ý") || a.equals("Ỷ") || a.equals("Ỵ")) {
                name = "\u1ef8";
            }
        }
        /**Dẫu nặng j*/
        if (str.equals("j")) {

            //------------------  ạ  ---------------
            if (a.equals("a") || a.equals("à") || a.equals("á") || a.equals("ả") || a.equals("ã")) {
                name = "\u1ea1";
            }

            //------------------  ặ  ----------------
            if (a.equals("ă") || a.equals("ằ") || a.equals("ắ") || a.equals("ẳ") || a.equals("ẵ")) {
                name = "\u1eb7";
            }

            //------------------  ậ  -----------------------
            if (a.equals("â") || a.equals("ầ") || a.equals("ấ") || a.equals("ẩ") || a.equals("ẫ")) {
                name = "\u1ead";
            }

            //------------------  ẹ  ------------------
            if (a.equals("e") || a.equals("è") || a.equals("é") || a.equals("ẻ") || a.equals("ẽ")) {
                name = "\u1eb9";
            }

            //------------------  ệ  ------------------
            if (a.equals("ê") || a.equals("ề") || a.equals("ế") || a.equals("ể") || a.equals("ễ")) {
                name = "\u1ec7";
            }

            //------------------  ọ  ------------------
            if (a.equals("o") || a.equals("ò") || a.equals("ó") || a.equals("ỏ") || a.equals("õ")) {
                name = "\u1ecd";
            }

            //------------------  ộ  ------------------
            if (a.equals("ô") || a.equals("ồ") || a.equals("ố") || a.equals("ổ") || a.equals("ỗ")) {
                name = "\u1ed9";
            }

            //------------------  ợ  ------------------
            if (a.equals("ơ") || a.equals("ờ") || a.equals("ớ") || a.equals("ở") || a.equals("ỡ")) {
                name = "\u1ee3";
            }

            //------------------  ụ  ------------------
            if (a.equals("u") || a.equals("ù") || a.equals("ú") || a.equals("ủ") || a.equals("ũ")) {
                name = "\u1ee5";
            }

            //------------------  ự  ------------------
            if (a.equals("ư") || a.equals("ừ") || a.equals("ứ") || a.equals("ử") || a.equals("ữ")) {
                name = "\u1ef1";
            }

            //------------------  ị  ------------------
            if (a.equals("i") || a.equals("ì") || a.equals("í") || a.equals("ỉ") || a.equals("ĩ")) {
                name = "\u1ecb";
            }

            //------------------  ỵ  ------------------
            if (a.equals("y") || a.equals("ỳ") || a.equals("ý") || a.equals("ỷ") || a.equals("ỹ")) {
                name = "\u1ef5";
            }
        }
        /**Dấu nặng J*/
        if (str.equals("J")) {
            //------------------  Ạ  ------------------
            if (a.equals("A") || a.equals("À") || a.equals("Á") || a.equals("Ả") || a.equals("Ã")) {
                name = "\u1ea0";
            }

            //------------------  Ậ  ------------------
            if (a.equals("Â") || a.equals("Ầ") || a.equals("Ấ") || a.equals("Ẩ") || a.equals("Ẫ")) {
                name = "\u1eac";
            }

            //------------------  Ặ  ------------------
            if (a.equals("Ă") || a.equals("Ằ") || a.equals("Ắ") || a.equals("Ẳ") || a.equals("Ẵ")) {
                name = "\u1eb6";
            }

            //------------------  Ẹ  ------------------
            if (a.equals("E") || a.equals("È") || a.equals("É") || a.equals("Ẻ") || a.equals("Ẽ")) {
                name = "\u1eb8";
            }

            //------------------  Ệ  ------------------  
            if (a.equals("Ê") || a.equals("Ề") || a.equals("Ế") || a.equals("Ể") || a.equals("Ễ")) {
                name = "\u1ec6";
            }

            //------------------  Ọ  ------------------
            if (a.equals("O") || a.equals("Ò") || a.equals("Ó") || a.equals("Ỏ") || a.equals("Õ")) {
                name = "\u1ecc";
            }

            //------------------  Ộ  ------------------
            if (a.equals("Ô") || a.equals("Ồ") || a.equals("Ố") || a.equals("Ổ") || a.equals("Ỗ")) {
                name = "\u1ed8";
            }

            //------------------  Ợ  ------------------
            if (a.equals("Ơ") || a.equals("Ờ") || a.equals("Ớ") || a.equals("Ở") || a.equals("Ỡ")) {
                name = "\u1ee2";
            }

            //------------------  Ụ  ------------------
            if (a.equals("U") || a.equals("Ù") || a.equals("Ú") || a.equals("Ủ") || a.equals("Ũ")) {
                name = "\u1ee4";
            }

            //------------------  Ự  ------------------
            if (a.equals("Ư") || a.equals("Ừ") || a.equals("Ứ") || a.equals("Ử") || a.equals("Ữ")) {
                name = "\u1ef0";
            }

            //------------------  Ị  ------------------
            if (a.equals("I") || a.equals("Ì") || a.equals("Í") || a.equals("Ỉ") || a.equals("Ĩ")) {
                name = "\u1eca";
            }

            //------------------  Ỵ  ------------------
            if (a.equals("Y") || a.equals("Ỳ") || a.equals("Ý") || a.equals("Ỷ") || a.equals("Ỹ")) {
                name = "\u1ef4";
            }
        }
        /**Chữ â*/
        if (str.equals("a")) {
            if (a.equals("a")) {
                name = "\u00e2";
            }
        }
        /**Chữ Â*/
        if (str.equals("A")) {
            if (a.equals("A")) {
                name = "\u00c2";
            }
        }
        /**Chữ ê*/
        if (str.equals("e")) {
            if (a.equals("e")) {
                name = "\u00ea";
            }
        }
        /**Chữ Ê*/
        if (str.equals("E")) {
            if (a.equals("E")) {
                name = "\u00ca";
            }
        }
        /**Chữ ô*/
        if (str.equals("o")) {
            if (a.equals("o")) {
                name = "\u00f4";
            }
        }
        /**Chữ Ô*/
        if (str.equals("O")) {
            if (a.equals("O")) {
                name = "\u00d4";
            }
        }
        /**Chữ đ*/
        if (str.equals("d")) {
            if (a.equals("d")) {
                name = "\u0111";
            }
        }
        /**Chữ Đ*/
        if (str.equals("D")) {
            if (a.equals("D")) {
                name = "\u0110";
            }
        }
        /**dấu w*/
        if (str.equals("w")) {
            /**Chữ ư*/
            if (a.equals("u")) {
                name = "\u01b0";
            }
            /**Chữ ơ*/
            if (a.equals("o")) {
                name = "\u01a1";
            }
            /**Chữ ă*/
            if (a.equals("a")) {
                name = "\u0103";
            }
        }
        /**dấu W*/
        if (str.equals("W")) {
            if (a.equals("A")) {
                name = "\u0102";
            }
            if (a.equals("U")) {
                name = "\u01af";
            }
            if (a.equals("O")) {
                name = "\u01a0";
            }
        }
//        System.out.println("Trả về vietnamese: " + name);
        return name;
    }

    public String conver(String str, JTextArea textArea, int caretPosition) {
        String name = str;
        String a = "";
        String k;
        int caret = caretPosition;
        int textFieldLength = textArea.getText().length();
        if (textFieldLength > 0) {
            String subTextField = textArea.getText().substring(0, caretPosition);
            int subTextFieldLength = subTextField.length();
            String[] arrStr = subTextField.split(" ");
            /**ở đây */
            if (arrStr.length == 0) {
                return str;
            } else {
                k = arrStr[arrStr.length - 1];
            }
            /**Thêm vào khoảng trắng thì ko làm j*/
            if (subTextFieldLength == 0) {
                return str;
            } else {
                if (subTextField.substring(subTextFieldLength - 1).equals(" ")) {
                    return str;
                }
            }
            for (int i = 0; i < k.length(); i++) {
                if (k.contains("d")) {
                    a = "d";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them d: " + index);
                        }
                    }
                }
                if (k.contains("ă")) {
                    a = "ă";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them ă: " + index);
                        }
                    }
                }
                if (k.contains("â")) {
                    a = "â";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them â: " + index);
                        }
                    }
                }
                if (k.contains("i")) {
                    a = "i";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them i: " + index);
                        }
                    }
                }
                if (k.contains("y")) {
                    a = "y";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them y: " + index);
                        }
                    }
                }
                if (k.contains("D")) {
                    a = "D";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them D: " + index);
                        }
                    }
                }

                if (k.contains("Ă")) {
                    a = "Ă";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Ă: " + index);
                        }
                    }
                }
                if (k.contains("Â")) {
                    a = "Â";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Â: " + index);
                        }
                    }
                }

                if (k.contains("I")) {
                    a = "I";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them I: " + index);
                        }
                    }
                }
                if (k.contains("Y")) {
                    a = "Y";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Y: " + index);
                        }
                    }
                }
                if (k.contains("U")) {
                    a = "U";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them U: " + index);
                        }
                    }
                }
                if (k.contains("u")) {
                    a = "u";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them u: " + index);
                        }
                    }
                }
                if (k.contains("Ô")) {
                    a = "Ô";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                        System.out.println("them Ô: " + index);
                        };
                    }
                }
                if (k.contains("ô")) {
                    a = "ô";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them ô: " + index);
                        }

                    }
                }
                if (k.contains("E")) {
                    a = "E";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Ê: " + index);
                        }
                    }
                }
                if (k.contains("e")) {
                    a = "e";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them e: " + index);
                        }
                    }
                }

                if (k.contains("Ê")) {
                    a = "Ê";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Ê: " + index);
                        }
                    }
                }
                if (k.contains("ê")) {
                    a = "ê";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them ê: " + index);
                        }
                    }
                }
                if (k.contains("Ư")) {
                    a = "Ư";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Ư: " + index);
                        }
                    }
                }
                if (k.contains("ư")) {
                    a = "ư";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them ư: " + index);
                        }
                    }
                }
                if (k.contains("O")) {
                    a = "O";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them O: " + index);
                        }
                    }
                }
                if (k.contains("o")) {
                    a = "o";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them o: " + index);
                        }
                    }
                }
                if (k.contains("Ơ")) {
                    a = "Ơ";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them Ơ: " + index);
                        }
                    }
                }
                if (k.contains("ơ")) {
                    a = "ơ";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them ơ: " + index);
                        }
                    }
                }
                if (k.contains("A")) {
                    a = "A";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them A: " + index);
                        }
                    }
                }
                /**Để lấy ra index ở vị trí đó*/
                if (k.contains("a")) {
                    a = "a";
                    for (int j = 0; j < subTextFieldLength; j++) {
                        if (subTextField.substring(j, j + 1).equals(a)) {
                            index = j;
//                            System.out.println("them a: " + index);
                        }
                    }
                }

                //------------------------  f  ------------------------
                if (str.equals("f")) {
                    //------------------------  à  ------------------------
                    if (k.contains("á")) {
                        a = "á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ả")) {
                        a = "ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ã")) {
                        a = "ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ạ")) {
                        a = "ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  ằ  ------------------------
                    if (k.contains("ắ")) {
                        a = "ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẳ")) {
                        a = "ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẵ")) {
                        a = "ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ặ")) {
                        a = "ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  ầ  ------------------------
                    if (k.contains("ấ")) {
                        a = "ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẩ")) {
                        a = "ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẫ")) {
                        a = "ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ậ")) {
                        a = "ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  è  ------------------------
                    if (k.contains("é")) {
                        a = "é";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẻ")) {
                        a = "ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẽ")) {
                        a = "ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẹ")) {
                        a = "ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  ề  ------------------------
                    if (k.contains("ế")) {
                        a = "ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ể")) {
                        a = "ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ễ")) {
                        a = "ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ệ")) {
                        a = "ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  ò  ------------------------
                    if (k.contains("ó")) {
                        a = "ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỏ")) {
                        a = "ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("õ")) {
                        a = "õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ọ")) {
                        a = "ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //------------------------  ồ  ------------------------
                    if (k.contains("ố")) {
                        a = "ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ổ")) {
                        a = "ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỗ")) {
                        a = "ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ộ")) {
                        a = "ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //------------------------  ờ  ------------------------
                    if (k.contains("ớ")) {
                        a = "ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ở")) {
                        a = "ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỡ")) {
                        a = "ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ợ")) {
                        a = "ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //--------------------  ù  --------------------
                    if (k.contains("ú")) {
                        a = "ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ủ")) {
                        a = "ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ũ")) {
                        a = "ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ụ")) {
                        a = "ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //--------------------  ừ  --------------------
                    if (k.contains("ứ")) {
                        a = "ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ử")) {
                        a = "ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ữ")) {
                        a = "ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ự")) {
                        a = "ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ì  --------------------
                    if (k.contains("í")) {
                        a = "í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỉ")) {
                        a = "ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ĩ")) {
                        a = "ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ị")) {
                        a = "ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ỳ  --------------------
                    if (k.contains("ý")) {
                        a = "ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỷ")) {
                        a = "ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỹ")) {
                        a = "ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỵ")) {
                        a = "ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //--------------------  dấu F  --------------------
                if (str.equals("F")) {
                    //--------------------  À  --------------------
                    if (k.contains("Á")) {
                        a = "Á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ả")) {
                        a = "Ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ã")) {
                        a = "Ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ạ")) {
                        a = "Ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //--------------------  Ầ  --------------------
                    if (k.contains("Ấ")) {
                        a = "Ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẩ")) {
                        a = "Ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẫ")) {
                        a = "Ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                                System.out.println("Chữ Ẫ: " + index);
                            }
                        }
                    }
                    if (k.contains("Ậ")) {
                        a = "Ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //--------------------  Ằ  --------------------
                    if (k.contains("Ắ")) {
                        a = "Ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẳ")) {
                        a = "Ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẵ")) {
                        a = "Ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ặ")) {
                        a = "Ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    //--------------------  È  --------------------
                    if (k.contains("É")) {
                        a = "É";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẻ")) {
                        a = "Ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẽ")) {
                        a = "Ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẹ")) {
                        a = "Ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ề  --------------------
                    if (k.contains("Ế")) {
                        a = "Ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ể")) {
                        a = "Ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ễ")) {
                        a = "Ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ệ")) {
                        a = "Ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ò  --------------------
                    if (k.contains("Ó")) {
                        a = "Ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỏ")) {
                        a = "Ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Õ")) {
                        a = "Õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ọ")) {
                        a = "Ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ồ  --------------------
                    if (k.contains("Ố")) {
                        a = "Ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ổ")) {
                        a = "Ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỗ")) {
                        a = "Ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ộ")) {
                        a = "Ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ờ  --------------------
                    if (k.contains("Ớ")) {
                        a = "Ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ở")) {
                        a = "Ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỡ")) {
                        a = "Ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ợ")) {
                        a = "Ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ù  --------------------
                    if (k.contains("Ú")) {
                        a = "Ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ủ")) {
                        a = "Ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ũ")) {
                        a = "Ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ụ")) {
                        a = "Ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ừ  --------------------
                    if (k.contains("Ứ")) {
                        a = "Ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ử")) {
                        a = "Ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ữ")) {
                        a = "Ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ự")) {
                        a = "Ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ì  --------------------
                    if (k.contains("Í")) {
                        a = "Í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỉ")) {
                        a = "Ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ĩ")) {
                        a = "Ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ị")) {
                        a = "Ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỳ  --------------------
                    if (k.contains("Ý")) {
                        a = "Ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỷ")) {
                        a = "Ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỹ")) {
                        a = "Ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỵ")) {
                        a = "Ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //--------------------  dấu s  --------------------
                if (str.equals("s")) {
                    //--------------------  á  --------------------
                    if (k.contains("à")) {
                        a = "à";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ả")) {
                        a = "ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ã")) {
                        a = "ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ạ")) {
                        a = "ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ắ  --------------------
                    if (k.contains("ằ")) {
                        a = "ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẳ")) {
                        a = "ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẵ")) {
                        a = "ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ặ")) {
                        a = "ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ấ  --------------------
                    if (k.contains("ầ")) {
                        a = "ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẩ")) {
                        a = "ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẫ")) {
                        a = "ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ậ")) {
                        a = "ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  é  --------------------
                    if (k.contains("è")) {
                        a = "è";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẻ")) {
                        a = "ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẽ")) {
                        a = "ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẹ")) {
                        a = "ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ế  --------------------
                    if (k.contains("ề")) {
                        a = "ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ể")) {
                        a = "ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ễ")) {
                        a = "ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ệ")) {
                        a = "ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ó  --------------------
                    if (k.contains("ò")) {
                        a = "ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỏ")) {
                        a = "ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("õ")) {
                        a = "õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ọ")) {
                        a = "ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ố  --------------------
                    if (k.contains("ồ")) {
                        a = "ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ổ")) {
                        a = "ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỗ")) {
                        a = "ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ộ")) {
                        a = "ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ớ  --------------------
                    if (k.contains("ờ")) {
                        a = "ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ở")) {
                        a = "ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỡ")) {
                        a = "ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ợ")) {
                        a = "ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ú  --------------------
                    if (k.contains("ù")) {
                        a = "ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ủ")) {
                        a = "ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ũ")) {
                        a = "ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ụ")) {
                        a = "ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ứ  --------------------
                    if (k.contains("ừ")) {
                        a = "ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ử")) {
                        a = "ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ữ")) {
                        a = "ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ự")) {
                        a = "ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  í  --------------------
                    if (k.contains("ì")) {
                        a = "ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỉ")) {
                        a = "ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ĩ")) {
                        a = "ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ị")) {
                        a = "ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ý  --------------------
                    if (k.contains("ỳ")) {
                        a = "ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỷ")) {
                        a = "ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỹ")) {
                        a = "ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỵ")) {
                        a = "ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //------------------------  dấu S  --------------------
                if (str.equals("S")) {
                    //--------------------  Á  --------------------
                    if (k.contains("À")) {
                        a = "À";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ả")) {
                        a = "Ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ã")) {
                        a = "Ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ạ")) {
                        a = "Ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ấ  --------------------
                    if (k.contains("Ầ")) {
                        a = "Ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẩ")) {
                        a = "Ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẫ")) {
                        a = "Ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ậ")) {
                        a = "Ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ắ  --------------------
                    if (k.contains("Ằ")) {
                        a = "Ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẳ")) {
                        a = "Ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẵ")) {
                        a = "Ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ặ")) {
                        a = "Ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  É  --------------------
                    if (k.contains("È")) {
                        a = "È";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẻ")) {
                        a = "Ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẽ")) {
                        a = "Ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẹ")) {
                        a = "Ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ế  --------------------
                    if (k.contains("Ề")) {
                        a = "Ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ể")) {
                        a = "Ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ễ")) {
                        a = "Ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ệ")) {
                        a = "Ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ó  --------------------
                    if (k.contains("Ò")) {
                        a = "Ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỏ")) {
                        a = "Ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Õ")) {
                        a = "Õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ọ")) {
                        a = "Ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ố  --------------------
                    if (k.contains("Ồ")) {
                        a = "Ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ổ")) {
                        a = "Ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỗ")) {
                        a = "Ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ộ")) {
                        a = "Ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ớ  --------------------
                    if (k.contains("Ờ")) {
                        a = "Ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ở")) {
                        a = "Ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỡ")) {
                        a = "Ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ợ")) {
                        a = "Ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ú  --------------------
                    if (k.contains("Ù")) {
                        a = "Ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ủ")) {
                        a = "Ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ũ")) {
                        a = "Ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ụ")) {
                        a = "Ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ứ  --------------------
                    if (k.contains("Ừ")) {
                        a = "Ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ử")) {
                        a = "Ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ữ")) {
                        a = "Ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ự")) {
                        a = "Ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Í  --------------------
                    if (k.contains("Ì")) {
                        a = "Ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỉ")) {
                        a = "Ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ĩ")) {
                        a = "Ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ĩ")) {
                        a = "Ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ị")) {
                        a = "Ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }


                    //--------------------  Ý  --------------------
                    if (k.contains("Ỳ")) {
                        a = "Ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỷ")) {
                        a = "Ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỹ")) {
                        a = "Ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỵ")) {
                        a = "Ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //---------------------  dấu r  --------------------
                if (str.equals("r")) {
                    //--------------------  ả  --------------------
                    if (k.contains("à")) {
                        a = "à";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("á")) {
                        a = "á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ã")) {
                        a = "ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ạ")) {
                        a = "ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẳ  --------------------
                    if (k.contains("ằ")) {
                        a = "ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ắ")) {
                        a = "ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẵ")) {
                        a = "ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ặ")) {
                        a = "ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẩ  --------------------
                    if (k.contains("ầ")) {
                        a = "ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ấ")) {
                        a = "ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẫ")) {
                        a = "ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ậ")) {
                        a = "ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẻ  --------------------
                    if (k.contains("è")) {
                        a = "è";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("é")) {
                        a = "é";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẽ")) {
                        a = "ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẹ")) {
                        a = "ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ể  --------------------
                    if (k.contains("ề")) {
                        a = "ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ế")) {
                        a = "ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ễ")) {
                        a = "ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ệ")) {
                        a = "ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ỏ  --------------------
                    if (k.contains("ò")) {
                        a = "ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ó")) {
                        a = "ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("õ")) {
                        a = "õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ọ")) {
                        a = "ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ổ  --------------------
                    if (k.contains("ồ")) {
                        a = "ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ố")) {
                        a = "ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỗ")) {
                        a = "ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ộ")) {
                        a = "ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ở  --------------------
                    if (k.contains("ờ")) {
                        a = "ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ớ")) {
                        a = "ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỡ")) {
                        a = "ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ợ")) {
                        a = "ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ủ  --------------------
                    if (k.contains("ù")) {
                        a = "ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ú")) {
                        a = "ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ũ")) {
                        a = "ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ụ")) {
                        a = "ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ử  --------------------
                    if (k.contains("ừ")) {
                        a = "ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ứ")) {
                        a = "ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ữ")) {
                        a = "ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ự")) {
                        a = "ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ỉ  --------------------
                    if (k.contains("ì")) {
                        a = "ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("í")) {
                        a = "í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ĩ")) {
                        a = "ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ị")) {
                        a = "ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ỷ  --------------------
                    if (k.contains("ỳ")) {
                        a = "ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ý")) {
                        a = "ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỹ")) {
                        a = "ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỵ")) {
                        a = "ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //----------------------  dấu R  --------------------
                if (str.equals("R")) {
                    //--------------------  Ả  --------------------
                    if (k.contains("À")) {
                        a = "À";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Á")) {
                        a = "Á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ã")) {
                        a = "Ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ạ")) {
                        a = "Ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẩ  --------------------
                    if (k.contains("Ầ")) {
                        a = "Ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ấ")) {
                        a = "Ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẫ")) {
                        a = "Ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ậ")) {
                        a = "Ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẳ  --------------------
                    if (k.contains("Ằ")) {
                        a = "Ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ắ")) {
                        a = "Ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẵ")) {
                        a = "Ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ặ")) {
                        a = "Ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẻ  --------------------
                    if (k.contains("È")) {
                        a = "È";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("É")) {
                        a = "É";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẽ")) {
                        a = "Ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẹ")) {
                        a = "Ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ể  --------------------
                    if (k.contains("Ề")) {
                        a = "Ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ế")) {
                        a = "Ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ễ")) {
                        a = "Ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ệ")) {
                        a = "Ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỏ  --------------------
                    if (k.contains("Ò")) {
                        a = "Ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ó")) {
                        a = "Ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Õ")) {
                        a = "Õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ọ")) {
                        a = "Ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ổ  --------------------
                    if (k.contains("Ồ")) {
                        a = "Ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ố")) {
                        a = "Ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỗ")) {
                        a = "Ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ộ")) {
                        a = "Ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ở  ------------------
                    if (k.contains("Ờ")) {
                        a = "Ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ớ")) {
                        a = "Ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỡ")) {
                        a = "Ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ợ")) {
                        a = "Ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ủ  -------------
                    if (k.contains("Ù")) {
                        a = "Ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ú")) {
                        a = "Ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ũ")) {
                        a = "Ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ụ")) {
                        a = "Ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ử  -------------
                    if (k.contains("Ừ")) {
                        a = "Ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ứ")) {
                        a = "Ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ữ")) {
                        a = "Ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ự")) {
                        a = "Ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỉ  ---------------
                    if (k.contains("Ì")) {
                        a = "Ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Í")) {
                        a = "Í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ĩ")) {
                        a = "Ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ị")) {
                        a = "Ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỷ  -------------
                    if (k.contains("Ỳ")) {
                        a = "Ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ý")) {
                        a = "Ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỹ")) {
                        a = "Ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỵ")) {
                        a = "Ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //------------------- dấu x  --------------------
                if (str.equals("x")) {
                    //--------------------  ã  --------------------
                    if (k.contains("à")) {
                        a = "à";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("á")) {
                        a = "á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ả")) {
                        a = "ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ạ")) {
                        a = "ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẵ  ----------------
                    if (k.contains("ằ")) {
                        a = "ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ắ")) {
                        a = "ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẳ")) {
                        a = "ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ặ")) {
                        a = "ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẫ  -------------------
                    if (k.contains("ầ")) {
                        a = "ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ấ")) {
                        a = "ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẩ")) {
                        a = "ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ậ")) {
                        a = "ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẽ  --------------------
                    if (k.contains("è")) {
                        a = "è";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("é")) {
                        a = "é";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẻ")) {
                        a = "ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẹ")) {
                        a = "ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ễ  --------------------
                    if (k.contains("ề")) {
                        a = "ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ế")) {
                        a = "ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ể")) {
                        a = "ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ệ")) {
                        a = "ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  õ  --------------------
                    if (k.contains("ò")) {
                        a = "ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ó")) {
                        a = "ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỏ")) {
                        a = "ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ọ")) {
                        a = "ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------   ỗ   -----------------
                    if (k.contains("ồ")) {
                        a = "ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ố")) {
                        a = "ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ổ")) {
                        a = "ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ộ")) {
                        a = "ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  ỡ  ----------------
                    if (k.contains("ờ")) {
                        a = "ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ớ")) {
                        a = "ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ở")) {
                        a = "ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ợ")) {
                        a = "ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  ũ  -------------
                    if (k.contains("ù")) {
                        a = "ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ú")) {
                        a = "ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ủ")) {
                        a = "ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ụ")) {
                        a = "ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  ữ  -------------
                    if (k.contains("ừ")) {
                        a = "ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ứ")) {
                        a = "ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ử")) {
                        a = "ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ự")) {
                        a = "ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------   ĩ   ------------------
                    if (k.contains("ì")) {
                        a = "ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("í")) {
                        a = "í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỉ")) {
                        a = "ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ị")) {
                        a = "ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------   ỹ  -------------
                    if (k.contains("ỳ")) {
                        a = "ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ý")) {
                        a = "ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỷ")) {
                        a = "ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỵ")) {
                        a = "ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //------------------------  Dấu X  ------------------------
                if (str.equals("X")) {

                    //--------------------  Ã  --------------------
                    if (k.contains("À")) {
                        a = "À";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Á")) {
                        a = "Á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ả")) {
                        a = "Ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ạ")) {
                        a = "Ạ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẫ  --------------------
                    if (k.contains("Ầ")) {
                        a = "Ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ấ")) {
                        a = "Ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẩ")) {
                        a = "Ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ậ")) {
                        a = "Ậ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẵ  --------------------
                    if (k.contains("Ằ")) {
                        a = "Ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ắ")) {
                        a = "Ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẳ")) {
                        a = "Ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ặ")) {
                        a = "Ặ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //------------------  Ẽ  --------------------
                    if (k.contains("È")) {
                        a = "È";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("É")) {
                        a = "É";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẻ")) {
                        a = "Ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẹ")) {
                        a = "Ẹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ễ  --------------------
                    if (k.contains("Ề")) {
                        a = "Ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ế")) {
                        a = "Ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ể")) {
                        a = "Ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ệ")) {
                        a = "Ệ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Õ  --------------------
                    if (k.contains("Ò")) {
                        a = "Ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ó")) {
                        a = "Ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỏ")) {
                        a = "Ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ọ")) {
                        a = "Ọ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ỗ  --------------------
                    if (k.contains("Ồ")) {
                        a = "Ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ố")) {
                        a = "Ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ổ")) {
                        a = "Ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ộ")) {
                        a = "Ộ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỡ  --------------------
                    if (k.contains("Ờ")) {
                        a = "Ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ớ")) {
                        a = "Ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ở")) {
                        a = "Ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ợ")) {
                        a = "Ợ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //------------------  Ũ  -------------
                    if (k.contains("Ù")) {
                        a = "Ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ú")) {
                        a = "Ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ủ")) {
                        a = "Ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ụ")) {
                        a = "Ụ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ữ  --------------------
                    if (k.contains("Ừ")) {
                        a = "Ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ứ")) {
                        a = "Ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ử")) {
                        a = "Ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ự")) {
                        a = "Ự";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ĩ  --------------------
                    if (k.contains("Ì")) {
                        a = "Ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Í")) {
                        a = "Í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỉ")) {
                        a = "Ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ị")) {
                        a = "Ị";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỹ  --------------------
                    if (k.contains("Ỳ")) {
                        a = "Ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ý")) {
                        a = "Ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỷ")) {
                        a = "Ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỵ")) {
                        a = "Ỵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //-------------------- dấu j  --------------------
                if (str.equals("j")) {
                    //---------------------  ạ  --------------------
                    if (k.contains("à")) {
                        a = "à";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("á")) {
                        a = "á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ả")) {
                        a = "ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ã")) {
                        a = "ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ặ  --------------------
                    if (k.contains("ằ")) {
                        a = "ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ắ")) {
                        a = "ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẳ")) {
                        a = "ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẵ")) {
                        a = "ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ậ  ------------------
                    if (k.contains("ầ")) {
                        a = "ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ấ")) {
                        a = "ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẩ")) {
                        a = "ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẫ")) {
                        a = "ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ẹ  --------------
                    if (k.contains("è")) {
                        a = "è";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("é")) {
                        a = "é";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẻ")) {
                        a = "ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ẽ")) {
                        a = "ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  ệ  --------------------
                    if (k.contains("ề")) {
                        a = "ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ế")) {
                        a = "ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ể")) {
                        a = "ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ễ")) {
                        a = "ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ọ  --------------
                    if (k.contains("ò")) {
                        a = "ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ó")) {
                        a = "ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỏ")) {
                        a = "ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("õ")) {
                        a = "õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ộ  ------------------
                    if (k.contains("ồ")) {
                        a = "ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ố")) {
                        a = "ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ổ")) {
                        a = "ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỗ")) {
                        a = "ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ợ  ----------------
                    if (k.contains("ờ")) {
                        a = "ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ớ")) {
                        a = "ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ở")) {
                        a = "ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỡ")) {
                        a = "ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ụ  ------------
                    if (k.contains("ù")) {
                        a = "ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ú")) {
                        a = "ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ủ")) {
                        a = "ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ũ")) {
                        a = "ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ự  --------------
                    if (k.contains("ừ")) {
                        a = "ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ứ")) {
                        a = "ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ử")) {
                        a = "ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ữ")) {
                        a = "ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ị  -----------------
                    if (k.contains("ì")) {
                        a = "ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("í")) {
                        a = "í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỉ")) {
                        a = "ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ĩ")) {
                        a = "ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  ỵ  --------------------
                    if (k.contains("ỳ")) {
                        a = "ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ý")) {
                        a = "ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỷ")) {
                        a = "ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("ỹ")) {
                        a = "ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }

                //--------------------  dấu J  --------------
                if (str.equals("J")) {
                    //--------------------  Ạ  --------------------
                    if (k.contains("À")) {
                        a = "À";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Á")) {
                        a = "Á";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ả")) {
                        a = "Ả";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ã")) {
                        a = "Ã";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ậ  --------------------
                    if (k.contains("Ầ")) {
                        a = "Ầ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ấ")) {
                        a = "Ấ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẩ")) {
                        a = "Ẩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẫ")) {
                        a = "Ẫ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ặ  --------------------
                    if (k.contains("Ằ")) {
                        a = "Ằ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ắ")) {
                        a = "Ắ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẳ")) {
                        a = "Ẳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẵ")) {
                        a = "Ẵ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ẹ  ----------------
                    if (k.contains("È")) {
                        a = "È";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("É")) {
                        a = "É";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẻ")) {
                        a = "Ẻ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ẽ")) {
                        a = "Ẽ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ệ  --------------
                    if (k.contains("Ề")) {
                        a = "Ề";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ế")) {
                        a = "Ế";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ể")) {
                        a = "Ể";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ễ")) {
                        a = "Ễ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ọ  ----------------
                    if (k.contains("Ò")) {
                        a = "Ò";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ó")) {
                        a = "Ó";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỏ")) {
                        a = "Ỏ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Õ")) {
                        a = "Õ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ộ  ----------------
                    if (k.contains("Ồ")) {
                        a = "Ồ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ố")) {
                        a = "Ố";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ổ")) {
                        a = "Ổ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỗ")) {
                        a = "Ỗ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ợ  -----------------
                    if (k.contains("Ờ")) {
                        a = "Ờ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ớ")) {
                        a = "Ớ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ở")) {
                        a = "Ở";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỡ")) {
                        a = "Ỡ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //-------------------  Ụ  -----------------
                    if (k.contains("Ù")) {
                        a = "Ù";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ú")) {
                        a = "Ú";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ủ")) {
                        a = "Ủ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ũ")) {
                        a = "Ũ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ự  ------------------
                    if (k.contains("Ừ")) {
                        a = "Ừ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ứ")) {
                        a = "Ứ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ử")) {
                        a = "Ử";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ữ")) {
                        a = "Ữ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ị  --------------------
                    if (k.contains("Ì")) {
                        a = "Ì";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Í")) {
                        a = "Í";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỉ")) {
                        a = "Ỉ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ĩ")) {
                        a = "Ĩ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }

                    //--------------------  Ỵ  -------------------
                    if (k.contains("Ỳ")) {
                        a = "Ỳ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ý")) {
                        a = "Ý";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỷ")) {
                        a = "Ỷ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                    if (k.contains("Ỹ")) {
                        a = "Ỹ";
                        for (int j = 0; j < subTextFieldLength; j++) {
                            if (subTextField.substring(j, j + 1).equals(a)) {
                                index = j;
                            }
                        }
                    }
                }
            }

            if (arrStr.length == 1) {
                k = str;
            }
        }
//        System.out.println("Cuoi cung a & index: " + a + " " + index);
        /**Dấu huyền f*/
        if (str.equals("f")) {
            /**à*/
            //------------------  à  ------------------
            if (a.equals("a") || a.equals("á") || a.equals("ả") || a.equals("ã") || a.equals("ạ")) {
                name = "\u00e0";
            }

            //------------------  ằ  ------------------
            if (a.equals("ă") || a.equals("ắ") || a.equals("ẳ") || a.equals("ẵ") || a.equals("ặ")) {
                name = "\u1eb1";
            }

            //------------------  ầ  ------------------
            if (a.equals("â") || a.equals("ấ") || a.equals("ẩ") || a.equals("ẫ") || a.equals("ậ")) {
                name = "\u1ea7";
            }

            //------------------  è  ------------------
            if (a.equals("e") || a.equals("é") || a.equals("ẻ") || a.equals("ẽ") || a.equals("ẹ")) {
                name = "\u00e8";
            }

            //------------------  ề  ------------------
            if (a.equals("ê") || a.equals("ế") || a.equals("ể") || a.equals("ễ") || a.equals("ệ")) {
                name = "\u1ec1";
            }

            //------------------  ò  ------------------
            if (a.equals("o") || a.equals("ó") || a.equals("ỏ") || a.equals("õ") || a.equals("ọ")) {
                name = "\u00f2";
            }

            //------------------  ồ  ------------------
            if (a.equals("ô") || a.equals("ố") || a.equals("ổ") || a.equals("ỗ") || a.equals("ộ")) {
                name = "\u1ed3";
            }

            //------------------  ờ  ------------------
            if (a.equals("ơ") || a.equals("ớ") || a.equals("ở") || a.equals("ỡ") || a.equals("ợ")) {
                name = "\u1edd";
            }

            //------------------  ù  ------------------
            if (a.equals("u") || a.equals("ú") || a.equals("ủ") || a.equals("ũ") || a.equals("ụ")) {
                name = "\u00f9";
            }

            //------------------  ừ  ------------------
            if (a.equals("ư") || a.equals("ứ") || a.equals("ử") || a.equals("ữ") || a.equals("ự")) {
                name = "\u1eeb";
            }

            //------------------  ì  ------------------
            if (a.equals("i") || a.equals("í") || a.equals("ỉ") || a.equals("ĩ") || a.equals("ị")) {
                name = "\u00ec";
            }

            //------------------  ỳ  ------------------
            if (a.equals("y") || a.equals("ý") || a.equals("ỷ") || a.equals("ỹ") || a.equals("ỵ")) {
                name = "\u1ef3";
            }
        }
        /**Dấu huyền F*/
        if (str.equals("F")) {
            //------------------  À  ------------------
            if (a.equals("A") || a.equals("Á") || a.equals("Ả") || a.equals("Ã") || a.equals("Ạ")) {
                name = "\u00c0";
            }

            //------------------  Ầ  ------------------
            if (a.equals("Â") || a.equals("Ấ") || a.equals("Ẩ") || a.equals("Ẫ") || a.equals("Ậ")) {
                name = "\u1ea6";
            }

            //------------------  Ằ  ------------------
            if (a.equals("Ă") || a.equals("Ắ") || a.equals("Ẳ") || a.equals("Ẵ") || a.equals("Ặ")) {
                name = "\u1eb0";
            }

            //------------------  È  ------------------
            if (a.equals("E") || a.equals("É") || a.equals("Ẻ") || a.equals("Ẽ") || a.equals("Ẹ")) {
                name = "\u00c8";
            }

            //------------------  Ề  ------------------
            if (a.equals("Ê") || a.equals("Ế") || a.equals("Ể") || a.equals("Ễ") || a.equals("Ệ")) {
                name = "\u1ec0";
            }

            //------------------  Ò  ------------------
            if (a.equals("O") || a.equals("Ó") || a.equals("Ỏ") || a.equals("Õ") || a.equals("Ọ")) {
                name = "\u00d2";
            }

            //------------------  Ồ  ------------------
            if (a.equals("Ô") || a.equals("Ố") || a.equals("Ổ") || a.equals("Ỗ") || a.equals("Ộ")) {
                name = "\u1ed2";
            }

            //------------------  Ờ  ------------------
            if (a.equals("Ơ") || a.equals("Ớ") || a.equals("Ở") || a.equals("Ỡ") || a.equals("Ợ")) {
                name = "\u1edc";
            }

            //------------------  Ù  ------------------
            if (a.equals("U") || a.equals("Ú") || a.equals("Ủ") || a.equals("Ũ") || a.equals("Ụ")) {
                name = "\u00d9";
            }

            //------------------  Ừ  ------------------
            if (a.equals("Ư") || a.equals("Ứ") || a.equals("Ử") || a.equals("Ữ") || a.equals("Ự")) {
                name = "\u1eea";
            }

            //------------------  Ì  ------------------
            if (a.equals("I") || a.equals("Í") || a.equals("Ỉ") || a.equals("Ĩ") || a.equals("Ị")) {
                name = "\u00cc";
            }

            //------------------  Ỳ  ------------------
            if (a.equals("Y") || a.equals("Ý") || a.equals("Ỷ") || a.equals("Ỹ") || a.equals("Ỵ")) {
                name = "\u1ef2";
            }
        }
        /**Dấu sắc s*/
        if (str.equals("s")) {
            //------------------  á  ------------------
            if (a.equals("a") || a.equals("à") || a.equals("ả") || a.equals("ã") || a.equals("ạ")) {
                name = "\u00e1";
            }

            //------------------  ắ  ------------------
            if (a.equals("ă") || a.equals("ằ") || a.equals("ẳ") || a.equals("ẵ") || a.equals("ặ")) {
                name = "\u1eaf";
            }

            //------------------  ấ  ------------------
            if (a.equals("â") || a.equals("ầ") || a.equals("ẩ") || a.equals("ẫ") || a.equals("ậ")) {
                name = "\u1ea5";
            }

            //------------------  é  ------------------
            if (a.equals("e") || a.equals("è") || a.equals("ẻ") || a.equals("ẽ") || a.equals("ẹ")) {
                name = "\u00e9";
            }

            //------------------  ế  ------------------
            if (a.equals("ê") || a.equals("ề") || a.equals("ể") || a.equals("ễ") || a.equals("ệ")) {
                name = "\u1ebf";
            }

            //------------------  ó  ------------------
            if (a.equals("o") || a.equals("ò") || a.equals("ỏ") || a.equals("õ") || a.equals("ọ")) {
                name = "\u00f3";
            }

            //------------------  ố  ------------------
            if (a.equals("ô") || a.equals("ồ") || a.equals("ổ") || a.equals("ỗ") || a.equals("ộ")) {
                name = "\u1ed1";
            }

            //------------------  ớ  ------------------
            if (a.equals("ơ") || a.equals("ờ") || a.equals("ở") || a.equals("ỡ") || a.equals("ợ")) {
                name = "\u1edb";
            }

            //------------------  ú  ------------------
            if (a.equals("u") || a.equals("ù") || a.equals("ủ") || a.equals("ũ") || a.equals("ụ")) {
                name = "\u00fa";
            }

            //------------------  ứ  ------------------
            if (a.equals("ư") || a.equals("ừ") || a.equals("ử") || a.equals("ữ") || a.equals("ự")) {
                name = "\u1ee9";
            }

            //------------------  í  ------------------
            if (a.equals("i") || a.equals("ì") || a.equals("ỉ") || a.equals("ĩ") || a.equals("ị")) {
                name = "\u00ed";
            }

            //------------------  ý  ------------------
            if (a.equals("y") || a.equals("ỳ") || a.equals("ỷ") || a.equals("ỹ") || a.equals("ỵ")) {
                name = "\u00fd";
            }
        }
        /**Dấu sắc S*/
        if (str.equals("S")) {
            //------------------  Á  ------------------
            if (a.equals("A") || a.equals("À") || a.equals("Ả") || a.equals("Ã") || a.equals("Ạ")) {
                name = "\u00c1";
            }

            //------------------  Ấ  ------------------
            if (a.equals("Â") || a.equals("Ầ") || a.equals("Ẩ") || a.equals("Ẫ") || a.equals("Ậ")) {
                name = "\u1ea4";
            }

            //------------------  Ắ  ------------------
            if (a.equals("Ă") || a.equals("Ằ") || a.equals("Ẳ") || a.equals("Ẵ") || a.equals("Ặ")) {
                name = "\u1eae";
            }

            //------------------  É  ------------------
            if (a.equals("E") || a.equals("È") || a.equals("Ẻ") || a.equals("Ẽ") || a.equals("Ẹ")) {
                name = "\u00c9";
            }

            //------------------  Ế  ------------------
            if (a.equals("Ê") || a.equals("Ề") || a.equals("Ể") || a.equals("Ễ") || a.equals("Ệ")) {
                name = "\u1ebe";
            }

            //------------------  Ó  ------------------
            if (a.equals("O") || a.equals("Ò") || a.equals("Ỏ") || a.equals("Õ") || a.equals("Ọ")) {
                name = "\u00d3";
            }

            //------------------  Ố  ------------------
            if (a.equals("Ô") || a.equals("Ồ") || a.equals("Ổ") || a.equals("Ỗ") || a.equals("Ộ")) {
                name = "\u1ed0";
            }

            //------------------  Ớ  ------------------
            if (a.equals("Ơ") || a.equals("Ờ") || a.equals("Ở") || a.equals("Ỡ") || a.equals("Ợ")) {
                name = "\u1eda";
            }

            //------------------  Ú  ------------------
            if (a.equals("U") || a.equals("Ù") || a.equals("Ủ") || a.equals("Ũ") || a.equals("Ụ")) {
                name = "\u00da";
            }

            //------------------  Ứ  ------------------
            if (a.equals("Ư") || a.equals("Ừ") || a.equals("Ử") || a.equals("Ữ") || a.equals("Ự")) {
                name = "\u1ee8";
            }

            //------------------  Í  ------------------
            if (a.equals("I") || a.equals("Ì") || a.equals("Ỉ") || a.equals("Ĩ") || a.equals("Ị")) {
                name = "\u00cd";
            }

            //------------------  Ý  ------------------
            if (a.equals("Y") || a.equals("Ỳ") || a.equals("Ỷ") || a.equals("Ỹ") || a.equals("Ỵ")) {
                name = "\u00dd";
            }
        }
        /**Dấu hỏi r*/
        if (str.equals("r")) {
            //------------------  ả  ------------------
            if (a.equals("a") || a.equals("à") || a.equals("á") || a.equals("ã") || a.equals("ạ")) {
                name = "\u1ea3";
            }

            //------------------  ẳ  ------------------
            if (a.equals("ă") || a.equals("ằ") || a.equals("ắ") || a.equals("ẵ") || a.equals("ặ")) {
                name = "\u1eb3";
            }

            //------------------  ẩ  ------------------
            if (a.equals("â") || a.equals("ầ") || a.equals("ấ") || a.equals("ẫ") || a.equals("ậ")) {
                name = "\u1ea9";
            }

            //------------------  ẻ  ------------------
            if (a.equals("e") || a.equals("è") || a.equals("é") || a.equals("ẽ") || a.equals("ẹ")) {
                name = "\u1ebb";
            }

            //------------------  ể  ------------------
            if (a.equals("ê") || a.equals("ề") || a.equals("ế") || a.equals("ễ") || a.equals("ệ")) {
                name = "\u1ec3";
            }

            //------------------  ỏ  ------------------
            if (a.equals("o") || a.equals("ò") || a.equals("ó") || a.equals("õ") || a.equals("ọ")) {
                name = "\u1ecf";
            }

            //------------------  ổ  ------------------
            if (a.equals("ô") || a.equals("ồ") || a.equals("ố") || a.equals("ỗ") || a.equals("ộ")) {
                name = "\u1ed5";
            }

            //------------------  ở  ------------------
            if (a.equals("ơ") || a.equals("ờ") || a.equals("ớ") || a.equals("ỡ") || a.equals("ợ")) {
                name = "\u1edf";
            }

            //------------------  ủ  ------------------
            if (a.equals("u") || a.equals("ù") || a.equals("ú") || a.equals("ũ") || a.equals("ụ")) {
                name = "\u1ee7";
            }

            //------------------  ử  ------------------
            if (a.equals("ư") || a.equals("ừ") || a.equals("ứ") || a.equals("ữ") || a.equals("ự")) {
                name = "\u1eed";
            }

            //------------------  ỉ  ------------------
            if (a.equals("i") || a.equals("ì") || a.equals("í") || a.equals("ĩ") || a.equals("ị")) {
                name = "\u1ec9";
            }

            //------------------  ỷ  ------------------
            if (a.equals("y") || a.equals("ý") || a.equals("ỳ") || a.equals("ỹ") || a.equals("ỵ")) {
                name = "\u1ef7";
            }
        }
        /**Dấu hỏi R*/
        if (str.equals("R")) {
            //------------------  Ả  ------------------
            if (a.equals("A") || a.equals("À") || a.equals("Á") || a.equals("Ã") || a.equals("Ạ")) {
                name = "\u1ea2";
            }

            //------------------  Ẩ  ------------------
            if (a.equals("Â") || a.equals("Ầ") || a.equals("Ấ") || a.equals("Ẫ") || a.equals("Ậ")) {
                name = "\u1ea8";
            }

            //------------------  Ẳ  ------------------
            if (a.equals("Ă") || a.equals("Ằ") || a.equals("Ắ") || a.equals("Ẵ") || a.equals("Ặ")) {
                name = "\u1eb2";
            }

            //------------------  Ẻ  ------------------
            if (a.equals("E") || a.equals("È") || a.equals("É") || a.equals("Ẽ") || a.equals("Ẹ")) {
                name = "\u1eba";
            }

            //------------------  Ể  ------------------
            if (a.equals("Ê") || a.equals("Ề") || a.equals("Ế") || a.equals("Ễ") || a.equals("Ệ")) {
                name = "\u1ec2";
            }

            //------------------   Ỏ  -----------------
            if (a.equals("O") || a.equals("Ò") || a.equals("Ó") || a.equals("Õ") || a.equals("Ọ")) {
                name = "\u1ece";
            }

            //------------------   Ổ  ----------------
            if (a.equals("Ô") || a.equals("Ồ") || a.equals("Ố") || a.equals("Ỗ") || a.equals("Ộ")) {
                name = "\u1ed4";
            }

            //------------------  Ở  ------------------
            if (a.equals("Ơ") || a.equals("Ờ") || a.equals("Ớ") || a.equals("Ỡ") || a.equals("Ợ")) {
                name = "\u1ede";
            }

            //------------------   Ủ   -----------------
            if (a.equals("U") || a.equals("Ù") || a.equals("Ú") || a.equals("Ũ") || a.equals("Ụ")) {
                name = "\u1ee6";
            }

            //------------------  Ử  ------------------
            if (a.equals("Ư") || a.equals("Ừ") || a.equals("Ứ") || a.equals("Ữ") || a.equals("Ự")) {
                name = "\u1eec";
            }

            //------------------  Ỉ  ------------------
            if (a.equals("I") || a.equals("Ì") || a.equals("Í") || a.equals("Ĩ") || a.equals("Ị")) {
                name = "\u1ec8";
            }

            //------------------  Ỷ  ------------------
            if (a.equals("Y") || a.equals("Ỳ") || a.equals("Ý") || a.equals("Ỹ") || a.equals("Ỵ")) {
                name = "\u1ef6";
            }
        }
        /**Dấu ngã x*/
        if (str.equals("x")) {

            //------------------  ã  -------------------
            if (a.equals("a") || a.equals("à") || a.equals("á") || a.equals("ả") || a.equals("ạ")) {
                name = "\u00e3";
            }

            //------------------  ẵ  -----------------
            if (a.equals("ă") || a.equals("ằ") || a.equals("ắ") || a.equals("ẳ") || a.equals("ặ")) {
                name = "\u1eb5";
            }

            //------------------  ẫ  ----------------
            if (a.equals("â") || a.equals("ầ") || a.equals("ấ") || a.equals("ẩ") || a.equals("ậ")) {
                name = "\u1eab";
            }

            //------------------  ẽ  ---------------
            if (a.equals("e") || a.equals("è") || a.equals("é") || a.equals("ẻ") || a.equals("ẹ")) {
                name = "\u1ebd";
            }

            //------------------  ễ  ------------------
            if (a.equals("ê") || a.equals("ề") || a.equals("ế") || a.equals("ể") || a.equals("ệ")) {
                name = "\u1ec5";
            }

            //------------------  õ  ------------
            if (a.equals("o") || a.equals("ò") || a.equals("ó") || a.equals("ỏ") || a.equals("ọ")) {
                name = "\u00f5";
            }

            //------------------  ỗ  ---------------
            if (a.equals("ô") || a.equals("ồ") || a.equals("ố") || a.equals("ổ") || a.equals("ộ")) {
                name = "\u1ed7";
            }

            //------------------  ỡ  -----------------
            if (a.equals("ơ") || a.equals("ờ") || a.equals("ớ") || a.equals("ở") || a.equals("ợ")) {
                name = "\u1ee1";
            }

            //------------------  ũ   -------------------
            if (a.equals("u") || a.equals("ù") || a.equals("ú") || a.equals("ủ") || a.equals("ụ")) {
                name = "\u0169";
            }

            //------------------  ữ  -----------------
            if (a.equals("ư") || a.equals("ừ") || a.equals("ứ") || a.equals("ử") || a.equals("ự")) {
                name = "\u1eef";
            }

            //------------------  ĩ  ----------------
            if (a.equals("i") || a.equals("ì") || a.equals("í") || a.equals("ỉ") || a.equals("ị")) {
                name = "\u0129";
            }

            //------------------  ỹ   --------------
            if (a.equals("y") || a.equals("ỳ") || a.equals("ý") || a.equals("ỷ") || a.equals("ỵ")) {
                name = "\u1ef9";
            }
        }
        /**Dấu ngã X*/
        if (str.equals("X")) {
            //------------------  Ã  ------------------
            if (a.equals("A") || a.equals("À") || a.equals("Á") || a.equals("Ả") || a.equals("Ạ")) {
                name = "\u00c3";
            }

            //------------------  Ẫ  ------------------
            if (a.equals("Â") || a.equals("Ầ") || a.equals("Ấ") || a.equals("Ẩ") || a.equals("Ậ")) {
                name = "\u1eaa";
            }

            //------------------  Ẵ  ------------------
            if (a.equals("Ă") || a.equals("Ằ") || a.equals("Ắ") || a.equals("Ẳ") || a.equals("Ặ")) {
                name = "\u1eb4";
            }

            //------------------  Ẽ  ------------------
            if (a.equals("E") || a.equals("È") || a.equals("É") || a.equals("Ẻ") || a.equals("Ẹ")) {
                name = "\u1ebc";
            }

            //------------------  Ễ  ------------------
            if (a.equals("Ê") || a.equals("Ề") || a.equals("Ế") || a.equals("Ể") || a.equals("Ệ")) {
                name = "\u1ec4";
            }

            //------------------  Õ  ------------------
            if (a.equals("O") || a.equals("Ò") || a.equals("Ó") || a.equals("Ỏ") || a.equals("Ọ")) {
                name = "\u00d5";
            }

            //------------------  Ỗ  ------------------
            if (a.equals("Ô") || a.equals("Ồ") || a.equals("Ố") || a.equals("Ổ") || a.equals("Ộ")) {
                name = "\u1ed6";
            }

            //------------------  Ỡ  ------------------
            if (a.equals("Ơ") || a.equals("Ờ") || a.equals("Ớ") || a.equals("Ở") || a.equals("Ợ")) {
                name = "\u1ee0";
            }

            //------------------  Ũ  ------------------
            if (a.equals("U") || a.equals("Ù") || a.equals("Ú") || a.equals("Ủ") || a.equals("Ụ")) {
                name = "\u0168";
            }

            //------------------  Ữ  ------------------
            if (a.equals("Ư") || a.equals("Ừ") || a.equals("Ứ") || a.equals("Ử") || a.equals("Ự")) {
                name = "\u1eee";
            }

            //------------------  Ĩ  ------------------
            if (a.equals("I") || a.equals("Ì") || a.equals("Í") || a.equals("Ỉ") || a.equals("Ị")) {
                name = "\u0128";
            }

            //------------------  Ỹ  ------------------
            if (a.equals("Y") || a.equals("Ỳ") || a.equals("Ý") || a.equals("Ỷ") || a.equals("Ỵ")) {
                name = "\u1ef8";
            }
        }
        /**Dẫu nặng j*/
        if (str.equals("j")) {

            //------------------  ạ  ---------------
            if (a.equals("a") || a.equals("à") || a.equals("á") || a.equals("ả") || a.equals("ã")) {
                name = "\u1ea1";
            }

            //------------------  ặ  ----------------
            if (a.equals("ă") || a.equals("ằ") || a.equals("ắ") || a.equals("ẳ") || a.equals("ẵ")) {
                name = "\u1eb7";
            }

            //------------------  ậ  -----------------------
            if (a.equals("â") || a.equals("ầ") || a.equals("ấ") || a.equals("ẩ") || a.equals("ẫ")) {
                name = "\u1ead";
            }

            //------------------  ẹ  ------------------
            if (a.equals("e") || a.equals("è") || a.equals("é") || a.equals("ẻ") || a.equals("ẽ")) {
                name = "\u1eb9";
            }

            //------------------  ệ  ------------------
            if (a.equals("ê") || a.equals("ề") || a.equals("ế") || a.equals("ể") || a.equals("ễ")) {
                name = "\u1ec7";
            }

            //------------------  ọ  ------------------
            if (a.equals("o") || a.equals("ò") || a.equals("ó") || a.equals("ỏ") || a.equals("õ")) {
                name = "\u1ecd";
            }

            //------------------  ộ  ------------------
            if (a.equals("ô") || a.equals("ồ") || a.equals("ố") || a.equals("ổ") || a.equals("ỗ")) {
                name = "\u1ed9";
            }

            //------------------  ợ  ------------------
            if (a.equals("ơ") || a.equals("ờ") || a.equals("ớ") || a.equals("ở") || a.equals("ỡ")) {
                name = "\u1ee3";
            }

            //------------------  ụ  ------------------
            if (a.equals("u") || a.equals("ù") || a.equals("ú") || a.equals("ủ") || a.equals("ũ")) {
                name = "\u1ee5";
            }

            //------------------  ự  ------------------
            if (a.equals("ư") || a.equals("ừ") || a.equals("ứ") || a.equals("ử") || a.equals("ữ")) {
                name = "\u1ef1";
            }

            //------------------  ị  ------------------
            if (a.equals("i") || a.equals("ì") || a.equals("í") || a.equals("ỉ") || a.equals("ĩ")) {
                name = "\u1ecb";
            }

            //------------------  ỵ  ------------------
            if (a.equals("y") || a.equals("ỳ") || a.equals("ý") || a.equals("ỷ") || a.equals("ỹ")) {
                name = "\u1ef5";
            }
        }
        /**Dấu nặng J*/
        if (str.equals("J")) {
            //------------------  Ạ  ------------------
            if (a.equals("A") || a.equals("À") || a.equals("Á") || a.equals("Ả") || a.equals("Ã")) {
                name = "\u1ea0";
            }

            //------------------  Ậ  ------------------
            if (a.equals("Â") || a.equals("Ầ") || a.equals("Ấ") || a.equals("Ẩ") || a.equals("Ẫ")) {
                name = "\u1eac";
            }

            //------------------  Ặ  ------------------
            if (a.equals("Ă") || a.equals("Ằ") || a.equals("Ắ") || a.equals("Ẳ") || a.equals("Ẵ")) {
                name = "\u1eb6";
            }

            //------------------  Ẹ  ------------------
            if (a.equals("E") || a.equals("È") || a.equals("É") || a.equals("Ẻ") || a.equals("Ẽ")) {
                name = "\u1eb8";
            }

            //------------------  Ệ  ------------------  
            if (a.equals("Ê") || a.equals("Ề") || a.equals("Ế") || a.equals("Ể") || a.equals("Ễ")) {
                name = "\u1ec6";
            }

            //------------------  Ọ  ------------------
            if (a.equals("O") || a.equals("Ò") || a.equals("Ó") || a.equals("Ỏ") || a.equals("Õ")) {
                name = "\u1ecc";
            }

            //------------------  Ộ  ------------------
            if (a.equals("Ô") || a.equals("Ồ") || a.equals("Ố") || a.equals("Ổ") || a.equals("Ỗ")) {
                name = "\u1ed8";
            }

            //------------------  Ợ  ------------------
            if (a.equals("Ơ") || a.equals("Ờ") || a.equals("Ớ") || a.equals("Ở") || a.equals("Ỡ")) {
                name = "\u1ee2";
            }

            //------------------  Ụ  ------------------
            if (a.equals("U") || a.equals("Ù") || a.equals("Ú") || a.equals("Ủ") || a.equals("Ũ")) {
                name = "\u1ee4";
            }

            //------------------  Ự  ------------------
            if (a.equals("Ư") || a.equals("Ừ") || a.equals("Ứ") || a.equals("Ử") || a.equals("Ữ")) {
                name = "\u1ef0";
            }

            //------------------  Ị  ------------------
            if (a.equals("I") || a.equals("Ì") || a.equals("Í") || a.equals("Ỉ") || a.equals("Ĩ")) {
                name = "\u1eca";
            }

            //------------------  Ỵ  ------------------
            if (a.equals("Y") || a.equals("Ỳ") || a.equals("Ý") || a.equals("Ỷ") || a.equals("Ỹ")) {
                name = "\u1ef4";
            }
        }
        /**Chữ â*/
        if (str.equals("a")) {
            if (a.equals("a")) {
                name = "\u00e2";
            }
        }
        /**Chữ Â*/
        if (str.equals("A")) {
            if (a.equals("A")) {
                name = "\u00c2";
            }
        }
        /**Chữ ê*/
        if (str.equals("e")) {
            if (a.equals("e")) {
                name = "\u00ea";
            }
        }
        /**Chữ Ê*/
        if (str.equals("E")) {
            if (a.equals("E")) {
                name = "\u00ca";
            }
        }
        /**Chữ ô*/
        if (str.equals("o")) {
            if (a.equals("o")) {
                name = "\u00f4";
            }
        }
        /**Chữ Ô*/
        if (str.equals("O")) {
            if (a.equals("O")) {
                name = "\u00d4";
            }
        }
        /**Chữ đ*/
        if (str.equals("d")) {
            if (a.equals("d")) {
                name = "\u0111";
            }
        }
        /**Chữ Đ*/
        if (str.equals("D")) {
            if (a.equals("D")) {
                name = "\u0110";
            }
        }
        /**dấu w*/
        if (str.equals("w")) {
            /**Chữ ư*/
            if (a.equals("u")) {
                name = "\u01b0";
            }
            /**Chữ ơ*/
            if (a.equals("o")) {
                name = "\u01a1";
            }
            /**Chữ ă*/
            if (a.equals("a")) {
                name = "\u0103";
            }
        }
        /**dấu W*/
        if (str.equals("W")) {
            if (a.equals("A")) {
                name = "\u0102";
            }
            if (a.equals("U")) {
                name = "\u01af";
            }
            if (a.equals("O")) {
                name = "\u01a0";
            }
        }
//        System.out.println("Trả về vietnamese: " + name);
        return name;
    }
}
