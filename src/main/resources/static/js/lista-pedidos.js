const buscadorPedidos = document.getElementById('buscadorPedidos');
const filtroEstado = document.getElementById('filtroEstado');
const limpiarFiltros = document.getElementById('limpiarFiltros');
const filasPedidos = Array.from(document.querySelectorAll('#tablaPedidos tr'));
const sinResultados = document.getElementById('sinResultados');

function filtrarPedidos() {
    const texto = buscadorPedidos.value.trim().toLowerCase();
    const estado = filtroEstado.value;
    let visibles = 0;

    filasPedidos.forEach((fila) => {
        const coincideTexto = fila.dataset.busqueda.toLowerCase().includes(texto);
        const coincideEstado = !estado || fila.dataset.estado === estado;
        const mostrar = coincideTexto && coincideEstado;

        fila.classList.toggle('d-none', !mostrar);

        if (mostrar) {
            visibles++;
        }
    });

    sinResultados.classList.toggle('d-none', visibles > 0 || filasPedidos.length === 0);
}

if (buscadorPedidos && filtroEstado && limpiarFiltros && sinResultados) {
    buscadorPedidos.addEventListener('input', filtrarPedidos);
    filtroEstado.addEventListener('change', filtrarPedidos);
    limpiarFiltros.addEventListener('click', () => {
        buscadorPedidos.value = '';
        filtroEstado.value = '';
        filtrarPedidos();
    });
}
