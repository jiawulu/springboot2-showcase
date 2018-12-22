package com.wuzhong.webapp.fav.dao;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class FavRepository {

    private List<FavDO> localDB = new CopyOnWriteArrayList();
    private AtomicLong id = new AtomicLong(1L);

    @PostConstruct
    public void init() {

        for (int i = 0; i < 10; i++) {
            FavDO favDO = new FavDO();
            favDO.setName("name " + i);
            favDO.setDesc("desc " + i);
            favDO.setGmtCreate(new Date());
            favDO.setGmtModified(new Date());
            favDO.setDelete(false);
            insert(favDO);
        }

    }

    public Boolean insert(FavDO favDO) {
        favDO.setId(id.incrementAndGet());
        localDB.add(favDO);
        return true;
    }

    public Boolean delete(Long id) {
        List<FavDO> collect = localDB.stream().filter(favDO -> favDO.getId().equals(id)).collect(Collectors.toList());
        localDB.removeAll(collect);
        return true;
    }

    public Boolean update(FavDO favDO) {
        List<FavDO> collect = localDB.stream().filter(fav -> favDO.getId().equals(fav.getId())).collect(Collectors.toList());
        localDB.removeAll(collect);
        localDB.add(favDO);
        return true;
    }

    public List<FavDO> query(String name) {
        return localDB.stream().filter(fav -> fav.getName().contains(name)).collect(Collectors.toList());
    }

}
