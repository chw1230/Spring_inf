package hello.itemservice.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesResolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required","item");
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required",
                "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
    /*
    FieldError rejectValue("itemName", "required")
    다음 4가지 오류 코드를 자동으로 생성
    required.item.itemName
    required.itemName
    required.java.lang.String
    required

    ObjectError reject("totalPriceMin")
    다음 2가지 오류 코드를 자동으로 생성
    totalPriceMin.item
    totalPriceMin

    타임리프 화면을 렌더링 할 때 th:errors 가 실행
    만약 이때 오류가 있다면 생성된 오류 메시지 코드를 순서대로 돌아가면서 메시지를 찾고
    없으면 디폴트 메시지를 출력
     */
}
