package io.github.kylinhunter.commons.component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;

import io.github.kylinhunter.commons.exception.embed.InitException;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 14:07
 **/
@Data
public class CObjects {
    private int order;
    private boolean hasPrimary;

    private List<Object> objects = Lists.newArrayList();
    private List<CObject> cobjects = Lists.newArrayList();

    public void add(CObject cobject) {

        if (cobject.isPrimary()) {
            if (!hasPrimary) {
                hasPrimary = true;
            } else {
                throw new InitException("duplicate primary object");
            }
        }

        if (!cobjects.contains(cobject)) {
            cobjects.add(cobject);
            Collections.sort(cobjects, Comparator.comparingInt(CObject::getOrder));
            objects = cobjects.stream().map(e -> e.getObject()).collect(Collectors.toList());
        }

    }

    /**
     * @return boolean
     * @title isEmpty
     * @description
     * @author BiJi'an
     * @date 2023-02-12 15:17
     */
    public boolean isEmpty() {
        return objects.isEmpty();
    }

    /**
     * @return java.lang.Object
     * @title getObject
     * @description
     * @author BiJi'an
     * @date 2023-02-12 15:19
     */
    public Object getObject() {
        if (objects != null) {
            return objects.get(0);
        } else {
            return null;
        }
    }

}
