package com.kylinhunter.plat.commons.bean.copy;

import java.util.List;

import com.kylinhunter.plat.commons.bean.copy.convertor.ClassCopyConvertor;
import com.kylinhunter.plat.commons.bean.copy.convertor.FieldCopyConvertor;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@Data
public class BeanCopy {
    private List<FieldCopyConvertor> fieldCopyConvertors;
    private ClassCopyConvertor<Object, Object> classCopyConvertor;

    public boolean isEmpty() {
        return classCopyConvertor == null && (fieldCopyConvertors == null || fieldCopyConvertors.isEmpty());

    }

    public void copy(Object source, Object target) {
        if (classCopyConvertor != null) {
            classCopyConvertor.convert(source, target);
        }
        if (fieldCopyConvertors != null && !fieldCopyConvertors.isEmpty()) {
            for (FieldCopyConvertor fieldCopyConvertor : fieldCopyConvertors) {
                fieldCopyConvertor.convert(source, target);
            }
        }
    }
}
