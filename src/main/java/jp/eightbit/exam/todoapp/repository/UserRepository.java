package jp.eightbit.exam.todoapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.eightbit.exam.todoapp.entity.User;

//ユーザー情報を管理
public interface UserRepository extends JpaRepository<User, Long> {

//	Optional<User> findByUsername(String username);
	User findByUsername(String username);

}