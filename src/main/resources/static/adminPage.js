console.log("Зарегистрированный пользователь");
fetch('http://localhost:8080/admin/currentUser')
    .then(response => response.json())
    .then(user => {
        console.log(user)
        document.querySelector('#emailCurrentUser').innerHTML = user.email;
        document.querySelector('#rolesCurrentUser').innerHTML = user.roles.map(role => role.role.replace('ROLE_', '')).join(', ');
    })

async function getAdminPage() {
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
        <td><button class="btn btn-info" onclick="loadDataForEditModal(${user.id})">Edit</button></td>       
        
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
                delName.value = `${user.name}`;
                delSurname.value = `${user.surname}`;
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
console.log("Добавление пользователя");
const form_new = document.getElementById('addNewUserInTable');
const create_btn = document.getElementById('closeCreateButton');
const user_table_tab = document.getElementById('nav-home-tab');
const rolesSelect = document.querySelector('#rolesForNewUser');
form_new.addEventListener('submit', addNewUser);
console.log("Добавление пользователя этап №2");

async function addNewUser(event) {
    console.log("Добавление пользователя этап №3");
    event.preventDefault();
    const urlNew = "/admin/newUser";
    console.log("Добавление пользователя этап №4");
    let listOfRole = [];
    for (let i = 0; i < rolesSelect.selectedOptions.length; i++) {
        listOfRole.push({
            id: rolesSelect.selectedOptions[i].value
        });
    }
    console.log("Добавление пользователя этап №5");
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
// обновление пользователя
const role_ed = document.getElementById('editRole');
const form_ed = document.getElementById('formForEditing');
const id_ed = document.getElementById('editId');
const name_ed = document.getElementById('editName');
const lastName_ed = document.getElementById('editSurname')
const age_ed = document.getElementById('editAge')
const email_ed = document.getElementById('editEmail');
const password_ed = document.getElementById('editPassword')
const editModal = document.getElementById("editUserModal");
const closeEditButton = document.getElementById("closeEdit")
const bsEditModal = new bootstrap.Modal(editModal);

async function loadDataForEditModal(id) {
    const urlDataEd = '/admin/user/' + id;
    let usersPageEd = await fetch(urlDataEd);
    if (usersPageEd.ok) {

        await usersPageEd.json().then(user => {
            console.log('userData', JSON.stringify(user))
            id_ed.value = `${user.id}`;
            name_ed.value = `${user.name}`;
            lastName_ed.value = `${user.surname}`;
            age_ed.value = `${user.age}`;
            email_ed.value = `${user.email}`;
            password_ed.value = `${user.password}`;

            role_ed.innerHTML = '';

            // Получите роли для пользователя
            async function main() {
                const urlRoles = '/admin/getRoles';
                let rolesPage = await fetch(urlRoles);
                if (rolesPage.ok) {
                    await rolesPage.json().then(roles => {
                        roles.forEach(role => {
                            const option = document.createElement('option');
                            option.value = role.id;
                            option.text = role.role;
                            role_ed.appendChild(option);

                            // Выберите роль, если она уже назначена пользователю
                            if (user.roles.find(r => r.id === role.id)) {
                                option.selected = true;
                            }
                        });
                    });
                }
            }

            main();
            console.log("id_ed: " + id_ed.value + " !!")
            bsEditModal.show();
        })
    } else {
        alert(`Error, ${usersPageEd.status}`)
    }
}


async function editUser  () {
    let urlEdit = '/admin/editUser/' + id_ed.value;
    let listOfRole = [];
    for (let i = 0; i < role_ed.options.length; i++) {
        if (role_ed.options[i].selected) {
            let tmp = {};
            tmp["id"] = role_ed.options[i].value;
            listOfRole.push(tmp);
        }
    }
    let method = {
        method: 'PATCH',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: form_ed.elements['name'].value,
           surname: form_ed.elements['surname'].value,
            age: form_ed.elements['age'].value,
            email: form_ed.elements['email'].value,
            password: form_ed.elements['password'].value,
            roles: listOfRole
        })
    }
    console.log(urlEdit,method)
    await fetch(urlEdit,method).then(() => {
        closeEditButton.click();
        getAdminPage();
    })
}