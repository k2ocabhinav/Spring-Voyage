package com.springvoyage.prod.services.impl;

import com.springvoyage.prod.dto.PostDTO;
import com.springvoyage.prod.entities.PostEntity;
import com.springvoyage.prod.exceptions.ResourceNotFoundException;
import com.springvoyage.prod.repo.PostRepo;
import com.springvoyage.prod.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepo.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        PostEntity toSaveEntityPost = modelMapper.map(inputPost, PostEntity.class);
        PostEntity savedEntityPost = postRepo.save(toSaveEntityPost);
        return modelMapper.map(savedEntityPost, PostDTO.class);

    }

    @Override
    public PostDTO getPostById(Long postId) {
        PostEntity postEntity = postRepo
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("This post was not found"));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePostById(Long postId, PostDTO inputPost) {
        PostEntity olderPost = postRepo
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("This post was not found."));
        inputPost.setId(postId);
        modelMapper.map(inputPost, olderPost);
        return modelMapper.map(postRepo.save(olderPost), PostDTO.class);
    }
}
