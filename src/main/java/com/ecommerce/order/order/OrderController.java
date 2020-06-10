package com.ecommerce.order.order;

import static org.springframework.http.HttpStatus.CREATED;

import com.ecommerce.order.order.command.CreateOrderCommand;
import com.ecommerce.order.order.command.PayOrderCommand;
import com.ecommerce.order.order.command.UpdateProductCountCommand;
import com.ecommerce.order.order.model.OrderId;
import com.ecommerce.order.order.model.OrderItem;
import com.ecommerce.order.order.representation.OrderRepresentation;
import com.ecommerce.order.product.ProductId;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderApplicationService service;

    public OrderController(OrderApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public OrderId createOrder(@RequestBody @Valid CreateOrderCommand command) {
        return service.createOrder(
            command.getItems().stream()
                .map(item ->
                    OrderItem.create(
                        ProductId.productId(item.getProductId()),
                        item.getCount(),
                        item.getItemPrice()
                    )
                )
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/{id}")
    public void updateProductCount(@PathVariable(name = "id") String id, @RequestBody @Valid UpdateProductCountCommand command) {
        service.updateProductCount(
            OrderId.orderId(id),
            ProductId.productId(command.getProductId()),
            command.getCount()
        );
    }

    @PostMapping("/{id}/payment")
    public void pay(@PathVariable(name = "id") String id, @RequestBody @Valid PayOrderCommand command) {
        service.pay(OrderId.orderId(id), command.getPaidPrice());
    }

    @GetMapping("/{id}")
    public OrderRepresentation byId(@PathVariable(name = "id") String id) {
        return service.byId(id);
    }
}
