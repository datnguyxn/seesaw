package com.seesaw.controller;

import com.seesaw.service.CategoryService;
import com.seesaw.service.CollectionService;
import com.seesaw.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final CategoryService categoryService;
    private final CollectionService collectionService;
    private final ProductService productService;

    @GetMapping(value = {"", "/"})
    public String products(
            @RequestParam(name = "type", defaultValue = "all") String type, // all or id category
            Model model
    ) {
        if (type.equalsIgnoreCase("all")) {
            model.addAttribute("category", categoryService.getAll());
        } else {
            model.addAttribute("category", categoryService.getCategoryById(type));
        }
        model.addAttribute("type", type);
        return "pages/user/products";
    }

    @GetMapping(value =  "/collection")
    public String product(
            @RequestParam(name = "id") String id, // id collection
            Model model
    ) {
        model.addAttribute("products", productService.getAllProductOfCollection(0,10, id));
        return "pages/user/collection";
    }
}
