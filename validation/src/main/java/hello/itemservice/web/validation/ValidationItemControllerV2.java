package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final View error;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        log.info("init binder {}", dataBinder);
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //    @PostMapping("/add")
//    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

    /// /        RedirectAttributes 이게 -> Map<String,String > errors = new HashMap<>(); 이 역할을 대신한다!
//
//        // 검증 로직 - 특정 필드 하나에 대해서만!
//        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.addError(new FieldError("item", "itemName", "Item name 은 필수 입니다.")); // FieldError -> 필드 단위 검증
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item", "price", "가격은 1000 ~ 1000000 사이에서 허용"));
//        }
//        if (item.getQuantity() == null || item.getQuantity() >= 999) {
//            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용"));
//        }
//
//        //특정 필드가 아닌 복합적으로!
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity(); //가격 * 수량
//            if (resultPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상 / 현재 값 = " + resultPrice));
//            }
//        }
//
//        // 검증 실패 -> 다시 입력 폼으로
//        if (bindingResult.hasErrors()) { // 에러 있으면
//            log.info("errors : {}", bindingResult);
//            return "validation/v2/addForm";
//        }
//
//        // 성공한 경우에
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//        // 한계점 : 타입 오류 처리 불가능
//    }
//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.addError(new FieldError("item", "itemName",
//                    item.getItemName(), false, null, null, "상품 이름은 필수입니다."));
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() >
//                1000000) {
//            bindingResult.addError(new FieldError("item", "price", item.getPrice(),
//                    false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
//        }
//        if (item.getQuantity() == null || item.getQuantity() >= 10000) {
//            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));
//            //특정 필드 예외가 아닌 전체 예외
//            if (item.getPrice() != null && item.getQuantity() != null) {
//                int resultPrice = item.getPrice() * item.getQuantity();
//                if (resultPrice < 10000) {
//                    bindingResult.addError(new ObjectError("item", null, null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
//                }
//            }
//        }
//        if (bindingResult.hasErrors()) {
//            log.info("errors={}", bindingResult);
//            return "validation/v2/addForm";
//        }
//        //성공 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }
//
//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

    /// /        RedirectAttributes 이게 -> Map<String,String > errors = new HashMap<>(); 이 역할을 대신한다!
//
//        // 검증 로직 - 특정 필드 하나에 대해서만!
//        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null)); // FieldError -> 필드 단위 검증
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
//        }
//        if (item.getQuantity() == null || item.getQuantity() >= 999) {
//            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, null, null));
//        }
//
//        //특정 필드가 아닌 복합적으로!
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity(); //가격 * 수량
//            if (resultPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
//            }
//        }
//        /*
//        codes : 메시지 코드 -> 메시지 코드를 지정
//        arguments : 메시지에서 사용하는 인자 -> Object[]{1000, 1000000} 를 사용해서 코드의 {0} , {1}에 치환할 값을 전달
//
//        FieldError 는 오류 발생시 사용자 입력 값을 저장하는 기능 제공!
//         */
//
//        // 검증 실패 -> 다시 입력 폼으로
//        if (bindingResult.hasErrors()) { // 에러 있으면
//            log.info("errors : {}", bindingResult);
//            return "validation/v2/addForm";
//        }
//
//        // 성공한 경우에
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//        // 한계점 : 타입 오류 처리 불가능
//    }
//    @PostMapping("/add")
//    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        // rejectValue() , reject() 를 사용하면 FieldError , ObjectError 를 직접 생성하지 않고, 깔끔하게 검증 오류를 다룰 수 있다.
//        log.info("objectName={}", bindingResult.getObjectName());
//        log.info("target={}", bindingResult.getTarget());
//
//        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.rejectValue("itemName", "required");
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
//            if (item.getQuantity() == null || item.getQuantity() > 10000) {
//                bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
//            }
//        }
//        //특정 필드 예외가 아닌 전체 예외
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
//            }
//        }
//
//        if (bindingResult.hasErrors()) {
//            log.info("errors={}", bindingResult);
//            return "validation/v2/addForm";
//        }
//        //성공 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//        /*
//        void rejectValue(@Nullable String field, String errorCode, @Nullable Object[] errorArgs, @Nullable String defaultMessage)
//        field : 오류 필드명
//        errorCode : 오류 코드(이 오류 코드는 메시지에 등록된 코드가 아니다. 뒤에서 설명할 messageResolver를 위한 오류 코드이다.)
//        errorArgs : 오류 메시지에서 {0} 을 치환하기 위한 값 메시지
//        defaultMessage : 오류 메시지를 찾을 수 없을 때 사용하는 기본
//
//
//        FieldError() 를 직접 다룰 때는 오류 코드를 range.item.price 와 같이 모두 입력했음
//        그런데 rejectValue() 를 사용하고 부터는 오류 코드를 range 로 간단하게 입력할 수 있었음!! -> 이거는 MessageCodesResolver를 통해서 이해 해야함
//         */
//    }
//    @PostMapping("/add")
//    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//
//        itemValidator.validate(item, bindingResult); // 검증기 사용하기
//
//        if (bindingResult.hasErrors()) {
//            log.info("errors={}", bindingResult);
//            return "validation/v2/addForm";
//        }
//        //성공 로직
//        Item savedItem = itemRepository.save(item);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/validation/v2/items/{itemId}";
//    }

    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // itemValidator.validate(item, bindingResult); // 검증기 사용하기
        // @Validated -> 이 애노테이션으로 위의 코드의 역할을 실행해줌!!
        // 검증기가 어떤 거를 사용하지? -> support => 에 Item 객체가 넘어가서 사용!
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }
        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }
}

