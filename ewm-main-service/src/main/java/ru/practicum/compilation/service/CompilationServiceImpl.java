package ru.practicum.compilation.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public void deleteCompilation(@NotNull Long compilationId) {
        compilationRepository.getCompilationById(compilationId);
        compilationRepository.delete(compilationId);
    }

    @Override
    @NotNull
    public Compilation createCompilation(@NotNull Compilation model, @Nullable Set<Long> eventIds) {
        var events = Collections.<Event>emptyList();
        if (eventIds != null) {
            events = eventRepository.findAllByIdIn(eventIds);
        }
        return compilationRepository.add(
                model.toBuilder()
                        .events(events)
                        .build()
        );
    }

    @Override
    @NotNull
    public Compilation updateCompilation(@NotNull Long compilationId, @NotNull Compilation compilation, @Nullable Set<Long> eventIds) {
        var current = compilationRepository.getCompilationById(compilationId);
        var builder = current.toBuilder();
        if (eventIds != null) {
            builder.events(eventRepository.findAllByIdIn(eventIds));
        }
        var newTitle = compilation.getTitle();
        var newPinned = compilation.getPinned();
        return compilationRepository.update(
                builder.title(newTitle == null ? current.getTitle() : newTitle)
                        .pinned(newPinned == null ? current.getPinned() : newPinned)
                        .build()
        );
    }

    @Override
    @NonNull
    public List<Compilation> getAll(@Nullable Boolean pinned, @NotNull Pageable pageable) {
        return compilationRepository.getAll(pinned, pageable);
    }

    @Override
    @NotNull
    public Compilation getById(@NotNull Long compilationId) {
        return compilationRepository.getCompilationById(compilationId);
    }
}