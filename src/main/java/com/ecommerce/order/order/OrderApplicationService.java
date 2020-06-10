package com.ecommerce.order.order;

import static com.ecommerce.order.order.model.OrderId.orderId;

import com.ecommerce.order.common.ddd.ApplicationService;
import com.ecommerce.order.order.model.Order;
import com.ecommerce.order.order.model.OrderFactory;
import com.ecommerce.order.order.model.OrderId;
import com.ecommerce.order.order.model.OrderItem;
import com.ecommerce.order.order.model.OrderService;
import com.ecommerce.order.order.representation.OrderRepresentation;
import com.ecommerce.order.order.representation.OrderRepresentationService;
import com.ecommerce.order.product.ProductId;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderApplicationService implements ApplicationService {

    private final OrderRepresentationService representationService;
    private final OrderRepository repository;
    private final OrderFactory factory;
    private final OrderPaymentProxy proxy;
    private final OrderService service = new OrderService();

    public OrderApplicationService(OrderRepresentationService representationService,
        OrderRepository repository, OrderFactory factory,
        OrderPaymentProxy proxy) {
        this.representationService = representationService;
        this.repository = repository;
        this.factory = factory;
        this.proxy = proxy;
    }

    @Transactional(readOnly = true)
    public OrderRepresentation byId(String id) {
        Order order = repository.byId(orderId(id));
        return representationService.toRepresentation(order);
    }

    @Transactional
    public OrderId createOrder(List<OrderItem> items) {
        Order order = factory.create(items);
        repository.save(order);
        return order.getId();
    }

    @Transactional
    public void updateProductCount(OrderId orderId, ProductId productId, int count) {
        Order order = repository.byId(orderId);
        order.updateProductCount(productId, count);
        repository.save(order);
    }

    @Transactional
    public void pay(OrderId orderId, BigDecimal paidPrice) {
        Order order = repository.byId(orderId);
        service.tryPay(order, paidPrice);
        proxy.pay(orderId, paidPrice);
        repository.save(order);
    }
}
