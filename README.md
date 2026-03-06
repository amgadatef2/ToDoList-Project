# 📋 Advanced To-Do List with Smart Reminders

A robust Desktop Application built with **Java Swing** that follows **OOP (Object-Oriented Programming)** principles to help users manage their daily tasks efficiently with real-time status tracking.

---

## ✨ Features
* **Task Management**: Create, Read, Update, and Delete (CRUD) tasks easily.
* **Smart Fields**: Each task includes a Title, Description, Due Date, Priority (High/Medium/Low), and Status.
* **Real-time Reminders**: The system automatically checks tasks every minute (or 5 seconds) to detect deadlines.
* **Auto-Overdue Tracking**: Status automatically changes to `OVERDUE` if the deadline passes.
* **Visual Highlights**: Overdue tasks are highlighted in **Red** for immediate attention.
* **Interactive UI**: Selecting a task from the table populates the input fields for quick editing.

---

## 🏗️ Technical Architecture (Class Structure)
The project is organized into three main layers to ensure clean code:

1.  **`Task` (Data Model)**: Uses **Encapsulation** with private fields and Public Getters/Setters to handle task data.
2.  **`TaskManager` (Logic Layer)**: Manages data using a custom **Array-based structure** (manual shifting for deletion) and handles the "Overdue" logic.
3.  **`TodoApp` (Presentation Layer)**: Built with **Java Swing**, managing the GUI, Table Renderers, and background Timers.

---
## 🛠️ Built With
* **Java** - Core programming language.
* **Swing/AWT** - For the Graphical User Interface.
* **Java Time API** - For accurate deadline management
