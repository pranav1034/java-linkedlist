class Book {
    String title;
    String author;
    String genre;
    int bookID;
    boolean isAvailable;
    Book next;
    Book prev;

    public Book(String title, String author, String genre, int bookID, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.bookID = bookID;
        this.isAvailable = isAvailable;
        this.next = null;
        this.prev = null;
    }
}

public class LibraryManagement {
    private Book head;
    private Book tail;
    private int totalBooks;

    public LibraryManagement() {
        this.head = null;
        this.tail = null;
        this.totalBooks = 0;
    }

    // Add a new book at the beginning
    public void addAtBeginning(String title, String author, String genre, int bookID, boolean isAvailable) {
        Book newBook = new Book(title, author, genre, bookID, isAvailable);
        if (head == null) {
            head = tail = newBook;
        } else {
            newBook.next = head;
            head.prev = newBook;
            head = newBook;
        }
        totalBooks++;
    }

    // Add a new book at the end
    public void addAtEnd(String title, String author, String genre, int bookID, boolean isAvailable) {
        Book newBook = new Book(title, author, genre, bookID, isAvailable);
        if (tail == null) {
            head = tail = newBook;
        } else {
            tail.next = newBook;
            newBook.prev = tail;
            tail = newBook;
        }
        totalBooks++;
    }

    // Add a book at a specific position
    public void addAtPosition(String title, String author, String genre, int bookID, boolean isAvailable, int position) {
        if (position == 0) {
            addAtBeginning(title, author, genre, bookID, isAvailable);
            return;
        }
        Book newBook = new Book(title, author, genre, bookID, isAvailable);
        Book current = head;
        for (int i = 0; i < position - 2 && current != null; i++) {
            current = current.next;
        }
        if (current != null) {
            newBook.next = current.next;
            newBook.prev = current;
            if (current.next != null) {
                current.next.prev = newBook;
            } else {
                tail = newBook;
            }
            current.next = newBook;
            totalBooks++;
        }
    }

    // Remove a book by Book ID
    public void removeByBookID(int bookID) {
        Book current = head;
        while (current != null) {
            if (current.bookID == bookID) {
                if (current == head) {
                    head = head.next;
                    if (head != null) head.prev = null;
                } else if (current == tail) {
                    tail = tail.prev;
                    if (tail != null) tail.next = null;
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                totalBooks--;
                return;
            }
            current = current.next;
        }
    }

    // Search for a book by Title
    public void searchByTitle(String title) {
        Book current = head;
        boolean found = false;
        System.out.println("Searching for book titled: " + title);
        while (current != null) {
            if (current.title.equalsIgnoreCase(title)) {
                System.out.println("Book Found: " + current.title + " by " + current.author + " (Genre: " + current.genre + ", ID: " + current.bookID + ")");
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No book found.");
        }
    }

    // Search for a book by Author
    public void searchByAuthor(String author) {
        Book current = head;
        boolean found = false;
        System.out.println("Books by " + author + ":");
        while (current != null) {
            if (current.author.equalsIgnoreCase(author)) {
                System.out.println(current.title + " (Genre: " + current.genre + ", ID: " + current.bookID + ")");
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No books found by this author.");
        }
    }

    // Update a book’s availability status
    public void updateAvailability(int bookID, boolean newStatus) {
        Book current = head;
        while (current != null) {
            if (current.bookID == bookID) {
                current.isAvailable = newStatus;
                return;
            }
            current = current.next;
        }
    }

    // Display all books in forward order
    public void displayForward() {
        Book current = head;
        System.out.println("\nLibrary Books in Forward Order: ");
        while (current != null) {
            System.out.println(current.bookID + ": " + current.title + " by " + current.author + " (Genre: " + current.genre + ", Available: " + current.isAvailable + ")");
            current = current.next;
        }
    }

    // Display all books in reverse order
    public void displayReverse() {
        Book current = tail;
        System.out.println("\nLibrary Books in Reverse Order: ");
        while (current != null) {
            System.out.println(current.bookID + ": " + current.title + " by " + current.author + " (Genre: " + current.genre + ", Available: " + current.isAvailable + ")");
            current = current.prev;
        }
    }

    // Count total number of books
    public int countBooks() {
        return totalBooks;
    }

    public static void main(String[] args) {
        LibraryManagement library = new LibraryManagement();

        // Adding books
        library.addAtBeginning("The Great Gatsby", "F. Scott Fitzgerald", "Classic", 101, true);
        library.addAtEnd("To Kill a Mockingbird", "Harper Lee", "Fiction", 102, true);
        library.addAtEnd("1984", "George Orwell", "Dystopian", 103, false);
        library.addAtEnd("On The Origin Of Species ", "Charles Darwin", "Scientific Literature", 104, true);
        library.addAtPosition("Pride and Prejudice", "Jane Austen", "Romance", 105, true, 2);

        // Display books
        library.displayForward();

        // Update availability
        library.updateAvailability(102, false);
        System.out.println("\nAfter updating availability of Book ID 102:");
        library.displayForward();

        // Search books
        library.searchByTitle("1984");
        library.searchByAuthor("George Orwell");

        // Remove a book
        System.out.println("\nAfter removing book ID 101:");
        library.removeByBookID(101);
        library.displayForward();

        // Display books in reverse order
        library.displayReverse();

        // Count total books
        System.out.println("\nTotal number of books in the library: " + library.countBooks());
    }
}
