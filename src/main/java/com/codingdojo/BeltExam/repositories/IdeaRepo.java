package com.codingdojo.BeltExam.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.BeltExam.models.Idea;

@Repository
public interface IdeaRepo extends CrudRepository<Idea, Long>{
	List<Idea> findAll();
}
