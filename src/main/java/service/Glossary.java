package service;

import java.util.HashMap;
/** Серверный словарь содержит наборы символов, которым соответствуют определённые unicode эмодзи
 * @author Сергей Шершавин*/
public class Glossary {
    private static HashMap<String, String> map = new HashMap<>();

    public static HashMap<String, String> getMap() {
        map.put("хех", "\uD83D\uDE00");
        map.put("хах", "\uD83D\uDE01");
        map.put(":Д", "\uD83D\uDE02");
        map.put(":д", "\uD83D\uDE02");
        map.put(":\\-Д", "\uD83D\uDE04");
        map.put(":\\-д", "\uD83D\uDE04");
        map.put("ХД", "\uD83D\uDE06");
        map.put("хд", "\uD83D\uDE06");
        map.put(";\\)", "\uD83D\uDE09");
        map.put(":\\)", "\uD83D\uDE0A");
        map.put(":\\-\\)", "\uD83D\uDE42");
        map.put("8\\-\\)", "\uD83D\uDE0E");
        map.put(";Р", "\uD83D\uDE0B");
        map.put(";р", "\uD83D\uDE0B");
        map.put("лбв", "\uD83D\uDE0D");
        map.put("ммм", "\uD83D\uDE0F");
        map.put(":\\(", "\uD83D\uDE12");
        map.put("З/", "\uD83D\uDE14");
        map.put("з/", "\uD83D\uDE14");
        map.put("брр", "\uD83D\uDE16");
        map.put(":\\*", "\uD83D\uDE18");
        map.put(":З", "\uD83D\uDE1A");
        map.put(":з", "\uD83D\uDE1A");
        map.put(";\\-р", "\uD83D\uDE1C");
        map.put(";\\-Р","\uD83D\uDE1C");
        map.put("Х\\-Р","\uD83D\uDE1D");
        map.put("х\\-р","\uD83D\uDE1D");
        map.put(":\\-\\(","\uD83D\uDE1E");
        map.put("блэт","\uD83D\uDE20");
        map.put("блядь","\uD83D\uDE21");
        map.put("ёпт","\uD83D\uDE23");
        map.put("пшш","\uD83D\uDE24");
        map.put("ужс","\uD83D\uDE28");
        map.put("Х\\(\\)","\uD83D\uDE2B");
        map.put("х\\(\\)","\uD83D\uDE2B");
        map.put("т_т","\uD83D\uDE2D");
        map.put("Т_Т","\uD83D\uDE2D");
        map.put("0о0","\uD83D\uDE31");
        map.put("ОоО","\uD83D\uDE31");
        map.put("О\\.О","\uD83D\uDE32");
        map.put("о\\.о","\uD83D\uDE32");
        map.put("0\\.0","\uD83D\uDE32");
        map.put("0_0","\uD83D\uDE33");
        map.put("О_О","\uD83D\uDE33");
        map.put("о_о","\uD83D\uDE33");
        map.put("х\\-о","\uD83D\uDE35");
        map.put("Х\\-О","\uD83D\uDE35");
        map.put("крнврс","\uD83D\uDE37");
        map.put("упс","\uD83D\uDE48");
        map.put("нслш","\uD83D\uDE49");
        map.put("млч","\uD83D\uDE4A");
        map.put("млтв","\uD83D\uDE4F");
        map.put("гвн","\uD83D\uDCA9");
        map.put("Зенит\\W{0,2}\\s","\uD83D\uDCA9 ");
        map.put("рклц","\uD83E\uDD26\u200D");
        map.put("двл","\uD83D\uDE08");
        map.put("срдц","❤");
        map.put("вххл","\uD83E\uDD37\u200D");
        map.put("хмм","\uD83E\uDD14");
        map.put("смрт","☠");
        map.put("пршлц","\uD83D\uDC7D");
        map.put("клн","\uD83E\uDD21");
        map.put("\\(:","\uD83D\uDE43");
        map.put("омг","\uD83D\uDE44");
        map.put("глз","\uD83D\uDC40");
        map.put("нйс","\uD83D\uDC4D");
        map.put("окей","\uD83D\uDC4C");
        map.put("роцк","\uD83E\uDD18");
        map.put("птх","\uD83D\uDC13");
        map.put("кокон\\W{0,2}\\s","\uD83D\uDC13 ");
        map.put("Кокон\\W{0,2}\\s","\uD83D\uDC13 ");
        map.put("вктр","\uD83D\uDC13");
        return map;
    }

}
