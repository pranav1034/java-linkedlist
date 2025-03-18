class Task {
    int taskId;
    String taskName;
    int priority;
    String dueDate;
    Task next;

    public Task(int taskId, String taskName, int priority, String dueDate) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.priority = priority;
        this.dueDate = dueDate;
        this.next = null;
    }
}

public class TaskSchedular {
    private Task head;
    private Task current; // Pointer to track the current task

    public TaskSchedular() {
        this.head = null;
        this.current = null;
    }

    // Add a task at the beginning
    public void addAtBeginning(int taskId, String taskName, int priority, String dueDate) {
        Task newTask = new Task(taskId, taskName, priority, dueDate);
        if (head == null) {
            head = newTask;
            newTask.next = head; // Circular linking
        } else {
            Task temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            newTask.next = head;
            temp.next = newTask;
            head = newTask; // New task becomes the head
        }
        current = head; // Update current pointer
    }

    // Add a task at the end
    public void addAtEnd(int taskId, String taskName, int priority, String dueDate) {
        Task newTask = new Task(taskId, taskName, priority, dueDate);
        if (head == null) {
            head = newTask;
            newTask.next = head; // Circular linking
        } else {
            Task temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newTask;
            newTask.next = head;
        }
        current = head; // Update current pointer
    }

    // Add a task at a specific position
    public void addAtPosition(int taskId, String taskName, int priority, String dueDate, int position) {
        Task newTask = new Task(taskId, taskName, priority, dueDate);
        if (position == 0 || head == null) {
            addAtBeginning(taskId, taskName, priority, dueDate);
            return;
        }

        Task temp = head;
        for (int i = 0; i < position - 2 && temp.next != head; i++) {
            temp = temp.next;
        }
        newTask.next = temp.next;
        temp.next = newTask;
    }

    // Remove a task by Task ID
    public void removeTask(int taskId) {
        if (head == null) return;

        Task temp = head, prev = null;

        // If the task to be deleted is the head
        if (head.taskId == taskId) {
            if (head.next == head) { // Only one node
                head = null;
            } else {
                Task last = head;
                while (last.next != head) {
                    last = last.next;
                }
                head = head.next;
                last.next = head;
            }
            current = head; // Update current pointer
            return;
        }

        // Find the task to be deleted
        do {
            prev = temp;
            temp = temp.next;
            if (temp.taskId == taskId) {
                prev.next = temp.next;
                current = head; // Reset current
                return;
            }
        } while (temp != head);
    }

    // View the current task and move to the next task
    public void viewAndMoveToNextTask() {
        if (current == null) {
            System.out.println("No tasks available.");
            return;
        }
        System.out.println("Current Task: " + current.taskName + " (Priority: " + current.priority + ", Due: " + current.dueDate + ")");
        current = current.next; // Move to next task
    }

    // Display all tasks in the list
    public void displayAllTasks() {
        if (head == null) {
            System.out.println("No tasks available.");
            return;
        }
        Task temp = head;
        do {
            System.out.println("Task ID: " + temp.taskId + ", Name: " + temp.taskName + ", Priority: " + temp.priority + ", Due: " + temp.dueDate);
            temp = temp.next;
        } while (temp != head);
    }

    // Search for a task by priority
    public void searchByPriority(int priority) {
        if (head == null) {
            System.out.println("No tasks available.");
            return;
        }
        Task temp = head;
        boolean found = false;
        do {
            if (temp.priority == priority) {
                System.out.println("Task details: ID: " + temp.taskId + ", Name: " + temp.taskName + ", Due: " + temp.dueDate);
                found = true;
            }
            temp = temp.next;
        } while (temp != head);

        if (!found) {
            System.out.println("No tasks found.");
        }
    }

    public static void main(String[] args) {
        TaskSchedular scheduler = new TaskSchedular();

        // Add tasks
        scheduler.addAtBeginning(1, "Complete assignment", 3, "2025-03-20");
        scheduler.addAtEnd(2, "Start learning Backend", 1, "2025-03-22");
        scheduler.addAtEnd(3, "Write documentation", 4, "2025-03-29");
        scheduler.addAtPosition(4, "Write testcases for testing", 2, "2025-03-26", 2);

        // Display all tasks
        System.out.println("All Tasks:");
        scheduler.displayAllTasks();

        // View and move through tasks in circular fashion
        System.out.println("\nCycling through tasks:");
        scheduler.viewAndMoveToNextTask(); // Task 1
        scheduler.viewAndMoveToNextTask(); // Task 2
        scheduler.viewAndMoveToNextTask(); // Task 4
        scheduler.viewAndMoveToNextTask(); // Task 3
        scheduler.viewAndMoveToNextTask(); // Back to Task 1

        // Search for a task by priority
        System.out.println("\nSearching for task with priority 2:");
        scheduler.searchByPriority(2);

        // Remove a task
        System.out.println("\nRemoving Task ID 2...");
        scheduler.removeTask(2);
        scheduler.displayAllTasks();
    }
}
