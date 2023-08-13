package ru.practicum.compilation.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import ru.practicum.compilation.model.Compilation;

import java.util.List;
import java.util.Set;

public interface CompilationService {
    void deleteCompilation(@NotNull Long compilationId);

    @NotNull
    Compilation createCompilation(@NotNull Compilation model, @Nullable Set<Long> eventIds);

    @NotNull
    Compilation updateCompilation(@NotNull Long compilationId, @NotNull Compilation model, @Nullable Set<Long> eventIds);

    @NotNull
    List<Compilation> getAll(@Nullable Boolean pinned, @NotNull Pageable pageable);

    @NotNull
    Compilation getById(@NotNull Long compilationId);
}
