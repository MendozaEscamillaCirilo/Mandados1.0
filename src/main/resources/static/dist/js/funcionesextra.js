function agregarproducto(id){
	Swal.fire({
        title: 'Ingrese la cantidad',
        input: 'number',
        inputAttributes: {
          autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Look up',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {

        },
        allowOutsideClick: () => !Swal.isLoading()
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire({
            title: `${result.value.login}'s avatar`,
            imageUrl: result.value.avatar_url
          })
        }
      })
}
function eliminarcomercio(id){
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
				url:"/eliminarcomercio/"+id,
				success: function(res){
					Swal.fire('Inhabilitado correctamente!', '', 'success');
					// location.reload();
					window.location.href = "/listacomercio";
				},
				error: function(res){
					Swal.fire('Sucedió un error!', '', 'error')
				}
			});
		} else if (result.isDenied) {
			console.log("inside else");
		  	Swal.fire('No se eliminará el comercio', '', 'info')
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
					Swal.fire('Inhabilitado correctamente!', '', 'success');
					// location.reload();
					window.location.href = "/listarepartidor";
				},
				error: function(res){
					Swal.fire('Sucedió un error!', '', 'error')
				}
			});
		} else if (result.isDenied) {
			console.log("inside else");
		  	Swal.fire('No se eliminará el comercio', '', 'info')
		}
	  })
}
function eliminarusuario(id){
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
				url:"/eliminarusuario/"+id,
				success: function(res){
					Swal.fire('Inhabilitado correctamente!', '', 'success');
					window.location.href = "/usuarios";
				},
				error: function(res){
					Swal.fire('Sucedió un error!', '', 'error')
				}
			});
		} else if (result.isDenied) {
			console.log("inside else");
		  	Swal.fire('No se eliminará el comercio', '', 'info')
		}
	  })
}
function eliminarProducto(id){
	Swal.fire({
		title: '¿Está seguro?',
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
					Swal.fire('eliminado correctamente!', '', 'success');
					window.location.href = "/listaproducto";
				},
				error: function(res){
					Swal.fire('Sucedió un error!', '', 'error')
				}
			});
		} else if (result.isDenied) {
			console.log("inside else");
		  	Swal.fire('No se eliminará el comercio', '', 'info')
		}
	  })
}