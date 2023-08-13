package ru.practicum;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Const {
    public static final String FROM_PARAM = "from";
    public static final String FROM_DEFAULT = "0";
    public static final String SIZE_PARAM = "size";
    public static final String SIZE_DEFAULT = "10";
    public static final String USER_ID_VAR_NAME = "userId";
    public static final String EVENT_ID_VAR_NAME = "eventId";
}
