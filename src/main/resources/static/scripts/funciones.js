function eliminar(id){
	Swal.fire({
		title: 'Solo se va a inabilitar',
		text: 'No se va a borrar',
		showDenyButton: true,
		confirmButtonText: `Aceptar`,
		denyButtonText: `Cancelar`,
		allowOutsideClick: false
	  }).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url:"/eliminar/"+id,
				success: function(res){
					console.log(res);
				}
			});
		  Swal.fire('Inhabilitado correctamente!', '', 'success')
		} else if (result.isDenied) {
		  Swal.fire('No se eliminará el comercio', '', 'info')
		}
	  })
}
function eliminarCategoria(id){
	Swal.fire({
		title: 'Solo se va a inabilitar',
		text: 'No se va a borrar',
		showDenyButton: true,
		confirmButtonText: `Aceptar`,
		denyButtonText: `Cancelar`,
		allowOutsideClick: false
	  }).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url:"/eliminarcategoria/"+id,
				success: function(res){
					console.log(res);
				}
			});
		  Swal.fire('Inhabilitada correctamente!', '', 'success')
		} else if (result.isDenied) {
		  Swal.fire('No se eliminará la categoria', '', 'info')
		}
	  })
}
function eliminarOrden(id){
	Swal.fire({
		title: '¿Estás seguro de eliminar la orden?',
		text: 'Esta acción no se podrá deshacer',
		showDenyButton: true,
		confirmButtonText: `Aceptar`,
		denyButtonText: `Cancelar`,
		allowOutsideClick: false
	  }).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url:"/eliminarorden/"+id,
				success: function(res){
					console.log(res);
				}
			});
		  Swal.fire('Eliminado correctamente!', '', 'success')
		} else if (result.isDenied) {
		  Swal.fire('No se eliminará la orden', '', 'info')
		}
	  })
}
function eliminarProducto(id){
	Swal.fire({
		title: 'Solo se va a inabilitar',
		text: 'No se va a borrar',
		showDenyButton: true,
		confirmButtonText: `Aceptar`,
		denyButtonText: `Cancelar`,
		allowOutsideClick: false
	  }).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url:"/eliminarproducto/"+id,
				success: function(res){
					console.log(res);
				}
			});
		  Swal.fire('Inhabilitado correctamente!', '', 'success')
		} else if (result.isDenied) {
		  Swal.fire('No se eliminará el producto', '', 'info')
		}
	  })
}
function eliminarRepartidor(id){
	Swal.fire({
		title: 'Solo se va a inabilitar',
		text: 'No se va a borrar',
		showDenyButton: true,
		confirmButtonText: `Aceptar`,
		denyButtonText: `Cancelar`,
		allowOutsideClick: false
	  }).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url:"/eliminarrepartidor/"+id,
				success: function(res){
					console.log(res);
				}
			});
		  Swal.fire('Inhabilitado correctamente!', '', 'success')
		} else if (result.isDenied) {
		  Swal.fire('No se eliminará el repartidor', '', 'info')
		}
	  })
}
