package com.wuzhong.webapp.fav.manager;

import com.wuzhong.webapp.fav.dao.FavDO;
import com.wuzhong.webapp.fav.dao.FavRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavManager {

    @Autowired
    private FavRepository favRepository;

    public Boolean save(FavDTO favDTO) {
        FavDO favDO = convert2DO(favDTO);
        return favRepository.insert(favDO);
    }

    private FavDO convert2DO(FavDTO favDTO) {
        FavDO favDO = new FavDO();
        BeanUtils.copyProperties(favDTO, favDO);
        favDO.setGmtCreate(new Date());
        favDO.setGmtModified(new Date());
        favDO.setDelete(false);
        return favDO;
    }

    public Boolean remove(Long id) {
        return favRepository.delete(id);
    }


    public Boolean update(FavDTO favDTO) {
        return favRepository.update(convert2DO(favDTO));
    }

    public List<FavDTO> list(String name) {
        List<FavDO> list = favRepository.query(name);
        return list.stream().map(favDO -> {
            FavDTO dto = new FavDTO();
            BeanUtils.copyProperties(favDO, dto);
            return dto;
        }).collect(Collectors.toList());
    }

}
