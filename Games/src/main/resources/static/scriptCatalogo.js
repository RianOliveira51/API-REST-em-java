function buscarJogos() {
    fetch("/games/listar")
    .then(res => res.json())
    .then(res => atualizarGridJogos(res));
}

function atualizarGridJogos(lista) {
    let cards = "";

    for (let jogos of lista) {
        cards +=
        `<div class="jogo card" style="align-items: center;">
                <img src="img/jg1.jpg" class="card-img-top">
                <div class="card-body">
                    <h5>${jogos.nome}</h5>
                    <p>R$ ${jogos.preco}</p>
                    <button class="btnpersonalisado btn">Comprar</button>
                </div>
            </div>`;
    }

    cardJogo.innerHTML = cards;
}