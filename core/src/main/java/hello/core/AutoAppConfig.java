package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core", // 탐색할 시작위치 지정하기 이거를 안하면 기본적으로 AutoAppConfig 설정 파일이 위치한 패키지가 시작 위치가됨! => 여기서는 파일이 package hello.core 에 있으므로, 여기서 하위를 다 탐색하게 됨!
        // 그래서 프로젝트의 메인 설정 정보를 프로젝트 시작 루트 튀치에 두는 것이 좋음!
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
) // @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록!, 아까 수동으로 등록(@Configuration)한 애들 뺴주기!
public class AutoAppConfig {
    // @Bean으로 등록한 클래스없음!

    /* 이전에는 @Bean으로 직접 연결 정보를 작성하고, 설정하는 과정이 있었지만
    이제는 없기 때문에 @Component 어노테이션을 붙인 곳을 스프링 빈으로 등록한다. 빈 이름은 클래스 이름으로!
     그러면 생성자 호출하며 생기는 기존의 의존관계는 어떻게 하지?
     -> 생성자에 @Autowired를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입!
     */

    /*
    컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함됨!
    @Component : 컴포넌트 스캔에서 사용
    @Controller : 스프링 MVC 컨트롤러에서 사용
    @Service : 스프링 비즈니스 로직에서 사용
    @Repository : 스프링 데이터 접근 계층에서 사용
    @Configuration : 스프링 설정 정보에서 사용

    그래서 기존의 프로젝트에서는 왜 @Component를 붙일일이 없었지라는 생각이 들었는데 그래서 그런거 였구나!!!... 이걸 이제야 알다니...
     */
}
