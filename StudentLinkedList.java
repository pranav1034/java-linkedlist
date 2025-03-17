class Student {
    int rollNumber;
    String name;
    int age;
    String grade;
    Student next;

    public Student(int rollNumber, String name, int age, String grade) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.next = null;
    }
}

public class StudentLinkedList {
    private Student head;

    public StudentLinkedList() {
        this.head = null;
    }

    // Add a new student record at the beginning
    public void addAtBeginning(int rollNumber, String name, int age, String grade) {
        Student newStudent = new Student(rollNumber, name, age, grade);
        newStudent.next = head;
        head = newStudent;
    }

    // Add a new student record at the end
    public void addAtEnd(int rollNumber, String name, int age, String grade) {
        Student newStudent = new Student(rollNumber, name, age, grade);
        if (head == null) {
            head = newStudent;
        } else {
            Student current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newStudent;
        }
    }

    // Add a new student record at a specific position
    public void addAtPosition(int rollNumber, String name, int age, String grade, int position) {
        Student newStudent = new Student(rollNumber, name, age, grade);
        if (position == 0) {
            newStudent.next = head;
            head = newStudent;
            return;
        }
        Student current = head;
        for (int i = 0; i < position - 2 && current != null; i++) {
            current = current.next;
        }
        if (current != null) {
            newStudent.next = current.next;
            current.next = newStudent;
        }
    }

    // Delete a student record by Roll Number
    public void deleteByRollNumber(int rollNumber) {
        if (head == null) return;
        if (head.rollNumber == rollNumber) {
            head = head.next;
            return;
        }
        Student current = head;
        while (current.next != null && current.next.rollNumber != rollNumber) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    // Search for a student record by Roll Number
    public Student searchByRollNumber(int rollNumber) {
        Student current = head;
        while (current != null) {
            if (current.rollNumber == rollNumber) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Display all student records
    public void displayAll() {
        Student current = head;
        while (current != null) {
            System.out.println("Roll Number: " + current.rollNumber + ", Name: " + current.name + ", Age: " + current.age + ", Grade: " + current.grade);
            current = current.next;
        }
    }

    public void updateGrade(int rollNumber, String newGrade) {
        Student student = searchByRollNumber(rollNumber);
        if (student != null) {
            student.grade = newGrade;
        }
    }
}

class Main {
    public static void main(String[] args) {
        StudentLinkedList list = new StudentLinkedList();

        // Add students
        list.addAtBeginning(1, "A", 20, "A");
        list.addAtEnd(2, "B", 21, "B");
        list.addAtEnd(4, "C", 21, "B");
        list.addAtEnd(5, "D", 21, "A");
        list.addAtPosition(3, "E", 22, "C", 3); //position in the list

        // Display all students
        list.displayAll();

        // Update grade
        list.updateGrade(2, "A+");

        // Search for a student
        Student student = list.searchByRollNumber(3);
        if (student != null) {
            System.out.println("Found: " + student.name + " with grade " + student.grade);
        }

        // Delete a student
        list.deleteByRollNumber(1);

        // Display all students again
        list.displayAll();
    }
}