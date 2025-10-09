package hello.typeconverter;

import hello.typeconverter.controller.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {
    @Override
    public IpPort convert(String source) {
//        127.0.0.1:8080 같은 문자를 입력하면 IpPort 객체를 만들어 반환하는 컨버터
        log.info("convert source={}", source);

        String[] split = source.split(":");

        String ip = split[0];
        int port = Integer.parseInt(split[1]);

        return new IpPort(ip, port);
    }
}