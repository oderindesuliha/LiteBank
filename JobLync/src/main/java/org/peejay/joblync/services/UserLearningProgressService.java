package org.peejay.joblync.services;

import org.peejay.joblync.data.models.CompletionStatus;
import org.peejay.joblync.data.models.UserLearningProgress;
import org.peejay.joblync.dtos.requests.UserLearningProgressRequest;

import java.util.List;
import java.util.Optional;

public interface UserLearningProgressService {
    UserLearningProgress trackUserLearningProgress(UserLearningProgressRequest request);
    Optional<UserLearningProgress> findUserLearningProgressById(Long id);
    List<UserLearningProgress> findUserLearningProgressByUser(Long userId);
    List<UserLearningProgress> findUserLearningProgressByModule(Long moduleId);
    List<UserLearningProgress> findUserLearningProgressByUserAndStatus(Long userId, CompletionStatus status);
    UserLearningProgress updateUserLearningProgress(Long id, UserLearningProgressRequest request);
    void removeUserLearningProgress(Long id);
}