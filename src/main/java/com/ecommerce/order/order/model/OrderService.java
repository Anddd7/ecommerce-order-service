package com.ecommerce.order.order.model;

import com.ecommerce.order.order.exception.PaidPriceNotSameWithOrderPriceException;
import java.math.BigDecimal;

public class OrderService {


  public void tryPay(Order order, BigDecimal paidPrice) {
    if (order.getTotalPrice().equals(paidPrice)) {
      order.pay();
    } else {
      throw new PaidPriceNotSameWithOrderPriceException(order.getId());
    }
  }
}
