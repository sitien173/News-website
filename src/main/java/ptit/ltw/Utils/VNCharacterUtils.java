package ptit.ltw.Utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class VNCharacterUtils {
    public static String removeAccent(String textUtf8){
        String str = textUtf8.replaceAll("\\s+","-").toLowerCase().replace("đ","d");
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
