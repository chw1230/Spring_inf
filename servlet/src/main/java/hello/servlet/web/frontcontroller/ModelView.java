package hello.servlet.web.frontcontroller;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class ModelView {
    private String viewName; // 뷰의 논리적 이름!
    private Map<String, Object> model = new HashMap<>(); // 모델

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

//    뷰의 이름과 뷰를 렌더링할 때 필요한 model 객체를 가지고 있음, model은 단순히 map으로 되어 있으므로 컨트롤러에서 뷰에 필요한 데이터를 key, value로 넣어주면 됨
}
