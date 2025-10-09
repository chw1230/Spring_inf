package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> {

    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        // "1,000"  이거를 그냥 생 숫자 1000으로 바꾸는 과정!
        log.info("text={}, locale={}", text, locale);
        NumberFormat format = NumberFormat.getInstance(locale);
        return format.parse(text); // parse : 문자를 객체로!
    }

    @Override
    public String print(Number object, Locale locale) {
        // 1000을 "1,000로 바꾸기
        log.info("object={}, locale={}", object, locale);
        return NumberFormat.getInstance(locale).format(object); // 객체를 문자로!
    }

    /*
    숫자 중간에 ' , ' 잇는 형태 NumberFormatter 사용하기
     */
}
