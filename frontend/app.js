const API_BASE_URL = 'http://localhost:8080/api';
let currentEntity = 'Reservation';
let currentAction = '';
let currentId = null;

const sidebar = document.querySelector('.sidebar');
const sidebarToggle = document.getElementById('sidebarToggle');
const themeToggle = document.getElementById('themeToggle');
const modal = document.getElementById('modal');

sidebarToggle.addEventListener('click', () => {
    sidebar.classList.toggle('active');
});

themeToggle.addEventListener('click', toggleTheme);

function toggleTheme() {
    document.body.classList.toggle('dark-mode');
    const icon = themeToggle.querySelector('i');
    if (document.body.classList.contains('dark-mode')) {
        icon.classList.replace('fa-moon', 'fa-sun');
    } else {
        icon.classList.replace('fa-sun', 'fa-moon');
    }
}

async function loadEntity(entity) {
    currentEntity = entity;
    document.getElementById('entityTitle').textContent = entity;
    try {
        const response = await fetch(`${API_BASE_URL}/${entity}/all`);
        const data = await response.json();
        displayData(entity, data);
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('content').innerHTML = `<p class="alert alert-danger">Error al cargar los datos de ${entity}</p>`;
    }
}

function displayData(entity, data) {
    let html = `
        <table>
            <thead>
                <tr>
    `;
    
    if (data.length > 0) {
        for (let key in data[0]) {
            html += `<th>${key}</th>`;
        }
        html += '<th>Acciones</th></tr></thead><tbody>';

        data.forEach(item => {
            html += '<tr>';
            for (let key in item) {
                let value = item[key];
                if (key === 'startDate' || key === 'devolutionDate') {
                    value = formatDate(value);
                }
                html += `<td>${value !== null ? value : '-'}</td>`;
            }
            html += `
                <td>
                    <button class="btn btn-secondary" onclick="showModal('edit', ${item.id || item.idReservation})">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-secondary" onclick="deleteItem(${item.id || item.idReservation})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>`;
        });
    } else {
        html += '<tr><td colspan="100%" class="text-center">No hay datos disponibles</td></tr>';
    }
    
    html += '</tbody></table>';
    document.getElementById('content').innerHTML = html;
}

function formatDate(dateString) {
    if (!dateString) return '-';
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES', { year: 'numeric', month: '2-digit', day: '2-digit' });
}

function showModal(action, id = null) {
    currentAction = action;
    currentId = id;
    document.getElementById('modalTitle').textContent = `${currentAction === 'create' ? 'Crear' : 'Editar'} ${currentEntity}`;
    
    let formHtml = '';
    switch (currentEntity) {
        case 'Reservation':
            formHtml = `
                <input type="date" id="startDate" name="startDate" required>
                <input type="date" id="devolutionDate" name="devolutionDate" required>
                <input type="text" id="status" name="status" placeholder="Estado" required>
            `;
            break;
        case 'Category':
            formHtml = `
                <input type="text" id="name" name="name" placeholder="Nombre" required>
                <textarea id="description" name="description" placeholder="Descripción" required></textarea>
            `;
            break;
        case 'Room':
            formHtml = `
                <input type="text" id="name" name="name" placeholder="Nombre" required>
                <input type="text" id="hotel" name="hotel" placeholder="Hotel" required>
                <input type="number" id="stars" name="stars" placeholder="Estrellas" required min="1" max="5">
                <textarea id="description" name="description" placeholder="Descripción" required></textarea>
            `;
            break;
        case 'Client':
            formHtml = `
                <input type="email" id="email" name="email" placeholder="Email" required>
                <input type="password" id="password" name="password" placeholder="Contraseña" required>
                <input type="text" id="name" name="name" placeholder="Nombre" required>
                <input type="number" id="age" name="age" placeholder="Edad" required min="18">
            `;
            break;
        case 'Message':
            formHtml = `
                <textarea id="messageText" name="messageText" placeholder="Mensaje" required></textarea>
            `;
            break;
    }
    
    document.getElementById('entityForm').innerHTML = formHtml;
    
    if (currentAction === 'edit' && currentId) {
        loadItemData(currentId);
    }
    
    modal.style.display = 'block';
}

function closeModal() {
    modal.style.display = 'none';
}

async function loadItemData(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${currentEntity}/${id}`);
        const data = await response.json();
        for (let key in data) {
            const input = document.getElementById(key);
            if (input) {
                if (input.type === 'date') {
                    input.value = data[key].split('T')[0];
                } else {
                    input.value = data[key];
                }
            }
        }
    } catch (error) {
        console.error('Error al cargar los datos del elemento:', error);
    }
}

async function saveEntity() {
    const form = document.getElementById('entityForm');
    const formData = new FormData(form);
    const data = Object.fromEntries(formData);
    
    try {
        const url = currentAction === 'create' 
            ? `${API_BASE_URL}/${currentEntity}/save`
            : `${API_BASE_URL}/${currentEntity}/update/${currentId}`;
        
        const method = currentAction === 'create' ? 'POST' : 'PUT';
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });
        
        if (response.ok) {
            closeModal();
            loadEntity(currentEntity);
        } else {
            throw new Error('Error al guardar el elemento');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Error al guardar el elemento');
    }
}

async function deleteItem(id) {
    if (confirm(`¿Estás seguro de que quieres eliminar este ${currentEntity}?`)) {
        try {
            const response = await fetch(`${API_BASE_URL}/${currentEntity}/delete/${id}`, {
                method: 'DELETE',
            });
            if (response.ok) {
                loadEntity(currentEntity);
            } else {
                throw new Error('Error al eliminar el elemento');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error al eliminar el elemento');
        }
    }
}

// Cargar las reservaciones por defecto al iniciar la página
loadEntity('Reservation');

// Cerrar el modal si se hace clic fuera de él
window.onclick = function(event) {
    if (event.target == modal) {
        closeModal();
    }
}