package org.peejay.joblync.services.impl;

import org.peejay.joblync.data.models.CareerDevelopmentPlan;
import org.peejay.joblync.data.models.PlanStatus;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.data.repository.CareerDevelopmentPlanRepository;
import org.peejay.joblync.data.repository.UserRepository;
import org.peejay.joblync.dtos.requests.CareerDevelopmentPlanRequest;
import org.peejay.joblync.services.CareerDevelopmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CareerDevelopmentPlanServiceImpl implements CareerDevelopmentPlanService {

    private final CareerDevelopmentPlanRepository careerDevelopmentPlanRepository;
    private final UserRepository userRepository;

    @Autowired
    public CareerDevelopmentPlanServiceImpl(CareerDevelopmentPlanRepository careerDevelopmentPlanRepository, UserRepository userRepository) {
        this.careerDevelopmentPlanRepository = careerDevelopmentPlanRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CareerDevelopmentPlan createCareerDevelopmentPlan(CareerDevelopmentPlanRequest request) {
        CareerDevelopmentPlan careerDevelopmentPlan = new CareerDevelopmentPlan();
        
        // Set employee
        Optional<User> employee = userRepository.findById(Long.valueOf(request.getEmployeeId()));
        if (employee.isPresent()) {
            careerDevelopmentPlan.setEmployee(employee.get());
        } else {
            throw new RuntimeException("Employee not found with id: " + request.getEmployeeId());
        }
        
        // Set manager
        if (request.getManagerId() != null) {
            Optional<User> manager = userRepository.findById(Long.valueOf(request.getManagerId()));
            if (manager.isPresent()) {
                careerDevelopmentPlan.setManager(manager.get());
            } else {
                throw new RuntimeException("Manager not found with id: " + request.getManagerId());
            }
        }
        
        careerDevelopmentPlan.setCurrentPosition(request.getCurrentPosition());
        careerDevelopmentPlan.setTargetPosition(request.getTargetPosition());
        careerDevelopmentPlan.setDepartment(request.getDepartment());
        careerDevelopmentPlan.setDevelopmentGoals(request.getDevelopmentGoals());
        careerDevelopmentPlan.setRequiredSkills(request.getRequiredSkills());
        careerDevelopmentPlan.setTrainingActivities(request.getTrainingActivities());
        careerDevelopmentPlan.setTargetCompletionDate(request.getTargetCompletionDate());
        careerDevelopmentPlan.setStatus(request.getStatus() != null ? request.getStatus() : PlanStatus.DRAFT);
        
        return careerDevelopmentPlanRepository.save(careerDevelopmentPlan);
    }

    @Override
    public Optional<CareerDevelopmentPlan> findCareerDevelopmentPlanById(Long id) {
        return careerDevelopmentPlanRepository.findById(id);
    }

    @Override
    public List<CareerDevelopmentPlan> findCareerDevelopmentPlansByEmployee(Long employeeId) {
        Optional<User> employee = userRepository.findById(employeeId);
        if (employee.isPresent()) {
            return careerDevelopmentPlanRepository.findByEmployee(employee.get());
        }
        throw new RuntimeException("Employee not found with id: " + employeeId);
    }

    @Override
    public List<CareerDevelopmentPlan> findCareerDevelopmentPlansByManager(Long managerId) {
        Optional<User> manager = userRepository.findById(managerId);
        if (manager.isPresent()) {
            return careerDevelopmentPlanRepository.findByManager(manager.get());
        }
        throw new RuntimeException("Manager not found with id: " + managerId);
    }

    @Override
    public List<CareerDevelopmentPlan> findCareerDevelopmentPlansByEmployeeAndStatus(Long employeeId, PlanStatus status) {
        Optional<User> employee = userRepository.findById(employeeId);
        if (employee.isPresent()) {
            return careerDevelopmentPlanRepository.findByEmployeeAndStatus(employee.get(), status);
        }
        throw new RuntimeException("Employee not found with id: " + employeeId);
    }

    @Override
    public List<CareerDevelopmentPlan> findCareerDevelopmentPlansByDepartment(String department) {
        return careerDevelopmentPlanRepository.findByDepartment(department);
    }

    @Override
    public CareerDevelopmentPlan updateCareerDevelopmentPlan(Long id, CareerDevelopmentPlanRequest request) {
        Optional<CareerDevelopmentPlan> existingPlan = careerDevelopmentPlanRepository.findById(id);
        if (existingPlan.isPresent()) {
            CareerDevelopmentPlan careerDevelopmentPlan = existingPlan.get();
            
            // Update employee if provided
            if (request.getEmployeeId() != null) {
                Optional<User> employee = userRepository.findById(Long.valueOf(request.getEmployeeId()));
                if (employee.isPresent()) {
                    careerDevelopmentPlan.setEmployee(employee.get());
                } else {
                    throw new RuntimeException("Employee not found with id: " + request.getEmployeeId());
                }
            }
            
            // Update manager if provided
            if (request.getManagerId() != null) {
                Optional<User> manager = userRepository.findById(Long.valueOf(request.getManagerId()));
                if (manager.isPresent()) {
                    careerDevelopmentPlan.setManager(manager.get());
                } else {
                    throw new RuntimeException("Manager not found with id: " + request.getManagerId());
                }
            }
            
            careerDevelopmentPlan.setCurrentPosition(request.getCurrentPosition());
            careerDevelopmentPlan.setTargetPosition(request.getTargetPosition());
            careerDevelopmentPlan.setDepartment(request.getDepartment());
            careerDevelopmentPlan.setDevelopmentGoals(request.getDevelopmentGoals());
            careerDevelopmentPlan.setRequiredSkills(request.getRequiredSkills());
            careerDevelopmentPlan.setTrainingActivities(request.getTrainingActivities());
            careerDevelopmentPlan.setTargetCompletionDate(request.getTargetCompletionDate());
            careerDevelopmentPlan.setStatus(request.getStatus());
            
            return careerDevelopmentPlanRepository.save(careerDevelopmentPlan);
        }
        throw new RuntimeException("Career development plan not found with id: " + id);
    }

    @Override
    public void deleteCareerDevelopmentPlan(Long id) {
        careerDevelopmentPlanRepository.deleteById(id);
    }

    @Override
    public void completeCareerDevelopmentPlan(Long id) {
        Optional<CareerDevelopmentPlan> existingPlan = careerDevelopmentPlanRepository.findById(id);
        if (existingPlan.isPresent()) {
            CareerDevelopmentPlan careerDevelopmentPlan = existingPlan.get();
            careerDevelopmentPlan.setStatus(PlanStatus.COMPLETED);
            careerDevelopmentPlan.setActualCompletionDate(LocalDateTime.now());
            careerDevelopmentPlanRepository.save(careerDevelopmentPlan);
        } else {
            throw new RuntimeException("Career development plan not found with id: " + id);
        }
    }
}