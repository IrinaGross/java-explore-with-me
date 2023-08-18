package ru.practicum.compilation.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import ru.practicum.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository {
    @NotNull
    Compilation add(@NotNull Compilation compilation);

    void delete(@NotNull Long compilationId);

    @NotNull
    Compilation getCompilationById(@NotNull Long compilationId);

    @NotNull
    Compilation update(@NotNull Compilation compilation);

    @NotNull
    List<Compilation> getAll(@Nullable Boolean pinned, @NotNull Pageable pageable);
}
