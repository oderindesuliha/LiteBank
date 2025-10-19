package org.peejay.joblync.controller;

import org.peejay.joblync.data.models.CareerDevelopmentPlan;
import org.peejay.joblync.data.models.PlanStatus;
import org.peejay.joblync.dtos.requests.CareerDevelopmentPlanRequest;
import org.peejay.joblync.dtos.responses.ApiResponse;
import org.peejay.joblync.services.CareerDevelopmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career-development-plans")
@CrossOrigin(origins = "*")
public class CareerDevelopmentPlanController {

    private final CareerDevelopmentPlanService careerDevelopmentPlanService;

    @Autowired
    public CareerDevelopmentPlanController(CareerDevelopmentPlanService careerDevelopmentPlanService) {
        this.careerDevelopmentPlanService = careerDevelopmentPlanService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CareerDevelopmentPlan>> createCareerDevelopmentPlan(@RequestBody CareerDevelopmentPlanRequest request) {
        try {
            CareerDevelopmentPlan careerDevelopmentPlan = careerDevelopmentPlanService.createCareerDevelopmentPlan(request);
            ApiResponse<CareerDevelopmentPlan> response = new ApiResponse<>(true, "Career development plan created successfully", careerDevelopmentPlan);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse<CareerDevelopmentPlan> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerDevelopmentPlan>> getCareerDevelopmentPlanById(@PathVariable String id) {
        try {
            return careerDevelopmentPlanService.findCareerDevelopmentPlanById(Long.valueOf(id))
                    .map(careerDevelopmentPlan -> {
                        ApiResponse<CareerDevelopmentPlan> response = new ApiResponse<>(true, "Career development plan retrieved successfully", careerDevelopmentPlan);
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        ApiResponse<CareerDevelopmentPlan> response = new ApiResponse<>(false, "Career development plan not found", null);
                        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                    });
        } catch (Exception e) {
            ApiResponse<CareerDevelopmentPlan> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<CareerDevelopmentPlan>>> getCareerDevelopmentPlansByEmployee(@PathVariable String employeeId) {
        try {
            List<CareerDevelopmentPlan> careerDevelopmentPlans = careerDevelopmentPlanService.findCareerDevelopmentPlansByEmployee(Long.valueOf(employeeId));
            ApiResponse<List<CareerDevelopmentPlan>> response = new ApiResponse<>(true, "Career development plans retrieved successfully", careerDevelopmentPlans);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CareerDevelopmentPlan>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<ApiResponse<List<CareerDevelopmentPlan>>> getCareerDevelopmentPlansByManager(@PathVariable String managerId) {
        try {
            List<CareerDevelopmentPlan> careerDevelopmentPlans = careerDevelopmentPlanService.findCareerDevelopmentPlansByManager(Long.valueOf(managerId));
            ApiResponse<List<CareerDevelopmentPlan>> response = new ApiResponse<>(true, "Career development plans retrieved successfully", careerDevelopmentPlans);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CareerDevelopmentPlan>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employee/{employeeId}/status/{status}")
    public ResponseEntity<ApiResponse<List<CareerDevelopmentPlan>>> getCareerDevelopmentPlansByEmployeeAndStatus(
            @PathVariable String employeeId, @PathVariable PlanStatus status) {
        try {
            List<CareerDevelopmentPlan> careerDevelopmentPlans = careerDevelopmentPlanService.findCareerDevelopmentPlansByEmployeeAndStatus(Long.valueOf(employeeId), status);
            ApiResponse<List<CareerDevelopmentPlan>> response = new ApiResponse<>(true, "Career development plans retrieved successfully", careerDevelopmentPlans);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CareerDevelopmentPlan>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<ApiResponse<List<CareerDevelopmentPlan>>> getCareerDevelopmentPlansByDepartment(@PathVariable String department) {
        try {
            List<CareerDevelopmentPlan> careerDevelopmentPlans = careerDevelopmentPlanService.findCareerDevelopmentPlansByDepartment(department);
            ApiResponse<List<CareerDevelopmentPlan>> response = new ApiResponse<>(true, "Career development plans retrieved successfully", careerDevelopmentPlans);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<CareerDevelopmentPlan>> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerDevelopmentPlan>> updateCareerDevelopmentPlan(@PathVariable String id, @RequestBody CareerDevelopmentPlanRequest request) {
        try {
            CareerDevelopmentPlan updatedCareerDevelopmentPlan = careerDevelopmentPlanService.updateCareerDevelopmentPlan(Long.valueOf(id), request);
            ApiResponse<CareerDevelopmentPlan> response = new ApiResponse<>(true, "Career development plan updated successfully", updatedCareerDevelopmentPlan);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<CareerDevelopmentPlan> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCareerDevelopmentPlan(@PathVariable String id) {
        try {
            careerDevelopmentPlanService.deleteCareerDevelopmentPlan(Long.valueOf(id));
            ApiResponse<String> response = new ApiResponse<>(true, "Career development plan deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<String>> completeCareerDevelopmentPlan(@PathVariable String id) {
        try {
            careerDevelopmentPlanService.completeCareerDevelopmentPlan(Long.valueOf(id));
            ApiResponse<String> response = new ApiResponse<>(true, "Career development plan completed successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}