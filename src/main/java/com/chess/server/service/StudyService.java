package com.chess.server.service;

import com.chess.server.entity.StudyRecord;
import com.chess.server.repository.StudyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRecordRepository studyRecordRepository;

    public StudyRecord recordStudy(Long userId, Long manualId, int progress) {
        StudyRecord record = studyRecordRepository
                .findByUserIdAndManualId(userId, manualId)
                .orElseGet(() -> {
                    StudyRecord r = new StudyRecord();
                    r.setUserId(userId);
                    r.setManualId(manualId);
                    return r;
                });
        record.setProgress(progress);
        record.setStudiedAt(LocalDateTime.now());
        return studyRecordRepository.save(record);
    }

    public List<StudyRecord> getUserRecords(Long userId) {
        return studyRecordRepository.findByUserId(userId);
    }

    public long getStudiedCount(Long userId) {
        return studyRecordRepository.countByUserId(userId);
    }
}
