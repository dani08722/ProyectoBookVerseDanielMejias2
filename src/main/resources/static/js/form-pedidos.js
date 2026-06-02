const clienteSelect = document.getElementById('clienteId');
const direccionEnvio = document.getElementById('direccionEnvio');
const lineasPedido = document.getElementById('lineasPedido');
const aniadirLinea = document.getElementById('aniadirLinea');
const totalInput = document.getElementById('total');

if (clienteSelect && direccionEnvio) {
    clienteSelect.addEventListener('change', () => {
        const opcion = clienteSelect.selectedOptions[0];

        if (opcion && opcion.dataset.direccion && !direccionEnvio.value.trim()) {
            direccionEnvio.value = opcion.dataset.direccion;
        }
    });
}

function calcularTotales() {
    let total = 0;

    document.querySelectorAll('.linea-pedido').forEach((linea) => {
        const libroSelect = linea.querySelector('.libro-select');
        const cantidadInput = linea.querySelector('.cantidad-input');
        const subtotalInput = linea.querySelector('.subtotal-linea');
        const opcionLibro = libroSelect.selectedOptions[0];
        const precio = opcionLibro && opcionLibro.dataset.precio ? Number(opcionLibro.dataset.precio) : 0;
        const stock = opcionLibro && opcionLibro.dataset.stock ? Number(opcionLibro.dataset.stock) : 1;
        let cantidad = Number(cantidadInput.value);

        if (cantidad < 1) {
            cantidad = 1;
            cantidadInput.value = 1;
        }

        if (stock > 0) {
            cantidadInput.max = stock;

            if (cantidad > stock) {
                cantidad = stock;
                cantidadInput.value = stock;
            }
        }

        const subtotal = precio * cantidad;
        total += subtotal;
        subtotalInput.value = subtotal.toFixed(2) + ' EUR';
    });

    if (totalInput) {
        totalInput.value = total.toFixed(2);
    }
}

function prepararLinea(linea) {
    linea.querySelector('.libro-select').addEventListener('change', calcularTotales);
    linea.querySelector('.cantidad-input').addEventListener('input', calcularTotales);
    linea.querySelector('.eliminar-linea').addEventListener('click', () => {
        if (document.querySelectorAll('.linea-pedido').length > 1) {
            linea.remove();
            calcularTotales();
        }
    });
}

if (lineasPedido && aniadirLinea) {
    aniadirLinea.addEventListener('click', () => {
        const nuevaLinea = document.querySelector('.linea-pedido').cloneNode(true);

        nuevaLinea.querySelector('.libro-select').value = '';
        nuevaLinea.querySelector('.cantidad-input').value = 1;
        nuevaLinea.querySelector('.subtotal-linea').value = '0.00 EUR';
        lineasPedido.appendChild(nuevaLinea);
        prepararLinea(nuevaLinea);
        calcularTotales();
    });

    document.querySelectorAll('.linea-pedido').forEach(prepararLinea);
    calcularTotales();
}
