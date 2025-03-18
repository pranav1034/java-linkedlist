class Process {
    int pid;
    int burstTime;
    int priority;
    Process next;

    public Process(int pid, int burstTime, int priority) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.priority = priority;
        this.next = null;
    }
}

public class RoundRobinScheduler{
    private Process head;
    private Process current;

    public RoundRobinScheduler() {
        this.head = null;
        this.current = null;
    }

    // Add a new process at the end of the circular list
    public void addProcess(int pid, int burstTime, int priority) {
        Process newProcess = new Process(pid, burstTime, priority);
        if (head == null) {
            head = newProcess;
            newProcess.next = head; // Circular linking
        } else {
            Process temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newProcess;
            newProcess.next = head;
        }
        current = head; // Set the current process
    }

    // Remove a process by Process ID after execution
    private void removeProcess(int pid) {
        if (head == null) return;

        Process temp = head, prev = null;

        // If the process to remove is the head
        if (head.pid == pid) {
            if (head.next == head) { // Only one process
                head = null;
            } else {
                Process last = head;
                while (last.next != head) {
                    last = last.next;
                }
                head = head.next;
                last.next = head;
            }
            current = head;
            return;
        }

        // Find the process to delete
        do {
            prev = temp;
            temp = temp.next;
            if (temp.pid == pid) {
                prev.next = temp.next;
                return;
            }
        } while (temp != head);
    }

    // Simulate Round-Robin Scheduling
    public void roundRobinScheduling(int timeQuantum) {
        if (head == null) {
            System.out.println("No processes to schedule.");
            return;
        }

        int totalTime = 0;
        int completedProcesses = 0;
        int waitingTime = 0;
        int turnAroundTime = 0;

        System.out.println("\nExecuting Round-Robin Scheduling (Time Quantum: " + timeQuantum + ")\n");

        while (head != null) {
            Process temp = head;
            do {
                if (temp.burstTime > 0) {
                    System.out.println("Executing Process " + temp.pid + " (Remaining Time: " + temp.burstTime + ")");
                    int executionTime = Math.min(timeQuantum, temp.burstTime);
                    temp.burstTime -= executionTime;
                    totalTime += executionTime;

                    if (temp.burstTime == 0) {
                        System.out.println("Process " + temp.pid + " completed.");
                        completedProcesses++;
                        turnAroundTime += totalTime;
                        waitingTime += (totalTime - executionTime);
                        removeProcess(temp.pid);
                    }
                }
                temp = temp.next;
            } while (temp != head);

            if (head == null) break; // No more processes

            displayProcesses();
        }

        // Calculate and display Average Waiting Time & Turnaround Time
        System.out.println("\nAverage Waiting Time: " + (waitingTime / (double) completedProcesses));
        System.out.println("Average Turnaround Time: " + (turnAroundTime / (double) completedProcesses));
    }

    // Display all processes in the queue
    public void displayProcesses() {
        if (head == null) {
            System.out.println("\nNo remaining processes.");
            return;
        }

        System.out.println("\nCurrent Process Queue:");
        Process temp = head;
        do {
            System.out.println("Process ID: " + temp.pid + ", Remaining Time: " + temp.burstTime + ", Priority: " + temp.priority);
            temp = temp.next;
        } while (temp != head);
    }

    public static void main(String[] args) {
        RoundRobinScheduler scheduler = new RoundRobinScheduler();

        // Add processes
        scheduler.addProcess(1, 10, 1);
        scheduler.addProcess(2, 7, 2);
        scheduler.addProcess(3, 9, 3);
        scheduler.addProcess(4, 5, 2);

        // Display initial processes
        scheduler.displayProcesses();

        // Execute Round-Robin Scheduling with a time quantum of 3
        scheduler.roundRobinScheduling(3);
    }
}
