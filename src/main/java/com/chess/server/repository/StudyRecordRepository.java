package com.chess.server.repository;

import com.chess.server.entity.StudyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudyRecordRepository extends JpaRepository<StudyRecord, Long> {
    List<StudyRecord> findByUserId(Long userId);
    Optional<StudyRecord> findByUserIdAndManualId(Long userId, Long manualId);
    long countByUserId(Long userId);
}
