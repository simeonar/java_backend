package com.sagedemo.backend.production.service;

import com.sagedemo.backend.production.entity.QualityControl;
import com.sagedemo.backend.production.repository.QualityControlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QualityControlServiceTest {
    @Mock
    private QualityControlRepository qualityControlRepository;
    @InjectMocks
    private QualityControlService qualityControlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createQualityControl_success() {
        QualityControl qc = new QualityControl();
        when(qualityControlRepository.save(qc)).thenReturn(qc);
        QualityControl result = qualityControlService.createQualityControl(qc);
        assertNotNull(result);
    }

    @Test
    void getAllQualityControls_returnsList() {
        QualityControl qc = new QualityControl();
        when(qualityControlRepository.findAll()).thenReturn(List.of(qc));
        List<QualityControl> result = qualityControlService.getAllQualityControls();
        assertEquals(1, result.size());
    }
}
