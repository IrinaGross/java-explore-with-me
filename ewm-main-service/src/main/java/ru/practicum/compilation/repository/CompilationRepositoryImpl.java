package ru.practicum.compilation.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Repository
interface CompilationRepositoryImpl extends CompilationRepository, JpaRepository<Compilation, Long> {
    @Override
    @NotNull
    default Compilation add(@NotNull Compilation compilation) {
        return save(compilation);
    }

    @Override
    default void delete(@NotNull Long compilationId) {
        deleteById(compilationId);
    }

    @Override
    @NotNull
    default Compilation getCompilationById(@NotNull Long compilationId) {
        return findById(compilationId)
                .orElseThrow(() -> new NotFoundException(String.format("Подборка с идентификатором %1$s не существует", compilationId)));
    }

    @Override
    @NotNull
    default Compilation update(@NotNull Compilation compilation) {
        return save(compilation);
    }

    @Override
    @NotNull
    default List<Compilation> getAll(@Nullable Boolean pinned, @NotNull Pageable pageable) {
        if (pinned == null) {
            return findAll(pageable).toList();
        }
        return findAllByPinned(pinned, pageable);
    }

    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}