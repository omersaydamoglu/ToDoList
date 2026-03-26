const BASE_URL = "http://localhost:8080";

let currentId = null;

window.onload = function () {
    getTodos();
};

function addTodo() {
    const title = document.getElementById("title").value.trim();
    const description = document.getElementById("description").value.trim();

    if (!title) {
        alert("Görev başlığı boş olamaz.");
        return;
    }

    fetch(`${BASE_URL}/api/todos/add`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify({
            title: title,
            description: description
        })
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("Görev eklenemedi");
            }
            return res.json ? res.json() : null;
        })
        .catch(() => null)
        .finally(() => {
            document.getElementById("title").value = "";
            document.getElementById("description").value = "";
            getTodos();
        });
}

function getTodos() {
    fetch(`${BASE_URL}/api/todos`, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`
        }
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("Görevler alınamadı");
            }
            return res.json();
        })
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

                        <div class="todo-content">
                            <div class="todo-title ${todo.completed ? "completed" : ""}">
                                ${todo.title || ""}
                            </div>

                            <div class="todo-desc ${todo.completed ? "completed" : ""}">
                                ${todo.description || ""}
                            </div>
                        </div>
                    </div>

                    <div class="todo-actions">
                        <button class="icon-btn" onclick='openModal(${JSON.stringify(todo)})'>✏️</button>
                        <button class="icon-btn" onclick="deleteTodo(${todo.id})">❌</button>
                    </div>
                </div>
                `;
            });
        })
        .catch(err => {
            console.error(err);
        });
}

function deleteTodo(id) {
    fetch(`${BASE_URL}/api/todos/delete/${id}`, {
        method: "DELETE",
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`
        }
    })
        .then(() => getTodos());
}

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

function openModal(todo) {
    currentId = todo.id;
    document.getElementById("editTitle").value = todo.title || "";
    document.getElementById("editDesc").value = todo.description || "";
    document.getElementById("modal").style.display = "block";
}

function closeModal() {
    document.getElementById("modal").style.display = "none";
}

function saveUpdate() {
    fetch(`${BASE_URL}/api/todos`, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`
        }
    })
        .then(res => res.json())
        .then(data => {
            const currentTodo = data.find(t => t.id === currentId);

            return fetch(`${BASE_URL}/api/todos/update/${currentId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                },
                body: JSON.stringify({
                    ...currentTodo,
                    title: document.getElementById("editTitle").value,
                    description: document.getElementById("editDesc").value
                })
            });
        })
        .then(() => {
            closeModal();
            getTodos();
        });
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "login.html";
}