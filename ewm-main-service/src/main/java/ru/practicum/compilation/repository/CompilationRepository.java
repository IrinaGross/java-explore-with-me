package ru.practicum.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.practicum.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository {
    @NonNull
    Compilation add(@NonNull Compilation compilation);

    void delete(@NonNull Long compilationId);

    @NonNull
    Compilation getCompilationById(@NonNull Long compilationId);

    @NonNull
    Compilation update(@NonNull Compilation compilation);

    @NonNull
    List<Compilation> getAll(@Nullable Boolean pinned, @NonNull Pageable pageable);
}
