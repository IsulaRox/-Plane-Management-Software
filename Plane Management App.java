import java.util.Arrays;
import java.util.Scanner;

// PlaneManagement class for managing plane seats
public class PlaneManagement {
    private static int[][] seats = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ticket[][] ticketArray = new Ticket[4][52];
        int option = 1;

        System.out.println("Welcome to the Plane Management application");

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Buy a seat");
            System.out.println("2. Cancel a seat");
            System.out.println("3. Find the first available seat");
            System.out.println("4. Show seating plan");
            System.out.println("5. Search Ticket");
            System.out.println("6. Print tickets information and total sales");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            try {
                option = scanner.nextInt();
            } catch (Exception e) {
                scanner.next();
                System.out.println("Integer Required");
                continue;
            }

            switch (option) {
                case 1 -> buySeat(ticketArray);
                case 2 -> cancelSeat(ticketArray);
                case 3 -> findFirstAvailable();
                case 4 -> showSeatingPlan();
                case 5 -> searchTicket(ticketArray);
                case 6 -> ticketInfo(ticketArray);
                case 0 -> System.out.println("Exiting program...");
                default -> System.out.println("Invalid option! Please try again.");
            }
        } while (option != 0);

        scanner.close();
    }

    private static void buySeat(Ticket[][] ticketsArray) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Name: ");
        String name = scanner.next();
        System.out.print("Enter your Surname: ");
        String surname = scanner.next();
        System.out.print("Enter your Email: ");
        String email = scanner.next();

        char row;
        int seatNumber;

        while (true) {
            try {
                System.out.print("Enter row letter (A-D): ");
                row = scanner.next().toUpperCase().charAt(0);
                if (row < 'A' || row > 'D') {
                    System.out.println("Invalid Row Letter");
                    continue;
                }
                System.out.print("Enter seat number: ");
                seatNumber = scanner.nextInt();
                break;
            } catch (Exception e) {
                scanner.next();
                System.out.println("Invalid value Entered!");
            }
        }

        int rowIdx = row - 'A';
        int seatIdx = seatNumber - 1;
        if (isValidSeat(rowIdx, seatIdx) && seats[rowIdx][seatIdx] == 0) {
            Person person = new Person(name, surname, email);
            int price = findPrice(seatNumber);
            Ticket ticket = new Ticket(row, seatNumber, price, person);
            seats[rowIdx][seatIdx] = 1;
            ticketsArray[rowIdx][seatIdx] = ticket;
            System.out.println("Seat " + row + seatNumber + " purchased successfully.");
            ticket.saveTicket();
        } else {
            System.out.println("Invalid seat or seat already sold.");
        }
    }

    private static void cancelSeat(Ticket[][] ticketsArray) {
        Scanner scanner = new Scanner(System.in);
        char row;
        int seatNumber;

        while (true) {
            try {
                System.out.print("Enter row letter (A-D): ");
                row = scanner.next().toUpperCase().charAt(0);
                if (row < 'A' || row > 'D') {
                    System.out.println("Invalid Row Letter");
                    continue;
                }
                System.out.print("Enter seat number: ");
                seatNumber = scanner.nextInt();
                break;
            } catch (Exception e) {
                scanner.next();
                System.out.println("Invalid value Entered!");
            }
        }

        int rowIdx = row - 'A';
        int seatIdx = seatNumber - 1;
        if (isValidSeat(rowIdx, seatIdx) && seats[rowIdx][seatIdx] == 1) {
            seats[rowIdx][seatIdx] = 0;
            ticketsArray[rowIdx][seatIdx] = null;
            System.out.println("Seat " + row + seatNumber + " canceled successfully.");
        } else {
            System.out.println("Invalid seat or seat already available.");
        }
    }

    private static void findFirstAvailable() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 0) {
                    char row = (char) ('A' + i);
                    System.out.println("First available seat: " + row + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }

    private static void showSeatingPlan() {
        System.out.println("Seating Plan:");
        for (int i = 0; i < seats.length; i++) {
            char row = (char) ('A' + i);
            System.out.print(row + " ");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] == 0 ? "O " : "X ");
            }
            System.out.println();
        }
    }

    private static void searchTicket(Ticket[][] ticketArray) {
        Scanner scanner = new Scanner(System.in);
        char row;
        int seatNumber;

        while (true) {
            try {
                System.out.print("Enter row letter (A-D): ");
                row = scanner.next().toUpperCase().charAt(0);
                if (row < 'A' || row > 'D') {
                    System.out.println("Invalid Row Letter");
                    continue;
                }
                System.out.print("Enter seat number: ");
                seatNumber = scanner.nextInt();
                break;
            } catch (Exception e) {
                scanner.next();
                System.out.println("Invalid value Entered!");
            }
        }

        int rowIdx = row - 'A';
        int seatIdx = seatNumber - 1;
        Ticket ticket = ticketArray[rowIdx][seatIdx];
        if (seats[rowIdx][seatIdx] == 1 && ticket != null) {
            System.out.println("\nTICKET INFORMATION:");
            ticket.printInfo();
        } else {
            System.out.println("Seat Not Booked");
        }
    }

    private static void ticketInfo(Ticket[][] ticketArray) {
        int totalPrice = 0;
        for (Ticket[] tickets : ticketArray) {
            for (Ticket ticket : tickets) {
                if (ticket != null) {
                    totalPrice += ticket.GetPrice();
                    ticket.printInfo();
                    System.out.println();
                }
            }
        }
        System.out.println("Total Sales: $" + totalPrice);
    }

    private static boolean isValidSeat(int row, int seat) {
        return row >= 0 && row < seats.length && seat >= 0 && seat < seats[row].length;
    }

    private static int findPrice(int seatNumber) {
        if (seatNumber >= 5 && seatNumber <= 9) return 150;
        return 200;
    }
}