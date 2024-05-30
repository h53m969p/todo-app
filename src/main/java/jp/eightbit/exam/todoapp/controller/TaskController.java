package jp.eightbit.exam.todoapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.eightbit.exam.todoapp.entity.Category;
import jp.eightbit.exam.todoapp.entity.Priority;
import jp.eightbit.exam.todoapp.entity.Task;
import jp.eightbit.exam.todoapp.entity.User;
import jp.eightbit.exam.todoapp.service.TaskService;
import jp.eightbit.exam.todoapp.service.UserService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	//タスク一覧を表示
	@GetMapping
	 public String listTasks(
			 //何も指定されていなかったらAll
	            @RequestParam(name = "priority", required = false, defaultValue = "All") String priority,
	            @RequestParam(name = "category", required = false, defaultValue = "All") String category,
	            Model model, Authentication authentication) {
	        String username = authentication.getName();
	        List<Task> tasks = taskService.findByUserUsernameAndFilters(username, priority, category);
	        model.addAttribute("tasks", tasks);

	        model.addAttribute("selectedPriority", priority);
	        model.addAttribute("selectedCategory", category);
	        model.addAttribute("priorities", taskService.findAllPriority());
	        model.addAttribute("categories", taskService.findAllCategory());
	        
	        //ユーザー情報を追加
	        User user = userService.findByUsername(username);
	        if (user != null) {
	        	model.addAttribute("user", user);
	        } else {
	        	model.addAttribute("error", "not found user infomation");
	        }

	        return "taskList";
	}
	
	//タスク一覧を表示するメソッド
	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("task", new Task());
		model.addAttribute("categories", taskService.findAllCategory());
		model.addAttribute("priority", taskService.findAllPriority());
		return "taskForm";
	}
	
	//タスクを新規作成する
	@PostMapping("/create")
	public String createTask(@ModelAttribute Task task, Authentication authentication) {
		String username = authentication.getName();
		task.setUser(userService.findByUsername(username));
		
		//IDでカテゴリーと優先度をDBから取得
		Category category = taskService.findByCategoryId(task.getCategory().getId());
		Priority priority = taskService.findByPriorityId(task.getPriority().getId());
		
		task.setCategory(category);
		task.setPriority(priority);
		
		taskService.save(task);
		return "redirect:/tasks";
	}
	
	//ステータスの完了未完了を変換する
	@PostMapping("/toggleTaskStatus")
	public String toggleTaskStatus(@RequestParam("taskId") Long taskId) {
		taskService.toggleTaskStatus(taskId);
		return "redirect:/tasks";
	}
	
	//タスク削除
	@PostMapping("/delete")
	public String deleteTask(@RequestParam("taskId") Long taskId) {
		taskService.deleteTask(taskId);
		return "redirect:/tasks";
	}
	
	//タスク編集
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
		Task task = taskService.findById(id);
		if(task == null) {
			return "redirect:/tasks";
		}
		
		System.out.println(task);
		model.addAttribute("task", task);
		
		model.addAttribute("categories", taskService.findAllCategory());
		model.addAttribute("priority", taskService.findAllPriority());
		return "taskForm";
	}
	
	//タスク更新
	@PostMapping("/edit")
	public String updateTask(@ModelAttribute Task task, Authentication authentication) {
		String username = authentication.getName();
		task.setUser(userService.findByUsername(username));
		
		//IDで既存のタスクを取得
		Task existingTask = taskService.findById(task.getId());
		if(existingTask != null) {
			existingTask.setName(task.getName());
			existingTask.setDescription(task.getDescription());
		
			//IDでカテゴリーと優先度をDBから取得
			Category category = taskService.findByCategoryId(task.getCategory().getId());
			Priority priority = taskService.findByPriorityId(task.getPriority().getId());
			
			existingTask.setCategory(category);
			existingTask.setPriority(priority);
			existingTask.setDueDate(task.getDueDate());
			existingTask.setCompleted(task.isCompleted());
			
			taskService.save(existingTask);
		}
		
		return "redirect:/tasks";
	}
	
	//タスクを検索
	@GetMapping("/search")
	public String searchTasks(Model model, @RequestParam("name") String name, Authentication authentication) {
		String username = authentication.getName();
		List<Task> tasks = taskService.searchTasksByTaskName(username, name);
		model.addAttribute("tasks", tasks);
		model.addAttribute("searchName", name);
		
		User user = userService.findByUsername(username);
		model.addAttribute("user", user);
		return "taskList";
	}
}