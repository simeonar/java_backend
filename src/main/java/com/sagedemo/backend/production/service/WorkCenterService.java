package com.sagedemo.backend.production.service;

import com.sagedemo.backend.production.entity.WorkCenter;
import com.sagedemo.backend.production.repository.WorkCenterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class WorkCenterService {
    private final WorkCenterRepository workCenterRepository;
    public WorkCenterService(WorkCenterRepository workCenterRepository) {
        this.workCenterRepository = workCenterRepository;
    }
    @Transactional
    public WorkCenter createWorkCenter(WorkCenter wc) {
        return workCenterRepository.save(wc);
    }
    public List<WorkCenter> getAllWorkCenters() {
        return workCenterRepository.findAll();
    }
    public WorkCenter updateWorkCenter(Long id, WorkCenter updated) {
        return workCenterRepository.findById(id).map(wc -> {
            wc.setName(updated.getName());
            wc.setDescription(updated.getDescription());
            return workCenterRepository.save(wc);
        }).orElseThrow(() -> new RuntimeException("WorkCenter not found"));
    }
    public void deleteWorkCenter(Long id) {
        workCenterRepository.deleteById(id);
    }
}
