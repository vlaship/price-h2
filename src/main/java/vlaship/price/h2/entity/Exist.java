package vlaship.price.h2.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Exist {
    YES("Есть", "y.png"),
    NO("Нет", "n.png"),
    RESERVED("В резерве", "q.png");

    private final String txt;
    private final String pic;

    public static Exist get(final String type) {
        for (Exist exist : values()) {
            if (exist.txt.equals(type)) return exist;
        }
        throw new IllegalArgumentException("Wrong type");
    }
}
