package com.wuzhong.webapp.fav.dao;

import com.google.common.base.Objects;
import lombok.Data;

import java.util.Date;

@Data
public class FavDO {

    private Long id;
    private String name;
    private String desc;
    private Date gmtCreate;
    private Date gmtModified;
    private Boolean delete;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FavDO favDO = (FavDO) o;
        return Objects.equal(id, favDO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
