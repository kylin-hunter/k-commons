package com.kylinhunter.user;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author bijian
 * @since 2023-02-19
 */
@ApiModel(value="User对象", description="")
public class UserEntity implements Serializable {



    @Override
    public String toString() {
        return "${entity}{" +
        "}";
    }
}
