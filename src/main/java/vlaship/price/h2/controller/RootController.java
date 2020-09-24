package vlaship.price.h2.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vlaship.price.h2.repository.ProductRepository;
import vlaship.price.h2.service.UpdateDbService;

import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

@Controller
@RequiredArgsConstructor
public class RootController {

    private final UpdateDbService updateDbService;
    private final ProductRepository productRepository;
    private final ServletContext servletContext;

    @GetMapping("/")
    public ModelAndView root() {
        return defaultModel();
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam String searchTerm, Pageable pageable) {
        final var modelAndView = defaultModel();
        if (StringUtils.isBlank(searchTerm)) return modelAndView;
        modelAndView.addObject("searchTerm", searchTerm);
        if (searchTerm.length() < 3) return modelAndView;
        final var products = productRepository.findByNameProductContainingIgnoreCase(searchTerm, pageable);
        modelAndView.addObject("products", products);
        modelAndView.addObject("searchTerm", searchTerm);
        return modelAndView;
    }

    @GetMapping("/update")
    public ModelAndView update() {
        updateDbService.update();
        servletContext.setAttribute("dateUpdated", getDate());
        final var modelAndView = defaultModel();
        modelAndView.addObject("updated", true);
        modelAndView.addObject("dateUpdated", servletContext.getAttribute("dateUpdated"));
        return modelAndView;
    }

    private ModelAndView defaultModel() {
        final var dateUpdated = servletContext.getAttribute("dateUpdated");
        final var modelAndView = new ModelAndView();
        modelAndView.addObject("searchTerm", "");
        modelAndView.addObject("products", Collections.emptyList());
        modelAndView.addObject("dateUpdated", dateUpdated == null ? "" : dateUpdated);
        modelAndView.addObject("updated", false);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    private String getDate() {
        final var formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Minsk"));
        return formatter.format(new Date());
    }
}
