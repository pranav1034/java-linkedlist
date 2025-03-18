class Ticket {
    int ticketID;
    String customerName;
    String movieName;
    String seatNumber;
    String bookingTime;
    Ticket next; // Next pointer for circular linked list

    public Ticket(int ticketID, String customerName, String movieName, String seatNumber, String bookingTime) {
        this.ticketID = ticketID;
        this.customerName = customerName;
        this.movieName = movieName;
        this.seatNumber = seatNumber;
        this.bookingTime = bookingTime;
        this.next = null;
    }
}

public class TicketReservation {
    private Ticket head = null; // Circular linked list head

    // Add a new ticket reservation at the end
    public void addTicket(int ticketID, String customerName, String movieName, String seatNumber, String bookingTime) {
        Ticket newTicket = new Ticket(ticketID, customerName, movieName, seatNumber, bookingTime);

        if (head == null) {
            head = newTicket;
            newTicket.next = head; // Circular linking
        } else {
            Ticket temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newTicket;
            newTicket.next = head;
        }
    }

    // Remove a ticket by Ticket ID
    public void removeTicket(int ticketID) {
        if (head == null) {
            System.out.println("No tickets booked.");
            return;
        }

        Ticket temp = head, prev = null;

        // If the ticket to remove is the head
        if (head.ticketID == ticketID) {
            if (head.next == head) { // Only one ticket in the list
                head = null;
            } else {
                Ticket last = head;
                while (last.next != head) {
                    last = last.next;
                }
                head = head.next;
                last.next = head;
            }
            System.out.println("Ticket " + ticketID + " has been canceled.");
            return;
        }

        // Traverse to find the ticket
        do {
            prev = temp;
            temp = temp.next;
            if (temp.ticketID == ticketID) {
                prev.next = temp.next;
                System.out.println("Ticket " + ticketID + " has been canceled.");
                return;
            }
        } while (temp != head);

        System.out.println("Ticket not found.");
    }

    // Display all booked tickets
    public void displayTickets() {
        if (head == null) {
            System.out.println("No tickets booked.");
            return;
        }

        System.out.println("\nCurrent Ticket Reservations:");
        Ticket temp = head;
        do {
            System.out.println("Ticket ID: " + temp.ticketID + ", Customer: " + temp.customerName +
                    ", Movie: " + temp.movieName + ", Seat: " + temp.seatNumber +
                    ", Booking Time: " + temp.bookingTime);
            temp = temp.next;
        } while (temp != head);
    }

    // Search for a ticket by Customer Name or Movie Name
    public void searchTicket(String keyword) {
        if (head == null) {
            System.out.println("No tickets booked.");
            return;
        }

        boolean found = false;
        Ticket temp = head;
        do {
            if (temp.customerName.equalsIgnoreCase(keyword) || temp.movieName.equalsIgnoreCase(keyword)) {
                System.out.println("Ticket Found - Ticket ID: " + temp.ticketID + ", Customer: " + temp.customerName +
                        ", Movie: " + temp.movieName + ", Seat: " + temp.seatNumber +
                        ", Booking Time: " + temp.bookingTime);
                found = true;
            }
            temp = temp.next;
        } while (temp != head);

        if (!found) {
            System.out.println("No ticket found for '" + keyword + "'.");
        }
    }

    // Count total number of booked tickets
    public int countTickets() {
        if (head == null) return 0;

        int count = 0;
        Ticket temp = head;
        do {
            count++;
            temp = temp.next;
        } while (temp != head);

        return count;
    }

    public static void main(String[] args) {
        TicketReservation system = new TicketReservation();

        // Adding some tickets
        system.addTicket(101, "Pranav", "Oppenheimer", "P1", "11:45 AM");
        system.addTicket(102, "Nikhil", "Barbie", "G2", "12:30 PM");
        system.addTicket(103, "Aman", "Mission Impossible 6", "H3", "11:00 AM");

        // Display all tickets
        system.displayTickets();

        // Search for a ticket by customer name or movie name
        system.searchTicket("Nikhil");
        system.searchTicket("Oppenheimer");

        // Remove a ticket
        system.removeTicket(102);

        // Display remaining tickets
        system.displayTickets();

        // Count total tickets
        System.out.println("\nTotal Tickets Booked: " + system.countTickets());
    }
}
