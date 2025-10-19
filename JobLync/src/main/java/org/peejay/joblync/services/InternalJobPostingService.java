package org.peejay.joblync.services;

import org.peejay.joblync.data.models.ExperienceLevel;
import org.peejay.joblync.data.models.InternalJobPosting;
import org.peejay.joblync.dtos.requests.InternalJobPostingRequest;

import java.util.List;
import java.util.Optional;

public interface InternalJobPostingService {
    InternalJobPosting createInternalJobPosting(InternalJobPostingRequest request, String creatorId);
    Optional<InternalJobPosting> findInternalJobPostingById(Long id);
    List<InternalJobPosting> findAllInternalJobPostings();
    List<InternalJobPosting> findOpenInternalJobPostings();
    List<InternalJobPosting> findInternalJobPostingsByDepartment(String department);
    List<InternalJobPosting> findInternalJobPostingsByExperienceLevel(ExperienceLevel experienceLevel);
    InternalJobPosting updateInternalJobPosting(Long id, InternalJobPostingRequest request);
    void deleteInternalJobPosting(Long id);
    void closeInternalJobPosting(Long id);
}