package com.farmersmarket.preorder_system.service;

import com.farmersmarket.preorder_system.dto.OrderProductRequest;
import com.farmersmarket.preorder_system.dto.PreOrderRequest;
import com.farmersmarket.preorder_system.exception.BusinessException;
import com.farmersmarket.preorder_system.exception.ResourceNotFoundException;
import com.farmersmarket.preorder_system.model.Customer;
import com.farmersmarket.preorder_system.model.OrderProduct;
import com.farmersmarket.preorder_system.model.OrderStatus;
import com.farmersmarket.preorder_system.model.PreOrder;
import com.farmersmarket.preorder_system.model.Product;
import com.farmersmarket.preorder_system.repository.CustomerRepository;
import com.farmersmarket.preorder_system.repository.PreOrderRepository;
import com.farmersmarket.preorder_system.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PreOrderService {

    private final PreOrderRepository preOrderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public PreOrderService(PreOrderRepository preOrderRepository,
                           CustomerRepository customerRepository,
                           ProductRepository productRepository) {
        this.preOrderRepository = preOrderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public PreOrder createPreOrder(PreOrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + request.getCustomerId()));

        if (request.getOrderProducts() == null || request.getOrderProducts().isEmpty()) {
            throw new BusinessException("At least one order item is required.");
        }

        Map<Long, Integer> requestedQuantities = new HashMap<>();
        for (OrderProductRequest item : request.getOrderProducts()) {
            if (item.getQuantity() == null || item.getQuantity() < 1) {
                throw new BusinessException("Each order item must include a quantity of at least 1.");
            }
            requestedQuantities.merge(item.getProductId(), item.getQuantity(), Integer::sum);
        }

        List<Product> products = productRepository.findAllById(requestedQuantities.keySet());
        if (products.size() != requestedQuantities.size()) {
            Set<Long> foundIds = products.stream().map(Product::getId).collect(Collectors.toSet());
            Set<Long> missingIds = requestedQuantities.keySet().stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet());
            throw new ResourceNotFoundException("Products not found: " + missingIds);
        }

        PreOrder preOrder = new PreOrder();
        preOrder.setCustomer(customer);
        preOrder.setOrderDate(LocalDateTime.now());
        preOrder.setStatus(OrderStatus.PENDING);

        double totalAmount = 0.0;
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (Product product : products) {
            int quantity = requestedQuantities.get(product.getId());
            if (product.getAvailableQuantity() < quantity) {
                throw new BusinessException("Product '" + product.getName() + "' only has " + product.getAvailableQuantity() + " available.");
            }
            product.setAvailableQuantity(product.getAvailableQuantity() - quantity);

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(quantity);
            orderProduct.setPreOrder(preOrder);
            orderProducts.add(orderProduct);

            totalAmount += product.getPrice() * quantity;
        }

        preOrder.setOrderProducts(orderProducts);
        preOrder.setTotalAmount(totalAmount);

        return preOrderRepository.save(preOrder);
    }

    @Transactional
    public PreOrder updateOrderStatus(Long orderId, String statusText) {
        PreOrder order = getOrderById(orderId);
        OrderStatus requestedStatus;
        try {
            requestedStatus = OrderStatus.valueOf(statusText.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Invalid order status: " + statusText);
        }

        if (order.getStatus() == requestedStatus) {
            return order;
        }

        switch (requestedStatus) {
            case CONFIRMED:
                if (order.getStatus() != OrderStatus.PENDING) {
                    throw new BusinessException("Only pending orders can be confirmed.");
                }
                order.setStatus(OrderStatus.CONFIRMED);
                break;
            case COMPLETED:
                if (order.getStatus() != OrderStatus.CONFIRMED) {
                    throw new BusinessException("Only confirmed orders can be completed.");
                }
                order.setStatus(OrderStatus.COMPLETED);
                break;
            case CANCELLED:
                if (order.getStatus() == OrderStatus.COMPLETED) {
                    throw new BusinessException("Completed orders cannot be cancelled.");
                }
                if (order.getStatus() != OrderStatus.CANCELLED) {
                    return cancelOrder(order);
                }
                break;
            default:
                throw new BusinessException("Cannot change order status to " + requestedStatus);
        }

        return preOrderRepository.save(order);
    }

    @Transactional
    public PreOrder cancelOrder(PreOrder order) {
        restoreInventory(order);
        order.setStatus(OrderStatus.CANCELLED);
        return preOrderRepository.save(order);
    }

    private void restoreInventory(PreOrder order) {
        if (order.getOrderProducts() == null) {
            return;
        }
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            Product product = orderProduct.getProduct();
            product.setAvailableQuantity(product.getAvailableQuantity() + orderProduct.getQuantity());
        }
    }

    public PreOrder getOrderById(Long id) {
        return preOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
    }

    public List<PreOrder> getAllOrders() {
        return preOrderRepository.findAll();
    }
}

