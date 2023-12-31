package ru.practicum;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utils {

    public static void onConflict(Supplier<Boolean> conditionSupplier, Supplier<String> messageSupplier) {
        check(conditionSupplier, () -> new ConflictException(messageSupplier.get()));
    }

    public static void onNotFound(Supplier<Boolean> conditionSupplier, Supplier<String> messageSupplier) {
        check(conditionSupplier, () -> new NotFoundException(messageSupplier.get()));
    }

    public static void check(Supplier<Boolean> conditionSupplier, Supplier<RuntimeException> exceptionSupplier) {
        if (conditionSupplier.get()) {
            throw exceptionSupplier.get();
        }
    }

    public static void checkDates(@Nullable LocalDateTime start, @Nullable LocalDateTime end) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new BadRequestException("Дата начала не может быть больше даты окончания");
        }
    }

    @NotNull
    public static Pageable newPage(@NotNull Integer from, @NotNull Integer size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }
}
