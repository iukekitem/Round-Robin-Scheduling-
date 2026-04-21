# Round-Robin CPU Scheduling with I/O Interruptions

This project is a **Java implementation of the Round-Robin CPU scheduling algorithm**
developed as part of my **Operating Systems Design** class at NJIT.

The scheduler simulates multiple processes with different arrival and burst times,
a fixed time quantum, and **explicit I/O interruptions occurring at specific system times**.

---

## 📌 Problem Overview

- **Scheduling Algorithm:** Round-Robin
- **Time Quantum (TQ):** 4 time-units
- **Total Processes:** 7
- **I/O Interruptions:** Occur at fixed system times and block the running process

### I/O Events
| System Time | I/O Duration |
|------------|--------------|
| t = 18     | 18 units     |
| t = 43     | 22 units     |
| t = 72     | 25 units     |
| t = 104    | 14 units     |

📌 **Special Rule Applied:**  
If a process finishes its time quantum **exactly at an I/O request time**, it is sent
to the **blocked queue**, not the ready queue.

---

## 🧾 Process Table

| Process | Arrival Time | Burst Time |
|--------|-------------|------------|
| P1 | 0  | 19 |
| P2 | 5  | 16 |
| P3 | 15 | 27 |
| P4 | 35 | 13 |
| P5 | 50 | 10 |
| P6 | 65 | 26 |
| P7 | 94 | 19 |

---

## ✅ Features Implemented

- ✔ Round-Robin scheduling with configurable time quantum
- ✔ Ready Queue and Blocked Queue management
- ✔ Accurate handling of multiple I/O interruptions
- ✔ Gantt Chart (Timing Diagram)
- ✔ Waiting Time calculation
- ✔ Response Time calculation
- ✔ Turnaround Time calculation
- ✔ No use of built-in Java scheduling or sorting libraries

---

## 📊 Output Produced

1. **Gantt Chart (CPU Execution Timeline)**
2. **Waiting Time for each process**
3. **Response Time for each process**
4. **Turnaround Time for each process**

All calculations follow standard Operating Systems definitions.

---

## ▶ How to Compile and Run

Use **any online Java compiler** or a local JDK.

### Compile
```bash
javac RoundRobinScheduler.java
