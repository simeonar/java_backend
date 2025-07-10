package com.sagedemo.backend.sales.service;

import com.sagedemo.backend.inventory.entity.Product;
import com.sagedemo.backend.inventory.repository.ProductRepository;
import com.sagedemo.backend.sales.dto.SalesOrderDTO;
import com.sagedemo.backend.sales.dto.SalesOrderItemDTO;
import com.sagedemo.backend.sales.entity.Customer;
import com.sagedemo.backend.sales.entity.OrderStatus;
import com.sagedemo.backend.sales.entity.SalesOrder;
import com.sagedemo.backend.sales.entity.SalesOrderItem;
import com.sagedemo.backend.sales.repository.CustomerRepository;
import com.sagedemo.backend.sales.repository.SalesOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SalesOrderServiceTest {
    @Mock
    private SalesOrderRepository salesOrderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private SalesOrderService salesOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSalesOrder_shouldSaveAndReturnSalesOrderDTO() {
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setCustomerId(1L);
        List<SalesOrderItemDTO> items = new ArrayList<>();
        SalesOrderItemDTO itemDTO = new SalesOrderItemDTO();
        itemDTO.setProductId(2L);
        itemDTO.setQuantity(3);
        items.add(itemDTO);
        dto.setItems(items);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCompanyName("Test Customer");

        Product product = new Product();
        product.setId(2L);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));

        SalesOrder savedOrder = new SalesOrder();
        savedOrder.setId(10L);
        savedOrder.setCustomer(customer);
        savedOrder.setStatus(OrderStatus.PENDING);
        savedOrder.setTotalAmount(BigDecimal.valueOf(300));
        List<SalesOrderItem> savedItems = new ArrayList<>();
        SalesOrderItem savedItem = new SalesOrderItem();
        savedItem.setProduct(product);
        savedItem.setQuantity(3);
        savedItem.setUnitPrice(BigDecimal.valueOf(100));
        savedItem.setTotalPrice(BigDecimal.valueOf(300));
        savedItems.add(savedItem);
        savedOrder.setItems(savedItems);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));
        when(salesOrderRepository.save(any(SalesOrder.class))).thenReturn(savedOrder);

        SalesOrderDTO result = salesOrderService.createSalesOrder(dto);
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(BigDecimal.valueOf(300), result.getTotalAmount());
        assertEquals(OrderStatus.PENDING, result.getStatus());
        assertEquals(1, result.getItems().size());
        assertEquals(3, result.getItems().get(0).getQuantity());
    }

    @Test
    void createSalesOrder_shouldThrowIfCustomerNotFound() {
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setCustomerId(99L);
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> salesOrderService.createSalesOrder(dto));
        assertTrue(ex.getMessage().contains("Customer not found"));
    }

    @Test
    void createSalesOrder_shouldThrowIfProductNotFound() {
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setCustomerId(1L);
        List<SalesOrderItemDTO> items = new ArrayList<>();
        SalesOrderItemDTO itemDTO = new SalesOrderItemDTO();
        itemDTO.setProductId(2L);
        itemDTO.setQuantity(1);
        items.add(itemDTO);
        dto.setItems(items);
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> salesOrderService.createSalesOrder(dto));
        assertTrue(ex.getMessage().contains("Product not found"));
    }

    @Test
    void getAllSalesOrders_shouldReturnListOfSalesOrderDTO() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCompanyName("Test Customer");
        SalesOrder order = new SalesOrder();
        order.setId(1L);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.valueOf(100));
        order.setItems(new ArrayList<>());
        when(salesOrderRepository.findAll()).thenReturn(List.of(order));
        List<SalesOrderDTO> result = salesOrderService.getAllSalesOrders();
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(BigDecimal.valueOf(100), result.get(0).getTotalAmount());
    }
}
