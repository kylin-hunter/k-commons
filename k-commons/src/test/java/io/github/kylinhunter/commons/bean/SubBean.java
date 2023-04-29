package io.github.kylinhunter.commons.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-21 17:46
 **/
@Getter
@Setter
@XmlRootElement
public class SubBean implements Serializable {
    private int id;
    private String text;
}
