package com.kaishengit.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.kaishengit.cache.RedisCacheHelper;
import com.kaishengit.entity.Movie;
import com.kaishengit.mapper.MovieMapper;
import com.kaishengit.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private RedisCacheHelper redisCacheHelper;

    @Override
    @Cacheable("movie")
    public Movie findById(Integer id) {
        return movieMapper.findById(id);
        /*String movieKey = "movie:" + id;
        Movie movie = (Movie) redisCacheHelper.get(movieKey,Movie.class);
        if(movie == null) {
            movie = movieMapper.findById(id);
            redisCacheHelper.set(movieKey,movie,10);
        }
        return movie;*/
    }

    @Override
    public List<Movie> findAll() {
        return movieMapper.findAll();
    }

    @Override
    public PageInfo<Movie> findAllByPageNo(Integer pageNo) {
        PageHelper.startPage(pageNo,10);
        return new PageInfo<>(movieMapper.findAll());
    }
}
