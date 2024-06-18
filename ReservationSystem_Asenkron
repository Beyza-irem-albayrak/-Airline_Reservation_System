import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

class Seat {
    boolean isBooked = false;
}

class Writer extends Thread {
    private List<Seat> seats;
    private int seatNumber;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    Writer(List<Seat> seats, int seatNumber) {
        this.seats = seats;
        this.seatNumber = seatNumber;
    }

    public void run() {
        String time = sdf.format(new Date());
        System.out.println("Time: " + time + " " + Thread.currentThread().getName() + " tries to book the seat " + (seatNumber + 1));
        // Simulate some delay to show the effect of lack of synchronization
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!seats.get(seatNumber).isBooked) {
            seats.get(seatNumber).isBooked = true;
            time = sdf.format(new Date());
            System.out.println("Time: " + time + " " + Thread.currentThread().getName() + " booked seat number " + (seatNumber + 1) + " successfully.");
        } else {
            time = sdf.format(new Date());
            System.out.println("Time: " + time + " " + Thread.currentThread().getName() + " could not book seat number " + (seatNumber + 1) + " since it has been already booked.");
        }
    }
}

class Reader extends Thread {
    private List<Seat> seats;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    Reader(List<Seat> seats) {
        this.seats = seats;
    }

    public void run() {
        String time = sdf.format(new Date());
        System.out.println("Time: " + time + " " + Thread.currentThread().getName() + " looks for available seats. State of the seats are : ");
        StringBuilder state = new StringBuilder();
        for (int i = 0; i < seats.size(); i++) {
            state.append("Seat No ").append(i + 1).append(" : ").append(seats.get(i).isBooked ? "1" : "0").append("    ");
        }
        System.out.println(state.toString());
        System.out.println("--------------------------------------------------------------------------------");
    }
}

public class ReservationSystem {
    public static void main(String[] args) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            seats.add(new Seat());
        }

        Writer writer1 = new Writer(seats, 0);
        Writer writer2 = new Writer(seats, 0);
        Writer writer3 = new Writer(seats, 0);

        Reader reader1 = new Reader(seats);
        Reader reader2 = new Reader(seats);
        Reader reader3 = new Reader(seats);

        writer1.setName("Writer1");
        writer2.setName("Writer2");
        writer3.setName("Writer3");

        reader1.setName("Reader1");
        reader2.setName("Reader2");
        reader3.setName("Reader3");

        // Start threads
        writer2.start();
        reader1.start();
        reader3.start();
        writer3.start();
        reader2.start();
        writer1.start();
    }
}
