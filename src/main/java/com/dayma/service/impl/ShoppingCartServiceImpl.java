package com.dayma.service.impl;

import com.dayma.dto.*;
import com.dayma.mapper.ProductMapper;
import com.dayma.mapper.ProductOrderMapper;
import com.dayma.mapper.ShoppingCartMapper;
import com.dayma.mapper.SizeMapper;
import com.dayma.model.*;
import com.dayma.repository.*;
import com.dayma.service.ProductService;
import com.dayma.service.ShoppingCartService;
import com.dayma.service.SizeService;
import com.dayma.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductService productService;
    private final UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductMapper productMapper;
    private final SizeMapper sizeMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final SizeService sizeService;
    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ProductOrderMapper productOrderMapper;
    private final ProductSizeRepository productSizeRepository;

    @Override
    @Transactional
    public void addShoppingCart(AddProductRequest shoppingCart) {
        ProductDto product = productService.getProduct(shoppingCart.getCode());
        SizeDto size = sizeService.getByCode(shoppingCart.getSizeCode());
        User user = userService.getCurrentUser();
        ShoppingCart shopCart = shoppingCartRepository
                .findByAppUserEmailAndProductCodeAndSizeCode(
                        user.getEmail(), shoppingCart.getCode(), shoppingCart.getSizeCode());
        if (shopCart == null) {
            shopCart = ShoppingCart.builder()
                    .product(productMapper.toEntity(product))
                    .appUser(user)
                    .quantity(shoppingCart.getQuantity() != null ? shoppingCart.getQuantity() : 1)
                    .size(sizeMapper.toEntity(size))
                    .build();
        } else {
            shopCart.setQuantity(shoppingCart.getQuantity() != null ? shoppingCart.getQuantity() : 1);
        }
        shoppingCartRepository.save(shopCart);
    }

    @Override
    public void updateShoppingCart(AddProductRequest shoppingCart) {
        User user = userService.getCurrentUser();
        ShoppingCart shopCart = shoppingCartRepository
                .findByAppUserEmailAndProductCodeAndSizeCode(
                        user.getEmail(), shoppingCart.getCode(), shoppingCart.getSizeCode());
        if (shopCart != null) {
            shopCart.setQuantity(shoppingCart.getQuantity());
            shoppingCartRepository.save(shopCart);
        }
    }

    @Override
    public void deleteFromShoppingCart(String productCode, String sizeCode) {
        User user = userService.getCurrentUser();
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByAppUserEmailAndProductCodeAndSizeCode(
                        user.getEmail(), productCode, sizeCode);
        if (shoppingCart != null) {
            shoppingCartRepository.delete(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCartDto> getShoppingCart() {
        return shoppingCartMapper.toDtoList(shoppingCartRepository.findByAppUserEmail(userService.getCurrentUser().getEmail()));
    }

    @Override
    @Transactional
    public List<ProductOrderDto> finishPurchase() {
        User user = userService.getCurrentUser();
        List<ShoppingCart> cartItems = shoppingCartRepository.findByAppUserEmail(user.getEmail());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        OrderStatus status = orderStatusRepository.findByCode("PENDING");
        if (status == null) {
            status = orderStatusRepository.save(OrderStatus.builder()
                    .status("Pendiente")
                    .code("PENDING")
                    .build());
        }

        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        Order order = Order.builder()
                .appUser(user)
                .total(total)
                .status(status)
                .date(LocalDateTime.now())
                .build();
        order = orderRepository.save(order);

        order.setCode("DM-" + order.getId());

        List<ProductOrder> productOrders = new ArrayList<>();
        for (ShoppingCart item : cartItems) {
            ProductOrder productOrder = ProductOrder.builder()
                    .order(order)
                    .product(item.getProduct())
                    .size(item.getSize())
                    .quantity(item.getQuantity())
                    .build();
            productOrders.add(productOrderRepository.save(productOrder));

            ProductSize productSize = productSizeRepository
                    .findByProductAndSize(item.getProduct(), item.getSize())
                    .orElse(null);
            if (productSize != null && productSize.getStock() != null) {
                productSize.setStock(productSize.getStock() - 1);
                productSizeRepository.save(productSize);
            }
        }

        shoppingCartRepository.deleteAll(cartItems);

        return productOrderMapper.toDtoList(productOrders);
    }
}
