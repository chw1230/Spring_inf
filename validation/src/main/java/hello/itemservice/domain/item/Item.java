package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
//@ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000")  --> 권장되지 않음!
public class Item {


//    @NotNull(groups = Updatecheck.class) // 수정 할때 필수 값을 가지도록 하겠다
    private Long id;

//    @NotBlank(groups = {Updatecheck.class, Savecheck.class})
    private String itemName;

//    @NotNull(groups = {Updatecheck.class, Savecheck.class})
//    @Range(min = 1000, max = 1000000 ,groups = {Updatecheck.class, Savecheck.class})
    private Integer price;

//    @NotNull(groups = {Updatecheck.class, Savecheck.class})
//    @Max(value = 9999, groups = Savecheck.class) // 등록할 때만 최대값을 지정하겠다
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}