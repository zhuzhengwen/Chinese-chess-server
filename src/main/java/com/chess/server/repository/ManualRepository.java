package com.chess.server.repository;

import com.chess.server.entity.Manual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ManualRepository extends JpaRepository<Manual, Long> {
    List<Manual> findByCategory(String category);
    List<Manual> findByPremium(boolean premium);
    List<Manual> findByCategoryAndDifficulty(String category, int difficulty);
    List<Manual> findTop6ByOrderByViewCountDesc();

    @Query("SELECT m FROM Manual m WHERE " +
           "(:category IS NULL OR m.category = :category) AND " +
           "(:difficulty IS NULL OR m.difficulty = :difficulty) AND " +
           "(:keyword IS NULL OR m.title LIKE %:keyword% OR m.author LIKE %:keyword%)")
    List<Manual> search(@Param("category") String category,
                        @Param("difficulty") Integer difficulty,
                        @Param("keyword") String keyword);
}
