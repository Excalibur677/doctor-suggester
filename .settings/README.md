# 🏥 Symptom-Based Doctor Appointment Suggester

> A desktop application built with **Java Swing + MySQL** that suggests the right doctor based on patient symptoms and manages appointments digitally.

---

## 📌 Table of Contents

- [About the Project](#about-the-project)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Database Design](#database-design)
- [How It Works](#how-it-works)
- [Syllabus Coverage](#syllabus-coverage)
- [Installation & Setup](#installation--setup)
- [Login Credentials](#login-credentials)
- [Screenshots](#screenshots)
- [Author](#author)

---

## 📖 About the Project

In today's healthcare system, patients often don't know **which type of doctor to visit** for their symptoms. They either visit the wrong specialist or delay treatment due to confusion.

**Doctor Suggester** solves this by:
- Allowing patients to select their symptoms
- Automatically analyzing and suggesting the right doctor specialization
- Enabling patients to book appointments with available doctors
- Allowing doctors to manage appointments and write digital prescriptions
- Exporting prescriptions as `.txt` files for patient records

This project was built as an **End Semester Project** covering both **Java Programming** and **Database Management Systems (DBMS)** syllabus at **Pimpri Chinchwad University (PCU), School of Engineering & Technology, CSE Department, 2025 Pattern**.

---

## ✨ Features

### 👤 Patient
- Register & Login securely
- Select symptoms from a comprehensive list
- Get intelligent doctor specialization suggestions
- View available doctors with experience & availability
- Book appointments with preferred doctors
- View appointment history with status
- Export prescription as `.txt` file

### 👨‍⚕️ Doctor
- Login to personal dashboard
- View all assigned appointments
- Confirm, Complete or Cancel appointments
- Manage patient records

### 👨‍💼 Admin
- Login to admin panel
- View all registered doctors
- Monitor doctor specializations and availability

### 🧠 Smart Symptom Analyzer
- Accepts multiple symptoms simultaneously
- Uses **HashMap scoring algorithm** to find best matching specialization
- Uses **Lambda expressions** for sorting results
- Handles ties by showing multiple suggestions

### 🔔 Appointment Reminder
- Background **multithreading** sends reminders after booking
- Runs as daemon thread — stops when app closes

### 📋 Prescription Export
- Write prescription with medicine & notes
- Export as formatted `.txt` file using **Java File Streams**
- Preview prescription inside the app before saving

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Frontend/UI** | Java Swing, AWT |
| **Backend Logic** | Java 17+ |
| **Database** | MySQL 8.0+ |
| **DB Connectivity** | JDBC (Java Database Connectivity) |
| **Design Pattern** | DAO (Data Access Object) |
| **IDE** | Eclipse IDE |
| **DB Tool** | MySQL Command Line / Workbench |
| **Version Control** | Git & GitHub |

---

## 📁 Project Structure

```
DoctorSuggester/
└── src/
    ├── com.doctorsuggester.model/
    │   ├── User.java               → Abstract base class
    │   ├── Patient.java            → Extends User
    │   ├── Doctor.java             → Extends User
    │   ├── Appointment.java        → With Status enum
    │   └── Symptom.java            → Symptom entity
    │
    ├── com.doctorsuggester.dao/
    │   ├── DAOInterface.java       → Generic DAO interface
    │   ├── UserDAO.java            → Login, Register operations
    │   ├── DoctorDAO.java          → Doctor fetch operations
    │   ├── SymptomDAO.java         → Symptom operations
    │   └── AppointmentDAO.java     → Booking, status update
    │
    ├── com.doctorsuggester.service/
    │   ├── SymptomAnalyzer.java    → Core suggestion logic
    │   ├── AppointmentService.java → Booking + symptom logging
    │   └── ReminderService.java    → Multithreading reminder
    │
    ├── com.doctorsuggester.ui/
    │   ├── LoginScreen.java        → Login form
    │   ├── RegisterScreen.java     → Patient registration
    │   ├── PatientDashboard.java   → Patient home screen
    │   ├── SymptomScreen.java      → Symptom selection
    │   ├── DoctorListScreen.java   → Doctor suggestions & booking
    │   ├── DoctorDashboard.java    → Doctor home screen
    │   ├── PrescriptionScreen.java → Prescription writer
    │   └── AdminDashboard.java     → Admin panel
    │
    └── com.doctorsuggester.util/
        ├── DBConnection.java           → JDBC connection (Singleton)
        ├── SessionManager.java         → Logged-in user session
        ├── UITheme.java                → Centralized UI colors & fonts
        ├── PrescriptionExporter.java   → File Stream export
        ├── InvalidSymptomException.java → Custom exception
        └── DoctorNotFoundException.java → Custom exception
```

---

## 🗄️ Database Design

### Tables

| Table | Purpose |
|---|---|
| `users` | Stores all users — patients, doctors, admin |
| `doctors` | Doctor profiles with specialization & availability |
| `symptoms` | Master list of symptoms mapped to specializations |
| `appointments` | All appointment records with status |
| `prescriptions` | Doctor prescriptions linked to appointments |
| `patient_symptoms` | Log of symptoms selected by patients |

### ER Diagram (Text)

```
users ──────────────────── doctors
  │                            │
  │ (patient_id FK)            │ (doctor_id FK)
  └──────── appointments ──────┘
                 │
                 │ (appointment_id FK)
            prescriptions

users ──── patient_symptoms ──── symptoms
      FK            FK
```

### Key SQL Features Used

```sql
-- View: Doctor Availability
CREATE VIEW doctor_availability AS
SELECT d.doctor_id, u.name, d.specialization, 
       d.experience, d.available_days
FROM doctors d JOIN users u ON d.user_id = u.user_id;

-- Trigger: Auto-complete appointments
CREATE TRIGGER after_appointment_date
BEFORE UPDATE ON appointments
FOR EACH ROW
BEGIN
    IF NEW.appointment_date < CURDATE() 
    AND NEW.status = 'confirmed' THEN
        SET NEW.status = 'completed';
    END IF;
END;

-- Transaction: Book appointment
conn.setAutoCommit(false);
-- INSERT appointment
-- INSERT patient_symptoms
conn.commit();
```

### Normalization
All 6 tables follow **Third Normal Form (3NF)**:
- ✅ No repeating groups (1NF)
- ✅ No partial dependencies (2NF)
- ✅ No transitive dependencies (3NF)

---

## 🔄 How It Works

```
1. Patient logs in
         ↓
2. Clicks "Check Symptoms"
         ↓
3. Selects symptoms (checkboxes)
         ↓
4. SymptomAnalyzer scores specializations using HashMap
         ↓
5. Best matching specialization suggested
         ↓
6. Available doctors of that type shown
         ↓
7. Patient selects doctor & enters date
         ↓
8. AppointmentService books via Transaction (MySQL)
         ↓
9. ReminderService starts background thread 🔔
         ↓
10. Patient views appointment in dashboard
         ↓
11. Doctor confirms/completes appointment
         ↓
12. Patient exports prescription as .txt file 📋
```

---

## 📚 Syllabus Coverage

### Java (PCU CSE 2025 Pattern)

| Unit | Topic | Coverage |
|---|---|---|
| I | Classes, Objects, Constructors | User, Doctor, Patient, Appointment classes |
| I | Enumerated Types | `Appointment.Status` enum |
| I | String class, Arrays | Throughout the project |
| II | Inheritance | Doctor & Patient extend User |
| II | Abstract Classes | User is abstract |
| II | Polymorphism | `getDashboardTitle()` overridden |
| II | Interfaces | `DAOInterface` implemented by DAOs |
| II | Packages | model, dao, service, ui, util |
| III | Exception Handling | try-catch-finally in all DAO classes |
| III | Custom Exceptions | `InvalidSymptomException`, `DoctorNotFoundException` |
| III | Multithreading | `ReminderService` implements Runnable |
| IV | Collections | HashMap, ArrayList throughout |
| IV | Lambda Expressions | Sorting doctors by symptom score |
| IV | File Streams | `PrescriptionExporter` — FileInputStream, FileOutputStream |
| IV | JDBC + DAO | Full DAO layer with PreparedStatements |
| V | Swing GUI | All 8 screens |
| V | Layout Managers | BorderLayout, GridLayout, FlowLayout, GridBagLayout |
| V | Event Handling | All button click handlers |

### DBMS (PCU CSE 2025 Pattern)

| Unit | Topic | Coverage |
|---|---|---|
| I | ER Diagram | Full diagram with 6 entities |
| I | Keys, Constraints | PK, FK, UNIQUE, NOT NULL, ENUM |
| I | Weak Entity | Prescriptions (depends on appointments) |
| II | Normalization | All tables in 3NF |
| II | Functional Dependencies | Documented in report |
| III | SQL Queries | SELECT, INSERT, UPDATE, DELETE |
| III | JOIN | Used in DoctorDAO & AppointmentDAO |
| III | Aggregate Functions | COUNT, AVG |
| III | Views | `doctor_availability` view |
| III | Triggers | Auto-status update trigger |
| III | Nested Subqueries | Doctor search queries |
| IV | Transactions | Appointment booking with rollback |
| IV | ACID Properties | Implemented in AppointmentDAO |

---

## ⚙️ Installation & Setup

### Prerequisites
- Java JDK 17+
- Eclipse IDE
- MySQL 8.0+
- MySQL Connector/J (JAR file)

### Step 1: Clone the Repository
```bash
git clone https://github.com/YourUsername/doctor-suggester.git
```

### Step 2: Setup Database
Open MySQL Command Line and run:
```sql
CREATE DATABASE doctor_suggester;
USE doctor_suggester;
```
Then run all the SQL from the `database/setup.sql` file.

### Step 3: Configure Database Connection
Open `src/com/doctorsuggester/util/DBConnection.java` and update:
```java
private static final String URL = "jdbc:mysql://localhost:3306/doctor_suggester";
private static final String USER = "root";
private static final String PASSWORD = "your_mysql_password"; // ← change this
```

### Step 4: Add MySQL JAR in Eclipse
1. Right-click project → Properties
2. Java Build Path → Libraries → Add External JARs
3. Select `mysql-connector-j-X.X.X.jar`
4. Apply and Close

### Step 5: Run the Project
Right-click `LoginScreen.java` → **Run As → Java Application**

---

## 🔐 Login Credentials

### Admin
| Email | Password |
|---|---|
| admin@hospital.com | admin123 |

### Doctors
| Name | Email | Password | Specialization |
|---|---|---|---|
| Dr. Ramesh Sharma | ramesh@hospital.com | doc123 | General Physician |
| Dr. Priya Mehta | priya@hospital.com | doc123 | Cardiologist |
| Dr. Anil Verma | anil@hospital.com | doc123 | Dermatologist |
| Dr. Sunita Joshi | sunita@hospital.com | doc123 | Neurologist |
| Dr. Karan Patel | karan@hospital.com | doc123 | Gastroenterologist |
| Dr. Neha Singh | neha@hospital.com | doc123 | Orthopedic |

### Patient (Sample)
| Email | Password |
|---|---|
| sumit@gmail.com | sumit123 |

> 💡 New patients can register directly from the app!

---

## 🗺️ Symptom → Doctor Mapping

| Symptoms | Suggested Doctor |
|---|---|
| Fever, Cold, Cough, Fatigue | General Physician |
| Chest Pain, Breathlessness, Palpitations | Cardiologist |
| Skin Rash, Itching, Acne, Hair Loss | Dermatologist |
| Headache, Dizziness, Memory Loss, Numbness | Neurologist |
| Stomach Pain, Vomiting, Diarrhea, Bloating | Gastroenterologist |
| Joint Pain, Back Pain, Muscle Weakness | Orthopedic |

---

## 👨‍💻 Author

**Sumit Kumar**
- 🎓 B.Tech CSE — 2nd Year (4th Semester)
- 🏫 Pimpri Chinchwad University (PCU)
- 💻 GitHub: [Excalibur677](https://github.com/Excalibur677)

---

## 📄 License

This project is built for academic purposes under PCU CSE curriculum.

---

> ⭐ If you found this project helpful, give it a star on GitHub!
