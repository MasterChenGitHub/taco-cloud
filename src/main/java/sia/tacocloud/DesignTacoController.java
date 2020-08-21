package sia.tacocloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController extends Loggable {
    private final IngredientRepository ingredientRepository;

    private  TacoRepository designRepo;

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }


    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository designRepo) {
        this.ingredientRepository = ingredientRepository;
        this.designRepo = designRepo;
    }


    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type t : types) {
            model.addAttribute(t.toString().toLowerCase(), ingredients.stream()
                    .filter(it -> it.getType() == t)
                    .collect(Collectors.toList()));
        }

        model.addAttribute("design", new Taco());
        i(model.toString());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors,@ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }
        i("Processing design: " + design);
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }
}
