package ru.practicum.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Hit;
import ru.practicum.model.View;

import java.time.LocalDateTime;
import java.util.List;

@Repository
interface StatsJpaRepository extends StatsRepository, JpaRepository<Hit, Long> {
    @Override
    default void addStatisticRecord(@NotNull Hit hit) {
        save(hit);
    }

    @Override
    @NotNull
    @Query("SELECT new ru.practicum.model.View(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "AND h.uri in :uris " +
            "GROUP BY h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<View> getUrisWithUniqueIP(
            @NotNull @Param("start") LocalDateTime start,
            @NotNull @Param("end") LocalDateTime end,
            @NotNull @Param("uris") List<String> uris
    );

    @Override
    @NotNull
    @Query("SELECT new ru.practicum.model.View(h.app, h.uri, COUNT(h.id)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "AND h.uri in :uris " +
            "GROUP BY h.uri " +
            "ORDER BY COUNT(h.id) DESC")
    List<View> getUrisStats(
            @NotNull @Param("start") LocalDateTime start,
            @NotNull @Param("end") LocalDateTime end,
            @NotNull @Param("uris") List<String> uris
    );

    @Override
    @NotNull
    @Query("SELECT new ru.practicum.model.View(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<View> getAllUrisWithUniqueIP(
            @NotNull @Param("start") LocalDateTime start,
            @NotNull @Param("end") LocalDateTime end
    );

    @Override
    @NotNull
    @Query("SELECT new ru.practicum.model.View(h.app, h.uri, COUNT(h.id)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.uri " +
            "ORDER BY COUNT(h.id) DESC")
    List<View> getAllUrisStats(
            @NotNull @Param("start") LocalDateTime start,
            @NotNull @Param("end") LocalDateTime end
    );
}
