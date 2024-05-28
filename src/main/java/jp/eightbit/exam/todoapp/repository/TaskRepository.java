package jp.eightbit.exam.todoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.eightbit.exam.todoapp.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUser_Username(String username);

	List<Task> findByNameContainingAndUserUsername(String name, String username);

	List<Task> findByUser_UsernameAndCategory_Category(String username, String category);

	List<Task> findByUser_UsernameAndPriority_Priority(String username, String priority);

	List<Task> findByUser_UsernameAndPriority_PriorityAndCategory_Category(String username, String priority,
			String category);
}