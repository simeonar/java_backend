package com.sagedemo.demo.sales.service;

import com.sagedemo.demo.inventory.entity.Product;
import com.sagedemo.demo.inventory.repository.ProductRepository;
import com.sagedemo.demo.sales.dto.SalesOrderDTO;
import com.sagedemo.demo.sales.dto.SalesOrderItemDTO;
import com.sagedemo.demo.sales.entity.Customer;
import com.sagedemo.demo.sales.entity.OrderStatus;
import com.sagedemo.demo.sales.entity.SalesOrder;
import com.sagedemo.demo.sales.entity.SalesOrderItem;
import com.sagedemo.demo.sales.repository.CustomerRepository;
import com.sagedemo.demo.sales.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public SalesOrderDTO createSalesOrder(SalesOrderDTO salesOrderDTO) {
        Customer customer = customerRepository.findById(salesOrderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCustomer(customer);
        salesOrder.setOrderDate(LocalDate.now());
        salesOrder.setStatus(OrderStatus.PENDING);
        salesOrder.setItems(new ArrayList<>());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SalesOrderItemDTO itemDTO : salesOrderDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            SalesOrderItem item = new SalesOrderItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(product.getPrice()); // Or use price from DTO if applicable
            item.setTotalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            item.setSalesOrder(salesOrder);

            salesOrder.getItems().add(item);
            totalAmount = totalAmount.add(item.getTotalPrice());
        }

        salesOrder.setTotalAmount(totalAmount);
        SalesOrder savedOrder = salesOrderRepository.save(salesOrder);
        return toDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<SalesOrderDTO> getAllSalesOrders() {
        return salesOrderRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private SalesOrderDTO toDto(SalesOrder order) {
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setItems(order.getItems().stream().map(this::toDto).collect(Collectors.toList()));
        return dto;
    }

    private SalesOrderItemDTO toDto(SalesOrderItem item) {
        SalesOrderItemDTO dto = new SalesOrderItemDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        return dto;
    }
}
