# 📝 To-Do List Application

## 📖 Project Overview
This project is a web-based To-Do List application developed as part of our course requirements.

The purpose of this project is to allow users to:

- Add tasks  
- Edit tasks  
- Delete tasks  
- Mark tasks as completed  

In addition to software development, this project demonstrates teamwork, project planning, and task management.

---

## 🚀 Features

- User authentication (Login/Register)
- Task management (Add, Edit, Delete)
- Mark tasks as completed
- Frontend–Backend integration

---
## 📸 Screenshots

### 🔐 Login Page
<img width="1901" height="823" alt="Screenshot 2026-03-26 113527" src="https://github.com/user-attachments/assets/54fe5e1c-e66d-4aa4-bf63-aedae298c736" />
### 📝 Register Page
<img width="1904" height="836" alt="Screenshot 2026-03-26 113508" src="https://github.com/user-attachments/assets/2034f14b-ce87-4b2f-a9a8-adedf42326af" />
### 📋 Task Dashboard
<img width="1893" height="829" alt="Screenshot 2026-03-26 120315" src="https://github.com/user-attachments/assets/18107026-63ef-49bc-873f-c5f19d883445" />
### ✅ Completed Tasks
<img width="1877" height="832" alt="Screenshot 2026-03-26 120359" src="https://github.com/user-attachments/assets/3a5dc107-80e6-49fa-88ca-bb043c2ef05c" />




## 👥 Group Members & Roles

- Ömer Saydamoğlı – Project Manager & Frontend Developer  
- Nazlım Aynacı – Backend Developer  
- Talha Soydaş – System Analyst & Integration Developer  

---

## 🛠 Technologies Used

- HTML  
- CSS  
- JavaScript  
- GitHub (Version Control)  
- Jira (Task Management)  
- Google Teams (Communication)  
- Draw.io / PlantUML (UML Diagram)  
- Spring Boot  
- PostgreSQL (Database)  
- Hibernate (JPA)  

---

## ▶️ How to Run the Project

### 🔹 Backend (Spring Boot)

1. Clone the repository:

```
git clone https://github.com/omersaydamoglu/ToDoList.git
```

2. Open the project in IntelliJ IDEA or Eclipse

3. Configure PostgreSQL:

* Create a database (e.g., `todolist_db`)
* Update `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/todolist_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

4. Run the application

The backend will start at:

```
http://localhost:8080
```

---

### 🔹 Frontend

1. Navigate to the frontend folder
2. Open `index.html` in your browser

OR (if using Live Server):

* Right click → Open with Live Server

---

### 🔹 API Testing (Optional)

You can test APIs using:

* Postman
* Swagger (if configured)

Example endpoints:

* `POST /api/auth/register`
* `POST /api/auth/login`
* `GET /api/tasks`


## 📋 Project Management

All team members created:

- One Jira account  
- One Google Teams account  

The instructor has been added as a Watcher in Jira.

All task distribution, sprint planning, and progress tracking were managed through planned milestones and team communication.

---

## 🗓 Project Timeline

### 🗓 Week 1 – Planning & Setup
- Project idea finalization  
- Role distribution  
- Jira setup  
- Git repository creation  
- Project structure planning  

### 🗓 Week 2 – Backend Foundation
- Spring Boot setup  
- Database configuration  
- Entity classes (User, Task)  
- Repository layer  

### 🗓 Week 3 – Backend Development
- CRUD APIs  
- Login & Register  
- Authentication  
- API testing  

### 🗓 Week 4 – Frontend Development
- UI design  
- Login/Register pages  
- Task list page  
- API integration  

### 🗓 Week 5 – Integration & Testing
- Frontend–Backend integration  
- Testing  
- Bug fixing  

### 🗓 Week 6 – Finalization
- Final testing  
- UML diagrams  
- Documentation  
- GitHub upload  

---

## ⚠️ Risk Summary

### Identified Risks
- Time constraints due to academic schedule  
- Integration issues between frontend and backend  
- Limited testing time  

### Mitigation
- Weekly planning  
- Task prioritization  
- Keeping the project scope manageable  

---

## 💰 Budget & Resource Planning

[Proje Planı Excel Dosyası İçin Tıklayın](https://github.com/omersaydamoglu/ToDoList/blob/main/project%20planning.xlsx)

### 👨‍💻 Team Members
3 Members

### ⏳ Estimated Duration
6 Weeks

### 🕒 Estimated Workload
- 40 hours per member
- Total: 120 hours

Detailed resource, time, and member planning is provided in the attached Excel document.

---

## UML Diagramı
<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/3bd43b7d-f7a2-4b6d-863f-01dece1f8e91" />

---

## 🎬 Demo Plan

The live demo of the project will include:

1. User Registration  
2. User Login  
3. Adding a new task  
4. Updating a task  
5. Deleting a task  
6. Marking a task as completed  
7. Showing GitHub repository  
8. Showing CI/CD pipeline  
9. Showing project board

## ⚙️ CI/CD Pipeline

This project uses GitHub Actions for continuous integration.

- Automatic build on push  
- Dependency installation  
- Basic pipeline execution  

You can check the pipeline in the Actions tab of this repository.

CI/CD workflow file:

.github/workflows/ci.yml


## 📂 Repository Information

The project has been uploaded to GitHub as required.

All documentation, UML diagrams, and project files are included.

---

## 📊 Project Analysis

For the project evaluation assignment, a detailed project analysis document was created.

This document includes:

- ⚠️ Risk Analysis
- 📉 Risk Assessment
- 🛡 Risk Mitigation
- 📋 Validation and Testing Plan
- ✅ Success Criteria

You can access the full document here:

👉 [Project Analysis Document](project-analysis.md)

## 📊 Sprint Board

Project board is available here:


👉 https://github.com/omersaydamoglu/ToDoList/projects

---

## 📄 License

This project is developed for educational purposes.
## 📊 Presentation

You can view the project presentation here:

[Download Presentation](./ToDoPresentation.pdf)
