
console.log("Зарегистрированный пользователь");
fetch('http://localhost:8080/admin/currentUser')
    .then(response=>response.json())
    .then(user=>{
        console.log(user)
        document.querySelector('#emailCurrentUser').innerHTML=user.email;
        document.querySelector('#rolesCurrentUser').innerHTML=user.roles.map(role => role.role.replace('ROLE_', '')).join(', ');
    })


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
                                                    data-bs-target="'#delUserModal-' + ${user.id}"
                                                    onclick="loadDelUserModal(${user.id})">
                                                Delete
                                            </button></td>
        
        `;
                tableBody.appendChild(row);
            }
        })
        .catch(error => {
                console.error('Ошибка при получении пользователей:', error);
                // Вы также можете отобразить сообщение об ошибке пользователю здесь
                const errorMessage = document.createElement('p');
        });