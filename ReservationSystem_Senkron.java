import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

class Seat {
    boolean isBooked = false;
}

class Writer extends Thread {
    private List<Seat> seats;
    private int seatNumber;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
    private ReentrantLock lock;

    Writer(List<Seat> seats, int seatNumber, ReentrantLock lock) {
        this.seats = seats;
        this.seatNumber = seatNumber;
        this.lock = lock;
    }

    public void run() {
        lock.lock();
        try {
            String time = sdf.format(new Date());
            System.out.println("Time: " + time + " " + Thread.currentThread().getName() + " tries to book the seat " + (seatNumber + 1));
            if (!seats.get(seatNumber).isBooked) {
                seats.get(seatNumber).isBooked = true;
                time = sdf.format(new Date());
                System.out.println("Time: " + time + " " + Thread.currentThread().getName() + " booked seat number " + (seatNumber + 1) + " successfully.");
            } else {
                time = sdf.format(new Date());
                System.out.println("Time: " + time + " " + Thread.currentThread().getName() + " could not book seat number " + (seatNumber + 1) + " since it has been already booked.");
            }
        } finally {
            lock.unlock();
        }
    }
}

class Reader extends Thread {
    private List<Seat> seats;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
    private ReentrantLock lock;

    Reader(List<Seat> seats, ReentrantLock lock) {
        this.seats = seats;
        this.lock = lock;
    }

    public void run() {
        lock.lock();
        try {
            String time = sdf.format(new Date());
            System.out.println("Time: " + time + " " + Thread.currentThread().getName() + " looks for available seats. State of the seats are : ");
            StringBuilder state = new StringBuilder();
            for (int i = 0; i < seats.size(); i++) {
                state.append("Seat No ").append(i + 1).append(" : ").append(seats.get(i).isBooked ? "1" : "0").append("    ");
            }
            System.out.println(state.toString());
            System.out.println("--------------------------------------------------------------------------------");
        } finally {
            lock.unlock();
        }
    }
}

public class ReservationSystem {
    public static void main(String[] args) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            seats.add(new Seat());
        }

        ReentrantLock lock = new ReentrantLock();

        Writer writer1 = new Writer(seats, 0, lock);
        Writer writer2 = new Writer(seats, 0, lock);
        Writer writer3 = new Writer(seats, 0, lock);

        Reader reader1 = new Reader(seats, lock);
        Reader reader2 = new Reader(seats, lock);
        Reader reader3 = new Reader(seats, lock);

        writer1.setName("Writer1");
        writer2.setName("Writer2");
        writer3.setName("Writer3");

        reader1.setName("Reader1");
        reader2.setName("Reader2");
        reader3.setName("Reader3");

        writer1.start();
        reader1.start();
        reader3.start();
        writer2.start();
        reader2.start();
        writer3.start();
    }
}
