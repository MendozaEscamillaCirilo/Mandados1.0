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