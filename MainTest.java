/**
 * Created by Виталик on 04.10.2017.
 */
import static java.lang.Character.*;

public class MainTest {

    public  String checkText(String startText){
        String text = startText;
        text = text.trim();
        StringBuilder bufferText = new StringBuilder(text);
        char symbol;
        //Флажок для определения заглавной буквы true - заглавная, false - прописная
        boolean flagBigLetter = true;
        try {
            for (int i = 0; i < bufferText.length(); i++) {
                symbol = bufferText.charAt(i);
                //Проверка правильноти написания прописной и заглавной буквы
                if (isLetter(symbol)) {
                    if (flagBigLetter) {
                        flagBigLetter = false;
                        bufferText.setCharAt(i, toUpperCase(symbol));
                    } else if (isUpperCase(symbol)) bufferText.setCharAt(i, toLowerCase(symbol));
                    //Оставляем только один пробел между словами
                }else if (symbol == ' ' && bufferText.charAt(i + 1) == ' '){
                    while (i < bufferText.length() - 1 && bufferText.charAt(i + 1) == ' ') bufferText.deleteCharAt(i + 1);
                    //Проверяем правильность написания апосторофа
                }else if (bufferText.charAt(i) == '\'' || i < bufferText.length() - 1 && bufferText.charAt(i + 1) == '\''){
                    if (bufferText.charAt(i) == ' ' && bufferText.charAt(i + 1) == '\''){
                        //Удаляем пробелы перед апастрофом
                        bufferText.setCharAt(i, '\'');
                        bufferText.deleteCharAt(i + 1);
                    }
                    //Удаляем пробелы за апострофом
                    while (i < bufferText.length() - 1 && !isLetter(bufferText.charAt(i + 1))) bufferText.deleteCharAt(i + 1);
                }
                //Проверка правильности написания знаков пунктуации
                else if (symbol == '.' || symbol == '!' || symbol == '?' || symbol == ',' || symbol == ';' || symbol == ':') {
                    if (i > 0 && i < bufferText.length() - 1 && isDigit(bufferText.charAt(i - 1)) && isDigit(bufferText.charAt(i + 1))) continue;
                    if (i > 0 && bufferText.charAt(i - 1) == ' ') bufferText.deleteCharAt(i - 1);
                    if (i < bufferText.length() && bufferText.charAt(i) != ' ') bufferText.insert(i, ' ');
                }
                //Определяем конец предложения по знакам пунктуации и присваиваем значение флагу заглавной буквы
                if ((symbol == '.' || symbol == '!' || symbol == '?') && i > 0 && i < bufferText.length() - 1 &&
                        !isDigit(bufferText.charAt(i - 1)) && !isDigit(bufferText.charAt(i + 1))) flagBigLetter = true;
            }
        }catch (StringIndexOutOfBoundsException e){
            System.out.println("Выход за границы масива строки");
            e.printStackTrace();
        }
        text = new String(bufferText);
        return text;
    }

    public static void main(String[] args) {
        String example1 =  "with its powerful tools and dazzling effects,Keynote makes it Easy to create stunning and memorable presentations. ";
        String example2 = "See Who you 're working with ... While you're editing, a separate list lets you quickly see who else is in the presentation.";
        String output1 = "With its powerful tools and dazzling effects, keynote makes it easy to create stunning and memorable presentations.";
        String output2 = "See who you're working with... While you're editing, a separate list lets you quickly see who else is in the presentation.";
        MainTest mainTest = new MainTest();
        System.out.println(output1.equals(mainTest.checkText(example1))? "Test 1 ok":"Test 1 bad");
        System.out.println(output2.equals(mainTest.checkText(example2))? "Test 2 ok":"Test 2 bad");
    }
}
