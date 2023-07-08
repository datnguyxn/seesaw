package com.seesaw.repository;

import com.seesaw.model.FeedbackKey;
import com.seesaw.model.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<FeedbackModel, FeedbackKey> {
}
