/**
 * 
 */

function confirmar(idcon) {
	let resposta = confirm("Confirma a exclusão do Contato?")
	
	if (resposta === true) {
		alert(idcon)
	}
}