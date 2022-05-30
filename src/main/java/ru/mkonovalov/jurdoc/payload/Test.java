package ru.mkonovalov.jurdoc.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class Test {
    private String name;
    private Long id;
}
