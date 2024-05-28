package jp.eightbit.exam.todoapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.eightbit.exam.todoapp.entity.Category;
import jp.eightbit.exam.todoapp.entity.Priority;
import jp.eightbit.exam.todoapp.entity.Task;
import jp.eightbit.exam.todoapp.repository.CategoryRepository;
import jp.eightbit.exam.todoapp.repository.PriorityRepository;
import jp.eightbit.exam.todoapp.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private PriorityRepository priorityRepository;
	
	public List<Task> findByUserUsername(String username) {
		return taskRepository.findByUser_Username(username);
	}
	
	public Task save(Task task) {
		return taskRepository.save(task);
	}
	
	public Task findById(Long id) {
		return taskRepository.findById(id).orElse(null);
	}
	
	public void deleteById(Long id) {
		taskRepository.deleteById(id);
	}
	
	//カテゴリーをDBから探してくる
	public List<Category> findAllCategory() {
		return categoryRepository.findAll();
	}
	
	//優先度をDBから探してくる
	public List<Priority> findAllPriority() {
		return priorityRepository.findAll();
	}
	
	//カテゴリーのID探してくる
	public Category findByCategoryId(Long id) {
		return categoryRepository.findById(id).orElse(null);
	}
	
	//優先度のID探してくる
	public Priority findByPriorityId(Long id) {
		return priorityRepository.findById(id).orElse(null);
	}
	
	//ステータスの完了未完了を変換させる
	public void toggleTaskStatus(Long taskId) {
		Task task = taskRepository.findById(taskId)
								  .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));
		//タスクのステータスを反転
		task.setCompleted(!task.isCompleted());
		taskRepository.save(task);
	}
	
	//タスクを削除する
	public void deleteTask(Long taskId) {
		taskRepository.deleteById(taskId);
	}

	//タスクを検索する
	public List<Task> searchTasksByTaskName(String username, String name) {
		return taskRepository.findByNameContainingAndUserUsername(name, username);
	}
	
	//カテゴリー・優先度のフィルタリングされたタスクを取得
	public List<Task> findByUserUsernameAndFilters(String username, String priority, String category) {
		if ((priority == null || "All".equals(priority)) && (category == null || "All".equals(category))) {
			return taskRepository.findByUser_Username(username);
		} else if (priority == null || "All".equals(priority)) {
			return taskRepository.findByUser_UsernameAndCategory_Category(username, category);
		} else if (category == null || "All".equals(category)) {
			return taskRepository.findByUser_UsernameAndPriority_Priority(username, priority);
		} else {
			return taskRepository.findByUser_UsernameAndPriority_PriorityAndCategory_Category(username, priority, category);
		}
	}
}