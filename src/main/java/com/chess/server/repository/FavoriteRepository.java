package com.chess.server.repository;

import com.chess.server.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    Optional<Favorite> findByUserIdAndManualId(Long userId, Long manualId);
    boolean existsByUserIdAndManualId(Long userId, Long manualId);

    @Modifying
    @Transactional
    void deleteByUserIdAndManualId(Long userId, Long manualId);
}
