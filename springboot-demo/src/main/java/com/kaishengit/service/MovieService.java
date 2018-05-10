package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie findById(Integer id);

    List<Movie> findAll();

    PageInfo<Movie> findAllByPageNo(Integer pageNo);
}
