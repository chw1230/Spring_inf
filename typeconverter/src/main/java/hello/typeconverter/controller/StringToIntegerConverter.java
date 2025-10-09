package hello.typeconverter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String source) {
        // 문자를 숫자로 바꾸는 컨버터
        log.info("convert source={}", source);
        return Integer.valueOf(source);
    }

}
