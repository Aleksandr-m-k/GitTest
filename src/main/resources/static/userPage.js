async function getUserPage() {
    await fetch('http://localhost:8080/user/')
        .then(response => response.json())
        .then(response => {
                let user = response;
                let role = user.roles.map(role => role.role.replace('ROLE_', '')).join(', ');
                if (role.toString() === "USER") {
                    document.querySelector('#emailUser').innerHTML = user.email;
                    document.querySelector('#roleUser').innerHTML = role;
                }
                ;
                let tableBody = document.querySelector('#user-page');
                let roles = user.roles.map(role => role.role.replace('ROLE_', '')).join(', ');
                const row = document.createElement('tr');
                row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.surname}</td>
        <td>${user.age}</td>
        <td>${user.email}</td> 
        
        <td>${roles}</td> 
                           
        `;
                tableBody.appendChild(row);
            }
        )
        .catch(error => {
            console.error('Ошибка при получении пользователей:', error);
            const errorMessage = document.createElement('p');
        });
}

getUserPage();