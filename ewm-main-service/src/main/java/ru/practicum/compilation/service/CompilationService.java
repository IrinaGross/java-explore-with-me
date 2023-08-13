package ru.practicum.compilation.service;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.practicum.compilation.model.Compilation;

import java.util.List;
import java.util.Set;

public interface CompilationService {
    void deleteCompilation(@NonNull Long compilationId);

    @NonNull
    Compilation createCompilation(@NonNull Compilation model, @Nullable Set<Long> eventIds);

    @NonNull
    Compilation updateCompilation(@NonNull Long compilationId, @NonNull Compilation model, @Nullable Set<Long> eventIds);

    @NonNull
    List<Compilation> getAll(@Nullable Boolean pinned, @NonNull Pageable pageable);

    @NonNull
    Compilation getById(@NonNull Long compilationId);
}
