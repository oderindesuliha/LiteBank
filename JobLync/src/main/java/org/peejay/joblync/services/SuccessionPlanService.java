package org.peejay.joblync.services;

import org.peejay.joblync.data.models.SuccessionPlan;
import org.peejay.joblync.data.models.SuccessionStatus;
import org.peejay.joblync.dtos.requests.SuccessionPlanRequest;

import java.util.List;
import java.util.Optional;

public interface SuccessionPlanService {
    SuccessionPlan createSuccessionPlan(SuccessionPlanRequest request);
    Optional<SuccessionPlan> findSuccessionPlanById(Long id);
    List<SuccessionPlan> findSuccessionPlansByIncumbent(Long incumbentId);
    List<SuccessionPlan> findSuccessionPlansBySuccessor(Long successorId);
    List<SuccessionPlan> findSuccessionPlansByDepartment(String department);
    List<SuccessionPlan> findSuccessionPlansByStatus(SuccessionStatus status);
    List<SuccessionPlan> findSuccessionPlansByReadinessLevel(Integer minReadinessLevel);
    SuccessionPlan updateSuccessionPlan(Long id, SuccessionPlanRequest request);
    void deleteSuccessionPlan(Long id);
    void markAsFilled(Long id);
}