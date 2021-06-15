package com.btu.nodartopuria.restapi.service.impl;

import com.btu.nodartopuria.restapi.dto.AddMovieDto;
import com.btu.nodartopuria.restapi.dto.DelMovieDto;
import com.btu.nodartopuria.restapi.dto.InputDtoForGetMovieWithId;
import com.btu.nodartopuria.restapi.dto.MovieDTO;
import com.btu.nodartopuria.restapi.model.Movie;
import com.btu.nodartopuria.restapi.repository.MovieRepository;
import com.btu.nodartopuria.restapi.service.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {


    private final MovieRepository movieRepository;

    @Autowired
    public ServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void add(AddMovieDto inputMovieDto) {
        Movie movie = new Movie();
        movie.setTitle(inputMovieDto.getTitle());
        movie.setCountry(inputMovieDto.getCountry());
        movieRepository.save(movie);
    }

    @Override
    public void delete(DelMovieDto delMovieDto) {
        movieRepository.deleteById(delMovieDto.getId());

    }

    @Override
    public List<MovieDTO> getAllMovies() {
        String[] titleArr = {"Shrek","Venom","Shot Caller"};
        String Country = "Georgia";
        List<MovieDTO> movieDTOList = new ArrayList<>();
        if(movieRepository.findAll().isEmpty()){
            for(int i = 0;i<3;i++){
                Movie movie = new Movie();
                movie.setCountry(Country);
                movie.setTitle(titleArr[i]);
                movieRepository.save(movie);
            }
        }
        return getMovieDTOS(movieDTOList);
    }

    private List<MovieDTO> getMovieDTOS(List<MovieDTO> movieDTOList) {
        List<Movie> movieList = movieRepository.findAll();
        for (Movie m : movieList) {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setCountry(m.getCountry());
            movieDTO.setTitle(m.getTitle());
            movieDTO.setId(m.getId());
            movieDTOList.add(movieDTO);
        }
        return movieDTOList;
    }

    @Override
    public MovieDTO getOneWithId(InputDtoForGetMovieWithId inputDtoForGetMovieWithId) {
        Optional<Movie> movie = movieRepository.findById(inputDtoForGetMovieWithId.getId());
        MovieDTO movieDTO = new MovieDTO();
        try {
            if (movie.isPresent()) {
                movieDTO.setId(movie.get().getId());
                movieDTO.setTitle(movie.get().getTitle());
                movieDTO.setCountry(movie.get().getCountry());
                return movieDTO;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return movieDTO;
    }
}
