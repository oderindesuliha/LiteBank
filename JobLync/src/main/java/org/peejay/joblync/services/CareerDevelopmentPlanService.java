package org.peejay.joblync.services;

import org.peejay.joblync.data.models.CareerDevelopmentPlan;
import org.peejay.joblync.data.models.PlanStatus;
import org.peejay.joblync.dtos.requests.CareerDevelopmentPlanRequest;

import java.util.List;
import java.util.Optional;

public interface CareerDevelopmentPlanService {
    CareerDevelopmentPlan createCareerDevelopmentPlan(CareerDevelopmentPlanRequest request);
    Optional<CareerDevelopmentPlan> findCareerDevelopmentPlanById(Long id);
    List<CareerDevelopmentPlan> findCareerDevelopmentPlansByEmployee(Long employeeId);
    List<CareerDevelopmentPlan> findCareerDevelopmentPlansByManager(Long managerId);
    List<CareerDevelopmentPlan> findCareerDevelopmentPlansByEmployeeAndStatus(Long employeeId, PlanStatus status);
    List<CareerDevelopmentPlan> findCareerDevelopmentPlansByDepartment(String department);
    CareerDevelopmentPlan updateCareerDevelopmentPlan(Long id, CareerDevelopmentPlanRequest request);
    void deleteCareerDevelopmentPlan(Long id);
    void completeCareerDevelopmentPlan(Long id);
}