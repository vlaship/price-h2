package vlaship.price.h2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vlaship.price.h2.repository.ProductRepository;
import vlaship.price.h2.service.UpdateDbService;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class RootController {

    private final UpdateDbService updateDbService;
    private final ProductRepository productRepository;

    @GetMapping("/")
    public ModelAndView root() {
        return defaultModel();
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam String searchTerm) {
        final var products = productRepository.findByNameProductContainingIgnoreCase(searchTerm);
        final var modelAndView = defaultModel();
        modelAndView.addObject("products", products);
        modelAndView.addObject("searchTerm", searchTerm);
        return modelAndView;
    }

    @GetMapping("/update")
    public ModelAndView update() {
        updateDbService.update();
        final var modelAndView = defaultModel();
        modelAndView.addObject("updated", true);
        return modelAndView;
    }

    private ModelAndView defaultModel() {
        final var modelAndView = new ModelAndView();
        modelAndView.addObject("searchTerm", "");
        modelAndView.addObject("products", Collections.emptyList());
        modelAndView.addObject("dateUpdated", "123");
        modelAndView.addObject("updated", false);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
