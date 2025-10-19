package main

import (
	"bufio"
	"fmt"
	"math/rand"
	"os"
	"strconv"
	"strings"
	"time"
)

type Todo struct {
	ID          int
	Title       string
	Description string
	Done        bool
}

var todos []Todo

func AddTask(title, description string) Todo {
	return Todo{
		ID:          rand.Intn(1000000),
		Title:       title,
		Description: description,
		Done:        false,
	}
}

func main() {
	rand.Seed(time.Now().UnixNano())
	reader := bufio.NewReader(os.Stdin)

	fmt.Println("=====================")
	fmt.Println("=== Tees Todo App ===")
	fmt.Println("=====================")
	for {
		fmt.Println("\n1. Add Task")
		fmt.Println("2. List of Tasks")
		fmt.Println("3. Mark Task as Completed")
		fmt.Println("4. Completed Tasks")
		fmt.Println("5. Delete Task")
		fmt.Println("6. Exit")

		fmt.Print("Enter your choice: ")
		input, _ := reader.ReadString('\n')
		choice, err := strconv.Atoi(strings.TrimSpace(input))
		if err != nil {
			fmt.Println("Invalid input: Please enter a number")
			continue
		}

		switch choice {
		case 1:
			addTask(reader)
		case 2:
			tasklists()
		case 3:
			markCompleted(reader)
		case 4:
			completedtasks()
		case 5:
			deletetask(reader)
		case 6:
			fmt.Println("Exiting app.......Thanks for using Tees Todo app")
			return
		default:
			fmt.Println("Invalid Choice")
		}
	}
}

func addTask(reader *bufio.Reader) {
	fmt.Print("Enter Task Title: ")
	title, _ := reader.ReadString('\n')
	title = strings.TrimSpace(title)

	fmt.Print("Enter Task Description: ")
	description, _ := reader.ReadString('\n')
	description = strings.TrimSpace(description)

	newTask := AddTask(title, description)
	todos = append(todos, newTask)

	fmt.Printf("Task added successfully with ID %d\n", newTask.ID)
}

func tasklists() {
	if len(todos) == 0 {
		fmt.Println("No tasks found")
		return
	}
	fmt.Println("Task List:")
	for _, todo := range todos {
		status := "[ ]"
		if todo.Done {
			status = "[✓]"
		}
		fmt.Printf("ID: %d %s %s - %s\n", todo.ID, status, todo.Title, todo.Description)
	}
}

func markCompleted(reader *bufio.Reader) {
	if len(todos) == 0 {
		fmt.Println("No tasks available")
		return
	}
	tasklists()
	fmt.Print("Enter the task ID to mark as completed: ")
	input, _ := reader.ReadString('\n')
	taskId, err := strconv.Atoi(strings.TrimSpace(input))
	if err != nil {
		fmt.Println("Invalid task ID")
		return
	}

	for i := range todos {
		if todos[i].ID == taskId {
			todos[i].Done = true
			fmt.Println("Task marked as completed")
			return
		}
	}
	fmt.Println("Task ID not found")
}

func completedtasks() {
	fmt.Println("Completed Tasks:")
	found := false
	for _, todo := range todos {
		if todo.Done {
			fmt.Printf("ID: %d [✓] %s - %s\n", todo.ID, todo.Title, todo.Description)
			found = true
		}
	}
	if !found {
		fmt.Println("No completed tasks found")
	}
}

func deletetask(reader *bufio.Reader) {
	if len(todos) == 0 {
		fmt.Println("No tasks found")
		return
	}
	tasklists()
	fmt.Print("Enter the task ID to delete: ")
	input, _ := reader.ReadString('\n')
	taskId, err := strconv.Atoi(strings.TrimSpace(input))

	if err != nil {
		fmt.Println("Invalid task ID")
		return
	}

	for i, todo := range todos {
		if todo.ID == taskId {
			todos = append(todos[:i], todos[i+1:]...)
			fmt.Println("Task deleted successfully")
			return
		}
	}
	fmt.Println("Task ID not found")
}
