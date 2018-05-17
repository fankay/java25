package com.kaishengit.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Movie;
import com.kaishengit.entity.MovieExample;
import com.kaishengit.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;

    public List<Movie> findAll() {
        return movieMapper.selectByExample(new MovieExample());
    }

    public void saveMovie(Movie movie) {
        movieMapper.insertSelective(movie);
    }

    public void deleteById(Integer id) {
        movieMapper.deleteByPrimaryKey(id);
    }

    public Movie findById(Integer id) {
        return movieMapper.selectByPrimaryKey(id);
    }

    public void updateMovie(Movie movie) {
        movieMapper.updateByPrimaryKeySelective(movie);
    }

    public PageInfo<Movie> findByPageNum(Integer pageNum) {
        PageHelper.startPage(pageNum,10);
        List<Movie> movieList = findAll();
        return new PageInfo<>(movieList);
    }
}
