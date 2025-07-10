package com.sagedemo.demo.crm.service;

import com.sagedemo.demo.crm.entity.Activity;
import com.sagedemo.demo.crm.repository.ActivityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    public Optional<Activity> findById(Long id) {
        return activityRepository.findById(id);
    }

    @Transactional
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Transactional
    public Optional<Activity> update(Long id, Activity updated) {
        return activityRepository.findById(id).map(existing -> {
            existing.setType(updated.getType());
            existing.setDescription(updated.getDescription());
            existing.setActivityDate(updated.getActivityDate());
            existing.setLead(updated.getLead());
            existing.setOpportunity(updated.getOpportunity());
            return activityRepository.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
