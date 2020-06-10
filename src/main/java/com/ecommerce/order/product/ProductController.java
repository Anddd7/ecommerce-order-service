package com.ecommerce.order.product;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import com.ecommerce.order.common.utils.PagedResource;
import com.ecommerce.order.product.representation.ProductSummaryRepresentation;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductApplicationService service;

    public ProductController(ProductApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ProductId createProduct(@RequestBody @Valid CreateProductCommand command) {
        return service
            .createProduct(command.getName(), command.getDescription(), command.getPrice());
    }

    @GetMapping
    public PagedResource<ProductSummaryRepresentation> pagedProducts(
        @RequestParam(required = false, defaultValue = "1") int pageIndex,
        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        // TODO: 获取Product列表
        return null;
    }


    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Object byId(@PathVariable(name = "id") String id) {
        // TODO: 获取单个Product详情
        return null;
    }

}
