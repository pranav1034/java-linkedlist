class Movie {
    String title;
    String director;
    int year;
    double rating;
    Movie next;
    Movie prev;

    public Movie(String title, String director, int year, double rating) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.next = null;
        this.prev = null;
    }
}

public class MovieManagement {
    private Movie head;
    private Movie tail;

    public MovieManagement() {
        this.head = null;
        this.tail = null;
    }

    // Add a movie at the beginning
    public void addAtBeginning(String title, String director, int year, double rating) {
        Movie newMovie = new Movie(title, director, year, rating);
        if (head == null) {
            head = tail = newMovie;
        } else {
            newMovie.next = head;
            head.prev = newMovie;
            head = newMovie;
        }
    }

    // Add a movie at the end
    public void addAtEnd(String title, String director, int year, double rating) {
        Movie newMovie = new Movie(title, director, year, rating);
        if (tail == null) {
            head = tail = newMovie;
        } else {
            tail.next = newMovie;
            newMovie.prev = tail;
            tail = newMovie;
        }
    }

    // Add a movie at a specific position
    public void addAtPosition(String title, String director, int year, double rating, int position) {
        if (position == 0) {
            addAtBeginning(title, director, year, rating);
            return;
        }
        Movie newMovie = new Movie(title, director, year, rating);
        Movie current = head;
        for (int i = 0; i < position - 2 && current != null; i++) {
            current = current.next;
        }
        if (current != null) {
            newMovie.next = current.next;
            newMovie.prev = current;
            if (current.next != null) {
                current.next.prev = newMovie;
            } else {
                tail = newMovie;
            }
            current.next = newMovie;
        }
    }

    // Remove a movie by title
    public void removeByTitle(String title) {
        Movie current = head;
        while (current != null) {
            if (current.title.equalsIgnoreCase(title)) {
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
                return;
            }
            current = current.next;
        }
    }

    // Search for a movie by director
    public void searchByDirector(String director) {
        Movie current = head;
        boolean found = false;
        System.out.println("Movies directed by " + director + ":");
        while (current != null) {
            if (current.director.equalsIgnoreCase(director)) {
                System.out.println(current.title + " (" + current.year + "), Rating: " + current.rating);
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No movies found by this director.");
        }
    }

    // Search for a movie by rating
    public void searchByRating(double rating) {
        Movie current = head;
        boolean found = false;
        System.out.println("Movies with rating " + rating + ":");
        while (current != null) {
            if (current.rating == rating) {
                System.out.println(current.title + " (" + current.year + "), Directed by: " + current.director);
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No movies found with this rating.");
        }
    }

    // Update a movie's rating by title
    public void updateRating(String title, double newRating) {
        Movie current = head;
        while (current != null) {
            if (current.title.equalsIgnoreCase(title)) {
                current.rating = newRating;
                return;
            }
            current = current.next;
        }
    }

    // Display all movies in forward order
    public void displayForward() {
        Movie current = head;
        System.out.println("Movies List in Forward Order: ");
        while (current != null) {
            System.out.println(current.title + " (" + current.year + ") - " + current.director + " - Rating: " + current.rating);
            current = current.next;
        }
    }

    // Display all movies in reverse order
    public void displayReverse() {
        Movie current = tail;
        System.out.println("Movies List in Reverse Order: ");
        while (current != null) {
            System.out.println(current.title + " (" + current.year + ") - " + current.director + " - Rating: " + current.rating);
            current = current.prev;
        }
    }

    public static void main(String[] args) {
        MovieManagement movies = new MovieManagement();

        // Adding movies
        movies.addAtBeginning("Inception", "Christopher Nolan", 2010, 8.8);
        movies.addAtEnd("Oppenheimer", "Christopher Nolan", 2023, 8.6);
        movies.addAtEnd("Barbie", "Greta Gerwig", 2023, 9.0);
        movies.addAtEnd("Pulp Fiction", "Quentin Tarantino", 1994, 8.9);
        movies.addAtPosition("Django Unchained", "Quentin Tarantino", 2012, 8.4, 3);

        // Display all movies
        movies.displayForward();

        // Update movie rating
        movies.updateRating("Inception", 9.0);
        System.out.println("\nAfter updating rating of Inception:");
        movies.displayForward();

        // Search by director
        System.out.println("\nSearching for movies by Christopher Nolan:");
        movies.searchByDirector("Christopher Nolan");

        // Search by rating
        System.out.println("\nSearching for movies with rating 9.0:");
        movies.searchByRating(9.0);

        // Removing a movie
        System.out.println("\nAfter removing 'Interstellar':");
        movies.removeByTitle("Interstellar");
        movies.displayForward();

        // Display movies in reverse order
        movies.displayReverse();
    }
}
