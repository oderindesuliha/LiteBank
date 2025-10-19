package org.peejay.joblync.services;

import org.peejay.joblync.data.models.PerformanceReview;
import org.peejay.joblync.data.models.ReviewType;
import org.peejay.joblync.data.models.User;
import org.peejay.joblync.dtos.requests.PerformanceReviewRequest;

import java.util.List;
import java.util.Optional;

public interface PerformanceReviewService {
    PerformanceReview createPerformanceReview(PerformanceReviewRequest request);
    Optional<PerformanceReview> findPerformanceReviewById(Long id);
    List<PerformanceReview> findPerformanceReviewsByEmployee(Long employeeId);
    List<PerformanceReview> findPerformanceReviewsByReviewer(Long reviewerId);
    List<PerformanceReview> findPerformanceReviewsByEmployeeAndType(Long employeeId, ReviewType reviewType);
    PerformanceReview updatePerformanceReview(Long id, PerformanceReviewRequest request);
    void deletePerformanceReview(Long id);
}