package com.sagedemo.demo.production.service;

import com.sagedemo.demo.production.entity.ProductionOrder;
import com.sagedemo.demo.production.entity.ProductionOrderStatus;
import com.sagedemo.demo.production.entity.BillOfMaterials;
import com.sagedemo.demo.production.repository.ProductionOrderRepository;
import com.sagedemo.demo.production.repository.BillOfMaterialsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductionOrderService {
    private final ProductionOrderRepository productionOrderRepository;
    private final BillOfMaterialsRepository billOfMaterialsRepository;
    public ProductionOrderService(ProductionOrderRepository productionOrderRepository, BillOfMaterialsRepository billOfMaterialsRepository) {
        this.productionOrderRepository = productionOrderRepository;
        this.billOfMaterialsRepository = billOfMaterialsRepository;
    }
    @Transactional
    public ProductionOrder createProductionOrder(Long bomId, Integer quantity) {
        BillOfMaterials bom = billOfMaterialsRepository.findById(bomId).orElseThrow(() -> new RuntimeException("BOM not found"));
        ProductionOrder order = new ProductionOrder();
        order.setOrderNumber("PO-" + System.currentTimeMillis());
        order.setOrderDate(LocalDate.now());
        order.setStatus(ProductionOrderStatus.PLANNED);
        order.setBillOfMaterials(bom);
        order.setQuantity(quantity);
        return productionOrderRepository.save(order);
    }
    public List<ProductionOrder> getAllProductionOrders() {
        return productionOrderRepository.findAll();
    }
}
