// Função para alternar entre as páginas com animação suave
function mostrarPagina(idPagina) {
    const paginas = document.querySelectorAll('.pagina');
    paginas.forEach(pagina => {
        pagina.style.transition = 'opacity 0.5s ease';
        pagina.style.opacity = pagina.id === idPagina ? '1' : '0';
        setTimeout(() => {
            pagina.style.display = pagina.id === idPagina ? 'block' : 'none';
        }, 500);
    });
}

function listarUsuarios() {
    fetch('/api/usuarios')
        .then(response => response.json())
        .then(usuarios => {
            const usuariosList = document.getElementById('usuarios-list');
            usuariosList.innerHTML = '';
            if (usuarios.length === 0) {
                usuariosList.innerHTML = '<li>Nenhum usuário encontrado.</li>';
                return;
            }
            usuarios.forEach(usuario => {
                const li = document.createElement('li');
                li.textContent = `ID: ${usuario.id} - Nome: ${usuario.nome} - Email: ${usuario.email}`;
                usuariosList.appendChild(li);
            });
        })
        .catch(error => alert('Erro ao listar usuários: ' + error.message));
}

function adicionarUsuario() {
    const nome = document.getElementById('usuario-nome').value;
    const email = document.getElementById('usuario-email').value;

    fetch('/api/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome, email }),
    })
        .then(response => {
            if (response.ok) {
                alert('Usuário adicionado com sucesso!');
                document.getElementById('usuario-nome').value = '';
                document.getElementById('usuario-email').value = '';
                listarUsuarios();
            } else {
                alert('Erro ao adicionar usuário.');
            }
        })
        .catch(error => alert('Erro ao adicionar usuário: ' + error.message));
}

function buscarUsuarioPorId() {
    const id = document.getElementById('usuario-id').value;

    fetch(`/api/usuarios/${id}`)
        .then(response => response.json())
        .then(usuario => {
            if (usuario) {
                alert(`Usuário encontrado: ${usuario.nome} - ${usuario.email}`);
            } else {
                alert('Usuário não encontrado.');
            }
        })
        .catch(error => alert('Erro ao buscar usuário: ' + error.message));
}

function editarUsuario() {
    const id = document.getElementById('usuario-id-editar').value;
    const nome = document.getElementById('usuario-nome-editar').value;
    const email = document.getElementById('usuario-email-editar').value;

    fetch(`/api/usuarios/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome, email }),
    })
        .then(response => {
            if (response.ok) {
                alert('Usuário editado com sucesso!');
                document.getElementById('usuario-id-editar').value = '';
                document.getElementById('usuario-nome-editar').value = '';
                document.getElementById('usuario-email-editar').value = '';
                listarUsuarios();
            } else {
                alert('Erro ao editar usuário. Verifique se o ID é válido.');
            }
        })
        .catch(error => alert('Erro ao editar usuário: ' + error.message));
}

function excluirUsuario() {
    const id = document.getElementById('usuario-id-excluir').value;

    fetch(`/api/usuarios/${id}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                alert('Usuário excluído com sucesso!');
                document.getElementById('usuario-id-excluir').value = '';
                listarUsuarios();
            } else {
                alert('Erro ao excluir usuário.');
            }
        })
        .catch(error => alert('Erro ao excluir usuário: ' + error.message));
}

// Função para listar produtos
function listarProdutos() {
    fetch('/api/produtos')
        .then(response => response.json())
        .then(produtos => {
            const produtosList = document.getElementById('produtos-list');
            produtosList.innerHTML = '';
            if (produtos.length === 0) {
                produtosList.innerHTML = '<li>Nenhum produto encontrado.</li>';
                return;
            }
            produtos.forEach(produto => {
                const li = document.createElement('li');
                li.textContent = `ID: ${produto.id} - Nome: ${produto.nome} - Preço: R$ ${produto.preco.toFixed(2)} - Proprietário: ${produto.usuarioId}`;
                produtosList.appendChild(li);
            });
        })
        .catch(error => alert('Erro ao listar produtos: ' + error.message));
}

function listarUsuariosParaProduto() {
    fetch('/api/usuarios')
        .then(response => response.json())
        .then(usuarios => {
            const usuariosSelect = document.getElementById('produto-usuarioId');
            usuariosSelect.innerHTML = '';

            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Selecione um usuário';
            usuariosSelect.appendChild(defaultOption);

            if (usuarios.length === 0) {
                alert('Nenhum usuário encontrado.');
                return;
            }

            usuarios.forEach(usuario => {
                const option = document.createElement('option');
                option.value = usuario.id;
                option.textContent = usuario.nome;
                usuariosSelect.appendChild(option);
            });
        })
        .catch(error => alert('Erro ao listar usuários: ' + error.message));
}

// Função para adicionar produto
function adicionarProduto() {
    const nome = document.getElementById('produto-nome').value;
    const preco = parseFloat(document.getElementById('produto-preco').value);
    const usuarioId = parseInt(document.getElementById('produto-usuarioId').value);

    if (!usuarioId) {
        alert('Por favor, selecione um usuário.');
        return;
    }

    fetch('/api/produtos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome, preco, usuarioId }),
    })
        .then(response => {
            if (response.ok) {
                alert('Produto adicionado com sucesso!');
                document.getElementById('produto-nome').value = '';
                document.getElementById('produto-preco').value = '';
                document.getElementById('produto-usuarioId').value = '';
                listarProdutos();
            } else {
                alert('Erro ao adicionar produto.');
            }
        })
        .catch(error => alert('Erro ao adicionar produto: ' + error.message));
}

// Função para editar produto
function editarProduto() {
    const id = document.getElementById('produto-id-editar').value;
    if (!id) {
        alert('Por favor, forneça um ID de produto.');
        return;
    }

    fetch(`/api/produtos/${id}`)
        .then(response => response.json())
        .then(produto => {
            if (produto) {
                // Preenche os campos de edição com os valores do produto
                document.getElementById('produto-nome-editar').value = produto.nome;
                document.getElementById('produto-preco-editar').value = produto.preco;
                document.getElementById('produto-usuarioId-editar').value = produto.usuarioId;

                // Atualiza a lista de usuários para o select de usuário
                listarUsuariosParaProduto();
            } else {
                alert('Produto não encontrado.');
            }
        })
        .catch(error => alert('Erro ao buscar produto: ' + error.message));
}

// Função para salvar a edição de produto
function salvarEdicaoProduto() {
    const id = document.getElementById('produto-id-editar').value;
    const nome = document.getElementById('produto-nome-editar').value;
    const preco = parseFloat(document.getElementById('produto-preco-editar').value);
    const usuarioId = parseInt(document.getElementById('produto-usuarioId-editar').value);

    if (!id || !usuarioId) {
        alert('Por favor, forneça um ID de produto e um usuário.');
        return;
    }

    // Envia a atualização para o servidor
    fetch(`/api/produtos/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nome, preco, usuarioId }),
    })
        .then(response => {
            if (response.ok) {
                alert('Produto editado com sucesso!');
                document.getElementById('produto-id-editar').value = '';
                document.getElementById('produto-nome-editar').value = '';
                document.getElementById('produto-preco-editar').value = '';
                document.getElementById('produto-usuarioId-editar').value = '';
                listarProdutos();
            } else {
                alert('Erro ao editar produto. Verifique se o ID é válido.');
            }
        })
        .catch(error => alert('Erro ao editar produto: ' + error.message));
}


// Função para excluir produto
function excluirProduto() {
    const id = document.getElementById('produto-id-excluir').value;

    fetch(`/api/produtos/${id}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                alert('Produto excluído com sucesso!');
                document.getElementById('produto-id-excluir').value = '';
                listarProdutos();
            } else {
                alert('Erro ao excluir produto.');
            }
        })
        .catch(error => alert('Erro ao excluir produto: ' + error.message));
}

// Chama a função listarUsuarios para preencher a lista de usuários e a função listarUsuariosParaProduto para preencher o select de usuários quando a página carregar
window.onload = () => {
    listarUsuarios();
    listarUsuariosParaProduto();
    listarProdutos();
};
