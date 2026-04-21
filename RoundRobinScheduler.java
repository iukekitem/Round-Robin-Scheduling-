public class RoundRobinScheduler {

    static class Process {
        String id;
        int arrival, burst, remaining, finish, tat, wt, rt;
        int blockedUntil = -1;
        int firstStart = -1;
        int ioTimeTotal = 0;
        boolean finished = false;

        Process(String id, int arrival, int burst) {
            this.id = id;
            this.arrival = arrival;
            this.burst = burst;
            this.remaining = burst;
        }
    }

    public static void main(String[] args) {
        Process[] ps = {
            new Process("P1", 0, 19),
            new Process("P2", 5, 16),
            new Process("P3", 15, 27),
            new Process("P4", 35, 13),
            new Process("P5", 50, 10),
            new Process("P6", 65, 26),
            new Process("P7", 94, 19)
        };

        for (int i = 0; i < ps.length - 1; i++) {
            for (int j = 0; j < ps.length - i - 1; j++) {
                if (ps[j].arrival > ps[j + 1].arrival) {
                    Process temp = ps[j];
                    ps[j] = ps[j + 1];
                    ps[j + 1] = temp;
                }
            }
        }

        int currentTime = 0;
        int completed = 0;
        int tq = 4;

        Process[] readyQueue = new Process[200];
        int head = 0, tail = 0;

        System.out.println("GANTT CHART:");
        System.out.print("|");

        while (completed < ps.length) {
            for (int i = 0; i < ps.length; i++) {
                if (ps[i].arrival == currentTime && !ps[i].finished) {
                    boolean alreadyIn = false;
                    for (int k = head; k < tail; k++) if (readyQueue[k] == ps[i]) alreadyIn = true;
                    if (!alreadyIn) readyQueue[tail++] = ps[i];
                }
            }

            for (int i = 0; i < ps.length; i++) {
                if (ps[i].blockedUntil == currentTime && !ps[i].finished) {
                    readyQueue[tail++] = ps[i];
                    ps[i].blockedUntil = -1;
                }
            }

            if (head < tail) {
                Process p = readyQueue[head++];
                if (p.firstStart == -1) p.firstStart = currentTime;

                int runTime = 0;
                boolean blocked = false;

                while (runTime < tq && p.remaining > 0) {
                    currentTime++;
                    runTime++;
                    p.remaining--;

                    if ((currentTime == 18) || (currentTime == 43) ||
                        (currentTime == 72) || (currentTime == 104)) {
                        int delay = (currentTime == 18) ? 18 :
                                    (currentTime == 43) ? 22 :
                                    (currentTime == 72) ? 25 : 14;
                        p.blockedUntil = currentTime + delay;
                        p.ioTimeTotal += delay;
                        blocked = true;
                        break;
                    }

                    for (int i = 0; i < ps.length; i++) {
                        if (ps[i].arrival == currentTime) readyQueue[tail++] = ps[i];
                    }
                    for (int i = 0; i < ps.length; i++) {
                        if (ps[i].blockedUntil == currentTime) {
                            readyQueue[tail++] = ps[i];
                            ps[i].blockedUntil = -1;
                        }
                    }
                }

                System.out.print(" " + p.id + " (" + (currentTime - runTime) + "-" + currentTime + ") |");

                if (p.remaining == 0 && !blocked) {
                    p.finished = true;
                    p.finish = currentTime;
                    completed++;
                } else if (!blocked) {
                    readyQueue[tail++] = p;
                }
            } else {
                currentTime++;
                System.out.print(" IDLE (" + (currentTime - 1) + "-" + currentTime + ") |");
            }
        }

        for (Process p : ps) {
            p.tat = p.finish - p.arrival;
            p.wt = p.tat - p.burst - p.ioTimeTotal;
            p.rt = p.firstStart - p.arrival;
        }

        System.out.println("\n\nWAITING TIME TABLE");
        System.out.println("-------------------------");
        System.out.println("Process ID\tWaiting Time");
        System.out.println("-------------------------");
        for (Process p : ps) {
            System.out.println(p.id + "\t\t" + p.wt);
        }

        System.out.println("\nRESPONSE TIME TABLE");
        System.out.println("-------------------------");
        System.out.println("Process ID\tResponse Time");
        System.out.println("-------------------------");
        for (Process p : ps) {
            System.out.println(p.id + "\t\t" + p.rt);
        }

        System.out.println("\nTURN-AROUND TIME TABLE");
        System.out.println("-------------------------");
        System.out.println("Process ID\tTurn-Around Time");
        System.out.println("-------------------------");
        for (Process p : ps) {
            System.out.println(p.id + "\t\t" + p.tat);
        }
    }
}
