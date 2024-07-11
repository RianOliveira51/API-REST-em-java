function listarFetch() {
    fetch("http://localhost:8090/pedidos/listar")
        .then(res => res.json())
    .then(res => atualizarTabela(res))
    .catch(err => alert(err.message));
    console.log("Chamada realizada");
}

function atualizarTabela(lista) {
    let linhas = ""

    for(let pedido of lista){
        linhas+=
            `<tr>
                <td>
                    <img src="../img/jg1.jpg" class="img">
                </td>
                <td>${pedido.nomeJogo}</td>
                <td>${pedido.nomeCategoria}</td>
                <td>${pedido.preco}</td>
            </tr>`

}

    let table = `<table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">Jogo</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Categoria</th>
                            <th scope="col">Preço</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>    
        ${linhas}
        </table>`;
        tablePedidos.innerHTML = table;
}