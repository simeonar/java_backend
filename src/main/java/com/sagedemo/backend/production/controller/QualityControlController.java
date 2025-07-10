package com.sagedemo.backend.production.controller;

import com.sagedemo.backend.production.entity.QualityControl;
import com.sagedemo.backend.production.service.QualityControlService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/production/quality-control")
public class QualityControlController {
    private final QualityControlService qualityControlService;
    public QualityControlController(QualityControlService qualityControlService) {
        this.qualityControlService = qualityControlService;
    }
    @PostMapping
    public QualityControl createQC(@RequestBody QualityControl qc) {
        return qualityControlService.createQualityControl(qc);
    }
    @GetMapping
    public List<QualityControl> getAllQC() {
        return qualityControlService.getAllQualityControls();
    }
}
