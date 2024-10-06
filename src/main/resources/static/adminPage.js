console.log("Зарегистрированный пользователь");
fetch('http://localhost:8080/admin/currentUser')
    .then(response => response.json())
    .then(user => {
        console.log(user)
        document.querySelector('#emailCurrentUser').innerHTML = user.email;
        document.querySelector('#rolesCurrentUser').innerHTML = user.roles.map(role => role.role.replace('ROLE_', '')).join(', ');
    })

async function getAdminPage() {
    console.log('Fetching users...');
    fetch('http://localhost:8080/admin/')
        .then(response => response.json())
        .then(response => {
            const tableBody = document.querySelector('#user-table');
            tableBody.innerHTML = '';
            for (let key in response) {
                let user = response[key];
                let roles = user.roles.map(role => role.role.replace('ROLE_', '')).join(', ');
                const row = document.createElement('tr');
                row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.surname}</td>
        <td>${user.age}</td>
        <td>${user.email}</td> 
        <td>${roles}</td> 
        <td><button class="btn btn-info" onclick="openEditUserPopup(${user.id})">Edit</button></td>       
        
        <td><button type="button" class="btn btn-danger" data-bs-toggle="modal"
        data-bs-target="#delUserModal"  data-bs-backdrop="static" onclick="deleteModalData(${user.id})" >Delete</button></td>

                           
        `;
                tableBody.appendChild(row);
            }
        })
        .catch(error => {
            console.error('Ошибка при получении пользователей:', error);
            const errorMessage = document.createElement('p');
        });
}

getAdminPage();

// Удаление пользователя
const delId = document.getElementById('delId');
const delName = document.getElementById('delName');
const delSurname = document.getElementById("delSurname")
const delAge = document.getElementById("delAge")
const delEmail = document.getElementById('delEmail');
const delRoles = document.getElementById("delRoles")
const deleteModal = document.getElementById("delUserModal");
const closeDeleteButton = document.getElementById("closeDelete")
const bsDeleteModal = new bootstrap.Modal(deleteModal);

async function deleteModalData(id) {
    const urlForDel = 'admin/user/' + id;
    let usersPageDel = await fetch(urlForDel);
    if (usersPageDel.ok) {
        let userData =
            await usersPageDel.json().then(user => {
                delId.value = `${user.id}`;
                delName.value = `${user.username}`;
                delSurname.value = `${user.lastName}`;
                delAge.value = `${user.age}`;
                delEmail.value = `${user.email}`;
                delRoles.value = user.roles.map(role => role.role.replace('ROLE_', '')).join(', ');
            })

        bsDeleteModal.show();
    } else {
        alert(`Error, ${usersPageDel.status}`)
    }
}

async function deleteModalUser() {
    let urlDel = 'admin/user/' + delId.value;
    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    }

    fetch(urlDel, method).then(() => {
        closeDeleteButton.click();
        getAdminPage()
    })
}

// Добавление нового пользователя
// let newUserForm = document.querySelector("addNewUser");
// let URLAddUser = "/admin/addNewUser";

// async function addNewUser(event) {
//     event.preventDefault();
//     let URLAddUser = "/admin/addNewUser"
//     // const urlNew = '/api/admins/newAddUser';
// }
const form_new = document.getElementById('addNewUser');
const create_btn = document.getElementById('closeCreateButton');
const user_table_tab= document.getElementById('nav-home-tab');
const rolesSelect = document.querySelector('#roles');
form_new.addEventListener('submit', addNewUser);

async function addNewUser(event) {
    event.preventDefault();
    const urlNew = "/admin/addNewUser";

    let listOfRole = [];
    for (let i = 0; i < rolesSelect.selectedOptions.length; i++) {
        listOfRole.push({
            id: rolesSelect.selectedOptions[i].value
        });
    }

    let method = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: form_new.name.value,
            surname: form_new.surname.value,
            age: form_new.age.value,
            email: form_new.email.value,
            password: form_new.password.value,
            roles: listOfRole
        })
    };

    try {
        const response = await fetch(urlNew, method);
        if (!response.ok) {
            throw new Error(`Server returned ${response.status} ${response.statusText}`);
        }

        form_new.reset();

        getAdminPage();

        user_table_tab.click();

        let triggerTabList = [].slice.call(document.querySelectorAll('#Admin_panel-tab a'))
        triggerTabList.forEach(function (triggerEl) {
            var tabTrigger = new bootstrap.Tab(triggerEl)

            triggerEl.addEventListener('click', function (event) {
                event.preventDefault()
                tabTrigger.show()
            })
        })
        var triggerEl = document.querySelector('#Admin_panel-tab a[href="#user_table"]');
        if (triggerEl) {
            var tabInstance = bootstrap.Tab.getInstance(triggerEl);
            if (tabInstance) {
                tabInstance.show();
            }
        }
    } catch (error) {
        console.error('Произошла ошибка при выполнении запроса:', error.message);
    }
}






const rolesElement = document.getElementById('rolesForNewUser');
async function getRoles() {
    try {
        const response = await fetch('/admin/getRoles');
        const roles = await response.json();

        rolesElement.innerHTML = '';

        roles.forEach(role => {
            const option = document.createElement('option');
            option.value = role.id;
            option.text = role.role;
            rolesElement.appendChild(option);
        });
    } catch (error) {
        console.error('Error:', error.message);
    }
}
getRoles();