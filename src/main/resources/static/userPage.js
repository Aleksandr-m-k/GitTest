async function getUserPage() {
    console.log('Fetching user...');
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
                console.log('Этап №1');
                let roles = user.roles.map(role => role.role.replace('ROLE_', '')).join(', ');
                const row = document.createElement('tr');
                console.log('Этап №2');
                row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.surname}</td>
        <td>${user.age}</td>
        <td>${user.email}</td> 
        
        <td>${roles}</td> 
                           
        `;
                tableBody.appendChild(row);
                console.log('Этап №3');
            }
        )
        .catch(error => {
            console.error('Ошибка при получении пользователей:', error);
            const errorMessage = document.createElement('p');
        });
    console.log('Этап №4');
}

getUserPage();