package jp.eightbit.exam.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.eightbit.exam.todoapp.entity.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
	
}