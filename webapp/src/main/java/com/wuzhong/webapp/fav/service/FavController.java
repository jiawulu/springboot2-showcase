package com.wuzhong.webapp.fav.service;

import com.wuzhong.webapp.fav.manager.FavDTO;
import com.wuzhong.webapp.fav.manager.FavManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/favs")
public class FavController {

    @Autowired
    private FavManager favManager;

    @GetMapping
    public List<FavBO> list() {
        return favManager.list("").stream().map(favDTO -> {
            FavBO bo = new FavBO();
            BeanUtils.copyProperties(favDTO, bo);
            return bo;
        }).collect(Collectors.toList());
    }

    @GetMapping("/name/{query}")
    public List<FavBO> list(@PathVariable(name = "query") String  name ) {
        return favManager.list(name).stream().map(favDTO -> {
            FavBO bo = new FavBO();
            BeanUtils.copyProperties(favDTO, bo);
            return bo;
        }).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<FavBO> query(FavQuery query) {
        return favManager.list(query.getName()).stream().map(favDTO -> {
            FavBO bo = new FavBO();
            BeanUtils.copyProperties(favDTO, bo);
            return bo;
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable(name = "id", required = true) Long id) {
        return favManager.remove(id);
    }

    @PostMapping
    public Boolean save(@RequestBody FavBO favBO) {

        FavDTO favDTO = new FavDTO();
        BeanUtils.copyProperties(favBO, favDTO);

        if (null != favDTO.getId()) {
            return favManager.update(favDTO);
        } else {
            return favManager.save(favDTO);
        }
    }

}
