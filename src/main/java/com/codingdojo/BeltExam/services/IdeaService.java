package com.codingdojo.BeltExam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.BeltExam.models.Idea;
import com.codingdojo.BeltExam.models.User;
import com.codingdojo.BeltExam.repositories.IdeaRepo;

@Service
public class IdeaService {
	@Autowired
	private IdeaRepo irepo;
	public Idea createIdea(Idea idea) {
		return irepo.save(idea);
	}
	public Idea updateIdea(Idea idea) {
		return irepo.save(idea);
	}
	public Idea findbyId(Long id) {
		return irepo.findById(id).orElse(null);
	}
	public void delete(Long id) {
		irepo.deleteById(id);
	}
	public List<Idea> findIdeas(){
		return irepo.findAll();
	}
}
