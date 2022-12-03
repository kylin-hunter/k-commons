package io.github.kylinhunter.commons.component;

import lombok.Getter;

@Getter
public enum IT implements CT<I> {
    CA(A.class),
    CB(B.class);
    private Class<? extends I> clazz;

    IT(Class<? extends I> clazz) {
        this.clazz = clazz;
    }
}
