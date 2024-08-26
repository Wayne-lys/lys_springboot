package com.example.lys.service;

import com.example.lys.entity.User;
import com.example.lys.repository.UserRepository;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Page<User> findByPageWithKeyword(int current_page, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(current_page, pageSize);
        Specification<User> spec = (root, query, criteriaBuilder) -> {
            Path<Object> userName = root.get("userName");
            Predicate likeUserName = criteriaBuilder.like(userName.as(String.class), "%" + keyword + "%");

            return criteriaBuilder.or(likeUserName);
        };
        return userRepository.findAll(spec, pageable);
    }

    public Page<User> findByPage(int current_page, int pageSize) {
        Pageable pageable = PageRequest.of(current_page, pageSize);
        return userRepository.findAll(pageable);
    }
}
