function logarUsuario() {

    var obj = {nome: login.value, senha: senhaLogin.value}

    let headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        mode: 'no-cors'
    };

    let url = "/usuario/login"
        fetch(url, {
        headers: headers,
        method: "POST",
        body: JSON.stringify(obj)
    })
        .then(res => res.json())
        .then(res => respostaLogin(res))
        .catch(err => alert(err.message))
}

function respostaLogin(resp) {
    if (resp.status == "OK") {
        window.location.href = "catalogoJogos2.html";
    } else {
        alert(resp.mensagemErro);
    }

}

function cadastrarUsuario() {
    var obj = {nome: nome.value, email: email.value, telefone: telefone.value, senha: senhaCadastro.value}

    let headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        mode: 'no-cors'
    };

    let url = "/usuario/cadastrar"
        fetch(url, {
        headers: headers,
        method: "POST",
        body: JSON.stringify(obj)
    })
        .then(res => res.text())
        .then(res => {console.log(res); return})
        .then(res => alert("Usuario Cadastrado"))
        .catch(err => console.log(err.message))
}

function buscarTabela(){
    fetch("/usuario/listar") // buscando na usuario no servidor
        .then(res => res.json()) // quando chegar a resposta, converte em json
        .then(res => atualizarTabela(res)) //atualiza tabela
       
}

function atualizarTabela(lista){
    let linhas = ""

    for(let usuario of lista){
        linhas+=
            `<tr>
            <td>${usuario.id}</td>
            <td>${usuario.nome}</td>
            <td>${usuario.senha}</td>
            <td>${usuario.email}</td>
            <td>${usuario.telefone}</td>
        </tr>`
    }

    let tabela = `
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>nome</th>
                    <th>senha</th>
                    <th>telefone</th>
                    <th>email</th>
                </tr>
            </thead>
            ${linhas}

        </table>`

    tabelaId.innerHTML = tabela;
}

function deleteUsuario() {
    var obj = {id:  parseInt(id.value)}

    let headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        mode: 'no-cors'
    };

    let url = "/usuario/" + parseInt(id.value)
    fetch(url, {
        headers: headers,
        method: "DELETE",
        body: JSON.stringify(obj)
    })
        .then(res => res.text())
        .then(res => {console.log(res); return})
        .then(res => {alert("Excluido com sucesso"); buscarTabela()})
        .catch(err => console.log(err.message))
}

function Atualizar() {
    var obj = {id: id.value, nome: nome.value, senha: senhaCadastro.value, telefone: telefone.value, email: email.value}

    let headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        mode: 'no-cors'
    };

    let url = "/usuario/" + id.value
    fetch(url, {
        headers: headers,
        method: "PUT",
        body: JSON.stringify(obj)
    })
        .then(res => res.text())
        .then(res => {console.log(res); return})
        .then(res => {alert("Usuario atualizado"); buscarTabela()})
        .catch(err => console.log(err.message))
}