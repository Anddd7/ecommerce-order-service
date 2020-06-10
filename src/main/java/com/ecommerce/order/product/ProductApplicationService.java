package com.ecommerce.order.product;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductApplicationService {

  private final ProductRepository repository;

  public ProductApplicationService(ProductRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public ProductId createProduct(String name, String description, BigDecimal price) {
    Product product = Product.create(name, description, price);
    repository.save(product);
    return product.getId();
  }
}
