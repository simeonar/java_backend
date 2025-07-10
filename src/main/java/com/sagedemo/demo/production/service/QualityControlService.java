package com.sagedemo.demo.production.service;

import com.sagedemo.demo.production.entity.QualityControl;
import com.sagedemo.demo.production.repository.QualityControlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class QualityControlService {
    private final QualityControlRepository qualityControlRepository;
    public QualityControlService(QualityControlRepository qualityControlRepository) {
        this.qualityControlRepository = qualityControlRepository;
    }
    @Transactional
    public QualityControl createQualityControl(QualityControl qc) {
        return qualityControlRepository.save(qc);
    }
    public List<QualityControl> getAllQualityControls() {
        return qualityControlRepository.findAll();
    }
}
