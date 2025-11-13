package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.data.data_transfer_objects.common.PageResponseDTO;
import org.example.data.data_transfer_objects.product.ProductListItemDTO;
import org.example.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/list")
    public String listUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size,
            Model model) {
        PageResponseDTO<ProductListItemDTO> pageResponse = productService
                .getAllPaginated(page, size);
        model.addAttribute("products", pageResponse.getContent());
        model.addAttribute("page", pageResponse.getPage());
        return "products/list";
    }
}
