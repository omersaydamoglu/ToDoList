const BASE_URL = "http://localhost:8080";

let currentId = null;

// LOAD
window.onload = () => {
    if (!localStorage.getItem("token")) {
        window.location.href = "login.html";
    }
    getTodos();
};

// GET TODOS
function getTodos() {
    fetch(`${BASE_URL}/api/todos`, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`
        }
    })
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById("todoList");
            list.innerHTML = "";

            data.forEach(todo => {
                list.innerHTML += `
            <div class="todo-card">

                <div class="todo-left">
                    <input type="checkbox"
                        ${todo.completed ? "checked" : ""}
                        onchange='toggleStatus(${JSON.stringify(todo)})' />

                    <div>
                        <div class="todo-title ${todo.completed ? "completed" : ""}">
                            ${todo.title}
                        </div>

                        <div>${todo.description}</div>

                        <div class="priority-${todo.priority?.toLowerCase()}">
                            ${todo.priority || ""}
                        </div>

                        <small>${todo.date || ""}</small>
                    </div>
                </div>

                <div>
                    <button onclick='openModal(${JSON.stringify(todo)})'>✏️</button>
                    <button onclick="deleteTodo(${todo.id})">❌</button>
                </div>

            </div>
            `;
            });
        });
}

// ADD
function addTodo() {
    fetch(`${BASE_URL}/api/todos`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify({
            title: document.getElementById("title").value,
            description: document.getElementById("description").value,
            priority: document.getElementById("priority").value,
            date: document.getElementById("date").value,
            completed: false
        })
    })
        .then(() => getTodos());
}

// DELETE
function deleteTodo(id) {
    fetch(`${BASE_URL}/api/todos/delete/${id}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`
        }
    })
        .then(() => getTodos());
}

// TOGGLE
function toggleStatus(todo) {
    fetch(`${BASE_URL}/api/todos/update/${todo.id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify({
            ...todo,
            completed: !todo.completed
        })
    })
        .then(() => getTodos());
}

// MODAL
function openModal(todo) {
    currentId = todo.id;

    document.getElementById("editTitle").value = todo.title;
    document.getElementById("editDesc").value = todo.description;

    document.getElementById("modal").style.display = "block";
}

function closeModal() {
    document.getElementById("modal").style.display = "none";
}

// UPDATE
function saveUpdate() {
    fetch(`${BASE_URL}/api/todos/update/${currentId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify({
            title: document.getElementById("editTitle").value,
            description: document.getElementById("editDesc").value,
            completed: false
        })
    })
        .then(() => {
            closeModal();
            getTodos();
        });
}

// LOGOUT
function logout() {
    localStorage.removeItem("token");
    window.location.href = "login.html";
}